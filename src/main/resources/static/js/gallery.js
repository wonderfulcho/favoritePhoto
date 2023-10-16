document.addEventListener('DOMContentLoaded', function() {

    /* write 버튼 클릭 */
    const writeButton = document.getElementById('writeButton');
    if (writeButton != null) {
        function writeButtonClick(event) {
            if (loginMember) {
                window.location.href = '/gallery/galleryWrite';
            } else {
                alert("로그인이 필요합니다.");
            }
        }

        writeButton.addEventListener('click', writeButtonClick);
    }

    /* ReadMore 버튼 클릭 */
    const readMoreButtons = document.querySelectorAll('.photoButton');
    if (readMoreButtons != null) {
        for (let i = 0; i < readMoreButtons.length; i++) {
            const button = readMoreButtons[i];
            const itemId = button.getAttribute('data-id');

            button.addEventListener("click", function() {
                $.ajax({
                    url: '/gallery/galleryDetail',
                    type: 'GET',
                    data: { id: itemId },
                    success: function(data) {
                        console.log('성공');
                        window.location.href = '/gallery/galleryDetail?id=' + itemId;
                    },
                    error: function(xhr, status, error) {
                        console.error('AJAX 요청에 실패했습니다.', error);
                    }
                });
            });
        }
    }

    /* 댓글 입력 버튼 클릭시 */
    const replyForm = document.querySelector('form');
    if (replyForm != null) {
        replyForm.addEventListener('submit', function(event) {
            const replyText = document.getElementById('replyText').value;
            if (loginMember == null) {
                event.preventDefault();
                alert('로그인이 필요합니다~~~');
            } else if (!replyText.trim()) {
                event.preventDefault();
                alert('댓글을 입력해주세요.');
            }
        });
    }

    /* 댓글 삭제 버튼 클릭시 */
    const deleteButtons = document.querySelectorAll('#replyDelete');
    if (deleteButtons != null) {
        for (let i = 0; i < deleteButtons.length; i++) {
            const button = deleteButtons[i];
            const itemId = button.getAttribute('data-id');

            button.addEventListener("click", function() {
                 $.ajax({
                     url: '/gallery/replyDelete',
                     type: 'POST',
                     data: {
                        id: itemId
                     },
                     success: function(response) {
                         console.log(response);
                         alert('댓글 삭제가 완료되었습니다.')
                         location.reload(); // 화면 새로고침
                     },
                     error: function(xhr, status, error) {
                         console.error('AJAX 요청에 실패했습니다.', error);
                     }
                 });
            });
        }
    }

    /* 게시글 삭제 버튼 클릭시 */
    const galleryDelete = document.getElementById('galleryDelete');
    if (galleryDelete != null) {
        galleryDelete.addEventListener("click", function() {
            const itemId = galleryDelete.getAttribute('data-id');
             $.ajax({
                 url: '/gallery/galleryDelete',
                 type: 'POST',
                 data: {
                    id: itemId
                 },
                 success: function(response) {
                     console.log(response);
                     alert('게시글 삭제가 완료되었습니다.')
                     window.location.href = '/gallery/galleryMain';
                 },
                 error: function(xhr, status, error) {
                     console.error('AJAX 요청에 실패했습니다.', error);
                 }
             });
        });
    }

    /* 게시글 수정 버튼 클릭시 */
    const galleryUpdate = document.getElementById('galleryUpdate');
    if (galleryUpdate != null) {
        galleryUpdate.addEventListener("click", function() {
            const itemId = galleryDelete.getAttribute('data-id');
             $.ajax({
                 url: '/gallery/galleryUpdate',
                 type: 'GET',
                 data: {
                    id: itemId
                 },
                 success: function(response) {
                     console.log("성공");
                     window.location.href = '/gallery/galleryUpdate?id=' + itemId;
                 },
                 error: function(xhr, status, error) {
                     console.error('AJAX 요청에 실패했습니다.', error);
                 }
             });
        });
    }
});