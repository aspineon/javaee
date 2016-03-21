
    // create the module and name it scotchApp
    var bookcenter = angular.module('bookCenter', []);

    // create the controller and inject Angular's $scope
    bookcenter.controller('mainController', function($scope) {

        // create a message to display in our view
        $scope.message = 'To do implements in Angular.js';
    });