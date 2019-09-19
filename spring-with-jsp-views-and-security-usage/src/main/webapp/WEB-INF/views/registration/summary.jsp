<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Podsumowanie rejestracji</title>
    <jsp:include page="/WEB-INF/views/fragments/head.jsp"/>
</head>
<body class="has-navbar-fixed-top">
<header>
    <jsp:include page="/WEB-INF/views/fragments/menu.jsp"/>
</header>
<section class="section">
    <div class="container">
        <h1 class="title">
            Zakończono rejestrację
        </h1>
        <c:choose>
            <c:when test="${not empty successMsg}">
                <h2 class="subtitle" style="color: green;">
                        ${successMsg}
                </h2>
            </c:when>
            <c:otherwise>
                <h2 class="subtitle" style="color: red;">
                        ${errorMsg}
                </h2>
            </c:otherwise>
        </c:choose>
    </div>
</section>
<footer class="footer">
    <jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</footer>
</body>
</html>
