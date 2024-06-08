package org.frog.controller.google;


import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.frog.DAO.AccountDAO;
import org.frog.model.Account;
import org.frog.model.GoogleUser;
import org.frog.utility.StatusEnum;

@WebServlet("/google")
public class LoginGoogleController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public LoginGoogleController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            RequestDispatcher dis = request.getRequestDispatcher("login");
            dis.forward(request, response);
        } else {
            String accessToken = GoogleUnits.getToken(code);
            GoogleUser googleUser = GoogleUnits.getUserInfo(accessToken);
            String email = googleUser.getEmail();
            Account user = null;
            AccountDAO accountDAO = new AccountDAO();
            user = accountDAO.getLoginGoogle(email);

            if (user == null) {
                //register if email don't have in database
                String url = "view/public/signin.jsp";
                String warningLogin = "an account that does not exist in the system";
                request.setAttribute("warningLogin", warningLogin);
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                if (user.getStatus().getId() == StatusEnum.INACTIVE) {
                    String url = "view/public/signin.jsp";
                    String warningLogin = "Account need to be activated";
                    request.setAttribute("warningLogin", warningLogin);
                    request.getRequestDispatcher(url).forward(request, response);
                }
                if (user.getStatus().getId() == StatusEnum.BAN) {
                    String url = "view/public/signin.jsp";
                    String warningLogin = "Your account has been banned, please contact the system to get support.";
                    request.setAttribute("warningLogin", warningLogin);
                    request.getRequestDispatcher(url).forward(request, response);
                }
                HttpSession session = request.getSession();
                session.setAttribute("account", user);
                Cookie c_username = new Cookie("email", email);
                c_username.setMaxAge(3600 * 24 * 7);

                Cookie c_password = new Cookie("passWord", user.getPassword());
                c_password.setMaxAge(3600 * 24 * 7);

                response.addCookie(c_username);
                response.addCookie(c_password);
                request.getRequestDispatcher("home").forward(request, response);
                response.sendRedirect("home");
//                response.getWriter().println(user.getUserName()+" "+user.getPassword());
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
