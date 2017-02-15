<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<c:import url="/jsp/common/menu.jsp" />

<display:table name="portfolios" requestURI="/list.htm" pagesize="20">
	<display:column property="name" title="&nbsp;" url="/view.htm" paramId="portfolio" paramProperty="id" />
</display:table>

<c:import url="/jsp/common/footer.jsp" />
