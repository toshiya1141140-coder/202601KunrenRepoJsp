package pack.coffee;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.coffee.CoffeeDao;
import dto.coffee.CoffeeDto;

/**
 * <P>タイトル : コーヒー在庫情報をCSV形式で出力するサーブレット</P>
 * <P>説明 : CoffeeDaoから取得した全件データをCSV形式に変換し、ブラウザへダウンロードさせる</P>
 * @author kashiwagi toshiya 
 * @version 1.0 2026.02.12
 */
@WebServlet("/CoffeeCsvDownload")
public class CoffeeCsvDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CoffeeCsvDownload() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * コーヒー在庫情報をCSV形式で出力する
	 * @param request  クライアントからのHTTPリクエスト
     * @param response クライアントへ返却するHTTPレスポンス
     * @throws ServletException
     * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//ブラウザにCSVファイルとして扱わせる。
		//文字コード指定→UTF-8
		response.setContentType("text/csv; charset=UTF-8");
		//ブラウザにダウンロードさせる命令
		//保存ファイル名はcoffee.csv
		response.setHeader("Content-Disposition", "attachment; filename=\"coffee.csv\"");

		//try-with-resources文 自動でclose()
		try (PrintWriter pw = response.getWriter()) {

			//CoffeeDaoからメソッド呼び出し(全件データを取得)
			List<CoffeeDto> list = new CoffeeDao().getSearchAll();

			//CSVの1行目はヘッダーとして書き出す
			//printlnで自動的に改行が入る
			pw.println("商品ID,銘柄名,価格(100g),在庫数量(g),最終入荷日");

			//拡張for文で繰り返す
			for (CoffeeDto coffeeDto : list) {
				//CSV形式にカンマ区切りで整形 printlnで自動改行
				pw.println(coffeeDto.getCoffeeId() + ","
						+ coffeeDto.getCoffeeName() + ","
						+ coffeeDto.getCoffeePrice() + ","
						+ coffeeDto.getCoffeeStock() + ","
						+ coffeeDto.getLastDate());
			}

		} catch (SQLException e) {
			//スタックトレース表示
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}
