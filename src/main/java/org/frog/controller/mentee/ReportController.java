package org.frog.controller.mentee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.ReportDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;

import java.io.BufferedReader;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.frog.model.Mentee;
import org.frog.model.Mentor;
import org.frog.model.Report;

@WebServlet("/mentee/report")
public class ReportController extends AuthenticationServlet {
     public void doGet(HttpServletRequest request, HttpServletResponse response, Account account) throws ServletException, IOException {

     }

     public void doPost(HttpServletRequest request, HttpServletResponse response, Account account) throws ServletException, IOException {
          handlePost(request, response, account);
     }

     private void handlePost(HttpServletRequest request, HttpServletResponse response, Account account) throws ServletException, IOException {
          String mentee_id = account.getId();

          BufferedReader reader = request.getReader();
          StringBuilder jsonBuffer = new StringBuilder();
          String line;
          while ((line = reader.readLine()) != null) {
               jsonBuffer.append(line);
          }
          Gson gson = new Gson();
          JsonObject json = gson.fromJson(jsonBuffer.toString(), JsonObject.class);

          String mentor_id = json.get("mentor_id").getAsString();
          String reason = json.get("reason").getAsString();

          ReportDAO reportDAO = new ReportDAO();
          Report report = new Report();

          Account menter_acc = new Account();
          menter_acc.setId(mentor_id);
          Mentor mentor = new Mentor();
          mentor.setAccount(menter_acc);
          report.setMentor(mentor);

          Account mentee_acc = new Account();
          mentee_acc.setId(mentee_id);
          Mentee mentee = new Mentee();
          mentee.setAccount(mentee_acc);
          report.setMentee(mentee);

          report.setReason(reason);
          reportDAO.insert(report);


          response.setContentType("application/json");
          response.setCharacterEncoding("UTF-8");
          JsonObject jsonResponse = new JsonObject();
          jsonResponse.addProperty("status", "success");
          response.getWriter().write(gson.toJson(jsonResponse));

     }


}
