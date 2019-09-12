package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.ToDoEntity;
import com.example.demo.entityrepository.ToDoEntityRepository;

@Controller
public class IndexController {
	//TOP
	@Autowired
	ToDoEntityRepository tdeRepository;
	@RequestMapping(value = "/",method=RequestMethod.GET)
	public ModelAndView dbpage(@ModelAttribute("formModel") ToDoEntity todotable , ModelAndView mav ) {
		List<ToDoEntity> tdelist=tdeRepository.findAll(new Sort(Sort.DEFAULT_DIRECTION.DESC,"id"));
		if(tdelist == null) {
			tdelist = new ArrayList<ToDoEntity>();
		}
		mav.addObject("NULLcheck",tdelist.size() == 0);
		mav.addObject("tdelist",tdelist);
		mav.addObject("trueValue","ToDo Does Not Exist");
		mav.addObject("falseValue","");
		mav.setViewName("dbpage");
		return mav;
	}

	@RequestMapping(value = "/",method=RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView form(@ModelAttribute("formModel") ToDoEntity todotable , ModelAndView mav) {

		/*
		else {
			if(todotable.getName() != "") {
				tdeRepository.saveAndFlush(todotable);
				return new ModelAndView("redirect:/?out=0");
			} else {
				return new ModelAndView("redirect:/?out=1");
			}
		}
		*/
		tdeRepository.saveAndFlush(todotable);
		return new ModelAndView("redirect:/");

	}

	@RequestMapping(value = "/{id}",method=RequestMethod.PUT)
	@Transactional(readOnly=false)
	public ModelAndView completecheck(@ModelAttribute("formModel") ToDoEntity todotable ,@PathVariable int id, ModelAndView mav) {
		Optional<ToDoEntity> todo = tdeRepository.findById(id);
		if(todo.get().getCompletec() == null) {
			todo.get().setCompletec("COMPLETE");
			tdeRepository.saveAndFlush(todo.get());
			return new ModelAndView("redirect:/");
		}else {
			todo.get().setCompletec(null);
			tdeRepository.saveAndFlush(todo.get());
			return new ModelAndView("redirect:/");
		}
	}

	//EDIT 作成途中
	@RequestMapping(value = "/editor{id}",method=RequestMethod.GET)
	public ModelAndView editor(@ModelAttribute("formModel") ToDoEntity todotable ,@PathVariable int id, ModelAndView mav ) {
		Optional<ToDoEntity> todo_edit = tdeRepository.findById(id);
		mav.addObject("id",id);
		mav.addObject("name",todo_edit.get().getName());
		mav.addObject("limitdate",todo_edit.get().getLimitdate());
		mav.setViewName("editor");
		return mav;
	}
	
	@RequestMapping(value = "/editor",method=RequestMethod.POST)
	public ModelAndView ediform(@RequestParam ("id") Integer id,@RequestParam ("name") String name,@RequestParam ("limitdate") Date limitdate,ModelAndView mav ) {
		System.out.println(name);
		Optional<ToDoEntity> todo_edited = tdeRepository.findById(id);
		todo_edited.get().setName(name);
		todo_edited.get().setLimitdate(limitdate);
		todo_edited.get().setCompletec(null);
		tdeRepository.saveAndFlush(todo_edited.get());
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(value = "/delete",method=RequestMethod.POST)
	public ModelAndView delete(@RequestParam("id") Integer id,ModelAndView mav) {
		tdeRepository.deleteById(id);
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(value = "/search",method=RequestMethod.GET)
	public ModelAndView search(ModelAndView mav) {
		mav.setViewName("search");
		return mav;
	}
	
	@RequestMapping(value="/search",method=RequestMethod.POST)
	public ModelAndView searchput(ModelAndView mav,@RequestParam("name") String name) {
		List<ToDoEntity> searchdata = tdeRepository.findByname(name);
		if(searchdata == null) {
			searchdata = new ArrayList<ToDoEntity>();
		}
		mav.addObject("searchcheck",searchdata.size() == 0);
		mav.addObject("searchdata",searchdata);
		mav.setViewName("search");
		return mav;
	}
	
	@RequestMapping(value = "/search{id}",method=RequestMethod.PUT)
	@Transactional(readOnly=false)
	public ModelAndView searchcomplete(@ModelAttribute("formModel") ToDoEntity todotable ,@PathVariable int id, ModelAndView mav) {
		Optional<ToDoEntity> todo = tdeRepository.findById(id);
		List<ToDoEntity> redata = tdeRepository.findByname(todo.get().getName());
		mav.addObject("searchdata",redata);
		todo.get().setCompletec("COMPLETE");
		tdeRepository.saveAndFlush(todo.get());
		mav.setViewName("search");
		return mav;
		//return new ModelAndView("redirect:'/search");
	}
	//ToDo
	/*
	@RequestMapping(value="/editor",method=RequestMethod.GET)
	public ModelAndView index(ModelAndView mav) {
		mav.addObject("msg","入力->送信");
		mav.setViewName("index");
		return mav;
	}

	@RequestMapping(value="/send",method=RequestMethod.POST)
	public ModelAndView send(@RequestParam("namepra")String str,@RequestParam("period")String str_2,ModelAndView mav) {
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
