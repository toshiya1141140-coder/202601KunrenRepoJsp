package dao.coffee;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cnst.Constant;
import dto.coffee.CoffeeDto;

/**
 * <P>タイトル : Daoクラス</P>
 * <P>説明 : DBとの読み書き担当。データ取得や保存の処理をここにまとめてる。</P>
 * @author kashiwagi toshiya 
 * @version 1.0 2026.01.29
 */
public class CoffeeDao {
	/**
	 * 説明：データベースから全件データを取得するメソッド
	 * @return coffeeDtoオブジェクトのリスト
	 * @throws SQLException
	 */
	public List<CoffeeDto> getSearchAll() throws SQLException {

		//PostgreSQLのURL
		String url = Constant.JDBC_CONNECTION;
		String user = Constant.JDBC_USER;
		String password = Constant.JDBC_PASS;

		//実行するSQL文を変数にまとめる
		String sql = "SELECT * FROM t_coffee ORDER BY coffee_id;";

		//PostgreSQL接続オブジェクト
		Connection con = null;
		//ステートメントオブジェクト
		Statement stmt = null;
		//SQL実行オブジェクト
		ResultSet rs = null;

		try {
			Class.forName("org.postgresql.Driver");
			//PostgreSQLへの接続
			con = DriverManager.getConnection(url, user, password);
			//ステートメントの生成
			stmt = con.createStatement();
			//SQLの実行
			rs = stmt.executeQuery(sql);

			//インスタンス生成
			List<CoffeeDto> list = new ArrayList<CoffeeDto>();

			//リストに格納する
			while (rs.next()) {
				//SELECT文の結果を1行ずつ読み込む
				//CoffeeDtoクラスのコンストラクタに引数として渡す
				//引数はデータベース上のカラム名
				list.add(new CoffeeDto(rs.getInt("coffee_id"),
						rs.getString("coffee_name"),
						rs.getInt("coffee_price"),
						rs.getInt("coffee_stock"),
						rs.getDate("last_date")));
			}
			//処理終了し、listを戻り値として返す
			return list;

		} catch (Exception e) {
			//スタックトレース表示
			e.printStackTrace();
			//処理中断
			return null;

		} finally {
			//リソースを閉じる
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (con != null)
				con.close();
		}
	}

	/**
	 * 説明：データベースから検索条件に沿ったデータを取得するメソッド
	 * @param strId:商品ID
	 * @param strName:銘柄名
	 * @return coffeeDtoオブジェクトのリスト
	 * @throws SQLException
	 */
	//CoffeeSearch.javaのselectByKey()呼び出しの際に渡されたデータを引数で受け取る
	public List<CoffeeDto> selectByKey(String strId, String strName) throws SQLException {

		//PostgreSQLのURL
		String url = Constant.JDBC_CONNECTION;
		String user = Constant.JDBC_USER;
		String password = Constant.JDBC_PASS;

		//実行するSQL文を変数にまとめる
		String sql = "SELECT * FROM t_coffee;";

		//strIdが空文字またはnullじゃない時宣言
		boolean hasId = (strId != null && !strId.isEmpty());
		//strNameが空文字またはnullじゃない時宣言
		boolean hasName = (strName != null && !strName.isEmpty());

		//両方未入力の場合
		if (!hasId && !hasName) {
			sql = "SELECT * FROM t_coffee";
			//商品IDだけが入力された場合
		} else if (hasId && !hasName) {
			sql = "SELECT * FROM t_coffee WHERE coffee_id =" + "'" + strId + "'";
			//銘柄名だけが入力された場合
		} else if (!hasId && hasName) {
			sql = "SELECT * FROM t_coffee WHERE coffee_name =" + "'" + strName + "'";
			//両方入力された場合
		} else if (hasId && hasName) {
			sql = "SELECT * FROM t_coffee WHERE coffee_id = '" + strId + "' AND coffee_name = '" + strName + "'";
		}

		//PostgreSQL接続オブジェクト
		Connection con = null;
		//ステートメントオブジェクト
		Statement stmt = null;
		//SQL実行オブジェクト
		ResultSet rs = null;

		try {
			//PostgreSQLへの接続
			con = DriverManager.getConnection(url, user, password);
			//ステートメントの生成
			stmt = con.createStatement();
			//SQLの実行
			rs = stmt.executeQuery(sql);

			//インスタンス生成
			List<CoffeeDto> list = new ArrayList<CoffeeDto>();

			//リストに格納する
			while (rs.next()) {
				//SELECT文の結果を1行ずつ読み込む
				//CoffeeDtoクラスのコンストラクタに引数として渡す
				//引数はデータベース上のカラム名
				list.add(new CoffeeDto(rs.getInt("coffee_id"),
						rs.getString("coffee_name"),
						rs.getInt("coffee_price"),
						rs.getInt("coffee_stock"),
						rs.getDate("last_date")));
			}
			//処理終了
			return list;

		} catch (Exception e) {
			//スタックトレース表示
			e.printStackTrace();
			//処理中断
			return null;

		} finally {
			//リソースを閉じる
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (con != null)
				con.close();
		}
	}

	/**
	 * 説明：ラジオボタンで選択された商品IDを条件にして、DBから条件に一致するデータを取得し、coffeeDtoクラスのインスタンスを戻り値として返すメソッド
	 * @param strId:商品
	 * @return CoffeeDtoオブジェクト
	 * @throws SQLException
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	//更新画面に遷移する際に空のフォームを表示するのではなく、現在の登録内容を表示させる
	public CoffeeDto selectByKey(String strId) throws SQLException, IOException, NumberFormatException {

		//宣言と初期化
		int coffeeId = 0;

		//nullかつ空文字じゃない時
		if (strId != null && !strId.isBlank()) {
			//受け取った商品IDがString型なので、int型へ変換
			coffeeId = Integer.parseInt(strId);
		}

		//?：インジェクション対策(不正なSQl文を入れさせないように防止する)
		String sql = "SELECT * FROM t_coffee WHERE coffee_id = ?";

		//try-with-resources文
		//自動でclose()が呼ばれる(リソース閉じ忘れ防止)
		try (Connection con = DriverManager.getConnection(Constant.JDBC_CONNECTION, Constant.JDBC_USER,
				Constant.JDBC_PASS);
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			//SQL文の?(予約席)に値をセットする
			//1（第一引数）：左から数えて1番目の?(予約席)という意味
			//coffeeId(第二引数):?(予約席)に、この変数の中身を入れろという意味
			//String型のデータを入れたい場合はsetStirng()にする
			pstmt.setInt(1, coffeeId);

			//SQLの実行
			//先にprepareStatementにSQL文をセットしているのでexecuteQueryの中は空
			ResultSet rs = pstmt.executeQuery();

			//rsにデータが入り、カーソルをずらせるかどうか
			if (rs.next()) {
				//戻り値は更新したいデータのインスタンス
				return new CoffeeDto(rs.getInt("coffee_id"), rs.getString("coffee_name"), rs.getInt("coffee_price"),
						rs.getInt("coffee_stock"), rs.getDate("last_date"));
			}
			//処理終了
			return null;
		}
	}

	/**
	 * 説明：ラジオボタンで選択された商品IDを条件に、データを削除するメソッド
	 * @param strId：商品ID
	 * @return 削除した件数
	 * @throws SQLException
	 */
	public int deleteById(String strId) throws SQLException {

		//CoffeeDelete.javaのdeleteStockById()の引数で渡されたstrId
		//coffee_idを文字列から整数へ変換
		int id = Integer.parseInt(strId);

		//実行するSQL文を変数にまとめる
		String sql = "DELETE FROM t_coffee WHERE coffee_id = " + id + ";";

		//PostgreSQL接続オブジェクト
		//DBとの接続担当
		Connection con = null;
		//ステートメントオブジェクト
		//SQL文の色々担当
		Statement stmt = null;

		try {

			//PostgreSQLへの接続
			con = DriverManager.getConnection(Constant.JDBC_CONNECTION, Constant.JDBC_USER, Constant.JDBC_PASS);

			//ステートメントの生成
			stmt = con.createStatement();

			//戻り値は削除した件数
			return stmt.executeUpdate(sql);

		} catch (Exception e) {
			//スタックトレース表示
			e.printStackTrace();

			//異常終了時の戻り値
			return Constant.RET_NG;

		} finally {
			//リソースを閉じる
			if (stmt != null)
				stmt.close();
			if (con != null)
				con.close();
		}
	}

	/**
	 * 説明：登録フォームで入力されたデータをDBに登録するメソッド
	 * @param coffeeDto
	 * @return 登録した件数
	 * @throws SQLException
	 * @throws IOException
	 */
	public int insertCoffee(CoffeeDto coffeeDto) throws SQLException {

		String sql = "INSERT INTO t_coffee "
				+ "(coffee_id, coffee_name, coffee_price, coffee_stock, last_date, create_date, update_date) "
				+ "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

		//try-with-resources文
		//自動でclose()が呼ばれる
		try (Connection con = DriverManager.getConnection(Constant.JDBC_CONNECTION, Constant.JDBC_USER,
				Constant.JDBC_PASS);
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			//インジェクション対策
			//?(予約席)に左から数えて順番に値をセットする
			pstmt.setInt(1, coffeeDto.getCoffeeId());
			pstmt.setString(2, coffeeDto.getCoffeeName());
			
			if (coffeeDto.getCoffeePrice() != null) {
				pstmt.setInt(3, coffeeDto.getCoffeePrice());
			} else {
				pstmt.setNull(3, java.sql.Types.INTEGER);
			}
			
			if (coffeeDto.getCoffeeStock() != null) {
				pstmt.setInt(4, coffeeDto.getCoffeeStock());
			} else {
				pstmt.setNull(4, java.sql.Types.INTEGER);
			}
			/**
			登録フォームで最終入荷日が未入力で送信された場合の対策
			最終入荷日が未入力でくると、insertCoffee()のSQL文には'null'という文字列が入る為、
			そのまま実行するとDATE型にnullが入る事になるので型エラーが発生してしまう。(SQLExcepption)
			なので、setNull(5, java.sql.Types.DATE)でDate型のnullだと言うことを指定していて
			その結果、SQL文にはシングルクォーテーション無しのSQLのnullがセットされてエラーを解消できる
			 */
			//最終入荷日がnullじゃない時
			if (coffeeDto.getLastDate() != null) {
				//?(予約席)に入力された最終入荷日をセットする
				pstmt.setDate(5, coffeeDto.getLastDate());
			} else {
				//pstmt.setNull(int parameterIndex, int sqlType)
				//parameterIndex → 何番目の?にセットするか
				//sqlType → SQLの型を指定。今回はjava.sql.Types.DATE(DATE型)
				//5番目の?にDate型のNULLをセットすると言う意味
				pstmt.setNull(5, java.sql.Types.DATE);
			}

			//SQL文実行
			//戻り値は登録した件数
			return pstmt.executeUpdate();
		}
	}

	/**
	 * 説明：更新フォームで入力されたデータを更新するメソッド
	 * @param coffeeDto
	 * @return 更新した件数
	 * @throws SQLException
	 */
	public int updateCoffee(CoffeeDto coffeeDto) throws SQLException {

		//SQL実行分
		String sql = "UPDATE t_coffee SET coffee_name = ?, coffee_price = ?, coffee_stock = ?, last_date = ?, update_date = CURRENT_TIMESTAMP WHERE coffee_id = ?";

		//try-with-resources
		//自動でclose()が呼ばれる(リソース閉じ忘れ防止)
		try (Connection con = DriverManager.getConnection(Constant.JDBC_CONNECTION, Constant.JDBC_USER,
				Constant.JDBC_PASS);
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			//SQL文の？(予約席)に左から数えて1~5番の順序でcoffeeDtoのフィールドをセットする
			pstmt.setString(1, coffeeDto.getCoffeeName());
			pstmt.setInt(2, coffeeDto.getCoffeePrice());
			pstmt.setInt(3, coffeeDto.getCoffeeStock());
			pstmt.setDate(4, coffeeDto.getLastDate());
			pstmt.setInt(5, coffeeDto.getCoffeeId());

			//SQL文実行
			//戻り値は更新した件数
			return pstmt.executeUpdate();
		}
	}

	/**
	 * 説明：商品IDの重複チェックをするメソッド
	 * @param coffeeId:商品ID
	 * @return 真偽値
	 * @throws SQLException
	 */
	public boolean isDuplicate(Integer coffeeId) throws SQLException {

		//SQL実行文
		//coffee_tableの中で、coffee_id が coffeeId と一致する行が何件あるかを数える処理
		//?：インジェクション対策(不正なSQl文を入れさせないように防止する)
		String sql = "SELECT COUNT(*) FROM t_coffee WHERE coffee_id = ?";

		//自分の勉強のためtry-with-resources文に挑戦
		//自動でclose()が呼ばれる(リソース閉じ忘れ防止)
		try (Connection con = DriverManager.getConnection(Constant.JDBC_CONNECTION, Constant.JDBC_USER,
				Constant.JDBC_PASS);
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			//SQL文の?(予約席)に値をセットする
			//1（第一引数）：左から数えて1番目の?(予約席)という意味
			//coffeeId(第二引数):?(予約席)に、この変数の中身を入れろという意味
			//String型のデータを入れたい場合はsetStirng()にする
			pstmt.setInt(1, coffeeId);

			//SQLの実行
			//先にprepareStatementにSQL文をセットしているのでexecuteQueryの中は空
			ResultSet rs = pstmt.executeQuery();

			//ResultSetはデータの1行目よりも前（見出しの部分）を指しているので、データを読み込む為に、矢印(カーソル)を1行進める
			if (rs.next()) {
				// カウントが1以上なら「既に存在する」
				//戻り値はboolean型で、trueならメソッド呼び出し側のif文が実行される
				//現在指している行の1番目の列のint型で取得し、真偽値で判定せよ
				return rs.getInt(1) > 0;
			}
			return false;
		}
	}

	/**
	 * 説明：商品ID・価格・在庫数量・最終入荷日の並べ替えをするメソッド
	 * @param SortVal:セレクトボックスで選択されたvalue値
	 * @return CoffeeDtoオブジェクトのリスト
	 * @throws SQLException
	 */
	public List<CoffeeDto> sort(String sortVal) throws SQLException {

		//インスタンス生成
		List<CoffeeDto> list = new ArrayList<>();

		//SQL文
		String sql = "SELECT * FROM t_coffee";

		//switch式で条件分岐
		String sortedSQL = switch (sortVal) {
		case "id_asc" -> sql + " ORDER BY coffee_id ASC";
		case "id_desc" -> sql + " ORDER BY coffee_id DESC";
		case "price_asc" -> sql + " ORDER BY coffee_price ASC";
		case "price_desc" -> sql + " ORDER BY coffee_price DESC";
		case "stock_asc" -> sql + " ORDER BY coffee_stock ASC";
		case "stock_desc" -> sql + " ORDER BY coffee_stock DESC";
		case "lastDate_asc" -> sql + " ORDER BY last_date ASC";
		case "lastDate_desc" -> sql + " ORDER BY last_date DESC";
		default -> sql + " ORDER BY coffee_id ASC";
		};

		//PostgreSQL接続オブジェクト
		//DBとの接続担当
		Connection con = null;
		//ステートメントオブジェクト
		//SQL文の色々担当
		Statement stmt = null;
		//SQL実行オブジェクト
		ResultSet rs = null;

		try {
			//PostgreSQLへの接続
			//引数はPostgreSQLのURL
			con = DriverManager.getConnection(Constant.JDBC_CONNECTION, Constant.JDBC_USER, Constant.JDBC_PASS);

			//ステートメントの生成
			stmt = con.createStatement();

			//SQl文の実行
			rs = stmt.executeQuery(sortedSQL);

			//リストに格納
			while (rs.next()) {
				//SELECT文の結果を1行ずつ読み込む
				//CoffeeDtoクラスのコンストラクタに引数として渡す
				//引数はデータベース上のカラム名
				list.add(new CoffeeDto(rs.getInt("coffee_id"),
						rs.getString("coffee_name"),
						rs.getInt("coffee_price"),
						rs.getInt("coffee_stock"),
						rs.getDate("last_date")));
			}

			return list;

		} catch (Exception e) {
			//スタックトレース表示
			e.printStackTrace();
		} finally {
			//リソースを閉じる
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (con != null)
				con.close();
		}
		return list;
	}
}