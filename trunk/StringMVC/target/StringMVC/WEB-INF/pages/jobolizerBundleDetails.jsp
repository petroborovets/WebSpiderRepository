<%--
  Created by IntelliJ IDEA.
  User: petroborovets
  Date: 10/7/14
  Time: 8:51 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div class="container">
    <ol class="breadcrumb">
        <li><a href='<spring:url value="/jobolizer/" /> '>Jobolizer Home</a></li>
        <li class="active">Bundle details</li>
    </ol>
</div>

<div class="container">
            <table class="table table-bordered table-striped">
                <colgroup>
                    <col class="col-xs-1">
                    <col class="col-xs-7">
                </colgroup>
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Value</th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>
                            <code>ID</code>
                        </td>
                        <td>${jobolizerBundle.id}</td>
                    </tr>
                    <c:if test="${not empty jobolizerBundle.jobTitle1}">
                        <tr>
                            <td>
                                <code>Job title 1</code>
                            </td>
                            <td>${jobolizerBundle.jobTitle1}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.jobTitle2}">
                        <tr>
                            <td>
                                <code>Job title 2</code>
                            </td>
                            <td>${jobolizerBundle.jobTitle2}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.position}">
                        <tr>
                            <td>
                                <code>Position</code>
                            </td>
                            <td>${jobolizerBundle.position}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.employmentType}">
                        <tr>
                            <td>
                                <code>Employment type</code>
                            </td>
                            <td>${jobolizerBundle.employmentType}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.limitedInTime}">
                        <tr>
                            <td>
                                <code>Limited in time</code>
                            </td>
                            <td>${jobolizerBundle.limitedInTime}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.companyName}">
                        <tr>
                            <td>
                                <code>Company name</code>
                            </td>
                            <td>${jobolizerBundle.companyName}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.industriesAndBrunches}">
                        <tr>
                            <td>
                                <code>Industries and brunches</code>
                            </td>
                            <td>${jobolizerBundle.industriesAndBrunches}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.travelingRequired}">
                        <tr>
                            <td>
                                <code>Traveling required</code>
                            </td>
                            <td>${jobolizerBundle.travelingRequired}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.militaryRequired}">
                        <tr>
                            <td>
                                <code>Military required</code>
                            </td>
                            <td>${jobolizerBundle.militaryRequired}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.workExperience}">
                        <tr>
                            <td>
                                <code>Work experience</code>
                            </td>
                            <td>${jobolizerBundle.workExperience}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.educationalLevel}">
                        <tr>
                            <td>
                                <code>Educational level</code>
                            </td>
                            <td>${jobolizerBundle.educationalLevel}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.educationalType}">
                        <tr>
                            <td>
                                <code>Educational type</code>
                            </td>
                            <td>${jobolizerBundle.educationalType}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.skillsNeeded}">
                        <tr>
                            <td>
                                <code>Skills needed</code>
                            </td>
                            <td>${jobolizerBundle.skillsNeeded}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.language}">
                        <tr>
                            <td>
                                <code>Language</code>
                            </td>
                            <td>${jobolizerBundle.language}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.languageLevel}">
                        <tr>
                            <td>
                                <code>Language level</code>
                            </td>
                            <td>${jobolizerBundle.languageLevel}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.operationalArea}">
                        <tr>
                            <td>
                                <code>Operational area</code>
                            </td>
                            <td>${jobolizerBundle.operationalArea}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.operationalAreaRelevance}">
                        <tr>
                            <td>
                                <code>Operational area relevance</code>
                            </td>
                            <td>${jobolizerBundle.operationalAreaRelevance}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.operationalArea2}">
                        <tr>
                            <td>
                                <code>Operational area 2</code>
                            </td>
                            <td>${jobolizerBundle.operationalArea2}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.operationalAreaRelevance2}">
                        <tr>
                            <td>
                                <code>Operational area relevance 2</code>
                            </td>
                            <td>${jobolizerBundle.operationalAreaRelevance2}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.workingCountry}">
                        <tr>
                            <td>
                                <code>Working country</code>
                            </td>
                            <td>${jobolizerBundle.workingCountry}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.emails}">
                        <tr>
                            <td>
                                <code>Emails</code>
                            </td>
                            <td>${jobolizerBundle.emails}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.city}">
                        <tr>
                            <td>
                                <code>City</code>
                            </td>
                            <td>${jobolizerBundle.city}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.postCode}">
                        <tr>
                            <td>
                                <code>Post code</code>
                            </td>
                            <td>${jobolizerBundle.postCode}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.phoneNumbers}">
                        <tr>
                            <td>
                                <code>Phone numbers</code>
                            </td>
                            <td>${jobolizerBundle.phoneNumbers}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.salutation}">
                        <tr>
                            <td>
                                <code>Salutation</code>
                            </td>
                            <td>${jobolizerBundle.salutation}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.contactName}">
                        <tr>
                            <td>
                                <code>Contact name</code>
                            </td>
                            <td>${jobolizerBundle.contactName}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.companyUrl}">
                        <tr>
                            <td>
                                <code>Company url</code>
                            </td>
                            <td>${jobolizerBundle.companyUrl}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.companyAddress}">
                        <tr>
                            <td>
                                <code>Company address</code>
                            </td>
                            <td>${jobolizerBundle.companyAddress}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.companyState}">
                        <tr>
                            <td>
                                <code>Company state</code>
                            </td>
                            <td>${jobolizerBundle.companyState}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.latitude}">
                        <tr>
                            <td>
                                <code>Latitude</code>
                            </td>
                            <td>${jobolizerBundle.latitude}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.longitude}">
                        <tr>
                            <td>
                                <code>Longitude</code>
                            </td>
                            <td>${jobolizerBundle.longitude}</td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty jobolizerBundle.vacancyURL}">
                        <tr>
                            <td>
                                <code>Vacancy URL</code>
                            </td>
                            <td>${jobolizerBundle.vacancyURL}</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
</div>