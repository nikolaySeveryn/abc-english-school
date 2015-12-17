<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Add Book</h1>
	<c:url var="saveUrl" value="/main/books/add?id=${personAttribute.id}" />
	<form:form modelAttribute="personAttribute" method="POST"
		action="${saveUrl}">
		<table>
			<tr>
				<td><form:label path="id">Id:</form:label></td>
				<td><form:input path="id" disabled="true" /></td>
			</tr>

			<tr>
				<td><form:label path="author">Author:</form:label></td>
				<td><form:input path="author" /></td>
			</tr>

			<tr>
				<td><form:label path="name">Name</form:label></td>
				<td><form:input path="name" /></td>
			</tr>
		</table>

		<input type="submit" value="Save" />
	</form:form>
</body>
</html>