package cnst;
/**
 *
 * システム共通定数定義クラス
 *
 * @version 00.00 2020/11/20
 */
public class Constant {

	/******** ↓キー **********/
	/** データベース接続 **/
	public static final String JDBC_CONNECTION = "jdbc:postgresql://localhost:5432/postgres";
	public static final String JDBC_USER = "postgres";
	public static final String JDBC_PASS = "postgres";

	//戻り値
	public static final int RET_OK = 0;
	public static final int RET_NG = -1;
	public static final String RET_ERR_UPLOAD = "ERR_UPLOAD";

	/******** ↓パス **********/
	/** ログイン画面 **/
	public static final String LOGIN = "/jsp/Login.jsp";
	/** メニュー画面 **/
	public static final String MENU = "/jsp/Menu.jsp";
	/** エラー画面 **/
	public static final String SYSTEM_ERR = "/jsp/ErrSystem.jsp";
	/** エラー画面 **/
	public static final String LOGIN_ERR = "/jsp/ErrSystemLogin.jsp";
	
	/******** ↓ No.11 矢田 **********/
	/** 一覧画面 **/
	public static final String GAME_HOME = "/jsp/game/home.jsp";
	/** 登録・更新画面 **/
	public static final String GAME_EDIT = "/jsp/game/edit.jsp";

	/******** ↓文字列定数 **********/
	public static final String COLON = ":";

	/** エラーメッセージ(システムエラー：JDBCドライバ読込) **/
	public static final String MSG_SYSTEM_ERR_JDBC_DRIVER = "システムエラー：JDBCドライバ読込処理で異常終了しました。";
	/** エラーメッセージ(システムエラー：) **/
	public static final String MSG_ERR_SYSTEM = "システム管理者にご連絡をお願いします。";
	/** エラーメッセージ(システムエラー：) **/
	public static final String MSG_ERR_SYSTEM_LOGIN = "システムエラー(ログイン処理)：システム管理者にご連絡をお願いします。";
	/** エラーメッセージ(システムエラー：) **/
	public static final String MSG_ERR_SYSTEM_SEARCH = "システムエラー(検索処理)：システム管理者にご連絡をお願いします。";

	/** エラーメッセージ(ログイン) **/
	public static final String MSG_ERR_LOGIN01 = "ログインIDまたはパスワードが誤っています。";

	/** 画像ファイルパス **/
	public static final String PICT_FILE_PATH = "C:\\java_2024\\pleiades\\workspace\\202601KunrenRepoJsp\\WebContent\\upload";
	
	/** 機能ID **/
	public static final String FUNCTION_ID_01 = "paint";
	public static final String FUNCTION_ID_02 = "cafe";
	public static final String FUNCTION_ID_03 = "sightseeing";
	public static final String FUNCTION_ID_04 = "alcohol";
	public static final String FUNCTION_ID_05 = "coffee";
	public static final String FUNCTION_ID_06 = "animal";
	public static final String FUNCTION_ID_07 = "bike";
	public static final String FUNCTION_ID_08 = "car";
	public static final String FUNCTION_ID_09 = "character";
	public static final String FUNCTION_ID_10 = "todo";
	public static final String FUNCTION_ID_11 = "game";
	public static final String FUNCTION_ID_12 = "soccer";
	public static final String FUNCTION_ID_13 = "yakisoba";
}
