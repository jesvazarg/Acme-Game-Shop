<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>
	<ul>
		<li>
			<b><spring:message code="comment.score"/>:</b>
			<jstl:out value="${comment.score}" />
		</li>
		<li>
			<b><spring:message code="comment.title"/>:</b>
			<jstl:out value="${comment.title}" />
		</li>
		<li>
			<b><spring:message code="comment.score"/>:</b>
			<jstl:out value="${comment.description}" />
		</li>
	</ul>
</div>

<security:authorize access="isAuthenticated()">
	<acme:button url="game/display.do?gameId=+ ${game.id}" code="comment.back" />
</security:authorize>

<security:authorize access="!isAuthenticated()">
	<acme:button url="game/displayNotAuth.do?gameId=+ ${game.id}" code="comment.back" />
</security:authorize>