<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Events</title>
</head>
<body>
    <c:forEach var="event" items="${eventList}">
        <h1> <spring:message code="label.eventId"/> : ${event.id}</h1>
        <h1> <spring:message code="label.eventSummary"/> : ${event.summary}</h1>
        <br>
    </c:forEach>
</body>
</html>