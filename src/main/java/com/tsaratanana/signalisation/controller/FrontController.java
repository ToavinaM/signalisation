
package com.tsaratanana.signalisation.controller;
import com.tsaratanana.signalisation.service.backOffice.ServiceBack;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.tsaratanana.signalisation.model.Admin;
import com.tsaratanana.signalisation.model.Region;
import com.tsaratanana.signalisation.model.Signal;
import com.tsaratanana.signalisation.service.frontOffice.ServiceRegion;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zola
 */
@CrossOrigin
@RestController
//@RequestMapping("backC")
public class FrontController {
    @Autowired
    ServiceRegion serv;
    
    @GetMapping("/signalRegions")
	public ResponseEntity<Map<String,Object>> signals (HttpServletRequest request,@RequestHeader(name = "Authorization") String authHeader) {
		Map<String,Object> map = new HashMap<>();
		try {
                    verifierTokenAdmin(authHeader, request);
                    System.out.println("MANDE NY ITOOOOOOO"+request.getAttribute("idAdmin"));
                    List<Signal> listSignals=serv.signalRegions();
                    map.put("message", "liste SIGNL");
                    map.put("status", "200");
                    map.put("data",listSignals);
                    return new ResponseEntity<>(map,HttpStatus.OK);
		} catch (Exception e) {
                    map.put("status", "400");
                    map.put("message", e.getMessage());
                    return new ResponseEntity<>(map,HttpStatus.OK);
		}
	}
    
    
    
    @PostMapping("/region")
    public ResponseEntity<Map<String,String>> huhu(@RequestBody Map<Object,Object> adminMap)throws Exception{
        String log = (String) adminMap.get("login");
        String passwords = (String) adminMap.get("mdp");
        System.out.println("log : "+log+" pass:" + passwords);
        Map<String,String> map = new HashMap<>();
        try {
                Region region = serv.login(log, passwords);
                map=generateJWTToken(region);
                return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(Exception e) {
                map.put("message", e.getMessage());
                map.put("status", "401");
                return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }
    
    
    
    private Map<String,String> generateJWTToken(Region user){
        long timestamp = System.currentTimeMillis();
        Date date = new Date(timestamp+30000000);
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256,"token")
            .setIssuedAt(new Date(timestamp))
         ///   .setExpiration(date)
            .claim("idAdmin",user.getIdRegion())
            .claim("login",user.getLogin())
            .claim("mdp",user.getMdp())
            .compact();
        Map<String,String> map = new HashMap<>();
        map.put("message", "Loggin successfully");
        map.put("status", "200");
        map.put("datas", token);
        return map;
    }
    
    
    public static void verifierTokenAdmin(String authHeader,HttpServletRequest request)throws Exception{
        String[] authHeaderArr = authHeader.split("Bearer");
        System.err.println("--"+authHeaderArr[1]);
        if(authHeaderArr.length>1 && authHeaderArr[1]!=null) {
            String token = authHeaderArr[1];
            try {
                System.out.println("com.tsaratanana.signalisation.controller.BackController.verifierTokenAdmin()");
                    Claims claims = Jwts.parser().setSigningKey("token")
                                    .parseClaimsJws(token).getBody();
                    request.setAttribute("idAdmin", Integer.parseInt(claims.get("idAdmin").toString()));
            } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
                    // TODO: handle exception
                    throw new Exception("Token invalid/expired 1");
            }
        }else {
                throw new Exception("Token invalid/expired 2");
        }
    }
    
    
    
    
    
    
 
  
    
}

