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

<c:if test="${not portfolio.executed}">
	<table>
		<tr>
			<td>Not executed</td>
		</tr>
	</table>
</c:if>

<c:if test="${portfolio.executed and portfolio.error != null}">
	<table>
		<tr>
			<td><a href="<c:url value="/delete.htm?portfolio=${portfolio.id}"/>">Delete</a></td>
		</tr>
	</table>
	<br />
	<table>
		<tr>
			<td>Error [ <c:out value="${portfolio.error}" /> ]</td>
		</tr>
	</table>
</c:if>

<c:if test="${portfolio.executed and portfolio.error == null}">

	<table>
		<tr>
			<td><a href="<c:url value="/project.htm?portfolio=${portfolio.id}"/>">Project</a></td>
			<td><a href="<c:url value="/delete.htm?portfolio=${portfolio.id}"/>">Delete</a></td>
		</tr>
	</table>
	<br />

	<display:table name="positions">
		<display:column property="stock.symbol" title="Symbol" />
		<display:column property="stock.name" title="Name" />
		<display:column property="weight" title="Weight" format="{0,number,0.00%}" />
	</display:table>

</c:if>

<c:import url="/jsp/common/footer.jsp" />
