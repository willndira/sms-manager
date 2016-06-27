<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<!-- RichFaces tag library declaration -->

<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<%@ taglib uri="http://richfaces.org/rich" prefix="rich" %>

<!----------------------------------------------------------------------------------------------------------------------->


<h:form id="form">
    <rich:dataTable 
        width="100%"
        id="accounts"
        rows="10"
        rowClasses="odd,even"
        styleClass="panelStyle"
        var="accounts" value="#{subAccounts.subAccounts}">
        <f:facet name="header">
            <h3><h:outputText value="Sub-Accounts for: #{subAccounts.mainAdmin.username}"/></h3>   
        </f:facet>
        <rich:column sortBy="#{accounts.username}"> 
            <f:facet name="header">
                <h:outputText value="Username"/>
            </f:facet>
            <h:outputText value="#{accounts.username}"/>
        </rich:column>
       
        <rich:column sortBy="#{accounts.maxTotal}"> 
            <f:facet name="header">
                <h:outputText value="Sms Credits"/>
            </f:facet>
            <h:outputText rendered="#{not accounts.selected}" value="#{accounts.maxTotal}"/>

        </rich:column>
        <rich:column sortBy="#{accounts.organization}"> 
            <f:facet name="header">
                <h:outputText value="Organisation"/>
            </f:facet>
            <h:outputText value="#{accounts.organization}"/>
        </rich:column>
        <rich:column sortBy="#{accounts.contactNumber}"> 
            <f:facet name="header">
                <h:outputText value="Mobile"/>
            </f:facet>
            <h:outputText value="#{accounts.contactNumber}"/>
        </rich:column>
        <rich:column sortBy="#{accounts.emailAddress}"> 
            <f:facet name="header">
                <h:outputText value="Email"/>
            </f:facet>
            <h:outputText value="#{accounts.emailAddress}"/>
        </rich:column>
        <rich:column>
            <h:commandLink  id="viewUser" action="#{subAccounts.linkToMysubAcc}">
                <f:setPropertyActionListener target="#{subAccounts.selectedItem}" value="#{accounts}"/>
                <h:graphicImage alt="View" value="/resources/images/icons/user.jpg"  width="22px" height="22px" />
            </h:commandLink>
            <rich:toolTip for="viewUser">
                <span style="white-space:nowrap">
                    Click to view Sub Account
                </span>
            </rich:toolTip>

            <h:commandLink  id="editCredits" action="#{subAccounts.linkToeditSMSCredit}">
                <f:setPropertyActionListener target="#{subAccounts.selectedItem}" value="#{accounts}" />
                <h:graphicImage alt="Credits" value="/resources/images/icons/sms.png"/>
            </h:commandLink>
            <rich:toolTip for="editCredits">
                <span style="white-space:nowrap">
                    Click to edit SMS Credits
                </span>
            </rich:toolTip>


            <h:commandLink  id="editUser" action="#{subAccounts.linkToeditSubAcc}">
                <f:setPropertyActionListener target="#{subAccounts.selectedItem}" value="#{accounts}" />
                <h:graphicImage alt="View" value="/resources/images/icons/edit.gif"/>
            </h:commandLink>
            <rich:toolTip for="editUser">
                <span style="white-space:nowrap">
                    Click to edit Sub Account
                </span>
            </rich:toolTip>


        </rich:column>
        <f:facet name="footer">
            <rich:datascroller for="accounts" />
        </f:facet>
    </rich:dataTable>

</h:form>

