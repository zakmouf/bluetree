<%@ include file="/jsp/admin/common/taglibs.jsp"%>

<c:import url="/jsp/admin/common/header.jsp" />

<c:import url="/jsp/admin/common/menu.jsp" />

<table>
	<tr>
		<td><a href="<c:url value="/admin/profile/new.htm"/>">New profile</a></td>
	</tr>
</table>

<display:table name="profiles" id="profile" requestURI="/admin/profile/list.htm" pagesize="20">
	<display:column property="name" title="Name" />
	<display:column title="default">
		<c:choose>
			<c:when test="${defaultProfile == null}">0</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${defaultProfile.id == profile.id}">1</c:when>
					<c:otherwise>0</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</display:column>
	<display:column title="&nbsp;" url="/admin/profile/edit.htm" paramId="profile" paramProperty="id">edit</display:column>
	<display:column title="&nbsp;" url="/admin/profile/default.htm" paramId="profile" paramProperty="id">set default</display:column>
	<display:column title="&nbsp;" url="/admin/profile/delete.htm" paramId="profile" paramProperty="id">delete</display:column>
</display:table>

<c:import url="/jsp/admin/common/footer.jsp" />
