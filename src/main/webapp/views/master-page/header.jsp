<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href=""><img src="images/logo.jpg" alt="Acme-Game-Shop Co., Inc." style = "max-width: 853px; max-height: 177px;"/></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a href="administrator/dashboard.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
			<li><a href="category/administrator/list.do"><spring:message code="master.page.administrator.category.list" /></a></li>
			<li><a href="banner/administrator/list.do"><spring:message code="master.page.administrator.banner.list" /></a></li>
			<li><a href="discount/administrator/list.do"><spring:message code="master.page.administrator.discount.list" /></a></li>
			<li><a href="critic/administrator/create.do"><spring:message code="master.page.administrator.critic.register" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('CRITIC')">
			<li><a href="review/critic/list.do"><spring:message code="master.page.critic.review.list" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('DEVELOPER')">
			<li><a class="fNiv"><spring:message	code="master.page.game" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="game/listMyGames.do"><spring:message code="master.page.game.listMyGames" /></a></li>
					<li><a href="game/developer/create.do"><spring:message code="master.page.game.create" /></a></li>
					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv"><spring:message	code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/create.do"><spring:message code="master.page.customer.register" /></a></li>
					<li><a href="developer/create.do"><spring:message code="master.page.developer.register" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a href="shoppingCart/customer/display.do"><spring:message	code="master.page.customer.shoppingCart" /></a></li>
			<li><a href="receipt/customer/list.do"><spring:message	code="master.page.customer.receipt.list" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a href="game/listNotAuth.do"><spring:message	code="master.page.game.list" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li><a href="game/list.do"><spring:message	code="master.page.game.list" /></a></li>
			
			<li><a class="fNiv"><spring:message	code="master.page.messages" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="messageEmail/listIn.do"><spring:message code="master.page.messagesEmail.received" /></a></li>
					<li><a href="messageEmail/listOut.do"><spring:message code="master.page.messagesEmail.sent" /></a></li>
					
				</ul>
			</li>
			
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="profile/myProfile.do"><spring:message code="master.page.profile.display" /></a></li>
					<security:authorize access="hasRole('CUSTOMER')">
							<li><a href="customer/edit.do"><spring:message code="master.page.edit.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('CRITIC')">
							<li><a href="critic/edit.do"><spring:message code="master.page.edit.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('DEVELOPER')">
							<li><a href="developer/edit.do"><spring:message code="master.page.edit.profile" /></a></li>
					</security:authorize>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
			
			
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

