package org.interntask.controllers;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.interntask.models.UserDet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	
	@Autowired
	private SessionFactory sessionFactory;
	private UserDet userdet;
	

	/**
	 * controller for signup of user
	 * @param userdet
	 * @return
	 */
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView signup(@ModelAttribute("userdet") org.interntask.models.UserDet userdet) {
		Session session = sessionFactory.openSession();
		ModelAndView model = new ModelAndView("index");
		if (session.get(UserDet.class,userdet.getEmailId()) == null) {
			
			session.beginTransaction();
			session.save(userdet);
			
			session.getTransaction().commit();
			model.addObject("invalid", "Successfully registered, login to proceed!");

		} else
			model.addObject("invalid", "This email is already registered.");
		session.close();
		return model;

	}
	
	/**\
	 * controller for login process of Buyer
	 * @param httpSession
	 * @param emailid
	 * @param password
	 * @return
	 */
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(HttpSession httpSession, @RequestParam("id") String emailid,
			@RequestParam("pass") String password) {
		ModelAndView model;
		Session session = sessionFactory.openSession();
		userdet = (UserDet) session.get(UserDet.class, emailid);
		if (userdet != null) {
					if (userdet.getPassword().equals(password)) {
							httpSession.setAttribute("SESSION_email", userdet.getEmailId());
							httpSession.setAttribute("SESSION_name", userdet.getFirstName());
						
							if((String) httpSession.getAttribute("SESSION_email")!=null){
								model = new ModelAndView("index");
								model.addObject("username","Logout Here" + "  " +	httpSession.getAttribute("SESSION_name"));
								
				               }
							else{
								model = new ModelAndView("index");
								model.addObject("invalid", "LOG IN FIRST TO CONTINUE");
							}
			} else {
				model = new ModelAndView("index");
				model.addObject("invalid", "invalid details");
			}
		}

		else {
			model = new ModelAndView("index");
			model.addObject("invalid", "no record found");
		}
		session.close();
		return model;
	}
	
	/**
	 * 
	 * controller for session logout
	 * 
	 * @author sarthak
	 * @param httpSession
	 * @return
	 */

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView LogoutController(HttpSession httpSession) {
		httpSession.invalidate();
		ModelAndView model = new ModelAndView("index");
		model.addObject("invalid", "successfully logged out");
		return model;
	}

	
	@RequestMapping(value = "/loginpage", method = RequestMethod.GET)
	public ModelAndView LogController(HttpSession httpSession) {
		
		ModelAndView model = new ModelAndView("login");
		model.addObject("invalid", "successfully logged out");
		return model;
	}
}
