import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

public class Ar459ExchangeRateApp {

    private static final String API_KEY = System.getenv("EXCHANGE_API_KEY");
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public static void main(String[] args) throws IOException, InterruptedException {
        if (API_KEY == null) {
            System.out.println("Por favor, configure la variable de entorno EXCHANGE_API_KEY.");
            return;
        }

        String endpoint = BASE_URL + API_KEY + "/latest/USD";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        JsonObject json = JsonParser.parseString(body).getAsJsonObject();

        if (!"success".equals(json.get("result").getAsString())) {
            System.out.println("Error en la respuesta de la API.");
            return;
        }

        JsonObject rates = json.getAsJsonObject("conversion_rates");

        // Mostrar tasas para las monedas objetivo
        List<String> monedas = List.of("MXN", "BRL", "GTQ", "RUB", "CNY", "TRY", "ZAR");
        System.out.println("Tasas de cambio para USD:");
        for (String moneda : monedas) {
            if (rates.has(moneda)) {
                double rate = rates.get(moneda).getAsDouble();
                System.out.printf("1 USD = %.4f %s%n", rate, moneda);
            }
        }

        // Solicitar monto y moneda destino
        Scanner sc = new Scanner(System.in);
        System.out.print("\nIngrese un monto en USD: ");
        double monto = sc.nextDouble();
        System.out.print("Ingrese la moneda de destino (ej. MXN): ");
        String destino = sc.next().toUpperCase();

        if (!rates.has(destino)) {
            System.out.println("Moneda no válida o no soportada.");
            return;
        }

        double tasa = rates.get(destino).getAsDouble();
        double resultado = monto * tasa;

        System.out.printf("%.2f USD = %.2f %s%n", monto, resultado, destino);
    }
}
