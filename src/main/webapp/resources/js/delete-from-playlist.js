function deleteFromPlaylist(playlistId) {
    fetch(contextPath + "/music/playlists/playlist/song/delete/" + playlistId, {method: "DELETE"})
        .then((response) => {
            if (response.status == 200 && response.redirected != true){
                var element = document.getElementById("playlist-" + playlistId);
                element.parentNode.removeChild(element);
            }
        })
}