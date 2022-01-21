
package com.tsaratanana.signalisation.controller;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.tsaratanana.signalisation.model.Utilisateur;
import com.tsaratanana.signalisation.service.mobile.ServiceUser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author zola
 */
@CrossOrigin
@RestController
//@RequestMapping("backC")
public class MobileController {
    @Autowired
    ServiceUser serv;
    
   
    @PostMapping("/user")
    public ResponseEntity<Map<String,String>> huhu(@RequestBody Map<Object,Object> adminMap)throws Exception{
        String log = (String) adminMap.get("login");
        String passwords = (String) adminMap.get("mdp");
        System.out.println("log : "+log+" pass:" + passwords);
        Map<String,String> map = new HashMap<>();
        try {
                Utilisateur region = serv.login(log, passwords);
                map=generateJWTToken(region);
                return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(Exception e) {
                map.put("message", e.getMessage());
                map.put("status", "401");
                return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }
    
     @GetMapping("/lien")
    public String lien (String lat,String lon,String key) {
        String r="";
        try{
            JSONObject json = serv.readJsonFromUrl("https://nominatim.openstreetmap.org/reverse?format=json&lat="+lat+"&lon="+lon+"&zoom=27");
            r=json.getJSONObject("address").getString(key);
            System.out.println(json.toString());
      }
      catch(IOException e){
      }
	return r;	
    }
    @GetMapping("/liens")
    public Map<String,Object> liens (String lat,String lon) {
        JSONObject v=serv.liens(lat,lon);
	return v.toMap();
    }
    
   
    @PostMapping("user/signal/{idUtilisateur}")
    public ResponseEntity<Map<String,String>> addSignl(HttpServletRequest request,@PathVariable("idUtilisateur") String idUtilisateur,@RequestBody Map<String, Object> signalMap,@RequestHeader(name = "Authorization") String authHeader) throws Exception{
            Map<String,String> map = new HashMap<>();
            int idtypeSignal=Integer.parseInt(signalMap.get("idtypeSignal").toString());
            String description= signalMap.get("description").toString();
            String photo=signalMap.get("photo").toString();
            Double lat=Double.parseDouble(signalMap.get("lat").toString());
            Double lng=Double.parseDouble(signalMap.get("lng").toString());
            int idRegion=Integer.parseInt(signalMap.get("idRegion").toString());
            String subUrb=signalMap.get("subUrb").toString();
            String province=signalMap.get("province").toString();
            try{
                verifierTokenAdmin(authHeader, request);
                serv.addsignal(Integer.parseInt(idUtilisateur), idtypeSignal, description, photo, lat, lng, idRegion, subUrb, province);
                map.put("message", "Add successfuly");
                return new ResponseEntity<>(map, HttpStatus.OK);
    }
    catch (Exception e)
    {
            map.put("status", "430");
                    map.put("message","ERROR O : "+ e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }	
    }
    
    
    private Map<String,String> generateJWTToken(Utilisateur user){
        long timestamp = System.currentTimeMillis();
        Date date = new Date(timestamp+30000000);
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256,"token")
            .setIssuedAt(new Date(timestamp))
         ///.setExpiration(date)
            .claim("idUtilisateur",user.getIdUtilisateur())
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
                    request.setAttribute("idUtilisateur", Integer.parseInt(claims.get("idUtilisateur").toString()));
            } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
                    // TODO: handle exception
                    throw new Exception("Token invalid/expired 1");
            }
        }else {
                throw new Exception("Token invalid/expired 2");
        }
    }
    
    
    
    
    
    
 
  
    
}

