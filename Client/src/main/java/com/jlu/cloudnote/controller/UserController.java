package com.jlu.cloudnote.controller;

import com.jlu.cloudnote.service.UserService;
import com.jlu.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
@Controller(value = "/user")
public class UserController {
	@Resource
	private UserService userService;

	@RequestMapping("add")
	@ResponseBody
	public NoteResult add(String name, String nick, String password) {
		return userService.addUser(name, nick, password);
	}
	@RequestMapping("login")
	@ResponseBody // JSON
	public NoteResult login(String name, String password) {
		return userService.checkLogin(name, password);
	}
}
