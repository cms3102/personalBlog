window.onload = function() {
	document.getElementById("close1").onclick = close1;
	document.getElementById("close2").onclick = close1;
	document.getElementById("close3").onclick = close2;
	document.getElementById("close4").onclick = close2;
	if(document.getElementById("open1")){
	document.getElementById("open1").onclick = open1;
	document.getElementById("open2").onclick = open2;
	document.getElementById("check_id1").onclick = check_pop;
	}
}

function open1() {
	document.getElementById('id01').style.display='block'
}

function open2() {
	document.getElementById('id02').style.display='block'
}

function close1() {
	document.getElementById('id01').style.display='none'
}

function close2() {
	document.getElementById('id02').style.display='none'
}

function check_pop() {
	var pop_option = "top=100, left=700, width=370, height=360, resizable=no, scrollbars=no, status=no";
	window.open("check_id/check_id.jsp?id=","아이디 중복 체크", pop_option);
}

