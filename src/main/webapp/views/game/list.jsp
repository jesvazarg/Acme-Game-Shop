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

<security:authorize access="isAuthenticated()">
<spring:message code="game.searchText"/>
<input type="text" value="" id="textSearch" />
<input type="button" id="buttonSearch"
value="<spring:message code="game.searchButton"/>" />
<br>
</security:authorize>

<br>
<security:authorize access="isAuthenticated()">
<spring:message code="game.filterByCategory"/>
<select id="categorySelected" name="category">
    <jstl:forEach items="${categories}" var="category">
        <option value="${category.id}">${category.name}</option>
    </jstl:forEach>
</select>
<input type="number" min=0 value="" id="minNumber" />
<input type="number" min =0 value="" id="maxNumber" />
<input type="button" id="Filter"
value="<spring:message code="game.filterButton"/>" />
</security:authorize>

<script type="text/javascript">
	$(document).ready(function(){
		$(document).on('click',"#buttonSearch",function(game){
			game.preventDefault();
			window.location.replace('game/search.do?key=' + $("#textSearch").val());
		});
		
		
		$(document).on('click',"#Filter",function(game){
			game.preventDefault();
			var e = document.getElementById("categorySelected");
			var strUser = e.options[e.selectedIndex].text;
			console.log("o dubi du");
			if(!$("#minNumber").val() && !$("#maxNumber").val()){
				window.location.replace('game/filter.do?key='+ strUser);
			}else if(!$("#minNumber").val()){
				window.location.replace('game/filter.do?key='+ strUser + '&' + 'maxPrice=' + $("#maxNumber").val());
			}else if(!$("#maxNumber").val()){
				window.location.replace('game/filter.do?key='+ strUser + '&' + 'minPrice=' + $("#minNumber").val());
			}else{
				window.location.replace('game/filter.do?key='+ strUser + '&' + 'minPrice=' + $("#minNumber").val() + '&' + 'maxPrice=' + $("#maxNumber").val());
			}
			
		});

	});
	

	
		
</script>




<display:table name="games" id="game" requestURI="game/list.do" class="displaytag">
	

	
	<acme:column code="game.title" property="title"/>
	<spring:message code="game.picture" var="pictureHeader" />
	<display:column title="${pictureHeader}">
		<a href="${game.picture}"><img src="${game.picture}" style = "max-width: 100 px; max-height: 100px;"/></a>
	</display:column>
	<acme:column code="game.age" property="age"/>
	<acme:column code="game.price" property="price"/>
	<display:column>
		<security:authorize access="isAuthenticated()">
			<a href="game/display.do?gameId=${game.id}"><spring:message code="game.display"/></a>
		</security:authorize>
		<security:authorize access="!isAuthenticated()">
			<a href="game/displayNotAuth.do?gameId=${game.id}"><spring:message code="game.display"/></a>
		</security:authorize>
	</display:column>
	
		<security:authorize access="hasRole('CUSTOMER')">
			<display:column style="width:150px">
					<jstl:set var="haveLike" value="${false}"/>
					<jstl:set var="haveDislike" value="${false}"/>
					<jstl:if test="${!principal.senses.isEmpty()}">
						<jstl:forEach var="sense" items="${principal.senses}">
							<jstl:choose>
								<jstl:when test="${senseList.contains(sense)}">
									<jstl:if test="${sense.game.id==game.id}">
										<jstl:if test="${sense.like==true}">
											<jstl:set var="haveLike" value="${true}"/>
										</jstl:if>
										<jstl:if test="${sense.like==false}">
											<jstl:set var="haveDislike" value="${true}"/>
										</jstl:if>
									</jstl:if>
								</jstl:when>
							</jstl:choose>
						</jstl:forEach>
					</jstl:if>
					<jstl:if test="${haveLike==false}">
						<li><a href="sense/customer/like.do?gameId=${game.id}">
							<spring:message code="game.like"/>
						</a></li>
					</jstl:if>
					<jstl:if test="${haveDislike==false}">
						<li><a href="sense/customer/dislike.do?gameId=${game.id}">
							<spring:message code="game.dislike"/></a></li>
					</jstl:if>
			</display:column>
			
	</security:authorize>
	
</display:table>




