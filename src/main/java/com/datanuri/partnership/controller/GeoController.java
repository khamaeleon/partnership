package com.datanuri.partnership.controller;

import com.datanuri.partnership.service.GeoLocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GeoController {
  private final GeoLocationService geoLocationService;

  @GetMapping(value = {"/geoLocation","/geoLocation/{keyword}"})
//  @ApiOperation(value = "지도 맵 조회")
  public void getRegionLocation(
      @PathVariable(name = "keyword", required = false) String keyword,
      HttpServletResponse response) {

    if(keyword == null ){
      keyword = "default";
    }
    String script = geoLocationService.getResourceByAddress(keyword);

    try {
      response.setContentType("text/html; charset=utf-8");
      response.setCharacterEncoding("utf-8");
      PrintWriter out = response.getWriter();
      out.println(script);
      out.flush();
    } catch (IOException e) {
      log.error(String.format("%s", e.getMessage()));
    }
  }
}
