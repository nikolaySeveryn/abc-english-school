<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Persons</h1>
	<c:url var="addUrl" value="/main/books/add" />
	<a href="${addUrl}">Add</a>
	<table style="border: 1px solid; width: 500px; text-align: center">
		<thead style="background: #fcf">
			<tr>
				<th>Author</th>
				<th>Name</th>
				<th colspan="2"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${books}" var="book">
				<c:url var="editUrl" value="/main/books/edit?id=${book.id}" />
				<c:url var="deleteUrl" value="/main/book/delete?id=${book.id}" />
				<tr>
					<td><c:out value="${person.author}" /></td>
					<td><c:out value="${person.name}" /></td>
					<td><a href="${editUrl}">Edit</a></td>
					<td><a href="${deleteUrl}">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<c:if test="${empty books}">
 		There are currently no persons in the list. <a href="${addUrl}">Add</a> a person.
	</c:if>
</body>
</html>