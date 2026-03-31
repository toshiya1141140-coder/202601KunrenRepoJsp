<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <!DOCTYPE html>
  <html lang="ja">

  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>商品更新画面</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/coffee/coffeeUpdate.css">
    <script>
    //商品更新ボタン押下時
    function fnc_update(form) {
      // 入力値(銘柄名検索欄に入力されたデータ)を変数coffeeNameにまとめる
      var coffeeName = form.text_coffee_name.value;
    //銘柄名は必須項目であり、条件によっては、アラートを発令
      if (coffeeName === "") {
        alert("銘柄名を入力してください。");
        //エラー箇所にフォーカスを当てる
        form.text_coffee_name.focus();
        return;
      //銘柄名の長さチェック（50文字以内）
      }else if (coffeeName.length > 50) {
         alert("銘柄名は50文字以内で入力してください。");
  	   form.text_coffee_name.focus();
  	   return;
         }
      
      form.action = "<%=request.getContextPath()%>/CoffeeUpdate";
      form.submit();
    }
    
    //戻るボタン押下時
    function fnc_back(form) {
      form.action = "<%=request.getContextPath()%>/DspCoffeeSearch";
      form.submit();
    }
  	</script>
  </head>
  <body>
    <!--ヘッダー情報-->
    <header>
      <div class="left_area">
        <img src="<%=request.getContextPath()%>/img/coffee/01385.png" alt="Kashiwagi Coffee ロゴ" class="cup_logo">
        <div class="shop_name">Kashiwagi Coffee</div>
        <img src="<%=request.getContextPath()%>/img/coffee/coffee_logo.png" alt="Kashiwagi Coffee ロゴ" class="shop_logo">
      </div>
      <h1>商品更新</h1>
      <div class="right_area">
        <a href="#" class="login_link">ログインページ</a>
      </div>
    </header>
    <!--登録エリア-->
    <form class="entry_area" method="post">
      <div class="entry_row">
        <label>
          <p>商品ID</p> 
          <input type="text" name="text_coffee_id" value="${dbdata.coffeeId}" disabled>
          <input type="hidden" name="text_coffee_id" value="${dbdata.coffeeId}">
        </label>
        <p class="p_id">※商品IDは変更できません</p>
      </div>
      <div class="entry_row">
        <label>
          <p>
            銘柄名 <span>必須</span>
          </p> <input type="text" name="text_coffee_name"  value="${dbdata.coffeeName}">
        </label>
      </div>
      <div class="entry_row">
        <label>
          <p>価格(100g)</p> <input type="text" name="text_coffee_price"  value="${dbdata.coffeePrice}">
        </label>
      </div>
      <div class="entry_row">
        <label>
          <p>在庫数量(g)</p> <input type="text" name="text_coffee_stock"  value="${dbdata.coffeeStock}">
        </label>
      </div>
      <div class="entry_row">
        <label>
          <p>最終入荷日</p> <input type="date" name="last_date" value="${dbdata.lastDate}">
        </label> 
      </div>
      <!-- ボタンエリア -->
      <div class="action-buttons">
        <button type="button" class="btn_entry btn" onclick="fnc_update(this.form)">商品更新</button>
        <button type="button" class="btn_back btn" onclick="fnc_back(this.form)">在庫一覧へ戻る</button>
      </div>
      <!-- エラーメッセージ表示 -->
      <c:if test="${!empty errorMsg}">
        <div class="msg">
          <strong>${errorMsg}</strong>
        </div>
      </c:if>
    </form>
  </body>

  </html>