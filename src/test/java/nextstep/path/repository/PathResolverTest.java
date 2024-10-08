package nextstep.path.repository;

import nextstep.line.domain.Line;
import nextstep.line.domain.Section;
import nextstep.line.repository.LineRepository;
import nextstep.path.application.ShortestPathFinder;
import nextstep.path.domain.LineSectionEdge;
import nextstep.path.payload.ShortestPathResponse;
import nextstep.station.domain.Station;
import nextstep.station.payload.StationMapper;
import nextstep.station.payload.StationResponse;
import nextstep.station.repository.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.*;

class PathResolverTest {
    private LineRepository lineRepository;
    private ShortestPathFinder<LineSectionEdge, Long> shortestPathFinder;

    private StationMapper stationMapper;
    private StationRepository stationRepository;

    private PathResolver pathResolver;

    private Station 교대역;
    private Station 강남역;
    private Station 양재역;
    private Station 남부터미널역;

    private List<Station> 전체역;

    private Line 이호선;
    private Line 신분당선;
    private Line 삼호선;

    /**
     * 교대역    --- *2호선* (10) ---    강남역
     * |                                 |
     * *3호선* (2)                   *신분당선* (10)
     * |                                 |
     * 남부터미널역  --- *3호선* (3) ---   양재
     */

    @BeforeEach
    void setUp() {
        교대역 = new Station(1L, "교대역");
        강남역 = new Station(2L, "강남역");
        양재역 = new Station(3L, "양재역");
        남부터미널역 = new Station(4L, "남부터미널역");
        전체역 = List.of(교대역, 강남역, 양재역, 남부터미널역);

        이호선 = new Line("2호선", "green", new Section(교대역.getId(), 강남역.getId(), 10L));
        신분당선 = new Line("신분당선", "red", new Section(강남역.getId(), 양재역.getId(), 10L));
        삼호선 = new Line("3호선", "orange", new Section(교대역.getId(), 남부터미널역.getId(), 2L));
        삼호선.addSection(남부터미널역.getId(), 양재역.getId(), 3L);

        lineRepository = mock(LineRepository.class);
        shortestPathFinder = new ShortestPathFinder<>();
        stationRepository = mock(StationRepository.class);
        stationMapper = new StationMapper(stationRepository);
        pathResolver = new PathResolver(lineRepository, shortestPathFinder, stationMapper);

        when(stationRepository.findByIdIn(anyCollection()))
                .thenAnswer(invocation -> {
                    Collection<Long> ids = invocation.getArgument(0);
                    return ids.stream()
                            .map(this::findStationById)
                            .collect(Collectors.toList());
                });
        when(lineRepository.findAll())
                .thenReturn(List.of(이호선, 신분당선, 삼호선));
    }

    @DisplayName("최단 거리와 경로를 반환한다")
    @Test
    void test() {
        ShortestPathResponse shortestPathResponse = pathResolver.get(교대역.getId(), 양재역.getId()).get();
        assertAll(
                () -> assertThat(shortestPathResponse.getDistance()).isEqualTo(5L),
                () -> assertThat(shortestPathResponse.getStations().stream().map(StationResponse::getName))
                        .containsExactly(교대역.getName(), 남부터미널역.getName(), 양재역.getName())

        );
    }

    @DisplayName("같은 경로를 두번 호출 할 경우 캐싱된 결과를 반환한다")
    @Test
    void test2() {
        //같은 경로를 두번 호출 할 경우
        pathResolver.get(교대역.getId(), 양재역.getId());
        ShortestPathResponse shortestPathResponse = pathResolver.get(교대역.getId(), 양재역.getId()).get();

        assertAll(
                () -> assertThat(shortestPathResponse.getDistance()).isEqualTo(5L),
                () -> assertThat(shortestPathResponse.getStations().stream().map(StationResponse::getName))
                        .containsExactly(교대역.getName(), 남부터미널역.getName(), 양재역.getName())
        );
        //캐싱된 결과를 반환한다
        verify(lineRepository, times(1)).findAll();
    }

    private Station findStationById(final Long id) {
        return 전체역.stream().filter(it -> it.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }
}
