<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="criticForm">
	<acme:input code="critic.username" path="username" />
	<acme:password code="critic.password" path="password" />
	<acme:password code="critic.confirmPassword" path="confirmPassword" />
	<acme:input code="critic.name" path="name" />
	<acme:input code="critic.surname" path="surname" />
	<acme:input code="critic.email" path="email" />
	<acme:input code="critic.phone" path="phone" />
	<acme:input code="critic.magazine" path="magazine" />
	
	<jstl:if test="${requestURI == 'critic/administrator/create.do'}">
		<br/>
		<form:checkbox path="isAgree"/>
		<form:label path="isAgree">
			<spring:message code="critic.isAgree" />
			<a href="misc/conditions.do" target="_blank"><spring:message code="critic.conditions" /></a>
		</form:label>
		<br/>
	</jstl:if>
	
	<acme:submit name="save" code="critic.save" />
	<jstl:if test="${requestURI == 'critic/administrator/create.do'}">
		<acme:cancel url="" code="critic.cancel" />
	</jstl:if>
	<jstl:if test="${requestURI == 'critic/edit.do'}">
		<acme:cancel url="profile/myProfile.do" code="critic.cancel" />
	</jstl:if>
</form:form>

