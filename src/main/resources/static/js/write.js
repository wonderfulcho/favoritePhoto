document.addEventListener('DOMContentLoaded', function() {
    ClassicEditor.create(document.getElementById("editor"), {
        language: "ko",
        ckfinder: {
			uploadUrl : '/image/upload',
			withCredentials: true
		}
    });
});

