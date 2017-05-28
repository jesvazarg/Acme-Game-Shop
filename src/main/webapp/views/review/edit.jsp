<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form method="post" action="review/critic/edit.do" modelAttribute="review" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="draft" />
	<form:hidden path="game" />
	<form:hidden path="critic" />
	
	<acme:input code="review.title" path="title" />
	<acme:textarea code="review.description" path="description" />
	
	<form:label path="score">
		<spring:message code="review.score" />
	</form:label>
	<form:select path="score" >
		<form:option value="-1" label="----" /> 
		<form:option value="0" label="0" />
		<form:option value="1" label="1" />
		<form:option value="2" label="2" />
		<form:option value="3" label="3" />
		<form:option value="4" label="4" />
		<form:option value="5" label="5" />
		<form:option value="6" label="6" />
		<form:option value="7" label="7" />
		<form:option value="8" label="8" />
		<form:option value="9" label="9" />
		<form:option value="10" label="10" />
	</form:select>
	<form:errors path="score" cssClass="error" />
	<br/>
	

	<jstl:if test="${review.id != 0}">
		<acme:submit name="save" code="review.save" />
		<acme:submit name="delete" code="review.delete" />
		<acme:cancel url="review/display.do?reviewId=${review.id}" code="review.cancel" />
	</jstl:if>
	<jstl:if test="${review.id == 0}">
		<acme:submit name="register" code="review.save" />
		<jstl:if test="${review.game != null}">
			<acme:cancel url="game/display.do?gameId=${review.game.id}" code="review.cancel" />
		</jstl:if>
		
		<jstl:if test="${review.game == null}">
			<acme:cancel url="game/list.do" code="review.cancel" />
		</jstl:if>
	</jstl:if>
</form:form>
