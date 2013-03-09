package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import play.mvc.Controller;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class JsonTest extends Controller {

	public static Map map = new ConcurrentHashMap<String, String>();

    public static void getJson() throws Exception {

    	// リクエストからJSONテキストを取得
    	String text = params.get("body");
		Gson gson = new Gson();
		// JSONをJAVAオブジェクトに変換
		JsonData jsonData = gson.fromJson(text, JsonData.class);

		List<String> list = new ArrayList();
		// （MONGO）
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("playtest");
		db.authenticate("user", "password".toCharArray());
		DBCollection col = db.getCollection("play");
		DBObject query = new BasicDBObject();
		query.put("name", jsonData.userId);
		DBObject sortObject = new BasicDBObject();
		sortObject.put("nameEda", -1);

//		// 枝番取得
//		DBCursor cursor = col.find(query).sort(sortObject).limit(1);
//		while(cursor.hasNext()) {
//			list.add((String)cursor.next().get("email"));
//		}

//		getLock(jsonData.userId);

		// 枝番取得
		DBCursor cursor = col.find(query).sort(sortObject).limit(1);
		int number = 0;
		while(cursor.hasNext()) {
			number = (Integer)cursor.next().get("nameEda");
		}

		// インサート
		DBObject insertQuery = new BasicDBObject();
		insertQuery.put("name", jsonData.userId);
		insertQuery.put("nameEda", number + 1);
		insertQuery.put("email", jsonData.program + "test@aaa.com" + (number + 1));
		col.insert(insertQuery);

		// mapから削除
		map.remove(jsonData.userId);

    	renderJSON(list);
    }

    synchronized static void getLock(String userId) {
		while (map.containsKey(userId)) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		map.put(userId, "1");
    }

    // リクエストJSONの設定クラス
    public class JsonData {
    	public  String userId;
    	public  String program;

    	public JsonData(String userId, String program) {
    		this.userId = userId;
    		this.program = program;
    	}
    }

}