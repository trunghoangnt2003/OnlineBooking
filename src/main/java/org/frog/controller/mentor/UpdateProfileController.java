package org.frog.controller.mentor;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.frog.DAO.AccountDAO;
import org.frog.DAO.LevelDAO;
import org.frog.DAO.Level_SkillDAO;
import org.frog.DAO.MentorDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Level;
import org.frog.model.Level_Skills;
import org.frog.model.Mentor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)

@WebServlet("/mentor/update_profile")
public class UpdateProfileController extends AuthenticationServlet {

    private static final String UPLOAD_DIR = "/img/image_user";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        String dob = req.getParameter("dob");
        String gender = req.getParameter("gender");

        String profile_detail = req.getParameter("detail");
        String education = req.getParameter("edu");
        String experience = req.getParameter("exp");
        String raw_price = req.getParameter("price");

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

        AccountDAO accountDAO = new AccountDAO();
        account.setName(name);
        account.setAddress(address);
        account.setPhone(phone);
        account.setGender(Integer.parseInt(gender));
        account.setDob(Date.valueOf(dob));
        accountDAO.updateAccount(account);

        MentorDAO mentorDAO = new MentorDAO();
        Mentor mentor = new Mentor();
        mentor.setProfileDetail(profile_detail);
        mentor.setEducation(education);
        mentor.setExperience(experience);
        mentor.setPrice(Integer.parseInt(raw_price));
        mentorDAO.updateMentor(mentor, account.getId());

        String[] levelSkills = req.getParameterValues("level_skill");
        if (levelSkills != null) {
            mentorDAO.deleteSkillMentor(account.getId());
            for(String s: levelSkills) {
                Level_SkillDAO level_skillDAO = new Level_SkillDAO();
                level_skillDAO.insertLevelSkill(account.getId(), Integer.parseInt(s));
            }
        } else {
            mentorDAO.deleteSkillMentor(account.getId());
        }
        if(avatar != null) {
            account.setAvatar(avatar);
            mentorDAO.updateImage(account);
        }

        resp.sendRedirect("profile?mentorid=" + account.getId());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        MentorDAO mentorDAO = new MentorDAO();
        Mentor mentor = mentorDAO.getMentorById(account.getId());
        LevelDAO levelDAO = new LevelDAO();
        ArrayList<Level> levels = levelDAO.getAll();
        Level_SkillDAO level_skillDAO = new Level_SkillDAO();
        ArrayList<Level_Skills> level_skills = level_skillDAO.getAllLevel_SkillList();
        ArrayList<Level_Skills> mentorSkill = mentorDAO.getSkillMentor(account.getId());

        req.setAttribute("mentorSkill", mentorSkill);
        req.setAttribute("levels", levels);
        req.setAttribute("level_skills", level_skills);
        req.setAttribute("mentor", mentor);
        req.getRequestDispatcher("../view/mentor/update_profile.jsp").forward(req, resp);
    }
}
