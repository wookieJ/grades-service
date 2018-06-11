"use strict";

var backendAddress = "http://localhost:8080/";

var collection = function (url, idAttr) {
    var self = ko.observableArray();

    self.url = url;
    self.postUrl = self.url;

    self.get = function () {
        var url = self.url;

        $.ajax({
            url: url,
            dataType: "json",
            success: function (data) {
                data.forEach(function (element, index, array) {
                    var object = ko.mapping.fromJS(element, {ignore: ["link"]});
                    console.log(object);
                    object.links = [];

                    if ($.isArray(element.link)) {
                        element.link.forEach(function (link) {
                            object.links[link.params.rel] = link.href;
                        });
                    } else {
                        object.links[element.link.params.rel] = element.link.href;
                    }

                    self.push(object);
                });
            }
        });
    };

    return self;
};

function viewModel() {
    var self = this;

    self.students = new collection(backendAddress + "students", "index");
    self.students.get();

    self.courses = new collection(backendAddress + "courses", "id");
    self.courses.get();

    self.grades = new collection(backendAddress + "grades", "id");

    return self;
}

var model = new viewModel();

$(document).ready(function() {
    ko.applyBindings(model);
    console.log("Binding...");
});