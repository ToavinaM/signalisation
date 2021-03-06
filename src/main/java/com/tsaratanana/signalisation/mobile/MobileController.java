
package com.tsaratanana.signalisation.mobile;
import com.fasterxml.jackson.databind.ObjectMapper;
import static com.tsaratanana.signalisation.frontOffice.FrontController.verifierTokenAdmin;
import com.tsaratanana.signalisation.model.Region;
import com.tsaratanana.signalisation.model.Signal;

import com.tsaratanana.signalisation.model.TypeSignal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
//import java.sql.Date;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.tsaratanana.signalisation.model.Utilisateur;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.sql.Blob;
import java.util.Arrays;
import java.util.List;
import javax.sql.rowset.serial.SerialBlob;
import org.apache.tomcat.util.codec.binary.Base64;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author zola
 */
@CrossOrigin
@RestController
//@RequestMapping("backC")
public class MobileController {
    @Autowired
    ServiceMobile serv;
   
   
//      @PostMapping("/newsignal")
//   ResponseEntity<Map<String,Object>> newSignal(HttpServletRequest request, String authHeader,@RequestParam MultipartFile multipartImage,@RequestParam String newSignal) throws Exception {
//     Map<String,Object> map = new HashMap<>();
//     Signalement nouv = new Signalement();
    
//       try{
      
//         ObjectMapper objm=new ObjectMapper();
        
//         nouv = objm.readValue(newSignal, Signalement.class);
        
//         nouv.setIdpersonne(1);
//         nouv.setDateheure(new Date());
//         System.out.println("dsssssssssssssssssssssssssssssssssssssssssssss");
//         nouv.setImage(multipartImage.getBytes());
//         System.out.println(multipartImage.getBytes());
//         Signalement liste= repository1.save(nouv);
//         map.put("message", "liste SIGNL");
//                     map.put("status", "200");
//                     map.put("data",liste);
//                     return new ResponseEntity<>(map,HttpStatus.OK);
//     }
//     catch(Exception exce){
//       map.put("status", "400");
//           map.put("message", exce);
//           return new ResponseEntity<>(map,HttpStatus.OK);
//     }
//   }
    @GetMapping("/regions")
    public ResponseEntity<Map<String,Object>> regions () {
        Map<String,Object> map = new HashMap<>();
        try {
            List<Region> listSignals=serv.findRegion();
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
        
   @GetMapping("/typeSignals")
	public ResponseEntity<Map<String,Object>> typeSignal () {
		Map<String,Object> map = new HashMap<>();
		try {
                    List<TypeSignal> listSignals=serv.findTypeSignal();
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
    @PostMapping("/user")
    public ResponseEntity<Map<String,String>> huhu(@RequestBody Map<Object,Object> adminMap)throws Exception{
        String log = (String) adminMap.get("login");
        String passwords = (String) adminMap.get("mdp");
        System.out.println("log : "+log+" pass:" + passwords);
        Map<String,String> map = new HashMap<>();
        try {
            
                Utilisateur region = serv.login(log, passwords);
                map=generateJWTTokenUtilisateur(region);
                map.put("profil","utilisateur");
                map.put("status", "200");
                return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(Exception e) {
                map.put("message", e.getMessage());
                map.put("status", "401");
                return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }
    

    
  
    
    
  
  
    @GetMapping("/liens")
    public Map<String,Object> liens (String lat,String lon) {
        JSONObject v=serv.liens(lat,lon);
	return v.toMap();
    }
    
    
    
    

     @PostMapping("user/signal1/{idUtilisateur}")
    public ResponseEntity<Map<String,String>> ajoutsignal(@PathVariable("idUtilisateur") String idUtilisateur,@RequestBody Map<Object, Object> mety) throws Exception{
            Map<String,String> map = new HashMap<>();
            int idtypeSignal=Integer.parseInt(mety.get("idtypeSignal").toString());
            String description=mety.get("description").toString();
            String photo=mety.get("photo").toString();
           byte[] byteData = photo.getBytes("UTF-8");//Better to specify encoding
            Blob blob = new SerialBlob(byteData);
           Double lat=Double.parseDouble(mety.get("lat").toString());
           System.out.println(blob);
               System.out.println(blob.toString());
            Double lng=Double.parseDouble(mety.get("lng").toString());
            int idRegion=Integer.parseInt(mety.get("region").toString());
            String province=mety.get("province").toString();
            String subUrb=mety.get("subUrb").toString();
            ByteBuffer wrapped = ByteBuffer.wrap(photo.getBytes());
            Integer i=wrapped.getInt();
            String bites=Arrays.toString(photo.getBytes());
            
             System.out.println("*******TAFIDITRA TAFIDITRA*******----"+lat+"----"+lng+"----"+idUtilisateur+"----"+idtypeSignal+"----"+photo+"----"+idRegion+"----"+province);
            
             try{
                //alana le string
                Integer id=serv.addsignal(Integer.parseInt(idUtilisateur), idtypeSignal, description,photo, lat, lng, idRegion, subUrb, province);
                
                map.put("message", "Add successfuly");
                map.put("idSignal",id.toString());
                return new ResponseEntity<>(map, HttpStatus.OK);
    }
            
    
    catch (Exception e)
    {
            map.put("status", "430");
                    map.put("message","ERROR O : "+ e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }	
    }
    
     @PostMapping("user/vaovao/{idUtilisateur}")
    public ResponseEntity<Map<String,String>> ajoutsignal(HttpServletRequest request,@PathVariable("idUtilisateur") String idUtilisateur,@RequestBody Map<Object, Object> mety,@RequestHeader(name = "Authorization") String authHeader) throws Exception{
            Map<String,String> map = new HashMap<>();
            int idtypeSignal=Integer.parseInt(mety.get("idtypeSignal").toString());
            String description=mety.get("description").toString();
            String photo=mety.get("photo").toString();
           Double lat=Double.parseDouble(mety.get("lat").toString());
         
            Double lng=Double.parseDouble(mety.get("lng").toString());
            int idRegion=Integer.parseInt(mety.get("region").toString());
            String province=mety.get("province").toString();
            String subUrb=mety.get("subUrb").toString();
            
            
             System.out.println("*******TAFIDITRA TAFIDITRA*******----"+lat+"----"+lng+"----"+idUtilisateur+"----"+idtypeSignal+"----"+photo+"----"+idRegion+"----"+province);
            
             try{
                verifierTokenUtlisateur(authHeader, request);
                System.out.println("MANDE NY ITOOOOOOidUtilisateur  "+request.getAttribute("idAdmin"));
                Integer id=serv.addsignal(Integer.parseInt(request.getAttribute("idAdmin").toString()), idtypeSignal, description,photo, lat, lng, idRegion, subUrb, province);
                map.put("message", "Add successfuly");
                map.put("idSignal",id.toString());
                return new ResponseEntity<>(map, HttpStatus.OK);
    }
            
    
    catch (Exception e)
    {
            map.put("status", "430");
                    map.put("message","ERROR O : "+ e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }	
    }
     
     @GetMapping("user/signals")
	public ResponseEntity<Map<String,Object>> signals (HttpServletRequest request,@RequestHeader(name = "Authorization") String authHeader) {
		Map<String,Object> map = new HashMap<>();
		try {
                    verifierTokenUtlisateur(authHeader, request);
                    System.out.println("MANDE NY ITOOOOOOO"+request.getAttribute("idAdmin"));
                    List<Signal> listSignals=serv.signalUsers(request.getAttribute("idAdmin").toString());
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

    
    
    @PostMapping("inscription/user")
    public ResponseEntity<Map<String,String>> addSignl(HttpServletRequest request,@RequestBody Map<String, Object> signalMap) throws Exception{
            Map<String,String> map = new HashMap<>();
            String login=signalMap.get("login").toString();
            String nom=signalMap.get("nom").toString(); 
            String mdp=signalMap.get("mdp").toString();
           
           
            try{
             
                
                if (mdp.length()<8)
                {
                    map.put("message", "Votre mot de passe doit contenir au minimum 8 caract??res"+mdp.length());
                    throw new  Exception("email non valide");
                }
                else
                {
                     serv.inscription(login,nom,mdp);
                     map.put("message", "Add successfuly");
                     return new ResponseEntity<>(map, HttpStatus.OK);
                }
               
    }
    catch (Exception e)
    {
            map.put("status", "430");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }	
    }
    
   
    
    
    private Map<String,String> generateJWTTokenUtilisateur(Utilisateur user){
        System.out.println("********************************************** "+ user.getIdUtilisateur() );
       
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256,"token")
            
            .claim("idAdmin",user.getIdUtilisateur())
            .claim("login",user.getLogin())
            .claim("mdp",user.getMdp())
            .compact();
        Map<String,String> map = new HashMap<>();
        map.put("message", "Loggin successfully");
        map.put("status", "200");
        map.put("datas", token);
        return map;
    }
    
    
    public static void verifierTokenUtlisateur(String authHeader,HttpServletRequest request)throws Exception{
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

