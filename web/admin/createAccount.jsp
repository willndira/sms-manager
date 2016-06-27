<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<!-- RichFaces tag library declaration -->

<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<%@ taglib uri="http://richfaces.org/rich" prefix="rich" %>

<!----------------------------------------------------------------------------------------------------------------------->


<h:form>

    <rich:panel styleClass="panelStyle" headerClass="panelHeaderAddAccount"   bodyClass="panelBodyAddAccount">
        <f:facet name="header" >
            <h1><h:outputText value="Create Sub-Account"/></h1>

        </f:facet>
        <h3><h:outputText >Available Sms Credits are: ${subAccounts.mainAdmin.maxTotal}</h:outputText></h3>
        <h:panelGrid id="contactform" styleClass="pahelGrid"  columns="3">
            <h:outputText styleClass="outputText" value="Username:" />
            <h:inputText 
                styleClass="inputText"
                required="true" 
                id="Username" 

                requiredMessage="Username Required" 
                value="#{subAccounts.subAccount.username}"
                >
                <f:validator validatorId="valUtil" />
                <a4j:support event="onblur"/>
            </h:inputText>
            <rich:message styleClass="error" for="Username" />



            <h:outputText styleClass="outputText"  value="Password" />
            <h:inputSecret required="true" styleClass="inputText" 
                           id="password" 

                           requiredMessage="Password required" 
                           value="#{subAccounts.subAccount.password}"
                           validatorMessage="Password must be between 5 and 15 characters"
                           >
                <f:validateLength minimum="5" maximum="15"/>
            </h:inputSecret>
            <rich:message styleClass="error" for="password"/>

            <h:outputText styleClass="outputText" value="SMS Credits" />
            <h:panelGroup>
                <h:inputText 
                    styleClass="inputText"
                    id="smsCredit"
                    value="#{subAccounts.subAccount.maxTotal}"
                    required="true"
                    requiredMessage="Sms Credit required"
                    validatorMessage="Sms Credit should be lower than main accounts credits."
                    
                    >
                    <f:validateLongRange minimum="0" maximum="#{subAccounts.mainAdmin.maxTotal}"  />

                </h:inputText>
            </h:panelGroup>
            <rich:message styleClass="error" for="smsCredit" />


            <h:outputText styleClass="outputText" value="Organizataion" />
            <h:inputText styleClass="inputText" id="organization" disabled="true" value="#{subAccounts.subAccount.organization}" />
            <rich:message styleClass="error" for="organization" />

            <h:outputText styleClass="outputText" value="Alphanumerics:" />
            <h:inputText  styleClass="inputText" disabled="true" id="alpha" value="#{subAccounts.allowedAlphanumerics.alphanumerics}" />
            <rich:message styleClass="error" for="alpha" />

            <h:outputText styleClass="outputText" value="User Mobile" />
            <h:inputText styleClass="inputText" id="mobile" 
                         required="true" 
                         validatorMessage="User Mobile must be  between 9 and 15"
                         requiredMessage="User Mobile required"
                         value="#{subAccounts.subAccount.contactNumber}"
                         >
                
                <f:validateLength minimum="9" maximum="15"/>
            </h:inputText>
            <rich:message styleClass="error" for="mobile" />

            <h:outputText styleClass="outputText" value="User Email" />
            <h:inputText 
                styleClass="inputText" 
                id="email" required="true" 
                requiredMessage="Email is required"
                validatorMessage="Email must be valid"
                value="#{subAccounts.subAccount.emailAddress}"
                >
                <f:validator validatorId="emailVAlidator"/>

            </h:inputText>
            <rich:message styleClass="error" for="email" />



            <h:outputText styleClass="outputText" value="Enable email alert when credit is over ?" />
            <h:selectBooleanCheckbox id="alert" styleClass="inputText" style="float:left;" required="true" value="#{subAccounts.subAccount.enableEmailAlert}" />
            <rich:message styleClass="error" for="alert" />

            <h:outputText styleClass="outputText" value="Alert Threshold" />
            <h:inputText  styleClass="inputText" required="true" requiredMessage="Alert Threshold required" id="threshold" value="#{subAccounts.subAccount.alertThreshold}" />
            <rich:message styleClass="error" for="threshold" />

        </h:panelGrid>
        <h:commandButton value="Save" id="submit" styleClass="btn btnSave" action="#{subAccounts.addSubAccount}"/>

    </rich:panel>
</h:form>
<!----------------------------------------------------------------------------------------------------------------------->     

