
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<!-- RichFaces tag library declaration -->

<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<%@ taglib uri="http://richfaces.org/rich" prefix="rich" %>


<h:form>

    <rich:panel styleClass="panelStyle">
        <f:facet name="header"><h:outputText value="Change Sub-Account Password"></h:outputText></f:facet>
        <h:panelGrid   columns="3">

            <h:outputText styleClass="outputText" id="username" value="Username : "></h:outputText>
            <h:outputLabel styleClass="inputText" value="#{subAccounts.editAccount.username}"/>
            <rich:message for="username"/>


            <h:outputText styleClass="outputText" id="password" value="Password: "></h:outputText>
            <h:inputSecret styleClass="inputText" value="#{subAccounts.editAccount.password}"/>
            <rich:message for="password"/>

            <h:outputText styleClass="outputText"></h:outputText>
            <h:inputHidden id="uId" value="#{subAccounts.editAccount.id}" />
            <h:commandButton value="Save" styleClass="btn" action="#{subAccounts.updateSubAcc}" >
                <f:setPropertyActionListener target="#{subAccounts.selectedItem}" value="#{subAccounts.editAccount}" />
            </h:commandButton>
            <rich:message for="uId"/>

        </h:panelGrid>

    </rich:panel>  





</h:form>
