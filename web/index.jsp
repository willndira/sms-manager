<%--
    Document   : login
    Created on : Oct 6, 2010, 11:19:15 AM
    Author     : Davis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<!-- RichFaces tag library declaration -->

<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<%@ taglib uri="http://richfaces.org/rich" prefix="rich" %>

<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>


            <style type="text/css">
                .rich-message-marker img {
                    padding-right:7px;
                }
                .rich-message-label {
                    color:red;
                }
                div#wrap{
                    text-align: center;
                }
                div.footer{

                    padding-bottom: 0px;
                    text-align: center;
                    background-color: #F7F7F7;
                    position:absolute;
                    bottom:0;
                    width:100%;
                    height:60px;   /* Height of the footer */
                }
                .addUSerPanelHeader{

                    padding: 10px 15px;
                    border-bottom: 1px solid transparent;
                    border-top-left-radius: 3px;
                    border-top-right-radius: 3px;
                }

                .header{
                    float: none;
                    margin: 40px auto 30px;
                    display: block;
                }
                .headerText{
                    font-family: "Open Sans",arial;
                    color: #555;
                    top: 10px;
                    font-size: 18px;
                    font-weight: 400;
                    margin-bottom: 20px;
                }
                .rich-panel{
                    width : 100%;
                    margin-bottom: 20px;
                    background-color: #FFF;
                    border: 1px solid transparent;
                    border-radius: 4px;
                    box-shadow: 0px 1px 1px rgba(0, 0, 0, 0.05);
                }
                .panelStyle{
                    background-color: #F7F7F7;
                    padding: 20px 25px 30px;
                    margin: 0px auto 25px;
                    width: 40%;
                    border-radius: 2px;
                    box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
                }
                .outputText{
                    font-family: "Open Sans",arial;
                    color: #555;
                    font-size: 18px;
                    font-weight: 400;
                    margin-bottom: 20px;
                }

                .inputText{
                    direction: ltr;
                    height: 25px;
                    width: 150px;
                    font-size: 16px;
                }
                .inputPassword{
                    height: 25px;
                    width: 150px;
                    font-size: 16px;
                }

            </style>

            <title>MSpace | Login</title></head>

    </head>
    <body>
        <!-- wrap starts here -->
        <div id="wrap">

            <!-- header -->
            <div class="header">
                <div align="center">
                    <h:graphicImage value="/resources/images/icons/mspacelogo.jpeg" alt="mspace" height="69" width="22%" longdesc="http://www.mspace.co.ke"/>


                </div>

            </div>

            <div class="headerText" >
                Control Panel :: Login
            </div>


            <div id="main">
                <center>
                    <rich:spacer height="20px"/>
                    <rich:panel  styleClass="panelStyle" >
                        <!--  put content here...    -->
                        <h:form id="loginForm">
                            <h:panelGrid columns="2" style=" padding-top:40px;">

                                <h:outputText styleClass="outputText" value="Username" />
                                <h:inputText  styleClass="inputText" id="name"  label="Username" required="true" requiredMessage="Username required" value="#{userData.userBean.username}"/>

                                <h:outputText styleClass="outputText" value="Password" />
                                <h:inputSecret  id="password" styleClass="inputPassword"  label = "Password" required="true" requiredMessage="Password is required" value="#{userData.userBean.password}"/>
                                <rich:spacer height="4px" />  
                                <h:panelGroup id="output">
                                    <h:outputLabel value=""  style="color:red;font-size:12px;"/>
                                </h:panelGroup>
                                <rich:spacer height="1px" /> 
                                <h:commandButton  value="Login"  action="#{userData.login}"/>
                            </h:panelGrid>

                        </h:form>
                        <!--    End put content     -->
                    </rich:panel>
                </center>
                <p>&nbsp;</p>
            </div>

            <!-- wrap ends here -->
        </div>

        <!-- footer starts here -->
        <div class="footer">

            <p>
                &copy; <h:outputLabel id="demo"></h:outputLabel> <a style="text-decoration: none;" href="http://www.mspace.co.ke"><strong>MSpace Solutions Ltd.</strong></a><strong> </strong>&nbsp;&nbsp;</p>


            </div>

            <script>
                var d = new Date();
                document.getElementById("demo").innerHTML = d.getFullYear();
            </script>

        </body>
    </html>
</f:view>