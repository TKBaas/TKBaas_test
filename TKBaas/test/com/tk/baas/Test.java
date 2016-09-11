//package com.tk.baas;
//
//import java.util.ArrayList;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.tk.baas.model.OrderSubmitForm;
//
//@Controller
//@RequestMapping(value="test")
//public class Test {
//	@RequestMapping("/showForm")
//	public String showForm(HttpServletRequest request, Model model){
//		ArrayList<String> list = new ArrayList<>();
//		list.add("aa");
//		list.add("bb");
//		list.add("cc");
//		list.add("dd");
//		model.addAttribute("list",list);
//		model.addAttribute("modelTT", new ModelTT());
//		return "testMVC";
//	}
//	
//	@RequestMapping("/test1")
//	public void test(HttpServletRequest request, @ModelAttribute("ModelTT") ModelTT modelTT){
//		for(int i=0; i<modelTT.names.length; i++){
//			System.out.println(modelTT.names[i]);
//		}
//		
//	}
//}
