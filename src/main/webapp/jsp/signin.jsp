<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<table>
	<tr>
		<td>Back to <a href="<c:url value="/login.htm"/>">Login</a>.</td>
	</tr>
</table>
<br />
<form action="<c:url value="/signin.htm"/>" method="post">
<table>
	<tr>
		<td colspan="2">Account information :</td>
	</tr>
	<tr>
		<td>User :</td>
		<td><input type="text" name="login" value="<c:out value="${signinForm.login}"/>" /></td>
	</tr>
	<tr>
		<td>Password :</td>
		<td><input type="password" name="password" /></td>
	</tr>
	<tr>
		<td>Confirm password :</td>
		<td><input type="password" name="password2" /></td>
	</tr>
	<tr>
		<td colspan="2">Personal information :</td>
	</tr>
	<tr>
		<td>Name :</td>
		<td><input type="text" name="name" value="<c:out value="${signinForm.name}"/>" /></td>
	</tr>
	<tr>
		<td>Company :</td>
		<td><input type="text" name="company" value="<c:out value="${signinForm.company}"/>" /></td>
	</tr>
	<tr>
		<td>Email :</td>
		<td><input type="text" name="email" value="<c:out value="${signinForm.email}"/>" /></td>
	</tr>
	<tr>
		<td>Phone :</td>
		<td><input type="text" name="phone" value="<c:out value="${signinForm.phone}"/>" /></td>
	</tr>
	<tr>
		<td>Address :</td>
		<td><input type="text" name="address" value="<c:out value="${signinForm.address}"/>" /></td>
	</tr>
	<tr>
		<td>Zip :</td>
		<td><input type="text" name="zip" value="<c:out value="${signinForm.zip}"/>" /></td>
	</tr>
	<tr>
		<td>City :</td>
		<td><input type="text" name="city" value="<c:out value="${signinForm.city}"/>" /></td>
	</tr>
	<tr>
		<td>Country :</td>
		<td><input type="text" name="country" value="<c:out value="${signinForm.country}"/>" /></td>
	</tr>
	<tr>
		<td colspan="2"><input type="submit" value="Create Account" /></td>
	</tr>
</table>
</form>

<c:import url="/jsp/common/error.jsp">
	<c:param name="formName">signinForm</c:param>
</c:import>

<c:import url="/jsp/common/footer.jsp" />
