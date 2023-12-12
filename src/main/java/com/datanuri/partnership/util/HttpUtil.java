package com.datanuri.partnership.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * create on 2023-12-07. create by IntelliJ IDEA.
 *
 * <p> 클래스 설명 </p>
 *
 * @author ytkim
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class HttpUtil {
  private static String REST_KEY = "d7648a42fb345572d43002397bc66171";

  public static String getApi(String type) {

    HttpURLConnection conn = null;
    String returnVal = null;
    try {
      //URL 설정
      URL url = new URL(
          "http://223.130.129.189:9999/api/v1/produce/schema/data/List?userId=ytkim.develop&tableName=mineral_info&pageNumber=1&pageSize=10000&transferedDataYn=false");

      conn = (HttpURLConnection) url.openConnection();

      // type의 경우 POST, GET, PUT, DELETE 가능
      conn.setRequestMethod(type);
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setRequestProperty("Connection", "keep-alive");
      conn.setDoOutput(false);

      // 보내고 결과값 받기
      int responseCode = conn.getResponseCode();
      if (responseCode == 200) {
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line = "";
        while ((line = br.readLine()) != null) {
          sb.append(line);
        }
        returnVal = sb.toString();
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      System.out.println("not JSON Format response");
    }
    return returnVal;
  }

  public static void saveApi(JsonObject address, String drinkYn, String type) {

    HttpURLConnection conn = null;

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

      commands.addProperty("address_name", address.get("address_name").getAsString());
      commands.addProperty("drk_yn", "비음용".equals(drinkYn));
      commands.addProperty("lat", address.get("y").getAsString());
      commands.addProperty("lng", address.get("x").getAsString());

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
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      System.out.println("not JSON Format response");
    }
  }


  public static JsonObject retrieveLatlng(String address) {
    JsonObject obj = null;
    try {
      HttpURLConnection conn = null;

      String charset = "UTF-8";

      String query = String.format("query=%s",
          URLEncoder.encode(address, charset));
      //URL 설정
      URL url = new URL("https://dapi.kakao.com/v2/local/search/address.json" + "?" + query);

      conn = (HttpURLConnection) url.openConnection();

      // type의 경우 POST, GET, PUT, DELETE 가능
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Authorization", "KakaoAK " + REST_KEY);
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setRequestProperty("Transfer-Encoding", "chunked");
      conn.setRequestProperty("Connection", "keep-alive");
      conn.setRequestProperty("Accept-Charset", charset);

      conn.setDoOutput(true);

      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

      bw.write("{query:" + address + "}");
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

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(sb.toString());
        JsonArray jsonDocuments = (JsonArray) jsonObject.get("documents");

        for (JsonElement jsonElement : jsonDocuments) {
          obj = jsonElement.getAsJsonObject();
          obj.addProperty("address_name", address);
          log.info("address:{}, lng:{}, lat:{}", obj.get("address_name"), obj.get("x"),
              obj.get("y"));
        }
      }
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      System.out.println("not JSON Format response");
    }
    return obj;
  }
}

