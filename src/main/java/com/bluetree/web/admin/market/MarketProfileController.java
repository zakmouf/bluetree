package com.bluetree.web.admin.market;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bluetree.domain.Market;
import com.bluetree.domain.Profile;
import com.bluetree.manager.MarketManager;
import com.bluetree.manager.ProfileManager;
import com.bluetree.util.MessageUtil;

public class MarketProfileController extends SimpleFormController {

    private MarketManager marketManager;

    public MarketManager getMarketManager() {
        return marketManager;
    }

    public void setMarketManager(MarketManager marketManager) {
        this.marketManager = marketManager;
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
        Long marketId = Long.valueOf(request.getParameter("market"));
        Market market = marketManager.findMarket(marketId);
        List profiles = marketManager.getProfiles(market);
        String[] profileId2 = new String[profiles.size()];
        Iterator iterator = profiles.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            Profile profile = (Profile) iterator.next();
            profileId2[i] = profile.getId().toString();
        }
        profiles = profileManager.getProfiles();
        MarketProfileForm marketProfileForm = new MarketProfileForm();
        marketProfileForm.setProfiles(profiles);
        marketProfileForm.setProfileId(new String[0]);
        marketProfileForm.setProfileId2(profileId2);
        return marketProfileForm;
    }

    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object form, BindException errors)
            throws Exception {
        Long marketId = Long.valueOf(request.getParameter("market"));
        Market market = marketManager.findMarket(marketId);
        MarketProfileForm marketProfileForm = (MarketProfileForm) form;
        String[] profileId = marketProfileForm.getProfileId();
        List profiles = new ArrayList();
        for (int i = 0; i < profileId.length; i++) {
            Profile profile = profileManager.findProfile(Long
                    .valueOf(profileId[i]));
            profiles.add(profile);
        }
        logger.info(MessageUtil.msg("market profiles <{0}>", market));
        marketManager.updateProfiles(market, profiles);
        ModelAndView mav = new ModelAndView(getSuccessView() + market.getId());
        return mav;
    }

}
