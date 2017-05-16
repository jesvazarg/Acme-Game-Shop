<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="${discounts}" id="discount" class="displaytag" pagesize="10" keepStatus="true" requestURI="${requestURI}">
	
	<acme:column code="discount.code" property="code" sortable="false"/>
	<acme:column code="discount.percentage" property="percentage" sortable="true"/>
	<acme:column code="discount.used" property="used" sortable="true"/>
	
	<spring:message code="discount.edit" var="editHeader" />
	<display:column title="${editHeader}">
		<a href="discount/administrator/edit.do?discountId=${discount.id}"><jstl:out value="${editHeader}" /></a>
	</display:column>
	
	<spring:message code="discount.delete" var="deleteHeader" />
	<display:column title="${deleteHeader}" >
		<a href="discount/administrator/delete.do?discountId=${discount.id}"><jstl:out value="${deleteHeader}" /></a>
	</display:column>
	
</display:table>

<acme:button code="discount.new" url="discount/administrator/create.do"/>
