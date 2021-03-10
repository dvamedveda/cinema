const REFRESH_DELAY = 30000;
$(document).ready(getPlaces);
$(document).ready(autoRefresh);
$(document).ready(checkFailed)

function getPlaces() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cinema/ajax/places.do',
        dataType: 'json'
    }).done(function (data) {
        let response = JSON.parse(JSON.stringify(data));
        let places = data[0];
        $("#table_head_row").empty().append('<th style="width: 120px;">Ряд / Место</th>');
        $("#table_body").empty();
        for (let place of places) {
            let placeNumber = places.indexOf(place) + 1;
            $("#table_head_row").append('<th>' + placeNumber + '</th>');
        }
        for (let row of data) {
            $("#table_body").append('<tr></tr>');
            let rowNumber = data.indexOf(row) + 1;
            $(`#table_body>:nth-child(${rowNumber})`).append('<th>' + rowNumber + '</th>');
            for (let place of row) {
                let placeNumber = row.indexOf(place) + 1;
                let placeObject = data[rowNumber - 1][placeNumber - 1];
                let disabled = placeObject.reserved ? "disabled" : "";
                let value = rowNumber + "" + placeNumber;
                let text = `Место ${placeNumber} в ряду ${rowNumber}`;
                let cellColor = placeObject.reserved ? "#ff8080" : "#80ff80";
                let htmlPlace = `<td style="background-color:${cellColor}"><input type="radio" name="place" value="${value}" ${disabled}>${text}</td>`;
                $(`#table_body>:nth-child(${rowNumber})`).append(htmlPlace)
            }
        }
    }).fail(function (error) {
        console.log("Что-то пошло не так! Запрос не выполнился!")
    })
}

function refresh() {
    getPlaces();
    setTimeout(refresh, REFRESH_DELAY);
}

function autoRefresh() {
    setTimeout(refresh, REFRESH_DELAY);
}

$("#submit_hall_places").click(
    function (event) {
        if ($('input[name="place"]:checked').length == 0) {
            alert("Нужно выбрать какое-то место, прежде чем переходить к оплате!");
            event.preventDefault();
        } else {
            $("#hall_places").submit();
        }
    }
);

function checkFailed() {
    if (getURLParameter("result") == "failed") {
        if (!$.contains(document.querySelector('#main_row'), document.querySelector('#main_row > .alert'))) {
            $('#hall_places').before('<div class="alert alert-danger" role="alert">' +
                'Кто-то уже купил выбранное вами место!' +
                '</div>');
        }
        $('#main_row > .alert').show().fadeOut(2000);
    }
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