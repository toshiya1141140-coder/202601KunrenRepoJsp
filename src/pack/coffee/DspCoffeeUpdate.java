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
 * <P>タイトル : 更新画面遷移サーブレット</P>
 * <P>説明 : 検索画面から更新画面へ画面遷移する際に、ラジオボタンで選択されたデータを初期表示させるサーブレットです</P>
 * @author kashiwagi toshiya 
 * @version 1.0 2026.02.02
 */
@WebServlet("/DspCoffeeUpdate")
public class DspCoffeeUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DspCoffeeUpdate() {
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
	 * 初期画面の更新ボタン押下時、doPOSTで呼び出し
	 * @param request  クライアントからのHTTPリクエスト
	 * @param response クライアントへ返却するHTTPレスポンス
	 * @throws ServletException, IOException
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//日本語のパラメータ対応
		request.setCharacterEncoding("UTF-8");

		//ラジオボタンで選択された行の商品IDを取得する
		//引数はformタグ内のname属性を設定し、valueの内容を取得する
		String strId = request.getParameter("radio");

		try {
			//CoffeeDaoクラスからselectByKey(String strId)を呼び出す為、インスタンス生成する
			CoffeeDao coffeeDao = new CoffeeDao();

			//メソッド戻り値はCoffeeDtoなので左辺に代入
			CoffeeDto coffeeDto = coffeeDao.selectByKey(strId);

			//selectByKey(String strId)から取得したデータ(coffeeDto)をCoffeeUpdate.jspのforEachに渡す
			request.setAttribute("dbdata", coffeeDto);

			//更新画面へ画面遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/coffee/CoffeeUpdate.jsp");
			dispatcher.forward(request, response);

		} catch (NumberFormatException e) {
			//スタックトレース表示
			e.printStackTrace();

			//検索初期画面に表示させるため、リクエストスコープにセットし、c:ifタグにデータを渡す
			request.setAttribute("errorMsg", "商品を選択し直してから、更新ボタンを押してください。");
			//更新画面へ画面遷移

			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/coffee/CoffeeSearchList.jsp");
			dispatcher.forward(request, response);

		} catch (IOException e) {
			//スタックトレース表示
			e.printStackTrace();

			//更新画面に表示させるため、リクエストスコープにセットし、c:ifタグにデータを渡す
			request.setAttribute("errorMsg", "入出力処理中にエラーが発生しました。");

			//更新画面へ画面遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/coffee/CoffeeUpdate.jsp");
			dispatcher.forward(request, response);

		} catch (SQLException e) {
			//スタックトレース表示
			e.printStackTrace();

			//更新画面に表示させるため、リクエストスコープにセットし、c:ifタグにデータを渡す
			request.setAttribute("errorMsg", "データベースエラーが発生しました。");

			//更新画面へ画面遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/coffee/CoffeeUpdate.jsp");
			dispatcher.forward(request, response);
		}
	}
}
