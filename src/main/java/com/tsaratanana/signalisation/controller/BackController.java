
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
import com.tsaratanana.signalisation.model.Signal;
import com.tsaratanana.signalisation.model.StatStatus;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author zola
 */
@CrossOrigin 
@RestController
//@RequestMapping("backC")
public class BackController {
    @Autowired
    ServiceBack serv;
   
    @GetMapping("/base64")
    public String imageEncoderDecoder(MultipartFile multipartFile) throws IOException
    {
         String path = "image";
        File fileS = new File(path);
         String fileName =multipartFile.getOriginalFilename();
        String absolutePath = fileS.getAbsolutePath()+"\\"+fileName;
       Path pathdd = Paths.get(absolutePath);
        System.out.println(absolutePath);
        System.out.println(pathdd);
      
       multipartFile.transferTo(new File(absolutePath));       
        File file = new File(absolutePath); 
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
       
        fileInputStreamReader.read(bytes);
        return "data:image/jpeg;base64,"+new String(Base64.encodeBase64(bytes), "UTF-8");
    }
    
    @PostMapping("/admin")
    public ResponseEntity<Map<String,String>> huhu(@RequestBody Map<Object,Object> adminMap)throws Exception{
        String log = (String) adminMap.get("login");
        String passwords = (String) adminMap.get("mdp");
        System.out.println("log : "+log+" pass:" + passwords);
        Map<String,String> map = new HashMap<>();
        try {
                Admin admin = serv.login(log, passwords);
                map=generateJWTToken(admin);
                return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(Exception e) {
                map.put("message", e.getMessage());
                map.put("status", "401");
                return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }
    
    @GetMapping("status/statistiques")
    public ResponseEntity<Map<String,Object>> statStatus (HttpServletRequest request,@RequestHeader(name = "Authorization") String authHeader) {
            Map<String,Object> map = new HashMap<>();
            try {
                verifierTokenAdmin(authHeader, request);
                System.out.println("MANDE NY ITOOOOOOO"+request.getAttribute("idAdmin"));
                List<StatStatus>  listSignals = serv.getStatStatus("status");
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
    @GetMapping("region/statistiques")
    public ResponseEntity<Map<String,Object>> statRegion (HttpServletRequest request,@RequestHeader(name = "Authorization") String authHeader) {
            Map<String,Object> map = new HashMap<>();
            try {
                verifierTokenAdmin(authHeader, request);
                System.out.println("MANDE NY ITOOOOOOO"+request.getAttribute("idAdmin"));
                List<StatStatus>  listSignals = serv.getStatStatus("region");
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
    
    @GetMapping("typeSignal/statistiques")
    public ResponseEntity<Map<String,Object>> statTypeSignal (HttpServletRequest request,@RequestHeader(name = "Authorization") String authHeader) {
            Map<String,Object> map = new HashMap<>();
            try {
                verifierTokenAdmin(authHeader, request);
                System.out.println("MANDE NY ITOOOOOOO"+request.getAttribute("idAdmin"));
                List<StatStatus>  listSignals = serv.getStatStatus("type");
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
    
    
    
    
    @GetMapping("/signals")
    public ResponseEntity<Map<String,Object>> signals (HttpServletRequest request,@RequestHeader(name = "Authorization") String authHeader) {
            Map<String,Object> map = new HashMap<>();
            try {
                verifierTokenAdmin(authHeader, request);
                System.out.println("MANDE NY ITOOOOOOO"+request.getAttribute("idAdmin"));
                List<Signal> listSignals=serv.signals();
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
        
    @PutMapping("/signal/{idSignal}")
    public ResponseEntity<Map<String,String>> updateSignal(HttpServletRequest request,@PathVariable("idSignal") String idSignal,@RequestBody Map<String, Object> signalMap,@RequestHeader(name = "Authorization") String authHeader) throws Exception{
            Map<String,String> map = new HashMap<>();
            String lastStatus = signalMap.get("lastStatus").toString();
            try{
                    verifierTokenAdmin(authHeader, request);
                    serv.updateSignal(Integer.parseInt(idSignal),lastStatus);
                    serv.historique(Integer.parseInt(idSignal),lastStatus);
        map.put("status", "200");
                    map.put("message", "Update successfuly");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    catch (Exception e)
    {
            map.put("status", "430");
                    map.put("message", e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }	
    }
    
    
   
    
    
    
    private Map<String,String> generateJWTToken(Admin user){
        long timestamp = System.currentTimeMillis();
        Date date = new Date(timestamp+30000000);
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256,"token")
            .setIssuedAt(new Date(timestamp))
         ///   .setExpiration(date)
            .claim("idAdmin",user.getId())
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
    
    @PostMapping("/affecter")
    public ResponseEntity<Map<String,Object>> affecter(HttpServletRequest request,@RequestBody Map<String, Object> signalMap,@RequestHeader(name = "Authorization") String authHeader) throws Exception {
            Map<String,Object> map = new HashMap<>();
            int idSignal = (Integer)signalMap.get("idSignal");
            String status =signalMap.get("status").toString();
            try {
                verifierTokenAdmin(authHeader, request);
                serv.historique(idSignal,status);
                map.put("status", "200");
                map.put("message", "Signalement ");
                return new ResponseEntity<>(map, HttpStatus.OK);
            } catch (Exception e) {
                map.put("status", "430");
                map.put("message", e.getMessage());
                return new ResponseEntity<>(map, HttpStatus.CONFLICT);
            }
    }
    
    
    
    
    
    
    
    
    
 
  
    
}

