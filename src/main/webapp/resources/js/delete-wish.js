function deleteWish(wishId) {
    fetch(contextPath + "/delete/" + wishId, {method: "DELETE"})
        .then((response) => {
            if (response.status == 200 && response.redirected != true){
                var element = document.getElementById("wishlist-" + wishId);
                element.parentNode.removeChild(element);
            }
        })
}