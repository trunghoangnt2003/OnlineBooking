package org.frog.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.frog.DAO.CategoryDAO;
import org.frog.DAO.LevelDAO;
import org.frog.DAO.Level_SkillDAO;
import org.frog.DAO.SkillsDAO;
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
@WebServlet("/admin/skill")
public class ManageSkill extends AuthenticationServlet {
    private static final String UPLOAD_DIR = "img\\image_skill";

    private void updateStatusSkill(HttpServletRequest req, HttpServletResponse resp)  {
        boolean isChecked = Boolean.parseBoolean(req.getParameter("isChecked"));
        String value = req.getParameter("value");
        int id = Integer.parseInt(value);
        Level_SkillDAO skillDAO = new Level_SkillDAO();
        // Xử lý dữ liệu
        if (isChecked) {
            skillDAO.updateStatus(id,7);
        } else {
            skillDAO.updateStatus(id,8);
        }
    }
    private void addNewSkill(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        Part imagePart = req.getPart("img");

        String img = null;
        if (imagePart != null && imagePart.getSize() > 0) {
            String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;

            File uploadDirFile = new File(uploadPath);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }

            String filePath = Paths.get(uploadPath, fileName).toString();
            try {
                imagePart.write(filePath);
                img = UPLOAD_DIR + "/" + fileName;
            } catch (IOException e) {
                e.printStackTrace();
                throw new ServletException("File upload failed!", e);
            }
        }

        String id_String = req.getParameter("skillCategory");
        int idCategory = Integer.parseInt(id_String);
        SkillsDAO skillsDAO = new SkillsDAO();
        Skill skills = skillsDAO.getByName(name);
        if (skills != null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }else {
            skillsDAO.insertSkill(name, idCategory, img);
            Skill skill = skillsDAO.getByName(name);
            int idSkill = skill.getId();
            LevelDAO levelDAO = new LevelDAO();
            ArrayList<Level> levels = levelDAO.getAll();
            Level_SkillDAO levelSkillDAO = new Level_SkillDAO();
            for (Level level : levels) {
                String des = req.getParameter("skillDescription_" + level.getId());
                levelSkillDAO.addNewLevelSkill(idSkill, level.getId(), des);
            }
            resp.setStatus(HttpServletResponse.SC_OK);
        }


    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("update")){
            updateStatusSkill(req, resp);
        }else if(action.equals("add")){
            addNewSkill(req, resp);

        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String page_raw = req.getParameter("page");
        String cate_raw = req.getParameter("cate");
        int cate = 0;
        if (cate_raw != null && !cate_raw.equalsIgnoreCase("all")){
            cate = Integer.parseInt(cate_raw);
        }
        int page = 1;
        if (page_raw != null) {
            page = Integer.parseInt(page_raw);
        }
        Level_SkillDAO levelSkillDAO = new Level_SkillDAO();
        ArrayList<Level_Skills> levelSkills =  levelSkillDAO.getLevel_SkillListPagitaion(page,cate);
        int total = 0;
        if(cate!=0){
            for(Level_Skills level_skills : levelSkillDAO.getAllLevel_SkillList()){
                if(level_skills.getSkill().getCategory().getId() == cate){
                    total ++;
                }
            }
        }else {
            total = levelSkillDAO.getAllLevel_SkillList().size();
        }
        int end_page = total /10 ;
        if ( total % 10 != 0) {
            end_page++;
        }
        req.setAttribute("cate",cate);
        LevelDAO levelDAO = new LevelDAO();
        ArrayList<Level> levels = levelDAO.getAll();
        CategoryDAO categoryDAO = new CategoryDAO();
        ArrayList<Category> categories = categoryDAO.getAll();
        req.setAttribute("levels",levels);
        req.setAttribute("categories",categories);
        req.setAttribute("total",total);
        req.setAttribute("end_page",end_page);
        req.setAttribute("page",page);
        req.setAttribute("list", levelSkills);
        System.out.println(levelSkills.size());
        req.getRequestDispatcher("../view/admin/viewSkill.jsp").forward(req, resp);
    }
}
