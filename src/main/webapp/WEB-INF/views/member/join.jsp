<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Ad Analyzer</title>

	<%@include file="../settings/font.jsp"%>
	<%@include file="../settings/bootstrap.jsp"%>
	
	<script type="text/javascript">
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
			$("#save").click(function() {
				var pw = $("#pw").val();
				var arrPw = pw.split('');
				var setPw = new Set(arrPw);
				
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
				} else if($("#name").val() == "") {
					alert("이름을 입력하세요.");
					return;
				} else {
					document.getElementById("joinForm").action = "${root}/member/save";
					document.getElementById("joinForm").submit();
				}
				
			});
		});
	</script>
</head>
<body>
	<div class="container">
		<h2 style="text-align: center;">회원 가입</h2>
	
		<form id="joinForm" method="POST" action="">
		  <div class="form-group">
		    <label for="uid">ID:</label>
		    <input type="text" class="form-control" id="uid" name="uid" placeholder="영문으로만 10자 이내"> 
		  </div>
		  <div class="form-group">
		    <label for="pw">PW:</label>
		    <input type="password" class="form-control" id="pw" name="pw" placeholder="숫자/영어/특수문자 8자 이상(2개 이상 중복된 문자 불가)">
		  </div>
		  <div class="form-group">
		    <label for="name">이름:</label>
		    <input type="text" class="form-control" id="name" name="name">
		  </div>
		</form>
		
		<div style="text-align: center;">
			<button type="button" class="btn btn-info" id="save">확인</button>	
		</div>
	
	</div>
</body>
</html>