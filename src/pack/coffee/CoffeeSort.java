package pack.coffee;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.coffee.CoffeeDao;
import dto.coffee.CoffeeDto;

/**
 * <P>タイトル : 並べ替え機能用サーブレット</P>
 * <P>説明 : 昇順降順で並べ替えができる</P>
 * @author kashiwagi toshiya 
 * @version 1.0 2026.02.16
 */
@WebServlet("/CoffeeSort")
public class CoffeeSort extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CoffeeSort() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * 並べ替えボタン押下時、doPostで呼び出し、並べ替え処理を行う
	 * @param request  クライアントからのHTTPリクエスト
	 * @param response クライアントへ返却するHTTPレスポンス
	 * @throws ServletException, IOException
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//文字化け防止
		request.setCharacterEncoding("UTF-8");

		// JSPのselectのname属性（sortVal）を取得
	    String sortVal = request.getParameter("sortVal");

		try {
			//Daoインスタンス生成
			CoffeeDao coffeeDao = new CoffeeDao();

			//並べ替えメソッド呼び出し、結果を取得
			List<CoffeeDto> list = coffeeDao.sort(sortVal);

			//リクエストスコープにセット
			//jsp側に表示させる
			request.setAttribute("dbdata", list);

			//検索画面へ画面遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/coffee/CoffeeSearchList.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {
			//スタックトレース表示
			e.printStackTrace();

			//検索画面に表示させるため、リクエストスコープにセットし、c:ifタグにデータを渡す
			request.setAttribute("errorMsg", "データベースエラーが発生しました。");

			//検索画面へ画面遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/coffee/CoffeeSearchList.jsp");
			dispatcher.forward(request, response);
		}
	}
}
