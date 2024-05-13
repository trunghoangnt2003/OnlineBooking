package org.frog.controller.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
@WebServlet("/login")
public class LoginController extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res){
        try {
            req.getRequestDispatcher("view/public/login.jsp").forward(req,res);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
