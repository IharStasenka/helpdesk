package com.training.istasenka.service.user;

import com.training.istasenka.dto.user.EngineerRatingDto;
import com.training.istasenka.model.user.User;

import java.util.List;

public interface UserService {
    User getUser(String email);

    Long addUser(User user);

    List<User> getUsers(int amountOfUsers, Long startId);

    void deleteUser(String email);

    void updateUser(User user, Long userId);

    EngineerRatingDto getEngineerRating(String engineerEmail);

}
