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
 * <P>タイトル : 検索画面遷移サーブレット</P>
 * <P>説明 : 検索画面へ画面遷移させるサーブレットです</P>
 * @author kashiwagi toshiya 
 * @version 1.0 2026.01.23
 */
@WebServlet("/DspCoffeeSearch")
public class DspCoffeeSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DspCoffeeSearch() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * HTTP GET リクエストを処理するメソッド。
	 * <p>
	 * このサーブレットにブラウザなどから GET リクエストが来た場合、
	 * 初期表示処理は doPost() にまとめてあるため、doPost() を呼び出して
	 * 処理を委譲する。
	 * </p>
	 * @param request  クライアントからの HTTP リクエスト
	 * @param response クライアントへの HTTP レスポンス
	 * @throws ServletException サーブレットでエラーが発生した場合
	 * @throws IOException      入出力エラーが発生した場合
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    doPost(request, response);
	}


	/**
	 * 検索画面に遷移する際に、DBから全件データを取得し、検索画面に初期表示
	 * @param request  クライアントからのHTTPリクエスト
	 * @param response クライアントへ返却するHTTPレスポンス
	 * @throws ServletException, IOException
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//文字化け防止
		request.setCharacterEncoding("UTF-8");

		//CoffeeDaoクラスからgetSearchAll()を呼び出す為、インスタンス生成する
		CoffeeDao coffeeDao = new CoffeeDao();

		try {
			//データﾍﾞｰｽからデータを全件取得
			List<CoffeeDto> list = coffeeDao.getSearchAll();

			//取得したデータをCoffeeSearch.jspのforEachに渡すため、リクエストスコープにセット
			request.setAttribute("dbdata", list);

			//CoffeeSearch.jsp(初期検索画面)へ画面遷移
			RequestDispatcher dispatch = request.getRequestDispatcher("/jsp/coffee/CoffeeSearchList.jsp");
			dispatch.forward(request, response);

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
