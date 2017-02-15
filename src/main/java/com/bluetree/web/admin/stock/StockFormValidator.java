package com.bluetree.web.admin.stock;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class StockFormValidator implements Validator {

    public boolean supports(Class clazz) {
        return clazz.equals(StockForm.class);
    }

    public void validate(Object form, Errors errors) {
        StockForm stockForm = (StockForm) form;
        String symbol = stockForm.getSymbol().trim();
        String name = stockForm.getName().trim();
        if (StringUtils.isBlank(symbol)) {
            errors.reject("Symbol is blank");
        }
        if (StringUtils.isBlank(name)) {
            errors.reject("Name is blank");
        }
    }

}
