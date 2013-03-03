package controllers;

import java.util.ArrayList;
import java.util.List;

import play.mvc.Controller;

import com.google.gson.Gson;

public class Application extends Controller {

    public static void index() {

    	String text = params.get("body");
//    	System.out.println(text);

    	List<String> list = new ArrayList<String>();
    	list.add("test1");
    	list.add("test2");
    	list.add("test3");

		Gson gson = new Gson();

    	renderJSON(gson.toJson(list, ArrayList.class));
    }



}