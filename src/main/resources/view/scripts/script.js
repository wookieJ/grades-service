"use strict";

var myObservableArray = ko.observableArray();
myObservableArray.push('Some value');

// alert('The length of the array is ' + myObservableArray().length);
// alert('The first element is ' + myObservableArray()[0]);

$.get( "http://localhost:8080/courses", function( data ) {
    alert( "Data Loaded: " + data);
    console.log(data)
});