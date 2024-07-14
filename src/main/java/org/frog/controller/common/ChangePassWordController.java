package org.frog.controller.common;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.AccountDAO;
import org.frog.model.Account;
import org.frog.utility.SHA1;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 *
 * @author trung
 */
@WebServlet("/change-pass")
public class ChangePassWordController extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String passWord = req.getParameter("password1");
        AccountDAO accountDAO = new AccountDAO();
        ArrayList<Account> accounts = accountDAO.selectAll();
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = formatter.format(new Date());
        String account_email = "";
        String mess;
        boolean check = true;
        for(Account account:accounts){
            if(SHA1.toSHA1(account.getEmail()+date).equals(email)){
                account_email = account.getEmail();
                check=false;
                break;
            }
        }
        if(check) {
            mess = "Sorry, the link you provided has expired";
            req.setAttribute("mess",mess);
        }else{

            mess = "Change Pass : "+account_email;
            req.setAttribute("mess",mess);
            accountDAO.updatePassWord(account_email, SHA1.toSHA1(passWord));
        }
        req.getRequestDispatcher("view/public/changePassDone.jsp").forward(req, resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = (req.getParameter("token"));
        AccountDAO accountDAO = new AccountDAO();
        ArrayList<Account> accounts = accountDAO.selectAll();
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = formatter.format(new Date());
        String account_email = "";
        boolean check = true;
        for (Account account : accounts) {
            System.out.println("Token real : " +email );
            System.out.println("Token: " + (SHA1.toSHA1(account.getEmail() + date)));
            System.out.println("check : " + (SHA1.toSHA1(account.getEmail() + date)).equals(email));
            if ((SHA1.toSHA1(account.getEmail() + date)).equals(email)) {
                System.out.println("Email 1 : " + account.getEmail());
                account_email = account.getEmail();
                break;
            }
        }
        if (account_email.isEmpty()) {
            resp.getWriter().println("Loi");
        } else {
            req.setAttribute("email", account_email);
            req.getRequestDispatcher("view/public/changePass.jsp").forward(req, resp);

        }
    }

}
