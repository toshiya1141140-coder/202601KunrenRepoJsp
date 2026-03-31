package pack.coffee;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <P>タイトル : 登録画面遷移サーブレット</P>
 * <P>説明 : 検索画面から登録画面へ画面遷移させるサーブレットです</P>
 * @author kashiwagi toshiya 
 * @version 1.0 2026.02.02
 */
@WebServlet("/DspCoffeeInsert")
public class DspCoffeeInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DspCoffeeInsert() {
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
	 * 検索画面の登録ボタン押下時、doPostで呼び出し、登録画面に遷移させる
	 * @param request  クライアントからのHTTPリクエスト
	 * @param response クライアントへ返却するHTTPレスポンス
	 * @throws ServletException, IOException
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//登録画面(Coffeeinsert.jsp)へ画面遷移
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/coffee/CoffeeInsert.jsp");
		dispatcher.forward(request, response);
	}

}
