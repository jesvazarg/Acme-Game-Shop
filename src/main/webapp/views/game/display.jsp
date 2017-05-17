<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>

<div>
	<ul>
		<li>
			<b><spring:message code="game.title"/>:</b>
			<jstl:out value="${game.title}" />
		</li>
		
		<li>
			<b><spring:message code="game.description"/>:</b>
			<jstl:out value="${game.description}"/>
		</li>
		
		<li>
			<b><spring:message code="game.picture"/>:</b>
			<jstl:out value="${game.picture}"/>
		</li>
		
		<li>
			<b><spring:message code="game.age" />:</b>
			<jstl:out value="${game.age}" />
		</li>
		
		<li>
			<b><spring:message code="game.price" />:</b>
			<jstl:out value="${game.price}" />
		</li>
		


		
	</ul>
	
</div>

<security:authorize access="hasRole('DEVELOPER')">
<form:form method="post" action="developer/game/delete.do" modelAttribute="game" >

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="title" />
	<form:hidden path="description"/>
	<form:hidden path="picture"/>
	<form:hidden path="age" />
	<form:hidden path="price" />
	<form:hidden path="developer" />
	<form:hidden path="sellsNumber" />
	<form:hidden path="reviews" />
	<form:hidden path="categories" />
	<form:hidden path="comments" />
	<form:hidden path="senses" />
	
	
	<jstl:if test="${game.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="game.delete" />"
			onclick="return confirm('<spring:message code="game.confirm.delete" />')" />&nbsp;
	</jstl:if>
</form:form>
</security:authorize>
