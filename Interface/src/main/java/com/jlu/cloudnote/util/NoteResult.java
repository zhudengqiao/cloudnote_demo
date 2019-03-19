package com.jlu.cloudnote.util;

import java.io.Serializable;

public class NoteResult implements Serializable {
	private int status;// 状态
	private String msg;//信息
	private Object data;//数据

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "NoteResult [status=" + status + ", msg=" + msg + ", data=" + data + "]";
	}

}
