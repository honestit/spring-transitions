<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--
    Ta strona reprezentuje nasze menu główne, które pojawiać się będzie na każdej
    innej stronie.

    Jej treść będzie osadzona wewnątrz tagu <header></header>.
--%>

<%-- Adresy wykorzystywane na strone --%>
<c:url var="homePageURL" value="/"/>
<c:url var="registerPageURL" value="/register"/>
<c:url var="loginPageURL" value="/login"/>
<c:url var="logoutPageURL" value="/logout"/>
<c:url var="accountPageURL" value="/account"/>

<%-- Koniec sekcji adresów --%>
<nav class="navbar is-fixed-top" role="navigation" aria-label="main navigation">
    <div class="container">
        <div class="navbar-brand">
            <a class="navbar-item" href="${homePageURL}">
                <img src="/media/images/logo.png"
                     alt="HonestIT Logo"/>
            </a>
        </div>

        <div class="navbar-menu">
            <div class="navbar-start">
                <a class="navbar-item" href="${homePageURL}">
                    Strona główna
                </a>
                <%-- Tutaj pozostałe linki, które chcemy mieć widoczne --%>

                <div class="navbar-item has-dropdown is-hoverable">
                    <a class="navbar-link">
                        Więcej
                    </a>

                    <div class="navbar-dropdown">
                        <a class="navbar-item">
                            Link do niczego
                        </a>
                        <%-- Tutaj kolejne linki w menu dodatkowym --%>
                    </div>
                </div>
            </div>

            <div class="navbar=end">
                <div class="navbar-item">
                    <div class="buttons">
                        <sec:authorize access="isAnonymous()">
                            <a class="button is-primary" href="${registerPageURL}">
                                <strong>Zarejestruj</strong>
                            </a>
                            <a class="button is-success" href="${loginPageURL}">
                                <strong>Zaloguj</strong>
                            </a>
                        </sec:authorize>
                        <sec:authorize access="isAuthenticated()">
                            <a class="button is-primary" href="${accountPageURL}">
                                <strong>Twoje konto</strong>
                            </a>
                            <form method="post" action="/logout">
                                <button class="button is-link" type="submit">Wyloguj</button>
                                <sec:csrfInput/>
                            </form>
                        </sec:authorize>
                    </div>
                </div>
            </div>
        </div>
    </div>
</nav>

