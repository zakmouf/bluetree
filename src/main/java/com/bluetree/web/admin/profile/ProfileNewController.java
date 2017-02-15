package com.bluetree.web.admin.profile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bluetree.domain.Profile;
import com.bluetree.manager.ProfileManager;
import com.bluetree.util.MessageUtil;

public class ProfileNewController extends SimpleFormController {

    private ProfileManager profileManager;

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public void setProfileManager(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }

    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object form, BindException errors)
            throws Exception {
        ProfileForm profileForm = (ProfileForm) form;
        String name = profileForm.getName().trim();
        Profile profile = profileManager.findProfile(name);
        if (profile == null) {
            profile = new Profile();
            profile.setName(name);
            logger.info(MessageUtil.msg("new profile <{0}>", profile));
            profileManager.insertProfile(profile);
        }
        ModelAndView mav = new ModelAndView(getSuccessView());
        return mav;
    }

}
