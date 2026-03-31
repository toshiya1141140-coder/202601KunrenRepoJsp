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
 * <P>タイトル : 削除用サーブレット</P>
 * <P>説明 : ラジオボタンで選択された行のデータを削除するサーブレット</P>
 * @author kashiwagi toshiya 
 * @version 1.0 2026.01.28
 */
@WebServlet("/CoffeeDelete")
public class CoffeeDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CoffeeDelete() {
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
	 * 削除ボタン押下時、doPostで呼び出し、削除処理を行う
	 * @param request  クライアントからのHTTPリクエスト
     * @param response クライアントへ返却するHTTPレスポンス
	 * @throws ServletException, IOException
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//文字化け防止
		request.setCharacterEncoding("UTF-8");

		//ラジオボタンで選択された行の商品IDを取得する
		//引数はformタグ内のname属性を設定し、valueの内容を取得する
		String strId = request.getParameter("radio");

		try {
			//CoffeeDaoクラスからdeleteById()を呼び出す為、インスタンス生成する
			CoffeeDao coffeeDao = new CoffeeDao();

			//strIdがnullや空文字じゃない時に実行
			if (strId != null && !strId.isEmpty()) {

				//インスタンス化した変数coffeeDaoからデータ削除用のdeleteStockById()を呼び出す
				//引数はラジオボタンで選択された行のデータ
				//戻り値は削除された行の件数でdeleteRowの変数に代入する
				int deleteRow = coffeeDao.deleteById(strId);
				
				//削除した件数が1件の時だけ、trueが実行
				String msg = (deleteRow == 1) ? "1件のデータを削除しました" : "データの削除に失敗しました";

				//deleteMsgと言う名前(キー)でリクエストスコープにmsg(値)をセットする
				//msgをCoffeeSearchList.jspの<c:if>タグに渡す
				request.setAttribute("deleteMsg", msg);

				//データﾍﾞｰｽからデータを全件取得
				List<CoffeeDto> list = coffeeDao.getSearchAll();

				//DBから取得したデータ(list)をdbdataと言う名前(キー)でリクエストスコープにセットする
				//CoffeeSearchList.jspのforEachに渡す
				request.setAttribute("dbdata", list);

				//検索画面へ画面遷移
				RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/coffee/CoffeeSearchList.jsp");
				dispatcher.forward(request, response);
			} else {
				//strIdがnullや空文字だった場合、処理終了
				return;
			}

		} catch (SQLException e) {
			//スタックトレース表示
			e.printStackTrace();

			//初期画面に表示させるため、リクエストスコープにセットし、c:ifタグにデータを渡す
			request.setAttribute("errorMsg", "データベースエラーが発生しました。");

			//初期検索画面へ画面遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/coffee/CoffeeSearchList.jsp");
			dispatcher.forward(request, response);
		}
	}
}
