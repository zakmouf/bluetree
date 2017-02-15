package com.bluetree.web.admin.stock;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bluetree.util.FormatUtil;
import com.bluetree.util.MessageUtil;

public class StockPriceFormValidator implements Validator {

    public boolean supports(Class clazz) {
        return clazz.equals(StockPriceForm.class);
    }

    public void validate(Object form, Errors errors) {
        StockPriceForm stockPriceForm = (StockPriceForm) form;
        String[] lines = StringUtils.split(stockPriceForm.getText(), "\r\n");
        Set dates = new HashSet();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.length() > 0) {
                String[] tokens = StringUtils.split(line, ",");
                if (tokens.length != 2) {
                    errors.reject(MessageUtil.msg("Line <{0}> is incorrect",
                            new Integer(i + 1)));
                } else {
                    String date = tokens[0].trim();
                    String value = tokens[1].trim();
                    try {
                        FormatUtil.parseDate(date);
                    } catch (ParseException e) {
                        errors.reject(MessageUtil.msg(
                                "Line <{0}> contains incorrect date",
                                new Integer(i + 1)));
                    }
                    try {
                        Double.valueOf(value);
                    } catch (NumberFormatException e) {
                        errors.reject(MessageUtil.msg(
                                "Line <{0}> contains incorrect value",
                                new Integer(i + 1)));
                    }
                    if (dates.contains(date)) {
                        errors.reject(MessageUtil.msg(
                                "Line <{0}> contains duplicated date",
                                new Integer(i + 1)));
                    }
                    dates.add(date);
                }
            } else {
                errors.reject(MessageUtil.msg("Line <{0}> is empty",
                        new Integer(i + 1)));
            }
        }
    }

}
