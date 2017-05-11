<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="${reviews}" id="review" class="displaytag" pagesize="5" keepStatus="true" requestURI="${requestURI}">
	
	<acme:column code="review.title" property="title" sortable="true"/>
	<acme:column code="review.moment" property="moment" sortable="true"/>
	<acme:column code="review.score" property="score" sortable="true"/>
	<acme:column code="review.draft" property="draft" sortable="true"/>
	
	<spring:message code="review.game" var="gameHeader" />
	<display:column title="${gameHeader}">
		<a href="game/display.do?gameId=${review.game.id}"><jstl:out value="${review.game.title}" /></a>
	</display:column>
	
	<spring:message code="review.display" var="displayHeader" />
	<display:column title="${displayHeader}" >
		<a href="review/display.do?reviewId=${review.id}"><spring:message code="review.display"/></a>
	</display:column>
	
</display:table>
