import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Cep;

import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite seu cep pra consulta: ");
        var input = scanner.next();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try{
            String path = "https://viacep.com.br/ws/" + input + "/json/";

            HttpClient client =  HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(path))
                    .build();


            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            String json = response.body();

            System.out.println(json);


            Cep cep = gson.fromJson(json, Cep.class);


            FileWriter escrita = new FileWriter("cep.json");
            escrita.write(cep.toString());
            escrita.close();;

            System.out.println("Arquivo json criado!");


        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }



    }
}