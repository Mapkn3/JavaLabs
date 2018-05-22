package servlet;

import dto.TaskDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;

@WebServlet("/result.xml")
public class ResultXmlServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/xml; charset=UTF-8");
        TaskDto task = (TaskDto) req.getSession().getAttribute("task");
        try {
            JAXBContext context = JAXBContext.newInstance(TaskDto.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(task, resp.getOutputStream());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
