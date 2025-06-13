package com.ead.authuser.validations;

import com.ead.authuser.dtos.UserRecordDto;
import com.ead.authuser.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    Logger logger = LogManager.getLogger(UserValidator.class);
    final Validator validator;
    final UserService userService;

    public UserValidator(@Qualifier("defaultValidator") Validator validator, UserService userService) {
        this.validator = validator;
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserRecordDto userRecordDto = (UserRecordDto) o;
        validator.validate(userRecordDto, errors);

        if(errors.hasErrors()) return;

        validateUserName(userRecordDto.username(), errors);
        validateEmail(userRecordDto.email(), errors);
    }

    private void validateUserName(String username, Errors errors) {
        if(userService.existsByUsername(username)){
            errors.rejectValue("username", "userNameError", "UserName is already exists.");
            logger.error("Error validation userName {}", username);
        }
    }

    private void validateEmail(String email, Errors errors) {
        if(userService.existsByEmail(email)){
            errors.rejectValue("email", "emailError", "email is already exists.");
            logger.error("Error validation email {}", email);
        }
    }
}
