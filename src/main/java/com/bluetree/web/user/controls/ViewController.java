package com.bluetree.web.user.controls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.bluetree.domain.Market;
import com.bluetree.domain.Portfolio;
import com.bluetree.domain.User;
import com.bluetree.manager.PortfolioManager;

public class ViewController extends AbstractController {

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

    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return new ModelAndView(getLoginView());
        }
        Long portfolioId = Long.valueOf(request.getParameter("portfolio"));
        Portfolio portfolio = portfolioManager.findPortfolio(portfolioId);
        Market market = portfolioManager.getMarket(portfolio);
        List positions = portfolioManager.getPositions(portfolio);
        Map model = new HashMap();
        model.put("portfolio", portfolio);
        model.put("market", market);
        model.put("positions", positions);
        ModelAndView mav = new ModelAndView(getViewName(), model);
        return mav;
    }

}
