package dto;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "task")
@XmlType(propOrder = {"str", "num", "result"})
public class TaskDto {
    private String str;
    private int num;
    private String result;

    public TaskDto(){}

    public TaskDto(String str, int num) {
        this.str = str;
        this.num = num;
    }

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
}
