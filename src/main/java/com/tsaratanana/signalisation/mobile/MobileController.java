
package com.tsaratanana.signalisation.mobile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsaratanana.signalisation.model.Region;
import com.tsaratanana.signalisation.model.Signalement;
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
import java.util.List;
import org.apache.commons.validator.EmailValidator;
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
    
    @Autowired
    private SignalRepository repository1;
   
     @PostMapping("/newsignal")
  ResponseEntity<Map<String,Object>> newSignal(HttpServletRequest request, String authHeader,@RequestParam MultipartFile multipartImage,@RequestParam String newSignal) throws Exception {
    Map<String,Object> map = new HashMap<>();
    Signalement nouv = new Signalement();
    
      try{
      
        ObjectMapper objm=new ObjectMapper();
        
        nouv = objm.readValue(newSignal, Signalement.class);
        
        nouv.setIdpersonne(1);
        nouv.setDateheure(new Date());
        System.out.println("dsssssssssssssssssssssssssssssssssssssssssssss");
        nouv.setImage(multipartImage.getBytes());
        System.out.println(multipartImage.getBytes());
        Signalement liste= repository1.save(nouv);
        map.put("message", "liste SIGNL");
                    map.put("status", "200");
                    map.put("data",liste);
                    return new ResponseEntity<>(map,HttpStatus.OK);
    }
    catch(Exception exce){
      map.put("status", "400");
          map.put("message", exce);
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
    
    
    
    public String imageEncoderDecoder(MultipartFile multipartFile) throws IOException
    {
        String path = "image";
        File fileS = new File(path);
        String fileName =multipartFile.getOriginalFilename();
        String absolutePath = "D:\\TRINOME-EXAMEN-S6\\signalisation\\WEB\\BackOffice\\mon-app\\public\\"+fileName;
        multipartFile.transferTo(new File(absolutePath));
      //// File file = new File(absolutePath); 
      // FileInputStream fileInputStreamReader = new FileInputStream(file);
      // byte[] bytes = new byte[(int)file.length()]; 
      // fileInputStreamReader.read(bytes);
      // String r="data:image/jpeg;base64,"+new String(Base64.encodeBase64(bytes), "UTF-8");
       return fileName;
    }
    @PostMapping("user/signal/{idUtilisateur}")
    public ResponseEntity<Map<String,String>> addSignl(HttpServletRequest request,@PathVariable("idUtilisateur") String idUtilisateur,@RequestBody Map<String, Object> signalMap,@RequestHeader(name = "Authorization") String authHeader) throws Exception{
            Map<String,String> map = new HashMap<>();
            int idtypeSignal=Integer.parseInt(signalMap.get("idtypeSignal").toString());
            String description= signalMap.get("description").toString();
            String photo=signalMap.get("photo").toString();
            Double lat=Double.parseDouble(signalMap.get("lat").toString());
            Double lng=Double.parseDouble(signalMap.get("lng").toString());
            
            String subUrb=signalMap.get("subUrb").toString();
            String province=lien(signalMap.get("lat").toString(),signalMap.get("lng").toString(),"region");
            String  region=lien(signalMap.get("lat").toString(),signalMap.get("lng").toString(),"state");
            System.out.println(region);
            Region uu=serv.findBynameRegion(region).get(0);
            int idRegion=uu.getIdRegion();
             System.out.println("**************"+province);
            
            try{
                verifierTokenAdmin(authHeader, request);
                Integer id=serv.addsignal(Integer.parseInt(idUtilisateur), idtypeSignal, description, photo, lat, lng, idRegion, subUrb, province);
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
    
    
    
    
    
    
    
    
    
     @PostMapping("user/signals/{idUtilisateur}")
    public ResponseEntity<Map<String,String>> addSignls(@PathVariable("idUtilisateur") String idUtilisateur,int idtypeSignal,String description,MultipartFile multipartFile,Double lat,Double lng,String subUrb ) throws Exception{
            Map<String,String> map = new HashMap<>();
            String province=lien(lat.toString(),lng.toString(),"region");
            String  region=lien(lat.toString(),lng.toString(),"state");
            String photo=imageEncoderDecoder(multipartFile);
            System.out.println(region);
            Region uu=serv.findBynameRegion(region).get(0);
            int idRegion=uu.getIdRegion();
             System.out.println("**************"+province);
            
            try{
                //verifierTokenAdmin(authHeader, request);
                Integer id=serv.addsignal(Integer.parseInt(idUtilisateur), idtypeSignal, description, photo, lat, lng, idRegion, subUrb, province);
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
    
    
    
    
    @PostMapping("inscription/user")
    public ResponseEntity<Map<String,String>> addSignl(@RequestBody Map<Object, Object> signalMap) throws Exception{
            Map<String,String> map = new HashMap<>();
            String login=signalMap.get("login").toString();
            String nom=signalMap.get("nom").toString(); 
            String mdp=signalMap.get("mdp").toString();
            boolean valid = EmailValidator.getInstance().isValid(login);
           
            try{
                
                if (!EmailValidator.getInstance().isValid(login)){
                    map.put("message", "email non valide");
                    throw new  Exception("email non valide");
                }
                
                else if (mdp.length()<8)
                {
                    map.put("message", "Votre mot de passe doit contenir au minimum 8 caractères"+mdp.length());
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

