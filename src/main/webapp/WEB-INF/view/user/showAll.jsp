<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<table>
		<c:forEach items="${list}" var="u">
			<tr>
				<td>${u.id}</td>
				<td>${u.password }</td>
				<td>${u.username }</td>


			</tr>
		</c:forEach>
	</table>

</body>

</html>