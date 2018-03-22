<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<h2>Send message</h2>

<sf:form method="post" commandName="message">

	<input type="hidden" name="_flowExecutionKey"
		value="${flowExecutionKey}" />
	<input type="hidden" name="_eventId" value="sendmess" />

	<table class="offertable">
		<tr>
			<td class="label">Your Name:</td>
			<td><sf:input class="control" type="text" name="name"
					path="name" placeholder="Enter your name" value="${fromName}" />
				<div class="error">
					<sf:errors path="name"></sf:errors>
				</div></td>
		</tr>
		<tr>
			<td class="label">Your Email:</td>
			<td><sf:input class="control" type="text" name="email"
					path="email" placeholder="Enter your email" value="${fromEmail}" />
				<div class="error">
					<sf:errors path="email"></sf:errors>
				</div></td>
		</tr>
		<tr>
			<td class="label">Subject:</td>
			<td><sf:input class="control" type="text" name="subject"
					path="subject" placeholder="Enter subject" />
				<div class="error">
					<sf:errors path="subject"></sf:errors>
				</div></td>
		</tr>
		<tr>
			<td class="label">Your message:</td>
			<td><sf:textarea class="control" name="content" path="content"
					rows="4" cols="50" placeholder="Enter your content here" />
				<div class="error">
					<sf:errors path="content"></sf:errors>
				</div></td>
		</tr>
		<tr>
			<td class="label"></td>
			<td><input class="control" type="submit" value="Send Message"></td>
		</tr>
	</table>
</sf:form>