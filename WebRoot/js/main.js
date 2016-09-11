/**
 * 判断开始字符是否是XX
 * 
 * @author zh
 */
$.startWith = function(source, str) {
	var reg = new RegExp("^" + str);
	return reg.test(source);
};

function getTime(/** timestamp=0 **/) {
    var ts = arguments[0] || 0;
    var t, y, m, d, h, i, s;
    t = ts ? new Date(ts * 1000) : new Date();
    y = t.getFullYear();
    m = t.getMonth() + 1;
    d = t.getDate();
    h = t.getHours();
    i = t.getMinutes();
    s = t.getSeconds();
    // 可根据需要在这里定义时间格式  
    return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d) + ' ' + (h < 10 ? '0' + h : h) + ':' + (i < 10 ? '0' + i : i) + ':' + (s < 10 ? '0' + s : s);
};

function myformatter(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
};

function myparser(s){
	if (!s) return new Date();
	var ss = (s.split('-'));
	var y = parseInt(ss[0],10);
	var m = parseInt(ss[1],10);
	var d = parseInt(ss[2],10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
		return new Date(y,m-1,d);
	} else {
		return new Date();
	}
};

function ww4(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	var h = date.getHours();
	return  y+'年'+(m<10?('0'+m):m)+'月'+(d<10?('0'+d):d)+'日'+(h<10?('0'+h):h)+'点';
};

function w4(s){
	var reg=/[\u4e00-\u9fa5]/  //利用正则表达式分隔
	var ss = (s.split(reg));
	var y = parseInt(ss[0],10);
	var m = parseInt(ss[1],10);
	var d = parseInt(ss[2],10);
	var h = parseInt(ss[3],10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d) && !isNaN(h)){
	  return new Date(y,m-1,d,h);
	} else {
	  return new Date();
	}
};

function ww3(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    var h = date.getHours();
    var min = date.getMinutes();
    var sec = date.getSeconds();
    var str = y+'/'+(m<10?('0'+m):m)+'/'+(d<10?('0'+d):d)+'/'+' '+(h<10?('0'+h):h)+':'+(min<10?('0'+min):min)+':'+(sec<10?('0'+sec):sec);
    return str;
};

function w3(s){
    if (!s) return new Date();
    var y = s.substring(0,4);
    var m =s.substring(5,7);
    var d = s.substring(8,10);
    var h = s.substring(11,14);
    var min = s.substring(15,17);
    var sec = s.substring(18,20);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d) && !isNaN(h) && !isNaN(min) && !isNaN(sec)){
    	return new Date(y,m-1,d,h,min,sec);
    } else {
    	return new Date();
    }
};

var Common = {

    //EasyUI用DataGrid用日期格式化
    TimeFormatter: function (value, rec, index) {
        if (value == undefined) {
            return "";
        }
        /*json格式时间转js时间格式*/
        value = value.substr(1, value.length - 2);
        var obj = eval('(' + "{Date: new " + value + "}" + ')');
        var dateValue = obj["Date"];
        if (dateValue.getFullYear() < 1900) {
            return "";
        }
        var val = dateValue.format("yyyy-mm-dd HH:MM");
        return val.substr(11, 5);
    },
    TimestampFormatter1: function (value, row, index) {
    	if (value == undefined) 
    		return "";
    	var unixTimestamp = new Date(value);
    	return unixTimestamp.toLocaleString();
    },
    TimestampFormatter2 : function(value, row, index) {
        var date = new Date(value);
        var year = date.getFullYear().toString();
        var month = (date.getMonth() + 1);
        var day = date.getDate().toString();
        var hour = date.getHours().toString();
        var minutes = date.getMinutes().toString();
        var seconds = date.getSeconds().toString();
        if (month < 10) {
            month = "0" + month;
        }
        if (day < 10) {
            day = "0" + day;
        }
        if (hour < 10) {
            hour = "0" + hour;
        }
        if (minutes < 10) {
            minutes = "0" + minutes;
        }
        if (seconds < 10) {
            seconds = "0" + seconds;
        }
        return year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
    },
    DateTimeFormatter: function (value, rec, index) {
        if (value == undefined) {
            return "";
        }
        /*json格式时间转js时间格式*/
        value = value.substr(1, value.length - 2);
        var obj = eval('(' + "{Date: new " + value + "}" + ')');
        var dateValue = obj["Date"];
        if (dateValue.getFullYear() < 1900) {
            return "";
        }

        return dateValue.format("yyyy-mm-dd HH:MM");
    },

    //EasyUI用DataGrid用日期格式化
    DateFormatter: function (value, rec, index) {
        if (value == undefined) {
            return "";
        }
        /*json格式时间转js时间格式*/
        value = value.substr(1, value.length - 2);
        var obj = eval('(' + "{Date: new " + value + "}" + ')');
        var dateValue = obj["Date"];
        if (dateValue.getFullYear() < 1900) {
            return "";
        }

        return dateValue.format("yyyy-mm-dd");
    },
    TitleFormatter : function (value, rec, index) {
        if (value.length > 10) value = value.substr(0, 8) + "...";
        return value;
    },
    LongTitleFormatter: function (value, rec, index) {
        if (value.length > 15) value = value.substr(0, 12) + "...";
        return value;
    }
};