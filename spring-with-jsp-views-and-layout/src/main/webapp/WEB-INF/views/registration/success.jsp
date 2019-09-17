<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Udana rejestracja</title>
</head>
<body>
<header>
    <h1>Rejestracja</h1>
</header>
<section>
    <h1>
        <c:choose>
            <c:when test="${not empty successMsg}">
                ${successMsg}
            </c:when>
            <c:otherwise>
                ${errorMsg}
            </c:otherwise>
        </c:choose>
    </h1>
</section>
</body>
</html>
