package com.bluetree.web.user.forms;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class LoginFormValidator implements Validator {

    public boolean supports(Class clazz) {
        return clazz.equals(LoginForm.class);
    }

    public void validate(Object form, Errors errors) {
        LoginForm loginForm = (LoginForm) form;
        if (StringUtils.isBlank(loginForm.getLogin())) {
            errors.reject("User is empty");
        }
        if (StringUtils.isBlank(loginForm.getPassword())) {
            errors.reject("Password is empty");
        }
    }

}
