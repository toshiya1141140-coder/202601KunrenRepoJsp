 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- forEachを使用するためのライブラリ -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>コーヒー在庫管理画面</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/coffee/coffeeSearch.css">
  <script src="<%=request.getContextPath()%>/js/coffee/coffeeSearchList.js" type="text/javascript"></script>
</head>

<body>
	<!--ヘッダー-->
	<header>
		<div class="left_area">
			<img src="<%=request.getContextPath()%>/img/coffee/01385.png" alt="Kashiwagi Coffee ロゴ" class="cup_logo">
			<div class="shop_name">Kashiwagi Coffee</div>
			<img src="<%=request.getContextPath()%>/img/coffee/coffee_logo.png" alt="Kashiwagi Coffee ロゴ" class="shop_logo">
		</div>
		<h1>在庫 一覧</h1>
		<a href="<%=request.getContextPath()%>/CoffeeCsvDownload" class="btn_csv">CSV出力</a>
		<form method="post">
			<div class="right_area">
				<button type="button" class="btn_logout" onclick="fnc_logout(this.form)">ログアウト</button>
			</div>
		</form>
	</header>
	<!--検索エリア-->
	<form action="DspCoffeeSearch" method="post" class="search_area">
		<div class="search_row">
			<label>商品ID： <input type="text" name="text_coffee_id" value=""></label>
			<input type="button" name="search_btn" value="検索" onclick="fnc_search(this.form)">
		</div>
		<div class="search_row">
			<label>銘柄名： <input type="text" name="text_coffee_name" value=""></label>
			<input type="reset" name="clear_btn" value="クリア" onclick="fnc_clear(this.form)">
		</div>
	</form>
	<form id="js_form" method="post">
		<div class="sort_area">
    		<label>並べ替え：</label>
    		<select id="mainSort" name="sortVal">
        		<option value="">-- 選択してください --</option>
        		<option value="id_asc" ${param.sortVal == 'id_asc' ? 'selected' : ''}>商品ID：昇順</option>
        		<option value="id_desc" ${param.sortVal == 'id_desc' ? 'selected' : ''}>商品ID：降順</option>
        		<option value="name_asc" ${param.sortVal == 'name_asc' ? 'selected' : ''}>銘柄名：五十音順(あ→ん)</option>
        		<option value="name_desc" ${param.sortVal == 'name_desc' ? 'selected' : ''}>銘柄名：五十音順(ん→あ)</option>
        		<option value="price_asc" ${param.sortVal == 'price_asc' ? 'selected' : ''}>価格：安い順</option>
        		<option value="price_desc" ${param.sortVal == 'price_desc' ? 'selected' : ''}>価格：高い順</option>
     			<option value="stock_asc" ${param.sortVal == 'stock_asc' ? 'selected' : ''}>在庫：少ない順</option>
        		<option value="stock_desc" ${param.sortVal == 'stock_desc' ? 'selected' : ''}>在庫：多い順</option>
        		<option value="lastDate_asc" ${param.sortVal == 'lastDate_asc' ? 'selected' : ''}>入荷日：古い順</option>
        		<option value="lastDate_desc" ${param.sortVal == 'lastDate_desc' ? 'selected' : ''}>入荷日：新しい順</option>
    		</select>
		</div>
		<!--在庫一覧テーブル-->
		<table border="1" class="coffee_table">
			<tr>
				<th></th>
				<th>商品ID</th>
				<th>銘柄名</th>
				<th>価格(100g)</th>
				<th>在庫数量(g)</th>
				<th>最終入荷日</th>
			</tr>
			<c:forEach items="${dbdata}" var="dbdataLine">
				<tr>
					<td><input type="radio" name="radio" value="${dbdataLine.coffeeId}" checked></td>
					<td>${dbdataLine.coffeeId}</td>
					<td>${dbdataLine.coffeeName}</td>
					<td>${dbdataLine.coffeePrice}円</td>
					<td class="${dbdataLine.coffeeStock == 0 ? 'out-stock' :  dbdataLine.coffeeStock < 500 ? 'low-stock' : ''}">
						<c:choose>
							<c:when test="${dbdataLine.coffeeStock == 0}">
								在庫切れ
							</c:when>
							<c:otherwise>
								${dbdataLine.coffeeStock}g
							</c:otherwise>
						</c:choose>
					</td>
					<td>${dbdataLine.lastDate}</td>
				</tr>
			</c:forEach>
		</table>
		<!-- ボタンエリア -->
		<div class="action-buttons">
			<button type="button" class="btn_entry btn" onclick="fnc_insert(this.form)">新規登録</button>
			<button type="button" class="btn_update btn" onclick="fnc_update(this.form)">更新</button>
			<button type="button" class="btn_delete btn" onclick="fnc_delete(this.form)">削除</button>
		</div>
		<!-- データが削除された場合に表示 -->
		<c:if test="${!empty deleteMsg}">
			<div class="msg">
				<strong>${deleteMsg}</strong>
			</div>
		</c:if>
		<!-- データが更新された場合に表示 -->
		<c:if test="${!empty updateMsg}">
			<div class="msg">
				<strong>${updateMsg}</strong>
			</div>
		</c:if>
		<!-- エラーメッセージ表示 -->
		<c:if test="${!empty errorMsg}">
			<div class="msg">
				<strong>${errorMsg}</strong>
			</div>
		</c:if>
	</form>
</body>

</html>