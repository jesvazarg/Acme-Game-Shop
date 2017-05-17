<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form method="post" action="developer/game/edit.do" modelAttribute="game" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="developer" />
	<form:hidden path="comments" />
	<form:hidden path="reviews" />
	<form:hidden path="senses" />
	<form:hidden path="sellsNumber" />
	
	<acme:input code="game.title" path="title" />
	<acme:input code="game.description" path="title" />
	<acme:input code="game.picture" path="title" />
	<acme:input code="game.age" path="title" />
	<acme:input code="game.price" path="title" />
	<form:label path="properties">
		<spring:message code="game.categories"/>
	</form:label>
	<jstl:forEach var="categories" items="${categories}">
		<form:checkbox path ="categories" value="${categories}"/><jstl:out value="${category.name}"/>
	</jstl:forEach>
	<form:errors path="categories" cssClass="error"/>
	
	
	<acme:submit name="save" code="category.save" />
	<acme:cancel url="game/developer/list.do" code="game.cancel" />
</form:form>
