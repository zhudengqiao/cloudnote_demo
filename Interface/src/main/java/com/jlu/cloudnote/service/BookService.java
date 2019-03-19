package com.jlu.cloudnote.service;

import com.jlu.cloudnote.util.NoteResult;

public interface BookService {
	public NoteResult addBook(String userId, String name);

	public NoteResult loadUserBooks(String userId);
}
