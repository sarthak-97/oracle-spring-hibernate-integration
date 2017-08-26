package org.interntask.controllers;

import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@Autowired
	private SessionFactory sessionFactory;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView landingPage(HttpSession httpSession){
		
		ModelAndView model=new ModelAndView("index");
	
	     return model;
	}
}
