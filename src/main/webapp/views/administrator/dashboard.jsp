<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('ADMIN')">
	
	<fieldset>
		<legend id="title" class="dashLegend"><b><spring:message code="admin.dashboard.c1"/></b></legend>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.c1_1"/></b></legend>
		<jstl:forEach var="gameC1_1" items="${c1_1}">
			<ul><li>
					<b><spring:message code="admin.dashboard.title"/>:</b>
					<jstl:out value="${gameC1_1.title}"/>
			</li></ul>
		</jstl:forEach>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.c1_2"/></b></legend>
		<jstl:forEach var="gameC1_2" items="${c1_2}">
			<ul><li>
					<b><spring:message code="admin.dashboard.title"/>:</b>
					<jstl:out value="${gameC1_2.title}"/>
			</li></ul>
		</jstl:forEach>
	</fieldset>
	
	<br/>
	<fieldset>
		<legend id="title" class="dashLegend"><b><spring:message code="admin.dashboard.c2"/></b></legend>
		<display:table name="c2" id="customer" requestURI="${requestURI}" pagesize="5" class="displaytag">
			<acme:column code="admin.dashboard.actor.name" property="name" sortable="true"/>
			<acme:column code="admin.dashboard.actor.surname" property="surname"/>
			<acme:column code="admin.dashboard.actor.email" property="email"/>
			<acme:column code="admin.dashboard.actor.phone" property="phone"/>
		</display:table>
	</fieldset>
	
	<br/>
	<fieldset>
		<legend id="title" class="dashLegend"><b><spring:message code="admin.dashboard.c3"/></b></legend>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.c3_1"/></b></legend>
		<display:table name="c3_1" id="developer" requestURI="${requestURI}" pagesize="5" class="displaytag">
			<acme:column code="admin.dashboard.actor.name" property="name" sortable="true"/>
			<acme:column code="admin.dashboard.actor.surname" property="surname"/>
			<acme:column code="admin.dashboard.actor.email" property="email"/>
			<acme:column code="admin.dashboard.actor.phone" property="phone"/>
			<acme:column code="admin.dashboard.developer.company" property="company" sortable="true"/>
		</display:table>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.c3_2"/></b></legend>
		<display:table name="c3_2" id="developer" requestURI="${requestURI}" pagesize="5" class="displaytag">
			<acme:column code="admin.dashboard.actor.name" property="name" sortable="true"/>
			<acme:column code="admin.dashboard.actor.surname" property="surname"/>
			<acme:column code="admin.dashboard.actor.email" property="email"/>
			<acme:column code="admin.dashboard.actor.phone" property="phone"/>
			<acme:column code="admin.dashboard.developer.company" property="company" sortable="true"/>
		</display:table>
	</fieldset>
	
	<br/>
	<fieldset>
		<legend id="title" class="dashLegend"><b><spring:message code="admin.dashboard.c4"/></b></legend>
		<jstl:forEach var="gameC4" items="${c4}">
			<ul><li>
					<b><spring:message code="admin.dashboard.title"/>:</b>
					<jstl:out value="${gameC4.title}"/>
			</li></ul>
		</jstl:forEach>
	</fieldset>
	
	<br/>
	<fieldset>
		<legend id="title" class="dashLegend"><b><spring:message code="admin.dashboard.c5"/></b></legend>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.c5_1"/></b></legend>
		<jstl:forEach var="gameC5_1" items="${c5_1}">
			<ul><li>
					<b><spring:message code="admin.dashboard.title"/>:</b>
					<jstl:out value="${gameC5_1.title}"/>
			</li></ul>
		</jstl:forEach>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.c5_2"/></b></legend>
		<jstl:forEach var="gameC5_2" items="${c5_2}">
			<ul><li>
					<b><spring:message code="admin.dashboard.title"/>:</b>
					<jstl:out value="${gameC5_2.title}"/>
			</li></ul>
		</jstl:forEach>
	</fieldset>
	
	<br/>
	<fieldset>
		<legend id="title" class="dashLegend"><b><spring:message code="admin.dashboard.c6"/></b></legend>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.c6_1"/></b></legend>
		<jstl:if test="${c6_1.isEmpty()}">
			<spring:message code="admin.dashboard.empty"/>
		</jstl:if>
		<jstl:forEach var="gameC6_1" items="${c6_1}">
			<ul><li>
					<b><spring:message code="admin.dashboard.title"/>:</b>
					<jstl:out value="${gameC6_1.title}"/>
			</li></ul>
		</jstl:forEach>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.c6_2"/></b></legend>
		<jstl:if test="${c6_2.isEmpty()}">
			<spring:message code="admin.dashboard.empty"/>
		</jstl:if>
		<jstl:forEach var="gameC6_2" items="${c6_2}">
			<ul><li>
					<b><spring:message code="admin.dashboard.title"/>:</b>
					<jstl:out value="${gameC6_2.title}"/>
			</li></ul>
		</jstl:forEach>
	</fieldset>
	
	<br/>
	<fieldset>
		<legend id="title" class="dashLegend"><b><spring:message code="admin.dashboard.c7"/></b></legend>
		<display:table name="c7" id="developer" requestURI="${requestURI}" pagesize="5" class="displaytag">
			<acme:column code="admin.dashboard.actor.name" property="name" sortable="true"/>
			<acme:column code="admin.dashboard.actor.surname" property="surname"/>
			<acme:column code="admin.dashboard.actor.email" property="email"/>
			<acme:column code="admin.dashboard.actor.phone" property="phone"/>
			<acme:column code="admin.dashboard.developer.company" property="company" sortable="true"/>
		</display:table>
	</fieldset>
	
	<br/>
	<fieldset>
	<legend id="title" class="dashLegend"><b><spring:message code="admin.dashboard.c8"/></b></legend>
		<ul>
			<li>
				<jstl:out value="${c8}" />
			</li>
		</ul>
	</fieldset>
	
	<br/>
	<fieldset>
		<legend id="title" class="dashLegend"><b><spring:message code="admin.dashboard.c9"/></b></legend>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.c9_1"/></b></legend>
		<jstl:if test="${c9_1.isEmpty()}">
			<spring:message code="admin.dashboard.empty"/>
		</jstl:if>
		<jstl:forEach var="gameC9_1" items="${c9_1}">
			<ul><li>
					<b><spring:message code="admin.dashboard.name"/>:</b>
					<jstl:out value="${gameC9_1.name}"/>
			</li></ul>
		</jstl:forEach>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.c9_2"/></b></legend>
		<jstl:if test="${c9_2.isEmpty()}">
			<spring:message code="admin.dashboard.empty"/>
		</jstl:if>
		<jstl:forEach var="gameC9_2" items="${c9_2}">
			<ul><li>
					<b><spring:message code="admin.dashboard.name"/>:</b>
					<jstl:out value="${gameC9_2.name}"/>
			</li></ul>
		</jstl:forEach>
	</fieldset>
	
	<br/>
	<fieldset>
		<legend id="title" class="dashLegend"><b><spring:message code="admin.dashboard.c10"/></b></legend>
		<display:table name="c10" id="par" requestURI="${requestURI}" pagesize="5" class="displaytag">
			<spring:message code="admin.dashboard.title" var="titleHeader" />
			<display:column title="${titleHeader}" >
				<jstl:out value="${par[0]}"/>
			</display:column>
			
			<spring:message code="admin.dashboard.ratio" var="ratioHeader" />
			<display:column title="${ratioHeader}" >
				<jstl:out value="${par[1]}%"/>
			</display:column>
		</display:table>
	</fieldset>
	
	
	<br/>
	<fieldset>
		<legend id="title" class="dashLegend"><b><spring:message code="admin.dashboard.b1"/></b></legend>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.b1_1"/></b></legend>
		<jstl:if test="${b1_1.isEmpty()}">
			<spring:message code="admin.dashboard.empty"/>
		</jstl:if>
		<jstl:forEach var="gameB1_1" items="${b1_1}">
			<ul><li>
					<b><spring:message code="admin.dashboard.title"/>:</b>
					<jstl:out value="${gameB1_1.title}"/>
			</li></ul>
		</jstl:forEach>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.b1_2"/></b></legend>
		<jstl:if test="${b1_2.isEmpty()}">
			<spring:message code="admin.dashboard.empty"/>
		</jstl:if>
		<jstl:forEach var="gameB1_2" items="${b1_2}">
			<ul><li>
					<b><spring:message code="admin.dashboard.title"/>:</b>
					<jstl:out value="${gameB1_2.title}"/>
			</li></ul>
		</jstl:forEach>
	</fieldset>
	
	<br/>
	<fieldset>
		<legend id="title" class="dashLegend"><b><spring:message code="admin.dashboard.b2"/></b></legend>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.b2_1"/></b></legend>
		<display:table name="b2_1" id="developer" requestURI="${requestURI}" pagesize="5" class="displaytag">
			<acme:column code="admin.dashboard.actor.name" property="name" sortable="true"/>
			<acme:column code="admin.dashboard.actor.surname" property="surname"/>
			<acme:column code="admin.dashboard.actor.email" property="email"/>
			<acme:column code="admin.dashboard.actor.phone" property="phone"/>
			<acme:column code="admin.dashboard.developer.company" property="company" sortable="true"/>
		</display:table>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.b2_2"/></b></legend>
		<display:table name="b2_2" id="developer" requestURI="${requestURI}" pagesize="5" class="displaytag">
			<acme:column code="admin.dashboard.actor.name" property="name" sortable="true"/>
			<acme:column code="admin.dashboard.actor.surname" property="surname"/>
			<acme:column code="admin.dashboard.actor.email" property="email"/>
			<acme:column code="admin.dashboard.actor.phone" property="phone"/>
			<acme:column code="admin.dashboard.developer.company" property="company" sortable="true"/>
		</display:table>
	</fieldset>
	
	<br/>
	<fieldset>
		<legend id="title" class="dashLegend"><b><spring:message code="admin.dashboard.b3"/></b></legend>
			<ul>
				<li>
					<b><spring:message code="admin.dashboard.max"/>:</b>
					<jstl:out value="${b3[0]}"/>
				</li>
				<li>
					<b><spring:message code="admin.dashboard.avg"/>:</b>
					<jstl:out value="${b3[1]}"/>
				</li>
				<li>
					<b><spring:message code="admin.dashboard.min"/>:</b>
					<jstl:out value="${b3[2]}"/>
				</li>
			</ul>
	</fieldset>
	
	<br/>
	<fieldset>
		<legend id="title" class="dashLegend"><b><spring:message code="admin.dashboard.a1"/></b></legend>
			<ul>
				<li>
					<b><spring:message code="admin.dashboard.min"/>:</b>
					<jstl:out value="${a1[0]}"/>
				</li>
				<li>
					<b><spring:message code="admin.dashboard.avg"/>:</b>
					<jstl:out value="${a1[1]}"/>
				</li>
				<li>
					<b><spring:message code="admin.dashboard.max"/>:</b>
					<jstl:out value="${a1[2]}"/>
				</li>
			</ul>
	</fieldset>
	
	<br/>
	<fieldset>
		<legend id="title" class="dashLegend"><b><spring:message code="admin.dashboard.a2"/></b></legend>
			<ul>
				<li>
					<b><spring:message code="admin.dashboard.min"/>:</b>
					<jstl:out value="${a2[0]}"/>
				</li>
				<li>
					<b><spring:message code="admin.dashboard.avg"/>:</b>
					<jstl:out value="${a2[1]}"/>
				</li>
				<li>
					<b><spring:message code="admin.dashboard.max"/>:</b>
					<jstl:out value="${a2[2]}"/>
				</li>
			</ul>
	</fieldset>
	
	<br/>
	<fieldset>
		<legend id="title" class="dashLegend"><b><spring:message code="admin.dashboard.a3"/></b></legend>
			<ul>
				<li>
					<b><spring:message code="admin.dashboard.max"/>:</b>
					<jstl:out value="${a3[0]}"/>
				</li>
				<li>
					<b><spring:message code="admin.dashboard.avg"/>:</b>
					<jstl:out value="${a3[1]}"/>
				</li>
				<li>
					<b><spring:message code="admin.dashboard.min"/>:</b>
					<jstl:out value="${a3[2]}"/>
				</li>
			</ul>
	</fieldset>
	
</security:authorize>