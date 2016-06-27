<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<!-- RichFaces tag library declaration -->

<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<%@ taglib uri="http://richfaces.org/rich" prefix="rich" %>

<!----------------------------------------------------------------------------------------------------------------------->

<rich:panel style="margin-left:auto; margin-right:auto" header="SMS OUT REPORT">

    <!----------------------------------------------------------------->
    <h:form>

        
            <h:panelGrid  columns="10" width="100%" style="margin-left:auto; margin-right:auto;padding:8px">
                <h:outputText value="Start Date :"/>
                <rich:calendar value="#{reportController.searchSmsUserBean.smsOutModel.startTime}" 

                               cellWidth="24px"
                               cellHeight="22px"
                               style="width:200px" 
                               datePattern="yyyy-MM-dd"/>
                <h:outputText value="End Date :"/>
                <rich:calendar 
                    value="#{reportController.searchSmsUserBean.smsOutModel.endTime}"
                    cellWidth="24px"
                    cellHeight="22px" 
                    style="width:200px" 
                    datePattern="yyyy-MM-dd"
                    />


                <h:outputText value="Username" />
                <rich:comboBox  inputClass="comboStyle" value="#{reportController.searchSmsUserBean.userBean.username}" defaultLabel="Select User" suggestionValues="#{reportController.userNameSuggetions}"   directInputSuggestions="true"/>


                <h:outputText value="Mobile Number" />
                <h:inputText   value="#{reportController.searchSmsUserBean.smsOutModel.destinationAddress}"/>

                <h:commandLink action="#{reportController.search}">
                    <f:setPropertyActionListener target="#{reportController.selectedSearchSmsUserBean}" value="#{reportController.searchSmsUserBean}"/>
                    <h:graphicImage value="/resources/images/icons/search1.png"  width="22px" height="22px" />
                </h:commandLink>

                <h:commandLink action="#{reportController.generateReport}">
                    <f:setPropertyActionListener target="#{reportController.selectedSearchSmsUserBean}" value="#{reportController.searchSmsUserBean}"/>
                    <h:graphicImage value="/resources/images/icons/icon-xls.gif"  width="22px" height="22px" />
                </h:commandLink>

            </h:panelGrid> 
        

    </h:form>

    <!----------------------------------------------------------------->


    <h:form>

        <a4j:keepAlive beanName="reportController"/>

        <rich:dataTable 
            width="100%"
            id="datatable"
            rows="20" 
            var="sms" 
            value="#{reportController.smsOutUserBeans}">
            <f:facet name="header">
                <h:outputText value="Sms Reports"/>
            </f:facet>
            <rich:column sortBy="#{sms.userBean.destinationAddress}">
                <f:facet name="header"><h:outputText value="Mobile"/></f:facet>
                <h:outputText value="#{sms.smsOutModel.destinationAddress}"/>
            </rich:column>
            <rich:column sortBy="#{sms.smsOutModel.sourceAddress}">
                <f:facet name="header"><h:outputText value="Source Address" /></f:facet>
                <h:outputText value="#{sms.smsOutModel.sourceAddress}"></h:outputText>
            </rich:column>
            <rich:column sortBy="#{sms.smsOutModel.messagePayload}">
                <f:facet name="header"><h:outputText value="Message" /></f:facet>
                <h:outputText value="#{sms.smsOutModel.messagePayload}"></h:outputText>
            </rich:column>
            <rich:column sortBy="#{sms.smsOutModel.timeSubmitted}">
                <f:facet name="header"><h:outputText value="Time Sent" /></f:facet>
                <h:outputText value="#{sms.smsOutModel.timeSubmitted}">
                    <f:convertDateTime pattern="yyyy-MM-dd hh:mm:ss"/> 
                </h:outputText>
            </rich:column>
            <rich:column sortBy="#{sms.smsOutModel.timeProcessed}">
                <f:facet name="header"><h:outputText value="Last Update" /></f:facet>
                <h:outputText value="#{sms.smsOutModel.timeProcessed}">
                    <f:convertDateTime pattern="yyyy-MM-dd hh:mm:ss"/> 
                </h:outputText>
            </rich:column>
            <rich:column sortBy="#{sms.userBean.username}">
                <f:facet name="header"><h:outputText value="User" /></f:facet>
                <h:outputText value="#{sms.userBean.username}"></h:outputText>
            </rich:column>
            <rich:column sortBy="#{sms.smsOutModel.realStatus}">
                <f:facet name="header"><h:outputText value="Status" /></f:facet>
                <h:outputText value="#{sms.smsOutModel.realStatus}"></h:outputText>
            </rich:column>
            <rich:column sortBy="#{sms.smsOutModel.smsCount}">
                <f:facet name="header"><h:outputText value="Number of SMS" /></f:facet>
                <h:outputText value="#{sms.smsOutModel.smsCount}"></h:outputText>
            </rich:column>


            <f:facet name="footer">
                <h:panelGrid columns="1" width="100%">
                    <rich:columnGroup>
                        <rich:column colspan="7">Summary: Total Number of SMS Sent for the period</rich:column>
                        <rich:column><h:outputLabel value="#{reportController.totalSmsCount}"/></rich:column>
                    </rich:columnGroup>
                    <rich:datascroller for="datatable" />
                </h:panelGrid>

            </f:facet>



        </rich:dataTable>



    </h:form>
</rich:panel>
