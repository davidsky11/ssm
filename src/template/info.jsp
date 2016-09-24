<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width"/>
	<meta name="viewport" content="initial-scale=1.0,user-scalable=no"/>
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<!-- <link rel="stylesheet" rev="stylesheet" type="text/css" href="css/css.css" media="all"/> -->
	<title>###title###</title>
	
<style type="text/css">
* {
	margin: 0;
	padding: 0;
	list-style: none;
}

input[type="button"], input[type="submit"], input[type="reset"] {
	-webkit-appearance: none;
}

a {
	text-decoration: none;
	color: #000;
}

.body, html {
	font-family: "\5FAE\8F6F\96C5\9ED1";
	width: 100%;
	max-width: 640px;
	min-width: 320px;
	margin: 0px auto;
	margin-left: auto;
	margin-right: auto;
	text-align: center;
}

.main {
	width: 100%;
	max-width: 640px;
	min-width: 320px;
	margin: 0px auto;
	margin-left: auto;
	margin-right: auto;
	text-align: center;
}

.msg {
	width: 100%;
	margin: 10px auto;
	text-align: center;
	font-size: 12px;
	color: #ccc;
	bottom: 0px;
}

.foot {
	width: 100%;
	margin: 0px auto;
	text-align: center;
	font-size: 12px;
	color: #666;
	bottom: 0px;
	position: fixed;
	background: #e6e6e6;
	height: 55px;
	width: 100%;
	max-width: 640px;
	min-width: 320px;
	margin: 0px auto;
	margin-left: auto;
	margin-right: auto;
	text-align: center;
}

.foot table {
	text-align: center;
	margin: 0px auto;
	width: 100%;
	margin-top: 5px;
}

.index_bj {
	width: 100%;
	max-width: 640px;
	min-width: 320px;
	margin: 0px auto;
	margin-left: auto;
	margin-right: auto;
	text-align: center;
}

.index_main {
	width: 100%;
	top: 85%;
	color: #FFF;
	position: absolute;
	max-width: 640px;
	min-width: 320px;
	margin: 0px auto;
	text-align: center;
}

.main-button-text {
	width: 100%;
	height: 50px;
	border: #ccc 1px solid;
	border-radius: 5px;
	font-family: "\5FAE\8F6F\96C5\9ED1";
	color: #000;
	font-size: 14px;
}

.main-button {
	width: 100%;
	height: 50px;
	border: #ffab4d 1px solid;
	border-radius: 5px;
	font-family: "\5FAE\8F6F\96C5\9ED1";
	color: #fff;
	font-size: 14px;
	background: url(../img/huang.png);
	margin-top: 20px;
}

.index_main p {
	text-align: left;
	font-size: 14px;
	width: 80%;
	margin: 0px auto;
	margin-top: 20px;
}

.nav {
	width: 100%;
	height: 50px;
	background: #679eff;
	line-height: 50px;
	font-size: 14px;
	color: #fff;
}

.one {
	width: 90%;
	margin: 0px auto;
	text-align: center;
}

.one-left {
	float: left;
	width: 48%;
	border: #dedede 1px solid;
	height: auto;
	padding-bottom: 5px;
	margin-top: 20px;
}

.one-right {
	float: right;
	width: 48%;
	border: #dedede 1px solid;
	height: auto;
	padding-bottom: 5px;
	margin-top: 20px;
}

.one-text {
	width: 95%;
	margin: 10px auto;
}

.one-text h3 {
	font-size: 14px;
	text-align: left;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}

.one-text p {
	text-align: left;
	font-size: 14px;
	color: #ff461f;
	width: 100%;
	float: left;
	line-height: 30px;
}

.one-text p span {
	font-size: 12px;
	text-align: right;
	color: #666;
	float: right;
}

.xq-one {
	width: 95%;
	margin: 10px auto;
	background: #fff;
	height: auto;
	padding-bottom: 10px;
	padding-top: 10px;
}

.xq-one h3 {
	font-size: 14px;
	text-align: left;
	width: 90%;
	margin: 0px auto;
	color: #666;
	border-bottom: #f0f0f0 1px solid;
}

.xq-one p {
	text-align: left;
	font-size: 16px;
	color: #ff461f;
	line-height: 30px;
	width: 90%;
	margin: 0px auto;
	font-weight: 900;
}

.xq-one p span {
	font-size: 12px;
	color: #666;
	font-weight: 100;
}

h2 {
	font-size: 16px;
	line-height: 40px;
	color: #679eff;
}

h2 span {
	font-size: 18px;
	color: #ff461f;
}

.time {
	width: 95%;
	margin: 0px auto;
	background: #fff;
	height: auto;
	padding-bottom: 10px;
	padding-top: 10px;
	border: #ccc 1px solid;
	height: auto;
}

.time-left span {
	color: #ff461f;
}

.time-left {
	float: left;
	font-size: 14px;
	color: #666;
	width: 50%;
}

.time-right {
	float: right;
	font-size: 14px;
	color: #666;
	width: 50%;
}

.anniu {
	width: 80%;
	margin: 0px auto;
}

.hezi1 {
	width: 100%;
	background-color: #000000;
	display: inline;
	z-index: 3;
}

.paohui {
	width: 100%;
	height: 100%;
	position: absolute;
	z-index: 1000;
	display: none;
	top: 0px;
	left: 50%;
	margin-left: -50%;
	min-width: 320px;
}

.cg-button {
	width: 100%;
	height: 50px;
	border: #aad95a 1px solid;
	border-radius: 5px;
	font-family: "\5FAE\8F6F\96C5\9ED1";
	color: #fff;
	font-size: 14px;
	background: url(../img/lv.png);
	margin-top: 20px;
}

.xq-one h4 {
	font-size: 14px;
	text-align: left;
	width: 90%;
	margin: 0px auto;
	color: #00bc12;
}

.anniu input {
	margin-top: 20px;
}

tb960x90 {
	display: none !important;
	display: none
}
</style>
</head>
<body style="background:#79c59d;">
<img src="###img###" width="100%" height="100%" class="index_bj" /> <!-- upload/img/bj.png -->
	<div class="index_main">
        <p>活动规则</p>
        <p>简单快速，只需要分享到朋友圈或邀请好友帮忙就能获得助力指数。</p>
        <p>病毒式传播，邀请小伙伴一起参与，好友只需点击活动链接就能帮小伙伴增加助力指数，大大增加获取奖品的机会。</p>
        <p>简单快速，活动模式多样，“排行榜模式”和“先到先得模式”，排行榜模式活动持续性较长，引发关注多；先到先得模式短时间内即可引爆朋友圈，商家可根据自己的需求进行选。</p>
        
        ###content###
        <div style="width:100%; height:100px;"></div>
    </div>
    <!-- <div class="foot">
        <table>
            <tr>
                <td><a href="index.html"><img src="img/d1.png" width="20"/><br/>活动</a></td>
                <td><a href="list.html"><img src="img/d2.png" width="20"/><br/>助力</a></td>
                <td><a href="my.html"><img src="img/d3.png" width="20"/><br/>我的</a></td>
            </tr>
        </table>
    </div> -->
    <div style="clear:both;"></div>
      
</body>
</html>
