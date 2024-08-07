package com.coffeeorderproject.view;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Map;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractView;

import com.coffeeorderproject.dto.BoardAttachDto;
import com.coffeeorderproject.service.BoardService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DownloadView2 extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		BoardAttachDto attach = (BoardAttachDto)model.get("attach");
		
		//브라우저가 응답 컨텐츠를 다운로드로 처리하도록 정보 설정
		resp.setContentType("application/octet-stream;charset=utf-8");	
		//브라우저에게 다운로드하는 파일의 이름을 알려주는 코드 
		resp.addHeader("Content-Disposition", 
				"Attachment;filename=\"" + 
				new String(attach.getUserfilename().getBytes("utf-8"), "ISO-8859-1") + "\"");
		
		//ServletContext : JSP의 application객체와 동일한 객체
		ServletContext application = req.getServletContext();  // this.getServletContext(); 도 가능
		
		String path = application.getRealPath("/board-attachments/" + attach.getSavedfilename());
		
		FileInputStream fis = new FileInputStream(path); 	//파일을 읽는 도구
		OutputStream fos = resp.getOutputStream();			//브라우저에게 전송하는 도구
		
		while (true) {
			int data = fis.read();  //파일에서 1byte 읽기
			if (data == -1) { //더 이상 읽을 데이터가 없다면 (EOF)
				break;
			}
			fos.write(data); //응답 스트림에 1byte 쓰기
		}
		
		fis.close();
		fos.close();
	}

}
