package org.frog.controller.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.frog.DAO.AccountDAO;
import org.frog.model.Account;

import java.io.IOException;


/**
 *
 * @author trung
 */
public abstract class AuthenticationServlet extends HttpServlet {

    private Account getAuthentication(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Account user = (Account) session.getAttribute("account");
        if (user == null) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                String userName = null;
                String passWord = null;
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("userName")) {
                        userName = cookie.getValue();
                    }
                    if (cookie.getName().equals("passWord")) {
                        passWord = cookie.getValue();
                    }
                    if (userName != null && passWord != null) {
                        break;
                    }
                }
                if (userName == null || passWord == null) {
                    return null;
                } else {
                    Account test;
                    AccountDAO accountDAO = new AccountDAO();
                    test = accountDAO.getLogin(userName,passWord);
                    if (test != null) {
                        session.setAttribute("account", test);
                    }
                    return test;
                }
            }
        }
        return user;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account = getAuthentication(req);
        if (account != null) {
            doPost(req, resp, account);
        } else {

            String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
                    + req.getContextPath();

            resp.sendRedirect(url + "/login");
        }
    }

    protected abstract void doPost(HttpServletRequest req, HttpServletResponse resp, Account account)
            throws ServletException, IOException;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account = getAuthentication(req);
        if (account != null) {
            doGet(req, resp, account);
        } else {
            String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
                    + req.getContextPath();

            resp.sendRedirect(url + "/login");
        }

    }

    protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp, Account account)
            throws ServletException, IOException;

}
