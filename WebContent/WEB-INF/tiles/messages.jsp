<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/javascript">

	$(document).ready(onLoad);
	
	function onLoad() {
		getData();
		window.setInterval(getData, 10000);
	}
	
	function getData() {
		$.ajax({
			url: "<c:url value="/getmessages" />",
			dataType: 'json',
			asnyc: false,
			success: showMessages
		});
	}
	
	function showMessages(data) {
		$("#messages").html("");
		for (var i = 0; i < data.messages.length; i++) {
			var message = data.messages[i];
			
			var messageDiv = document.createElement("div");
			messageDiv.setAttribute("class", "message");
			
			var subjectSpan = document.createElement("span");
			subjectSpan.setAttribute("class", "subject");
			subjectSpan.appendChild(document.createTextNode(message.subject));
			
			var contentSpan = document.createElement("span");
			contentSpan.setAttribute("class", "messageContent");
			contentSpan.appendChild(document.createTextNode(message.content));
			
			var nameSpan = document.createElement("span");
			nameSpan.setAttribute("class", "messageName");
			nameSpan.appendChild(document.createTextNode(message.name + " (" + message.email + ")"));
			
			messageDiv.appendChild(subjectSpan);
			messageDiv.appendChild(contentSpan);
			messageDiv.appendChild(nameSpan);
			$("#messages").append(messageDiv);
		}
	}
	
</script>

<h2 align="center">Messages</h2>
<div id="messages">
</div>