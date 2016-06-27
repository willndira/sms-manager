<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<!-- RichFaces tag library declaration -->

<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<%@ taglib uri="http://richfaces.org/rich" prefix="rich" %>

<!----------------------------------------------------------------------------------------------------------------------->

<rich:panel styleClass="panelStyle">
    <f:facet name="header"><h:outputText>Sub-Account Details</h:outputText></f:facet>
    <h:panelGrid columns="2">
        <h:outputText styleClass="outputNormal"  value="Username:"/>
        <h:outputText styleClass="outputNormal" value="#{subAccounts.subAccount.username}"/>

        <h:outputText styleClass="outputNormal" value="Password:"/>
        <h:outputText styleClass="outputNormal" value="#{subAccounts.subAccount.password}"/>

        <h:outputText styleClass="outputNormal" value="SmsCredits:"/>
        <h:outputText styleClass="outputNormal" value="#{subAccounts.subAccount.maxTotal}"/>

        <h:outputText styleClass="outputNormal" value="Contact:"/>
        <h:outputText styleClass="outputNormal" value="#{subAccounts.subAccount.contactNumber}"/>

    </h:panelGrid>
</rich:panel>
