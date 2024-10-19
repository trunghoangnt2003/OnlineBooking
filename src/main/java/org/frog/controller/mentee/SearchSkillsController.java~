package org.frog.controller.mentee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.CategoryDAO;
import org.frog.DAO.LevelDAO;
import org.frog.DAO.Level_SkillDAO;
import org.frog.DAO.SkillsDAO;
import org.frog.model.Category;
import org.frog.model.Level;
import org.frog.model.Level_Skills;
import org.frog.model.Skill;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


@WebServlet("/Search_Skills")
public class SearchSkillsController extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String skill = request.getParameter("skill");
        String[] levels = request.getParameterValues("level");

        SkillsDAO skillsDAO = new SkillsDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        LevelDAO levelDAO = new LevelDAO();
        Level_SkillDAO level_skillDAO = new Level_SkillDAO();

        ArrayList<Level> list_level = levelDAO.getAll();
        ArrayList<Category> list_cate = categoryDAO.getAll();
        ArrayList<Skill> list_skill = skillsDAO.getAllActive();
        if(levels != null){
            ArrayList<String> levels_select = new ArrayList<>(Arrays.asList(levels));
            request.setAttribute("levels_select", levels_select);
        }


        ArrayList<Level_Skills> list_level_skill = level_skillDAO.getLevel_Skill(skill, levels);

        request.setAttribute("list_level_skill", list_level_skill);
        request.setAttribute("skill", skill);
        request.setAttribute("list_level", list_level);
        request.setAttribute("list_cate", list_cate);
        request.setAttribute("list_skill", list_skill);
        request.getRequestDispatcher("view/mentee/search_skills.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
