package com.datanuri.partnership.controller;

import com.datanuri.partnership.service.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * create on 2023-12-07. create by IntelliJ IDEA.
 *
 * <p> 클래스 설명 </p>
 *
 * @author ytkim
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequiredArgsConstructor
public class BatchController {

  private final BatchService batchservice;

  @GetMapping("/loadApi")
  public void loadApi(){
    batchservice.loadApi();
  }
}
