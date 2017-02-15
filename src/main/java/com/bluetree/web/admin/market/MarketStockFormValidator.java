package com.bluetree.web.admin.market;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bluetree.util.MessageUtil;

public class MarketStockFormValidator implements Validator {

    public boolean supports(Class clazz) {
        return clazz.equals(MarketStockForm.class);
    }

    public void validate(Object form, Errors errors) {
        MarketStockForm stockPriceForm = (MarketStockForm) form;
        String[] lines = StringUtils.split(stockPriceForm.getText(), "\r\n");
        Set symbols = new HashSet();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.length() > 0) {
                String[] tokens = StringUtils.split(line, ",");
                if (tokens.length != 2) {
                    errors.reject(MessageUtil.msg("Line <{0}> is incorrect",
                            new Integer(i + 1)));
                } else {
                    String symbol = tokens[0].trim().toUpperCase();
                    String name = tokens[1].trim();
                    if (StringUtils.isBlank(symbol)) {
                        errors.reject(MessageUtil.msg(
                                "Line <{0}> contains incorrect symbol",
                                new Integer(i + 1)));
                    }
                    if (StringUtils.isBlank(name)) {
                        errors.reject(MessageUtil.msg(
                                "Line <{0}> contains incorrect name",
                                new Integer(i + 1)));
                    }
                    if (symbols.contains(symbol)) {
                        errors.reject(MessageUtil.msg(
                                "Line <{0}> contains duplicated symbol",
                                new Integer(i + 1)));
                    }
                    symbols.add(symbol);
                }
            } else {
                errors.reject(MessageUtil.msg("Line <{0}> is empty",
                        new Integer(i + 1)));
            }
        }
    }

}
