package com.bluetree.web.user.controls;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.bluetree.domain.Market;
import com.bluetree.domain.Portfolio;
import com.bluetree.domain.User;
import com.bluetree.manager.PortfolioManager;
import com.bluetree.project.ProjectConfig;
import com.bluetree.project.ProjectException;
import com.bluetree.project.Projector;

public class ProjectController extends AbstractController {

    private String viewName;

    private String loginView;

    public String getLoginView() {
        return loginView;
    }

    public void setLoginView(String loginView) {
        this.loginView = loginView;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    private PortfolioManager portfolioManager;

    public PortfolioManager getPortfolioManager() {
        return portfolioManager;
    }

    public void setPortfolioManager(PortfolioManager portfolioManager) {
        this.portfolioManager = portfolioManager;
    }

    private Projector projector;

    public Projector getProjector() {
        return projector;
    }

    public void setProjector(Projector projector) {
        this.projector = projector;
    }

    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return new ModelAndView(getLoginView());
        }
        Long portfolioId = Long.valueOf(request.getParameter("portfolio"));
        Portfolio portfolio = portfolioManager.findPortfolio(portfolioId);
        Market market = portfolioManager.getMarket(portfolio);

        ProjectConfig config = new ProjectConfig(portfolio);
        String error = null;
        try {
            projector.project(config);
            JFreeChart chart = generateChart(config.getDates(), config
                    .getIndiceNavs(), config.getPortfolioNavs());
            request.getSession().setAttribute("project", chart);
        } catch (ProjectException e) {
            error = e.getMessage();
        }

        Map model = new HashMap();
        model.put("portfolio", portfolio);
        model.put("market", market);
        model.put("error", error);
        ModelAndView mav = new ModelAndView(getViewName(), model);
        return mav;
    }

    public static JFreeChart generateChart(Date[] dates, double[] indiceNavs,
            double[] portfolioNavs) {

        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        TimeSeries series = new TimeSeries("Indice");
        for (int i = 0; i < dates.length; i++) {
            series.add(new Day(dates[i]), indiceNavs[i]);
            if (min > indiceNavs[i]) {
                min = indiceNavs[i];
            }
            if (max < indiceNavs[i]) {
                max = indiceNavs[i];
            }
        }
        dataset.addSeries(series);
        series = new TimeSeries("Portoflio");
        for (int i = 0; i < dates.length; i++) {
            series.add(new Day(dates[i]), portfolioNavs[i]);
            if (min > portfolioNavs[i]) {
                min = portfolioNavs[i];
            }
            if (max < portfolioNavs[i]) {
                max = portfolioNavs[i];
            }
        }
        dataset.addSeries(series);

        DateAxis domainAxis = new DateAxis();
        domainAxis.setTickMarksVisible(false);
        domainAxis.setAxisLineVisible(false);
        domainAxis.setTickLabelFont(new Font("Verdana", Font.PLAIN, 10));

        NumberAxis rangeAxis = new NumberAxis();
        rangeAxis.setAutoRangeIncludesZero(false);
        rangeAxis.setTickMarksVisible(false);
        rangeAxis.setAxisLineVisible(false);
        rangeAxis.setTickLabelFont(new Font("Verdana", Font.PLAIN, 10));
        rangeAxis.setLowerBound(min - 5.0);
        rangeAxis.setUpperBound(max + 5.0);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true,
                false);
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(0, Color.BLUE);

        XYPlot plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineStroke(null);
        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlineStroke(new BasicStroke(0.5f));
        plot.setRangeGridlinePaint(Color.DARK_GRAY);
        plot.clearDomainMarkers();
        plot.clearRangeMarkers();

        JFreeChart chart = new JFreeChart(null, null, plot, true);
        chart.setBackgroundPaint(Color.white);
        chart.setAntiAlias(true);
        chart.setBorderVisible(false);
        chart.getLegend().setBorder(BlockBorder.NONE);
        chart.getLegend().setItemFont(new Font("Verdana", Font.PLAIN, 10));

        return chart;
    }

}
