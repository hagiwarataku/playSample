package sample;

import java.net.UnknownHostException;

import com.google.gson.Gson;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

public class MongoInsertTest {

	public static void main(String[] args) throws UnknownHostException {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("playtest");
		db.authenticate("user", "password".toCharArray());
		DBCollection col = db.getCollection("play");
//		DBCursor cursor = col.find();
//		while(cursor.hasNext()) {
//			System.out.println(cursor.next());
//		}

		MongoTestData mongoData = new MongoTestData();
		mongoData.setName("tanaka");
		mongoData.setEmail("123456@tanaka.com");

		// json形式のStringをparseしてオブジェクトを生成する
		Gson gson = new Gson();
		String jsonStr = gson.toJson(mongoData);
		DBObject dbObject = (DBObject) JSON.parse(jsonStr);
		col.insert(dbObject);

	}
}
