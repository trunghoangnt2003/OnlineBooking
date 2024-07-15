package org.frog.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.AccountDAO;
import org.frog.DAO.MentorDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Role;
import org.frog.model.Status;
import org.frog.utility.SHA1;
import org.frog.utility.StatusEnum;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

@WebServlet("/admin/manageManager")
public class ManageManager extends AuthenticationServlet {
    private boolean isIDExists(String id) {
        AccountDAO accountDAO = new AccountDAO();
        return !accountDAO.checkExitsId(id);
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

    private String createManagerID() {
        return generateID("MANAGER");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {

        // Read the request body
        String requestBody = "";
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = req.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            requestBody = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle error reading the request body
            return;
        }

        // Parse the JSON string
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(requestBody);
        } catch (JSONException e) {
            e.printStackTrace();
            // Handle error parsing JSON
            return;
        }

        // Extract the fullName and userName values
        String fullName;
        String userName;
        try {
            fullName = jsonObject.getString("fullName");
            userName = jsonObject.getString("userName");
        } catch (JSONException e) {
            e.printStackTrace();
            // Handle error accessing keys
            return;
        }
            System.out.println("userName:"+userName);
            AccountDAO accountDAO = new AccountDAO();
            if(accountDAO.getAccountByUserName(userName) == null) {
                System.out.println("OK\n");
                String password = "123456789";
                String id = createManagerID();
                Account account1 = new Account();
                account1.setUserName(userName);
                account1.setRole(new Role(4,"manager"));
                account1.setId(id);
                account1.setPassword(SHA1.toSHA1(password));
                account1.setName(fullName);
                account1.setStatus(new Status(StatusEnum.INACTIVE, "Inactive"));
                account1.setEmail(userName+"@Frog.edu.vn");
                int check = accountDAO.insertManager(account1);
                if(check!=0)resp.setStatus(HttpServletResponse.SC_OK);
                else resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            }


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String page_raw = request.getParameter("page");
        String name = request.getParameter("name");
        if(name == null || name.isEmpty()) name = "";
        int page = 1;
        if (page_raw != null) {
            page = Integer.parseInt(page_raw);
        }
        AccountDAO accountDAO = new AccountDAO();
        ArrayList<Account> list = accountDAO.selectAllManager(page);
        int totalManager = accountDAO.totalManager();
        int end_page = totalManager /4 ;
        if ( totalManager % 4 != 0) {
            end_page++;
        }
        request.setAttribute("page",page);
        request.setAttribute("totalMentor",totalManager);
        request.setAttribute("name",name);
        request.setAttribute("end_page",end_page);
        request.setAttribute("list",list);
        request.getRequestDispatcher("../view/admin/manageManager.jsp").forward(request,resp);
    }
}
