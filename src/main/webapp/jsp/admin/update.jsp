<%@ include file="/jsp/admin/common/taglibs.jsp"%>

<c:import url="/jsp/admin/common/header.jsp" />

<c:import url="/jsp/admin/common/menu.jsp" />

<table>
	<tr>
		<td>Update</td>
	</tr>
</table>
<c:if test="${not empty updateForm.jobDetails}">
	<table>
		<c:forEach items="${updateForm.jobDetails}" var="jobDetail">
			<tr>
				<td><c:out value="${jobDetail.jobDataMap['timeStamp']}"/></td>
				<td><c:out value="${jobDetail.jobDataMap['marketId']}"/></td>
				<td><c:out value="${jobDetail.jobDataMap['startDate']}"/></td>
				<td><c:out value="${jobDetail.jobDataMap['increment']}"/></td>
			</tr>
		</c:forEach>
	</table>
</c:if>

<form action="<c:url value="/admin/update.htm"/>" method="post"><c:set var="markets" value="${updateForm.markets}" />

<table>
	<tr>
		<td>Market :</td>
		<td><select name="marketId">
			<c:forEach items="${updateForm.markets}" var="market">
				<c:choose>
					<c:when test="${market.id eq updateForm.marketId}">
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
		<td>Start :</td>
		<td><input type="text" name="startDate" value="<c:out value="${updateForm.startDate}"/>" /></td>
	</tr>
	<tr>
		<td>Increment :</td>
		<td><input type="text" name="increment" value="<c:out value="${updateForm.increment}"/>" /></td>
	</tr>
</table>

<table>
	<tr>
		<td><input type="submit" value="Ok" /></td>
	</tr>
</table>

</form>

<c:import url="/jsp/admin/common/error.jsp">
	<c:param name="formName">updateForm</c:param>
</c:import>

<c:import url="/jsp/admin/common/footer.jsp" />
