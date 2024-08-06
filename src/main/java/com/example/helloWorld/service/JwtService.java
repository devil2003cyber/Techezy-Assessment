package com.example.helloWorld.service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	private static final String SECRECT_KEY = "43e2f82188928c60d9aae27022c2bf0470f8d3851bf9a34e9d9d39e0adea0bce";

	 
	 @org.springframework.beans.factory.annotation.Value("${application.security.jwt.expiration}")
	  private long jwtExpiration;
	  
	  @org.springframework.beans.factory.annotation.Value("${application.security.jwt.refresh-token.expiration}")
	  private long refreshExpiration;
	  
	public  String extarctUsername(String token)
   {
       return extractClaim(token, Claims::getSubject);//
   }
	
	public <T> T extractClaim(String token,Function<Claims,T> claimsResolver)//extract specified claims here such as subject ;
	{
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	public List<String> extractRoles(String token) {
	    Claims claims = extractAllClaims(token);
	    Object rolesObject = claims.get("roles");
	    if (rolesObject instanceof List<?>) {
	        return ((List<?>) rolesObject).stream()
	            .filter(item -> item instanceof String)
	            .map(item -> (String) item)
	            .collect(Collectors.toList());
	    }
	    return new ArrayList<>();
	}

	public String generateToken(UserDetails userDetails) {
//		return generateToken(new HashMap<>(), userDetails);
		
		Map<String, Object> claims = new HashMap<>();
	    List<String> roles = userDetails.getAuthorities().stream()
	        .map(GrantedAuthority::getAuthority)
	        .collect(Collectors.toList());
	    claims.put("roles", roles);
	    System.out.println("Generating JWT with roles: " + roles);
	        return generateToken(claims, userDetails);
	        
	}
	public String generateToken(
			Map<String,Object> extraClaims,
			UserDetails userDetails
			) {
		if (extraClaims == null) {
	        extraClaims = new HashMap<>();
	        extraClaims.put("roles", userDetails.getAuthorities().stream()
		            .map(GrantedAuthority::getAuthority)
		            .collect(Collectors.toList()));
	    }
	    
		return buidToken(extraClaims,userDetails,jwtExpiration);
				
	}
	public String generateRefreshToken( UserDetails userDetails) {
		// TODO Auto-generated method stub
		Map<String, Object> claims = new HashMap<>();
	    List<String> roles = userDetails.getAuthorities().stream()
	        .map(GrantedAuthority::getAuthority)
	        .collect(Collectors.toList());
	    claims.put("roles", roles);
	    System.out.println("Generating Refresh Token with roles: " + roles);
		return buidToken(new HashMap<>(), userDetails , refreshExpiration);
	}

	public String buidToken(
			Map<String,Object> extraClaims,
			UserDetails userDetails , long expiration
			) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()  + expiration))
				.signWith(getSignInKey(),SignatureAlgorithm.HS256)
				.compact();//
				
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
	    	final String username = extarctUsername(token);
	    	return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
    
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	private Date extractExpiration(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token, Claims::getExpiration);
	}

	private  Claims extractAllClaims(String token) //this function contain all claims / payload 
	{
		return Jwts
				.parserBuilder()// This initializes the process of building a JWT parser, which will be used to parse and validate the JWT.
				.setSigningKey(getSignInKey()) // Sets the key used to verify the JWT’s signature.
				.build()//The build() method finalizes the setup of the JWT parser with the specified signing key and other configurations.
				.parseClaimsJws(token)//Parses the JWT and validates its signature.
				.getBody();//Retrieves the claims(payload )conatins in jwt 
	}
	
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRECT_KEY);//decode the seceret key
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
