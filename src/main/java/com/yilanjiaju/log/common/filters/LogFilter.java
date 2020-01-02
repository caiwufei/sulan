package com.yilanjiaju.log.common.filters;

import com.yilanjiaju.log.common.AppContext;
import com.yilanjiaju.log.common.base.ExtendHttpServletRequestWrapper;
import com.yilanjiaju.log.common.base.ExtendHttpServletResponseWrapper;
import com.yilanjiaju.log.common.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String uri = ((HttpServletRequest) servletRequest).getRequestURI();
        String contentType = ((HttpServletRequest) servletRequest).getContentType();

        //如果是同步接口，就不处理相关，否则日志太庞大
        if(uri.indexOf("/api/sync/")>-1){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        long start = System.currentTimeMillis();
        //设置日志id
        String requestLogId = UUID.randomUUID().toString().replace("-", "").substring(0,10);
        AppContext.setRequestLogId(requestLogId);
        MDC.put(Constant.REQUEST_LOG_ID, requestLogId);
        log.info("----------------Request start, LogFilter set requestLogId == {}", requestLogId);

        //转换request和response的包装类
        if (null!= contentType && (contentType.indexOf(MediaType.MULTIPART_FORM_DATA_VALUE) >= 0
                || contentType.indexOf(MediaType.APPLICATION_PDF_VALUE) >= 0
                || contentType.indexOf(MediaType.IMAGE_JPEG_VALUE) >= 0
                || contentType.indexOf(MediaType.IMAGE_PNG_VALUE) >= 0
                || contentType.indexOf(MediaType.IMAGE_GIF_VALUE) >= 0)) {
            //do nothing
            log.debug("request body is a type of stream,skip print.");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ExtendHttpServletRequestWrapper requestWrapper = new ExtendHttpServletRequestWrapper((HttpServletRequest) servletRequest);
            ExtendHttpServletResponseWrapper responseWrapper = new ExtendHttpServletResponseWrapper((HttpServletResponse) servletResponse);
            log.info("----------------request uri  : " + requestWrapper.getRequestURI());
            log.info("----------------request body : " + requestWrapper.getRequestBody());

            filterChain.doFilter(requestWrapper, responseWrapper);

            if(null==responseWrapper.getContentType() || responseWrapper.getContentType().indexOf(MediaType.APPLICATION_OCTET_STREAM_VALUE)==-1){
                String responseBodyString = new String(responseWrapper.toByteArray(), StandardCharsets.UTF_8);
                log.info("----------------response body : " + responseBodyString);
            } else {
                log.info("----------------response body : as media_type==application/octet-stream, none print response body");
            }
        }
        long end = System.currentTimeMillis();
        log.info("----------------Request end, cost : " + (end - start));
    }
}
