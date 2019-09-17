<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--
    Ta strona reprezentuje nasze menu główne, które pojawiać się będzie na każdej
    innej stronie.

    Jej treść będzie osadzona wewnątrz tagu <header></header>.
--%>

<nav>
    <div>
        <ul>
            <li>
                <c:url var="homePageURL" value="/"/>
                <a href="${homePageURL}">Strona główna</a>
            </li>
            <li>
                <c:url var="registerPageURL" value="/register"/>
                <a href="${registerPageURL}">Rejestracja</a>
            </li>
        </ul>
    </div>
</nav>

