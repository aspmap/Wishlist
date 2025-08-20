function deleteTodo(todoId) {
    fetch(contextPath + "/todo/delete/" + todoId, {method: "DELETE"})
        .then((response) => {
            if (response.status == 200 && response.redirected != true){
                var element = document.getElementById("todo-" + todoId);
                element.parentNode.removeChild(element);
            }
        })
}