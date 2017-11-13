package br.com.dynara.agenda;

import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Dynara on 09/07/2017.
 */

public class WebClient {
    public String post(String json){
        String endereco = "https://www.caelum.com.br/mobile";
        return realizaRequisicao(json, endereco);
    }

    private String realizaRequisicao(String json, String endereco) {
        try {
            URL url = new URL(endereco);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-type", "application/json");

            connection.setDoInput(true);
            connection.setDoOutput(true);

            PrintStream saida = new PrintStream(connection.getOutputStream());
            saida.println(json);

            connection.connect();

            String resposta = new Scanner(connection.getInputStream()).next();

            return resposta;


        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
