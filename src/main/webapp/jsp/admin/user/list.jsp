<%@ include file="/jsp/admin/common/taglibs.jsp"%>

<c:import url="/jsp/admin/common/header.jsp" />

<c:import url="/jsp/admin/common/menu.jsp" />

<display:table name="users" requestURI="/admin/user/list.htm" pagesize="20">
	<display:column property="login" title="Login" url="/admin/user/view.htm" paramId="user" paramProperty="id" />
	<display:column property="name" title="Name" />
</display:table>

<c:import url="/jsp/admin/common/footer.jsp" />
