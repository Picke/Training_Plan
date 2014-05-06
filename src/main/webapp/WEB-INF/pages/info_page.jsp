<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<html>
<body>
    <span style="float: right">
        <a href="?lang=en">en</a>
        |
        <a href="?lang=uk">uk</a>
    </span>
	<h1> <spring:message code="label.name"/> : ${name}</h1>
	<h1> <spring:message code="label.surname"/> : ${surname}</h1>

    <h2>
        <a	href="events">Events</a>
    </h2>

	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<h2>
			<a	href="home">Home</a>
		</h2>
	</c:if>


</body>
</html>