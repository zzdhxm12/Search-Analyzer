<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ad Analyzer</title>

	<%@include file="../settings/font.jsp"%>
	<%@include file="../settings/bootstrap.jsp"%>
	
	<script type="text/javascript">
		function mvJoin(){
			document.location.href = "${root}/join";
		}
		
		$(document).ready(function() {
			// ID 입력 시, 영문만 입력되도록 정규표현식 사용 
			$("#uid").keyup(function(event){
				if (!(event.keyCode >=37 && event.keyCode<=40)) {
					var inputVal = $(this).val();
					$(this).val(inputVal.replace(/[^a-z]/gi,''));
				}
			});
			// Password 입력 시, 영문/숫자/특수문자만 입력되도록 정규표현식 사용
			$("#pw").keyup(function(event){
				if (!(event.keyCode >=37 && event.keyCode<=40)) {
					var inputVal = $(this).val();
					$(this).val(inputVal.replace(/[^a-z0-9~!@#$%^&*()_+-|<>?]/gi,''));
				}
			});
		});
		
		$(document).ready(function() {
			$("#login").click(function() {
				var pw = $("#pw").val();
				var arrPw = pw.split('');
				var setPw = new Set(arrPw);
				
				if($("#uid").val() == "admin" && $("#pw").val() == "1234") {
					document.getElementById("loginForm").action = "${root}/member/login";
					document.getElementById("loginForm").submit();
				} else {
					if($("#uid").val() == "") {
						alert("ID를 입력하세요.");
						return;
					} else if($("#uid").val().length > 10) {
						alert("ID를 10자 이내로 입력해주세요.");
						return;
					} else if($("#pw").val() == "") {
						alert("비밀번호를 입력하세요.");
						return;
					} else if($("#pw").val().length < 8) {
						alert("비밀번호를 8자 이상 입력해주세요.");
						return;
					} else if(arrPw.length !== setPw.size) {
						alert("비밀번호 입력 시, 중복된 문자를 제외하고 입력해주세요.");
						return;
					} else {
						document.getElementById("loginForm").action = "${root}/member/login";
						document.getElementById("loginForm").submit();
					}	
				}
				
			});
		});
	</script>
</head>
<body>
	<div class="container">
		<h2 style="text-align: center;">로그인</h2>

		<form id="loginForm" method="GET" action="">
			<label for="uid">ID:</label>
		  	<input type="text" class="form-control" id="uid" name="uid">
			<label for="pw">Password:</label>
		 	<input type="password" class="form-control" placeholder="숫자/영어/특수문자 8자 이상" id="pw" name="pw">
		</form><br><br>
		<div style="text-align: center;">
			<button type="button" class="btn btn-secondary" onclick="javascript:mvJoin()">회원가입</button>
			<button type="button" class="btn btn-info" id="login">로그인</button>
		</div>
	</div>
</body>
</html>