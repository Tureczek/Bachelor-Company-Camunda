package com.example.workflow;

import com.google.common.base.Splitter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class CheckCvr {


    public static void main(String[] args) throws IOException, InterruptedException {
        CheckCvr checkCvr = new CheckCvr();

       for (String s : checkCvr.checkingForMail("10007372")){
          System.out.println(s);
       }
    }


    public List<String> checkingForMail(String cvr) throws IOException, InterruptedException {
        final Logger logger = LoggerFactory.getLogger(CheckCvr.class);

        List<String> mails = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8002/companies/search/cvr/" + cvr))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String newString = response.body();


        JSONObject jsonObject = new JSONObject(newString);

        JSONArray jsonArray = jsonObject.getJSONArray("departments");


        for (int i = 0; i < jsonArray.length(); i++) {
            String obj = jsonArray.get(i).toString().replaceAll("[{}\"]", "");
            Map<String, String> map = Splitter.on(",").withKeyValueSeparator(':').split(obj);
            mails.add(map.get("email"));

        }

        return mails;
    }
}