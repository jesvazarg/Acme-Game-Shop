<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('ADMIN')">
	<h2><spring:message code="admin.dashboard.levelC"/></h2>
	
	<fieldset>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.c1"/></b></legend>
		<legend class="dashLegend"><spring:message code="admin.dashboard.c1_1"/></legend>
		<jstl:forEach var="gameC1_1" items="${c1_1}">
			<ul><li>
					<b><spring:message code="admin.dashboard.title"/>:</b>
					<jstl:out value="${gameC1_1.title}"/>
			</li></ul>
		</jstl:forEach>
		<legend class="dashLegend"><spring:message code="admin.dashboard.c1_2"/></legend>
		<jstl:forEach var="gameC1_2" items="${c1_2}">
			<ul><li>
					<b><spring:message code="admin.dashboard.title"/>:</b>
					<jstl:out value="${gameC1_2.title}"/>
			</li></ul>
		</jstl:forEach>
	</fieldset>
	
	<br/>
	<fieldset>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.c3"/></b></legend>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.c3_1"/></b></legend>
		<display:table name="c3_1" id="developer" requestURI="${requestURI}" pagesize="5" class="displaytag">
			<acme:column code="admin.dashboard.actor.name" property="name" sortable="true"/>
			<acme:column code="admin.dashboard.actor.surname" property="surname"/>
			<acme:column code="admin.dashboard.developer.company" property="company" sortable="true"/>
		</display:table>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.c3_2"/></b></legend>
		<display:table name="c3_2" id="developer" requestURI="${requestURI}" pagesize="5" class="displaytag">
			<acme:column code="admin.dashboard.actor.name" property="name" sortable="true"/>
			<acme:column code="admin.dashboard.actor.surname" property="surname"/>
			<acme:column code="admin.dashboard.developer.company" property="company" sortable="true"/>
		</display:table>
	</fieldset>
	
	<br/>
	<fieldset>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.c4"/></b></legend>
		<jstl:forEach var="gameC4" items="${c4}">
			<ul><li>
					<b><spring:message code="admin.dashboard.title"/>:</b>
					<jstl:out value="${gameC4.title}"/>
			</li></ul>
		</jstl:forEach>
	</fieldset>
	
	<br/>
	<fieldset>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.c6"/></b></legend>
		<jstl:if test="${c6.size() > 0}">
			<legend class="dashLegend"><spring:message code="admin.dashboard.c6.mayor"/></legend>
			<ul>
				<li>
					<b><spring:message code="admin.dashboard.title"/>:</b>
					<jstl:out value="${c6[0].title}"/>
				</li>
			</ul>
			<legend class="dashLegend"><spring:message code="admin.dashboard.c6.menor"/></legend>
			<ul>
				<li>
					<b><spring:message code="admin.dashboard.title"/>:</b>
					<jstl:if test="${c6.size() > 1}">
						<jstl:out value="${c6[1].title}"/>
					</jstl:if>
					<jstl:if test="${c6.size() == 1}">
						<jstl:out value="${c6[0].title}"/>
					</jstl:if>
				</li>
			</ul>
		</jstl:if>
		<jstl:if test="${c6.size() == 0}">
			<spring:message code="admin.dashboard.c6.vacio"/>
		</jstl:if>
	</fieldset>
	
	<br/>
	<fieldset><legend class="dashLegend"><b><spring:message code="admin.dashboard.c8"/></b></legend>
		<ul>
			<li>
				<jstl:out value="${c8}" />
			</li>
		</ul>
	</fieldset>
	
	<br/>
	<fieldset>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.c9"/></b></legend>
		<jstl:if test="${c9.size() > 0}">
			<legend class="dashLegend"><spring:message code="admin.dashboard.c9.mayor"/></legend>
			<ul>
				<li>
					<b><spring:message code="admin.dashboard.name"/>:</b>
					<jstl:out value="${c9[0].name}"/>
				</li>
			</ul>
			<legend class="dashLegend"><spring:message code="admin.dashboard.c9.menor"/></legend>
			<ul>
				<li>
					<b><spring:message code="admin.dashboard.name"/>:</b>
					<jstl:if test="${c9.size() > 1}">
						<jstl:out value="${c9[1].name}"/>
					</jstl:if>
					<jstl:if test="${c9.size() == 1}">
						<jstl:out value="${c9[0].name}"/>
					</jstl:if>
				</li>
			</ul>
		</jstl:if>
		<jstl:if test="${c9.size() == 0}">
			<spring:message code="admin.dashboard.c9.vacio"/>
		</jstl:if>
	</fieldset>
	
	
	<br/>
	<h2><spring:message code="admin.dashboard.levelB"/></h2>
	
	<fieldset>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.b1"/></b></legend>
		<jstl:if test="${b1.size() > 0}">
			<legend class="dashLegend"><spring:message code="admin.dashboard.b1.mejor"/></legend>
			<ul>
				<li>
					<b><spring:message code="admin.dashboard.title"/>:</b>
					<jstl:out value="${b1[0].title}"/>
				</li>
			</ul>
			<legend class="dashLegend"><spring:message code="admin.dashboard.b1.peor"/></legend>
			<ul>
				<li>
					<b><spring:message code="admin.dashboard.title"/>:</b>
					<jstl:if test="${b1.size() > 1}">
						<jstl:out value="${b1[1].title}"/>
					</jstl:if>
					<jstl:if test="${b1.size() == 1}">
						<jstl:out value="${b1[0].title}"/>
					</jstl:if>
				</li>
			</ul>
		</jstl:if>
		<jstl:if test="${b1.size() == 0}">
			<spring:message code="admin.dashboard.b1.vacio"/>
		</jstl:if>
	</fieldset>
	
	<br/>
	<fieldset>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.b2"/></b></legend>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.b2_1"/></b></legend>
		<display:table name="b2_1" id="developer" requestURI="${requestURI}" pagesize="5" class="displaytag">
			<acme:column code="admin.dashboard.actor.name" property="name" sortable="true"/>
			<acme:column code="admin.dashboard.actor.surname" property="surname"/>
			<acme:column code="admin.dashboard.developer.company" property="company" sortable="true"/>
		</display:table>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.b2_2"/></b></legend>
		<display:table name="b2_2" id="developer" requestURI="${requestURI}" pagesize="5" class="displaytag">
			<acme:column code="admin.dashboard.actor.name" property="name" sortable="true"/>
			<acme:column code="admin.dashboard.actor.surname" property="surname"/>
			<acme:column code="admin.dashboard.developer.company" property="company" sortable="true"/>
		</display:table>
	</fieldset>
	
	<br/>
	<fieldset>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.b3"/></b></legend>
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
	<h2><spring:message code="admin.dashboard.levelA"/></h2>
	
	<br/>
	<fieldset>
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.a2"/></b></legend>
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
		<legend class="dashLegend"><b><spring:message code="admin.dashboard.a3"/></b></legend>
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