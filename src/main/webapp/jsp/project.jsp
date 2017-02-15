<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<c:import url="/jsp/common/menu.jsp" />

<table>
	<tr>
		<td>Name :</td>
		<td><c:out value="${portfolio.name}" /></td>
	</tr>
	<tr>
		<td>Market :</td>
		<td><c:out value="${market.name}" /></td>
	</tr>
	<tr>
		<td>From date :</td>
		<td><fmt:formatDate value="${portfolio.fromDate}" pattern="yyyy-MM-dd" /></td>
	</tr>
	<tr>
		<td>To date :</td>
		<td><fmt:formatDate value="${portfolio.toDate}" pattern="yyyy-MM-dd" /></td>
	</tr>
	<tr>
		<td>Beta :</td>
		<td><c:out value="${portfolio.beta}" /></td>
	</tr>
	<tr>
		<td>Size :</td>
		<td><c:out value="${portfolio.size}" /></td>
	</tr>
</table>
<br />

<table>
	<tr>
		<td><a href="<c:url value="/view.htm?portfolio=${portfolio.id}"/>">Positions</a></td>
		<td><a href="<c:url value="/delete.htm?portfolio=${portfolio.id}"/>">Delete</a></td>
	</tr>
</table>
<br />

<c:if test="${error != null}">
	<table>
		<tr>
			<td><c:out value="${error}" /></td>
		</tr>
	</table>
</c:if>

<c:if test="${error == null}">
	<table>
		<tr>
			<td><img src="<c:url value="/chart.htm?name=project&width=400&height=200"/>" width="400" height="200" /></td>
		</tr>
	</table>
</c:if>

<c:import url="/jsp/common/footer.jsp" />
