package com.bluetree.web.admin.market;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MarketFormValidator implements Validator {

    public boolean supports(Class clazz) {
        return clazz.equals(MarketForm.class);
    }

    public void validate(Object form, Errors errors) {
        MarketForm marketForm = (MarketForm) form;
        String name = marketForm.getName().trim();
        String riskless = marketForm.getRiskless().trim();
        if (StringUtils.isBlank(name)) {
            errors.reject("Name is blank");
        }
        if (StringUtils.isBlank(riskless)) {
            errors.reject("Riskless is blank");
        }
        if (errors.hasErrors()) {
            return;
        }
        try {
            Double.valueOf(riskless);
        } catch (NumberFormatException e) {
            errors.reject("Riskless is incorrect");
        }
    }

}
