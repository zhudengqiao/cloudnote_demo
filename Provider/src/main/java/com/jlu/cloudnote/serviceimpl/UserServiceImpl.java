package com.jlu.cloudnote.serviceimpl;

import com.jlu.cloudnote.dao.UserDao;
import com.jlu.cloudnote.entity.User;
import com.jlu.cloudnote.service.UserService;
import com.jlu.cloudnote.util.NoteException;
import com.jlu.cloudnote.util.NoteResult;
import com.jlu.cloudnote.util.NoteUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	@Resource
	private UserDao userDao;

	public NoteResult checkLogin(String name, String password) {
		NoteResult result = new NoteResult();
		User user = userDao.findByName(name);
		if (user == null) {
			result.setStatus(1);
			result.setMsg("success");
			return result;
		}
		String md5_pwd;
		try {
			md5_pwd = NoteUtil.md5(password);
			if (!user.getCn_user_password().equals(md5_pwd)) {
				result.setStatus(2);
				result.setMsg("success");
				return result;
			}
		} catch (Exception e) {
			throw new NoteException("fail", e);
		}
		result.setStatus(0);
		result.setMsg("success");
		user.setCn_user_password("");
		result.setData(user);
		return result;
	}

	public NoteResult addUser(String name, String nick, String password) {
		NoteResult result = new NoteResult();
		try {
			User has_user = userDao.findByName(name);
			if (has_user != null) {
				result.setStatus(1);
				result.setMsg("success");
				return result;
			}
			User user = new User();
			user.setCn_user_id(NoteUtil.createId());
			user.setCn_user_name(name);
			user.setCn_user_desc(nick);
			user.setCn_user_password(NoteUtil.md5(password));
			userDao.save(user);
			result.setStatus(0);
			result.setMsg("success");
			return result;
		} catch (Exception e) {
			throw new NoteException("fail", e);
		}
	}


}
