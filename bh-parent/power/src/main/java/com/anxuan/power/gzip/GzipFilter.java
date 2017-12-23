package com.anxuan.power.gzip;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;


/**
 * GZIP流压缩文件传输 压缩率50%-80%
 * 
 * @author shadowOO
 * @email 124010356@qq.com
 * @create 2012.04.28
 */
public class GzipFilter implements Filter {

	private final String ENABLE = "enable";
	private final String GZIP = "gzip";

	private boolean enable = true;

	public void init(FilterConfig config) throws ServletException {

		// 获取配置参数
		String enable = config.getInitParameter(ENABLE);
		if (null == enable)
			// 默认是开启压缩功能
			this.enable = true;
		else
			this.enable = Boolean.valueOf(enable);
	}

	public void doFilter(ServletRequest rq, ServletResponse rp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) rq;
		HttpServletResponse response = (HttpServletResponse) rp;
		// 浏览器支持GZIP以及开启GZIP功能的情况
		if (enable && isGZipEncoding(request)) {
			GzipResponse zipResponse = new GzipResponse(response);
			response.setHeader("Content-Encoding", GZIP);
			chain.doFilter(request, zipResponse);
			zipResponse.flush();
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * 判断浏览器是否支持GZIP
	 * 
	 * @param request
	 * @return Boolean
	 */
	private boolean isGZipEncoding(HttpServletRequest request) {
		boolean flag = false;
		String encoding = request.getHeader("Accept-Encoding");
		if (!StringUtils.isEmpty(encoding) && encoding.indexOf(GZIP) != -1)
			flag = true;
		return flag;
	}

	public void destroy() {
	}

}
