package donorschoosetest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Luis Martin Padilla
 */
public class DonorsChooseTest {
    public static void main(String[] args) {
        httpRequest();
    }
    
    public static void httpRequest(){
        String url = "https://api.donorschoose.org/common/json_feed.html?state=CA&APIKey=DONORSCHOOSE&max=5&costToCompleteRange=0+TO+2000&sortBy=0&keywords=";
        System.out.println("Enter the search terms:");
        Scanner sc = new Scanner(System.in);
        String searchTerm = sc.next();
        try {
            URL obj = new URL(url+searchTerm);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null){
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            JSONObject JSONContent = new JSONObject(content.toString());
            JSONArray proposals = JSONContent.getJSONArray("proposals");
            System.out.println("=======================");
            System.out.println("PROPOSALS:");
            System.out.println("=======================\n");
            for (int i = 0; i < proposals.length(); i++){
                JSONObject temp = proposals.getJSONObject(i);
                System.out.println("=======================");
                System.out.println("Title: " + temp.getString("title"));
                System.out.println("Short Description: " + temp.getString("shortDescription"));
                System.out.println("Proposal URL: " + temp.getString("proposalURL"));
                System.out.println("Cost to complete: " + temp.getString("costToComplete"));
                System.out.println("=======================\n");
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(DonorsChooseTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DonorsChooseTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(DonorsChooseTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
