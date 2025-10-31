package chapter6.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import chapter6.beans.User;

@WebFilter(urlPatterns = {"/setting", "/edit"})
public class LoginFilter implements Filter {

	public static String INIT_PARAMETER_NAME_ENCODING = "encoding";

	public static String DEFAULT_ENCODING = "UTF-8";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse HttpServletResponse = (HttpServletResponse) response;
		HttpSession session = httpServletRequest.getSession();
		User user = (User) session.getAttribute("loginUser");

		// セッションにログインの記録がない場合、
		// ログイン画面に遷移する
		if (user != null) {
			chain.doFilter(request, response); // サーブレットを実行
		}else {
			List<String> errorMessages = new ArrayList<String>();
			errorMessages.add("ログインをしてください");
			session.setAttribute("errorMessages", errorMessages);
			HttpServletResponse.sendRedirect("./login");
		}

	}

	@Override
	public void init(FilterConfig config) {
	}

	@Override
	public void destroy() {
	}
}
