package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserRecordDto;
import com.ead.authuser.dtos.views.UserView;
import com.ead.authuser.exceptions.NotFoundException;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }
    @GetMapping("/{userId}")
    public UserModel getOneUser(@PathVariable(value = "userId")UUID userId){
        return userService.findById(userId)
                .orElseThrow(() -> new NotFoundException("Error: User not found."));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId")UUID userId){
        var userModel = getOneUser(userId);
        userService.delete(userModel);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted sucessfully");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "userId")UUID userId,
                                             @RequestBody @Validated(UserView.UserPut.class)
                                             @JsonView(UserView.UserPut.class)
                                             UserRecordDto userRecordDto){

        var userModel = getOneUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userRecordDto, userModel));
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId")UUID userId,
                                             @RequestBody @Validated(UserView.PasswordPut.class)
                                             @JsonView(UserView.PasswordPut.class)
                                             UserRecordDto userRecordDto){

        var userModel = getOneUser(userId);
        if(!userModel.getPassword().equals(userRecordDto.oldPassword())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Mismatched old password!");
        }
        userService.updatePassword(userRecordDto, userModel);
        return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully");
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "userId")UUID userId,
                                                 @RequestBody @Validated(UserView.ImagePut.class)
                                                 @JsonView(UserView.ImagePut.class)
                                                 UserRecordDto userRecordDto){

        var userModel = getOneUser(userId);
        userService.updateImage(userRecordDto, userModel);
        return ResponseEntity.status(HttpStatus.OK).body("Image updated successfully");
    }
}
