"use strict";

var backendAddress = "http://localhost:8080/";

var collection = function (url) {
    var self = ko.observableArray();

    self.url = url;
    self.postUrl = self.url;

    self.get = function () {
        var url = self.url;

        $.ajax({
            url: url,
            dataType: "json",
            success: function (data) {
                data.forEach(function (element) {
                    var object = ko.mapping.fromJS(element);
                    console.log(object);

                    self.push(object);
                });
            }
        });
    };

    return self;
};

function viewModel() {
    var self = this;

    self.students = new collection(backendAddress + "students");
    self.students.get();

    self.courses = new collection(backendAddress + "courses");
    self.courses.get();

    self.grades = new collection(backendAddress + "grades");

    return self;
}

var model = new viewModel();

$(document).ready(function() {
    ko.applyBindings(model);
    console.log("Binding...");
});