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
			<b><spring:message code="game.developer"/>:</b>
			<a href="profile/display.do?actorId=${game.developer.id}"><jstl:out value="${game.developer.name}" /></a>
		</li>
		
		<li>
			<b><spring:message code="game.title"/>:</b>
			<jstl:out value="${game.title}" />
		</li>
		
		<li>
			<b><spring:message code="game.description"/>:</b>
			<jstl:out value="${game.description}"/>
		</li>
		
		<li>
			<img src="${game.picture}" style = "max-width: 400 px; max-height: 400px;"/>
		</li>
		
		<li>
			<b><spring:message code="game.age" />:</b>
			<jstl:out value="${game.age}" />
		</li>
		
		<li>
			<b><spring:message code="game.price" />:</b>
			<jstl:out value="${game.price}" />
		</li>
		
		<li>
			
			<b><spring:message code="game.categories" />:</b>
			<ul>
				<jstl:forEach var="category" items="${game.categories}">
					<li><jstl:out value="${category.name}" /></li>
				</jstl:forEach>
			</ul>
			
		</li>


		
	</ul>

<security:authorize access="hasRole('DEVELOPER')">
	<jstl:if test="${isOwner==true}">
		<acme:button url="game/developer/edit.do?gameId=${game.id}" code="game.edit"/>
	</jstl:if>
</security:authorize>

<security:authorize access="hasRole('DEVELOPER')">
	<jstl:if test="${isOwner==true}">
		<acme:button url="game/developer/delete.do?gameId=${game.id}" code="game.delete" />
	</jstl:if>
</security:authorize>
	
</div>
<h3><spring:message code="game.reviews"/></h3>
<display:table name="${reviews}" id="review" class="displaytag" pagesize="5" keepStatus="true" requestURI="${requestURI}">
	
	<acme:column code="review.title" property="title" sortable="true"/>
	<acme:column code="review.moment" property="moment" sortable="true"/>
	<acme:column code="review.score" property="score" sortable="true"/>
	
	<spring:message code="review.critic" var="criticHeader" />
	<display:column title="${criticHeader}">
		<a href="profile/display.do?actorId=${review.critic.id}"><jstl:out value="${review.critic.name}" /></a>
	</display:column>
	
	<spring:message code="review.display" var="displayHeader" />
	<display:column title="${displayHeader}" >
		<a href="review/display.do?reviewId=${review.id}"><spring:message code="review.display"/></a>
	</display:column>
	
</display:table>

<security:authorize access="hasRole('CRITIC')">
	<acme:button code="game.newReview" url="review/critic/create.do?gameId=${game.id}"/>
</security:authorize>

<h3><spring:message code="game.comments"/></h3>
<display:table name="${game.comments}" id="comment" class="displaytag" pagesize="5" keepStatus="true" requestURI="${requestURI}">
	
	<acme:column code="review.title" property="title" sortable="false"/>
	<acme:column code="review.moment" property="moment" sortable="false"/>
	<acme:column code="review.score" property="score" sortable="true"/>
	
	<spring:message code="review.display"/>
	<display:column title="${displayHeader}">
		<a href="comment/customer/display.do?commentId=${comment.id}"><spring:message code="review.display"/></a>
	</display:column>
	
</display:table>

<security:authorize access="hasRole('CUSTOMER')">

<acme:button url="comment/customer/create.do?gameId=${game.id}" code="game.comment.create"/>
<br/>

<jstl:if test="${canAddToShoppingcart==true}">
<acme:button url="shoppingCart/customer/addGame.do?gameId=${game.id}" code="game.add.shoppingcart"/>
</jstl:if>
</security:authorize>

<acme:button url="game/list.do?" code="game.goBack" />