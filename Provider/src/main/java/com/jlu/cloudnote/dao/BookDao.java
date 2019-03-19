package com.jlu.cloudnote.dao;

import com.jlu.cloudnote.entity.Book;

import java.util.List;

public interface BookDao {
	public void save(Book book);

	public List<Book> findByUserId(String userId);
}
