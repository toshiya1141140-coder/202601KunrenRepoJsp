package cm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ComServlet {

	public ComServlet() {

	}

	/**
	 * 文字列をNull又はEmptyをチェックする
	 * 
	 * @param	str
	 * @return true or false
	 */
	public boolean isNullOrEmpty(String str) {
	    /*
	     * 判定が逆になるとExceptionを起こすから、逆にしないこと
	     */
	    return str == null || str.isEmpty();
	}
	
	/**
	 * 日付を和暦(GGGGy年MM月dd日)のStringに変換する
	 * @param ts 日付
	 * @return 空文字 or GGGGy年MM月dd日
	 */
	public String dateFormatWareki(Date dt){

		//引数がnullの場合空文字を返却
		if (dt == null){
			return "";
		}
		
        //日本のロケールを作成
        Locale japan = new Locale("ja","JP","JP");
        //書式の作成(和暦変換)
        SimpleDateFormat sdf = new SimpleDateFormat("GGGGy年MM月dd日",japan);

		return sdf.format(dt);
	}	

}
