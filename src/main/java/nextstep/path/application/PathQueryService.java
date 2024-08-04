package nextstep.path.application;

import nextstep.exceptions.ErrorMessage;
import nextstep.path.exceptions.PathNotFoundException;
import nextstep.path.payload.ShortestPathResponse;
import nextstep.path.repository.PathRepository;
import nextstep.station.domain.Station;
import nextstep.station.exception.NonExistentStationException;
import nextstep.station.repository.StationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class PathQueryService {

    private final StationRepository stationRepository;
    private final PathRepository pathRepository;

    public PathQueryService(final StationRepository stationRepository, final PathRepository pathRepository) {
        this.stationRepository = stationRepository;
        this.pathRepository = pathRepository;
    }

    public ShortestPathResponse getShortestPath(final Long source, final Long target) {
        assertStationExist(source, target);
        return pathRepository.get(source, target)
                .orElseThrow(() -> new PathNotFoundException(ErrorMessage.PATH_NOT_FOUND));
    }

    private void assertStationExist(final Long source, final Long target) {
        List<Station> stations = stationRepository.findByIdIn(List.of(source, target));
        if (stations.size() != 2) {
            throw new NonExistentStationException(ErrorMessage.NON_EXISTENT_STATION);
        }
    }

}
