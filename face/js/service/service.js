gogoApp.service('service', function (config, $http) {

    console.log('core service initialized');

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
