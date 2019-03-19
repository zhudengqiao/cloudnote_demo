package com.jlu.cloudnote.service;

import com.jlu.cloudnote.util.NoteResult;

public interface NoteService {
	public NoteResult shareNote(String noteId);

	public NoteResult moveNote(String noteId, String bookId);

	public NoteResult deleteNote(String noteId);

	public NoteResult addNote(String userId, String noteTitle, String bookId);

	public NoteResult updateNote(String noteId, String title, String body);

	public NoteResult loadBookNotes(String bookId);

	public NoteResult loadNote(String noteId);
}
