package wingsoft.shopping.service.custom;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebFilter(filterName="SearchFilter", urlPatterns = "/shopping/search.jsp")
public class SearchFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession httpSession = (HttpSession) request.getSession();
        if(httpSession.getAttribute("userContextStr") != null && httpSession.getAttribute("userId") != null){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/shopping/login.jsp";
            response.sendRedirect(basePath);
        }
    }

    @Override
    public void destroy() {

    }
}
