package chapter6.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import chapter6.beans.Message;
import chapter6.logging.InitApplication;
import chapter6.service.MessageService;

@WebServlet(urlPatterns = { "/edit" })
public class EditServlet extends HttpServlet {

	/**
	* ロガーインスタンスの生成
	*/
	Logger log = Logger.getLogger("twitter");

	/**
	* デフォルトコンストラクタ
	* アプリケーションの初期化を実施する。
	*/
	public EditServlet() {
		InitApplication application = InitApplication.getInstance();
		application.init();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
		" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		// top.jspから遷移する際のつぶやきを取得・反映処理
		// 編集するつぶやきのIDを取得
		List<String> errorMessages = new ArrayList<String>();
		String id = request.getParameter("editId");
		HttpSession session = request.getSession();

		// URL上のIDが修正されたかチェックする
		if (StringUtils.isBlank(id) || !id.matches("^[0-9]*$")) {
			errorMessages.add("不正なパラメータが入力されました");
			session.setAttribute("errorMessages", errorMessages);
			response.sendRedirect("./");
			return;
		}

		int editId = Integer.parseInt(id);

		Message messages = new MessageService().Select(editId);

		if(messages == null) {
			errorMessages.add("不正なパラメータが入力されました");
			session.setAttribute("errorMessages", errorMessages);
			response.sendRedirect("./");
			return;
		}

		// 編集画面に遷移
		request.setAttribute("messages", messages);
		request.getRequestDispatcher("edit.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
				" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		// edit.jspで「更新」が押下された際の処理
		HttpSession session = request.getSession();
		List<String> errorMessages = new ArrayList<String>();

		// つぶやきのIDと編集内容を取得
		String id = request.getParameter("editId");
		int editId = Integer.parseInt(id);

		String text = request.getParameter("text");
		Message messages = new Message() ;
		messages.setId(editId);
		messages.setText(text);

		if (!isValid(text, errorMessages)) {
			session.setAttribute("errorMessages", errorMessages);
			request.setAttribute("messages", messages);
			request.getRequestDispatcher("edit.jsp").forward(request, response);
			return;
		}

		// 更新を行いtop.jspに遷移
		messages.setText(text);
		new MessageService().update(messages);
		response.sendRedirect("./");

    }

	private boolean isValid(String text, List<String> errorMessages) {

		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
				" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		if (StringUtils.isBlank(text)) {
			errorMessages.add("入力してください");
		} else if (140 < text.length()) {
			errorMessages.add("140文字以下で入力してください");
		}

		if (errorMessages.size() != 0) {
			return false;
		}
		return true;
	}
}
