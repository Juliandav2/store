package com.tienda.usecases;

import com.tienda.security.RateLimitFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

public class RateLimitFilterTest {

    private RateLimitFilter rateLimitFilter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        rateLimitFilter = new RateLimitFilter();
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        filterChain = Mockito.mock(FilterChain.class);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");
    }

    @Test
    void shouldAllowRequestsUnderLimit() throws Exception {
        rateLimitFilter.doFilter(request, response, filterChain);
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void shouldBlockRequestsOverLimit() throws Exception {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        for (int i = 0; i < 61; i++) {
            rateLimitFilter.doFilter(request, response, filterChain);
        }

        verify(response, atLeastOnce()).setStatus(429);
    }

    @Test
    void shouldAllowDifferentIPs() throws Exception {
        HttpServletRequest request2 = Mockito.mock(HttpServletRequest.class);
        when(request2.getRemoteAddr()).thenReturn("192.168.1.1");

        rateLimitFilter.doFilter(request, response, filterChain);
        rateLimitFilter.doFilter(request2, response, filterChain);

        verify(filterChain, times(2)).doFilter(any(), any());
    }
}