package org.frog.controller.mentee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.frog.DAO.MenteeDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Mentee;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Date;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
@WebServlet("/mentee/update")
public class UpdateMenteeController extends AuthenticationServlet {

    private static final String UPLOAD_DIR = "img\\image_user"; // Removed leading slash for relative path

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        MenteeDAO menteeDAO = new MenteeDAO();
        Mentee mentee = menteeDAO.getMenteeById(account.getId());
        req.setAttribute("mentee", mentee);
        req.getRequestDispatcher("../view/mentee/profile/update.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String dob = req.getParameter("dob");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String username = req.getParameter("username");
        String gender = req.getParameter("gender");

        Part filePart = req.getPart("photo");
        String avatar = null;
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;

            File uploadDirFile = new File(uploadPath);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }

            String filePath = Paths.get(uploadPath, fileName).toString();
            try {
                filePart.write(filePath);
                avatar = UPLOAD_DIR + "/" + fileName;
            } catch (IOException e) {
                e.printStackTrace();
                throw new ServletException("File upload failed!", e);
            }
        }

        // Get existing mentee
        MenteeDAO menteeDAO = new MenteeDAO();
        Mentee existingMentee = menteeDAO.getMenteeById(id);
        if (existingMentee == null) {
            throw new ServletException("Mentee not found!");
        }

        // Update account information
        Account a = existingMentee.getAccount();
        a.setName(name);
        a.setDob(Date.valueOf(dob));
        a.setUserName(username);
        a.setPhone(phone);
        a.setAddress(address);
        a.setGender(Integer.parseInt(gender));

        // Only update avatar if a new one was uploaded
        if (avatar != null) {
            a.setAvatar(avatar);
        }

        Mentee mentee = new Mentee(a);
        menteeDAO.updateMentee(mentee);
        account.setAvatar(avatar);
        HttpSession session = req.getSession();
        session.setAttribute("account", account);
        resp.sendRedirect("profile?menteeid=" + id);

    }
}