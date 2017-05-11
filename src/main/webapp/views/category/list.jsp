<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="${categories}" id="category" class="displaytag" pagesize="10" keepStatus="true" requestURI="${requestURI}">
	
	<jstl:set var="isEmpty" value="${false}"/>
	<jstl:if test="${category.games.size() == 0}">
		<jstl:set var="isEmpty" value="${true}"/>
	</jstl:if>
	
	<acme:column code="category.name" property="name" sortable="true"/>
	
	<spring:message code="category.edit" var="editHeader" />
	<display:column title="${editHeader}">
		<jstl:if test="${isEmpty}">
			<a href="category/administrator/edit.do?categoryId=${category.id}"><jstl:out value="${editHeader}" /></a>
		</jstl:if>
	</display:column>
	
	<spring:message code="category.delete" var="deleteHeader" />
	<display:column title="${deleteHeader}" >
		<jstl:if test="${isEmpty}">
			<a href="category/administrator/delete.do?categoryId=${category.id}"><jstl:out value="${deleteHeader}" /></a>
		</jstl:if>
	</display:column>
	
</display:table>

<acme:button code="category.new" url="category/administrator/create.do"/>
