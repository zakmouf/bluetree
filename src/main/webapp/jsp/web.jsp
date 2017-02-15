<%@ include file="/jsp/common/taglibs.jsp"%>

<c:import url="/jsp/common/header.jsp" />

<c:import url="/jsp/common/navigation.jsp" />

<table>
	<tr>
		<td id="subcontent"><c:import url="/jsp/common/menu.jsp" /></td>

		<td><c:if test="${navigation <= 1 or navigation > 7}">

			<c:if test="${menu <= 1 or menu > 3}">
				<div id="content">
				<h2>Company</h2>
				<p><strong>BLUE TREE FINANCE</strong> is a financial consulting company based in Lausanne. It provides financial advice to institutions as well as private individuals concerning investments and asset allocation.</p>
				<p><strong>BLUE TREE FINANCE</strong>, which started with the Forex market, developed its products and broadened its activities to include equities and commodities.</p>
				</div>
			</c:if>

			<c:if test="${menu == 2}">
				<div id="content">
				<h2>Team</h2>
				<p><strong>BLUE TREE FINANCE</strong> combines strong technical skills thanks to the expertise of its three founders.</p>
				<p>A. Dehbi holds a PhD from the Massachusetts Institute of Technology (MIT), and has an extensive background in quantitative methods and portfolio management.</p>
				<p>A. Guidoum holds a Ph.D. from the Swiss Federal Institute of Technology (EPFL), and is specialized in econometrics and statistics.</p>
				<p>A. Mejri holds a MBA from the EPFL and is an expert in supply chain management. The team is backed by B. Bastian, who deals with communication and marketing.</p>
				</div>
			</c:if>

			<c:if test="${menu == 3}">
				<div id="content">
				<h2>Jobs</h2>
				<p>There are no open positions for the moment!</p>
				<p>We will update this page regularly.</p>
				<p>Stay tuned !</p>
				</div>
			</c:if>

		</c:if> <c:if test="${navigation == 2}">
			<div id="content">
			<h2>Advanced Portfolio Solutions&trade; Equity</h2>
			<p><strong>APS&trade;</strong> was developed by <strong>BLUE TREE FINANCE</strong> during the last six years. It is a tool geared towards improving fund returns under rigorously controlled risk levels. <strong>APS&trade;</strong> is based on Modern Portfolio Theory, with significant enhancements. <strong>APS&trade;</strong> builds so-called 'efficient portfolios', which yield the highest returns for a specified amount of risk.</p>
			<h3>Motivation</h3>
			<p>Most equity mutual funds fail to match their respective market index and this is why index funds have grown so dramatically to dominate the fund business in the past few years. This situation indicates that random diversification is no guarantee of superior returns, or even lower risk. Thus diversification must be systematic to provide high returns and low risks.</p>
			<h3>Strength</h3>
			<p>Classical algorithms cannot solve in a reasonable amount of time the portfolio selection problem beyond a universe of 50 stocks or so. The problem is even more complicated by adding constraints. Limiting the universe to this number of stocks comes with huge penalty. For the S&P 500, it means disregarding 90% of companies. <strong>APS&trade;</strong> major strength is its ability to scan the full market and take advantage of almost an infinite number of correlations between stocks to device portfolios with truly exceptional reward-to-risk ratios. It can also accommodate any set of
			constraints.</p>
			<h3>Portfolio Selection</h3>
			<p>Given a universe of possible stocks to be owned (e.g. FTSE 100, Dow, S&P 500, etc) and investor tolerance of risk (as measured by percent of market risk or beta), <strong>APS&trade;</strong> provides the details of the optimum portfolio, i.e. the composition and weights.</p>
			<h2>Portfolio Management</h2>
			<p>In addition to stock selection, <strong>APS&trade;</strong> provides continuous update of portfolio composition to maintain optimum risk/reward characteristics. This leads to an additional improvement in the returns on the order of 5% for 100% turnover a year.</p>
			<h2>Advanced Portfolio Solutions&trade; within a global strategy</h2>
			<p><strong>APS&trade;</strong> is intended to be used along with macroeconomic and fundamental analyses, which are the duty of the fund manager. The pool of potential assets which prove to be fundamentally sound constitute then an input universe for <strong>APS&trade;</strong>.</p>
			</div>
		</c:if> <c:if test="${navigation == 3}">

			<c:if test="${menu <= 1 or menu > 3}">
				<div id="content">
				<h2>Portfolio Management</h2>
				<p>No text.</p>
				</div>
			</c:if>

			<c:if test="${menu == 2}">
				<div id="content">
				<h2>Optimization</h2>
				<p>No text.</p>
				</div>
			</c:if>

			<c:if test="${menu == 3}">
				<div id="content">
				<h2>Risk Assessment</h2>
				<p>No text.</p>
				</div>
			</c:if>

		</c:if> <c:if test="${navigation == 4}">

			<c:if test="${menu <= 1 or menu > 4}">
				<div id="content">
				<h2>S&amp;P 500 - 5 Year Performance</h2>
				<p>The <strong>APS&trade;</strong> portfolio with a beta of 0.7 shows a return of 36% whereas the S&amp;P500 has a -7% return for the same 5 year period. Most noteworthy is the fact that the <strong>APS&trade;</strong> portfolio significantly beats the index in both up and down turns.</p>
				<p><img src="<c:url value="/images/portfolios1.jpg"/>" width="450" /></p>
				</div>
			</c:if>

			<c:if test="${menu == 2}">
				<div id="content">
				<h2>S&amp;P 350 EURO - 1 Year Performance</h2>
				<p>The <strong>APS&trade;</strong> portfolio on the image below shows how it significantly beats the index in both up and down turns.</p>
				<p><img src="<c:url value="/images/portfolios2.jpg"/>" width="450" /></p>
				</div>
			</c:if>

			<c:if test="${menu == 3}">
				<div id="content">
				<h2>CAC 40 - 2 Year Performance</h2>
				<p>The <strong>APS&trade;</strong> portfolio on the image below shows how it significantly beats the index in both up and down turns.</p>
				<p><img src="<c:url value="/images/portfolios3.jpg"/>" width="450" /></p>
				</div>
			</c:if>

			<c:if test="${menu == 4}">
				<div id="content">
				<h2>FTSE 250 - 1 Year Performace</h2>
				<p>The <strong>APS&trade;</strong> portfolio on the image below shows how it significantly beats the index in both up and down turns.</p>
				<p><img src="<c:url value="/images/portfolios4.jpg"/>" width="450" /></p>
				</div>
			</c:if>

		</c:if> <c:if test="${navigation == 5}">
			<div id="content">
			<h2>Partners</h2>
			<p>No text.</p>
			</div>
		</c:if> <c:if test="${navigation == 6}">
			<div id="content">
			<h2>News</h2>
			<p>No text.</p>
			</div>
		</c:if> <c:if test="${navigation == 7}">
			<div id="content">
			<h2>Contact</h2>
			<p>BLUE TREE FINANCE<br />
			Chemin de la Dent d'Oche 1b<br />
			CH-1015 Lausanne, Switzerland<br />
			<strong>Tel :</strong> +41 21 691 70 13<br />
			<strong>Fax :</strong> +41 21 691 70 14<br />
			<strong>Web :</strong> <a href="http://www.bluetreefinance.com">http://www.bluetreefinance.com</a><br />
			<strong>Email :</strong> <a href="mailto:info@bluetreefinance.com">info@bluetreefinance.com</a><br />
			</p>
			</div>
		</c:if></td>
	</tr>
</table>

<c:import url="/jsp/common/footer.jsp" />
