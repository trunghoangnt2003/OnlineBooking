package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;

import java.io.IOException;
@WebServlet("/mentor/schedule/deleteSession")
public class DeleteSessionScheduleController extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        req.getSession().removeAttribute("AddSlotError");
        req.getSession().removeAttribute("DeleteSlotError");
        req.getSession().removeAttribute("numberUpdateSuccess");
        resp.sendRedirect("/Frog/mentor/schedule");
    }
}
