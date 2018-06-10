"use strict";

var students = ko.observableArray();

$(document).ready(function () {
    $.ajax({
        type: 'GET',
        headers: {
            Accept: "application/json; charset=utf-8"
        },
        url: 'http://localhost:8080/students',
        success: function (data) {
            ko.mapping.fromJS(data, students);
            console.log(data)
        }
    });
});