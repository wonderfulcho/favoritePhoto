document.addEventListener('DOMContentLoaded', function() {
    const writeButton = document.getElementById('writeButton');

    function writeButtonClick(event) {
        if (loginMember) {
            window.location.href = '/gallery/galleryWrite';
        } else {
            alert("로그인이 필요합니다.");
        }
    }

    writeButton.addEventListener('click', writeButtonClick);
});
