package br.eti.kge.SecurityAPIKEY.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

/**
 * 01
 * 
 * A ideia é obter o cabeçalho da chave da API HTTP da solicitação e, 
 * em seguida, verificar a chave com nossa configuração. 
 * 
 * Nesse caso, precisamos adicionar um filtro personalizado na classe de 
 * configuração do Spring Security. Começaremos implementando o GenericFilterBean. 
 * O GenericFilterBean é uma implementação simples de javax.servlet.Filter 
 * compatível com Spring. Vamos criar a classe AuthenticationFilter.
 * 
 * Precisamos apenas implementar um método doFilter(). Avaliamos o cabeçalho 
 * da Chave de API neste método e definimos o objeto Authentication resultante 
 * na instância atual de SecurityContext. Em seguida, a solicitação é passada 
 * para os filtros restantes para processamento, roteada para DispatcherServlet 
 * e, finalmente, para o nosso controlador. 
 * 
 * Se algo der errado, capturamos a exceção e escrevemos de volta para o 
 * chamador sem prosseguir com a cadeia de filtros. Delegamos a avaliação da 
 * Chave de API e a construção do objeto Authentication à classe 
 * AuthenticationService
 * 
 * Aqui verificamos se o endpoint solicitado precisa ou não de autenticação.
 * No caso, /public não precisa.
 *
 *
 * @author kge
 */

@Component
public class AuthenticationFilter extends GenericFilterBean {

    private static final Logger LOGGER = Logger.getLogger( AuthenticationFilter.class.getName() );
    
    @Autowired
    private AuthenticationService authenticationService;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        LOGGER.log(Level.INFO, "01 AuthenticationFilter acionado!");

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // TODO: Implementar uma WhiteList ou BlackList.
        if (httpRequest.getServletPath().startsWith("/public")) {
            
            // /public acionado!
            // Se for /public, permite a passagem sem tentar autenticar
                LOGGER.log(Level.INFO, "Area publica AUTORIZADA.");

            filterChain.doFilter(request, response);
            
        } else {
            
            try {
                
                Authentication authentication = authenticationService.getAuthentication((HttpServletRequest) request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
        
            } catch (Exception exp) {

                LOGGER.log(Level.INFO, "Acesso Não autorizado");

                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = httpResponse.getWriter();
                writer.print(exp.getMessage());
                writer.flush();
                writer.close();
            }
        
        }
    }
}
