// 유효성 검사
var id_reg = /^[a-zA-Z0-9]{1,8}$/; 
var name_reg = /^[a-zA-Z]{1,20}$|^[가-힣]{1,6}$/;
var pw_reg = /^[a-zA-Z0-9]{4,8}$/; 
var email_reg = /^[a-zA-Z0-9]{1,10}@[a-zA-Z]+(\.[a-zA-Z]+)+$/;
var telephone_reg = /^(\d{3,4}\-\d{4})?$/; 
var check_id = /^\D$/; 

function formatCheck() {
	var id = document.getElementsByName("id")[0].value;
	var name = document.getElementById("namename").value;
	var pw1 = document.getElementById("pw1").value;
	var pw2 = document.getElementById("pw2").value;
	var email = document.getElementsByName("email")[0].value;
	var telephone2 = document.getElementsByName("telephone2")[0].value;
	var check_id1 = document.getElementById("sign_up").value;
	
	
	if (!id_reg.test(id)) {
		alert(id + "아이디를 영문 8자 내에서 입력해 주세요.");
		return false;
	}
	
	if (!name_reg.test(name)) {
		alert(name + "이름을 영문 20자 / 한글 6자 내로 입력해 주세요.");
		return false;
	}
	
	if (!pw_reg.test(pw1)) {
		alert("비밀번호를 영문/숫자 4~8자 형식으로 입력해 주세요.");
		return false;
	}
	
	if (pw1 != pw2) {
		alert("비밀번호를 동일하게 다시 한 번 입력해 주세요.");
		return false;
	}
	
	if (!email_reg.test(email)) {
		alert("올바른 이메일 주소를 입력해 주세요.");
		return false;
	}
	
	if (!telephone_reg.test(telephone2)) {
		alert("전화번호를 111(1)-1111 형태로 입력해 주세요.");
		return false;
	}
	
	if (!check_id.test(check_id1)) {
		alert("아이디 중복 체크를 해주세요.");
		return false;
	}
	
	return true;
	
}