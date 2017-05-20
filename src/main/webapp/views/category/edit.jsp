<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form method="post" action="category/administrator/edit.do" modelAttribute="category" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="games" />
	
	<acme:input code="category.name" path="name" />
	
	<jstl:if test="${category.id != 0}">
		<acme:submit name="save" code="category.save" />
	</jstl:if>
	<jstl:if test="${category.id == 0}">
		<acme:submit name="register" code="category.save" />
	</jstl:if>
	<acme:cancel url="category/administrator/list.do" code="category.cancel" />
</form:form>
