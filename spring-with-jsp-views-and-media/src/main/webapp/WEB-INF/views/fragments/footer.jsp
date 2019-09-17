<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.LocalDate"%>

<%--
    Ta strona trafi jako zawartość stopki dla naszej strony.

    Jej zawartość będzie umieszczona wewnątrz tagów <footer></footer>
--%>

<div>
    <p>&copy; Michał Kupisiński, ${LocalDate.now().getYear()}</p>
</div>