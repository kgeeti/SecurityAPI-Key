package br.eti.kge.SecurityAPIKEY.security;

import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * 03
 * 
 * Para implementar a autenticação com sucesso em nossa aplicação, precisamos 
 * converter a chave de API recebida em um objeto de autenticação, 
 * como um AbstractAuthenticationToken. 
 * 
 * A classe AbstractAuthenticationToken implementa a interface de autenticação, 
 * representando o segredo/principal de uma solicitação autenticada. 
 * 
 * Essa é função da classe ApiKeyAuthentication:
 * 
 * @author kge
 */

public class ApiKeyAuthentication extends AbstractAuthenticationToken {

    private final String apiKey;

    private boolean authenticated;

    
    public ApiKeyAuthentication(String apiKey, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.apiKey = apiKey;
        setAuthenticated(true);
    }
    
    @Override
    public Object getCredentials() {
        return null; // Geralmente nulo para autenticação baseada em token/chave
    }

    @Override
    public Object getDetails() {
        return null; // Pode conter informações adicionais da requisição
    }

    @Override
    public Object getPrincipal() {
        return apiKey;  // Devolve a chave de API.
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;   // Pode desenrolar a lógica para autenticar ou não.
        
    }

    @Override
    public String getName() {
        return apiKey;
    }
}
