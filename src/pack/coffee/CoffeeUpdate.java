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
 * <P>タイトル : 更新処理用サーブレット</P>
 * <P>説明 : 商品更新時の更新処理用のサーブレット</P>
 * @author kashiwagi toshiya 
 * @version 1.0 2026.02.10
 */
@WebServlet("/CoffeeUpdate")
public class CoffeeUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CoffeeUpdate() {
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
	 * 商品更新ボタン押下時、更新フォームで入力されたデータをDBに登録するサーブレット
	 * @param request  クライアントからのHTTPリクエスト
	 * @param response クライアントへ返却するHTTPレスポンス
	 * @throws ServletException, IOException
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//文字化け防止
		request.setCharacterEncoding("UTF-8");

		//CoffeeDaoクラスからupdateCoffee()を呼び出す為、インスタンス生成する
		CoffeeDao coffeeDao = new CoffeeDao();

		try {

			//更新フォームから入力されたデータをcoffeeDtoに預ける独自のメソッド
			CoffeeDto coffeeDto = CoffeeDtoBuilder.buildDto(request);

			//更新処理実行
			//戻り値は更新した件数
			int updateRow = coffeeDao.updateCoffee(coffeeDto);
			
			//更新が成功した場合、trueの文字列を変数msgにまとめる
			String msg = (updateRow == 1) ? "1件のデータを更新しました" : "データの更新に失敗しました";

			//msgをリクエストスコープにupdateMsgと言うキーでセットする
			//メッセージを表示するため、CoffeeUpdate.jspのc:ifタグに渡す
			request.setAttribute("updateMsg", msg);

			//データﾍﾞｰｽからデータを全件取得
			List<CoffeeDto> list = coffeeDao.getSearchAll();

			//DBから取得したデータ(list)をdbdataと言う名前(キー)でリクエストスコープにセットする
			//CoffeeSearchList.jspのforEachに渡す
			request.setAttribute("dbdata", list);

			//検索画面へ画面遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/coffee/CoffeeSearchList.jsp");
			dispatcher.forward(request, response);

			//CoffeeDtoBuilderクラスのbuildDto()呼び出し時に発生しうる例外をここでキャッチ
			//NumberFormatExceptionはIllegalArgumentExceptionの子クラスのため省略
		} catch (IllegalArgumentException e) {
			//スタックトレース表示
			e.printStackTrace();

			//リクエストスコープにセットし、更新画面にエラーメッセージを表示
			request.setAttribute("errorMsg", "入力内容に誤りがあります。");

			//更新画面へ画面遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/coffee/CoffeeUpdate.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {
			//スタックトレース表示
			e.printStackTrace();

			//リクエストスコープにerrorMsgをセット
			//更新画面に表示
			request.setAttribute("errorMsg", "データベースエラーが発生しました。");

			//更新画面へ画面遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/coffee/CoffeeUpdate.jsp");
			dispatcher.forward(request, response);
		}
	}

}
