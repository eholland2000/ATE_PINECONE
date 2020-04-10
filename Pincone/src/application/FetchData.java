package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.*;

public class FetchData {
	
	private ArrayList<CustomerOrder> building;
	
	public FetchData() {
		building = new ArrayList<>();
	}
	
	public ArrayList<CustomerOrder> loadCustomerOrders() {
		//getOrders();
		return building;
	}
	
//    public void getOrders() {
//        String url = "http://group8fastfit.com/app_data/orders.php";
//        JSONArray json;
//		try {
//			json = readJsonFromUrl(url);
//			System.out.println(json.toString());
//			for (int i = 0; i < json.length(); i++)
//			{
//				building.add(new CustomerOrder());
//				json.getJSONObject(i).getString("order_id"));
//			}
//        } catch (IOException | JSONException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//    }
//    
//    public void getOrders() {
//        String url = "http://group8fastfit.com/app_data/orders.php";
//        JSONArray json;
//		try {
//			json = readJsonFromUrl(url);
//			System.out.println(json.toString());
//			for (int i = 0; i < json.length(); i++)
//			{
//			  System.out.println(json.getJSONObject(i).getString("order_id"));
//			}
//        } catch (IOException | JSONException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//    }
//    
//    private JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
//        InputStream is = new URL(url).openStream();
//        try {
//          BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//          String jsonText = readAll(rd);
//          JSONArray json = new JSONArray(jsonText);
//          return json;
//        } finally {
//          is.close();
//        }
//    }
//    
//    private String readAll(Reader rd) throws IOException {
//        StringBuilder sb = new StringBuilder();
//        int cp;
//        while ((cp = rd.read()) != -1) {
//          sb.append((char) cp);
//        }
//        return sb.toString();
//      }
//
//    
//    public static void main(String[] args) {
//    	(new FetchData()).getOrders();
//    }
}
