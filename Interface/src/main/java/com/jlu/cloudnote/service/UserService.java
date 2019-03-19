package com.jlu.cloudnote.service;

import com.jlu.cloudnote.util.NoteResult;

public interface UserService {
	public NoteResult checkLogin(String name, String password);

	public NoteResult addUser(String name, String nick, String password);
}

