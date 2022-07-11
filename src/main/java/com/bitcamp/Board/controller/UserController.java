package com.bitcamp.Board.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bitcamp.Board.model.UserDTO;
import com.bitcamp.Board.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/selectAll")
	public String selectAll(Model model) {
		model.addAttribute("list", userService.selectAll());
		return "user/showAll";
	}

	@RequestMapping(value = "/logIn", method = RequestMethod.POST)
	public String logIn(String username, String password, HttpSession session) {
		UserDTO u = new UserDTO();
		u.setUsername(username);
		u.setPassword(convertSha2(password));

		System.out.println(u);

		u = userService.auth(u);
		
		System.out.println(u);
		
		if(u !=null) {
			session.setAttribute("logIn", u);
			return "redirect:/board/showAll";
		}
		return "index";
	}

	private String convertSha2(String password) {
		String converted = null;
		StringBuilder builder = null;

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(password.getBytes("UTF-8"));

			builder = new StringBuilder();
			for (int i = 0; i < hash.length; i++) {
				builder.append(String.format("%02x", 255 & hash[i]));
			}

			converted = builder.toString();

		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return converted;
	}

	@RequestMapping("/selectOne/{id}")
	public String selectOne(@PathVariable int id) {
		System.out.println(id);

		return "/index.html";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		return "user/register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerLogic(UserDTO u) {
		u.setPassword(convertSha2(u.getPassword()));
		userService.register(u);
		return "redirect:/";
	}
}
