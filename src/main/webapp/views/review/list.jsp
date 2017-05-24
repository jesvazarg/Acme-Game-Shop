<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="${reviews}" id="review" class="displaytag" pagesize="5" keepStatus="true" requestURI="${requestURI}">
	
	<jstl:set var="style" value="none"/>
	<jstl:if test="${review.draft == false}">
		<jstl:set var="style" value="font-weight:bold;text-shadow: 0.1em 0.1em 0.2em darkgrey"/>
	</jstl:if>
	
	
	<acme:column code="review.title" property="title" sortable="true" style="${style}"/>
	<acme:column code="review.moment" property="moment" sortable="true" style="${style}"/>
	<acme:column code="review.score" property="score" sortable="true" style="${style}"/>
	
	<spring:message code="review.draft" var="draftHeader" />
	<display:column title="${draftHeader}" sortable="true" style="${style}">
		<jstl:if test="${review.draft}">
			<spring:message code="review.yes" />
		</jstl:if>
		<jstl:if test="${!review.draft}">
			<spring:message code="review.no" />
		</jstl:if>
	</display:column>
	
	
	<spring:message code="review.game" var="gameHeader" />
	<display:column title="${gameHeader}" style="${style}">
		<a href="game/display.do?gameId=${review.game.id}"><jstl:out value="${review.game.title}" /></a>
	</display:column>
	
	<spring:message code="review.display" var="displayHeader" />
	<display:column title="${displayHeader}" style="${style}">
		<a href="review/display.do?reviewId=${review.id}"><spring:message code="review.display"/></a>
	</display:column>
	
</display:table>
