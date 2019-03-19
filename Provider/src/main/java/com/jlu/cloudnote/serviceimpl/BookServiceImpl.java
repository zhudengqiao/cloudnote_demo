package com.jlu.cloudnote.serviceimpl;

import com.jlu.cloudnote.dao.BookDao;
import com.jlu.cloudnote.entity.Book;
import com.jlu.cloudnote.service.BookService;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlu.cloudnote.util.NoteResult;
import com.jlu.cloudnote.util.NoteUtil;
import org.springframework.transaction.annotation.Transactional;

@Service("bookService")
@Transactional
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    public NoteResult loadUserBooks(String userId) {
        List<Book> list = this.bookDao.findByUserId(userId);
        NoteResult result = new NoteResult();
        result.setStatus(0);
        result.setMsg("success");
        result.setData(list);
        return result;
    }

    public NoteResult addBook(String userId, String name) {
        Book book = new Book();
        book.setCn_user_id(userId);
        book.setCn_notebook_name(name);
        String bookId = NoteUtil.createId();
        book.setCn_notebook_id(bookId);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        book.setCn_notebook_createtime(time);
        book.setCn_notebook_type_id("5");
        this.bookDao.save(book);
        NoteResult result = new NoteResult();
        result.setStatus(0);
        result.setMsg("success");
        result.setData(book);
        return result;
    }
}
