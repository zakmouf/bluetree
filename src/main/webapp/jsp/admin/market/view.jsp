<%@ include file="/jsp/admin/common/taglibs.jsp"%>

<c:import url="/jsp/admin/common/header.jsp" />

<c:import url="/jsp/admin/common/menu.jsp" />

<table>
	<tr>
		<td><a href="<c:url value="/admin/market/edit.htm?market=${param.market}"/>">Edit</a></td>
		<td><a href="<c:url value="/admin/market/delete.htm?market=${param.market}"/>">Delete</a></td>
	</tr>
</table>

<table>
	<tr>
		<td>Name :</td>
		<td><c:out value="${market.name}" /></td>
	</tr>
	<tr>
		<td>Riskless :</td>
		<td><c:out value="${market.riskless}" /></td>
	</tr>
	<tr>
		<td>Indice :</td>
		<td><c:out value="${indice.symbol}" /> ( <c:out value="${indice.name}" /> )</td>
	</tr>
</table>

<table>
	<tr>
		<td><a href="<c:url value="/admin/market/profile.htm?market=${param.market}"/>">Profiles</a></td>
	</tr>
</table>

<c:if test="${not empty profiles}">

	<display:table name="profiles">
		<display:column property="name" title="Name" />
	</display:table>

</c:if>

<table>
	<tr>
		<td><a href="<c:url value="/admin/market/stock.htm?market=${param.market}"/>">Stocks</a></td>
	</tr>
</table>

<c:if test="${not empty stocks}">

	<display:table name="stocks" requestURI="/admin/market/view.htm" pagesize="20">
		<display:column property="symbol" title="Symbol" url="/admin/stock/view.htm" paramId="stock" paramProperty="id" />
		<display:column property="name" title="Name" />
	</display:table>

</c:if>

<c:import url="/jsp/admin/common/footer.jsp" />
