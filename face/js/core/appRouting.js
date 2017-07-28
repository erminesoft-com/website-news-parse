gogoApp.config(function ($locationProvider, $routeProvider) {

    $routeProvider
        .when('/parser/native', {
            templateUrl: 'pages/parser.html',
            controller: 'primaryActionsController'
        })
        .when('/parser/library', {
            templateUrl: 'pages/library.html',
            controller: 'primaryActionsController'
        })
        .when('/info', {
            templateUrl: 'pages/info.html',
            controller: 'primaryActionsController'
        })
        .otherwise({
            redirectTo: '/info'
        });
});