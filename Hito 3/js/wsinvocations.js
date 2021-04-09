function jsonToHtml(booking) {
    var keys = [];
    // string = "<div>";
    for(var k in booking) keys.push(k);
    for(var i = 0; i < Object.keys(booking).length; ++i)
    {
        string += "<p>" + keys[i] + ": " + booking[keys[i]] + "</p>"
    }
    // string += "</div>"
    return string;
}

function jsonsToHtml(bookings) {
    string = "";
    for (var i = 0; i < Object.keys(bookings).length; ++i) {
        string += jsonToHtml(bookings[i]);
    }
    return string;
}

function getBooking(movieId) {
    var myUrl = "http://localhost:8080/bookings/" + movieId;
    $.ajax({
        type: "GET",
        dataType: "json",
        url: myUrl,
        success: function(data) {
            $("#resPelicula").html(jsonToHtml(data[0]));
        },
        error: function(res) {
            alert("ERROR:" + res.statusText);
        }
    });
}

function postBooking() {
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/bookings/",
        contentType: "application/json",
        dataType: "text",
        data: JSON.stringify({
            "title": "Dunkirk",
            "director": "Christopher Nolan",
            "year": 2017
        }),
        success: function(data) {
            $("#resPelicula").html(data);
        },
        error: function(res) {
            alert("ERROR: " + res.statusText);
        }
    });
}

function getAllBookings() {
    var myUrl = "http://localhost:8080/bookings/";
    $.ajax({
        type: "GET",
        dataType: "json",
        url: myUrl,
        success: function(data) {
            $("#resPelicula").html(jsonsToHtml(data));
        },
        error: function(res) {
            alert("ERROR " + res.statusText);
        }
    });
}

function deleteBooking(movieId) {
    var myUrl = "http://localhost:8080/bookings/" + movieId;
    $.ajax({
        type: "DELETE",
        dataType: "text",
        url: myUrl,
        success: function (data) {
            $("#resPelicula").html(data);
        },
        error: function (res) {
            alert("ERROR " + res.statusText);
        }
    });
}

function putBooking(movieId) {
    var myUrl = "http://localhost:8080/bookings/" + movieId;
    $.ajax({
        type: "PUT",
        url: myUrl,
        contentType: "application/json",
        dataType: "text",
        data: JSON.stringify({
            "title": "Dunkirk",
            "director": "Christopher Nolan",
            "year": 2019
        }),
        success: function (data) {
            $("#resPelicula").html(data);
        },
        error: function (res) {
            alert("ERROR: " + res.statusText);
        }
    });
}

