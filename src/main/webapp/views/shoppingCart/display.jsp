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



<jstl:if test="${not empty games }">
	<b><spring:message code="shoppingCart.total"/>: </b> 
	<h2><jstl:out value="${total}" /></h2>
	<spring:message code="discount.code"/>
	<input type="text" value="" id="code" />
	</br>
	<input type="button" id="buttonBuy"
	value="<spring:message code="shoppingCart.buy"/>" />
	
	<script type="text/javascript">
		$(document).ready(function(){
			$("#buttonBuy").click(function(){
				window.location.replace('shoppingCart/customer/buy.do?code=' + $("#code").val());
			});
			$("#buttonBuy").onsubmit(function(){
				window.location.replace('shoppingCart/customer/buy.do?code=' + $("#code").val());
			});
		});
	</script>
</jstl:if>
<jstl:if test="${empty games }">
	</br>
	<img src="https://media.giphy.com/media/ORTPDqxMQD4I/giphy.gif" style = "max-width: 200 px; max-height: 200px;"/>
</jstl:if>
</br></br>
<jstl:if test="${message.equals('shoppingCart.commit.error.notCreditCard')}">
	<acme:button url="creditCard/create.do" code="shoppingCart.newCreditCard"/>
</jstl:if>
<jstl:if test="${message.equals('shoppingCart.commit.error.validCreditCard')}">
	<acme:button url="creditCard/edit.do" code="shoppingCart.editCreditCard"/>
</jstl:if>
<jstl:if test="${message.equals('shoppingCart.commit.error.invalidDiscunt') || message.equals('shoppingCart.commit.error.discuntUsed')}">
	<img src="https://media.giphy.com/media/10VWDDhFOr9pvO/source.gif" style = "max-width: 200 px; max-height: 200px;"/>
</jstl:if>



