<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// index.jsp の役割：
// プロジェクトのトップページ（ルート）にアクセスされたときに
// 自動で DspCoffeeSearch サーブレットにリダイレクトする
// 初期表示としてコーヒー一覧画面を表示させる
    response.sendRedirect("DspCoffeeSearch");
%>