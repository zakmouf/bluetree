package com.bluetree.web.admin.profile;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ProfileFormValidator implements Validator {

    public boolean supports(Class clazz) {
        return clazz.equals(ProfileForm.class);
    }

    public void validate(Object form, Errors errors) {
        ProfileForm profileForm = (ProfileForm) form;
        String name = profileForm.getName().trim();
        if (StringUtils.isBlank(name)) {
            errors.reject("Name is blank");
        }
    }

}
