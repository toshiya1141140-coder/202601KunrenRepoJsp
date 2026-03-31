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
 * <P>タイトル : 検索用サーブレット</P>
 * <P>説明 : 検索条件に沿ったデータを取得し、表示するサーブレットです</P>
 * @author kashiwagi toshiya 
 * @version 1.0 2026.01.28
 */
@WebServlet("/CoffeeSearch")
public class CoffeeSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CoffeeSearch() {
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
	 * 初期画面の検索ボタン押下時、doPostで呼び出し、検索条件に沿った検索処理を行う
	 * @param request  クライアントからのHTTPリクエスト
	 * @param response クライアントへ返却するHTTPレスポンス
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @throws ServletException, IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//文字化け防止
		request.setCharacterEncoding("UTF-8");

		try {
			//商品IDのテキスト欄で入力されたデータを取得
			//引数はformタグ内のname属性を設定し、valueの内容を取得する
			String strId = request.getParameter("text_coffee_id");

			//銘柄名のテキスト欄で入力されたデータを取得
			//引数はformタグ内のname属性を設定し、valueの内容を取得する
			String strName = request.getParameter("text_coffee_name");

			//CoffeeDaoクラスからselectByKey()を呼び出す為、インスタンス生成する
			CoffeeDao coffeeDao = new CoffeeDao();

			//データベースから検索条件に沿ったデータを取得するメソッドを呼び出す
			//インスタンス化した変数coffeeDaoからselectByKey()を呼び出し、text_coffee_idとtext_coffee_nameの中身を引数として渡す
			List<CoffeeDto> list = coffeeDao.selectByKey(strId, strName);

			//selectByKey()から取得したデータ(list)をCoffeeSearch.jspのforEachに渡す
			request.setAttribute("dbdata", list);

			//検索画面ページへ画面遷移
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
