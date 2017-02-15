<%@ include file="/jsp/admin/common/taglibs.jsp"%>

<c:import url="/jsp/admin/common/header.jsp" />

<c:import url="/jsp/admin/common/menu.jsp" />

<table>
	<tr>
		<td><a href="<c:url value="/admin/user/identity.htm?user=${param.user}"/>">Take identity</a></td>
	</tr>
</table>

<table>
	<tr>
		<td>Login :</td>
		<td><c:out value="${user.login}" /></td>
	</tr>
	<tr>
		<td>Name :</td>
		<td><c:out value="${user.login}" /></td>
	</tr>
	<tr>
		<td>Company :</td>
		<td><c:out value="${user.company}" /></td>
	</tr>
	<tr>
		<td>Email :</td>
		<td><c:out value="${user.email}" /></td>
	</tr>
	<tr>
		<td>Phone :</td>
		<td><c:out value="${user.phone}" /></td>
	</tr>
	<tr>
		<td>Address :</td>
		<td><c:out value="${user.address}" /></td>
	</tr>
	<tr>
		<td>Zip :</td>
		<td><c:out value="${user.zip}" /></td>
	</tr>
	<tr>
		<td>City :</td>
		<td><c:out value="${user.city}" /></td>
	</tr>
	<tr>
		<td>Country :</td>
		<td><c:out value="${user.country}" /></td>
	</tr>
</table>

<table>
	<tr>
		<td><a href="<c:url value="/admin/user/profile.htm?user=${param.user}"/>">Profiles</a></td>
	</tr>
</table>

<c:if test="${not empty profiles}">

	<display:table name="profiles">
		<display:column property="name" title="Name" />
	</display:table>

</c:if>

<c:import url="/jsp/admin/common/footer.jsp" />
