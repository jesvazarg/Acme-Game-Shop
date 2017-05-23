<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="createDeveloperForm">
	<acme:input code="developer.username" path="username" />
	<acme:password code="developer.password" path="password" />
	<acme:password code="developer.confirmPassword" path="confirmPassword" />
	<acme:input code="developer.name" path="name" />
	<acme:input code="developer.surname" path="surname" />
	<acme:input code="developer.email" path="email" />
	<acme:input code="developer.phone" path="phone" />
	<acme:input code="developer.company" path="company" />
	
	<jstl:if test="${requestURI == 'developer/create.do'}">
		<br/>
		<form:checkbox path="isAgree"/>
		<form:label path="isAgree">
			<spring:message code="developer.isAgree" />
			<a href="misc/conditions.do" target="_blank"><spring:message code="developer.conditions" /></a>
		</form:label>
		<br/>
	</jstl:if>
	
	<acme:submit name="save" code="developer.save" />
	<acme:cancel url="" code="developer.cancel" />
</form:form>

