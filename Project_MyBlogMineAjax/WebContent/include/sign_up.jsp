<%@ page contentType="text/html; charset=UTF-8"%>


    
	<!-- SignUp -->
	<div id="id01" class="modal">
  
 		<form class="modal-content animate" action="regist_action.jsp" onsubmit="return formatCheck();" method="post">
 		<span id="close1" class="close" title="Close Modal">×</span>
    		<div class="container1">
    		<div class="check_id">
            <label class="infoname"><b>아이디</b></label>
    		<input id="input_name" class="idid" type="text" placeholder="아이디를 입력하세요." name="id" required>
            <div>
            <span id="check_id1"></span>
            </div>
            </div>
			<label class="infoname"><b>이름</b></label>
    		<input id="namename" class="inputsign" type="text" placeholder="이름을 입력하세요." name="name" required>
    		<label class="infoname"><b>비밀번호</b></label>
    		<input id="pw1" class="inputsign" type="password" placeholder="비밀번호를 입력하세요." name="passwd" required>
    		<label class="infoname"><b>비밀번호 확인</b></label>
    		<input id="pw2" class="inputsign" type="password" placeholder="비밀번호를 다시 한 번 입력하세요." name="passwd" required>
            <label class="infoname"><b>이메일</b></label>
            <input class="inputsign" type="text" placeholder="이메일을 입력하세요." name="email" required>
            <span id="emailemail"></span>
			<label class="infoname"><b>전화번호</b></label>
            <div class="contact">
            <select class="select1" name="telephone1">
              <option>010</option>
              <option>011</option>
            </select>
    		<input type="text" placeholder="전화번호를 입력하세요." name="telephone2">
            </div>
            <label class="infoname"><b>직업</b></label>
             <select class="select1" name="job">
              <option>회사원</option>
              <option>강사</option>
              <option>학생</option>
              <option>기타</option>
            </select>
            <label class="infoname"><b>가입인사</b></label>
            <textarea class="inputsign" rows="2" cols="30" placeholder="가입인사를 입력하세요." name="message"></textarea>
				<!-- <input class="inputsign" type="checkbox" checked="checked"> Remember me -->	
    		<div class="clearfix">
    		<button id="sign_up" type="submit" class="signupbtn alt" value="0">회원가입</button>
    		<button id="close2" type="reset" class="cancelbtn alt">취소</button>
    		</div>
    		</div>
  		</form>
	</div>
  
	
			
