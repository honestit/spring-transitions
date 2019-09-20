<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>${user.username}</title>
    <jsp:include page="/WEB-INF/views/fragments/head.jsp"/>
</head>
<body class="has-navbar-fixed-top">
<header>
    <jsp:include page="/WEB-INF/views/fragments/menu.jsp"/>
</header>
<section class="hero is-bold is-medium">
    <div class="container">
        <div class="hero-body">
            <h1 class="title">
                Witaj ${user.username}
            </h1>
            <h2 class="subtitle">
                Poniżej znajduje się podsumowanie Twoich danych
            </h2>
        </div>
    </div>
</section>
<section class="section">
    <div class="container">
        <div class="content">
            <h2 class="subtitle">
                Twoje dane
            </h2>
        </div>
        <div class="columns">
            <div class="column">
                <c:set var="disabled" value="${edit != null ? false : true}"/>
                <form method="post">
                    <div class="field">
                        <label class="label" for="firstName">Imię</label>
                        <div class="control has-icons-left">
                            <input class="input" type="text" id="firstName" name="firstName"
                                   value="${details.firstName}" ${disabled ? 'disabled' : ''}/>
                            </span>
                            <span class="icon is-small is-left">
                                <i class="fas fa-user"></i>
                            </span>
                            <p class="help">Twoje imię</p>
                        </div>
                    </div>
                    <div class="field">
                        <label class="label" for="lastName">Nazwisko</label>
                        <div class="control has-icons-left">
                            <input class="input" type="text" id="lastName" name="lastName"
                                   value="${details.lastName}" ${disabled ? 'disabled' : ''}/>
                            </span>
                            <span class="icon is-small is-left">
                                <i class="fas fa-user"></i>
                            </span>
                            <p class="help">Twoje nazwisko</p>
                        </div>
                    </div>
                    <div class="field">
                        <label class="label" for="pesel">PESEL</label>
                        <div class="control has-icons-left">
                            <input class="input" type="text" id="pesel" name="pesel"
                                   value="${details.pesel}" ${disabled ? 'disabled' : ''}/>
                            </span>
                            <span class="icon is-small is-left">
                                <i class="fas fa-fingerprint"></i>
                            </span>
                            <p class="help">Twój pesel</p>
                        </div>
                    </div>
                    <div class="field">
                        <label class="label" for="dateOfBirth">Data urodzin</label>
                        <div class="control has-icons-left">
                            <input class="input" type="date" id="dateOfBirth" name="dateOfBirth"
                                   value="${details.dateOfBirth}" ${disabled ? 'disabled' : ''}/>
                            </span>
                            <span class="icon is-small is-left">
                                <i class="fas fa-calendar-day"></i>
                            </span>
                            <p class="help">Twoja data urodzin</p>
                        </div>
                    </div>
                    <div class="field is-grouped">
                        <c:choose>
                            <c:when test="${disabled}">
                                <div class="control">
                                    <button class="button is-success is-link" type="submit"
                                            name="edit">Edytuj
                                    </button>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="control">
                                    <button class="button is-success is-link" type="submit"
                                            name="save">Zapisz
                                    </button>
                                </div>
                                <div class="control">
                                    <button class="button is-success is-link" type="submit"
                                            name="cancel">Anuluj
                                    </button>
                                </div>
                            </c:otherwise>
                        </c:choose>
                        <sec:csrfInput/>
                    </div>
                </form>
            </div>
            <div class="column"></div>
            <div class="column"></div>
        </div>
    </div>
</section>
<footer class="footer">
    <jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</footer>
</body>
</html>
