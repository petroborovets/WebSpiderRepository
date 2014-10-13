<%--
  Created by IntelliJ IDEA.
  User: petroborovets
  Date: 10/10/14
  Time: 8:59 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div class = "navbar navbar-inverse navbar-static-top">
    <div class = "container">
        <a href='<spring:url value="/"/>' class = "navbar-brand">Spider</a>

        <button class = "navbar-toggle" data-toggle = "collapse" data-target = ".navHeaderCollapse">
            <span class = "icon-bar"></span>
            <span class = "icon-bar"></span>
            <span class = "icon-bar"></span>
        </button>

        <div class = "collapse navbar-collapse navHeaderCollapse">
            <ul class = "nav navbar-nav navbar-right">
                <li class="home"><a href='<spring:url value="/"/>'>Home</a></li>
                <li class="crawler"><a href="#">Crawler</a></li>
                <li class="jobolizer"><a href='<spring:url value="/jobolizer/"/>'>Jobolizer</a></li>
                <li class="googleAPI"><a href="#">GoogleAPI</a></li>
                <li class="utilities"><a href="#">Utilities</a></li>
                <li class="settings"><a href="#">Settings</a></li>
                <li class="about"><a href="#">About</a></li>
                <li class="contacts"><a href="#">Contacts</a></li>
                <!--<li><a href="#login" data-toggle="modal">Log in</a></li>-->
            </ul>
        </div>
    </div>
</div>