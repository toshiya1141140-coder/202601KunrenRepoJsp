# コーヒー在庫管理システム（JSP / Servlet）

## 概要

このプロジェクトは、コーヒー豆の情報を管理するためのWebアプリケーションです。
JSPとServletを使用して、以下の機能を実装しています。

* 検索
* 登録
* 更新
* 削除
* CSVダウンロード
* 並べ替え

---

## 使用技術

* Java
* JSP / Servlet
* JDBC（DB接続）
* HTML / CSS / JavaScript
* Apache Tomcat

---

## 機能一覧

### 1. 検索機能

商品ID・銘柄名で検索可能

### 2. 登録機能

新しいコーヒー豆の情報を登録できます

### 3. 更新機能

登録済みデータの編集

### 4. 削除機能

不要なデータの削除

### 5. CSVダウンロード

データをCSV形式で出力

### 6. 並べ替え機能

各条件に応じて並べ替えができます

---

## 画面構成

* index.jsp：トップページ（初期アクセス時に検索画面へリダイレクト）
* CoffeeSearchList.jsp：検索結果一覧
* CoffeeInsert.jsp：登録画面
* CoffeeUpdate.jsp：更新画面

---

## ディレクトリ構成

```
src/
 ├─ dao/        // DB操作
 ├─ dto/        // データ保持クラス
 ├─ pack/       // 機能ごとの処理（検索・登録など）
 ├─ cm/         // 共通Servlet
 └─ cnst/       // 定数
 
WebContent/
 ├─ jsp/coffee/ // JSP画面
 └─ index.jsp   // トップページ
```

---

## 実行方法

### 1. 環境準備

* Java（JDK）
* Eclipse（または任意のIDE）
* Tomcat（サーバー）

### 2. 手順

1. このリポジトリをクローン

```
git clone <https://github.com/toshiya1141140-coder/202601KunrenRepoJsp.git>
```

2. Eclipseにインポート

3. Tomcatを設定

4. プロジェクトを右クリック  
   →「実行」→「サーバーで実行（Run on Server）」を選択

5. ブラウザでアクセス  
http://localhost:8080/202601KunrenRepoJsp/CoffeeSearch
※ コーヒー豆在庫一覧画面が表示されます

---

## 学習ポイント
このプロジェクトでは以下を学べます：

* MVCモデルの基本構成
* ServletとJSPの連携
* DAOパターン
* CRUD処理（登録・参照・更新・削除）
