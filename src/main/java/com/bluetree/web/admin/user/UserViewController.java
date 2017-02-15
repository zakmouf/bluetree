package com.bluetree.web.admin.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.bluetree.domain.User;
import com.bluetree.manager.UserManager;

public class UserViewController extends AbstractController {

    private UserManager userManager;

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    private String viewName;

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Long userId = Long.valueOf(request.getParameter("user"));
        User user = userManager.findUser(userId);
        List profiles = userManager.getProfiles(user);
        Map model = new HashMap();
        model.put("user", user);
        model.put("profiles", profiles);
        return new ModelAndView(getViewName(), model);
    }

}
