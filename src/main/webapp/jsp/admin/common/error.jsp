<%@ include file="/jsp/admin/common/taglibs.jsp"%>

<spring:hasBindErrors name="${param.formName}">
	<table>
		<c:forEach items="${errors.allErrors}" var="error">
			<tr>
				<td class="error"><c:out value="${error.code}" /></td>
			</tr>
		</c:forEach>
	</table>
</spring:hasBindErrors>
