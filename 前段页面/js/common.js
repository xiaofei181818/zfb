
var rootUrl = "http://localhost:8888"

$(function () {
    init()
})

function init() {
    $.ajax({
        url: rootUrl + '/findAll' + '',
        dataType:'json',
        type:'get',
        success:function (data) {
            console.log(data)
            fillTable(data)
        },
        error:function (e) {
            console.log(e)
        }
    })
}

function fillTable(data) {
    var html = '<table class="hovertable"><tr><th>序号</th><th>下单时间</th><th>订单号</th>' +
        '<th>渠道</th><th>金额</th><th>支付状态</th><th>回调状态</th><th>次数</th></tr>'
    for(var i in data){
        html += '<tr onmouseover="this.style.backgroundColor=\'#ffff66\';" onmouseout="this.style.backgroundColor=\'#d4e3e5\';">'
        html += '<td>' + (parseInt(i) + 1) + '</td>'
        html += '<td>' + data[i].create + '</td>'
        html += '<td>' + data[i].out_trade_no + '</td>'
        if (parseInt(data[i].channel) == 0){
            html += '<td>支付宝手机</td>'
        }else if (parseInt(data[i].channel) == 1) {
            html += '<td>微信</td>'
        }else if (parseInt(data[i].channel) == 2){
            html += '<td>支付宝扫码</td>'
        }
        html += '<td>' + data[i].amount + '</td>'
        if (parseInt(data[i].status) == 0){
            html += '<td>未支付</td>'
        }else {
            html += '<td>已支付</td>'
        }
        if (parseInt(data[i].call_back) == 0){
            html += '<td>未回调</td>'
        }else {
            html += '<td>已回调</td>'
        }
        html += '<td>' + data[i].call_no + '</td>'
        html += '</tr>'
    }
    html += '</table>'
    $('#mmm').html(html)
}