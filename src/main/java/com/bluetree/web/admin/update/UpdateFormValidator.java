package com.bluetree.web.admin.update;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bluetree.util.FormatUtil;

public class UpdateFormValidator implements Validator {

    public boolean supports(Class clazz) {
        return clazz.equals(UpdateForm.class);
    }

    public void validate(Object form, Errors errors) {
        UpdateForm updateForm = (UpdateForm) form;
        String startDate = updateForm.getStartDate().trim();
        String increment = updateForm.getIncrement().trim();
        if (StringUtils.isBlank(startDate)) {
            errors.reject("start is blank");
        }
        if (StringUtils.isBlank(increment)) {
            errors.reject("increment is blank");
        }
        if (errors.hasErrors()) {
            return;
        }
        try {
            FormatUtil.parseDate(startDate);
        } catch (ParseException e) {
            errors.reject("start is incorrect");
        }
        try {
            Integer.valueOf(increment);
        } catch (NumberFormatException e) {
            errors.reject("market is incorrect");
        }
    }

}
