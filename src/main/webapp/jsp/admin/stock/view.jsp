<%@ include file="/jsp/admin/common/taglibs.jsp"%>

<c:import url="/jsp/admin/common/header.jsp" />

<c:import url="/jsp/admin/common/menu.jsp" />

<table>
	<tr>
		<td><a href="<c:url value="/admin/stock/price.htm?stock=${param.stock}"/>">Populate</a></td>
		<td><a href="<c:url value="/admin/stock/clear.htm?stock=${param.stock}"/>">Clear</a></td>
		<td><a href="<c:url value="/admin/stock/edit.htm?stock=${param.stock}"/>">Edit</a></td>
		<td><a href="<c:url value="/admin/stock/delete.htm?stock=${param.stock}"/>">Delete</a></td>
	</tr>
</table>

<table>
	<tr>
		<td><c:out value="${stockViewForm.viewStock.symbol}" /> ( <c:out value="${stockViewForm.viewStock.name}" /> )</td>
	</tr>
</table>

<table>
	<tr>
		<td>Count :</td>
		<td><c:out value="${stockViewForm.count}" /></td>
	</tr>
	<tr>
		<td>First date :</td>
		<td><fmt:formatDate value="${stockViewForm.firstDate}" pattern="yyyy-MM-dd" /></td>
	</tr>
	<tr>
		<td>Last date :</td>
		<td><fmt:formatDate value="${stockViewForm.lastDate}" pattern="yyyy-MM-dd" /></td>
	</tr>
</table>

<table>
	<tr>
		<td><img src="<c:url value="/chart.htm?name=stock.view&width=400&height=200"/>" width="400" height="200" /></td>
	</tr>
</table>

<table>
	<tr>
		<td>View</td>
	</tr>
</table>

<form action="<c:url value="/admin/stock/view.htm?stock=${param.stock}"/>" method="post">

<table>
	<tr>
		<td>From :</td>
		<td><input type="text" name="fromDate" value="<c:out value="${stockViewForm.fromDate}" />" /></td>
	</tr>
	<tr>
		<td>To :</td>
		<td><input type="text" name="toDate" value="<c:out value="${stockViewForm.toDate}" />" /></td>
	</tr>
</table>

<table>
	<tr>
		<td><input type="submit" value="Ok" /></td>
	</tr>
</table>

</form>

<c:import url="/jsp/admin/common/error.jsp">
	<c:param name="formName">stockViewForm</c:param>
</c:import>

<c:import url="/jsp/admin/common/footer.jsp" />
