package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class IndexController {
	//database
	@Autowired
	EmployeeRepository empRepository;
	@RequestMapping(value = "/",method=RequestMethod.GET)
	public ModelAndView dbpage(@ModelAttribute("formModel") Employee ptodo , ModelAndView mav) {
		List<Employee> emplist=empRepository.findAll();
		//mav.addObject("NULLcheck",ptodo.getName() == null);
		if(emplist == null) {
			emplist = new ArrayList<Employee>();
		}
		mav.addObject("NULLcheck",emplist.size() == 0);
		mav.addObject("emplist",emplist);
		mav.addObject("trueValue","ToDo Does Not Exist");
		mav.addObject("falseValue","");
		mav.setViewName("dbpage");
		return mav;
	}
	
	@RequestMapping(value = "/",method=RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView form(@ModelAttribute("formModel") Employee ptodo , ModelAndView mav) {
		empRepository.saveAndFlush(ptodo);
		return new ModelAndView("redirect:/");
	}
	/*
	//ToDo
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView index(ModelAndView mav) {
		mav.addObject("msg","入力->送信");
		mav.setViewName("index");
		return mav;
	}

	@RequestMapping(value="/send",method=RequestMethod.POST)
	public ModelAndView send(@RequestParam("name")String str,@RequestParam("period")String str_2,ModelAndView mav) {
		mav.addObject("msg","予定:"+str+"期限:"+str_2);
		mav.addObject("value",str);
		mav.addObject("value_2",str_2);
		mav.setViewName("send");
		return mav;
	}
	*/
	/*
	//練習
	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView index(ModelAndView mav) {
		mav.setViewName("index");
		mav.addObject("msg","フォーム送信");
		return mav;
	}
	
	@RequestMapping(value="/",method=RequestMethod.POST)
	public ModelAndView send(
			@RequestParam(value="check1",required=false)boolean check1,@RequestParam(value="radio1",required=false)String radio1,@RequestParam(value="select1",required=false)Stirng select1,@RequestParam(value="select2",required=false)String select2,ModelAndView mav) {
		String res="";
		try {
			res = "check:"+check1+"radio:"+radio1+"select:"+select1+"\nselect2";
		} catch(NullPointerException e) {}
		try {
			res += select2[0];
			for(int i=1;i<select2.length();i++) {
				res += ","+select2[i]
		}
	}
	*/
}
