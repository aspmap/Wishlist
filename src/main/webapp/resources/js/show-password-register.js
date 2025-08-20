function show_hide_password(target){
    var input = document.getElementById('password');
    var input_repeat = document.getElementById('password_repeat');
    if (input.getAttribute('type') == 'password' && input_repeat.getAttribute('type') == 'password') {
        target.classList.add('view');
        input.setAttribute('type', 'text');
        input_repeat.setAttribute('type', 'text');
    } else {
        target.classList.remove('view');
        input.setAttribute('type', 'password');
        input_repeat.setAttribute('type', 'password');
    }
    return false;
}