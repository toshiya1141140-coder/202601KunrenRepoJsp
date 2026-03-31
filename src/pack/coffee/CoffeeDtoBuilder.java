package pack.coffee;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import dto.coffee.CoffeeDto;

/**<P>タイトル : CoffeeDto組み立てクラス</P>
 * <P>説明 : フォームから入力されたデータをcoffeeDtoにデータを渡す独自のクラス</P>
 * @author kashiwagi toshiya 
 * @version 1.0 2026.01.28
 */
//継承禁止クラス
public final class CoffeeDtoBuilder {
	/**
	 * フォームから入力されたリクエストパラメータを取得し、CoffeeDtoオブジェクトを生成する
	 * @param request
	 * @return 変換後のデータを保持するCoffeeDtoインスタンス
	 * @throws NumberFormatException :数値変換（ID・価格・在庫数量）に失敗した場合
	 * @throws IllegalArgumentException 日付変換に失敗した場合
	 */
	public static CoffeeDto buildDto(HttpServletRequest request) throws IllegalArgumentException {

		//登録画面のフォームで入力されたデータをDBへ渡すためにまず値(データ)の受け取りを行う
		//商品ID
		String strId = request.getParameter("text_coffee_id");
		//銘柄名
		String strName = request.getParameter("text_coffee_name");
		//価格
		String strPrice = request.getParameter("text_coffee_price");
		//在庫数量
		String strStock = request.getParameter("text_coffee_stock");
		//最終入荷日
		String strLastDate = request.getParameter("last_date");

		//変数初期化
		int coffeeId = 0;
		Integer coffeePrice = null;
		Integer coffeeStock = null;
		Date coffeeLastDate = null;

		//CoffeeDtoへ渡すために、それぞれの対応するデータ型へ変換させる
		//商品IDがnullや空文字の時
		if (strId == null || strId.isBlank()) {
			throw new IllegalArgumentException("商品IDは必須項目です");
		} else {
			try {
				coffeeId = Integer.parseInt(strId);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("商品IDは数値で入力してください");
			}
		}

		//銘柄名がnullや空文字の時
		if (strName == null || strName.isBlank()) {
			throw new IllegalArgumentException("銘柄名は必須項目です");
		}

		//価格がnullや空文字じゃない時
		if (strPrice != null && !strPrice.isBlank()) {
			try {
				coffeePrice = Integer.parseInt(strPrice);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("価格は数値で入力してください");
			}
		}
		//在庫数がnullや空文字じゃない時
		if (strStock != null && !strStock.isBlank()) {
			try {
				coffeeStock = Integer.parseInt(strStock);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("在庫数は数値で入力してください");
			}
		}
		//最終入荷日がnullや空文字じゃない時
		if (strLastDate != null && !strLastDate.isBlank()) {
			try {
				coffeeLastDate = Date.valueOf(strLastDate);
			} catch (IllegalArgumentException e) { // ←ここ重要
				throw new IllegalArgumentException("日付の形式が正しくありません");
			}
		}

		//戻り値はCoffeeDtoのインスタンス、coffeeDtoのコンストラクタへデータを渡す
		return new CoffeeDto(coffeeId, strName, coffeePrice, coffeeStock, coffeeLastDate);
	}
}
