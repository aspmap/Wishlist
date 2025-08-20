function deletePost(postId) {
	fetch(contextPath + "/post/delete/" + postId, {method: "DELETE"})
  	 .then((response) => {
    	if (response.status == 200 && response.redirected != true){
    		var element = document.getElementById("post-" + postId);
    		element.parentNode.removeChild(element);
    	}
 	 })
}