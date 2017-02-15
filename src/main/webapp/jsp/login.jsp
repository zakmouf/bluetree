<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<table>
	<tr>
		<td>New user ?</td>
	</tr>
	<tr>
		<td>Click <a href="<c:url value="/signin.htm"/>">here</a> to register.</td>
	</tr>
</table>
<br/>
<form action="<c:url value="/login.htm"/>" method="post">
<table>
	<tr>
		<td colspan="2">Please authenticate :</td>
	</tr>
	<tr>
		<td>User :</td>
		<td><input type="text" name="login" value="<c:out value="${loginForm.login}"/>" /></td>
	</tr>
	<tr>
		<td>Password :</td>
		<td><input type="password" name="password" /></td>
	</tr>
	<tr>
		<td colspan="2"><input type="submit" value="Log in" /></td>
	</tr>
</table>
</form>

<c:import url="/jsp/common/error.jsp">
	<c:param name="formName">loginForm</c:param>
</c:import>

<c:import url="/jsp/common/footer.jsp" />
