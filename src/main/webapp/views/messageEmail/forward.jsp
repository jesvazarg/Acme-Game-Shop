<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form method="post" action="messageEmail/forward.do" modelAttribute="messageEmail" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="sender" />
	<form:hidden path="deletedForSender"/>
	<form:hidden path="deletedForRecipient"/>

	<%-- <acme:select items="${recipients}" itemLabel="name" code="messageEmail.recipients" path="recipients" /> --%>
	
	<form:label path="recipient">
		<spring:message code="messageEmail.recipient" />
	</form:label>	
	<form:select id="${id}" path="recipient">
		<%-- <form:option value="0" label="----" /> --%>		
		<form:options items="${recipients}" itemValue="id" itemLabel="name" />
	</form:select>
	<form:errors path="${path}" cssClass="error" />
	
	<acme:input code="messageEmail.subject" path="subject" />
	
	<acme:textarea code="messageEmail.text" path="text" />
	
	<acme:input code="messageEmail.attachments" path="attachments" />
	
	
	<acme:submit name="save" code="messageEmail.save" />
	
	<acme:cancel url="messageEmail/listOut.do" code="messageEmail.cancel" />
	
</form:form>