<%@ include file="/jsp/admin/common/taglibs.jsp"%>

<c:import url="/jsp/admin/common/header.jsp" />

<c:import url="/jsp/admin/common/menu.jsp" />

<table>
	<tr>
		<td>Edit market</td>
	</tr>
</table>

<form action="<c:url value="/admin/market/edit.htm?market=${param.market}"/>" method="post">

<table>
	<tr>
		<td>Name :</td>
		<td><input type="text" name="name" value="<c:out value="${marketForm.name}"/>" /></td>
	</tr>
	<tr>
		<td>Riskless :</td>
		<td><input type="text" name="riskless" value="<c:out value="${marketForm.riskless}"/>" /></td>
	</tr>
	<tr>
		<td>Indice :</td>
		<td><select name="indiceId">
			<c:forEach items="${marketForm.stocks}" var="stock">
				<c:choose>
					<c:when test="${stock.id eq marketForm.indiceId}">
						<option value="<c:out value="${stock.id}"/>" selected="selected"><c:out value="${stock.symbol}" /> ( <c:out value="${stock.name}" /> )</option>
					</c:when>
					<c:otherwise>
						<option value="<c:out value="${stock.id}"/>"><c:out value="${stock.symbol}" /> ( <c:out value="${stock.name}" /> )</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</select></td>
	</tr>
</table>

<table>
	<tr>
		<td><input type="submit" value="Ok" /></td>
	</tr>
</table>

</form>

<c:import url="/jsp/admin/common/error.jsp">
	<c:param name="formName">marketForm</c:param>
</c:import>

<c:import url="/jsp/admin/common/footer.jsp" />
