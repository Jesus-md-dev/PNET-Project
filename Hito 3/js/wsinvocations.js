function jsonToHtml(movie) {
    var keys = [];
    string = "";
    for(var k in movie) keys.push(k);
    for(var i = 0; i < Object.keys(movie).length; ++i)
    {
        string += "<p>" + keys[i] + ": " + movie[keys[i]] + "</p>"
    }
    return string;
}

function jsonsToHtml(movies) {
    string = "";
    for (var i = 0; i < Object.keys(movies).length; ++i) {
        string += jsonToHtml(movies[i]);
    }
    return string;
}

function getMovie(movieId) {
    var myUrl = "http://localhost:8080/movies/" + movieId;
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

function postMovie() {
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/movies/",
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

function getAllMovies() {
    var myUrl = "http://localhost:8080/movies/";
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

function deleteMovie(movieId) {
    var myUrl = "http://localhost:8080/movies/" + movieId;
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

function putMovie(movieId) {
    var myUrl = "http://localhost:8080/movies/" + movieId;
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

