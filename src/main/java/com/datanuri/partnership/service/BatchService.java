/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.datanuri.partnership.service;

import com.datanuri.partnership.util.HttpUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

@Service
@Slf4j
@RequiredArgsConstructor
public class BatchService {

  private final String API_URL = "http://223.130.129.189:9191/I2400";

  public void loadApi() {
    String response = HttpUtil.getApi("GET");

    try {
      long startTime = System.currentTimeMillis();
      DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
      Document doc = dBuilder.parse(API_URL);

      // 제일 첫번째 태그
      doc.getDocumentElement().normalize();

      // 파싱할 tag
      NodeList nList = doc.getElementsByTagName("I2400");

      NodeList childNodes = nList.item(0).getChildNodes();
      for (int j = 0; j < childNodes.getLength(); ++j) {
        if ("row".equals(childNodes.item(j).getNodeName())) {
          NodeList dataNode = childNodes.item(j).getChildNodes();
          String address = "";
          String drinkYn = "";
          JsonObject transAddress = null;
          for (int i = 0; i < dataNode.getLength(); ++i) {
            if ("ADDR".equals(dataNode.item(i).getNodeName())) {
              address = dataNode.item(i).getTextContent();
            } else if ("DKPS_YN_NM".equals(dataNode.item(i).getNodeName())) {
              drinkYn = dataNode.item(i).getTextContent();
            }
          }

          JsonObject jsonObject = (JsonObject) JsonParser.parseString(response);
          JsonArray jsonArray = (JsonArray) jsonObject.get("result");
          Iterator<JsonElement> it = jsonArray.iterator();

          boolean saveYn = true;
          while(it.hasNext()){
            JsonObject obj = it.next().getAsJsonObject();
            if(obj.get("address_name").getAsString().equals(address)){
              saveYn = false;
              break;
            }
          }
          if(saveYn){
            transAddress = HttpUtil.retrieveLatlng(address);
            if(transAddress != null){
              HttpUtil.saveApi(transAddress, drinkYn, "PUT");
            }
          }
        }
      }
      long endTime = System.currentTimeMillis();
      log.debug("size: {}, time: {}ms",nList.getLength(), endTime-startTime);
    } catch (Exception e) {
      log.debug(e.getMessage());
    }
  }

}
