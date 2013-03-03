package controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Test;

import play.db.DB;
import play.mvc.Controller;

import com.google.gson.Gson;

public class JsonTest extends Controller {

    public static void getJson() {

    	// リクエストからJSONテキストを取得
    	String text = params.get("body");
		Gson gson = new Gson();
		// JSONをJAVAオブジェクトに変換
		JsonData jsonData = gson.fromJson(text, JsonData.class);

//    	Connection conn = DB.getConnection();
//    	List<String> list = new ArrayList();
//    	try {
//    		ResultSet rs =  conn.createStatement().executeQuery("select name from test where id = '" + jsonData.userId + "'");
//    		while(rs.next()) {
//    			list.add(rs.getString("name"));
//    		}
//		} catch (SQLException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}

		// IDで検索する
    	List<Test> test = Test.find("ID",  jsonData.userId).fetch();
    	List<String> list = new ArrayList();
    	for (Test value : test) {
    		list.add(value.name);
    	}
    	String gsonStr = gson.toJson(list, ArrayList.class);
    	System.out.println(gsonStr);

    	renderJSON(list);
    }


    // リクエストJSONの設定クラス
    public class JsonData {
    	public  String userId;

    	public JsonData(String userId) {
    		this.userId = userId;
    	}
    }

}