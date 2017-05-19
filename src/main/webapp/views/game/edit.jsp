<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form method="post" action="game/developer/edit.do" modelAttribute="game" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="developer" />
	<form:hidden path="comments" />
	<form:hidden path="reviews" />
	<form:hidden path="senses" />
	<form:hidden path="sellsNumber" />
	<form:hidden path="categories" />
	
	<acme:input code="game.title" path="title" />
	<acme:input code="game.description" path="description" />
	<acme:input code="game.picture" path="picture" />
	<acme:input code="game.age" path="age" />
	<acme:input code="game.price" path="price" />
	
	<%-- <form:label path="categories">
		<spring:message code="game.categories"/>
	</form:label>
	<jstl:forEach var="category" items="${categories}">
		<form:checkbox path ="categories" value="${categories}"/><jstl:out value="${category.name}"/>
	</jstl:forEach>
	<form:errors path="categories" cssClass="error"/> --%>
	
	<br/>
	<acme:submit name="save" code="category.save" />
	<jstl:if test="${game.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="game.delete" />"
			onclick="return confirm('<spring:message code="game.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<acme:cancel url="game/display.do?gameId=${game.id}" code="game.cancel" />
</form:form>
