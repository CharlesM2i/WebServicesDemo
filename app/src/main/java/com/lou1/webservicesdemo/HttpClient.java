package com.lou1.webservicesdemo;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient extends Thread {

    public HttpClient() {

    }
    public HttpClient(String adr) {
        setAdr(adr);
    }
    public HttpClient(String adr, String method) {
        setAdr(adr);
        setMethod(method);
    }
    public HttpClient(String adr, String method, String body)
    {
        setAdr(adr);
        setMethod(method);
        setBody(body);
    }
    // Propriétés :
    private String adr = "";

    public String getAdr()
    {
        return adr;
    }
    public void setAdr(String value)
    {
        adr = value;
    }
    private String method = "GET";

    public String getMethod()
    {
        return method;
    }
    public void setMethod(String value)
    {
        method = value;
    }
    private String body = "";

    public String getBody()
    {
        return body;
    }
    public void setBody(String value)
    {
        body = value;
    }
    private String response = "";

    public String getResponse()
    {
        return response;
    }
    // Méthode run(), effectue la connexion, écrit les données et lit la réponse
    @Override
    public void run() {
        URL url;
        HttpURLConnection cnt = null;
        try {
            url = new URL(adr);
            // Établir la connexion :
            cnt = (HttpURLConnection) url.openConnection(); cnt.setRequestMethod(method); cnt.setDoInput(true);
            // Envoyer les données :
            if(method.equals("POST")) { cnt.setDoOutput(true);
                OutputStream out = cnt.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter( out, "UTF-8"));
                writer.write(body);
                writer.flush();
                writer.close();
                out.close();
            }
            // Lire la réponse :
            InputStream in = cnt.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in)); response = "";
            String line = "";
            while ((line = reader.readLine()) != null) {
                response += line;
            }
            reader.close();
            in.close();
        }
        catch (Exception ex) {
            response += "\nErreur : " + ex.getMessage(); }
        finally {
            cnt.disconnect();
        }
    }
}

