package com.bluetree.web.admin.stock;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bluetree.util.FormatUtil;

public class StockViewFormValidator implements Validator {

    public boolean supports(Class clazz) {
        return clazz.equals(StockViewForm.class);
    }

    public void validate(Object form, Errors errors) {
        StockViewForm stockViewForm = (StockViewForm) form;
        String fromDate = stockViewForm.getFromDate().trim();
        String toDate = stockViewForm.getToDate().trim();
        if (StringUtils.isBlank(fromDate)) {
            errors.reject("From is empty");
        }
        if (StringUtils.isBlank(toDate)) {
            errors.reject("To is empty");
        }
        if (errors.hasErrors()) {
            return;
        }
        try {
            FormatUtil.parseDate(fromDate);
        } catch (ParseException e) {
            errors.reject("From is incorrect");
        }
        try {
            FormatUtil.parseDate(toDate);
        } catch (ParseException e) {
            errors.reject("To is incorrect");
        }
    }

}
