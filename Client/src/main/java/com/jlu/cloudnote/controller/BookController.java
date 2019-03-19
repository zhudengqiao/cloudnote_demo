package com.jlu.cloudnote.controller;

import com.jlu.cloudnote.service.BookService;
import com.jlu.cloudnote.service.NoteService;
import com.jlu.cloudnote.util.NoteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller(value = "/book")
public class BookController {
    @Resource
    private BookService bookService;

    @RequestMapping("add")
    @ResponseBody
    public NoteResult add(String userId, String name) {
        NoteResult result = bookService.addBook(userId, name);
        return result;
    }
    @RequestMapping("loadbooks")
    @ResponseBody
    public NoteResult loadbooks(String userId) {
        NoteResult result = bookService.loadUserBooks(userId);
        return result;
    }
}
