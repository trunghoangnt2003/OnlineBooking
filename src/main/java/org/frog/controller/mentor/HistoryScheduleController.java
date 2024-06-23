package org.frog.controller.mentor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.frog.DAO.Booking_ScheduleDAO;
import org.frog.controller.auth.AuthenticationServlet;
import org.frog.model.Account;
import org.frog.model.Booking;
import org.frog.utility.DateTimeHelper;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/mentor/schedule/history")
public class HistoryScheduleController extends AuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        // Đọc dữ liệu JSON từ yêu cầu
//        StringBuilder sb = new StringBuilder();
//        BufferedReader reader = req.getReader();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            sb.append(line);
//        }
//        String requestData = sb.toString();
//
//        Gson gson = new Gson();
//        JsonObject jsonObject = gson.fromJson(requestData, JsonObject.class);
//        String filterValue = jsonObject.get("filter").getAsString();
//
//        ArrayList<Booking> bookingsHistory = new ArrayList<>();
//        Booking_ScheduleDAO bs = new Booking_ScheduleDAO();
//
//        if (filterValue.equals("money")) {
//            bookingsHistory = bs.getBookingsHistoryByMoney(account.getId());
//        }
//
//        req.setAttribute("bookingsHistory", bookingsHistory);
//        req.getRequestDispatcher("/view/mentor/schedule/ViewHistoryBooking.jsp").forward(req, resp);

        }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        int ITEMS_PER_PAGE = 20;
        try{
            String opt = req.getParameter("opt");
            String filter = req.getParameter("filter");
            ArrayList<Booking> bookingsHistory = new ArrayList<>();
            ArrayList<Booking> bookingsHistoryList = new ArrayList<>();
            Booking_ScheduleDAO bs = new Booking_ScheduleDAO();
            if(opt == null && filter == null){
                bookingsHistoryList = bs.getBookingsHistory(account.getId());
            }
            if(opt != null && filter == null){
                if(opt.equals("1")){
                    bookingsHistoryList=bs.filterBookingsHistoryByStatus(account.getId(),2);
                }
                if(opt.equals("2")){
                    bookingsHistoryList=bs.filterBookingsHistoryByStatus(account.getId(),13);
                }
            }
            if(opt != null && filter != null){
                String start = req.getParameter("StartDate");
                String end = req.getParameter("EndDate");
                if(opt.equals("1")){
                    bookingsHistoryList=bs.filterBookingsHistoryByStatusAndDate(account.getId(),2, DateTimeHelper.convertStringToDateByDay(start), DateTimeHelper.convertStringToDateByDay(end));
                }
                if(opt.equals("2")){
                    bookingsHistoryList=bs.filterBookingsHistoryByStatusAndDate(account.getId(),13, DateTimeHelper.convertStringToDateByDay(start), DateTimeHelper.convertStringToDateByDay(end));
                }
            }
            if(filter != null && opt == null){
                String start = req.getParameter("StartDate");
                String end = req.getParameter("EndDate");
                bookingsHistoryList = bs.filterBookingsHistoryByDate(account.getId(),DateTimeHelper.convertStringToDateByDay(start), DateTimeHelper.convertStringToDateByDay(end));
            }
            // phan trang
            int currentPage = 1;
            String page = req.getParameter("page");
            if (page != null) {
                currentPage = Integer.parseInt(page);
            }

            int total = bookingsHistoryList.size();
            int totalPages = (int) Math.ceil(total / (double) ITEMS_PER_PAGE);
            int startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
            int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, total);
            for(int i = startIndex; i < endIndex ; i++) {
                bookingsHistory.add(bookingsHistoryList.get(i));
            }

            //thong tin cho statistic
            int totalBookings = bookingsHistoryList.size();
            int numberOfInvited = bs.getNumberOfInvited(account.getId());
            int numberOfAccepted=bs.getNumberOfAccepted(account.getId());
            int numberOfRejected = bs.getNumberOfRejected(account.getId());
            int numberOfWaiting = bs.getNumberOfWaiting(account.getId());
            float rating = bs.getRating(account.getId());
            req.setAttribute("totalBookings",totalBookings);
            req.setAttribute("numberOfInvited",numberOfInvited);
            req.setAttribute("numberOfAccepted",numberOfAccepted);
            req.setAttribute("numberOfRejected",numberOfRejected);
            req.setAttribute("numberOfWaiting",numberOfWaiting);
            req.setAttribute("bookingsHistory",bookingsHistory);
            req.setAttribute("rating",rating);
            req.setAttribute("count", 1);
            String currentUrl = req.getRequestURL().toString() + "?" + req.getQueryString();
            req.setAttribute("currentUrl", currentUrl);

            req.getRequestDispatcher("/view/mentor/schedule/ViewHistoryBooking.jsp").forward(req, resp);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
