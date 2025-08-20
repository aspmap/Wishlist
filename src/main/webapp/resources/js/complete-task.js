function completeTask(toDoId) {
    fetch(contextPath + "/todo/" + toDoId + "/complete", {method: "GET"})
        .then((response) => {
            if (response.status == 200 && response.redirected != true){
                var element = document.getElementById("todo-" + toDoId);
                element.parentNode.removeChild(element);
            }
        })
}