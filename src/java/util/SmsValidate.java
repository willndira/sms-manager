/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import service.UserData;


/**
 *
 * @author Norrey Osako
 */
public class SmsValidate implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String desc = (String) value;

        // Obtain the component and submitted value of the confirm password component.
        UIInput confirmComponent = (UIInput) component.getAttributes().get("manageCredits");
        String manageCredits = (String) confirmComponent.getSubmittedValue();

        UIInput uIDComponent = (UIInput) component.getAttributes().get("uID");
        String subAccID = ((String) uIDComponent.getSubmittedValue());

        int sAId = 0, mC = 0;
        try {
            sAId = Integer.parseInt(subAccID);
            mC = Integer.parseInt(manageCredits);
        } catch (NumberFormatException e) {
            System.err.println("SmSValidate" + e.getMessage());
        }

        // Cast the value of the entered password to String.
        int d = 0;
        if (desc.equalsIgnoreCase("add")) {
            d = 1;
        } else if (desc.equalsIgnoreCase("update")) {
            d = 2;
        } else {
            d = 2;
        }
        Connection conn = DbUtil.getConnection();
        UserData userData = new UserData();
        try {
//            if (!userData.isSmsCreditPossible(conn, sAId, mC, d)) {
//                throw new ValidatorException(new FacesMessage("Sms entered not valid."));
//            }
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SmsValidate.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        // Compare the password with the confirm password.
    }

}
