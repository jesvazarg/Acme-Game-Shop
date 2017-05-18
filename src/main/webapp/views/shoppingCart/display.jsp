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

<display:table name="${games}" id="game" class="displaytag" pagesize="5" keepStatus="true" requestURI="${requestURI}">
	<acme:column code="game.title" property="title" sortable="false"/>
	<acme:column code="game.price" property="price" sortable="true"/>
	<display:column>
		<a href="shoppingCart/customer/removeGame.do?gameId=${game.id}"><spring:message
		code="shoppingCart.remove"/>
		</a>
	</display:column>
</display:table>

<form:form method="post" action="shoppingCart/customer/buy.do" modelAttribute="shoppingCart" >

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="games" />
	
	<jstl:if test="${not empty games }">
		<input type="submit" name="save" value="<spring:message code="shoppingCart.buy" />" />&nbsp; 
	</jstl:if>
</form:form>



