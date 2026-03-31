package pack.coffee;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.coffee.CoffeeDao;
import dto.coffee.CoffeeDto;

/**
 * <P>タイトル : 登録処理用サーブレット</P>
 * <P>説明 : 入力されたデータの登録処理を行うサーブレット</P>
 * @author kashiwagi toshiya 
 * @version 1.0 2026.01.28
 */
@WebServlet("/CoffeeInsert")
public class CoffeeInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CoffeeInsert() {
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
	 * 登録画面の登録ボタン押下時、doPostで呼び出し、登録処理をする
	 * @param request  クライアントからのHTTPリクエスト
	 * @param response クライアントへ返却するHTTPレスポンス
	 * @throws ServletException, IOException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//日本語のパラメータ対応
		request.setCharacterEncoding("UTF-8");
		//CoffeeDaoクラスからinsertCoffee()を呼び出す為、インスタンス生成する
		CoffeeDao coffeeDao = new CoffeeDao();
		//初期化
		CoffeeDto coffeeDto = null;

		try {

			//登録フォームから入力されたデータをcoffeeDtoに預ける独自のメソッド
			coffeeDto = CoffeeDtoBuilder.buildDto(request);

			//重複チェック実行
			//重複チェックメソッドの引数に商品IDを渡し、重複しているか確認してもらう
			//メソッドの戻り値がtrueなら実行 falseなら不実行
			//受け取り側をCoffeeDto型にすれば、引数にcoffeeDtoをそのまま渡せるよ(可読性が上がるかも？)
			if (coffeeDao.isDuplicate(coffeeDto.getCoffeeId())) {
				//重複していた場合、CoffeeInsert.jspのc:ifタグに渡して、画面にエラーメッセージを表示させる
				request.setAttribute("errorMsg", "この商品IDは既に登録されています");

				//登録画面へ遷移
				RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/coffee/CoffeeInsert.jsp");
				dispatcher.forward(request, response);

				//登録処理はせず、ここで終了
				return;
			}

			//重複がなければ登録処理実行
			//戻り値は登録した件数
			int insertRow = coffeeDao.insertCoffee(coffeeDto);
			
			//登録が成功した場合、trueの文字列を変数msgにまとめる
			String msg = (insertRow == 1) ? "1件のデータを登録しました" : "データの登録に失敗しました";

			//msgをリクエストスコープにinsertMsgと言うキーでセットする
			//メッセージを表示するため、CoffeeInsert.jspのc:ifタグに渡す
			request.setAttribute("insertMsg", msg);

			//登録画面へ画面遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/coffee/CoffeeInsert.jsp");
			dispatcher.forward(request, response);

			//CoffeeDtoBuilderクラスのbuildDto()呼び出し時に発生しうる例外をここでキャッチ
			//NumberFormatExceptionはIllegalArgumentExceptionの子クラスのため省略
		} catch (IllegalArgumentException e) {
			//スタックトレース表示
			e.printStackTrace();

			//リクエストスコープにセットし、登録画面にエラーメッセージを表示
			request.setAttribute("errorMsg", "e.getMessage()");

			//登録画面に登録フォームに入力された値を表示するため、リクエストスコープにセット
			request.setAttribute("coffee", coffeeDto);
			
			//登録画面へ画面遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/coffee/CoffeeInsert.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {
			//スタックトレース表示
			e.printStackTrace();

			//サーブレットでも重複チェックし、ここでも一意制約(商品ID)エラーをキャッチする(必殺：二段構え)
			//登録画面に表示させるため、リクエストスコープにセットし、c:ifタグにデータを渡す
			request.setAttribute("errorMsg", "データベースエラーが発生しました。");

			//登録画面へ画面遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/coffee/CoffeeInsert.jsp");
			dispatcher.forward(request, response);
		}
	}
}
