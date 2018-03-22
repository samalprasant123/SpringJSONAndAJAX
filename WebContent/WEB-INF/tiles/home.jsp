<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	
	<h2 align="center">Current Offers</h2>
	<table class="offerDisplayTable" align="center">
		<thead>
			<tr>
				<th class="offerDisplayTh">NAME</th>
				<th class="offerDisplayTh">CONTACT</th>
				<th class="offerDisplayTh">TEXT</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="offer" items="${ offers }">
				<tr>
					<td class="offerDisplayTd">${offer.user.name}</td>
					<td class="offerDisplayTd">
						<a href="<c:url value='/message?uid=${offer.username}' />">Contact</a>
					</td>
					<td class="offerDisplayTd">${offer.text}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<c:choose>
		<c:when test="${ hasOffer }">
			<p>
				<a href="${pageContext.request.contextPath}/createoffer">Edit/Delete your current Offer</a>
			</p>
		</c:when>
		<c:otherwise>
			<p>
				<a href="${pageContext.request.contextPath}/createoffer">Create Offer</a>
			</p>
		</c:otherwise>
	</c:choose>
	
	<sec:authorize access="hasRole('ADMIN')">
		<p>		
			<a href="<c:url value="/admin" />">Admin</a>
		</p>
	</sec:authorize>
