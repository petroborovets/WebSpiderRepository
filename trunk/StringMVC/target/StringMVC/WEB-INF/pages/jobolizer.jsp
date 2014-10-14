<%--
  Created by IntelliJ IDEA.
  User: petroborovets
  Date: 10/10/14
  Time: 8:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript">
    $(document).ready(function() {
        $('.jobolizer').addClass('active');
    });
</script>

<!-- Jobolizer starter -->
<div class="container">
    <div class = "row">
        <div class = "col-lg-4">
            <div class="panel panel-default">
                <div class="panel-heading">Location of URLs to analyze</div>
                <div class="panel-body">
                    <form:form name = "resourceLocation" method="post" action="/jobolizer/startJobolizer" commandName="jobolizerResourceDTO" modelAttribute="jobolizerResourceDTO">
                        <div class="input-group tableName" style="margin-bottom:5px">
                            <span class="input-group-addon">Table name</span>
                            <form:input class="form-control" id="tableNameId" path="tableName" placeholder="Enter table name" />
                        </div>
                        <div class="input-group urlFieldName" style="margin-bottom:5px">
                            <span class="input-group-addon">URL field name</span>
                            <form:input class="form-control" id="urlFieldNameId" path="urlFieldName" placeholder="Enter URL field name" />
                        </div>
                        <div class="input-group idFieldName" style="margin-bottom:5px">
                            <span class="input-group-addon">ID field name</span>
                            <form:input class="form-control" id="idFieldNameId" path="idFieldName" placeholder="Enter ID field name" />
                        </div>
                        <div class="input-group idFrom" style="margin-bottom:5px">
                            <span class="input-group-addon">ID from</span>
                            <form:input class="form-control" id="idFromId" path="idFrom" placeholder="Enter from" />
                        </div>
                        <div class="input-group idTo" style="margin-bottom:5px">
                            <span class="input-group-addon">ID to</span>
                            <form:input class="form-control" id="idToId" path="idTo" placeholder="Enter to" />
                        </div>
                    </form:form>
                    <!-- Indicates a successful or positive action -->
                    <button onclick="startJobolizer()" type="submit" class="btn btn-success btn-block">Start</button>
                </div>
            </div>
        </div>
        <div class="col-lg-8">
            <div class="panel panel-info">
                <div class="panel-heading">Jobolizer results</div>
                <div class="panel-body" style="max-height: 259px;overflow-y: scroll;">
                    <c:choose>
                    <c:when test="${jobolizerResultsDTO != null}">
                        <c:forEach items="${jobolizerResultsDTO.urlResultList}" var="resultInfo">
                            <p><strong>Vacancy: </strong><a><c:out value="${resultInfo.vacancyURL}"/></a></p>
                            <c:choose>
                                <c:when test="${resultInfo.error == true}">
                                    <p> <code>Error: <c:out value="${resultInfo.errorDescription}"/> </code></p>
                                </c:when>
                                <c:otherwise>
                                    <p><mark>Successfully saved with ID: <c:out value="${resultInfo.id}"/></mark></p>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <br>
                        <strong>Conclusion:</strong>
                        <p><mark>Jobolizer successfully saved <c:out value="${jobolizerResultsDTO.numberOfURLsSaved}"/>/<c:out value="${jobolizerResultsDTO.numberOfURLsSendToJobolizer}"/> vacancies.</mark></p>
                    </c:when>
                    <c:otherwise>
                        No results yet.
                    </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>
<br>
<!-- Jobolizer collected data -->
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                    <b>Jobolizer data</b>
                    <a class="btn btn-sm btn-success pull-right" data-toggle="modal"
                       data-target=".addJobolizerBundle">Add vacancy</a>
                    <mark style="margin-right: 10px; margin-top: 4px" class="pull-right">${message}</mark>
                    <div class="clearfix"></div>
                </a>
            </h4>
        </div>
        <div id="collapseOne" class="panel-collapse collapse">
            <div class="panel-body">
                <style>
                    table { table-layout: fixed; }
                    table th, table td { overflow: hidden; }
                </style>

                <table class="table table-hover table-responsive">
                    <thead>
                        <tr>
                            <td style="text-align: left; width: 6%"><b>#</b></td>
                            <td style="text-align: center; width: 19%"><b>Job Title 1</b></td>
                            <td style="text-align: center; width: 20%"><b>Company Name</b></td>
                            <td style="text-align: center; width: 37%"><b>Company Url</b></td>
                            <td style="text-align: center; width: 6%"><b>Details</b></td>
                            <td style="text-align: center; width: 6%"><b>Edit</b></td>
                            <td style="text-align: center; width: 6%"><b>Delete</b></td>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${jobolizerBundles}" var="jBundle">
                            <tr>
                                <td style="text-align: left"><c:out value="${jBundle.id}"/></td>
                                <td style="text-align: center;"><c:out value="${jBundle.jobTitle1}"/></td>
                                <td style="text-align: center;"><c:out value="${jBundle.companyName}"/></td>
                                <td style="text-align: center; margin:auto"><c:out value="${jBundle.companyUrl}"/></td>
                                <td style="text-align: center;"><a href="/jobolizer/showBundle/${jBundle.id}"><span class="glyphicon glyphicon-zoom-in"></span></a></td>
                                <td style="text-align: center;" disabled><a href="#"><span class="glyphicon glyphicon-pencil"></span></a></td>
                                <td style="text-align: center;"><a href="/jobolizer/deleteBundle/${jBundle.id}"><span class="glyphicon glyphicon-trash"></span></a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Get bundle details modal -->


<!-- Add new bundle element modal -->
<div class="modal fade bs-modal-lg addJobolizerBundle" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">Add new vacancy</h4>
            </div>
            <div class = "modal-body">
                <form:form name = "addBundleId" method="post" action="/jobolizer/addBundle" commandName="jobolizerBundle" modelAttribute="jobolizerBundle">
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Job title 1</span>
                        <form:input class="form-control" path="jobTitle1" placeholder="Enter first job title" />
                        <!--<form:input size="15" path="jobTitle1" type="text" class="form-control" placeholder="Job Title"/>-->
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Job title 2</span>
                        <form:input class="form-control" path="jobTitle2" placeholder="Enter second job title" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Position</span>
                        <form:input class="form-control" path="position" placeholder="Enter position" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Employment type</span>
                        <form:input class="form-control" path="employmentType" placeholder="Enter employment type" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Limited in time</span>
                        <form:input class="form-control" path="limitedInTime" placeholder="Is vacancy time limited?" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Company name</span>
                        <form:input class="form-control" path="companyName" placeholder="Enter company name" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Industries and brunches</span>
                        <form:input class="form-control" path="industriesAndBrunches" placeholder="Enter industries and brunches" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Traveling required</span>
                        <form:input class="form-control" path="travelingRequired" placeholder="Is traveling requiered?" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Military required</span>
                        <form:input class="form-control" path="militaryRequired" placeholder="Is military requiered?" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Work experience</span>
                        <form:input class="form-control" path="workExperience" placeholder="Enter work experience" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Educational level</span>
                        <form:input class="form-control" path="educationalLevel" placeholder="Enter educational level" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Educational type</span>
                        <form:input class="form-control" path="educationalType" placeholder="Enter educational type" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Skills needed</span>
                        <form:input class="form-control" path="skillsNeeded" placeholder="Enter skills" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Language</span>
                        <form:input class="form-control" path="language" placeholder="Enter language" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Language level</span>
                        <form:input class="form-control" path="languageLevel" placeholder="Enter language level" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Operational area</span>
                        <form:input class="form-control" path="operationalArea" placeholder="Enter operational area" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Operational area relevance</span>
                        <form:input class="form-control" path="operationalAreaRelevance" placeholder="Enter operational area relevance" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Operational area 2</span>
                        <form:input class="form-control" path="operationalArea2" placeholder="Enter operational area 2" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Operational area relevance 2</span>
                        <form:input class="form-control" path="operationalAreaRelevance2" placeholder="Enter operational area relevance 2" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Working country</span>
                        <form:input class="form-control" path="workingCountry" placeholder="Enter working country" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Emails</span>
                        <form:input class="form-control" path="emails" placeholder="Enter emails" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">City</span>
                        <form:input class="form-control" path="city" placeholder="Enter city" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Post code</span>
                        <form:input class="form-control" path="postCode" placeholder="Enter post code" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Phone numbers</span>
                        <form:input class="form-control" path="phoneNumbers" placeholder="Enter phone numbers" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Salutation</span>
                        <form:input class="form-control" path="salutation" placeholder="Enter salutation" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Contact name</span>
                        <form:input class="form-control" path="contactName" placeholder="Enter contact name" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Company Url</span>
                        <form:input class="form-control" path="companyUrl" placeholder="Enter company Url" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">company address</span>
                        <form:input class="form-control" path="companyAddress" placeholder="Enter company address" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Company state</span>
                        <form:input class="form-control" path="companyState" placeholder="Enter company state" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Latitude</span>
                        <form:input class="form-control" path="latitude" placeholder="Enter latitude" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Longitude</span>
                        <form:input class="form-control" path="longitude" placeholder="Enter longitude" />
                    </div>
                    <div class="input-group" style="margin-bottom:5px">
                        <span class="input-group-addon">Vacancy URL</span>
                        <form:input class="form-control" path="vacancyURL" placeholder="Enter vacancy URL" />
                    </div>
                </form:form>
            </div>
            <div class = "modal-footer">
                <a class="btn btn-danger" data-dismiss = "modal">Cancel</a>
                <a href="javascript:document.addBundleId.submit()" class = "btn btn-success">Add</a>
            </div>
        </div>
    </div>
</div>

<div id="waitingForProcessModal" class="modal fade" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1>Processing...</h1>
            </div>
            <div class="modal-body">
                <div class="progress">
                    <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%;">
                        <span class="sr-only">Analizing...</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function startJobolizer() {
        var isValid = true;
        var fromIdValue = parseInt(document.getElementById("idFromId").value);
        var toIdValue = parseInt(document.getElementById("idToId").value);

        if(document.getElementById("tableNameId").value == "" ||
                document.getElementById("tableNameId").value == null) {
            $('.tableName').addClass('has-error');
            $('.tableName').removeClass('has-success');
            isValid = false;
        } else {
            $('.tableName').addClass('has-success');
            $('.tableName').removeClass('has-error');
        }

        if(document.getElementById("urlFieldNameId").value == "" ||
                document.getElementById("urlFieldNameId").value == null) {
            $('.urlFieldName').addClass('has-error');
            $('.urlFieldName').removeClass('has-success');
            isValid = false;
        } else {
            $('.urlFieldName').addClass('has-success');
            $('.urlFieldName').removeClass('has-error');
        }

        if(document.getElementById("idFieldNameId").value == "" ||
                document.getElementById("idFieldNameId").value == null) {
            $('.idFieldName').addClass('has-error');
            $('.idFieldName').removeClass('has-success');
            isValid = false;
        } else {
            $('.idFieldName').addClass('has-success');
            $('.idFieldName').removeClass('has-error');
        }

        if(document.getElementById("idFromId").value == "" ||
                document.getElementById("idFromId").value == null ||
                fromIdValue < 0 || toIdValue < fromIdValue ||
                isNaN(fromIdValue)){
            $('.idFrom').addClass('has-error');
            $('.idFrom').removeClass('has-success');
            isValid = false;
        } else {
            $('.idFrom').addClass('has-success');
            $('.idFrom').removeClass('has-error');
        }

        if(document.getElementById("idToId").value == "" ||
                document.getElementById("idToId").value == null ||
                toIdValue == 0 || toIdValue < 0 ||
                fromIdValue >= toIdValue || isNaN(toIdValue)) {
            $('.idTo').addClass('has-error');
            $('.idTo').removeClass('has-success');
            isValid = false;
        } else {
            $('.idTo').removeClass('has-error');
            $('.idTo').addClass('has-success');
        }
        if(isValid) {
            document.resourceLocation.submit();
            $('#waitingForProcessModal').modal('toggle');
            $('#waitingForProcessModal').modal('show');
        }
    }
</script>