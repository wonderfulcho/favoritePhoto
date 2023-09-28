document.addEventListener('DOMContentLoaded', function() {
    const join = document.getElementById('joinButton');

    function joinButtonClick() {
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        const passwordConfirm = document.getElementById('passwordConfirm').value;
        const nickname = document.getElementById('nickname').value;

        const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

        let showAlert = true;

        if (!emailPattern.test(email)) {
            if (showAlert) {
                alert('유효한 이메일 형식이 아닙니다.');
                showAlert = false;
            }
            event.preventDefault();
        } else if (!email) {
            if (showAlert) {
                alert('이메일을 입력하세요.');
                showAlert = false;
            }
            event.preventDefault();
        } else if (!password) {
            if (showAlert) {
                alert('비밀번호를 입력하세요.');
                showAlert = false;
            }
            event.preventDefault();
        } else if (password.length < 8) {
            if (showAlert) {
                alert('유요한 비밀번호 형식이 아닙니다.');
                showAlert = false;
            }
            event.preventDefault();
        }
        else if (password !== passwordConfirm) {
            if (showAlert) {
                alert('비밀번호가 일치하지 않습니다.');
                showAlert = false;
            }
            event.preventDefault();
        }
        else if (!nickname) {
            if (showAlert) {
                alert('닉네임을 입력하세요.');
                showAlert = false;
            }
            event.preventDefault();
        } else if (nickname.length < 2 || nickname.length > 15) {
            if (showAlert) {
                alert('닉네임은 2~15자여야 합니다.');
            }
            event.preventDefault();
        }
    }

    join.addEventListener('click', joinButtonClick);
});

