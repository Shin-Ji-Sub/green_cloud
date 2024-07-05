<%@page import="java.util.Date"%>
<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=utf-8" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
	<br /><br /><br />
	<div style="padding-left:20px; margin-bottom: 10rem;">
		<h2>Spring MVC Controller Demo</h2>
		
		<h3>1. <a href="demo/param?data1=this%20is%20test%20data&data2=100">Process Request Parameter (GET)</a></h3>
		<h3>2. Process Request Parameter (POST)</h3>
		<form action="demo/param" method="post">
			이름 : <input type="text" name="name" value="홍길동" /><br />
			전화 : <input type="text" name="phone" /><br />
			메일 : <input type="text" name="email" /><br />
			나이 : <input type="text" name="age" /><br />
			나이 : <input type="date" name="birthDate" /><br />
			<input type="submit" value="전송" />
		</form>
		<h3>3. <a href="#">Transfer Data from Controller to View (Refer to 1, 2)</a></h3>
		<h3>4. <a href="demo/redirect">Redirect to Another Action</a></h3>
		<h3>5. <a href="demo/forward">Forward to HTML</a></h3>
		<h3>6-1. <a href="hello.html">static resource NOT IN resources</a></h3>
		<h3>6-2. <a href="resources/hello.html">static resource IN resources</a></h3>
		<h3>7. <a href="demo/custom-view">custom view</a></h3>
		<h3>8-1. <a href="demo/sync">Synchronous Request/Response</a></h3>
		<h3>8-2. <a href="javascript:" class="async-link" data-no="1">Asynchronous Request/Response 1</a></h3>
		<h3>8-3. <a href="javascript:" class="async-link" data-no="2">Asynchronous Request/Response 2</a></h3>
		<h3>8-4. <a href="javascript:" id="async-link3">Asynchronous Request/Response 3</a></h3>
		<div style="border: solid 1px; margin-bottom: 2px; padding: 5px;"><%= new Date() %></div>
		<div id="message" style="border: solid 1px; margin-bottom: 2px; padding: 5px;">${ syncResult }</div>
	</div>
	
	<script src="http://code.jquery.com/jquery-3.7.1.js"></script>
	<script type="text/javascript">
		$(() => {
			
			$('.async-link').on('click', (e) => {
				
				$.ajax({
					"url" : "demo/async" + $(e.currentTarget).data('no'),
					"method" : "GET",
					"data" : { "a" : "123", "b" : "456" },  // "a=123&b456"
					"dataType" : "json",
					"success" : (data, status, xhr) => {
						// data = JSON.stringify(data);
						html = "<h3>PERSON INFO</h3>" +
							   "<table>" +
							   "<tr><th>이름</th><td>" + data.name + "</td></tr>" +
							   "<tr><th>이메일</th><td>" + data.email + "</td></tr>" +
							   "<tr><th>전화</th><td>" + data.phone + "</td></tr>" +
							   "<tr><th>나이</th><td>" + data.age + "</td></tr>" +
							   "<tr><th>생일</th><td>" + data.birthDate + "</td></tr>" +
							   "</table>"	
						// $('#message').html(data);
						$('#message').html(html);
					},
					"error" : (xhr, status, err) => {
						$('#message').html("요청 실행 중 오류");
					}
				});
			
			});
			
			$('#async-link3').on('click', (e) => {
				
				$('#message').load("demo/async3");
			
			});
			
		});
	</script>
</body>
</html>
