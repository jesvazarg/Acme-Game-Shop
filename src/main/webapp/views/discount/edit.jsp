<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form method="post" action="discount/administrator/edit.do" modelAttribute="discount" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="code" />
	<form:hidden path="used" />
	
	<acme:input code="discount.percentage" path="percentage" />
	
	<jstl:if test="${discount.id != 0}">
		<acme:submit name="save" code="discount.save" />
	</jstl:if>
	<jstl:if test="${discount.id == 0}">
		<acme:submit name="register" code="discount.save" />
	</jstl:if>
	<acme:cancel url="discount/administrator/list.do" code="discount.cancel" />
</form:form>
