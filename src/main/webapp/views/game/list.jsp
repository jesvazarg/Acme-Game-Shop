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
	<option value="-1" label="----" />	
    <jstl:forEach items="${categories}" var="category">
        <option value="${category.id}">${category.name}</option>
    </jstl:forEach>
</select>
<spring:message code="game.minimunPrice"/>
<input type="number" min=0 value="" id="minNumber" />
<spring:message code="game.maximunPrice"/>
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
			if(!!strUser){
				if(!$("#minNumber").val() && !$("#maxNumber").val()){
					window.location.replace('game/filter.do?key='+ strUser);
				}else if(!$("#minNumber").val()){
					window.location.replace('game/filter.do?key='+ strUser + '&' + 'maxPrice=' + $("#maxNumber").val());
				}else if(!$("#maxNumber").val()){
					window.location.replace('game/filter.do?key='+ strUser + '&' + 'minPrice=' + $("#minNumber").val());
				}else{
					window.location.replace('game/filter.do?key='+ strUser + '&' + 'minPrice=' + $("#minNumber").val() + '&' + 'maxPrice=' + $("#maxNumber").val());
				}
			}else{
				if(!$("#minNumber").val() && !$("#maxNumber").val()){
					window.location.replace('game/list.do');
				}else if(!$("#minNumber").val()){
					window.location.replace('game/filter.do?maxPrice=' + $("#maxNumber").val());
				}else if(!$("#maxNumber").val()){
					window.location.replace('game/filter.do?minPrice=' + $("#minNumber").val());
				}else{
					window.location.replace('game/filter.do?minPrice=' + $("#minNumber").val() + '&' + 'maxPrice=' + $("#maxNumber").val());
				}
			}
			
			
		});

	});
	

	
		
</script>




<display:table name="games" id="game" requestURI="game/list.do" class="displaytag">
	
	<jstl:set var="style" value="none"/>
	<jstl:if test="${game[1] == true}">
		<jstl:set var="style" value="font-weight:bold;text-shadow: 0.1em 0.1em 0.2em darkgrey"/>
	</jstl:if>

	<spring:message code="game.title" var="titleHeader" />
	<display:column title="${titleHeader}" style="${style}">
				<jstl:out value="${game[0].title}"/>
	</display:column>
	
	<spring:message code="game.picture" var="pictureHeader" />
	<display:column title="${pictureHeader}" style="${style}">
		<a href="${game[0].picture}"><img src="${game[0].picture}" style = "max-width: 100 px; max-height: 100px;"/></a>
	</display:column>
	
	<spring:message code="game.age" var="ageHeader" />
	<display:column title="${ageHeader}" style="${style}">
				<jstl:out value="${game[0].age}"/>
	</display:column>
	
	<spring:message code="game.price" var="priceHeader" />
	<display:column title="${priceHeader}" style="${style}">
				<jstl:out value="${game[0].price}"/>
	</display:column>
	
	
	<display:column style="${style}">
		<security:authorize access="isAuthenticated()">
			<a href="game/display.do?gameId=${game[0].id}"><spring:message code="game.display"/></a>
		</security:authorize>
		<security:authorize access="!isAuthenticated()">
			<a href="game/displayNotAuth.do?gameId=${game[0].id}"><spring:message code="game.display"/></a>
		</security:authorize>
	</display:column>
	
		<security:authorize access="hasRole('CUSTOMER')">
			<display:column style="${style}">
					<jstl:set var="haveLike" value="${false}"/>
					<jstl:set var="haveDislike" value="${false}"/>
					<jstl:if test="${!principal.senses.isEmpty()}">
						<jstl:forEach var="sense" items="${principal.senses}">
							<jstl:choose>
								<jstl:when test="${senseList.contains(sense)}">
									<jstl:if test="${sense.game.id==game[0].id}">
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
						<li><a href="sense/customer/like.do?gameId=${game[0].id}">
							<spring:message code="game.like"/>
						</a></li>
					</jstl:if>
					<jstl:if test="${haveDislike==false}">
						<li><a href="sense/customer/dislike.do?gameId=${game[0].id}">
							<spring:message code="game.dislike"/></a></li>
					</jstl:if>
			</display:column>
			
	</security:authorize>
	
</display:table>




