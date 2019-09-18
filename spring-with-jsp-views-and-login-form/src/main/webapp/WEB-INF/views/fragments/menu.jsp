<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        <a class="button is-primary" href="${registerPageURL}">
                            <strong>Zarejestruj</strong>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</nav>

