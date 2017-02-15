<%@ include file="/jsp/admin/common/taglibs.jsp"%>

<c:import url="/jsp/admin/common/header.jsp" />

<c:import url="/jsp/admin/common/menu.jsp" />

<table>
	<tr>
		<td>Edit profile</td>
	</tr>
</table>

<form action="<c:url value="/admin/profile/edit.htm?profile=${param.profile}"/>" method="post">

<table>
	<tr>
		<td>Name : </td>
		<td><input type="text" name="name" value="<c:out value="${profileForm.name}"/>" /></td>
	</tr>
</table>

<table>
	<tr>
		<td><input type="submit" value="Ok" /></td>
	</tr>
</table>

</form>

<c:import url="/jsp/admin/common/error.jsp">
	<c:param name="formName">profileForm</c:param>
</c:import>

<c:import url="/jsp/admin/common/footer.jsp" />
