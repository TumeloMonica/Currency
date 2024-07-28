import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class task4 {

    private static final String API_KEY = "YOUR_API_KEY"; // Replace with your API key
    private static String https;
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + https://www.exchangerate-api.com/  + "/latest/";

    public static <JSONObject> void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Select base and target currencies
        System.out.print("Enter base currency (e.g., USD): ");
        String baseCurrency = scanner.nextLine().toUpperCase();

        System.out.print("Enter target currency (e.g., EUR): ");
        String targetCurrency = scanner.nextLine().toUpperCase();

        // Step 2: Fetch real-time exchange rates from API
        try {
            JSONObject exchangeRates = fetchExchangeRates(baseCurrency);
            if (exchangeRates != null) {
                // Step 3: Input amount to convert
                System.out.print("Enter amount to convert from " + baseCurrency + " to " + targetCurrency + ": ");
                double amount = scanner.nextDouble();

                // Step 4: Perform currency conversion
                double exchangeRate = ((Object) exchangeRates).getJSONObject("conversion_rates").getDouble(targetCurrency);
                double convertedAmount = amount * exchangeRate;

                // Step 5: Display results
                System.out.printf("%.2f %s = %.2f %s\n", amount, baseCurrency, convertedAmount, targetCurrency);
            } else {
                System.out.println("Failed to fetch exchange rates. Please try again later.");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static <JSONObject> JSONObject fetchExchangeRates(String baseCurrency) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + baseCurrency))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return new JSONObject(response.body());
        } else {
            System.out.println("Failed to fetch exchange rates. Status code: " + response.statusCode());
            return null;
        }
    }
}
