<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>左侧导航menu</title>
<link href="css/css.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="js/sdmenu.js"></script>
<script type="text/javascript">
	// <![CDATA[
	var myMenu;
	window.onload = function() {
		myMenu = new SDMenu("my_menu");
		myMenu.init();
	};
	// ]]>
</script>
<style type=text/css>
html{ SCROLLBAR-FACE-COLOR: #538ec6; SCROLLBAR-HIGHLIGHT-COLOR: #dce5f0; SCROLLBAR-SHADOW-COLOR: #2c6daa; SCROLLBAR-3DLIGHT-COLOR: #dce5f0; SCROLLBAR-ARROW-COLOR: #2c6daa;  SCROLLBAR-TRACK-COLOR: #dce5f0;  SCROLLBAR-DARKSHADOW-COLOR: #dce5f0; overflow-x:hidden;}
body{overflow-x:hidden; background:url(images/main/leftbg.jpg) left top repeat-y #f2f0f5; width:194px;}
</style>
</head>
<body onselectstart="return false;" ondragstart="return false;" oncontextmenu="return false;">
<div id="left-top">
	<div><img src="<%=basePath %>/images/main/member.gif" width="44" height="44" /></div>
    <span>用户：admin<br>角色：超级管理员</span>
</div>
    <div style="float: left" id="my_menu" class="sdmenu">
      <div class="collapsed">
        <span>系统设置</span>
        <a href="main.html" target="mainFrame" onFocus="this.blur()">后台首页</a>
        <a href="<%=path%>/servlet/RegisterAction?action_flag=listUser" target="mainFrame" onFocus="this.blur()">用户信息</a>
          <a href="<%=path%>/servlet/MessageAction?action_flag=listChoiceType" target="mainFrame" onFocus="this.blur()">添加药品</a>
        <a href="<%=path%>/servlet/MessageAction?action_flag=ListShopPCMessage" target="mainFrame" onFocus="this.blur()">药品信息</a>
        
         <a href="<%=path%>/formType.jsp" target="mainFrame" onFocus="this.blur()">添加分类</a>
        <a href="<%=path%>/servlet/MessageAction?action_flag=listTypeMessage" target="mainFrame" onFocus="this.blur()">药品分类</a>
        
        
        <a href="<%=path%>/servlet/MessageAction?action_flag=listShopOrderPcMessage" target="mainFrame" onFocus="this.blur()">订单信息</a>
        
        
      </div>
    </div>
</body>
</html>