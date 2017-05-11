<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="createCustomerForm">
	<acme:input code="customer.username" path="username" />
	<acme:password code="customer.password" path="password" />
	<acme:password code="customer.confirmPassword" path="confirmPassword" />
	<acme:input code="customer.name" path="name" />
	<acme:input code="customer.surname" path="surname" />
	<acme:input code="customer.email" path="email" />
	<acme:input code="customer.phone" path="phone" />
	<acme:input code="customer.birthdate" path="birthdate" />
	
	<jstl:if test="${requestURI == 'customer/create.do'}">
		<br/>
		<form:checkbox path="isAgree"/>
		<form:label path="isAgree">
			<spring:message code="customer.isAgree" />
			<a href="misc/conditions.do" target="_blank"><spring:message code="customer.conditions" /></a>
		</form:label>
		<br/>
	</jstl:if>
	
	<acme:submit name="save" code="customer.save" />
	<acme:cancel url="" code="customer.cancel" />
</form:form>

