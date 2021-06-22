$(document).ready(function () {
    auth();
    loadPost();
});

function auth() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/cars/auth',
        dataType: 'json'
    }).done(function (data) {
        let a1 = $('#a1');
        let a2 = $('#a2');
        let add = $('#add');
        let userId = $('#userId');
        if (data.userName === undefined) {
            a1.text("Войти").attr("href", "http://localhost:8080/cars/login.html");
            a2.text("Регистрация").attr("href", "http://localhost:8080/cars/reg.html");
            add.prop('hidden', true);
            userId.val(-1);
        } else {
            a1.text(data.userName);
            a2.text("Выйти").attr("onclick", "logout();return false;");
            add.prop('hidden', false);
            userId.val(data.userId);
        }
    }).fail(function () {
        alert("Ошибка авторизации");
    });
}

function logout() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/cars/logout',
    }).done(function () {
        auth();
        loadPost();
    }).fail(function () {
        alert("Ошибка выхода");
    });
}

function loadPost() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cars/getPost',
        dataType: 'json'
    }).done(function (data) {
        let table = '';
        let userId = $('#userId').val();
        data.forEach(el => {
            table += '<tr>';
            let img = (el.photo !== null) ? '<img src=http://localhost:8080/cars/download?photoId='
                + el.photo.id + ' width="80px" height="80px"/>' : '';
            table += '<td>' + img + '</td>';
            table += '<td>' + el.brand.name + '</td>';
            table += '<td>' + el.model.name + '</td>';
            table += '<td>' + el.body.name + '</td>';
            table += '<td>' + el.color.name + '</td>';
            table += '<td>' + el.mileage + '</td>';
            table += '<td>' + el.user.name + '</td>';

            let status = (el.status === true) ? 'Активно' : 'Продано';
            table += '<td>' + status + '</td>';

            let created = el.created;
            table += '<td>' + created.hour + ':' + created.minute + ' '
                + created.dayOfMonth + '.' + created.monthValue + '.' + created.year + '</td>';

            if (userId == el.user.id) {
                let checked = (el.status === true) ? 'checked' : '';
                table += '<td><label><input type="checkbox" ' + checked
                    + ' onchange="changeStatus(' + el.id + ', this)"> Активно</label></td>';
                table += '<td><a href=# onclick=del(' + el.id + ')><i class="fa fa-trash" ></i></a></td>';
            } else {
                table += '<td></td>';
                table += '<td></td>';
            }
            table += '</tr>';
        });
        $('#table tbody').html(table);
    }).fail(function () {
        alert("Ошибка загрузки объявления");
    });
}

function del(id) {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/cars/delete',
        data: {id: id},
    }).done(function () {
        loadPost();
    }).fail(function () {
        alert("Ошибка удаления объявления");
    });
}

function changeStatus(id, el) {
    let check = el.checked;
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/cars/changeStatus',
        data: {id: id, check: check},
    }).done(function () {
        loadPost();
    }).fail(function () {
        alert("Ошибка смены статуса объявления");
    });
}
