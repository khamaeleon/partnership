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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service @Slf4j
@RequiredArgsConstructor
public class GeoLocationService {
  private final String KAKAO_APP_KEY = "5a6df184cdd9353147de78f77f079b42";
  private final String KAKAO_DATA_MAP_HTML = "templates/dataMap.html";
  private final String KAKAOMAP_CATEGORY_HTML = "templates/categoryMap.html";
  private final String KAKAOMAP_HTML = "templates/kakaoMap.html";

  public String getResourceCategoryByKeyword(String keyword) {
    String frame = "";

    try {
      InputStream inputStream = new ClassPathResource(KAKAOMAP_CATEGORY_HTML).getInputStream();
      frame = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8)
          .replaceAll("#appkey#", KAKAO_APP_KEY)
          .replaceAll("#keyword#", keyword);
    } catch (NullPointerException | IOException e2) {
      frame = "";
    }
    return frame;
  }

  public String getResourceByKeyword(String keyword) {
    String frame = "";

    try {
      InputStream inputStream = new ClassPathResource(KAKAO_DATA_MAP_HTML).getInputStream();
      frame = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8)
          .replaceAll("#appkey#", KAKAO_APP_KEY)
          .replaceAll("#keyword#", keyword);
    } catch (NullPointerException | IOException e2) {
      frame = "";
    }
    return frame;
  }
  public String getResourceByAddress(String keyword) {
    String frame = "";

    try {
      InputStream inputStream = new ClassPathResource(KAKAOMAP_HTML).getInputStream();
      frame = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8)
          .replaceAll("#appkey#", KAKAO_APP_KEY)
          .replaceAll("#keyword#", keyword);
    } catch (NullPointerException | IOException e2) {
      frame = "";
    }
    return frame;
  }
}
