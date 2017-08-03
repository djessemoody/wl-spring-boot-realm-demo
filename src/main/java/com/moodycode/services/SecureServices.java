package com.moodycode.services;

import com.moodycode.MockObject.Returner;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/***
 *  MOODYCODE.COM DEMO
 *  SERVICE REQUIRING ROLE_USER PREAUTHORIZED WEBLOGIC GROUP
 */

@RestController
@RequestMapping(value = "/service",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@PreAuthorize("hasRole('ROLE_USER')")
public class SecureServices {



  @RequestMapping(value = "/getSecureResponse", method = RequestMethod.GET)
  public @ResponseBody
  Returner getKeyValues() {
    Map<String, Object> response = new HashMap<>();
    response.put("ResponseVal","Here is a resonse value");


    Returner returner = new Returner();
    returner.setResponse(response);
    returner.setVersion(120);
    returner.setUpdateTime(new Date());
    return returner;
  }

}
