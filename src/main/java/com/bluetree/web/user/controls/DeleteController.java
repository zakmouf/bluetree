package com.bluetree.web.user.controls;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.bluetree.domain.Portfolio;
import com.bluetree.domain.User;
import com.bluetree.manager.PortfolioManager;
import com.bluetree.util.MessageUtil;

public class DeleteController extends AbstractController {

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
        logger.info(MessageUtil.msg("delete portfolio <{0}>", portfolio));
        portfolioManager.deletePortfolio(portfolio);
        return new ModelAndView(getViewName());
    }

}
