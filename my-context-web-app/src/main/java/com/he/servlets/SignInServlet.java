package com.he.servlets;

import com.he.context.ApplicationContext;
import com.he.dto.UserDto;
import com.he.presenters.Presenter;
import com.he.presenters.PresenterFactory;
import com.he.services.SignInService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/sign_in")
public class SignInServlet extends HttpServlet {

    private SignInService signInService;
    private PresenterFactory presenterFactory;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ApplicationContext context = (ApplicationContext) config.getServletContext().getAttribute("appContext");
        this.signInService = context.getComponent(SignInService.class, "signInService");
        this.presenterFactory = context.getAttribute("presenterFactory");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Presenter presenter = presenterFactory.createPresenter("sign_in");
        presenter.render(resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserDto userDto;
        try {
            userDto = signInService.signIn(login, password);
        } catch (IllegalArgumentException e) {
            resp.sendRedirect(req.getContextPath() + "/sign_in" + "?status=incorrect");
            return;
        }
        req.getSession(true).setAttribute("user_id", userDto.getId());
        req.getSession(true).setAttribute("role", userDto.getRole());
        req.getSession(true).setAttribute("user_login", userDto.getLogin());
        resp.sendRedirect(req.getContextPath() + "/products");
    }


}
