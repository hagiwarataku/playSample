package controllers;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import models.Test;
import play.mvc.Controller;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

public class JsonTest extends Controller {

    public static void getJson() throws Exception {

    	// リクエストからJSONテキストを取得
    	String text = params.get("body");
		Gson gson = new Gson();
		// JSONをJAVAオブジェクトに変換
		JsonData jsonData = gson.fromJson(text, JsonData.class);

		// （ORACLE）JPA使用せずに検索
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

		// （ORACLE）JPA使用で検索
//    	List<Test> test = Test.find("ID",  jsonData.userId).fetch();
//    	for (Test value : test) {
//    		list.add(value.name);
//    	}

		List<String> list = new ArrayList();
		// （MONGO）
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("playtest");
		db.authenticate("user", "password".toCharArray());
		DBCollection col = db.getCollection("play");
		BasicDBObject query = new BasicDBObject();
		query.put("name", jsonData.userId);
		DBCursor cursor = col.find(query);
		while(cursor.hasNext()) {
			list.add((String)cursor.next().get("email"));
		}




    	//    	String gsonStr = gson.toJson(list, ArrayList.class);

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