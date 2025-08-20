var selector = 'audio';
var audios = $(selector), len = audios.length, j = 0;
audios.bind('ended', function (e) {
    j++;
    if (j === len) j = 0;
    audios[j].play();
});
