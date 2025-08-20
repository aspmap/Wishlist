var selector = 'video';
var videos = $(selector), len = videos.length, j = 0;
videos.bind('ended', function (e) {
    j++;
    if (j === len) j = 0;
    videos[j].play();
});
