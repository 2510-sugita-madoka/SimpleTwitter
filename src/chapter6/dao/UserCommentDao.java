package chapter6.dao;

import static chapter6.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import chapter6.beans.UserComment;
import chapter6.exception.SQLRuntimeException;
import chapter6.logging.InitApplication;

public class UserCommentDao {

	/**
	* ロガーインスタンスの生成
	*/
	Logger log = Logger.getLogger("twitter");

	/**
	* デフォルトコンストラクタ
	* アプリケーションの初期化を実施する。
	*/
	public UserCommentDao() {
		InitApplication application = InitApplication.getInstance();
		application.init();

	}
	// 返信の検索
	public List<UserComment> select(Connection connection) {

		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
				" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ");
			sql.append("comments.id AS commentId, ");
			sql.append("comments.text AS commentText, ");
			sql.append("comments.user_id AS commentUserId, ");
			sql.append("comments.message_id AS commentMessageId, ");
			sql.append("comments.created_date AS commentCreatedDate, ");
			sql.append("users.account AS userAccount, ");
			sql.append("users.name AS userName ");
			sql.append("FROM comments ");
			sql.append("INNER JOIN users ");
			sql.append("ON comments.user_id = users.id");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			List<UserComment> comments = toComment(rs);
			return comments;

		} catch (SQLException e) {
			log.log(Level.SEVERE, new Object(){}.getClass().getEnclosingClass().getName() + " : " + e.toString(), e);
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UserComment> toComment(ResultSet rs) throws SQLException {

		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
				" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		List<UserComment> comments = new ArrayList<UserComment>();
		try {
			while (rs.next()) {
				UserComment comment = new UserComment();
				comment.setCommentId(rs.getInt("commentId"));
				comment.setCommentText(rs.getString("commentText"));
				comment.setCommentUserId(rs.getInt("commentUserId"));
				comment.setCommentMessageId(rs.getInt("commentMessageId"));
				comment.setCommentCreatedDate(rs.getTimestamp("commentCreatedDate"));
				comment.setUserAccount(rs.getString("userAccount"));
				comment.setUserName(rs.getString("userName"));

				comments.add(comment);
			}
			return comments;
		} finally {
			close(rs);
		}
	}
}
