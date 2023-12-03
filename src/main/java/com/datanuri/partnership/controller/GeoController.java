package com.datanuri.partnership.controller;

import com.datanuri.partnership.service.GeoLocationService;
//import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GeoController {
  private final GeoLocationService geoLocationService;

  @GetMapping("/geoLocation")
//  @ApiOperation(value = "지도 맵 조회")
  public void getMapReportByKeyword(
      @RequestParam("keyword") String keyword,
      HttpServletResponse response) {

    String script = geoLocationService.getResourceByKeyword(keyword);

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

  @GetMapping("/geoLocation/category")
//  @ApiOperation(value = "지도 맵 조회")
  public void getMapCategoryByKeyword(
      @RequestParam("keyword") String keyword,
      HttpServletResponse response) {

    String script = geoLocationService.getResourceCategoryByKeyword(keyword);

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

  @GetMapping("/geoLocation/{targetAddr}/{targetName}")
//  @ApiOperation(value = "지도 맵 조회")
  public void getRegionLocation(
      @PathVariable(name = "targetAddr") String targetAddr,
      @PathVariable(name = "targetName") String targetName,
      HttpServletResponse response) {

    String script = geoLocationService.getResourceByAddress(targetAddr, targetName);

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
