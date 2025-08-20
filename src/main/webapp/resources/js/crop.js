var cropper;
$(document).ready(function(){
    $('#imgdiv').hide();
    $('#croppedimg').hide();
    $('#image').on("change", function (e) {
        console.log(e);
        ratio = 1 / 1;
        croppingimg(e, ratio);
    });

    function croppingimg(e, ratio) {
        var imgsrc = URL.createObjectURL(e.target.files[0])
        if (imgsrc) {
            $('#imagecan').attr("src", imgsrc);
            $('#imgdiv').show();
            var image = document.getElementById('imagecan');
            if (cropper) {
                cropper.destroy();
            }
            cropper = new Cropper(image, {
                viewMode: 3,
                aspectRatio: ratio,
                dragMode: 'move',
                cropBoxMovable: false,
                cropBoxResizable: false,
                checkOrientation: false,
                viewMode:1,
                crop: function (event) {
                    var url = cropper.getCroppedCanvas({
                        width: 50,
                        height: 50,
                    }).toDataURL('image/png', 1.0);
                    $('#cropped').attr("src", url);
                    $('#croppedimg').show();
                    window.URL.revokeObjectURL(imgsrc);
                }
            });
        }
    }
});

function onClickUpload() {
    var profileImage;
    if(cropper.getCroppedCanvas()){
        cropper.getCroppedCanvas().toBlob(function (blob) {
            var url = cropper.getCroppedCanvas().toDataURL();
            console.log(url);
            if (cropper) {
                cropper.destroy();
                $('#croppedimg').hide();
                $('#imgdiv').hide();
            }
            $('#profileImageModal').modal('toggle');
            $('#profilePicture').removeAttr('src')
            $('#profilePicture').attr('src', url);
            $('#blob-img').val(url);
            $('#image').val("");
        }, 'image/png');
    }
}