var rootUrl = "http://xiaofei181818.picp.net:29705/"

var imageUrl = 'http://qr.liantu.com/api.php?text='

$(function () {
    init()
})

const tradeNo = function () {
    const now = new Date()
    const year = now.getFullYear();
    let month = now.getMonth() + 1;
    let day = now.getDate();
    let hour = now.getHours();
    let minutes = now.getMinutes();
    let seconds = now.getSeconds();
    String(month).length < 2 ? (month = Number("0" + month)) : month;
    String(day).length < 2 ? (day = Number("0" + day)) : day;
    String(hour).length < 2 ? (hour = Number("0" + hour)) : hour;
    String(minutes).length < 2 ? (minutes = Number("0" + minutes)) : minutes;
    String(seconds).length < 2 ? (seconds = Number("0" + seconds)) : seconds;
    const yyyyMMddHHmmss = `${year}${month}${day}${hour}${minutes}${seconds}`;
    return yyyyMMddHHmmss + '_' + Math.random().toString(36).substr(2, 9);
};

function init() {

    var mert_no = '20180808'
    var notify_url = 'http://www.baidu.com'
    var out_trade_no = 'XF' + tradeNo()
    var total_amount = '1.0'
    var key = 'ssjwiSHiwnznwi'
    var str =
        'mert_no='+ mert_no +
        '&notify_url='+ notify_url +'' +
        '&out_trade_no='+ out_trade_no +'' +
        '&total_amount='+ total_amount+'' +
        '&key=' + key

    var sign = str.MD5(32)
    console.log(str)
    console.log(sign.toUpperCase())


    // $.ajax({
    //     url: rootUrl + '/aliface',
    //     dataType:'json',
    //     type:'post',
    //     data:{
    //         out_trade_no:'27182738191292',
    //         total_amount:'1.0',
    //         sign:'A8290462767C1BED7103B4E2584BE687',
    //         mert_no:'20180808',
    //         notify_url:'http://www.baidu.com'
    //     },
    //     success:function (data) {
    //         $('#qr_code').attr('src',imageUrl + data.data)
    //     },
    //     error:function (e) {
    //         console.log(e)
    //     }
    // })
}