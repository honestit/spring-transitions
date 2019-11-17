<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Rejestracja</title>
    <jsp:include page="/WEB-INF/views/fragments/head.jsp"/>
</head>
<body class="has-navbar-fixed-top">
<header>
    <jsp:include page="/WEB-INF/views/fragments/menu.jsp"/>
</header>
<section class="section">
    <div class="container">
        <h1 class="title">
            Rejestracja
        </h1>
        <h2 class="subtitle">
            Zarejestruj swoje konto, aby móc w pełni korzystać ze <strong>Spring
            Transitions</strong>
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
                            <input class="input ${invalidFields.contains('username') ? 'is-danger' : ''}"
                                   type="text" id="username" name="username"/>
                            <span class="icon is-small is-left">
                            <i class="fas fa-user"></i>
                        </span>
                            <p class="help">Podaj nazwę użytkownika</p>
                            <spring:hasBindErrors name="data">
                                <c:forEach items="${errors.getFieldErrors('username')}" var="error">
                                    <p class="help is-danger">${error.defaultMessage}</p>
                                </c:forEach>
                            </spring:hasBindErrors>
                        </div>
                    </div>
                    <div class="field">
                        <label class="label" for="email">Email</label>
                        <div class="control has-icons-left">
                            <input class="input ${invalidFields.contains('email') ? 'is-danger' : ''}" type="text" id="email" name="email"/>
                            <span class="icon is-small is-left">
                            <i class="fas fa-envelope"></i>
                        </span>
                            <p class="help">Podaj adres email</p>
                            <spring:hasBindErrors name="data">
                                <c:forEach items="${errors.getFieldErrors('email')}" var="error">
                                    <p class="help is-danger">${error.defaultMessage}</p>
                                </c:forEach>
                            </spring:hasBindErrors>
                        </div>
                    </div>
                    <div class="field">
                        <label class="label" for="password">Hasło</label>
                        <div class="control has-icons-left">
                            <input class="input ${invalidFields.contains('password') ? 'is-danger' : ''}" type="password" id="password" name="password"/>
                            <span class="icon is-small is-left">
                            <i class="fas fa-lock"></i>
                        </span>
                            <p class="help">Podaj hasło</p>
                            <spring:hasBindErrors name="data">
                                <c:forEach items="${errors.getFieldErrors('password')}" var="error">
                                    <p class="help is-danger">${error.defaultMessage}</p>
                                </c:forEach>
                            </spring:hasBindErrors>
                        </div>
                    </div>
                    <div class="field">
                        <label class="label" for="rePassword">Powtórzone hasło</label>
                        <div class="control has-icons-left">
                            <input class="input ${invalidFields.contains('rePassowrd') ? 'is-danger' : ''}" type="password" id="rePassword" name="rePassword"/>
                            <span class="icon is-small is-left">
                            <i class="fas fa-lock"></i>
                        </span>
                            <p class="help">Podaj ponownie hasło</p>
                            <spring:hasBindErrors name="data">
                                <c:forEach items="${errors.getFieldErrors('rePassword')}"
                                           var="error">
                                    <p class="help is-danger">${error.defaultMessage}</p>
                                </c:forEach>
                            </spring:hasBindErrors>
                        </div>
                    </div>
                    <div class="field is-grouped">
                        <div class="control">
                            <button class="button is-success is-link" type="submit">Zarejestruj
                            </button>
                        </div>
                        <div class="control">
                            <button class="button is-text" type="reset">Wyczyść</button>
                        </div>
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
