"use strict";

var backendAddress = "http://localhost:8080";

var request = function (address, id) {
    var self = ko.observableArray();

    self.url = address;
    self.postUrl = self.url;

    self.get = function () {
        if (self.sub)
            self.sub.dispose();

        var url = self.url;

        $.ajax({
            dataType: "json",
            url: url,
            success: function (data) {
                self.removeAll();

                data.forEach(function (element) {
                    var object = ko.mapping.fromJS(element);

                    self.push(object);
                });

                self.sub = self.subscribe(function (changes) {
                    changes.forEach(function (change) {
                        if (change.status === 'added') {
                            self.saveRequest(change.value);
                        }
                    });
                }, null, "arrayChange");
            }
        });
    };

    self.saveRequest = function (object) {
        $.ajax({
            url: self.postUrl,
            dataType: "json",
            contentType: "application/json",
            data: ko.mapping.toJSON(object),
            method: "POST",
            success: function (data) {
                var response = ko.mapping.fromJS(data);
                object[id](response[id]());

                object.links = [];
            }
        });
    };

    return self;
}

function viewModel() {
    var self = this;

    self.students = new request(backendAddress + "/students", "index");
    self.students.get();

    self.courses = new request(backendAddress + "/courses", "id");
    self.courses.get();
}

var viewModel = new viewModel();

$(document).ready(function () {
    ko.applyBindings(viewModel);
});
