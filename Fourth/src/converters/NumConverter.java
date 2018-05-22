package converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.validator.ValidatorException;

@FacesConverter(value = "numConverter", forClass = Integer.class)
public class NumConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        try {
            int num = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            FacesMessage msg = new FacesMessage("Неверный формат числа",
                    "Необходимо ввести число");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        return Integer.parseInt(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        return o.toString();
    }
}
