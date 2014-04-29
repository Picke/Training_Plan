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
	<h1>${title}</h1>
	<h1>${message}</h1>

	<c:url value="/j_spring_security_logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>

		<h2>
			Welcome : ${pageContext.request.userPrincipal.name} | <a href="info"> Info</a>
			<br>
			<a href="javascript:formSubmit()"> Logout</a>
		</h2>

</body>
</html>