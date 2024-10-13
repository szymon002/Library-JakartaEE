package lab.library.controller.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lab.library.controller.servlet.ApiServlet;
import lab.library.controller.servlet.exception.HttpRequestException;

import java.io.IOException;

@WebFilter(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
public class ExceptionFilter extends HttpFilter implements Filter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            super.doFilter(request, response, chain);
        } catch (HttpRequestException ex) {
            response.sendError(ex.getResponseCode());
        }
    }

}
