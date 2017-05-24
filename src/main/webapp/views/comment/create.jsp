<%--
 * action-2.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<form:form method="post" action="comment/customer/create.do" modelAttribute="comment" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="customer" />
	<form:hidden path="game" />
	
	<form:label path="score">
		<spring:message code="comment.score" />
	</form:label>
	<form:select path="score" >
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
	
	<acme:input code="comment.title" path="title" />
	<acme:textarea code="comment.description" path="description" />
	
	<acme:submit name="save" code="comment.save" />
	<jstl:if test="${comment.game!=null }">
	<acme:cancel url="game/display.do?gameId=${comment.game.id}" code="comment.cancel" />
	</jstl:if>
	
	<jstl:if test="${comment.game==null }">
	<acme:cancel url="game/list.do" code="comment.cancel" />
	</jstl:if>
</form:form>


