package org.frog.controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.CategoryDAO;
import org.frog.DAO.LevelDAO;
import org.frog.DAO.Level_SkillDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Category;
import org.frog.model.Level;
import org.frog.model.Level_Skills;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/admin/skill")
public class ManageSkill extends AuthenticationServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        boolean isChecked = Boolean.parseBoolean(req.getParameter("isChecked"));
        String value = req.getParameter("value");
        int id = Integer.parseInt(value);
        Level_SkillDAO skillDAO = new Level_SkillDAO();
        // Xử lý dữ liệu
        if (isChecked) {
            System.out.println("Hello 1 "+id);
            skillDAO.updateStatus(id,7);
        } else {
            System.out.println("Hello 2 "+id);
            skillDAO.updateStatus(id,8);
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        String page_raw = req.getParameter("page");
        int page = 1;
        if (page_raw != null) {
            page = Integer.parseInt(page_raw);
        }
        Level_SkillDAO levelSkillDAO = new Level_SkillDAO();
        ArrayList<Level_Skills> levelSkills =  levelSkillDAO.getLevel_SkillListPagitaion(page);
        int total = levelSkillDAO.getLevel_SkillList().size();
        int end_page = total /10 ;
        if ( total % 10 != 0) {
            end_page++;
        }
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
