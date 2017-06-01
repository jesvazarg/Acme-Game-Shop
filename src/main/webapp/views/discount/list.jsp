<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="${discounts}" id="discount" class="displaytag" pagesize="10" keepStatus="true" requestURI="${requestURI}">
	
	<jstl:set var="style" value="none"/>
	<jstl:if test="${discount.used == false}">
		<jstl:set var="style" value="font-weight:bold;text-shadow: 0.1em 0.1em 0.2em darkgrey"/>
	</jstl:if>
	
	<acme:column code="discount.code" property="code" sortable="false" style="${style}"/>
	<acme:column code="discount.percentage" property="percentage" sortable="true" style="${style}"/>
	
	<spring:message code="discount.used" var="usedHeader" />
	<display:column title="${usedHeader}" sortable="true" style="${style}">
		<jstl:if test="${discount.used}">
			<spring:message code="discount.yes" />
		</jstl:if>
		<jstl:if test="${!discount.used}">
			<spring:message code="discount.no" />
		</jstl:if>
	</display:column>
	
	<spring:message code="discount.edit" var="editHeader" />
	<display:column title="${editHeader}" style="${style}">
		<jstl:if test="${!discount.used}">
			<a href="discount/administrator/edit.do?discountId=${discount.id}"><jstl:out value="${editHeader}" /></a>
		</jstl:if>
	</display:column>
	
	<spring:message code="discount.delete" var="deleteHeader" />
	<display:column title="${deleteHeader}" style="${style}">
		<a href="discount/administrator/delete.do?discountId=${discount.id}"><jstl:out value="${deleteHeader}" /></a>
	</display:column>
	
</display:table>

<acme:button code="discount.new" url="discount/administrator/create.do"/>
