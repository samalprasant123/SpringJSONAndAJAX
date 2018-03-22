<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

	<script type="text/javascript">
		$(document).ready(function() {
			document.f.username.focus();
		});
	</script>

	<h3>Login with Username and Password</h3>
	<c:if test="${param.error == true}">
		<p class="error">Incorrect Username or Password</p>
	</c:if>
	<form name="f" action="${pageContext.request.contextPath}/login"
		method="POST">
		<table class="offertable">
			<tr>
				<td>Username:</td>
				<td><input type="text" name="username" value="" />
					<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}" />
				</td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="password" /></td>
			</tr>
			<tr>
				<td>Remember me:</td>
				<td><input type="checkbox" name="remember-me" checked="checked" /></td>
			</tr>
			<tr>
				<td></td>
				<td colspan="2"><input name="submit" type="submit"
					value="Login" /></td>
			</tr>
		</table>
	</form>
	<br>
	<p>
		<a href="${pageContext.request.contextPath}/newaccount">Create
			Account</a>
	</p>
