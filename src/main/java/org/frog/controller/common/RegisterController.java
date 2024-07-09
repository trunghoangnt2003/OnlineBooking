package org.frog.controller.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.frog.DAO.AccountDAO;
import org.frog.DAO.MenteeDAO;
import org.frog.DAO.MentorDAO;
import org.frog.DAO.WalletDAO;
import org.frog.model.Account;
import org.frog.model.Role;
import org.frog.model.Status;
import org.frog.model.Wallet;
import org.frog.utility.Email;
import org.frog.utility.SHA1;
import org.frog.utility.StatusEnum;
import org.frog.utility.validate;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@WebServlet("/register")
public class RegisterController extends HttpServlet {

    private void sendEmail(String link,String email,Account account){
        java.util.Date date = new Date();
        String tile = "Activate Your Account Now!";
        String content = "<h4>Dear "+email+"</h4><br>" +
                "<br>" +
                "Congratulations on creating your account with us! To activate your account and get started right away, simply click on the following link: " +
                "<a href = \""+link+"\" >[Activation]</a><br>" +
                "<b>User Name : "+account.getUserName()+"</b>"+
                "<br>" +
                "<b>Email : "+account.getEmail()+"</b>"+
                "<br>" +
                "<b>Address : "+account.getAddress()+"</b>"+
                "<br>" +
                "<b>Gender : "+(account.getGender() == 1?"Male":"Female")+"</b> <br>"+
                "<b>Role : "+(account.getRole().getId()==1?"Mentee":"Mentor")+"</b>"+
                "<br>" +
                "Once you click on the link, you will be directed to the activation page where you can provide any necessary information and complete the activation process.<br>" +
                "<br>" +
                "If you have any questions or encounter any difficulties, please feel free to contact our support team at [<i>0942532003</i>]. We're here to assist you.<br>" +
                "<br>" +
                "Thank you for choosing our platform. We look forward to serving you!<br>" +
                "<br>" +
                "Best regards<br>"
                +date;
        Email send = new Email();
        send.sendEmail(tile, content, email);

    }
    private String generateID(String prefix) {
        String id;
        do {
            // Tạo một số ngẫu nhiên 6 chữ số
            Random random = new Random();
            int randomNum = 100000 + random.nextInt(900000);
            id = prefix + randomNum;
        } while (isIDExists(id));
        return id;
    }

    private String createMentorID() {
        return generateID("MO");
    }

    private String createMenteeID() {
        return generateID("ME");
    }
    private boolean isIDExists(String id) {
        AccountDAO accountDAO = new AccountDAO();
        return !accountDAO.checkExitsId(id);
    }
    public void doGet(HttpServletRequest req, HttpServletResponse res){
        try {
            req.getRequestDispatcher("view/public/signup.jsp").forward(req,res);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String password = req.getParameter("password");
        String email = req.getParameter("mail");
        String name = req.getParameter("name");
        String userName = req.getParameter("userName");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String roleString = req.getParameter("role");
        // if role == null . default = 1
        roleString = roleString!=null?roleString:"1";
        int role = Integer.parseInt(roleString);
        String genderString = req.getParameter("gender");
        // if gender == null . default = 1
        genderString = genderString!=null?genderString:"1";
        int gender = Integer.parseInt(genderString);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date dob = null;
        try {
            Date date = dateFormat.parse(req.getParameter("dob"));
            dob = new java.sql.Date(date.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String warningRegister;
        password = validate.checkPassword(password);
        email = validate.checkMail(email);
        if(password == null || email == null){
            warningRegister = "Error validating";
            req.setAttribute("warningRegister", warningRegister);
            req.getRequestDispatcher("view/public/signup.jsp").forward(req,res);
        }
        password = SHA1.toSHA1(password);
        AccountDAO accountDAO = new AccountDAO();
        Account account = accountDAO.getAccountByEmail(email);
        System.out.println(account);
        if(account!=null){
            warningRegister = "Email already exists";
            req.setAttribute("warningRegister", warningRegister);
            req.getRequestDispatcher("view/public/signup.jsp").forward(req,res);
        }
        else {
            Account newAccount = accountDAO.getAccountByUserName(userName);
            if(newAccount!=null){
                warningRegister = "User already exists";
                req.setAttribute("warningRegister", warningRegister);
                req.getRequestDispatcher("view/public/signup.jsp").forward(req,res);
            }else {
                String id;
                if(role == 1){
                    id = createMenteeID();
                }else {
                    id = createMentorID();
                }
                Wallet wallet = new Wallet(id,0,0);
                WalletDAO walletDAO = new WalletDAO();
                walletDAO.insert(wallet);
                Account accountRegister = new Account(id,null,name,userName,password,dob,phone,email,address,gender,wallet,new Status(StatusEnum.INACTIVE,""),new Role(role,""));

                accountDAO.insertUser(accountRegister);
                if(role == 1){
                    MenteeDAO menteeDAO = new MenteeDAO();
                    menteeDAO.insert(accountRegister);
                }else {
                    MentorDAO mentorDAO = new MentorDAO();
                    mentorDAO.register(accountRegister);
                    //insert mentor schedule
                    mentorDAO.createScheduleMaster(accountRegister.getId());

                }
                String done;
                done = "Please check your mail to activate your account.";

                String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
                        + req.getContextPath() + "/activate?token=" + SHA1.toSHA1(email+email);
                sendEmail(url,email,accountRegister);
                req.setAttribute("done", done);
                req.getRequestDispatcher("view/public/signup.jsp").forward(req,res);
            }


        }

    }

}
