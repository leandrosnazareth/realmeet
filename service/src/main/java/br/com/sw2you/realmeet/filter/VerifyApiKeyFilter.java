package br.com.sw2you.realmeet.filter;

import static org.apache.commons.lang3.StringUtils.isBlank;

import br.com.sw2you.realmeet.domain.entity.Client;
import br.com.sw2you.realmeet.domain.repository.ClientRepository;
import java.io.IOException;
import java.io.Writer;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

public class VerifyApiKeyFilter extends GenericFilterBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerifyApiKeyFilter.class);
    private static final String HEADER_API_KEY = "api-key";

    private final ClientRepository clientRepository;

    public VerifyApiKeyFilter(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;//converter pata http request
        var httpResponse = (HttpServletResponse) servletResponse;//converter parar response

        var apiKey = httpRequest.getHeader(HEADER_API_KEY);//pegar o api-key

        //verificar se é branco ou nulo
        if (!isBlank(apiKey) && isValidApiKey(apiKey)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            //se está válida
            sendUnauthorizedError(httpResponse, apiKey);
        }
    }

    private boolean isValidApiKey(String apiKey) {
        return clientRepository
                .findById(apiKey)
                .filter(Client::getActive)//retornar somente a api ativa
                .stream()
                .peek(c -> LOGGER.info("Valid API Key: '{}' ({})", c.getApiKey(), c.getDescription()))//substituir os {} pelo apikey e a descrição
                .findFirst()//pegar o primeiro elemento ou nã pega nenhum
                .isPresent();//verifica se está presente
    }

    //enviar erro para o cliente
    private void sendUnauthorizedError(HttpServletResponse response, String apiKey) throws IOException {
        var errorMessage = isBlank(apiKey) ? "API Key esta faltando" : "API Key inválida";
        LOGGER.error(errorMessage);

        //setar o erro
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentLength(errorMessage.length());
        response.setContentType("plain/text");

        //escrever dados na response
        try (Writer out = response.getWriter()) {
            out.write(errorMessage);
        }
    }
}