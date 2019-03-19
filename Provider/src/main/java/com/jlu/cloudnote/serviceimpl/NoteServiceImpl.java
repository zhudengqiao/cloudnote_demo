package com.jlu.cloudnote.serviceimpl;

import com.jlu.cloudnote.dao.NoteDao;
import com.jlu.cloudnote.dao.ShareDao;
import com.jlu.cloudnote.entity.Note;
import com.jlu.cloudnote.entity.Share;
import com.jlu.cloudnote.service.NoteService;
import com.jlu.cloudnote.util.NoteResult;
import com.jlu.cloudnote.util.NoteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("noteService")
@Transactional
public class NoteServiceImpl implements NoteService {
	@Resource
	private NoteDao noteDao;
	@Resource
	private ShareDao shareDao;

	public NoteResult loadBookNotes(String bookId) {
		//按笔记本ID查询笔记信息
		List<Map> list = noteDao.findByBookId(bookId);
		//创建返回结果
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("success");
		result.setData(list);
		return result;
	}

	public NoteResult loadNote(String noteId) {
		//按笔记ID查询笔记信息
		Note note = noteDao.findById(noteId);
		// 创建返回结果
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("success");
		result.setData(note);
		return result;
	}

	public NoteResult updateNote(String noteId, String title, String body) {
		Note note = new Note();
		note.setCn_note_id(noteId);//设置笔记ID
		note.setCn_note_title(title);//设置标题
		note.setCn_note_body(body);//设置内容
		Long time = System.currentTimeMillis();
		note.setCn_note_last_modify_time(time);//设置修改时间
		int rows = noteDao.updateBookId(note);//更新
		//创建返回结果
		NoteResult result = new NoteResult();
		if (rows == 1) {//成功
			result.setStatus(0);
			result.setMsg("success");
		} else {//失败
			result.setStatus(1);
			result.setMsg("fail");
		}
		return result;
	}

	public NoteResult addNote(String userId, String noteTitle, String bookId) {
		//创建note参数保存
		Note note = new Note();
		note.setCn_user_id(userId);//设置用户ID
		note.setCn_note_title(noteTitle);//设置笔记名称
		note.setCn_notebook_id(bookId);//设置笔记ID
		String noteId = NoteUtil.createId();
		note.setCn_note_id(noteId);//笔记ID
		Long time = System.currentTimeMillis();
		note.setCn_note_create_time(time);//创建时间
		note.setCn_note_last_modify_time(time);//最后修改时间
		noteDao.save(note);//保存笔记
		//创建返回结果
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("success");
		result.setData(noteId);//返回笔记ID
		return result;
	}

	public NoteResult deleteNote(String noteId) {
		int rows = noteDao.updateStatus(noteId);
		NoteResult result = new NoteResult();
		if (rows >= 1) {
			result.setStatus(0);
			result.setMsg("success");
		} else {
			result.setStatus(1);
			result.setMsg("fail");
		}
		return result;
	}

	public NoteResult moveNote(String noteId, String bookId) {
		Note note = new Note();
		note.setCn_note_id(noteId);
		note.setCn_notebook_id(bookId);
		int rows = noteDao.updateBookId(note);
		NoteResult result = new NoteResult();
		if (rows >= 1) {
			result.setStatus(0);
			result.setMsg("success");
		} else {
			result.setStatus(1);
			result.setMsg("fail");
		}
		return result;
	}

	public NoteResult shareNote(String noteId) {
		NoteResult result = new NoteResult();
		Note note = noteDao.findById(noteId);
		Share share = new Share();
		share.setCn_note_id(noteId);
		share.setCn_share_id(NoteUtil.createId());
		share.setCn_share_title(note.getCn_note_title());
		share.setCn_share_body(note.getCn_note_body());
		shareDao.save(share);
		result.setStatus(0);
		result.setMsg("success");
		return result;
	}

}