<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	List<Map<String, Object>> listMessage = (List<Map<String, Object>>)request.getAttribute("listMessage");
	Map<String, Object> map = (Map<String, Object>) request.getAttribute("map");
%>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>添加药品</title>
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<link href="../css/select.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/jquery.idTabs.min.js"></script>
<script type="text/javascript" src="../js/select-ui.min.js"></script>
<script type="text/javascript" src="../editor/kindeditor.js"></script>
<script type="text/javascript" src="../js/zooming.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".click").click(function() {
			$(".tip").fadeIn(200);
		});

		$(".tiptop a").click(function() {
			$(".tip").fadeOut(200);
		});

		$(".sure").click(function() {
			$(".tip").fadeOut(100);
		});

		$(".cancel").click(function() {
			$(".tip").fadeOut(100);
		});

	});
</script>

<script type="text/javascript">
	$(document).ready(function(e) {
		$(".select1").uedSelect({
			width : 345
		});
		$(".select2").uedSelect({
			width : 167
		});
		$(".select3").uedSelect({
			width : 100
		});
	});
</script>


	<script>
	function showImg(fileObj,imgId) {
 
    var file=fileObj.files[0];
           var r = new FileReader();
                r.readAsDataURL(file);
                $(r).load(function() {
                    $('#'+imgId).attr("src", this.result);
            })                   
     
}
	</script>

 
<script type="text/javascript">
 function dosubmit(){
   var th = document.form2;
   th.action="<%=path%>/servlet/MessageAction?action_flag=updateShop&shopId=<%=map.get("shopId")%>";
		th.submit();
	}
</script>
</head>

<body>
	<form name="form2" action="" method="post"  enctype="multipart/form-data">
		<div class="place">
			<span>位置：</span>
			<ul class="placeul">
				<li><a href="#">首页</a></li>
				<li><a href="#">表单</a></li>
			</ul>
		</div>

		<div class="formbody">

			<div class="formtitle">
				<span>基本信息</span>
			</div>

			<ul class="forminfo">
			
			
			
				<li><label>药品名称</label><input name="shopName" type="text" class="dfinput" value="<%=map.get("shopName")%>"/></li>
				
				<li><label>药品分类</label>


					<div class="vocation">
						<select class="select1" name="typeMessage">


							<%
								if (!listMessage.isEmpty()) {
									/*  for(Map<String,Object> map:list){ */
									for (int i = 0; i < listMessage.size(); i++) {
										Map<String, Object> mapInfor = listMessage.get(i);
							%>


							<option value="<%=mapInfor.get("typeId")%>,<%=mapInfor.get("typeName")%>"><%=mapInfor.get("typeName")%></option>

							<%
								}
								}
							%>
						</select>
					</div>
				<li><label>药品金额</label><input name="shopMoney" type="text" class="dfinput" value="<%=map.get("shopMoney")%>"></li>
				<li><label>药品库存</label><input name="shopNumber" type="text" class="dfinput" value="<%=map.get("shopNumber")%>"></li>
				<li><label>药品说明</label><textarea name="shopMessage" cols="" rows="" class="textinput"><%=map.get("shopMessage")%></textarea></li>
				<li><label>药品图片</label><input name="bookImage" type="file" class="dfinput" size="60" value="<%=map.get("shopImg")%>"></li>
				<li><label>&nbsp;</label><input name="" type="button" class="btn" onclick="javascript:dosubmit();"  value="确认保存" /></li>
			</ul>

		</div>
	</form>
		<script type="text/javascript">
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
	

 
</body>
</html>
