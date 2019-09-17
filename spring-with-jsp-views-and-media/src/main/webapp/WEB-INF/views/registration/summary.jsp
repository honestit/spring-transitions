<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Podsumowanie rejestracji</title>
    <jsp:include page="/WEB-INF/views/fragments/head.jsp"/>
</head>
<body>
<header>
    <jsp:include page="/WEB-INF/views/fragments/menu.jsp"/>
</header>
<section>
    <h1>Rejestracja zakończona</h1>
</section>
<section>
    <div>
        <h3>
            <c:choose>
                <c:when test="${not empty successMsg}">
                    ${successMsg}
                </c:when>
                <c:otherwise>
                    ${errorMsg}
                </c:otherwise>
            </c:choose>
        </h3>
    </div>
</section>
<footer>
    <jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</footer>
</body>
</html>
