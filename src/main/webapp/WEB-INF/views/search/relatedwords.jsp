<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<c:if test="${userInfo == null}">
	<c:redirect url="/login" />
</c:if>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Ad Analyzer</title>
  
  <%@include file="../settings/font.jsp"%>
  <%@include file="../settings/bootstrap.jsp"%>
  
  <script type="text/javascript">
	function searchByRelated(){
		document.location.href = "${root}/search/related?keyword="+document.getElementById('keyword').value;
	}
	
	function mvSearch(){
		document.location.href = "${root}/search";
	}
	
	function mvHost(){
		document.location.href = "${root}/host";
	}
	
	function mvRelated(){
		document.location.href = "${root}/related";
	}
	
	function mvPage(num) {
		var keyword = '<c:out value="${ keyword }"/>';
		if(keyword == null) keyword = "";
		document.location.href = "${root}/search/related?keyword="+keyword+"&page="+num;
	}
    </script>
</head>
<body>
	<nav class="navbar navbar-expand-sm bg-info navbar-dark d-flex">
    	<span class="navbar-brand mr-auto mb-0 h1">Search Analyzer</span>
		<span class="navbar-brand mb-0 h1">by. 조희은</span>
	</nav>
	
	<div class="row">
	  <div class="col-sm-2">
	  	<nav class="navbar bg-light">
		  <ul class="navbar-nav">
		    <li class="nav-item active">
		      <a class="nav-link" onclick="javascript:mvSearch()">실시간 검색 결과</a>
		    </li>
		    <li class="nav-item">
		      <a class="nav-link" onclick="javascript:mvHost()">호스트 통계</a>
		    </li>
		    <li class="nav-item">
		      <a class="nav-link" onclick="javascript:mvRelated()">연관 검색어</a>
		    </li>
		  </ul>
		</nav>
	  </div>
	  <div class="col-sm-10">
		<br>
		<form id="searchForm" method="GET" action="">
			<div class="form-row">
				<div class="col-5">
					<input type="text" class="form-control" placeholder="검색어를 입력해주세요." id="keyword" name="keyword">
				</div>
				<div class="col">
					<button type="button" class="btn btn-success" id="search" onclick="javascript:searchByRelated()">조회</button>
				</div>
			</div>
		</form><br>
		
		<table class="table table-hover">
	      <thead>
		    <tr>
		      <th>No.</th>
		      <th>검색어</th>
		      <th>연관 검색어</th>
		    </tr>
	      </thead>
	      <tbody>
	        <c:forEach var="related" items="${ list }" varStatus="status">
	        <tr>
	          <td style="width: 10%">${ status.count + (pageList.pageable.pageNumber*10) }</td>
		      <td style="width: 10%">${ related.keyword }</td>
		      <td>${ related.related }</td>
		    </tr>
		    </c:forEach>
		  </tbody>
	    </table>
		
		<nav aria-label="Page navigation">
		  <ul class="pagination">
		  	<c:choose>
		  		<c:when test="${pageList.first}"></c:when>
		  		<c:otherwise>
		  			<li class="page-item" onclick="javascript:mvPage(${ pageList.number-1 })">
				      <a class="page-link" href="#" aria-label="Previous">
				        <span aria-hidden="true">&laquo;</span>
				        <span class="sr-only">Previous</span>
				      </a>
				    </li>
		  		</c:otherwise>
		  	</c:choose>
			
			<c:forEach begin="${startBlockPage}" end="${endBlockPage}" var="i">
				<c:choose>
					<c:when test="${pageList.pageable.pageNumber+1 == i}">
						<li class="page-item disabled"><a class="page-link" href="#" onclick="javascript:mvPage(${ i-1 })">${ i }</a></li>
					</c:when>
					<c:otherwise>
						<li class="page-item"><a class="page-link" href="#" onclick="javascript:mvPage(${ i-1 })">${ i }</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			
			<c:choose>
				<c:when test="${pageList.last}"></c:when>
				<c:otherwise>
					<li class="page-item" onclick="javascript:mvPage(${ pageList.number+1 })">
				      <a class="page-link" href="#" aria-label="Next">
				        <span aria-hidden="true">&raquo;</span>
				        <span class="sr-only">Next</span>
				      </a>
				    </li>
				</c:otherwise>
			</c:choose>

		  </ul>
		</nav>
		
	  </div>
	</div>
</body>
</html>