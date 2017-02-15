<%@ include file="/jsp/admin/common/taglibs.jsp"%>

<c:import url="/jsp/admin/common/header.jsp" />

<c:import url="/jsp/admin/common/menu.jsp" />

<table>
	<tr>
		<td>Profiles on user</td>
	</tr>
</table>

<form action="<c:url value="/admin/user/profile.htm?user=${param.user}"/>" method="post">

<table>
	<c:forEach items="${userProfileForm.profiles}" var="profile">
		<c:set var="found" value="0" />
		<c:forEach items="${userProfileForm.profileId2}" var="profileId2">
			<c:if test="${profile.id eq profileId2}">
				<c:set var="found" value="1" />
			</c:if>
		</c:forEach>
		<tr>
			<c:choose>
				<c:when test="${found eq 0}">
					<td><input type="checkbox" name="profileId" value="<c:out value="${profile.id}"/>" /></td>
				</c:when>
				<c:otherwise>
					<td><input type="checkbox" name="profileId" value="<c:out value="${profile.id}"/>" checked="checked" /></td>
				</c:otherwise>
			</c:choose>
			<td><c:out value="${profile.name}" /></td>
		</tr>
	</c:forEach>
</table>

<table>
	<tr>
		<td><input type="submit" value="Ok" /></td>
	</tr>
</table>

</form>

<c:import url="/jsp/admin/common/footer.jsp" />
