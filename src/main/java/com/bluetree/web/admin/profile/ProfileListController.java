package com.bluetree.web.admin.profile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.bluetree.domain.Profile;
import com.bluetree.manager.ProfileManager;

public class ProfileListController extends AbstractController {

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
        List profiles = profileManager.getProfiles();
        Profile defaultProfile = profileManager.findDefaultProfile();
        Map model = new HashMap();
        model.put("profiles", profiles);
        model.put("defaultProfile", defaultProfile);
        return new ModelAndView(getViewName(), model);
    }

}
