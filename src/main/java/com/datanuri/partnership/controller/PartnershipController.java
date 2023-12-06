package com.datanuri.partnership.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * create on 2023-12-05. create by IntelliJ IDEA.
 *
 * <p> 클래스 설명 </p>
 *
 * @author ytkim
 * @version 1.0
 * @since 1.0
 */
@Controller
@RequestMapping("/")
public class PartnershipController {

  @GetMapping("/")
  public String index() {
    return "index";
  }


}
