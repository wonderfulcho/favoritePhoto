document.addEventListener('DOMContentLoaded', function() {
    let isImageInserted = false;

    ClassicEditor
        .create(document.getElementById("editor"), {
            language: "ko",
            ckfinder: {
                uploadUrl: '/image/upload',
                withCredentials: true,
                image: {
                    upload: {
                        imageUploadLimit: 5 * 1024 * 1024
                    }
                }
            }
        })
        .then(editor => {
            editor.model.document.on('change:data', () => {
                const content = editor.getData();
                isImageInserted = content.includes('<img'); // 이미지 태그가 포함되어 있는지 확인
            });

            const form = document.getElementById('update');
            form.addEventListener('submit', function(event) {
                const titleInput = document.querySelector('input[name="title"]');
                if (!titleInput.value.trim()) {
                    event.preventDefault();
                    alert('제목을 입력해주세요.');
                } else if (!isImageInserted) {
                    event.preventDefault();
                    alert('이미지를 추가해주세요.');
                }
            });
        })
        .catch(error => {
            console.error(error);
        });
});