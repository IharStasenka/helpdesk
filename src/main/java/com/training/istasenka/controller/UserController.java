package com.training.istasenka.controller;

import com.training.istasenka.assembler.UserDtoAssembler;
import com.training.istasenka.converter.user.UserConverter;
import com.training.istasenka.dto.user.EngineerRatingDto;
import com.training.istasenka.dto.user.PasswordChange;
import com.training.istasenka.dto.user.UserDto;
import com.training.istasenka.dto.user.UserLoginDto;
import com.training.istasenka.service.user.db.UserService;
import com.training.istasenka.service.user.keycloak.KeycloakUserService;
import com.training.istasenka.validator.user.Password;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.Email;

import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
@Validated
public class UserController {
    private static final Logger logger = LogManager.getLogger("DebugLevelLog");

    private final KeycloakUserService keycloakUserService;
    private final UserService userService;
    private final UserDtoAssembler userDtoAssembler;
    private final UserConverter userConverter;

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody @Validated UserDto userDto) {

        var savedUserId = userService.addUser(userConverter.convertUserDtoToUser(userDto));

        var currentUri = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
        var savedUserLocation = currentUri + "/" + savedUserId;

        return ResponseEntity
                .status(CREATED)
                .header(HttpHeaders.LOCATION, savedUserLocation)
                .body(savedUserId);
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<UserDto> getByName(@PathVariable("name") String name) {
        var user = userService.getUser(name);
        return ResponseEntity
                .status(OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userDtoAssembler.toModel(user));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(
            @PathVariable("id") Long id,
            @RequestBody @Validated UserDto userDto) {
        userService.updateUser(userConverter.convertUserDtoToUser(userDto), id);

        return ResponseEntity.status(NO_CONTENT).build();
    }

    @DeleteMapping(value = "/{name}")
    public ResponseEntity<Void> deleteByName(@PathVariable("name") @Email String name) {
        userService.deleteUser(name);

        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping("/engineers/{name}/rating")
    public ResponseEntity<EngineerRatingDto> getEngineerRating(@PathVariable("name") @Email String engineerEmail) {
        return ResponseEntity.status(OK).body(userService.getEngineerRating(engineerEmail));
    }

    @GetMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(
            @RequestParam(name = "username") @Email String username,
            @RequestParam(name = "password") @Password String password) {
        var accessToken =
                keycloakUserService.getAccessToken(UserLoginDto.builder().username(username).password(password).build());
        return ResponseEntity.status(OK).body(accessToken);
    }

    @PutMapping("/{username}/changePassword")
    public ResponseEntity<Void> changePassword(
            @PathVariable @Email String username,
            @RequestBody @Validated PasswordChange passwordChangeData) {
        keycloakUserService.changePassword(passwordChangeData, username);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}


