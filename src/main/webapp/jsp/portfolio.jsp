<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<c:import url="/jsp/common/menu.jsp" />

<form action="<c:url value="/portfolio.htm"/>" method="post">
<table>
	<tr>
		<td>Name :</td>
		<td><input type="text" name="name" value="<c:out value="${portfolioForm.name}"/>" /></td>
	</tr>
	<tr>
		<td>Market :</td>
		<td><select name="marketId">
			<c:forEach items="${portfolioForm.markets}" var="market">
				<c:choose>
					<c:when test="${market.id eq portfolioForm.marketId}">
						<option value="<c:out value="${market.id}"/>" selected="selected"><c:out value="${market.name}" /></option>
					</c:when>
					<c:otherwise>
						<option value="<c:out value="${market.id}"/>"><c:out value="${market.name}" /></option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</select></td>
	</tr>
	<tr>
		<td>From date :</td>
		<td><input type="text" name="fromDate" value="<c:out value="${portfolioForm.fromDate}"/>" /></td>
	</tr>
	<tr>
		<td>To date :</td>
		<td><input type="text" name="toDate" value="<c:out value="${portfolioForm.toDate}"/>" /></td>
	</tr>
	<tr>
		<td>Beta :</td>
		<td><input type="text" name="beta" value="<c:out value="${portfolioForm.beta}"/>" /></td>
	</tr>
	<tr>
		<td>Size :</td>
		<td><input type="text" name="size" value="<c:out value="${portfolioForm.size}"/>" /></td>
	</tr>
	<tr>
		<td colspan="2"><input type="submit" value="Create portfolio" /></td>
	</tr>
</table>
</form>

<c:import url="/jsp/common/error.jsp">
	<c:param name="formName">portfolioForm</c:param>
</c:import>

<c:import url="/jsp/common/footer.jsp" />
