package br.eti.kge.SecurityAPIKEY.security;

import jakarta.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

/**
 * 02
 * Responsável por avaliar a Chave de API e a construir o objeto 
 * Authentication.
 * 
 * Aqui, verificamos se a solicitação contém o cabeçalho da chave de API 
 * com um segredo. Se o cabeçalho for nulo ou diferente de "clave_exclusiva_api", 
 * lançamos uma BadCredentialsException. Se a solicitação tiver o cabeçalho, 
 * ela executa a autenticação, adiciona o segredo ao contexto de segurança e, 
 * em seguida, passa a chamada para o filtro de segurança subsequente. 
 * 
 * Nosso método getAuthentication é bastante simples: comparamos o cabeçalho 
 * da chave de API e o segredo com um valor estático. 
 * 
 * Para construir o objeto Authentication, precisamos usar a mesma 
 * abordagem que o Spring Security normalmente usa para construir o 
 * objeto usando autenticação padrão. Portanto, vamos estender a 
 * classe AbstractAuthenticationToken e acionar a autenticação manualmente.
 * (Veja ApiKeyAuthentication.class)
 * 
 * @author kge
 */

@Component
public class AuthenticationService {

    // TODO: Implementar o nome do token e a chave no application.properties.
    
    @Value("${auth.token-header-name}")
    private String AUTH_TOKEN_HEADER_NAME;
    
    
    @Value("${auth.token}")
    private String AUTH_TOKEN;
    
    
//    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";
//    private static final String AUTH_TOKEN = "chave_exclusiva_api";

    private static final Logger LOGGER = Logger.getLogger(AuthenticationService.class.getName());

    public Authentication getAuthentication(HttpServletRequest request) {

        LOGGER.log(Level.INFO, "02 AuthenticationService acionado!");
        LOGGER.log(Level.INFO, String.format("TokenHeader: %s | Token: %s", AUTH_TOKEN_HEADER_NAME, AUTH_TOKEN));

        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if (apiKey == null) {

            LOGGER.log(Level.INFO, "API Key not Found!");
            throw new AuthenticationCredentialsNotFoundException("API Key not found.");

        } else if (!apiKey.equals(AUTH_TOKEN)) {
            LOGGER.log(Level.INFO, "Invalid API Key");
            throw new BadCredentialsException("Invalid API Key");

        } else {
            LOGGER.log(Level.INFO, "Area privada Authorized!");
            return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);

        }

    }
}
