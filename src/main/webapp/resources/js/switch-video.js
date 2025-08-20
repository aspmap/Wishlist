var a = document.getElementsByTagName("video");

function switchVideo(b) {
    for (i = 0; i < a.length; i++) {
        if (!(i == b)) {
            a[i].pause()
            a[i].currentTime = 0;
        };
    }
}