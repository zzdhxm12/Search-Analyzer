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
	function searchByKeyword(){
		document.location.href = "${root}/search/search?keyword="+document.getElementById('keyword').value;
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
					<button type="button" class="btn btn-success" id="search" onclick="javascript:searchByKeyword()">검색</button>
				</div>
			</div>
		</form><br>
		
		<table class="table table-hover">
	      <thead>
		    <tr>
		      <th>No.</th>
		      <th>URL</th>
		      <th>타이틀</th>
		      <th>검색어 출현수</th>
		    </tr>
	      </thead>
	      <tbody>
	        <c:forEach var="item" items="${ list }" varStatus="status">
	        <tr>
	          <td style="width: 10%">${ status.count }</td>
		      <td style="width: 50%; word-break:break=all;">${ item.url }</td>
		      <td style="width: 30%">${ item.title }</td>
	          <td style="width: 10%">${ item.freq }</td>
		    </tr>
		    </c:forEach>
		  </tbody>
	    </table>

	  </div>
	</div>
</body>
</html>