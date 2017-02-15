package com.bluetree.web.user.forms;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bluetree.util.FormatUtil;

public class PortfolioFormValidator implements Validator {

    public boolean supports(Class clazz) {
        return clazz.equals(PortfolioForm.class);
    }

    public void validate(Object form, Errors errors) {
        PortfolioForm portfolioForm = (PortfolioForm) form;
        if (StringUtils.isBlank(portfolioForm.getName())) {
            errors.reject("Name is blank");
        }
        if (StringUtils.isBlank(portfolioForm.getFromDate())) {
            errors.reject("From date is blank");
        }
        if (StringUtils.isBlank(portfolioForm.getToDate())) {
            errors.reject("To date is blank");
        }
        if (StringUtils.isBlank(portfolioForm.getBeta())) {
            errors.reject("Beta is blank");
        }
        if (StringUtils.isBlank(portfolioForm.getSize())) {
            errors.reject("Size is blank");
        }
        if (errors.hasErrors()) {
            return;
        }
        try {
            FormatUtil.parseDate(portfolioForm.getFromDate().trim());
        } catch (ParseException e) {
            errors.reject("From date is invalid");
        }
        try {
            FormatUtil.parseDate(portfolioForm.getToDate().trim());
        } catch (ParseException e) {
            errors.reject("To date is invalid");
        }
        try {
            Double.parseDouble(portfolioForm.getBeta().trim());
        } catch (NumberFormatException e) {
            errors.reject("Beta is invalid");
        }
        try {
            Integer.parseInt(portfolioForm.getSize().trim());
        } catch (NumberFormatException e) {
            errors.reject("Size is invalid");
        }

    }

}
