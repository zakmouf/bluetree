package com.bluetree.web.user.controls;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bluetree.domain.Market;
import com.bluetree.domain.Portfolio;
import com.bluetree.domain.User;
import com.bluetree.manager.MarketManager;
import com.bluetree.manager.PortfolioManager;
import com.bluetree.manager.UserManager;
import com.bluetree.optimization.OptimizeJobBean;
import com.bluetree.util.DateUtil;
import com.bluetree.util.FormatUtil;
import com.bluetree.util.MessageUtil;
import com.bluetree.web.user.forms.PortfolioForm;

public class PortfolioController extends SimpleFormController {

    private String loginView;

    public String getLoginView() {
        return loginView;
    }

    public void setLoginView(String loginView) {
        this.loginView = loginView;
    }

    private UserManager userManager;

    private MarketManager marketManager;

    private PortfolioManager portfolioManager;

    private Scheduler scheduler;

    public MarketManager getMarketManager() {
        return marketManager;
    }

    public void setMarketManager(MarketManager marketManager) {
        this.marketManager = marketManager;
    }

    public PortfolioManager getPortfolioManager() {
        return portfolioManager;
    }

    public void setPortfolioManager(PortfolioManager portfolioManager) {
        this.portfolioManager = portfolioManager;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

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
        if (user == null) {
            return new ModelAndView(getLoginView());
        }
        return super.showForm(request, response, errors);
    }

    protected Object formBackingObject(HttpServletRequest request)
            throws Exception {
        PortfolioForm portfolioForm = new PortfolioForm();
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return portfolioForm;
        }
        List profiles = userManager.getProfiles(user);
        List markets = marketManager.getMarkets(profiles);
        portfolioForm.setFromDate(FormatUtil.formatDate(DateUtil.getDate(1,
                Calendar.JANUARY, 2005)));
        portfolioForm.setToDate(FormatUtil.formatDate(DateUtil.getDate(31,
                Calendar.DECEMBER, 2005)));
        portfolioForm.setBeta("0.9");
        portfolioForm.setSize("20");
        portfolioForm.setMarkets(markets);
        return portfolioForm;
    }

    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object form, BindException errors)
            throws Exception {

        PortfolioForm portfolioForm = (PortfolioForm) form;

        User user = (User) request.getSession().getAttribute("user");

        Long marketId = Long.valueOf(portfolioForm.getMarketId());
        Market market = marketManager.findMarket(marketId);

        Portfolio portfolio = new Portfolio();
        portfolio.setName(portfolioForm.getName().trim());
        portfolio.setFromDate(FormatUtil.parseDate(portfolioForm.getFromDate()
                .trim()));
        portfolio.setToDate(FormatUtil.parseDate(portfolioForm.getToDate()
                .trim()));
        portfolio.setBeta(Double.valueOf(portfolioForm.getBeta().trim()));
        portfolio.setSize(Integer.valueOf(portfolioForm.getSize().trim()));

        portfolioManager.insertPortfolio(portfolio);
        portfolioManager.setMarket(portfolio, market);
        portfolioManager.setUser(portfolio, user);

        logger.info(MessageUtil.msg("new portfolio <{0}>", portfolio));

        long now = System.currentTimeMillis();
        String suffix = "-" + now + "-" + Math.random();

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobBeanName", "optimizeJob");
        jobDataMap.put("timeStamp", new Long(now));
        jobDataMap.put("portfolioId", portfolio.getId());

        JobDetail jobDetail = new JobDetail("J" + suffix, "OPTIMIZE_JOB",
                OptimizeJobBean.class);
        jobDetail.setJobDataMap(jobDataMap);

        Trigger trigger = new SimpleTrigger("T" + suffix, "OPTIMIZE_TRIGGER",
                jobDetail.getName(), jobDetail.getGroup(), new Date(), null, 0,
                0);

        scheduler.scheduleJob(jobDetail, trigger);

        Map model = new HashMap();
        ModelAndView mav = new ModelAndView(getSuccessView(), model);
        return mav;
    }

}
