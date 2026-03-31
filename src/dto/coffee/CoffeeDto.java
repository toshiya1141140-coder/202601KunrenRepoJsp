package dto.coffee;

import java.sql.Date;

/**
 * <P>タイトル : Dtoクラス</P>
 * <P>説明 : データをそのまま詰めて運ぶだけの入れ物。処理はしない。</P>
 * @author kashiwagi toshiya 
 * @version 1.0 2026.01.29
 */
public class CoffeeDto {

	//商品ID
	private Integer coffeeId;
	//銘柄名
	private String coffeeName;
	//価格
	private Integer coffeePrice;
	//在庫数量
	private Integer coffeeStock;
	//最終入荷日
	private Date lastDate;

	public CoffeeDto() {
	}

	//コンストラクタ
	public CoffeeDto(Integer coffeeId, String coffeeName, Integer coffeePrice, Integer coffeeStock, Date lastDate) {
		this.coffeeId = coffeeId;
		this.coffeeName = coffeeName;
		this.coffeePrice = coffeePrice;
		this.coffeeStock = coffeeStock;
		this.lastDate = lastDate;

	}

	//getter setter
	public Integer getCoffeeId() {
		return coffeeId;
	}

	public void setCoffeeId(Integer coffeeId) {
		this.coffeeId = coffeeId;
	}

	public String getCoffeeName() {
		return coffeeName;
	}

	public void setCoffeeName(String coffeeName) {
		this.coffeeName = coffeeName;
	}

	public Integer getCoffeePrice() {
		return coffeePrice;
	}

	public void setCoffeePrice(Integer coffeePrice) {
		this.coffeePrice = coffeePrice;
	}

	public Integer getCoffeeStock() {
		return coffeeStock;
	}

	public void setCoffeeStock(Integer coffeeStock) {
		this.coffeeStock = coffeeStock;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
}
