package com.jlu.cloudnote.dao;

import com.jlu.cloudnote.entity.Note;

import java.util.List;
import java.util.Map;

public interface NoteDao {
	public int updateBookId(Note note);

	public int updateStatus(String noteId);

	public void save(Note note);

	public int updateNote(Note note);

	public List<Map> findByBookId(String bookId);

	public Note findById(String noteId);
}
