<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	   String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()

            + path;
	List<Map<String, Object>> list = (List<Map<String, Object>>) request.getAttribute("listMessage");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head href="<%=basePath%>">
<title>订单数据</title>
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>

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


</head>


<body>

	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="#">首页</a></li>
			<li><a href="#">数据表</a></li>
			<li><a href="#">基本内容</a></li>
		</ul>
	</div>

	<div class="rightinfo">


		<table class="tablelist">
			<thead>
				<tr>
					<th>序号</th>
					<th>订单号</th>
					<th>地址信息</th>
					<th>药品信息</th>
					<th>购买用户</th>
					<th>订单金额</th>
					<th>购买时间 </th>
					<th>操作 </th>
				</tr>
			</thead>
			<tbody>




				<%
					int houseNumber = 0;
											if (!list.isEmpty()) {
												/*  for(Map<String,Object> map:list){ */
												for (int i = 0; i < list.size(); i++) {
													houseNumber = i + 1;
													Map<String, Object> map = list.get(i);
				%>


				<tr>
					<td ><%=houseNumber%></td>
					<td><%=map.get("orderNo")%></td>
					<td><%=map.get("orderAddress")%></td>
					<td><%=map.get("shopLists")%></td>
					
					<td><%=map.get("orderUserName")%></td>
					<td><%=map.get("orderMessageMoney")%>元</td>
					<td><%=map.get("orderCreatime")%></td>
					
						<td>
					
					<%
					if(map.get("orderState").equals("1")){
					
					
					 %>
							<a href="<%=path%>/servlet/MessageAction?action_flag=updateOrderMessage&orderId=<%=map.get("orderId") %>" class="tablelink"> 已付款待发货</a>
						<% 
					}else{
					out.print("已发货");
					}
					
					 %>
				
					&nbsp&nbsp&nbsp					
					<a href="<%=path%>/servlet/RegisterAction?action_flag=deleteUser&uid=<%=map.get("uid") %>" class="tablelink"> 删除</a>
					
				</tr>

				<%
					}
											}
				%>
			</tbody>
		</table>







	</div>

	<script type="text/javascript">
		$('.tablelist tbody tr:odd').addClass('odd');
	</script>
</body>
</html>
