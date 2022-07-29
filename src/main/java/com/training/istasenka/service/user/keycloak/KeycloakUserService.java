package com.training.istasenka.service.user.keycloak;

import com.training.istasenka.dto.user.PasswordChange;
import com.training.istasenka.dto.user.UserLoginDto;
import com.training.istasenka.model.user.User;
import org.keycloak.representations.AccessTokenResponse;

public interface KeycloakUserService {

    void postUser(User user);

    void deleteUser( User user);

    AccessTokenResponse getAccessToken(UserLoginDto userLoginDto);

    void changePassword(PasswordChange passwordChangeData, String username);

}
