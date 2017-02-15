package com.bluetree.web.admin.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

public class UserProfileController extends SimpleFormController {

    private UserManager userManager;

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    private ProfileManager profileManager;

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public void setProfileManager(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }

    protected Object formBackingObject(HttpServletRequest request)
            throws Exception {
        Long userId = Long.valueOf(request.getParameter("user"));
        User user = userManager.findUser(userId);
        List profiles = userManager.getProfiles(user);
        String[] profileId2 = new String[profiles.size()];
        Iterator iterator = profiles.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            Profile profile = (Profile) iterator.next();
            profileId2[i] = profile.getId().toString();
        }
        profiles = profileManager.getProfiles();
        UserProfileForm userProfileForm = new UserProfileForm();
        userProfileForm.setProfiles(profiles);
        userProfileForm.setProfileId(new String[0]);
        userProfileForm.setProfileId2(profileId2);
        return userProfileForm;
    }

    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object form, BindException errors)
            throws Exception {
        Long userId = Long.valueOf(request.getParameter("user"));
        User user = userManager.findUser(userId);
        UserProfileForm userProfileForm = (UserProfileForm) form;
        String[] profileId = userProfileForm.getProfileId();
        List profiles = new ArrayList();
        for (int i = 0; i < profileId.length; i++) {
            Profile profile = profileManager.findProfile(Long
                    .valueOf(profileId[i]));
            profiles.add(profile);
        }
        logger.info(MessageUtil.msg("user profiles <{0}>", user));
        userManager.updateProfiles(user, profiles);
        ModelAndView mav = new ModelAndView(getSuccessView() + user.getId());
        return mav;
    }

}
