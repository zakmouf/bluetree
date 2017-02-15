package com.bluetree.web.user.controls;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.bluetree.domain.User;
import com.bluetree.util.MessageUtil;

public class LogoutController extends AbstractController {

    private String viewName;

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        request.getSession().removeAttribute("user");
        logger.info(MessageUtil.msg("logout user <{0}>", user));
        Map model = new HashMap();
        ModelAndView mav = new ModelAndView(getViewName(), model);
        return mav;
    }

}
