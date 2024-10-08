package nextstep.security.application;

import nextstep.security.domain.Authentication;
import nextstep.security.domain.UserInfo;

public interface UserDetailsService<CREDENTIALS, PRINCIPAL> {

  Authentication<CREDENTIALS, PRINCIPAL> authenticateByUserInfo(
      UserInfo userInfo);

}
