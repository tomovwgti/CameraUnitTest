if ($$ === undefined) {
    var $$ = {};
}
$$.debug = true;

$(function() {
    // エラー処理は定型
    $(document.body).ajaxError(
            function(req, status, thrown) {
                $$.assert(false, 'error!\n' + req.status + "\n"
                        + req.statusText + "\n" + req.responseText);
            });
});

$$.log = function(message) {
    if ($$.debug !== true) {
        return;
    }
    if (window.console) {
        console.log(message);
    }
};

$$.assert = function(condition, message) {
    if ($$.debug !== true) {
        return;
    }
    if (!condition) {
        if (window.console) {
            console.error('assertion failure');
            if (message) {
                console.error(message);
            }
            if (console.trace) {
                console.trace();
            }
            if (Error().stack) {
                console.error(Error().stack);
            }
        }
        $$.alert('assertion failure');
    }
};

$$.assertHasValue = function(it, message) {
    $$.assert(it !== undefined && it !== null, message);
};

$$.ajax = function(options) {
    $$.assertHasValue(options);

    var retry = 3;
    var original = options.success;
    $$.assertHasValue(original);

    // dataがString(JSON)じゃなかったら変換する
    if (typeof options.data !== "string") {
        options.data = JSON.stringify(options.data);
    }

    var doit = function() {
        if ($.url.attr('port') === '8888' || $.url.attr('port') === '8080') {
            // 開発サーバはasync=falseで動作させる。Jettyがトラブるので
            options.async = false;
        }
        retry--;
        $.ajax(options);
    };
    options.success = function(data, dataType) {
        $$.assertHasValue(data);
        $$.assertHasValue(data.status);
        $$.assertHasValue(dataType);

        if (data.status === "OK") {
            original(data.body, dataType);
        } else if (data.status === "APPENGINE" && retry !== 0) {
            $$.log(data);
            doit();
        } else {
            $$.log(data);
            $$.alert(data.errorMessage);
        }
    };
    if (options.error === undefined) {
        options.error = function(status, error) {
            $$.alert('ajax error');
            $$.log(status);
            $$.log(error);
        }
    }

    doit();
};
