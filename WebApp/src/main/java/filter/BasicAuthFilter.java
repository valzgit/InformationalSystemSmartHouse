/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import entities.Korisnik;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Stefan
 */
@Provider
public class BasicAuthFilter implements ContainerRequestFilter{

    @PersistenceContext(unitName = "my_persistence_unit")
    EntityManager em;
    
    //izmeniti telo ove metode
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        List<String> authHeaderValues = requestContext.getHeaders().get("Authorization");
        
        if(authHeaderValues != null && authHeaderValues.size() > 0){
            String authHeaderValue = authHeaderValues.get(0);
            String decodedAuthHeaderValue = new String(Base64.getDecoder().decode(authHeaderValue.replaceFirst("Basic ", "")),StandardCharsets.UTF_8);
            StringTokenizer stringTokenizer = new StringTokenizer(decodedAuthHeaderValue, ":");
            String username = stringTokenizer.nextToken();
            String password = stringTokenizer.nextToken();
            
            List<Korisnik> korisnikList = em.createNamedQuery("Korisnik.findByUsername",Korisnik.class).setParameter("username", username).getResultList();
            
            if(korisnikList==null || korisnikList.size()!=1){
                Response response = Response.status(Response.Status.UNAUTHORIZED).entity("User doesn't exist!").build();
                requestContext.abortWith(response);
                return;
            }
            
            Korisnik korisnik = korisnikList.get(0);
            
            if(!korisnik.getPassword().equals(password)){
                Response response = Response.status(Response.Status.UNAUTHORIZED).entity("Incorrect password!").build();
                requestContext.abortWith(response);
                return;
            }
            
            return;
        }
        
        Response response = Response.status(Response.Status.UNAUTHORIZED).entity("Send credentials.").build();
        requestContext.abortWith(response);
    }
    
}