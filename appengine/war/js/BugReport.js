if ($$ === undefined) {
    var $$ = {};
}
$$.bugReport = {};

$(document).ready(function() {
    $$.bugReport.fetch();
});

$$.bugReport.fetch = function() {
    $$.ajax({
        type: 'post',
        dataType: 'json',
        data: {
            model: "HTC Desire",
            setting: "abc-operation",
            trace: ["1", "2", "3", "だー！"],
            result: "OK"
        },
        url: '/bugReport',
        success: function(data) {
            $$.log(data);
            alert(data);
        }
    });
};

$$.bugReport.post = function() {
    $$.ajax({
        type: 'get',
        dataType: 'json',
        data: {},
        url: '/bugReport',
        success: function(data) {
            $$.log(data);
            alert(data);
        }
    });
};