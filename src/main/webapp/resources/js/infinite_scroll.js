let page = 0;

function posts_pagination() {
    $.ajax({
        url: contextPath + "/pagination_api?page=" + page + "&size=" + size,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var dataString = JSON.stringify(data);
            var dataParse = JSON.parse(dataString);

            if (dataParse.content.length == 0 && page == 0) {
                var post = $('<div class="post"></div>');
                var noPublic = $('<div class="no_public"></div>');
                var defaulImg = $('<img src="' + contextPath + '/resources/img/icons/face.png" alt="" /><h1 class="h3 mb-3 font-weight-normal no_public">Пока нет подписок</h1>');
                post.append(noPublic);
                noPublic.append(defaulImg);
                $('#posts').append(post);
            }

            if (dataParse.content.length > 0) {
                dataParse.content.forEach(el => {
                    var post = $('<div class="post"></div>');
                    var info = $('<div class="info"></div>');
                    var user = $('<div class="user"></div>');
                    var pic = $('<div class="profile-pic"></div>')
                    post.append(info);
                    info.append(user);
                    user.append(pic);
                    var imgLink;
                    if (el.user.isGoogle == true && el.user.photo != null) {
                        imgLink = $('<a href="' + contextPath + '/' + el.user.username + '"><img src="' + el.user.photo + '" alt="" /></a>');
                    } else if (el.user.isGoogle == false && el.user.photo != null) {
                        imgLink = $('<a href="' + contextPath + '/' + el.user.username + '"><img src="' + contextPath + "/resources/img/users/" + el.user.username + "/profile/" + el.user.photo + '" alt="" /></a>');
                    } else if (el.user.photo == null) {
                        imgLink = $('<a href="' + contextPath + '/' + el.user.username + '"><img src="' + contextPath + "/resources/img/icons/avatar.jpg" + '"alt="" /></a>');
                    }
                    var usernameLink;
                    if (el.user.isGoogle == true) {
                        usernameLink = $('<a href="' + contextPath + '/' + el.user.username + '"><p class="username">' + el.user.email + '</p></a>');
                    } else if (el.user.isGoogle == false) {
                        usernameLink = $('<a href="' + contextPath + '/' + el.user.username + '"><p class="username">' + el.user.username + '</p></a>');
                    }
                    pic.append(imgLink);
                    user.append(usernameLink);
                    var ahref = $('<a href="' + contextPath + "/post_subscriber/" + el.postId + '"></a>');
                    if (el.storageType == 'S3' && el.extFile == 'png') {
                        var imgpost = $('<img class="post-image" src="' + el.photo + '" alt="" />');
                    } else if (el.storageType != 'S3' && el.extFile == 'png') {
                        var imgpost = $('<img class="post-image" src="' + contextPath + "/resources/img/users/" + el.user.username + "/" + el.photo + '" alt="" />');
                    }
                    post.append(ahref);
                    ahref.append(imgpost);
                    if (el.extFile == 'mp4' || el.extFile == 'mov') {
                        var videopost = $('<video width = "100%" autoPlay controls loop muted alt=""></video>');
                        var videoSource = $('<source src="' + contextPath + "/resources/video/users/" + el.user.username + "/" + el.photo + '" type = video/mp4 >');
                        ahref.append(videopost);
                        videopost.append(videoSource);
                    }
                    var postContent = $('<div class="post-content"></div>');
                    var reactionWrapper = $('<div class="reaction-wrapper"></div>');
                    postContent.append(reactionWrapper);
                    var emptyDiv = $('<div></div>');
                    reactionWrapper.append(emptyDiv);
                    var likesAhref;
                    if (el.postLike.length > 0) {
                        el.postLike.forEach(elLikes => {
                            if (elLikes.userLikeId.username == usernameCurrent && isYourLike.includes(el.postId)) {
                                likesAhref = $('<a href="/likes/unlike_in_subscribers/' + el.postId + '"><img src="' + contextPath + '/resources/img/icons/like.png" class="icon" alt=""></a>');
                            }
                            if (elLikes.userLikeId.username != usernameCurrent && !isYourLike.includes(el.postId)) {
                                likesAhref = $('<a href="/likes/like_in_subscribers/' + el.postId + '"><img src="' + contextPath + '/resources/img/icons/notlike.png" class="icon" alt=""></a>');
                            }
                            emptyDiv.append(likesAhref);
                        });
                    }
                    if (el.postLike.length == 0) {
                        likesAhref = $('<a href="/likes/like_in_subscribers/' + el.postId + '"><img src="' + contextPath + '/resources/img/icons/notlike.png" class="icon" alt=""></a>');
                        emptyDiv.append(likesAhref);
                    }
                    var commentsIcon = $('<a href="/comments/comments_subscriber/' + el.postId + '"><img src="' + contextPath + '/resources/img/nav/comment.png" class="icon" alt=""></a>');
                    reactionWrapper.append(commentsIcon);
                    if (el.postLike.length > 0) {
                        var textCountLikes = $('<p class="likes">Нравится: ' + el.postLike.length + '</p>');
                        postContent.append(textCountLikes);
                    }
                    var desc = $('<b class="description_ms"></b>');
                    var span;

                    if (el.user.isGoogle == true) {
                        span = $('<span>' + el.user.email + '</span>');
                    } else if (el.user.isGoogle == false) {
                        span = $('<span>' + el.user.username + '</span>');
                    }
                    var content = $('<span class="description-normal" >' + el.content + '</span>');
                    var date_dmy = new Date(el.createdAt);
                    var date_result = date_dmy.getDate() + '.' + date_dmy.getMonth() + '.' + date_dmy.getFullYear();
                    var dateContent = $('<p class="post-time">' + date_result + ' г.</p>');
                    post.append(postContent);
                    postContent.append(desc);
                    desc.append(span);
                    postContent.append(content);
                    postContent.append(dateContent);
                    var commentView = $('<div class="view-source comments-view"></div>');
                    postContent.append(commentView);
                    if (el.comments.length > 0) {
                        var linkComments = $('<a href="/comments/comments_subscriber/' + el.postId + '" class="description comments">Смотреть все комментарии (' + el.comments.length + ')</a>');
                        commentView.append(linkComments);
                    }
                    var commentWrapper = $('<form class="comment-wrapper" method="POST" action="/comments/create_comment_detail_sub"></form>');
                    var inputComment = $('<input type="hidden" name="postId" value="' + el.postId + '"/>');
                    var infoView = $('<div class="info-view"></div>');
                    var userComment = $('<div class="user"></div>');
                    var profilePic = $('<div class="profile-pic"></div>');
                    if (userInfo.isGoogle == true) {
                        var userImg = $('<img src="' + userPhotoCurrent + '" alt="" />');
                    } else if (userInfo.isGoogle == false) {
                        var userImg = $('<img src="' + contextPath + "/resources/img/users/" + usernameCurrent + "/profile/" + userPhotoCurrent + '" alt="" />');
                    }
                    var inputCommentText = $('<input type="text" class="comment-box description" name="commentText" placeholder="Добавить комментарий..."><br>');
                    var buttonComment = $('<button class="comment-btn">Опубликовать</button>');
                    post.append(commentWrapper);
                    commentWrapper.append(inputComment);
                    commentWrapper.append(infoView);
                    infoView.append(userComment);
                    userComment.append(profilePic);
                    profilePic.append(userImg);
                    commentWrapper.append(inputCommentText);
                    commentWrapper.append(buttonComment);

                    $('#posts').append(post);
                });
            }
        }
    });
}

$(window).scroll(function () {
    if ($(window).scrollTop() + $(window).height() >= $(document).height()) {
        document.getElementById('next-page').classList.toggle('hidden', !(page < totalPages - 1));
        if (page < totalPages) {
            page++;
            posts_pagination();
        }
    }
});