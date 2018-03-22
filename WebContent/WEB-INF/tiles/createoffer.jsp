<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/javascript">
$(document).ready(onReady);

function onReady() {
	$("#deleteButton").click(onDeleteButtonClick);
}

function onDeleteButtonClick(event) {
	var doDelete = confirm("Are you sure you want to delete this offer?");
	if (doDelete == false) {
		event.preventDefault();
	}
}


</script>

<sf:form method="post"
	action="${pageContext.request.contextPath}/docreate"
	commandName="offer">

	<sf:input type="hidden" path="id" />

	<table class="offertable">
		<tr>
			<td class="label">Your Offer:</td>
			<td><sf:textarea class="control" name="text" path="text"
					rows="4" cols="50" placeholder="Enter your offer here"></sf:textarea><br />
				<sf:errors path="text" cssClass="error"></sf:errors></td>
		</tr>
		<tr>
			<td class="label"></td>
			<td><input name="save" class="control" type="submit"
				value="Save Offer"> <c:if test="${ offer.id != 0 }">
					<input id="deleteButton" name="delete" class="delete control" type="submit"
						value="Delete Offer">
				</c:if></td>
		</tr>
	</table>
</sf:form>
