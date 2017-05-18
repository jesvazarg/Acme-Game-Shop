<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="${banners}" id="banner" class="displaytag" pagesize="5" keepStatus="true" requestURI="${requestURI}">
	
	<spring:message code="banner.picture" var="pictureHeader" />
	<display:column title="${pictureHeader}">
		<a href="${banner.picture}"><img src="${banner.picture}" style = "max-width: 100 px; max-height: 100px;"/></a>
	</display:column>
	
	
	<spring:message code="banner.edit" var="editHeader" />
	<display:column title="${editHeader}">
		<a href="banner/administrator/edit.do?bannerId=${banner.id}"><jstl:out value="${editHeader}" /></a>
	</display:column>
	
	<spring:message code="banner.delete" var="deleteHeader" />
	<display:column title="${deleteHeader}" >
		<a href="banner/administrator/delete.do?bannerId=${banner.id}"><jstl:out value="${deleteHeader}" /></a>
	</display:column>
	
</display:table>

<acme:button code="banner.new" url="banner/administrator/create.do"/>
