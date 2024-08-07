<%@page import="com.demoweb.dto.MemberDto"%>
<%@page import="com.demoweb.dto.BoardAttachDto"%>
<%@page import="com.demoweb.dto.BoardDto"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

    
<!DOCTYPE html>

<html>
<head>
	<meta charset="utf-8" />
	<title>글상세보기</title>
	<link rel="Stylesheet" href="/mvcdemoweb/styles/default.css" />
	<link rel="Stylesheet" href="/mvcdemoweb/styles/input.css" />
</head>
<body>

	<div id="pageContainer">
	
		<jsp:include page="/WEB-INF/views/modules/header.jsp" />
		
		<div style="padding-top:25px;text-align:center">
		<div id="inputcontent">
		    <div id="inputmain">
		        <div class="inputsubtitle">게시글 정보</div>
		        <% 
		        BoardDto board = (BoardDto)request.getAttribute("board"); 
		        %>
		        <table>
		            <tr>
		                <th>제목</th>
		                <td>
		                	<%= board.getTitle() %>
		                </td>
		            </tr>
		            <tr>
		                <th>작성자</th>
		                <td>
		                	<%= board.getWriter() %>
		                </td>
		            </tr>
		            <tr>
		            	<th>조회수</th>
		            	<td>
		            		<%= board.getReadcount() %>
		            	</td>
		            </tr>
		            <tr>
		            	<th>등록일자</th>
		            	<td>
		            		<%= board.getWritedate() %>
		            	</td>
		            </tr>
		            <tr>
		            	<th>수정일자</th>
		            	<td>
		            		<%= board.getModifydate() %>
		            	</td>
		            </tr>
		            <tr>
		                <th>첨부파일</th>
		                <td>
		                <% for (BoardAttachDto attach : board.getAttachments()) { %>
		                	<a href="download?attachno=<%=attach.getAttachNo()%>"><%= attach.getUserFileName() %></a><br>
		                <% } %>
		                </td>
		            </tr>
		            <tr>
		                <th>글내용</th>
						<td>
							<%= board.getContent().replace("\r\n", "<br>")
												  .replace("\r", "<br>")
												  .replace("\n", "<br>") %>
						</td>
		            </tr>
		        </table>
		        <div class="buttons">
		        	<% MemberDto loginUser = (MemberDto)session.getAttribute("loginuser"); %>
		        	<% if (loginUser != null && loginUser.getMemberid().equals(board.getWriter())) { %>
			        	<input type="button" id="edit_button" value="편집" style="height:25px" />
			        	<input type="button" id="delete_button" value="삭제" style="height:25px" />
			        <% } %>
		        	<input type="button" id="tolist_button" value="목록보기" style="height:25px" />
		        </div>
		    </div>
		</div>   	
	
	</div>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script type="text/javascript">
		const deleteBtn = $("#delete_button");
		deleteBtn.on("click", () => {
			const ok = confirm("<%= board.getBoardno() %>번 글을 삭제하시겠습니까?");
			if (ok) {
				location.href = "delete?boardno=<%= board.getBoardno() %>"
			}
		});
		
		$("#edit_button").on("click", () => {
			location.href = 'edit?boardno=<%= board.getBoardno() %>';
		});
	</script>

</body>
</html>










