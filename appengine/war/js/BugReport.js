if ($$ === undefined) {
    var $$ = {};
}
$$.bugReport = {};

$(document).ready(function() {
    if ($.url.attr('port') === '8888' || $.url.attr('port') === '8080') {
        // ローカルで実行しているときはなにもしない
    } else {
        // サーバで実行しているときはローカル用管理コンソールリンクを消す
        $('#local-console').remove();
    }

    $('#submit').click($$.bugReport.post);
    $$.bugReport.fetch();
});

$$.bugReport.post = function() {

    var data = {
        model: $('#model').val(),
        setting: $('#setting').val(),
        trace: [$('#trace1').val(), $('#trace2').val()],
        result: $('#result').val()
    };

    $$.ajax({
        type: 'post',
        dataType: 'json',
        data: data,
        url: '/bugReport',
        success: function(data) {
            alert("送信しました!");
            $$.bugReport.fetch();
        }
    });
};

$$.bugReport.fetch = function() {
    $$.ajax({
        type: 'get',
        dataType: 'json',
        data: {},
        url: '/bugReport',
        success: function(data) {
            $$.log(data);
            $$.bugReport.bugReportRender(data);
        }
    });
};

$$.bugReport.bugReportRender = function(data) {
    var root = $('#receive');
    if (data.length === 0) {
        root.html('データがないよ');
        return root;
    }

    var rn = function(item, parent) {
        var tr = $('<tr/>').appendTo(parent);
        $('<td/>').append(item.model).appendTo(tr);
        $('<td/>').append(item.setting).appendTo(tr);
        $('<td/>').append(item.result).appendTo(tr);
        $('<td/>').append(new Date(item.createdAt).toString()).appendTo(tr);
        var td = $('<td/>').appendTo(tr);
        for (var i = 0; i < item.trace.length; i++) {
            $('<div/>').append(item.trace[i]).appendTo(td);
        }
    };

    root.html('');
    var table = $('<table border="1"/>').appendTo(root);

    var tr = $('<tr/>').appendTo(table);
    $('<th/>').append("モデル").appendTo(tr);
    $('<th/>').append("設定").appendTo(tr);
    $('<th/>').append("結果").appendTo(tr);
    $('<th/>').append("送信日時").appendTo(tr);
    $('<th/>').append("スタックトレース").appendTo(tr);

    $.each(data, function(i, item) {
        rn(item, table);
    });

    return root;
};