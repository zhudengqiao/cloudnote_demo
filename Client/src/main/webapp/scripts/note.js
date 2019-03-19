/** note.js 封装笔记相关操作 */
// 分享笔记
function shareNote() {
	// 获取请求参数
	var $li = $(this).parents("li");
	var noteId = $li.data("noteId");
	// 发送Ajax请求
	$.ajax({
		url : base_path + "/note/share.do",
		type : "post",
		data : {
			"noteId" : noteId
		},
		dataType : "json",
		success : function(result) {
			
		},
		error : function() {
			alert("分享笔记异常");
		}
	});
}
// 移动笔记
function moveNote() {
	// 获取请求参数
	// 获取要转移的笔记ID
	var $li = $("#note_ul a.checked").parent();
	var noteId = $li.data("noteId");
	// 获取要转入的笔记本ID
	var bookId = $("#moveSelect").val();
	// 发送Ajax请求
	$.ajax({
		url : base_path + "/note/move.do",
		type : "post",
		data : {
			"noteId" : noteId,
			"bookId" : bookId
		},
		dataType : "json",
		success : function(result) {
			if (result.status == 0) {
				// 移除笔记li
				$li.remove();
				// 提示成功
				alert(result.msg);
			}
		},
		error : function() {
			alert("转移笔记异常");
		}
	});
}
// 删除笔记
function deleteNote() {
	// 获取请求参数
	var $li = $("#note_ul a.checked").parent();
	var noteId = $li.data("noteId");
	// 发送Ajax请求
	$.ajax({
		url : base_path + "/note/delete.do",
		type : "post",
		data : {
			"noteId" : noteId
		},
		dataType : "json",
		success : function(result) {
			if (result.status == 0) {
				// 删除li
				$li.remove();
				// 提示成功
				alert(result.msg);
			}
		},
		error : function() {
			alert("删除笔记异常");
		}
	});
}
// 隐藏笔记菜单
function hideNoteMenu() {
	// 隐藏所有笔记菜单
	$("#note_ul div").hide();
}
// 弹出笔记菜单
function popNoteMenu() {
	// 隐藏所有笔记菜单
	$("#note_ul div").hide();
	// 显示点击的笔记菜单
	var $menu = $(this).parent().next();
	$menu.slideDown(300);
	// 设置点击笔记选中效果
	$("#note_ul a").removeClass("checked");
	$(this).parent().addClass("checked");
	// 阻止事件向li，body冒泡
	return false;

}
// 创建笔记操作
function addNote() {
	// 获取请求参数
	var userId = getCookie("uid");
	var noteTitle = $("#input_note").val().trim();
	var $li = $("#book_ul a.checked").parent();// 获取选中的笔记本li元素
	var bookId = $li.data("bookId");
	// 检查格式
	var ok = true;
	if (userId == null) {
		ok = false;
		window.location.href = "log_in.html";
	}
	if (noteTitle == "") {
		ok = false;
		$("#note_span").html("<font color='red'>笔记名称为空</font>");
	}
	// 发送Ajax请求
	if (ok) {
		$.ajax({
			url : base_path + "/note/add.do",
			type : "post",
			data : {
				"userId" : userId,
				"noteTitle" : noteTitle,
				"bookId" : bookId
			},
			dataType : "json",
			success : function(result) {
				// 关闭对话框
				closeAlertWindow();
				// 生成笔记列表li
				var noteId = result.data;// 获取服务器返回的ID
				createNoteLi(noteId, noteTitle);
				// 弹出提示
				alert(result.msg);
			},
			error : function() {
				alert("创建笔记本异常");
			}
		});
	}
}
// "保存笔记"按钮的处理
function updateNote() {
	// 获取请求参数
	var title = $("#input_note_title").val().trim();
	var body = um.getContent();
	var $li = $("#note_ul a.checked").parent();// 获取选中的笔记li元素
	var noteId = $li.data("noteId");
	// 清除上次提示信息
	$("#note_title_span").html("");
	// 检查格式
	if ($li.length == 0) {
		alert("请选择要保存的笔记");
	} else if (title == "") {
		$("#note_title_span").html("<font color='red'>标题不能为空</font>");
	} else {
		// 发送Ajax请求
		$
				.ajax({
					url : base_path + "/note/update.do",
					type : "post",
					data : {
						"noteId" : noteId,
						"title" : title,
						"body" : body
					},
					dataType : "json",
					success : function(result) {
						if (result.status == 0) {// 成功
							// 更新列表li中标题
							var sli = "";
							sli += '<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
							sli += title;
							sli += '<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
							// 将选中的li元素的a内容替换
							$li.find("a").html(sli);
							// 提示成功
							alert(result.msg);
						}
					},
					error : function() {
						alert("保存笔记异常");
					}
				});
	}
}
// 根据笔记ID加载笔记信息
function loadNote() {
	// 设置笔记选中状态
	$("#note_ul a").removeClass("checked");
	$(this).find("a").addClass("checked");
	// 获取请求参数
	var noteId = $(this).data("noteId");
	// 发送Ajax请求
	$.ajax({
		url : base_path + "/note/load.do",
		type : "post",
		data : {
			"noteId" : noteId
		},
		dataType : "json",
		success : function(result) {
			if (result.status == 0) {// 成功
				var title = result.data.cn_note_title;// 获取笔记标题
				var body = result.data.cn_note_body;// 获取笔记内容
				// 设置到编辑区域
				$("#input_note_title").val(title);
				um.setContent(body);
			}
		},
		error : function() {
			alert("加载笔记异常");
		},
	});
}
// 根据笔记本ID加载笔记列表
function loadBookNotes() {
	// 设置笔记本li选中效果
	$("#book_ul a").removeClass("checked");
	$(this).find("a").addClass("checked");
	// 获取请求参数
	var bookId = $(this).data("bookId");
	// 发送ajax请求
	$.ajax({
		url : base_path + "/note/loadnotes.do",
		type : "post",
		data : {
			"bookId" : bookId
		},
		dataType : "json",
		success : function(result) {
			if (result.status == 0) {
				// 清空原有笔记列表
				$("#note_ul").empty();
				// $("#note_ul li").remove();
				// 获取服务器返回的笔记集合信息
				var notes = result.data;
				// 循环生成笔记li元素
				for (var i = 0; i < notes.length; i++) {
					// 获取笔记ID和笔记标题
					var noteId = notes[i].cn_note_id;
					var noteTitle = notes[i].cn_note_title;
					// 创建一个笔记li元素
					createNoteLi(noteId, noteTitle);
					// 将新添加的元素判断是否该添加分享图标
					var typeId = notes[i].cn_note_type_id;
					if (typeId == '2') {
						// 加分享图标
						var img = '<i class="fa fa-sitemap"></i>';
						// 获取新追加的li元素
						var $li = $("#note_ul li:last");
						$li.find(".btn_slide_down").before(img);
					}
				}
			}
		},
		error : function() {
			alert("加载笔记列表异常");
		},
	});
}
// 创建笔记列表li元素
function createNoteLi(noteId, noteTitle) {
	var sli = "";
	sli += '<li class="online">';
	sli += '  <a>';
	sli += '    <i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
	sli += noteTitle;
	sli += '    <button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
	sli += '  </a>';
	sli += '  <div class="note_menu" tabindex="-1">';
	sli += '    <dl>';
	sli += '      <dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt>';
	sli += '      <dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>';
	sli += '      <dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>';
	sli += '    </dl>';
	sli += '  </div>';
	sli += '</li>';
	// 将noteId绑定到li元素上
	var $li = $(sli);
	$li.data("noteId", noteId);
	// 将li元素添加到笔记列表ul中
	$("#note_ul").append($li);
}
/*******************************************************************************
 * 加载普通笔记
 */
function getNormalNoteList() {
	console.log("加载普通笔记");
}

/*******************************************************************************
 * 查询普通笔记内容
 */
function getNoteDetail() {
	console.log("查询普通笔记内容");
}

/*******************************************************************************
 * 创建普通笔记
 */
function createNormalNote() {
	alert("创建普通笔记");
}

/*******************************************************************************
 * 更新普通笔记
 */
function updateNormalNote() {
	alert("更新普通笔记");
}

/*******************************************************************************
 * 删除普通笔记
 */
function deleteNormalNote() {
	alert("删除普通笔记");
}

/*******************************************************************************
 * 分享笔记
 */
function createShareNote() {
	$("footer div strong").text("分享成功").parent().fadeIn(100);
	setTimeout(function() {
		$("footer div").fadeOut(500);
	}, 1500);
}

/*******************************************************************************
 * 查询回收站笔记列表
 */
function getRecycleNoteList() {
	alert("查询回收站笔记列表");
}

/*******************************************************************************
 * 查看回收站笔记内容
 */
function getRecycleNoteDetail() {
	console.log("查看回收站笔记内容");
}

/*******************************************************************************
 * 删除回收站笔记
 */
function deleteRecycleNote() {
	alert("删除回收站笔记");
}

/*******************************************************************************
 * 搜索分享笔记列表
 */
function getShareNoteList() {
	alert("搜索分享笔记列表");
}

/*******************************************************************************
 * 查询分享笔记内容
 */
function getShareNoteDetail() {
	alert("查询分享笔记内容");
}

/*******************************************************************************
 * 收藏分享笔记
 */
function likeShareNote(shareId, dom) {
	alert("收藏分享笔记");
}

/*******************************************************************************
 * 加载收藏笔记
 */
function getLikeNoteList(likeNoteId) {
	alert("加载收藏笔记");
}

/*******************************************************************************
 * 查看收藏笔记内容
 */
function getLikeNoteDetail(noteId) {
	console.log("查看收藏笔记内容");
}

/*******************************************************************************
 * 删除收藏笔记
 */
function deleteLikeNote(noteId, dom) {
	alert("删除收藏笔记");
}

/*******************************************************************************
 * 加载本用户参加活动笔记列表
 */
function getNoteActivityList(noteBookId) {
	alert("加载本用户参加活动笔记列表");
}

/*******************************************************************************
 * 查询参加活动的笔记内容
 */
function getActivityNoteDetail(noteId) {
	console.log("查询参加活动的笔记内容");
}