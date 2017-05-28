<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>

<div>
	<ul>
		<li>
			<b><spring:message code="messageEmail.moment"/>:</b>
			<jstl:out value="${messageEmail.moment}" />
		</li>
		
		<li>
			<b><spring:message code="messageEmail.subject"/>:</b>
			<jstl:out value="${messageEmail.subject}"/>
		</li>
		
		<li>
			<b><spring:message code="messageEmail.text"/>:</b>
			<jstl:out value="${messageEmail.text}"/>
		</li>
		
		<li>
			<b><spring:message code="messageEmail.sender" />:</b>
			<jstl:out value="${messageEmail.sender.name}" />
		</li>
		
		<li>
			<b><spring:message code="messageEmail.recipient" />:</b>
			<jstl:out value="${messageEmail.recipient.name}" />
		</li>
		

		
		<li>
			<b><spring:message code="messageEmail.attachments" />:</b>
			<jstl:out value="${messageEmail.attachments}" />
		</li>
		
	</ul>
	
</div>

<jstl:if test="${isRecipient==true}">
	<div>
		<acme:button url="messageEmail/reply.do?messageEmailId=${messageEmail.id}" code="messageEmail.reply"/>
	</div>
</jstl:if>

<jstl:if test="${isRecipient==false}">
	<div>
		<acme:button url="messageEmail/forward.do?messageEmailId=${messageEmail.id}" code="messageEmail.forward"/>
	</div>
</jstl:if>

<div>
		<acme:button url="messageEmail/delete.do?messageEmailId=${messageEmail.id}" code="messageEmail.delete" />
</div>
