<%@ include file="/jsp/admin/common/taglibs.jsp"%>

<c:import url="/jsp/admin/common/header.jsp" />

<c:import url="/jsp/admin/common/menu.jsp" />

<table>
	<tr>
		<td>Populate</td>
	</tr>
</table>

<form action="<c:url value="/admin/stock/price.htm?stock=${param.stock}"/>" method="post">

<table>
	<tr>
		<td><textarea rows="15" cols="40" name="text"><c:out value="${stockPriceForm.text}" /></textarea></td>
	</tr>
</table>

<table>
	<tr>
		<td><input type="submit" value="Ok" /></td>
	</tr>
</table>

</form>

<c:import url="/jsp/admin/common/error.jsp">
	<c:param name="formName">stockPriceForm</c:param>
</c:import>

<c:import url="/jsp/admin/common/footer.jsp" />
