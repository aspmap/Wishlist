function handleFileSelect() {
    const fileInput = document.getElementById('fileInput');
    const uploadButton = document.getElementById('uploadButton');
    const uploadedButton = document.getElementById('uploadedButton');

    if (fileInput.files.length > 0) {
        uploadButton.style.display = 'none';
        uploadedButton.style.display = 'inline-block';
        // Здесь можно добавить код для загрузки файла на сервер
    }
}