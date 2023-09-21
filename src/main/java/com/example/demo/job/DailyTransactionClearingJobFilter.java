package com.example.demo.job;

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
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class DailyTransactionClearingJobFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(DailyTransactionClearingJobFilter.class);
    private final TransactionClearingJob transactionClearingJob;

    private final ResponseModel responseModel;

    public DailyTransactionClearingJobFilter(TransactionClearingJob transactionClearingJob, ResponseModel responseModel1) {
        this.transactionClearingJob = transactionClearingJob;
        this.responseModel = responseModel1;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws IOException, ServletException {

        try {
            if (transactionClearingJob.isDailyJobCommencing()) {
                throw new RuntimeException("System unavailable");
            }
        } catch (RuntimeException e) {
            handleRunTImeException(response, e);
        } finally {
            SecurityContextHolder.clearContext();
        }

    }


    private void handleRunTImeException(HttpServletResponse response, RuntimeException e) {
        String errMessage = "A server error occurred. please retry later";
        LOGGER.error(e.getMessage(), e);

        ResponseError responseError = ResponseErrorAssembler.toResponseError(errMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        Response<ResponseError> errorResponse = ResponseAssembler.toResponse(HttpStatus.INTERNAL_SERVER_ERROR, responseError);
        responseModel.writeResponse(response, errorResponse);
    }

}
