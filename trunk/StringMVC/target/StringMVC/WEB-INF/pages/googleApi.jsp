<%--
  Created by IntelliJ IDEA.
  User: petroborovets
  Date: 10/13/14
  Time: 4:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript">
    $(document).ready(function() {
        $('.googleAPI').addClass('active');
    });
</script>

<div class="container">
    <div class = "row">
        <div class = "col-lg-4">
            <div class="panel panel-default">
                <div class="panel-heading">Choose googled URLs save location</div>
                <div class="panel-body">
                    <form:form name = "saveLocation" method="post" action="/googleApi/startGoogle" commandName="googleOutputLocationDTO" modelAttribute="googleOutputLocationDTO">
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
                        <div class="input-group searchString" style="margin-bottom:5px">
                            <span class="input-group-addon">Search string</span>
                            <form:input class="form-control" id="idFieldNameId" path="idFieldName" placeholder="What are you looking for?" />
                        </div>

                    </form:form>
                    <!-- Indicates a successful or positive action -->
                    <button onclick="startGoogle()" type="submit" class="btn btn-success btn-block">Google</button>
                </div>
            </div>
        </div>
        <div class="col-lg-8">
                <div class="panel panel-info">
                    <div class="panel-heading">Google results</div>
                    <div class="panel-body" style="max-height: 239px;overflow-y: scroll;">
                        <c:choose>
                            <c:when test="${spiderResultsDTO != null}">
                                <c:forEach items="${spiderResultsDTO.urlResultList}" var="resultInfo">
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
                                <p><mark>Google successfully saved <c:out value="${spiderResultsDTO.numberOfURLsSaved}"/>/<c:out value="${spiderResultsDTO.numberOfURLsSendToJobolizer}"/> vacancies.</mark></p>
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
    function startGoogle() {
        var isValid = true;

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

        if(isValid) {
            document.saveLocation.submit();
            $('#waitingForProcessModal').modal('toggle');
            $('#waitingForProcessModal').modal('show');
        }
    }
</script>