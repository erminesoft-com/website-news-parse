gogoApp.service('entityService', function (config, $http, ngNotify) {

    console.log('core entityService initialized');

    // var token ='Bearer Y2FtcGZpaXJl;c232a8cb-171b-4d93-8ded-52a20e84848b';
    var token ='Bearer Y2FtcGZpaXJl;a95017af-6e3b-4ce4-a76b-2d5f1782c13e';

    this.get = function (targetUrl, resp) {
        console.log('get(' + targetUrl + ') works');
        $http({
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': token
            },
            url: targetUrl
        })
            .then(function successCallback(response) {
                console.log(targetUrl + ' responded status ' + response);
                console.log('Result response: ' + response);
                resp(response.data);
            }, function errorCallback(response) {
                console.warn('Result error: ' + response.error);
                alert('Something went wrong');
            });
    };

    this.parse = function (targetUrl, strategy, resp) {
        console.log('parse() works..');
        $http({
            url: targetUrl,
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': token
            },
            param: {
                'strategy': strategy
            }
        })
            .then(function successCallback(response) {
                console.log(targetUrl + ' responded status ' + response);
                console.log('Result response: ' + response);
                resp(response.data);
            }, function errorCallback(response) {
                console.warn('Result error: ' + response.error);
                alert('Something went wrong');
            });
    };

    this.post = function (targetUrl, newEntity, resp) {
        console.log('post() works..');
        $http({
            url: targetUrl,
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': token
            },
            data: newEntity
        })
            .then(function successCallback(response) {
                console.log(targetUrl + ' responded status ' + response);
                // console.log('Result response: ' + response);
                resp(response.data);
            }, function errorCallback(response) {
                console.warn('Result error: ' + response.error);
            });
    };


    this.delete = function (targetUrl, id, resp) {
        console.log('delete() works..' + targetUrl);
        $http({
            url: targetUrl,
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': token
            },
            params: {'id': id}
        })
            .then(function successCallback(response) {
                console.log(targetUrl + ' responded status ' + response);
                console.log('Result response: ' + response);
                resp(response.data);
            }, function errorCallback(response) {
                console.warn('Result error: ' + response.error);
                alert('Something went wrong');
            });
    };

    this.deleteSite = function (targetUrl, resp) {
        console.log('delete() works..' + targetUrl);
        $http({
            url: targetUrl,
            headers: {
                'Content-Type': 'application/json',
                'Authorization': token
            },
            method: 'DELETE'
        })
            .then(function successCallback(response) {
                console.log(targetUrl + ' responded status ' + response);
                console.log('Result response: ' + response);
                resp(response.data);
            }, function errorCallback(response) {
                console.warn('Result error: ' + response.error);
                alert('Something went wrong');
            });
    };

    this.put = function (targetUrl, entity, resp) {
        console.log('put() with: ' + entity);
        $http({
            url: targetUrl,
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': token
            },
            data: entity
        })
            .then(function successCallback(response) {
                console.log(targetUrl + ' responded status ' + response);
                console.log('Result response: ' + response);
                resp(response.data);
            }, function errorCallback(response) {
                console.warn('Result error: ' + response.error);
                alert('Something went wrong');
            });
    };

});
