package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entity.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/home")
	public String home()
	{
		return "home";
	}
	@RequestMapping("/about")
	public String about()
	{
		return "about";
	}
	@RequestMapping("/signup")
	public String signup(Model model)
	{ model.addAttribute("user",new User());
		return "signup";
	}
	@RequestMapping(value ="/do_register",method= RequestMethod.POST)
	public String registeruser(@ModelAttribute("user") User user, @RequestParam(value="agreement", defaultValue ="false") boolean agreement, Model model,HttpSession Session)
     {
		
		try
		{
			if(!agreement)
			{
				System.out.println("you have not agreed the term and condition");
				throw new Exception("you have not agreed the term and condition");
			}
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			System.out.println("Agreement"+ agreement);
			System.out.println("User"+user);
	       	User result=userRepository.save(user);
			model.addAttribute("user",result);
			Session.setAttribute("my message", new Message("Successfully registered", "alert-success"));
			return "signup";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			model.addAttribute("user", user);
			Session.setAttribute("my message", new Message("Error has ocurred "+ e.getMessage(), "alert error"));
			return "signup";
		}
		
		
	
	}
	
}
