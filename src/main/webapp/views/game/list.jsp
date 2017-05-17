<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<display:table name="games" id="game" requestURI="game/list.do" class="displaytag">
	

	
	<acme:column code="game.title" property="title"/>
	<acme:column code="game.description" property="description"/>
	<acme:column code="game.picture" property="picture"/>
	<acme:column code="game.age" property="age"/>
	<acme:column code="game.price" property="price"/>
	<display:column>
		<a href="game/display.do?gameId=${game.id}"><spring:message
		code="game.display"/>
		</a>
	</display:column>
	
</display:table>




