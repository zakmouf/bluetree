<%@ include file="/jsp/common/taglibs.jsp"%>

<spring:hasBindErrors name="${param.formName}">
	<br />
	<table>
		<c:forEach items="${errors.allErrors}" var="error">
			<tr>
				<td><c:out value="${error.code}" /></td>
			</tr>
		</c:forEach>
	</table>
</spring:hasBindErrors>
