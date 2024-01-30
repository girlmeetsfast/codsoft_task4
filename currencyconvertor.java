package convertor;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class currencyconvertor {
	
	
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

      
        System.out.print("enter the currency you want to convert: ");
        String money = scanner.nextLine().toUpperCase();
        System.out.print("enter the currency in which you want to convert to: ");
        
        
        String convcurrency = scanner.nextLine().toUpperCase();

    
        double exg = findexgrate(money, convcurrency);


        
        System.out.println("Enter the amount to convert from " + money + " to " + convcurrency + ": ");
        
        double amount = scanner.nextDouble();

   
        double result = amount * exg;

       
        System.out.println( money + " to " + convcurrency + ": " + result + " " + convcurrency);
    }

  
    private static double findexgrate(String baseCurrency, String convcurrency) {
        try {
            String apiKey = "b3f7b7662b4bb46ce6a3d548";
            
            
            URL url = new URL("https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + baseCurrency);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int rc = connection.getResponseCode();
            if (rc == HttpURLConnection.HTTP_OK) {
            	
            	
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String ll;

                while ((ll = reader.readLine()) != null) {
                    response.append(ll);
                }
                reader.close();

         
                String jsonResponse = response.toString();
                int tx = jsonResponse.indexOf("\"" + convcurrency + "\"");
                if (tx != -1) {
                    int rx = jsonResponse.indexOf(":", tx) + 1;
                    int cx = jsonResponse.indexOf(",", rx);
                    String exstring = jsonResponse.substring(rx, cx).trim();
                    return Double.parseDouble(exstring);
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1; 
        }
    }
};