document.addEventListener('DOMContentLoaded', function() {
    const login = document.getElementById('loginButton');

    function loginButtonClick(event) {
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        let showAlert = true;
        if (!email) {
            if(showAlert) {
                alert('이메일을 입력하세요.');
                showAlert = false;
            }
            event.preventDefault();
        } else if (!password) {
            if(showAlert) {
                alert('비밀번호를 입력하세요.');
                showAlert = false;
            }
            event.preventDefault();
        }

        // AJAX를 사용하여 서버로 로그인 정보 전송
        $.ajax({
            url: '/member/loginOK',
            type: 'POST',
            data: {
                email: email,
                password: password
            },
            success: function(response) {
                if (response.success) {
                    alert('정상적으로 로그인되었습니다.');
                    window.location.href = '/main';
                } else {
                    if(showAlert) {
                        alert('아이디 또는 패스워드를 확인하세요.');
                        showAlert = false;
                    }
                    event.preventDefault();
                }
            },
            error: function() {
                alert('서버와의 통신 중 오류가 발생했습니다.');
            }
        });
    }



    login.addEventListener('click', loginButtonClick);
});