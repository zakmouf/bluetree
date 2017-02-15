package com.bluetree.web.admin.update;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.bluetree.manager.MarketManager;
import com.bluetree.util.DateUtil;
import com.bluetree.util.FormatUtil;
import com.bluetree.util.MessageUtil;

public class UpdateController extends SimpleFormController {

    private static final String UPDATE_TRIGGER = "UPDATE_TRIGGER";

    private static final String UPDATE_GROUP = "UPDATE_GROUP";

    private MarketManager marketManager;

    public MarketManager getMarketManager() {
        return marketManager;
    }

    public void setMarketManager(MarketManager marketManager) {
        this.marketManager = marketManager;
    }

    private Scheduler scheduler;

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    protected Object formBackingObject(HttpServletRequest request)
            throws Exception {
        List markets = marketManager.getMarkets();
        UpdateForm updateForm = new UpdateForm();
        updateForm.setMarketId(null);
        updateForm.setStartDate(FormatUtil.formatDate(DateUtil.getDate(1,
                Calendar.JANUARY, 2000)));
        updateForm.setIncrement(Integer.toString(250));
        updateForm.setMarkets(markets);

        String jobNames[] = scheduler.getJobNames(UPDATE_GROUP);
        List jobDetails = new ArrayList();
        for (int i = 0; i < jobNames.length; i++) {
            Trigger trigger = scheduler.getTriggersOfJob(jobNames[i],
                    UPDATE_GROUP)[0];
            if (scheduler
                    .getTriggerState(trigger.getName(), trigger.getGroup()) != Trigger.STATE_COMPLETE) {
                JobDetail jobDetail = scheduler.getJobDetail(jobNames[i],
                        UPDATE_GROUP);
                jobDetails.add(jobDetail);
            }
        }
        updateForm.setJobDetails(jobDetails);

        return updateForm;
    }

    protected ModelAndView onSubmit(HttpServletRequest request,
            HttpServletResponse response, Object form, BindException errors)
            throws Exception {

        UpdateForm updateForm = (UpdateForm) form;

        Long marketId = Long.valueOf(updateForm.getMarketId());
        Date startDate = FormatUtil.parseDate(updateForm.getStartDate().trim());
        Integer increment = Integer.valueOf(updateForm.getIncrement().trim());

        long now = System.currentTimeMillis();
        String suffix = "-" + now + "-" + Math.random();

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobBeanName", "updateJob");
        jobDataMap.put("timeStamp", new Long(now));
        jobDataMap.put("marketId", marketId);
        jobDataMap.put("startDate", startDate);
        jobDataMap.put("increment", increment);

        Market market = marketManager.findMarket(marketId);
        logger.info(MessageUtil.msg(
                "Update market <{0}> from <{1}> with <{2}>", market, FormatUtil
                        .formatDate(startDate), increment));

        JobDetail jobDetail = new JobDetail("J" + suffix, UPDATE_GROUP,
                UpdateJobBean.class);
        jobDetail.setJobDataMap(jobDataMap);

        Trigger trigger = new SimpleTrigger("T" + suffix, UPDATE_TRIGGER,
                jobDetail.getName(), jobDetail.getGroup(), new Date(), null, 0,
                0);

        scheduler.scheduleJob(jobDetail, trigger);

        return new ModelAndView(getSuccessView());
    }
}
