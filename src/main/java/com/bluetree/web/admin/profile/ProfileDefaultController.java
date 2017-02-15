package com.bluetree.web.admin.profile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.bluetree.domain.Profile;
import com.bluetree.manager.ProfileManager;
import com.bluetree.util.MessageUtil;

public class ProfileDefaultController extends AbstractController {

    private ProfileManager profileManager;

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public void setProfileManager(ProfileManager profileManager) {
        this.profileManager = profileManager;
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
        Long profileId = Long.valueOf(request.getParameter("profile"));
        Profile profile = profileManager.findProfile(profileId);
        logger.info(MessageUtil.msg("profile default <{0}>", profile));
        profileManager.updateDefaultProfile(profile);
        return new ModelAndView(getViewName());
    }

}
