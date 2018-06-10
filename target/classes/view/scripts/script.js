"use strict";

var students = ko.observableArray();
// myObservableArray.push('Some value');

// alert('The length of the array is ' + myObservableArray().length);
// alert('The first element is ' + myObservableArray()[0]);

// $.get( "http://localhost:8080/courses", function( data ) {
//     alert( "Data Loaded: " + data);
//     console.log(data)
// });

// $.getJSON('http://localhost:8080/students', function(data) {
//     console.log(data);
// });

$(document).ready(function () {
    $.ajax({
        type: 'GET',
        // accept: 'application/json',
        url: 'http://localhost:8080/',
        // data: { get_param: 'value' },
        success: function (data) {
            // $('#cand').html(data);
            ko.mapping.fromJS(data, students);
            console.log(data)
            // console.log(students);
        }
    });
});