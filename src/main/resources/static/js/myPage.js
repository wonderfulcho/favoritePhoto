document.addEventListener('DOMContentLoaded', function() {
    const updateButton = document.getElementById('updateButton');

    function updateButtonClick(event) {
        const id = document.getElementById('id').value;
        const email = document.getElementById('email').value;
        let password = document.getElementById('password').value;
        const newPassword = document.getElementById('newPassword').value;
        const passwordConfirm = document.getElementById('passwordConfirm').value;
        const nickname = document.getElementById('nickname').value;

        $.ajax({
            url: '/member/memberUpdate',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify({
                memberDTO: {
                    id: id,
                    email: email,
                    password: password,
                    nickname: nickname
                },
                newPassword: newPassword,
                passwordConfirm: passwordConfirm
            }),
            success: function(response) {
                if (response.check === 1) {
                    alert('비밀번호를 확인해주세요.');
                    event.preventDefault();
                } else if (response.check === 2) {
                    alert('중복된 닉네임입니다.');
                    event.preventDefault();
                }else if (response.check === 3) {
                    alert('닉네임을 입력하세요.');
                    event.preventDefault();
                } else if (response.check === 4) {
                    alert('닉네임은 2~15자여야 합니다.');
                    event.preventDefault();
                } else if (response.check === 5) {
                    alert('정상적으로 수정되었습니다.');
                    window.location.href = '/main';
                } else {
                    alert('알 수 없는 오류가 발생했습니다.');
                    event.preventDefault();
                }
            },
            error: function() {
                console.log("통신오류");
            }
        });

    }

    updateButton.addEventListener('click', updateButtonClick);
});