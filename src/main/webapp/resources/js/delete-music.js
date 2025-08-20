function deleteMusic(musicId) {
    fetch(contextPath + "/music/delete/" + musicId, {method: "DELETE"})
        .then((response) => {
            if (response.status == 200 && response.redirected != true){
                var element = document.getElementById("music-" + musicId);
                element.parentNode.removeChild(element);
            }
        })
}