package com.bluetree.web.user.controls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bluetree.domain.Profile;
import com.bluetree.domain.User;
import com.bluetree.manager.ProfileManager;
import com.bluetree.manager.UserManager;
import com.bluetree.util.MessageUtil;
import com.bluetree.web.user.forms.SigninForm;

public class SigninController extends SimpleFormController {

    private UserManager userManager;

    private ProfileManager profileManager;

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public void setProfileManager(ProfileManager profileManager) {
        this.profileManager = profileManager;
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
        return new SigninForm();
    }

    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object form, BindException errors)
            throws Exception {

        SigninForm signinForm = (SigninForm) form;
        String login = signinForm.getLogin().trim();

        User user = userManager.findUser(login);

        if (user != null) {
            errors.reject("User already exists");
            return showForm(request, response, errors);
        }

        user = new User();
        user.setLogin(signinForm.getLogin().trim());
        user.setPassword(signinForm.getPassword().trim());
        user.setName(signinForm.getName().trim());
        user.setCompany(signinForm.getCompany().trim());
        user.setEmail(signinForm.getEmail().trim());
        user.setPhone(signinForm.getPhone().trim());
        user.setAddress(signinForm.getAddress().trim());
        user.setZip(signinForm.getZip().trim());
        user.setCity(signinForm.getCity().trim());
        user.setCountry(signinForm.getCountry().trim());
        userManager.insertUser(user);

        Profile profile = profileManager.findDefaultProfile();
        List profiles = new ArrayList();
        profiles.add(profile);
        userManager.updateProfiles(user, profiles);

        logger.info(MessageUtil.msg("new user <{0}> with profile <{1}>", user, profile));

        request.getSession().setAttribute("user", user);

        Map model = new HashMap();
        ModelAndView mav = new ModelAndView(getSuccessView(), model);
        return mav;
    }

}
