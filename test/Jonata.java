import org.json.JSONArray;
import org.json.JSONObject;


public class Jonata {

	public static void main(String[] args) {
		
		JSONArray arr = new JSONArray();
		
		JSONObject item = new JSONObject();
		item.put("idCliente", 10);
		item.put("fantasia", "ughu");
		arr.put(item);

		JSONObject item2 = new JSONObject();
		item2.put("idCliente", 1564);
		item2.put("fantasia", "asdasd");
		arr.put(item2);
		
		System.out.print(arr);
		
	}
}
