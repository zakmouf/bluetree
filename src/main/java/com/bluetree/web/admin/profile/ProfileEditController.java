package com.bluetree.web.admin.profile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bluetree.domain.Profile;
import com.bluetree.manager.ProfileManager;
import com.bluetree.util.MessageUtil;

public class ProfileEditController extends SimpleFormController {

    private ProfileManager profileManager;

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public void setProfileManager(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }

    protected Object formBackingObject(HttpServletRequest request)
            throws Exception {
        Long profileId = Long.valueOf(request.getParameter("profile"));
        Profile profile = profileManager.findProfile(profileId);
        ProfileForm profileForm = new ProfileForm();
        profileForm.setName(profile.getName());
        return profileForm;
    }

    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object form, BindException errors)
            throws Exception {
        Long profileId = Long.valueOf(request.getParameter("profile"));
        ProfileForm profileForm = (ProfileForm) form;
        String name = profileForm.getName().trim();
        Profile profile = profileManager.findProfile(name);
        if (profile != null) {
            if (!profile.getId().equals(profileId)) {
                errors.reject("Name already used");
                return showForm(request, errors, getFormView());
            }
        }
        profile = profileManager.findProfile(profileId);
        profile.setName(name);
        logger.info(MessageUtil.msg("update profile <{0}>", profile));
        profileManager.updateProfile(profile);
        ModelAndView mav = new ModelAndView(getSuccessView());
        return mav;
    }

}
