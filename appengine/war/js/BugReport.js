if ($$ === undefined) {
    var $$ = {};
}
$$.bugReport = {};

$(document).ready(function() {
    if ($.url.attr('port') !== '8888') {
        // サーバで実行しているときは色々非表示にする
        $('#local-console').remove();
        $('#send-header').remove();
        $('#send').remove();
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

    var conv = function(data) {
        var result = [];
        $.each(data, function(i, item) {
            var r = [];
            r.push(item.model);
            r.push(item.setting);
            r.push(item.result);
            r.push(item.createdAt);
            var s = "";
            $.each(item.trace, function(i, data) {
                s += data.replace(/\n/g, "<br>");
                s += "<br>";
            });
            r.push(s);
            result.push(r);
        });
        return result;
    };

    root.html('');
    var table = $('<table width="99%"/>').appendTo(root);
    table.dataTable({
        "aaData" : conv(data),
        "aoColumns" : [
            {
                "sTitle" : "モデル",
                "sWidth" : "20%"
            },
            {
                "sTitle" : "設定",
                "sWidth" : "20%"
            },
            {
                "sTitle" : "結果",
                "sWidth" : "10%"
            },
            {
                "sTitle" : "送信日時",
                "sWidth" : "20%",
                "fnRender" : function(oObj) {
                    var pad = function(num) {
                        return ("0" + num).slice(-2);
                    };

                    var d = new Date(oObj.aData[oObj.iDataColumn]);
                    var s = d.getFullYear() + "/" + pad(d.getMonth() + 1) + "/"
                            + pad(d.getDate()) + " " + pad(d.getHours()) + ":"
                            + pad(d.getMinutes());

                    return s;
                }
            },
            {
                "sTitle" : "スタックトレース",
                "sWidth" : "30%"
            }
        ],
        "aaSorting" : [
            [ 3, 'desc' ]
        ],
        "bJQueryUI" : true,
        "sPaginationType" : "full_numbers",
        "aLengthMenu" : [
            [ 10, 25, 50, -1 ],
            [ 10, 25, 50, "全て" ]
        ]
    });

    return root;
};