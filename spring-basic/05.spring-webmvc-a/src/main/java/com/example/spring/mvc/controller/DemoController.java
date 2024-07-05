package com.example.spring.mvc.controller;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.example.spring.mvc.dto.Person;
import com.example.spring.mvc.view.MyView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = { "/demo" })
public class DemoController {

//	@RequestMapping(path = { "/param" })
//	public String processParam(HttpServletRequest req) {
//		
//		String data1 = req.getParameter("data1");
//		String sData2 = req.getParameter("data2");
//		int data2 = Integer.parseInt(sData2);
//		
//		System.out.println(data1 + " / " + data2);
//		
//		return "demo/result";  // /WEB-INF/views/ + demo/result + .jsp
// 	}
	
	
	/* ==================================================================================== */
	
	
//	@RequestMapping(path = { "/param" })  // GET, POST, ... 등 모든 요청 처리
//	public String processParam(@RequestParam("data1") String data1, @RequestParam("data2") int data2) {
//		
//		System.out.println(data1 + " / " + data2);
//		
//		return "demo/result";  // /WEB-INF/views/ + demo/result + .jsp
// 	}
	
	
	/* ==================================================================================== */
	
	
	// @RequestMapping(path = { "/param" }, method = RequestMethod.GET)  // GET 요청만 처리
//	@GetMapping(path = { "/param" })  // GET 요청만 처리
//	public String processParam(String data1, int data2) {  // 전달인자 이름은 parameter 이름과 일치할 때 데이터 저장
//		
//		System.out.println(data1 + " / " + data2);
//		
//		return "demo/result";  // /WEB-INF/views/ + demo/result + .jsp
// 	}
	
	
	
	/* ==================================================================================== */
	
	
	
//	@GetMapping(path = { "/param" })  // GET 요청만 처리
//	public String processParam(@RequestParam("data1") String data3, @RequestParam("data2") int data4) {
//		
//		System.out.println(data3 + " / " + data4);
//		
//		return "demo/result";  // /WEB-INF/views/ + demo/result + .jsp
// 	}

	
	/* ModelAndView 방식으로 jsp 에 데이터 넘기기 */
	@GetMapping(path = { "/param" })  // GET 요청만 처리
	public ModelAndView processParam(String data1, int data2, Model model) {   // Model : View(JSP) 로 전달되는 객체
		
		System.out.println(data1 + " / " + data2);
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("newdata1", data1);  // mav 에 데이터를 저장하면 request 객체에 저장
		mav.addObject("newdata2", data2);
		
		mav.setViewName("demo/result");
		
		return mav;  // /WEB-INF/views/ + demo/result + .jsp
 	}
	
	
	/* Model 방식으로 jsp 에 데이터 넘기기 (대부분 이 방식으로 쓰임) */
//	@GetMapping(path = { "/param" })  // GET 요청만 처리
//	public String processParam(String data1, int data2, Model model) {   // Model : View(JSP) 로 전달되는 객체
//		
//		System.out.println(data1 + " / " + data2);
//		
//		model.addAttribute("newdata1", data1);  // model 에 데이터를 저장하면 request 객체에 저장
//		model.addAttribute("newdata2", data2);
//		
//		return "demo/result";  // /WEB-INF/views/ + demo/result + .jsp
// 	}
	
	
	/* ==================================================================================== */
	
	
	
//	@RequestMapping(path = { "/param" }, method = RequestMethod.POST)
//	@PostMapping(path = { "/param" })
//	public String processParam2(Person person) {
//		
//		System.out.println(person.toString());
//		
//		return "demo/result";
//	}
	
//	@PostMapping(path = { "/param" })
////	public String processParam2(@ModelAttribute Person person) {
//	public String processParam2(Person person) {  // 객체 타입의 전달인자는 자동으로 View(jsp)로 전달 (@ModelAttribute 생략)
//		
//		System.out.println(person.toString());
//		
//		return "demo/result";
//	}
	
	@PostMapping(path = { "/param" })
	public String processParam2(Person person, Model model) {  // 객체 타입의 전달인자는 자동으로 View(jsp)로 전달 (@ModelAttribute 생략)
		
		System.out.println(person.toString());
		
		model.addAttribute("person2", person);
		
		return "demo/result";
	}
	
	
	/* ==================================================================================== */
	
	
	/* Redirect : 주소 이름 바뀜 (많이 쓰임) */
////	@GetMapping("/redirect")
//	@GetMapping(path = { "/redirect" })
//	public String redirect() {
//		
//		return "redirect:/demo/redirect-target";
//	}
	@GetMapping(path = { "/redirect" })
	public RedirectView redirect() {
		RedirectView rv = new RedirectView("/mvc-a/demo/redirect-target");
		return rv;
	}
	@GetMapping(path = { "/redirect-target" }, produces="text/palin;charset=utf-8")
	@ResponseBody  // view 를 사용하지 않고 controller 메서드의 반환 값을 바로 요청한 곳으로 응답하는 설정
	public String redirectTarget() {
		
		return "redirect-target -> redirect 결과입니다.";
	}
	
	
	/* forward : 거의 안쓰임, 위 방식을 주로 씀 */
	@GetMapping(path = { "/forward" })
	public String forward() {
		
		return "forward:/demo/forward-target";
	}
	@GetMapping(path = { "/forward-target" }, produces="text/palin;charset=utf-8")
	@ResponseBody
	public String forwardTarget() {
		
		return "forward-target -> forward 결과입니다.";
	}
	
	
	/* ==================================================================================== */
	
	
	@GetMapping(path = { "/custom-view" })
	public View customView(Model model) {
		
		model.addAttribute("mbti", "INFJ");
		model.addAttribute("hobby", "nothing");
		
		MyView view = new MyView();
		return view;
		
	}
	
	
	/* ==================================================================================== */
	
	
//	// 요청 데이터 중 날짜 형식의 데이터는 Model 객체에 자동으로 binding 되지 않기 때문에 변환기를 등록하는 작업
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));
//	}
	
	
	/* ==================================================================================== */
	
	
	@GetMapping(path = { "/sync" })
	public String handleSynchronousRequest() {
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		return "redirect:/home?syncResult=" + sdf.format(new Date());
		String msg = "";
		try {
			msg = URLEncoder.encode(sdf.format(new Date()), "utf-8");
		} catch (Exception ex) {
			msg = "error";
		}
		return "redirect:/home?syncResult=" + msg;
	}
	
	@GetMapping(path = { "/async1" }, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String handleAsynchronousRequest() {
		
		Person person = new Person();
		person.setName("홍길동");
		person.setEmail("길동@naver.com");
		person.setPhone("010-1111-1111");
		person.setBirthDate(new Date());
		person.setAge(20);
		
//		Gson gson = new Gson();
//		String personJson = gson.toJson(person);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd a hh:mm:ss").create();
		String personJson = gson.toJson(person);
		
		return personJson;
	}
	
	@GetMapping(path = { "/async2" }, produces = "application/json;charset=utf-8")
	@ResponseBody
	public Person handleAsynchronousRequest2() {
		
		Person person = new Person();
		person.setName("홍길동");
		person.setEmail("길동@naver.com");
		person.setPhone("010-1111-1111");
		person.setBirthDate(new Date());
		person.setAge(20);
		
		return person;
	}
	
	@GetMapping(path = { "/async3" })
	public String handleAsynchronousRequest3(Model model) {
		
		Person person = new Person();
		person.setName("홍길동");
		person.setEmail("길동@naver.com");
		person.setPhone("010-1111-1111");
		person.setBirthDate(new Date());
		person.setAge(20);
		
		model.addAttribute("person", person);
		
		return "demo/person";
	}
	
	
}
