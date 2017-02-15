package com.bluetree.web.user.forms;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SigninFormValidator implements Validator {

    public boolean supports(Class clazz) {
        return clazz.equals(SigninForm.class);
    }

    public void validate(Object form, Errors errors) {
        SigninForm signinForm = (SigninForm) form;
        if (StringUtils.isBlank(signinForm.getLogin())) {
            errors.reject("User is blank");
        }
        if (StringUtils.isBlank(signinForm.getPassword())) {
            errors.reject("Password is blank");
        }
        if (StringUtils.isBlank(signinForm.getPassword2())) {
            errors.reject("Password2 is blank");
        }
        if (StringUtils.isBlank(signinForm.getName())) {
            errors.reject("Name is blank");
        }
        if (StringUtils.isBlank(signinForm.getCompany())) {
            errors.reject("Company is blank");
        }
        if (StringUtils.isBlank(signinForm.getEmail())) {
            errors.reject("Email is blank");
        }
        if (StringUtils.isBlank(signinForm.getPhone())) {
            errors.reject("Phone is blank");
        }
        if (StringUtils.isBlank(signinForm.getAddress())) {
            errors.reject("Address is blank");
        }
        if (StringUtils.isBlank(signinForm.getZip())) {
            errors.reject("Zip is blank");
        }
        if (StringUtils.isBlank(signinForm.getCity())) {
            errors.reject("City is blank");
        }
        if (StringUtils.isBlank(signinForm.getCountry())) {
            errors.reject("Country is blank");
        }
        if (errors.hasErrors()) {
            return;
        }
        if (!signinForm.getPassword2().equals(signinForm.getPassword())) {
            errors.reject("Passwords are different");
        }
    }

}
