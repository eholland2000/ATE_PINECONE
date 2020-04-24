package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.*;

public class FetchData {
	
	private static ArrayList<CustomerOrder> building;
	
	public static ArrayList<CustomerOrder> loadCustomerOrders() {
		building = new ArrayList<>();
		getOrders();
		for (int a = 0; a < building.size(); a++) {
			building.get(a).setItems(getOrderItems(building.get(a).getOrderID()));
		}
		return building;
	}
	
	/**
	 * Gets orders with processing status
	 */
    private static void getOrders() {
        String url = "http://group8fastfit.com/app_data/openOrders.php";
        JSONArray json;
		try {
			json = readJsonFromUrl(url);
			building = new ArrayList<>();
			int index = -1;
			for (int i = 0; i < json.length(); i++) {
				boolean newEntry = true;
				for (int j = 0; j < building.size(); j++) {
					if (building.get(j).getOrderID().equals(json.getJSONObject(i).getString("ID"))) {
						newEntry = false;
						break;
					}
				}
				if (newEntry) {
					building.add(new CustomerOrder());
					index++;
					building.get(index).setOrderID(json.getJSONObject(i).getString("ID"));
					building.get(index).setOrderDateTime(json.getJSONObject(i).getString("post_date"));
					building.get(index).setStatus("Processing");
				}
				if(json.getJSONObject(i).getString("meta_key").equals("_billing_address_1")) {
					building.get(index).setAddress1(json.getJSONObject(i).getString("meta_value"));
				} else if (json.getJSONObject(i).getString("meta_key").equals("_billing_address_2")) {
					building.get(index).setAddress2(json.getJSONObject(i).getString("meta_value"));
				} else if (json.getJSONObject(i).getString("meta_key").equals("_billing_city")) {
					building.get(index).setCity(json.getJSONObject(i).getString("meta_value"));
				} else if (json.getJSONObject(i).getString("meta_key").equals("_billing_state")) {
					building.get(index).setState(json.getJSONObject(i).getString("meta_value"));
				} else if (json.getJSONObject(i).getString("meta_key").equals("_billing_postcode")) {
					building.get(index).setZip(json.getJSONObject(i).getString("meta_value"));
				}
				
			}
        } catch (IOException | JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
    private static ArrayList<Product> getOrderItems(String id) {
    	ArrayList<Product> products = new ArrayList<>();
        String url = "http://group8fastfit.com/app_data/order_products.php?orderID=%27" + id + "%27";
        JSONArray json;
        String url2 = "http://group8fastfit.com/app_data/product_skus.php";
		try {
			json = readJsonFromUrl(url);
			int index = -1;
			int orderItemID = 0;
			for (int i = 0; i < json.length(); i++)
			{
				boolean newEntry = true;
				for (int j = 0; j < products.size(); j++) {
					if (orderItemID == Integer.parseInt(json.getJSONObject(i).getString("order_item_id"))) {
						newEntry = false;
						break;
					}
				}
				if (newEntry) {
					products.add(new Product());
					index++;
					orderItemID = Integer.parseInt(json.getJSONObject(i).getString("order_item_id"));
					products.get(index).setName(json.getJSONObject(i).getString("order_item_name"));
				}
				if(json.getJSONObject(i).getString("meta_key").equals("_product_id")) {
					products.get(index).setSKU(Integer.parseInt(json.getJSONObject(i).getString("meta_value")));
				} else if (json.getJSONObject(i).getString("meta_key").equals("_qty")) {
					products.get(index).setStockIn(Integer.parseInt(json.getJSONObject(i).getString("meta_value")));
				}
			}
			json = readJsonFromUrl(url2);
			index = -1;
			for (int i = 0; i < json.length(); i++) 
			{
				for (int j = 0; j < products.size(); j++) 
				{
					if(products.get(j).getSKU() == Integer.parseInt(json.getJSONObject(i).getString("product_id"))) {
						products.get(j).setSKU(Integer.parseInt(json.getJSONObject(i).getString("sku")));
						break;
					} 
				}
			}
			
			return products;
        } catch (IOException | JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
    }
    
    public static void setCompletedOrder(String id) {
        try {
            // get URL content
            URL url = new URL("http://group8fastfit.com/app_data/completeOrder.php?ID='" + id + "'");
            BufferedReader in = new BufferedReader(
            new InputStreamReader(url.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            in.close();
            
            System.out.println("Done");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    
    private static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
          BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
          String jsonText = readAll(rd);
          JSONArray json = new JSONArray(jsonText);
          return json;
        } finally {
          is.close();
        }
    }
    
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
          sb.append((char) cp);
        }
        return sb.toString();
      }

    
    public static void main(String[] args) {
    	(new FetchData()).getOrders();
    }
}
