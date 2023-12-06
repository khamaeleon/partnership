package com.datanuri.partnership.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * create on 2023-12-07. create by IntelliJ IDEA.
 *
 * <p> 클래스 설명 </p>
 *
 * @author ytkim
 * @version 1.0
 * @since 1.0
 */
public class HttpUtil {

  private static String REST_KEY = "d7648a42fb345572d43002397bc66171";

  public static void saveApi(String address, String drinkYn, String type){

    HttpURLConnection conn = null;
    JsonObject responseJson = null;

    try {
      //URL 설정
      URL url = new URL("http://223.130.129.189:9999/api/v1/input/tool/schema/data/add?tableName=mineral_info&transferedDataYn=false&userId=ytkim.develop");

      conn = (HttpURLConnection) url.openConnection();

      // type의 경우 POST, GET, PUT, DELETE 가능
      conn.setRequestMethod(type);
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setRequestProperty("Transfer-Encoding", "chunked");
      conn.setRequestProperty("Connection", "keep-alive");
      conn.setDoOutput(true);


      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
      // JSON 형식의 데이터 셋팅
      JsonObject commands = new JsonObject();
      JsonArray jsonArray = new JsonArray();

      commands.addProperty("address", address);
      commands.addProperty("drink_yn", "비음용".equals(drinkYn));
      // JSON 형식의 데이터 셋팅 끝

      // 데이터를 STRING으로 변경
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String jsonOutput = gson.toJson(commands);

      bw.write(commands.toString());
      bw.flush();
      bw.close();

      // 보내고 결과값 받기
      int responseCode = conn.getResponseCode();
      if (responseCode == 200) {
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line = "";
        while ((line = br.readLine()) != null) {
          sb.append(line);
        }

        // 응답 데이터
        System.out.println("responseJson :: " + sb.toString());
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      System.out.println("not JSON Format response");
    }
  }


  public static void retrievelatlng(String address){

    HttpURLConnection conn = null;
    JsonObject responseJson = null;

    try {
      //URL 설정
      URL url = new URL("https://dapi.kakao.com/v2/local/search/address.json");

      conn = (HttpURLConnection) url.openConnection();

      // type의 경우 POST, GET, PUT, DELETE 가능
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Authorization","KakaoAK " + REST_KEY);
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setRequestProperty("Transfer-Encoding", "chunked");
      conn.setRequestProperty("Connection", "keep-alive");

      conn.setDoOutput(true);


      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
      // JSON 형식의 데이터 셋팅
      JsonObject commands = new JsonObject();
      JsonArray jsonArray = new JsonArray();

      commands.addProperty("query", address);
      // JSON 형식의 데이터 셋팅 끝

      // 데이터를 STRING으로 변경
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String jsonOutput = gson.toJson(commands);

      bw.write(commands.toString());
      bw.flush();
      bw.close();

      // 보내고 결과값 받기
      int responseCode = conn.getResponseCode();
      if (responseCode == 200) {
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line = "";
        while ((line = br.readLine()) != null) {
          sb.append(line);
        }

        // 응답 데이터
        System.out.println("responseJson :: " + sb.toString());
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      System.out.println("not JSON Format response");
    }
  }
}

