package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.validation.constraints.NotNull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collections;

@ManagedBean(name = "taskBean")
@SessionScoped
@XmlRootElement(name = "task")
@XmlType(propOrder = {"str", "num", "result"})
public class TaskBean implements Serializable {

    @NotNull(message = "Строка не должна быть пустой")
    private String str;
    @NotNull(message = "Число не должно быть пустым")
    private int num;
    private String result;

    public TaskBean() {}

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String doMultiply() {
        setResult(String.join("", Collections.nCopies(num, str)));
        return "success";
    }

    public void download() throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        ec.responseReset();
        ec.setResponseContentType("text/xml; charset=UTF-8");
        ec.setResponseHeader("Content-Disposition", "attachment; filename=result.xml");

        OutputStream outputStream = ec.getResponseOutputStream();

        try {
            JAXBContext context = JAXBContext.newInstance(TaskBean.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(this, outputStream);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        outputStream.flush();
        outputStream.close();

        fc.responseComplete();
    }
}
