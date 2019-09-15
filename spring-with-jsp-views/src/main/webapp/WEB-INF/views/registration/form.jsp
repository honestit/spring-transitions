<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Rejestracja</title>
</head>
<body>
<header>
    <h1>Rejestracja</h1>
</header>
<section>
    <h1>Wypełnij dane rejestracyjne</h1>
    <div>
        <form method="post">
            <c:if test="${not empty errors}">
                <fieldset>
                    <legend>Błędy</legend>
                    <c:forEach items="${errors}" var="error" varStatus="stat">
                        <p>
                                ${stat.count}. ${error}
                        </p>
                    </c:forEach>
                </fieldset>
            </c:if>
            <fieldset>
                <legend>Dane użytkownika</legend>
                <p>
                    <label for="username">Nazwa użytkownika: </label><input id="username"
                                                                            type="text"
                                                                            name="username"/>
                </p>

                <p>
                    <label for="email">Email: </label><input id="email" type="text"
                                                             name="email"/>
                </p>

                <p>
                    <label for="password">Hasło: </label><input id="password" type="text"
                                                                name="password"/>
                </p>

                <p>
                    <label for="rePassword">Powtórz hasło: </label><input id="rePassword"
                                                                          type="text"
                                                                          name="rePassword"/>
                </p>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </fieldset>
            <fieldset>
                <legend>Akcje</legend>
                <p>
                    <input type="submit" value="Zarejestruj"/>
                </p>
            </fieldset>
        </form>
    </div>
</section>
<footer></footer>
</body>
</html>
