<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div>
	<ul>
		<li>
			<b><spring:message code="receipt.moment"/>:</b>
			<fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${receipt.moment}" />
		</li>
	</ul>
</div>

<display:table name="${orderedGames}" id="game" class="displaytag" pagesize="5" keepStatus="true" requestURI="${requestURI}">
	<acme:column code="game.title" property="title" sortable="false"/>
	<acme:column code="game.price" property="price" sortable="true"/>
</display:table>

<display:table name="total" id="row" class="displaytag" pagesize="5" keepStatus="true" requestURI="${requestURI}">
	
	<spring:message code="receipt.subtotal" var="subtotalHeader" />
	<display:column title="${subtotalHeader}">
		<jstl:out value="${total*0.79}" />
	</display:column>
	
	<spring:message code="receipt.iva" var="ivaHeader" />
	<display:column title="${ivaHeader}">
		<jstl:out value="(21%) ${total*0.21}" />
	</display:column>
	
	<spring:message code="receipt.total" var="totalHeader" />
	<display:column title="${totalHeader}">
		<jstl:out value="${total}" />
	</display:column>
	
</display:table>
			
<br/>
<acme:cancel url="receipt/customer/list.do" code="receipt.cancel" />

