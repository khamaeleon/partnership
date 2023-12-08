package com.datanuri.partnership.controller;

import com.datanuri.partnership.service.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create on 2023-12-07. create by IntelliJ IDEA.
 *
 * <p> 클래스 설명 </p>
 *
 * @author ytkim
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/batch")
public class BatchController {

  private final BatchService batchservice;

  @GetMapping("/loadApi")
  public ResponseEntity loadApi(){
    try{
      batchservice.loadApi();
      return new ResponseEntity<>(HttpStatus.OK);
    }catch (Exception e){
      return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
    }
  }
}
