<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<!-- RichFaces tag library declaration -->

<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<%@ taglib uri="http://richfaces.org/rich" prefix="rich" %>

<!----------------------------------------------------------------------------------------------------------------------->



    <!----------------------------------------------------------------->


    <!----------------------------------------------------------------->


    <h:form>

        <rich:dataTable 
            width="100%"
            id="datatable"
            rows="10" 
            var="sms" 
            value="#{reportController.smsCreditses}">
            <f:facet name="header">
                <h:outputText value="Sms Reports"/>
            </f:facet>

            <rich:column sortBy="#{sms.username}">
                <f:facet name="header"><h:outputText value="User"/></f:facet>
                <h:outputText value="#{sms.username}"/>
            </rich:column>

            <rich:column sortBy="#{sms.actionType}">
                <f:facet name="header"><h:outputText value="Details"/></f:facet>
                 <h:outputText value="#{sms.actType}"/> 
                
            </rich:column>

            <rich:column sortBy="#{sms.previous_balance}">
                <f:facet name="header"><h:outputText value="Previous Balance"/></f:facet>
                <h:outputText value="#{sms.previous_balance}"/>
            </rich:column>
            <rich:column sortBy="#{sms.numCredits}">
                <f:facet name="header"><h:outputText value="Transacted"/></f:facet>
                <h:outputText value="#{sms.numCredits}"/>
            </rich:column>
            <rich:column sortBy="#{sms.new_balance}">
                <f:facet name="header"><h:outputText value="New Balance"/></f:facet>
                <h:outputText value="#{sms.new_balance}"/>
            </rich:column>


            <rich:column sortBy="#{sms.actionTime}">
                <f:facet name="header"><h:outputText value="Time" /></f:facet>
                <h:outputText value="#{sms.actionTime}">
                    <f:convertDateTime dateStyle="long" pattern="yyyy-MM-dd hh:mm:ss"/>
                </h:outputText>
            </rich:column>

            <f:facet name="footer">

                <rich:datascroller for="datatable" />
            </f:facet>



        </rich:dataTable>



    </h:form>

