<%@ include file="/jsp/admin/common/taglibs.jsp"%>

<c:import url="/jsp/admin/common/header.jsp" />

<c:import url="/jsp/admin/common/menu.jsp" />

<table>
	<tr>
		<td>New stock</td>
	</tr>
</table>

<form action="<c:url value="/admin/stock/new.htm"/>" method="post">

<table>
	<tr>
		<td>Symbol : </td>
		<td><input type="text" name="symbol" value="<c:out value="${stockForm.symbol}"/>" /></td>
	</tr>
	<tr>
		<td>Name : </td>
		<td><input type="text" name="name" value="<c:out value="${stockForm.name}"/>" /></td>
	</tr>
</table>

<table>
	<tr>
		<td><input type="submit" value="Ok" /></td>
	</tr>
</table>

</form>

<c:import url="/jsp/admin/common/error.jsp">
	<c:param name="formName">stockForm</c:param>
</c:import>

<c:import url="/jsp/admin/common/footer.jsp" />
