"use strict";

var backendAddress = "http://localhost:8080/";
var backendURL = "http://localhost:8080";

var collection = function (url, idAttr) {
    var self = ko.observableArray();

    self.url = url;
    self.postUrl = self.url;

    self.get = function (query) {
        var url = self.url;

        if (query) {
            url = url + query;
        }
        console.log(url);
        $.ajax({
            dataType: "json",
            url: url,
            success: function (data) {
                if (self.sub) {
                    self.sub.dispose();
                }
                self.removeAll();

                data.forEach(function (element) {
                    var object = ko.mapping.fromJS(element, {ignore: ["link"]});
                    object.links = [];

                    if ($.isArray(element.link)) {
                        element.link.forEach(function (link) {
                            object.links[link.params.rel] = link.href;
                        });
                    } else {
                        object.links[element.link.params.rel] = element.link.href;
                    }

                    self.push(object);

                    console.log("GET object: " + url + "/" + object[idAttr]());

                    ko.computed(function () {
                        return ko.toJSON(object);
                    }).subscribe(function () {
                        self.updateRequest(object);
                    });
                });

                self.sub = self.subscribe(function (changes) {
                    changes.forEach(function (change) {
                        if (change.status === 'added') {
                            self.saveRequest(change.value);
                        }
                        if (change.status === 'deleted') {
                            self.deleteRequest(change.value);
                        }
                    });
                }, null, "arrayChange");
            }
        });
    }

    self.saveRequest = function (object) {
        $.ajax({
            url: self.postUrl,
            dataType: "json",
            contentType: "application/json",
            data: ko.mapping.toJSON(object),
            method: "POST",
            success: function (data) {
                var response = ko.mapping.fromJS(data);
                console.log(response);
                console.log(idAttr);
                console.log(object[idAttr]);
                console.log(response[idAttr]);
                object[idAttr](response[idAttr]());

                object.links = [];

                if ($.isArray(data.link)) {
                    data.link.forEach(function (link) {
                        object.links[link.params.rel] = link.href;
                    });
                } else {
                    object.links[data.link.params.rel] = data.link.href;
                }

                ko.computed(function () {
                    return ko.toJSON(object);
                }).subscribe(function () {
                    self.updateRequest(object);
                });
            }
        });
    }

    self.updateRequest = function (object) {
        if (object['course'] != null) {
            console.log("PUT on " + backendURL + object.links['self'] + ":");
            console.log(object.course.id());

            object.course = ko.utils.arrayFirst(model.courses(), function (course) {
                console.log(course.id());
                if (object.course.id() === course.id()) {
                    return course;
                }
            });

            // console.log("After: " + object.course.name());
        }
        $.ajax({
            url: backendURL + object.links['self'],
            dataType: "json",
            contentType: "application/json",
            data: ko.mapping.toJSON(object, {ignore: ["links"]}),
            method: "PUT"
        });
    }

    self.deleteRequest = function (object) {
        console.log("DELETE on " + backendURL + object.links['self'] + ":");
        console.log(object);
        $.ajax({
            url: backendURL + object.links['self'],
            method: "DELETE"
        });
    }

    self.add = function (form) {
        console.log("POST ");
        var data = {};
        $(form).serializeArray().map(function (x) {
            console.log(x.value);
            data[x.name] = x.value;
        });
        data[idAttr] = null; // TODO - = null
        self.push(ko.mapping.fromJS(data));
        $(form).each(function () {
            this.reset();
        });

    }

    self.delete = function () {
        self.remove(this);
    }

    self.parseQuery = function () {
        // console.log(ko.mapping.toJS(self.queryParams['dateRelation']));
        // if(self.students.queryParams['dateRelation'] !== "" && self.queryParams['dateRelation'] !== null)
        //     self.queryParams['dateRelation'] = "equal";
        self.get('?' + $.param(ko.mapping.toJS(self.queryParams)));
    }

    return self;
}

function viewModel() {
    var self = this;

    self.students = new collection(backendAddress + "students", "index");
    self.students.getGrades = function () {
        window.location = "#student-grades-content";
        self.grades.selectedStudent(this.index());
        self.grades.selectedCourse(null);
        self.grades.isCourseEnable(true);
        self.grades.isStudentEnable(false);
        self.grades.url = backendAddress + "students/" + this.index() + "/grades";
        // self.courses.get();
        self.grades.get();
    }
    self.students.queryParams = {
        firstName: ko.observable(),
        lastName: ko.observable(),
        birthday: ko.observable(),
        dateRelation: ko.observable()
    }

    self.students.getIndex = ko.observable();
    self.indexTrigger = ko.computed(function () {
        if (self.students.getIndex() != undefined) {
            console.log("Trigger");
            self.students.get('/' + self.students.getIndex());
        }
    }, self);

    self.students.getRelG = ko.observable();
    self.relGTrigger = ko.computed(function () {
        if (self.students.getRelG() !== undefined) {
            self.students.getRelL(false);

            if (self.students.getRelG() === true)
                self.students.queryParams.dateRelation("grater");
            else
                self.students.queryParams.dateRelation("equal");
        }
        else
            self.students.queryParams.dateRelation("equal");
    }, self);

    self.students.getRelL = ko.observable();
    self.relLTrigger = ko.computed(function () {
        if (self.students.getRelL() !== undefined) {
            self.students.getRelG(false);

            if (self.students.getRelL() === true)
                self.students.queryParams.dateRelation("lower");
            else
                self.students.queryParams.dateRelation("equal");
        }
        else
            self.students.queryParams.dateRelation("equal");
    }, self);

    Object.keys(self.students.queryParams).forEach(function (key) {
        self.students.queryParams[key].subscribe(function () {
            self.students.parseQuery();
        });
    });
    self.students.get();

    self.courses = new collection(backendAddress + "courses", "id");
    self.courses.queryParams = {
        name: ko.observable(),
        lecturer: ko.observable()
    }
    Object.keys(self.courses.queryParams).forEach(function (key) {
        self.courses.queryParams[key].subscribe(function () {
            self.courses.parseQuery();
        });
    });
    self.courses.get();

    self.grades = new collection(backendAddress + "grades", "id");
    self.grades.selectedCourse = ko.observable();
    self.grades.selectedStudent = ko.observable();
    self.grades.isCourseEnable = ko.observable(true); // TODO - why???
    self.grades.isStudentEnable = ko.observable(true);

    self.grades.add = function (form) {
        self.grades.postUrl = backendAddress + 'students/' + self.grades.selectedStudent() + '/grades';
        var data = {};
        $(form).serializeArray().map(function (x) {
            data[x.name] = x.value;
        });

        console.log(self.grades.selectedCourse());
        data.course = ko.utils.arrayFirst(self.courses(), function (course) {
            if (course.id() === self.grades.selectedCourse()) {
                console.log("H" + course);
                console.log(course.id());
                return ko.mapping.toJS(course);
            }
        });

        data.id = self.grades.selectedCourse();
        console.log(data);
        self.grades.push(ko.mapping.fromJS(data));
        $(form).each(function () {
            this.reset();
        });
    }
    self.grades.queryParams = {
        noteQuery: ko.observable(),
        dateQuery: ko.observable()
    }
    Object.keys(self.grades.queryParams).forEach(function (key) {
        self.grades.queryParams[key].subscribe(function () {
            self.grades.parseQuery();
        });
    });
}

var model = new viewModel();

$(document).ready(function () {
    ko.applyBindings(model);
});
