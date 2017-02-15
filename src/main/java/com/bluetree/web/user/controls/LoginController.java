package com.bluetree.web.user.controls;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bluetree.domain.User;
import com.bluetree.manager.UserManager;
import com.bluetree.util.MessageUtil;
import com.bluetree.web.user.forms.LoginForm;

public class LoginController extends SimpleFormController {

    private UserManager userManager;

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    protected ModelAndView showForm(HttpServletRequest request,
            HttpServletResponse response, BindException errors)
            throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            Map model = new HashMap();
            ModelAndView mav = new ModelAndView(getSuccessView(), model);
            return mav;
        }
        return super.showForm(request, response, errors);
    }

    protected Object formBackingObject(HttpServletRequest request)
            throws Exception {
        return new LoginForm();
    }

    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object form, BindException errors)
            throws Exception {

        LoginForm loginForm = (LoginForm) form;
        String login = loginForm.getLogin().trim();
        String password = loginForm.getPassword().trim();

        User user = userManager.findUser(login);

        if (user == null || !password.equals(user.getPassword())) {
            errors.reject("Incorrect user or password");
            return showForm(request, response, errors);
        }

        request.getSession().setAttribute("user", user);
        
        logger.info(MessageUtil.msg("login user <{0}>", user));

        Map model = new HashMap();
        ModelAndView mav = new ModelAndView(getSuccessView(), model);
        return mav;
    }

}
