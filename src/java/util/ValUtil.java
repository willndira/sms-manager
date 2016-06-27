/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import service.UserData;


/**
 *
 * @author Norrey Osako
 */
public class ValUtil implements javax.faces.validator.Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String username = (String) value;
        Connection conn = DbUtil.getConnection();
        if (UserData.isUserNameExists(conn, username)) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("Username Already exists.");
            message.setDetail("Username Already exists");
            context.addMessage("Username", message);//userForm:
            throw new ValidatorException(message);
        }
        DbUtil.closeConnection(conn);

    }
}
