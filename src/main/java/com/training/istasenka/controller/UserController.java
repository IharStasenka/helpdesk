package com.training.istasenka.controller;

import com.training.istasenka.assembler.UserDtoAssembler;
import com.training.istasenka.converter.user.UserConverter;
import com.training.istasenka.dto.user.EngineerRatingDto;
import com.training.istasenka.dto.user.UserDto;
import com.training.istasenka.model.user.User;
import com.training.istasenka.service.user.UserService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class UserController {
    private static final Logger logger = LogManager.getLogger("DebugLevelLog");

    private final UserService userService;
    private final UserDtoAssembler userDtoAssembler;
    private final UserConverter userConverter;

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody User user) {

        var savedUserId = userService.addUser(user);
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
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        userService.updateUser(userConverter.convertUserDtoToUser(userDto), id);

        return ResponseEntity.status(NO_CONTENT).build();
    }

    @DeleteMapping(value = "/{name}")
    public ResponseEntity<Void> deleteByName(@PathVariable("name") String name) {
        userService.deleteUser(name);

        return ResponseEntity.status(NO_CONTENT).build();
    }

    @GetMapping("/engineers/{name}/rating")
    public ResponseEntity<EngineerRatingDto> getEngineerRating(@PathVariable("name") String engineerEmail) {
        return ResponseEntity.status(OK).body(userService.getEngineerRating(engineerEmail));
    }
}


