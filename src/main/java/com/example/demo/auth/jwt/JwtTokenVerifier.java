package com.example.demo.auth.jwt;

import com.example.demo.auth.AuthService;
import com.example.demo.auth.model.request.AuthenticateViaAccessJWTRequest;
import com.example.demo.auth.model.response.AuthenticateViaAccessJWTResult;
import com.example.demo.utils.response.ResponseModel;
import com.example.demo.utils.response.model.Response;
import com.example.demo.utils.response.model.ResponseError;
import com.example.demo.utils.response.transformer.ResponseAssembler;
import com.example.demo.utils.response.transformer.ResponseErrorAssembler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class JwtTokenVerifier extends OncePerRequestFilter {
    public static final String HEADER_PREFIX = "Bearer ";
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenVerifier.class);

    private final List<RequestMatcher> requestMatchers;


    private final AuthService authService;
    private final ResponseModel responseModel;

    public JwtTokenVerifier(@Qualifier("defaultAuthService") AuthService authService,
                            @Qualifier("handlerMappingIntrospector") HandlerMappingIntrospector handlerMappingIntrospector,
                            @Qualifier("responseModel") ResponseModel responseModel) {
        this.authService = authService;
        this.responseModel = responseModel;

        MvcRequestMatcher mvcRequestMatcher = new MvcRequestMatcher(handlerMappingIntrospector, "/api/v1/auth/login");
        mvcRequestMatcher.setMethod(HttpMethod.POST);

        MvcRequestMatcher mvcRequestMatcher2 = new MvcRequestMatcher(handlerMappingIntrospector, "/api/v1/blog-post/");
        mvcRequestMatcher2.setMethod(HttpMethod.GET);

        MvcRequestMatcher mvcRequestMatcher3 = new MvcRequestMatcher(handlerMappingIntrospector, "/api/v1/register/");
        mvcRequestMatcher3.setMethod(HttpMethod.POST);

        requestMatchers = List.of(mvcRequestMatcher, mvcRequestMatcher2, mvcRequestMatcher3);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws IOException, ServletException {

        try {
            String authToken = authToken(request);
            if (Objects.isNull(authToken)) throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Auth Token Required");

            AuthenticateViaAccessJWTRequest authenticateViaAccessJWTRequest = new AuthenticateViaAccessJWTRequest(authToken);
            AuthenticateViaAccessJWTResult authenticateViaAccessJWTResult = authService.authenticateViaAccessJWT(authenticateViaAccessJWTRequest);

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authenticateViaAccessJWTResult.authentication());

            filterChain.doFilter(request, response);
        } catch (ResponseStatusException e) {
            handleAuthException(response, e);
        } catch (RuntimeException e) {
            handleRunTImeException(response, e);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    private String authToken(@NonNull HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        return (bearerToken != null && bearerToken.startsWith(HEADER_PREFIX)) ?
                bearerToken.replace(HEADER_PREFIX, "") : bearerToken;
    }

    private void handleAuthException(@NonNull HttpServletResponse response, ResponseStatusException e) {
        //TODO: Create custom exception
        LOGGER.warn(e.getMessage(), e);

        HttpStatus httpStatus = HttpStatus.resolve(e.getStatusCode().value());
        assert httpStatus != null;
        ResponseError responseError = ResponseErrorAssembler.toResponseError(e.getReason(), httpStatus);
        Response<ResponseError> errorResponse = ResponseAssembler.toResponse(httpStatus, responseError);
        responseModel.writeResponse(response, errorResponse);
    }

    private void handleRunTImeException(HttpServletResponse response, RuntimeException e) {
        String errMessage = "A server error occurred. please retry later";
        LOGGER.error(e.getMessage(), e);

        ResponseError responseError = ResponseErrorAssembler.toResponseError(errMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        Response<ResponseError> errorResponse = ResponseAssembler.toResponse(HttpStatus.INTERNAL_SERVER_ERROR, responseError);
        responseModel.writeResponse(response, errorResponse);
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        //Has bugs; implement yours

        return requestMatchers.stream().anyMatch(requestMatcher -> requestMatcher.matches(request));
    }

}
