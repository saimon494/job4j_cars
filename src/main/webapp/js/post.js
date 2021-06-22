$(document).ready(function () {
    auth();
    loadBrand();
    loadColor();
});

function auth() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/cars/auth',
        dataType: 'json'
    }).done(function (data) {
        if (data.userName === undefined) {
            window.location.href = 'http://localhost:8080/cars/index.html';
        }
    }).fail(function () {
        alert("Ошибка авторизации");
    });
}

function loadBrand() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cars/getBrand',
        dataType: 'json'
    }).done(function (data) {
        let options = '';
        data.forEach(el => options += '<option value=' + el.id + '>' + el.name + '</option>');
        $('#brand').append(options).prop('selectedIndex', -1);
    }).fail(function () {
        alert("Ошибка загрузки марки");
    });
}

function loadModel() {
    let brandId = $("#brand option:selected").val();
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cars/getModel',
        data: {id: brandId},
        dataType: 'json'
    }).done(function (data) {
        let options = '';
        data.forEach(el => options += '<option value=' + el.id + '>' + el.name + '</option>');
        $('#model').html(options).prop({'selectedIndex': -1, 'disabled': false});
        $('#body').html('').prop('disabled', true);
    }).fail(function () {
        alert("Ошибка загрузки модели");
    });
}

function loadBody() {
    let modelId = $("#model option:selected").val();
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cars/getBody',
        data: {id: modelId},
        dataType: 'json'
    }).done(function (data) {
        let options = '';
        data.forEach(el => options += '<option value=' + el.id + '>' + el.name + '</option>');
        $('#body').html(options).prop({'selectedIndex': -1, 'disabled': false});
    }).fail(function () {
        alert("Ошибка загрузки кузова");
    });
}

function loadColor() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cars/getColor',
        dataType: 'json'
    }).done(function (data) {
        let options = '';
        data.forEach(el => options += '<option value=' + el.id + '>' + el.name + '</option>');
        $('#color').html(options).prop('selectedIndex', -1);
    }).fail(function () {
        alert("Ошибка загрузки цвета");
    });
}

$(document).on('change', '.custom-file-input', function () {
    let fileName = $(this).val().split('\\').pop();
    $(this).next('.custom-file-label').addClass("selected").html(fileName);
});

function send() {
    let formData = new FormData();
    formData.append('brand', $('#brand').val());
    formData.append('model', $('#model').val());
    formData.append('body', $('#body').val());
    formData.append('mileage', $('#mileage').val());
    formData.append('color', $('#color').val());
    jQuery.each($('#file')[0].files, function (i, file) {
        formData.append('file', file);
    });
    // for (var value of formData.values()) {
    //     console.log(value);
    // }
    $.ajax({
        url: "http://localhost:8080/cars/add",
        type: "POST",
        cache: false,
        contentType: false,
        processData: false,
        data: formData,
    }).done(function () {
        window.location.href = 'http://localhost:8080/cars/index.html';
    }).fail(function () {
        alert("Ошибка добавления объявления");
    });
}

function validate() {
    let msg = $("#msg");
    if ($("#brand option:selected").val() === undefined) {
        msg.text('Выберите марку');
        return false;
    }
    if ($("#model option:selected").val() === undefined) {
        msg.text('Выберите модель');
        return false;
    }
    if ($("#body option:selected").val() === undefined) {
        msg.text('Выберите тип кузова');
        return false;
    }
    if ($("#color option:selected").val() === undefined) {
        msg.text('Выберите цвет');
        return false;
    }
    let mileage = $("#mileage");
    if (mileage.val() === '') {
        msg.text('Укажите пробег');
        return false;
    }
    if (!/^[1-9][0-9]*$/.test(mileage.val())) {
        msg.text('Некорректно указан пробег');
        return false;
    }
    return true;
}

function execute() {
    if (validate()) {
        send();
    }
}