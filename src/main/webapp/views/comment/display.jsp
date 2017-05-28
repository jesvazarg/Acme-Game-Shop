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
			<b><spring:message code="comment.description"/>:</b>
			<jstl:out value="${comment.description}" />
		</li>
		<security:authorize access="isAuthenticated()">
		<li>
			<b><spring:message code="comment.customer"/>:</b>
			<a href="profile/display.do?actorId=${comment.customer.id}"><jstl:out value="${comment.customer.name}" /></a>
		</li>
		</security:authorize>
		<security:authorize access="!isAuthenticated()">
		<li>
			<b><spring:message code="comment.customer"/>:</b>
			<jstl:out value="${comment.customer.name}" />
		</li>
		</security:authorize>
	</ul>
</div>

<security:authorize access="isAuthenticated()">
	<acme:button url="game/display.do?gameId=${game.id}" code="comment.back" />
</security:authorize>

<security:authorize access="!isAuthenticated()">
	<acme:button url="game/displayNotAuth.do?gameId=${game.id}" code="comment.back" />
</security:authorize>