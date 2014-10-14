<%--
  Created by IntelliJ IDEA.
  User: petroborovets
  Date: 10/6/14
  Time: 1:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript">
    $(document).ready(function() {
        $('.home').addClass('active');
    });
</script>

<div class = "container">

    <div class = "jumbotron" name = "jumbotron">
        <center><h1>Spider app</h1></center>
            <blockquote>
                <p>Whatever you do in life will be insignificant, but it's very important that you do it. Because nobody else will. Like when someone comes into your life and half of you says you're nowhere near ready, but the other half says: make her yours forever.</p>
                <footer>Mahatma Handi</footer>
            </blockquote>
        <center><a class = "btn btn-default" >More</a>
        <a class = "btn btn-info">Tweet it!</a></center>
    </div>
</div>

<div class = "container">
    <div class = "row">
        <div class = "col-md-3">
            <h3><a href="#">Crawler</a></h3>
            <p>Web search engines and some other sites use Web crawling or spidering software to update their web content or indexes of others sites' web content. Web crawlers can copy all the pages they visit for later processing by a search engine that indexes the downloaded pages so that users can search them much more quickly.</p>
            <a href="#" class = "btn btn-default">Start Crawl</a>
        </div>
        <div class = "col-md-3">
            <h3><a href='<spring:url value="/jobolizer/"/>'>Jobolizer</a></h3>
            <p>Module uses <a href="http://jobolizer.com">jobolizer.com</a> to collect and parse data. Job ads can be extracted from complex HTML pages. Direct processing of newspaper advertisements. Processes all common document formats (also scanned documents). Multi-language capabilities (more than 30 languages).</p>
            <a href="/jobolizer/" class = "btn btn-default">Start Jobolizer</a>

        </div>
        <div class = "col-md-3">
            <h3><a href='<spring:url value="/googleApi/"/>'>Google API</a></h3>
            <p>Module is based on Google API. Uses google engine to find links needed.</p>
            <a href=<a href='<spring:url value="/googleApi/"/>' class = "btn btn-default">Go Googling</a>

        </div>
        <div class = "col-md-3">
            <h3><a href="#">Utilities</a></h3>
            <p>Module combining different utilities, such as combining or synchronizing database data.</p>
            <a href="#" class = "btn btn-default">Go to Utilities</a>
        </div>
    </div>
</div>