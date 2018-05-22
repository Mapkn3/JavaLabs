package servlet;

import dto.TaskDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@WebServlet("/result.html")
public class TaskServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TaskDto task = (TaskDto) req.getSession().getAttribute("task");
        if (task != null) {
            String str = task.getStr();
            int num = task.getNum();
            task.setResult(String.join("", Collections.nCopies(num, str)));
            resp.sendRedirect(String.format("%s%s", req.getContextPath(), "pages/jsp/result.jsp"));
        }
    }
}
