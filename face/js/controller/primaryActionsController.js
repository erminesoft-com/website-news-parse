gogoApp.controller('primaryActionsController', function (config, $scope, $window, service, ngNotify, ngProgressFactory) {
    console.log('core primaryActionsController initialized');

    $scope.progressbar = ngProgressFactory.createInstance();
    var blockFeedUrl = config.url_basic + config.one_block_feed;
    var titleUrl = config.url_basic + config.title;
    var linkUrl = config.url_basic + config.link;
    var imageUrl = config.url_basic + config.image;
    var descUrl = config.url_basic + config.desc;
    var timeUrl = config.url_basic + config.time;
    var listUrl = config.url_basic + config.list;
    var saveUrl = config.url_basic + config.save_config;
    var updateUrl = config.url_basic + config.update_config;
    var loadListUrl = config.url_basic + config.load_list;
    var loadConfigUrl = config.url_basic + config.load_config;
    var deleteSiteUrl = config.url_basic + config.delete_site;
    var fileNameUrl = config.url_basic + config.get_fileName;
    var downloadUrl = config.url_basic + config.download;
    var pushToUrlTry = config.url_basic + config.sendToUrlTry;
    var pushToEmail = config.url_basic + config.sendToEmail;

    $scope.title = {};
    $scope.title.rule = {};
    $scope.title.rule.id = undefined;
    $scope.title.rule.key = {};
    $scope.title.rule.strategy = undefined;
    $scope.title.rule.key.id = undefined;
    $scope.title.rule.key.one = undefined;
    $scope.title.rule.key.two = undefined;

    $scope.link = {};
    $scope.link.rule = {};
    $scope.link.rule.key = {};
    $scope.link.rule.id = undefined;
    $scope.link.rule.strategy = undefined;
    $scope.link.rule.key.id = undefined;
    $scope.link.rule.key.one = undefined;
    $scope.link.rule.key.two = undefined;

    $scope.image = {};
    $scope.image.rule = {};
    $scope.image.rule.id = undefined;
    $scope.image.rule.key = {};
    $scope.image.rule.strategy = undefined;
    $scope.image.rule.key.id = undefined;
    $scope.image.rule.key.one = undefined;
    $scope.image.rule.key.two = undefined;

    $scope.desc = {};
    $scope.desc.rule = {};
    $scope.desc.rule.id = undefined;
    $scope.desc.rule.key = {};
    $scope.desc.rule.strategy = undefined;
    $scope.desc.rule.key.id = undefined;
    $scope.desc.rule.key.one = undefined;
    $scope.desc.rule.key.two = undefined;

    $scope.time = {};
    $scope.time.rule = {};
    $scope.time.rule.id = undefined;
    $scope.time.rule.key = {};
    $scope.time.rule.strategy = undefined;
    $scope.time.rule.key.one = undefined;
    $scope.time.rule.key.two = undefined;
    $scope.time.rule.key.id = undefined;

    $scope.requestData = {};
    $scope.requestSendToUrl = {};
    $scope.requestSendToEmail = {};

    service.get(loadListUrl, function (data) {
        console.log('get all web sites');
        $scope.websiteList = data;
        $scope.initDataValue();
    });

    $scope.deleteSite = function (index, siteId) {
        console.log('delete site id - ' + siteId);
        console.log('delete site index - ' + index);
        service.deleteSite(deleteSiteUrl + siteId, function (data) {
            console.log('delete site id - ' + siteId);
            $scope.websiteList.splice(index, 1);
            if (data.data) {
                ngNotify.set('Site was deleted', {
                    type: "success",
                    position: 'top',
                    duration: 1500
                });
            }
            if ($scope.blockFeed.id !== null && siteId === $scope.blockFeed.id) {
                $scope.resetForm();
            }
        });
    };

    $scope.getFeedBlock = function (blockFeed) {
        $scope.progressbar.start();
        console.log('get blockFeed');
        service.post(blockFeedUrl, blockFeed, function (data) {
            $scope.oneFeed = '';
            $scope.sizeFeeds = '';
            $scope.oneFeed = data.feed;
            $scope.sizeFeeds = data.size;
            $scope.progressbar.complete();
        });
    };

    $scope.getTitle = function (blockFeed, title) {
        console.log('url - ' + titleUrl);
        $scope.requestData.block = blockFeed;
        $scope.requestData.title = title;
        service.post(titleUrl, $scope.requestData, function (data) {
            console.log('get one title ' + data.data);
            $scope.testTitle = data.data;
        });
    };

    $scope.getLink = function (blockFeed, link) {
        $scope.requestData.block = blockFeed;
        $scope.requestData.link = link;
        service.post(linkUrl, $scope.requestData, function (data) {
            console.log('Link path = ' + data.data);
            $scope.testLink = data.data;
        });
    };

    $scope.getImage = function (blockFeed, image) {
        $scope.requestData.block = blockFeed;
        $scope.requestData.image = image;
        service.post(imageUrl, $scope.requestData, function (data) {
            console.log('Image path = ' + data.data);
            $scope.testImage = data.data;
        });
    };

    $scope.getDesc = function (blockFeed, desc) {
        $scope.requestData.block = blockFeed;
        $scope.requestData.desc = desc;
        service.post(descUrl, $scope.requestData, function (data) {
            $scope.testDesc = data.data;
        });
    };

    $scope.getTime = function (blockFeed, time) {
        $scope.requestData.block = blockFeed;
        $scope.requestData.time = time;
        service.post(timeUrl, $scope.requestData, function (data) {
            $scope.testTime = data.data;
        });
    };

    $scope.getListArticle = function (blockFeed, title, link, image, desc, time) {
        $scope.progressbar.start();
        $scope.requestData.block = blockFeed;
        $scope.requestData.title = title;
        $scope.requestData.link = link;
        $scope.requestData.image = image;
        $scope.requestData.desc = desc;
        $scope.requestData.time = time;
        console.log('url - ' + listUrl);
        service.post(listUrl, $scope.requestData, function (data) {
            $scope.resultArticleList = data;
            $scope.progressbar.complete();
        });
    };

    $scope.saveConfig = function (blockFeed, title, link, image, desc, time) {
        console.log('Saving configuration for site - ' + blockFeed.site);
        if (blockFeed.name == null) {
            ngNotify.set('Error: site name is not set', {
                type: "error",
                position: 'top',
                duration: 1500
            });
            return;
        }
        $scope.requestData.block = blockFeed;
        $scope.requestData.title = title;
        $scope.requestData.link = link;
        $scope.requestData.image = image;
        $scope.requestData.desc = desc;
        $scope.requestData.time = time;
        console.log('url - ' + saveUrl);
        service.post(saveUrl, $scope.requestData, function (data) {
            if (data.error != null) {
                ngNotify.set('Error ' + data.error.message, {
                    type: "error",
                    position: 'top',
                    duration: 1500
                });
            } else {
                ngNotify.set('Site ' + blockFeed.name + ' has saved', {
                    type: "success",
                    position: 'top',
                    duration: 1500
                });
                var item = {
                    id: data.block.id,
                    name: data.block.name,
                    site: data.block.site
                };
                $scope.websiteList.push(item);
                $scope.resetForm();
            }
        });
    };

    $scope.updateConfig = function (blockFeed, title, link, image, desc, time) {
        console.log('updateConfig()');
        $scope.requestData.block = blockFeed;
        $scope.requestData.title = title;
        $scope.requestData.link = link;
        $scope.requestData.image = image;
        $scope.requestData.desc = desc;
        $scope.requestData.time = time;
        console.log('url - ' + saveUrl);
        service.post(updateUrl, $scope.requestData, function (data) {
            ngNotify.set('Site ' + blockFeed.name + ' has updated', {
                type: "success",
                position: 'top',
                duration: 1500
            });
            $scope.resetForm();
        });
    };




    $scope.loadConfig = function (siteId) {
        console.log('loadConfig - ' + siteId);
        service.get(loadConfigUrl + siteId, function (data) {
            $scope.blockFeed = data.block;

            if (data.title != null) {
                $scope.title.rule.enable = data.title.enable;
                if (data.title.enable) {
                    $scope.title.rule.id = data.title.id;
                }
                $scope.title.rule.default = data.title.default;
                if (data.title.strategy != null) {
                    $scope.title.rule.strategy = data.title.strategy;
                }

                if (data.title.key != null) {
                    $scope.title.rule.key.id = data.title.key.id;
                    $scope.title.rule.key.one = data.title.key.one;

                    if (data.title.key.two != null) {
                        $scope.title.rule.key.two = data.title.key.two;
                    }
                }
            }

            if (data.link != null) {
                $scope.link.rule.enable = data.link.enable;
                if (data.link.enable) {
                    $scope.link.rule.id = data.link.id;
                }
                $scope.link.rule.default = data.link.default;
                if (data.link.strategy != null) {
                    $scope.link.rule.strategy = data.link.strategy;
                }

                if (data.link.key != null) {
                    $scope.link.rule.key.id = data.link.key.id;
                    $scope.link.rule.key.one = data.link.key.one;

                    if (data.link.key.two != null) {
                        $scope.link.rule.key.two = data.link.key.two;
                    }

                    if (data.link.key.prefix != null) {
                        $scope.link.rule.prefix = true;
                        $scope.link.rule.key.prefix = data.link.key.prefix;
                    }
                }
            }

            if (data.image != null) {
                $scope.image.rule.enable = data.image.enable;
                if (data.image.enable) {
                    $scope.image.rule.id = data.image.id;
                }
                $scope.image.rule.default = data.image.default;
                if (data.image.strategy != null) {
                    $scope.image.rule.strategy = data.image.strategy;
                }
                if (data.image.key != null) {
                    $scope.image.rule.key.id = data.image.key.id;
                    if (data.image.key.link_default != null) {
                        $scope.image.rule.key.linkEnable = true;
                        $scope.image.rule.key.link_default = data.image.key.link_default;
                    }

                    if (data.image.key.one != null) {
                        $scope.image.rule.key.one = data.image.key.one;
                    }

                    if (data.image.key.two != null) {
                        $scope.image.rule.key.two = data.image.key.two;
                    }

                    if (data.image.key.prefix != null) {
                        $scope.image.rule.prefix = true;
                        $scope.image.rule.key.prefix = data.image.key.prefix;
                    }
                }
            }

            if (data.desc != null) {
                $scope.desc.rule.enable = data.desc.enable;
                if (data.desc.enable) {
                    $scope.desc.rule.default = data.desc.default;
                    $scope.desc.rule.id = data.desc.id;

                    $scope.desc.rule.default = data.desc.default;
                    if (data.desc.strategy != null) {
                        $scope.desc.rule.strategy = data.desc.strategy;
                    }
                    if (data.desc.key != null) {
                        $scope.desc.rule.key.id = data.desc.key.id;
                        $scope.desc.rule.key.one = data.desc.key.one;

                        if (data.desc.key.two != null) {
                            $scope.desc.rule.key.two = data.desc.key.two;
                        }
                    }
                }
            }

            if (data.time != null) {
                $scope.time.rule.enable = data.time.enable;
                if (data.time.enable) {
                    if (data.time.default != null) {
                        $scope.time.rule.default = data.time.default;
                        $scope.time.rule.id = data.time.id;
                    }
                    if (data.time.strategy != null) {
                        $scope.time.rule.strategy = data.time.strategy;
                    }

                    if (data.time.key != null) {
                        $scope.time.rule.key.id = data.time.key.id;
                        $scope.time.rule.key.one = data.time.key.one;
                        if (data.time.key.two != null) {
                            $scope.time.rule.key.two = data.time.key.two;
                        }
                    }
                }
            }
        });
    };

    $scope.getListArticleById = function (id) {
        $scope.progressbar.start();
        console.log('url - ' + listUrl + "/" + id);
        service.get(listUrl + "/" + id, function (data) {
            $scope.resultArticleList = data;
            $scope.progressbar.complete();
        });
    };

    $scope.getFile = function (id) {
        console.log('url - ' + fileNameUrl + id);
        service.get(fileNameUrl + id, function (data) {
            $scope.fileName = data.data;
            console.log('File name - ' + data.data);
            var url = downloadUrl + data.data;
            console.log('Download URL - ' + url);
            $window.location.href = url;
        });
    };

    $scope.sendPushToUrl = function (current) {
        $scope.requestSendToUrl.siteId = angular.copy(current);
        angular.element(document.querySelector('#sendToUrlModal')).modal('show');
    };

    $scope.sendPushToEmail = function (current) {
        $scope.requestSendToEmail.siteId = angular.copy(current);
        angular.element(document.querySelector('#sendToEmailModal')).modal('show');
    };

    $scope.sendToUrlTry = function (url, time, format, frequency) {
        console.log('sendToUrlTry - ' +  $scope.requestSendToUrl.siteId, url, time, format, frequency);
        $scope.requestSendToUrl.url = url;
        $scope.requestSendToUrl.time = time;
        $scope.requestSendToUrl.format = format;
        $scope.requestSendToUrl.frequency = frequency;
        service.post(pushToUrlTry, $scope.requestSendToUrl, function (data) {
            $scope.sendToUrlTryCheck = '';
            if (data.data == true) {
                $scope.sendToUrlTryCheck = true;
            } else {
                $scope.sendToUrlTryCheck = false;
            }
        });
    };

    $scope.saveConfigSendToUrl = function () {
        console.log('saveConfigSendToUrl - ' + pushToUrlTry);
        $scope.resetFormSendToUrl();
        angular.element(document.querySelector('#sendToUrlModal')).modal('hide');
    };

    $scope.sendEmail = function (dist, format) {
        console.log('saveConfigSendToEmail()');
        $scope.resetFormSendToEmail();
        var url = pushToEmail + "?id=" + $scope.requestSendToEmail.siteId + "&email=" + dist + "&format=" + format;
        console.log('saveConfigSendToEmail() url - ' + url);
        service.get(url, function (data) {
            if (data.data == true){
                console.log('Send to email complete.');
            }
        });
        angular.element(document.querySelector('#sendToEmailModal')).modal('hide');
    };

    $scope.resetFormSendToUrl = function () {
        $scope.sendToUrlTryCheck = undefined;
        $scope.url = undefined;
        $scope.format = undefined;
        $scope.frequency = undefined;
        $scope.time = undefined;
        $scope.requestSendToUrl = {};
        $scope.requestSendToUrl.url = undefined;
        $scope.requestSendToUrl.time = undefined;
        $scope.requestSendToUrl.format = undefined;
        $scope.requestSendToUrl.frequency = undefined;
    };

    $scope.resetFormSendToEmail = function () {
        $scope.email = {};
        $scope.email.dist = undefined;
        $scope.email.format = undefined;
    };

    $scope.resetForm = function () {
        $scope.title = {};
        $scope.title.rule = {};
        $scope.title.rule.id = undefined;
        $scope.title.rule.key = {};
        $scope.title.rule.strategy = undefined;
        $scope.title.rule.key.id = undefined;
        $scope.title.rule.key.one = undefined;
        $scope.title.rule.key.two = undefined;

        $scope.link = {};
        $scope.link.rule = {};
        $scope.link.rule.key = {};
        $scope.link.rule.id = undefined;
        $scope.link.rule.strategy = undefined;
        $scope.link.rule.key.id = undefined;
        $scope.link.rule.key.one = undefined;
        $scope.link.rule.key.two = undefined;

        $scope.image = {};
        $scope.image.rule = {};
        $scope.image.rule.id = undefined;
        $scope.image.rule.key = {};
        $scope.image.rule.strategy = undefined;
        $scope.image.rule.key.id = undefined;
        $scope.image.rule.key.one = undefined;
        $scope.image.rule.key.two = undefined;
        $scope.image.rule.key.linkEnable = undefined;
        $scope.image.rule.key.link_default = undefined;

        $scope.desc = {};
        $scope.desc.rule = {};
        $scope.desc.rule.id = undefined;
        $scope.desc.rule.key = {};
        $scope.desc.rule.strategy = undefined;
        $scope.desc.rule.key.id = undefined;
        $scope.desc.rule.key.one = undefined;
        $scope.desc.rule.key.two = undefined;

        $scope.time = {};
        $scope.time.rule = {};
        $scope.time.rule.id = undefined;
        $scope.time.rule.key = {};
        $scope.time.rule.strategy = undefined;
        $scope.time.rule.key.one = undefined;
        $scope.time.rule.key.two = undefined;
        $scope.time.rule.key.id = undefined;

        $scope.testTitle = undefined;
        $scope.testLink = undefined;
        $scope.testImage = undefined;
        $scope.testDesc = undefined;
        $scope.testTime = undefined;
        $scope.resultArticleList = undefined;
        $scope.oneFeed = undefined;

        $scope.blockFeed.name = undefined;
        $scope.blockFeed.id = undefined;
        $scope.blockFeed.strategy = undefined;
        $scope.blockFeed.site = undefined;
        $scope.blockFeed.pattern = undefined;
        $scope.blockFeed.key = undefined;
        $scope.blockFeed.second = undefined;
    };

    $scope.initDataValue = function () {
        $scope.faq = '<code>' +
            '{ <br> [ <br> "id" : int, <br> "title": String, <br> "description": String, <br> "image": String,<br> "date": String <br> ] <br> } </code>';

        $scope.timeDays = [
            {time: '00:00'},
            {time: '01:00 AM'},
            {time: '02:00 AM'},
            {time: '03:00 AM'},
            {time: '04:00 AM'},
            {time: '05:00 AM'},
            {time: '06:00 AM'},
            {time: '07:00 AM'},
            {time: '08:00 AM'},
            {time: '09:00 AM'},
            {time: '10:00 AM'},
            {time: '11:00 AM'},
            {time: '12:00 AM'},
            {time: '01:00 PM'},
            {time: '02:00 PM'},
            {time: '03:00 PM'},
            {time: '04:00 PM'},
            {time: '05:00 PM'},
            {time: '06:00 PM'},
            {time: '07:00 PM'},
            {time: '08:00 PM'},
            {time: '09:00 PM'},
            {time: '10:00 PM'},
            {time: '11:00 PM'}
        ]
    };

})
;