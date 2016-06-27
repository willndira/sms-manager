<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

<!-- RichFaces tag library declaration -->

<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<%@ taglib uri="http://richfaces.org/rich" prefix="rich" %>



<f:view>

    <html>
        <head>


            <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
            <title>MSpace: Main Panel</title> 

            <link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css">



            <style>

                body{
                    font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
                    font-size: 14px;
                    line-height: 1.42857;
                    color: #333;

                }
                div.footer{

                    text-align: center; 
                    background-color: #F7F7F7;
                    position: fixed;
                    left:0px; 
                    right:0px;
                    bottom: 0px;
                    width: 100%; 
                    height: 60px; 
                    margin-top: -10px; 
                    clear:both;
                    /* negative value of footer height 

                    
                                        padding-bottom: 0px;
                                        text-align: center;
                                        background-color: #F7F7F7;
                                        position:absolute;
                                        bottom:0;
                                        width:100%;
                                        height:60px;   
                    */
                }
                .h1 ,h2 ,h3{
                    font-family: inherit;
                    font-weight: 500;
                    line-height: 1.1;
                    color: inherit;
                }
                .btn{
                    display: inline-block;
                    padding: 6px 20px;
                    margin-bottom: 0px;
                    font-size: 14px;
                    font-weight: 400;
                    line-height: 1.42857;
                    text-align: center;
                    white-space: nowrap;
                    vertical-align: middle;
                    cursor: pointer;
                    -moz-user-select: none;
                    background-image: none;
                    border: 1px solid transparent;
                    border-radius: 4px;
                    box-sizing: border-box;
                }
                .btnSave{
                    float: right;
                    position: relative;
                    right: 250px;
                }
                .h1{
                    font-size: 36px;
                }
                .header{
                    float: none;
                    height: 20px;
                    margin: 40px auto 30px;
                    display: block;
                }
                .header .myImg{
                    height: 20px;
                    margin: 2px auto;
                }
                .panelStyle{
                    background-color: #F7F7F7;
                    //padding: 20px 25px 30px;
                    margin: 0px auto 25px;
                    width: 100%;
                    border-radius: 2px;
                    box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
                    overflow: hidden;
                    position: relative;
                }


                .panelHeaderAddAccount{
                    padding: 10px 15px;
                    border-bottom: 1px solid transparent;
                    border-top-left-radius: 3px;
                    border-top-right-radius: 3px;
                }
                .panelBodyAddAccount{

                }
                .odd{
                    background-color: #FFF; 
                }
                .even{
                    background-color: whitesmoke;
                }





                .homePar{
                    text-align: left;
                    float: left;
                    line-height: 100%;

                }

                .outputText{
                    font-weight: bold;
                    font-family: "Open Sans",arial;
                    padding-top: 10px;
                    padding-bottom: 10px;
                    text-align: left;
                }
                .outputNormal{

                    font-family: Arial, Helvetica, sans-serif;
                    padding-top: 10px;
                    padding-bottom: 10px;
                    text-align: left; 
                    font-size: 1.5em;

                }

                .comboStyle{
                    display: block;
                    width: 160%;
                    //height: 34px;
                    //padding: 6px 12px;
                    font-size: 14px;
                    //line-height: 1.42857;
                    color: #555;
                    background-color: #FFF;
                    background-image: none;
                    border: 1px solid #CCC;
                    border-radius: 4px;
                    box-shadow: 0px 1px 1px rgba(0, 0, 0, 0.075) inset;
                    transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
                    float:left;
                }

                .inputText{
                    display: block;
                    width: 150%;
                    height: 20px;
                    padding: 5px 12px;
                    font-size: 14px;
                    line-height: 1.42857;
                    color: #555;
                    background-color: #FFF;
                    background-image: none;
                    border: 1px solid #CCC;
                    border-radius: 4px;
                    box-shadow: 0px 1px 1px rgba(0, 0, 0, 0.075) inset;
                    transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;

                    float:left;
                    direction: ltr;


                }
                .inputSelect{
                    display: block;
                    width: 160%;
                    height: 34px;
                    padding: 6px 12px;
                    font-size: 14px;
                    line-height: 1.42857;
                    color: #555;
                    background-color: #FFF;
                    background-image: none;
                    border: 1px solid #CCC;
                    border-radius: 4px;
                    box-shadow: 0px 1px 1px rgba(0, 0, 0, 0.075) inset;
                    transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
                    float:left;

                }


                .inputPassword{
                    height: 25px;
                    width: 150px;
                    font-size: 16px;
                }

                .one{width: 40%; border: none ; text-align: right; padding: 8px}
                .two{width: 25%; border: none ; text-align: left}
                .three{width: 35%; border: none ; text-align: left}
                .reportTableCol1{width: 50%; border: 1px solid ; text-align: right; padding: 8px}
                .reportTableCol2{width: 50%; border: 1px solid ; text-align: left; padding: 8px}
                .manageSMSCol1{width: 50%; border: none ; text-align: right; padding: 3px}
                .manageSMSCol2{width: 50%; border: none ; text-align: left; padding: 3px}

                .info, .success, .warning, .error, .validation {
                    border: 1px solid;
                    width: 100%;
                    padding:15px 10px 15px 50px;
                    background-repeat: no-repeat;
                    background-position: 10px center;
                    margin-left: auto;
                    margin-right: auto;
                }
                .info {
                    color: #00529B;
                    background-color: #BDE5F8;
                    background-image: url('resources/images/icons/info.png');
                }
                .success {
                    color: #4F8A10;
                    background-color: #DFF2BF;
                    background-image:url('resources/images/icons/success.png');
                }
                .warning {
                    color: #9F6000;
                    background-color: #FEEFB3;
                    background-image: url('resources/images/icons/warning.png');
                }

                .pahelGrid{

                }
                .error {
                    margin-left: 60px;
                    color: #D8000C;
                    border: none;
                    font-size: 11px; 
                    line-height: 14px;
                    font-family: "Trebuchet MS", sans-serif;
                    //padding: 4px;
                }
            </style>



        </head>
        <body style="font-size: 20px">

            <!-- header -->
            <div id="header" >
                <div align="center">

                    <h:graphicImage value="/resources/images/icons/mspacelogo.jpeg" alt="mspace" height="50" width="180"/>
                </div>
            </div>
            <rich:spacer height="20"/>
            <table height="70%"  width="100%" border="0" >
                <tr>
                    <td valign="top" align="center" width="20%" >

                        <h:form id="formmenu">
                            <h:panelGrid width="100%">
                                <rich:panelMenu  mode="ajax"
                                                 iconExpandedGroup="disc" iconCollapsedGroup="disc"
                                                 iconExpandedTopGroup="chevronUp" iconGroupTopPosition="right"
                                                 iconCollapsedTopGroup="chevronDown">

                                    <rich:panelMenuGroup label="Manage User Details" style="text-align:left;">
                                        <rich:panelMenuItem reRender="pages" id="home">
                                            <h:outputText value="Home"/>

                                            <f:setPropertyActionListener target="#{panelMenuBean.currentPage}" value="/admin/home.jsp"/>
                                        </rich:panelMenuItem>
                                        <rich:toolTip for="home">
                                            <span style="white-space:nowrap">
                                                Click to home
                                            </span>
                                        </rich:toolTip>

                                        <rich:panelMenuItem reRender="pages" id="profile">
                                            <h:outputText value="Admin Account"/>

                                            <f:setPropertyActionListener target="#{panelMenuBean.currentPage}" value="/admin/profile.jsp"/>
                                        </rich:panelMenuItem>
                                        <rich:toolTip for="profile">
                                            <span style="white-space:nowrap">
                                                Click to view Admin Account
                                            </span>
                                        </rich:toolTip>

                                        <rich:panelMenuItem reRender="pages" id="addUserItem">
                                            <h:outputText value="Add Sub Account"/>

                                            <f:setPropertyActionListener target="#{panelMenuBean.currentPage}" value="/admin/createAccount.jsp"/>
                                        </rich:panelMenuItem>
                                        <rich:toolTip for="addUserItem">
                                            <span style="white-space:nowrap">
                                                Click to add new sub Account
                                            </span>
                                        </rich:toolTip>

                                        <rich:panelMenuItem reRender="pages" id="viewUsersItem">
                                            <h:outputText value="View Sub Accounts"/>
                                            <f:setPropertyActionListener target="#{panelMenuBean.currentPage}" value="/admin/accounts.jsp"/>

                                        </rich:panelMenuItem>
                                        <rich:toolTip for="viewUsersItem">
                                            <span style="white-space:nowrap">
                                                Click to view Sub Accounts
                                            </span>
                                        </rich:toolTip>


                                    </rich:panelMenuGroup>



                                    <rich:panelMenuGroup label="Reports" style="text-align:left;">
                                        <rich:panelMenuItem reRender="pages" id="report">
                                            <h:outputText value="Sms Reports"/>

                                            <f:setPropertyActionListener target="#{panelMenuBean.currentPage}" value="/admin/smsOutReports.jsp"/>
                                        </rich:panelMenuItem>
                                        <rich:toolTip for="report">
                                            <span style="white-space:nowrap">
                                                Click to view Reports
                                            </span>
                                        </rich:toolTip>

                                        <rich:panelMenuItem reRender="pages" id="smsUtilisation">
                                            <h:outputText value="SMS Utilization Reports"/>
                                            <f:setPropertyActionListener target="#{panelMenuBean.currentPage}" value="/admin/smsUtilisationReports.jsp"/>
                                        </rich:panelMenuItem>
                                        <rich:toolTip for="smsUtilisation">
                                            <span style="white-space:nowrap">
                                                Click to view SMS Utilization Reports
                                            </span>
                                        </rich:toolTip>
                                    </rich:panelMenuGroup>


                                    <rich:panelMenuItem  action="#{userData.logout}" id="logoutItem">
                                        <h:outputText style="padding:5px;background-color:#F0F0F0;color:#FF0000 ;" value="Log Out"/>
                                        <f:setPropertyActionListener value="/index.jsp" target="#{userBean.logout}"/>
                                    </rich:panelMenuItem>
                                    <rich:toolTip for="logoutItem">
                                        <span style="white-space:nowrap">
                                            Click to logout of the SMPP application
                                        </span>
                                    </rich:toolTip>

                                </rich:panelMenu>

                            </h:panelGrid>
                        </h:form>



                    </td>
                    <td valign="top" align="center" width="80%" height="50%">
                        <!----------------------------------------------------------------------------------------------------------------------->

                        <a4j:outputPanel id="pages" ajaxRendered="true">


                            <h:panelGroup>
                                <a4j:include viewId="#{panelMenuBean.currentPage}"/>
                            </h:panelGroup>



                        </a4j:outputPanel>

                        <a4j:status onstart="#{rich:component('processing')}.show()" onstop="#{rich:component('processing')}.hide()"/>

                        <rich:modalPanel style="text-align:center" id="processing" autosized="true" width="200" height="60" moveable="false" resizeable="false">

                        </rich:modalPanel>





                        <!----------------------------------------------------------------------------------------------------------------------->     

                    </td>
                </tr>
            </table>

            <rich:spacer height="50"></rich:spacer>
                <div style="clear:both;"></div>
                <div class="footer">
                    <p> &copy; <h:outputLabel id="demo"></h:outputLabel> <a style="text-decoration: none;" href="http://www.mspace.co.ke"><strong>MSpace Solutions Ltd.</strong></a><strong> </strong>&nbsp;&nbsp;</p>

                </div>
                <!-- JavaScript-->
                <script>
                    var d = new Date();
                    document.getElementById("demo").innerHTML = d.getFullYear();
                </script>
                <script type="text/javascript" src="https://www.google.com/jsapi"></script>
                <script type="text/javascript">

                    Array.prototype.reduce = undefined;
                    // Load the Visualization API and the piechart package.
                    google.load('visualization', '1.0', {
                        'packages': ['corechart']
                    });


                    function drawChart(serverData) {


                        var rows = JSON.parse('[' + serverData + ']');
                        var data = new google.visualization.DataTable();

                        data.addColumn('string', 'Year Month');
                        data.addColumn('number', 'No. of Times Sent');
                        data.addRows(rows);
                        // Set chart options
                        var options = {
                            'title': 'No of SMS Sent',
                            is3D: true,
                            pieSliceText: 'label',
                            tooltip: {
                                showColorCode: true
                            },
                            'width': 900,
                            'height': 500
                        };
                        // Instantiate and draw our chart, passing in some options.
                        //PieChart
                        //LineChart
                        //BarChart
                        var chart = new google.visualization.LineChart(document
                                .getElementById('chart_div'));
                        chart.draw(data, options);
                        var chart = new google.visualization.BarChart(document
                                .getElementById('chart1_div'));
                        chart.draw(data, options);


                    }

                    function drawChartAll(serverData, users) {


                        var rows = JSON.parse('[' + serverData + ']');
                        var usernames = JSON.parse(users);
                        var index;

                        var data = new google.visualization.DataTable();

                        data.addColumn('string', 'Timeline');
                        for (index = 0; index < usernames.length; index++) {

                            data.addColumn('number', usernames[index]);

                        }

                        data.addRows(rows);
                        // Set chart options
                        var options = {
                            'title': 'No of SMS Sent',
                            is3D: true,
                            pieSliceText: 'label',
                            tooltip: {
                                showColorCode: true
                            },
                            'width': 900,
                            'height': 500
                        };
                        // Instantiate and draw our chart, passing in some options.
                        //PieChart
                        //LineChart
                        //BarChart
                        var chart = new google.visualization.LineChart(document
                                .getElementById('chart_div'));
                        chart.draw(data, options);
                        var chart = new google.visualization.BarChart(document
                                .getElementById('chart1_div'));
                        chart.draw(data, options);


                    }


                </script>

                <!-- JavaScript End-->
            </body>
        </html>
</f:view>
