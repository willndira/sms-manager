
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<!-- RichFaces tag library declaration -->

<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<%@ taglib uri="http://richfaces.org/rich" prefix="rich" %>


<h:form>

    <rich:panel>
        <f:facet name="header">Edit SMS CREDITS</f:facet>
        <h:panelGrid   columns="3">

            <h:outputText styleClass="outputText" id="username" value="Username :"></h:outputText>
            <h:outputLabel styleClass="outputText" value="#{subAccounts.subAccount.username}"/>
            <rich:message for="username" />

            <h:outputText styleClass="outputText" value="Available SMS Credits : "></h:outputText>
            <h:outputLabel id="sub" styleClass="outputText" value="#{subAccounts.subAccount.maxTotal}"/>
            <rich:message for="sub" />


            <h:outputText styleClass="outputText"  value="Select Action"/>
            <h:selectOneMenu id="selectedAction"  styleClass="inputSelect" value="#{subAccounts.editAccount.selectedAction}">
                <f:selectItem itemValue="add" itemLabel="Add Credit"/>
                <f:selectItem itemValue="update" itemLabel="Deduct Credit"/>
            </h:selectOneMenu>
            <rich:message for="selectedAction" />

            <h:outputText styleClass="outputText" value="SMS Credit Value : "></h:outputText>
            <h:inputText 
                styleClass="inputText" 
                id="manageCredits" 
                requiredMessage="Cannot be blank" 
                required="true" 
                value="#{subAccounts.editAccount.maxTotal}">

            </h:inputText>
            <h:outputLabel styleClass="error" value="#{subAccounts.editAccount.errorCredits}" />


            <h:outputText styleClass="outputText" value=""></h:outputText>
            <h:inputHidden id="uID" value="#{subAccounts.editAccount.id}" />
            <h:commandButton value="Save" styleClass="btn" action="#{subAccounts.updateSMSCredits}" >
                <f:setPropertyActionListener target="#{subAccounts.selectedItem}" value="#{subAccounts.editAccount}"/>
            </h:commandButton>
            <rich:message for="uID" />

        </h:panelGrid>

    </rich:panel>  





</h:form>
