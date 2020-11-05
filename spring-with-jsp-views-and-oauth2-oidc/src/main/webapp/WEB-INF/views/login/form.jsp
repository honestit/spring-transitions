<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Logowanie</title>
    <jsp:include page="/WEB-INF/views/fragments/head.jsp"/>
</head>
<body class="has-navbar-fixed-top">
<header>
    <jsp:include page="/WEB-INF/views/fragments/menu.jsp"/>
</header>
<section class="section">
    <div class="container">
        <h1 class="title">
            Logowanie
        </h1>
        <h2 class="subtitle">
            Zaloguj się, aby uzyskać pełen dostęp do aplikacji
        </h2>
    </div>
</section>
<section class="section">
    <div class="container">
        <div class="columns">
            <div class="column">
                <form method="post">
                    <div class="field">
                        <label class="label" for="username">Nazwa użytkownika</label>
                        <div class="control has-icons-left">
                            <input class="input" type="text" id="username" name="username"/>
                            <span class="icon is-small is-left">
                            <i class="fas fa-user"></i>
                        </span>
                            <p class="help">Podaj nazwę użytkownika</p>
                        </div>
                    </div>
                    <div class="field">
                        <label class="label" for="password">Hasło</label>
                        <div class="control has-icons-left">
                            <input class="input" type="password" id="password" name="password"/>
                            <span class="icon is-small is-left">
                            <i class="fas fa-lock"></i>
                        </span>
                            <p class="help">Podaj hasło</p>
                        </div>
                    </div>
                    <div class="field is-grouped">
                        <div class="control">
                            <button class="button is-success is-link" type="submit">Zaloguj</button>
                        </div>
                        <div class="control">
                            <button class="button is-text" type="reset">Wyczyść</button>
                        </div>
                        <sec:csrfInput/>
                    </div>
                </form>
            </div>
            <div class="column">
                <c:choose>
                    <c:when test="${param.error != null}">
                        <div class="content">
                            <h2 class="subtitle">
                                Błąd logowania
                            </h2>
                            <p>
                                <span class="tag is-danger">!</span>
                                <span>Błędne dane logowania</span>
                            </p>
                        </div>
                    </c:when>
                    <c:when test="${param.logout != null}">
                        <div class="content">
                            <h2 class="subtitle">
                                Zostałeś poprawnie wylogowany
                            </h2>
                        </div>
                    </c:when>
                </c:choose>
            </div>
            <div class="column"></div>
        </div>
        <div class="columns">
            <div class="column">
                <p class="buttons">
                    <a href="/oauth2/authorization/facebook">
                        <button class="button is-medium">
                        <span class="icon is-medium">
                            <i class="fab fa-facebook"></i>
                        </span>
                            <span>Zaloguj z Facebook</span>
                        </button>
                    </a>
                    <a href="/oauth2/authorization/google">
                        <button class="button is-medium">
                        <span class="icon is-medium">
                            <i class="fab fa-google"></i>
                        </span>
                            <span>Zaloguj z Google</span>
                        </button>
                    </a>
                </p>
            </div>
        </div>
    </div>
</section>
<footer class="footer">
    <jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</footer>
</body>
</html>
