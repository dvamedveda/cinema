let x, y;
$(document).ready(setPlace);
$(document).ready(showHeader);

function showHeader() {
    let header = `Вы выбрали место ${y} в ряду ${x}, Сумма : 500 рублей.`;
    $("#payment_header").append(header);
}

function setPlace() {
    let place = getURLParameter('place');
    let coords = place.split("");
    x = coords[0];
    y = coords[1];
    $("#placex").attr("value", x - 1);
    $("#placey").attr("value", y - 1);
}

function getURLParameter(sParam) {
    let sPageURL = window.location.search.substring(1);
    let sURLVariables = sPageURL.split('&');
    for (let i = 0; i < sURLVariables.length; i++) {
        let sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam) {
            return typeof sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
        }
    }
    return false;
}

$("#payment_submit").click(
    function (event) {
        if (!validatePayment()) {
            event.preventDefault();
        } else {
            $("#payment_form").submit();
        }
    }
);

function validatePayment() {
    if ($("#username").val() == "") {
        if (!$.contains(document.querySelector('#name_input'), document.querySelector('#name_input > .alert'))) {
            $('#name_input').append('<div class="alert alert-danger" role="alert">' +
                'Нужно заполнить имя!' +
                '</div>');
        }
        $('#name_input > .alert').show().fadeOut(2000);
        return false
    } else if ($("#phone").val() == "") {
        if (!$.contains(document.querySelector('#tel_input'), document.querySelector('#tel_input > .alert'))) {
            $('#tel_input').append('<div class="alert alert-danger" role="alert">' +
                'Нужно заполнить номер телефона!' +
                '</div>');
        }
        $('#tel_input > .alert').show().fadeOut(2000);
        return false;
    }
    return true;
}