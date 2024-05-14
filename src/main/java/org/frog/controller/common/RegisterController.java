package org.frog.controller.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.frog.model.Account;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res){
        try {
            req.getRequestDispatcher("view/public/register.jsp").forward(req,res);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String passWord = req.getParameter("passWord");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        req.getRequestDispatcher("view/public/login.jsp").forward(req,res);

    }

}
