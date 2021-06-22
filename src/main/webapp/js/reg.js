function validate() {
    let args = [$('#name'), $('#email'), $('#password')];
    let div = '';
    for (let i = 0; i < args.length; i++) {
        if (args[i].val() === '') {
            div += '<div class="alert alert-danger mt-3 mb-0" role="alert" id="msg">'
                + 'Заполните поле ' + args[i].attr('title') + '</div>'
            $('p').html(div);
            return false;
        }
    }
    return true;
}

function reg() {
    if (validate() === true) {
        let name = $('#name');
        let email = $('#email');
        let password = $('#password');
        let div = '';
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/cars/reg',
            data: {name: name.val(), email: email.val(), password: password.val()},
            dataType: 'json'
        }).done(function(data) {
            if (data.result === true) {
                window.location.href = 'http://localhost:8080/cars/login.html';
            } else {
                div += '<div class="alert alert-danger mt-3 mb-0" role="alert" id="msg">' + data.msg + '</div>';
                $('p').html(div);
            }
        }).fail(function(){
            alert("Ошибка регистрации");
        });
    }
}