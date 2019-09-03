package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	@RequestMapping(value="/",method=RequestMethod.GET)
	public ModelAndView index(ModelAndView mav) {
		mav.addObject("msg","名前入力→送信");
		mav.setViewName("index");
		return mav;
	}
	
	@RequestMapping(value="/",method=RequestMethod.POST)
	public ModelAndView send(@RequestParam("name")String str,@RequestParam("period")String str_2,ModelAndView mav) {
		mav.addObject("msg","予定:"+str+"期限:"+str_2);
		mav.addObject("value",str);
		mav.addObject("value_2",str_2);
		mav.setViewName("index");
		return mav;
	}
}
