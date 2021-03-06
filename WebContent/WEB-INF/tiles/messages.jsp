<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script type="text/javascript">

	var timer;
	$(document).ready(onLoad);
	
	function onLoad() {
		getData();
		startTimer();
	}
	
	function startTimer() {
		timer = window.setInterval(getData, 5000);
	}
	
	function stopTimer() {
		window.clearInterval(timer);
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
			nameSpan.appendChild(document.createTextNode(message.name + " ("));
			var link = document.createElement("a");
			link.setAttribute("class", "replylink");
			link.setAttribute("href", "#");
			link.setAttribute("onclick", "showReply(" + i + ")");
			link.appendChild(document.createTextNode(message.email));
			nameSpan.appendChild(link);
			nameSpan.appendChild(document.createTextNode(")"));

			var replyArea = document.createElement("textarea");
			replyArea.setAttribute("class", "replyarea");
			replyArea.setAttribute("id", "textbox" + i);
			
			var replyButton = document.createElement("input");
			replyButton.setAttribute("class", "replybutton");
			replyButton.setAttribute("type", "button");
			replyButton.setAttribute("value", "Reply");
			replyButton.onclick = function(j, name, email, subject) {
				return function() {
					sendMessage(j, name, email, subject);
				}
			}(i, message.name, message.email, message.subject);
			
			var alertSpan = document.createElement("span");
			alertSpan.setAttribute("class", "alert");
			alertSpan.setAttribute("id", "alert" + i);
			//alertSpan.appendChild(document.createTextNode("Message sent"));
			
			var replyForm = document.createElement("form");
			replyForm.setAttribute("class", "replyform");
			replyForm.setAttribute("id", "form" + i);
			replyForm.appendChild(replyArea);
			replyForm.appendChild(replyButton);
			
			messageDiv.appendChild(subjectSpan);
			messageDiv.appendChild(contentSpan);
			messageDiv.appendChild(nameSpan);
			messageDiv.appendChild(alertSpan);
			messageDiv.appendChild(replyForm);
			$("#messages").append(messageDiv);
		}
	}
	
	function showReply(i) {
		stopTimer();
		$("#form" + i).toggle();
	}
	
	function sendMessage(i, name, email, subject) {
		//alert($("#textbox" + i).val() + " : " + name + " : " + email);
		$.ajax({
			"type": 'POST',
			"url": '<c:url value="/sendmessage" />',
			"data": JSON.stringify({"messageId":i, "text": $("#textbox" + i).val(), "name": name, "email": email, "subject": subject}),
			"success": success,
			"error": error,
			contentType: "application/json",
			dataType: "json",
			headers: {'X-CSRF-Token': $('meta[name="_csrf"]').attr('content')}
		});
	}
	
	function success(data) {
		$("#form" + data.messageId).toggle();
		$("#alert" + data.messageId).text("Message sent.");		
		startTimer();
	}
	
	function error(data) {
		alert("failure");
	}
	
</script>

	<meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <%-- <meta name="_csrf_header" content="${_csrf.headerName}"/> --%>

<h2 align="center">Messages</h2>
<div id="messages">
</div>