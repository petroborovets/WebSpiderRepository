<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Spring with names!</title>
</head>
<body>
    <c:choose>
        <c:when test="${name == 'kitten'}">
            Kitten purr..
        </c:when>
        <c:otherwise>
            <h1>Hello, ${name}!</h1>
        </c:otherwise>
    </c:choose>
</body>
</html>
