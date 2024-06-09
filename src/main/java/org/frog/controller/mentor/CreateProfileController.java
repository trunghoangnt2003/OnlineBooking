package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.frog.DAO.*;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)

@WebServlet("/mentor/create_profile")
public class CreateProfileController extends AuthenticationServlet {

    private static final String UPLOAD_DIR = "/img/image_user";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String edu = req.getParameter("edu");
        String exp = req.getParameter("exp");
        String raw_price = req.getParameter("price");
        String detail = req.getParameter("detail");
        int price = 0;

        String[] levelSkills = req.getParameterValues("level_skill");
        for(String s: levelSkills) {
            Level_SkillDAO level_skillDAO = new Level_SkillDAO();
            level_skillDAO.insertLevelSkill(account.getId(), Integer.parseInt(s));
        }

        MentorDAO mentorDAO = new MentorDAO();
        Mentor mentor = new Mentor();

        if(raw_price != null) {
            try {
                price = Integer.parseInt(raw_price);
            }catch (NumberFormatException e) {
                resp.sendRedirect("/Frog/mentor/create_profile");
                return;
            }
        }

        Part filePart = req.getPart("photo");
        String avatar = null;

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIR;

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

        if(avatar != null) {
            account.setAvatar(avatar);
            mentorDAO.updateImage(account);
        }

        mentor.setEducation(edu);
        mentor.setExperience(exp);
        mentor.setAccount(account);
        mentor.setPrice(price);
        mentor.setProfileDetail(detail);
        mentorDAO.update(mentor);
        resp.sendRedirect("/Frog/mentor/profile?mentorid=" + account.getId());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        try {
            AccountDAO accountDAO = new AccountDAO();
            account = accountDAO.getAccountById(account.getId());
            LevelDAO levelDAO = new LevelDAO();
            ArrayList<Level> levels = levelDAO.getAll();
            req.setAttribute("levels", levels);
            Level_SkillDAO level_skillDAO = new Level_SkillDAO();
            ArrayList<Level_Skills> level_skills = level_skillDAO.getAllLevel_SkillList();

            req.setAttribute("levels", levels);
            req.setAttribute("level_skills", level_skills);
            req.setAttribute("account", account);
            req.getRequestDispatcher("../view/mentor/create_profile.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
