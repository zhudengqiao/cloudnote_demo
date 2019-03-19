package com.jlu.cloudnote.controller;

import com.jlu.cloudnote.service.NoteService;
import com.jlu.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller(value = "/note")
public class NoteController {
	@Resource
	private NoteService noteService;

	@RequestMapping("add")
	@ResponseBody
	public NoteResult add(String userId, String noteTitle, String bookId) {
		NoteResult result = noteService.addNote(userId, noteTitle, bookId);
		return result;
	}
	@RequestMapping("delete")
	@ResponseBody
	public NoteResult delete(String noteId) {
		NoteResult result = noteService.deleteNote(noteId);
		return result;
	}
	@RequestMapping("load")
	@ResponseBody
	public NoteResult load(String noteId) {
		NoteResult result = noteService.loadNote(noteId);
		return result;
	}
	@RequestMapping("loadnotes")
	@ResponseBody
	public NoteResult loadnotes(String bookId) {
		NoteResult result = noteService.loadBookNotes(bookId);
		return result;
	}
	@RequestMapping("move")
	@ResponseBody
	public NoteResult move(String noteId, String bookId) {
		NoteResult result = noteService.moveNote(noteId, bookId);
		return result;
	}
	@RequestMapping("share")
	@ResponseBody
	public NoteResult share(String noteId) {
		NoteResult result = noteService.shareNote(noteId);
		return result;
	}
	@RequestMapping("update")
	@ResponseBody
	public NoteResult update(String noteId, String title, String body) {
		NoteResult result = noteService.updateNote(noteId, title, body);
		return result;
	}
}
