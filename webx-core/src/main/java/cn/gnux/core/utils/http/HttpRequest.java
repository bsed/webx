package cn.gnux.core.utils.http;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.gnux.core.utils.CollectionUtil;
import cn.gnux.core.utils.Conver;
import cn.gnux.core.utils.FileUtil;
import cn.gnux.core.utils.IoUtil;
import cn.gnux.core.utils.SecureUtil;
import cn.gnux.core.utils.StrUtil;
import cn.gnux.core.utils.exceptions.HttpException;


/**
 * http请求类
 * @author 
 */
public class HttpRequest extends HttpBase<HttpRequest>{
	private static final String BOUNDARY = "--------------------Hutool_" + SecureUtil.simpleUUID();
	private static final String BOUNDARY_CRLF = "\r\n" + BOUNDARY +"\r\n";
	private static final String CONTENT_DISPOSITION_TEMPLATE = "Content-Disposition: form-data; name=\"{}\"\r\n\r\n";
	private static final String CONTENT_DISPOSITION_FILE_TEMPLATE = "Content-Disposition: form-data; name=\"{}\"; filename=\"{}\"\r\n";
	private static final String CONTENT_TYPE_FILE_TEMPLATE = "Content-Type:{}\r\n\r\n";

	protected Method method = Method.GET;
	private String url = "";
	/** 默认超时 */
	private int timeout = -1;
	/**存储表单数据*/
	protected Map<String, Object> form;
	/** 文件表单对象，用于文件上传 */
	protected Map<String, File> fileForm;
	
	/** 连接对象 */
	private HttpConnection httpConnection;
	
	/**
	 * 构造
	 * @param url URL
	 */
	public HttpRequest(String url) {
		this.url = url;
	}
	
	// ---------------------------------------------------------------- Http Method start
	/**
	 * 设置请求方法
	 * @param method HTTP方法
	 * @return HttpRequest
	 */
	public HttpRequest method(Method method) {
		this.method = method;
		return this;
	}
	
	/**
	 * POST请求
	 * @param url URL
	 * @return HttpRequest
	 */
	public static HttpRequest post(String url) {
		return new HttpRequest(url).method(Method.POST);
	}
	
	/**
	 * GET请求
	 * @param url URL
	 * @return HttpRequest
	 */
	public static HttpRequest get(String url) {
		return new HttpRequest(url).method(Method.GET);
	}
	
	/**
	 * HEAD请求
	 * @param url URL
	 * @return HttpRequest
	 */
	public static HttpRequest head(String url) {
		return new HttpRequest(url).method(Method.HEAD);
	}
	
	/**
	 * OPTIONS请求
	 * @param url URL
	 * @return HttpRequest
	 */
	public static HttpRequest options(String url) {
		return new HttpRequest(url).method(Method.OPTIONS);
	}
	
	/**
	 * PUT请求
	 * @param url URL
	 * @return HttpRequest
	 */
	public static HttpRequest put(String url) {
		return new HttpRequest(url).method(Method.PUT);
	}
	
	/**
	 * DELETE请求
	 * @param url URL
	 * @return HttpRequest
	 */
	public static HttpRequest delete(String url) {
		return new HttpRequest(url).method(Method.DELETE);
	}
	
	/**
	 * TRACE请求
	 * @param url URL
	 * @return HttpRequest
	 */
	public static HttpRequest trace(String url) {
		return new HttpRequest(url).method(Method.TRACE);
	}
	// ---------------------------------------------------------------- Http Method end
	
	// ---------------------------------------------------------------- Http Request Header start
	/**
	 * 设置contentType
	 * @param contentType contentType
	 * @return HttpRequest
	 */
	public HttpRequest contentType(String contentType) {
		header(Header.CONTENT_TYPE, contentType);
		return this;
	}
	
	/**
	 * 设置是否为长连接
	 * @param isKeepAlive 是否长连接
	 * @return HttpRequest
	 */
	public HttpRequest keepAlive(boolean isKeepAlive) {
		header(Header.CONNECTION, isKeepAlive ? "Keep-Alive" : "Close");
		return this;
	}
	
	/**
	 * @return 获取是否为长连接
	 */
	public boolean isKeepAlive() {
		String connection = header(Header.CONNECTION);
		if (connection == null) {
			return !httpVersion.equalsIgnoreCase(HTTP_1_0);
		}

		return !connection.equalsIgnoreCase("close");
	}
	
	/**
	 * 获取内容长度
	 * @return String
	 */
	public String contentLength() {
		return header(Header.CONTENT_LENGTH);
	}
	/**
	 * 设置内容长度
	 * @param value 长度
	 * @return HttpRequest
	 */
	public HttpRequest contentLength(int value) {
		header(Header.CONTENT_LENGTH, String.valueOf(value));
		return this;
	}
	// ---------------------------------------------------------------- Http Request Header end
	
	// ---------------------------------------------------------------- Form start
	/**
	 * 设置表单数据<br>
	 * 自动编码数据
	 * @param name 名
	 * @param value 值
	 */
	public HttpRequest form(String name, Object value) {
		return form(name, value, this.charset);
	}
	
	/**
	 * 设置表单数据<br>
	 * 自动编码数据
	 * @param name 名
	 * @param value 值
	 * @param charset 编码
	 */
	public HttpRequest form(String name, Object value, String charset) {
		//停用body
		this.body =null;
		
		if(value instanceof File){
			return this.form(name, (File)value);
		}else if(this.form == null) {
			form = new HashMap<String, Object>();
		}
		
		String strValue;
		if(value instanceof List){
			//列表对象
			strValue = CollectionUtil.join((List<?>)value, ",");
		}else if(CollectionUtil.isArray(value)){
			//数组对象
			strValue = CollectionUtil.join((Object[])value, ",");
		}else{
			//其他对象一律转换为字符串
			strValue = Conver.toStr(value, null);
		}
		
		form.put(HttpUtil.encode(name, charset), HttpUtil.encode(strValue, charset));
		return this;
	}
	/**
	 * 设置表单数据
	 * @param name 名
	 * @param value 值
	 * @param parameters 参数对，奇数为名，偶数为值

	 */
	public HttpRequest form(String name, Object value, Object... parameters) {
		form(name, value);

		for (int i = 0; i < parameters.length; i += 2) {
			name = parameters[i].toString();
			form(name, parameters[i + 1]);
		}
		return this;
	}

	/**
	 * 设置map类型表单数据
	 * @param formMap

	 */
	public HttpRequest form(Map<String, Object> formMap) {
		for (Map.Entry<String, Object> entry : formMap.entrySet()) {
			form(entry.getKey(), entry.getValue());
		}
		return this;
	}
	
	/**
	 * 文件表单项<br>
	 * 一旦有文件加入，表单变为multipart/form-data
	 * @param name 名
	 * @param file 文件
	 * @return HttpRequest
	 */
	public HttpRequest form(String name, File file){
		if(null == file){
			return this;
		}
		
		if(false == isKeepAlive()){
			keepAlive(true);
		}
		header(Header.CONTENT_TYPE, "multipart/form-data;boundary=" + BOUNDARY);
		
		if(this.fileForm == null) {
			fileForm = new HashMap<String, File>();
		}
		//文件对象
		this.fileForm.put(name, file);
		return this;
	}

	/**
	 * 获取表单数据
	 * @return Map<String, Object>
	 */
	public Map<String, Object> form() {
		return form;
	}
	// ---------------------------------------------------------------- Form end
	
	// ---------------------------------------------------------------- Body start
	/**
	 * 设置内容主体
	 * @param body
	 */
	public HttpRequest body(String body) {
		this.body = body;
		this.form = null; //当使用body时，废弃form的使用
		contentLength(body.length());
		return this;
	}

	/**
	 * 设置主体字节码
	 * @param content
	 * @param contentType
	 */
	public HttpRequest body(byte[] content, String contentType) {
//		this.contentType(contentType);
		return body(StrUtil.str(content, charset));
	}
	// ---------------------------------------------------------------- Body end
	
	/**
	 * 设置超时
	 * @param milliseconds
	 * @return HttpRequest
	 */
	public HttpRequest timeout(int milliseconds) {
		this.timeout = milliseconds;
		return this;
	}
	
	/**
	 * 执行Reuqest请求 
	 * @return HttpResponse
	 */
	public HttpResponse execute(){
		if(Method.GET.equals(method)){
			//优先使用body形式的参数，不存在使用form
			if(StrUtil.isNotBlank(this.body)) {
				this.url = HttpUtil.urlWithForm(this.url, this.body);
			}else {
				this.url = HttpUtil.urlWithForm(this.url, this.form);
			}
		}
		
		//初始化 connection
		this.httpConnection = HttpConnection.create(url, method)
				.setConnectionAndReadTimeout(timeout)
				.header(this.headers);
		
		//发送请求
		try {
			if(Method.POST.equals(method) || Method.PUT.equals(method)){
				send();//发送数据
			} else {
				this.httpConnection.connect();
			}
			
		} catch (IOException e) {
			throw new HttpException(e.getMessage(), e);
		}
		
		// 获取响应
		HttpResponse httpResponse = HttpResponse.readResponse(httpConnection);
		
		this.httpConnection.disconnect();
		return httpResponse;
	}
	
	/**
	 * 简单验证
	 * @param username 用户名
	 * @param password 密码
	 * @return HttpRequest
	 */
	public HttpRequest basicAuth(String username, String password) {
		final String data = username.concat(":").concat(password);
		final String base64 = SecureUtil.base64(data, charset);

		header("Authorization", "Basic " + base64, true);

		return this;
	}
	
	// ---------------------------------------------------------------- Private method start
	/**
	 * 发送数据流
	 * @throws IOException
	 */
	private void send() throws IOException {
		if(CollectionUtil.isNotEmpty(fileForm)){
			sendMltipart();
		}else{
			//Write的时候会优先使用body中的内容，write时自动关闭OutputStream
			String content;
			if(StrUtil.isNotBlank(this.body)) {
				content = this.body;
			}else {
				content = HttpUtil.toParams(this.form);
			}
			IoUtil.write(this.httpConnection.getOutputStream(), this.charset, true, content);
		}
	}
	
	/**
	 * 发送多组件请求（例如包含文件的表单）
	 * @throws IOException 
	 */
	private void sendMltipart() throws IOException{
		contentType("multipart/form-data");//设置表单类型
		
		this.httpConnection.disableCache();
		final OutputStream out = this.httpConnection.getOutputStream();
		
		//普通表单内容
		if(CollectionUtil.isNotEmpty(this.form)){
			StringBuilder builder = StrUtil.builder();
			for (Entry<String, Object> entry : this.form.entrySet()) {
				builder.append(BOUNDARY_CRLF);
				builder.append(StrUtil.format(CONTENT_DISPOSITION_TEMPLATE, entry.getKey()));
				builder.append(entry.getValue());
			}
			IoUtil.write(out, this.charset, false, builder.toString());
		}
		
		//文件
		File file;
		for (Entry<String, File> entry : this.fileForm.entrySet()) {
			file = entry.getValue();
			StringBuilder builder = StrUtil.builder().append(BOUNDARY_CRLF);
			builder.append(StrUtil.format(CONTENT_DISPOSITION_FILE_TEMPLATE, entry.getKey(), file.getName()));
			builder.append(StrUtil.format(CONTENT_TYPE_FILE_TEMPLATE, HttpUtil.getMimeType(file.getName())));
			IoUtil.write(out, this.charset, false, builder.toString());
			FileUtil.writeToStream(file, out);
		}
		
		//结尾
		out.write(("\r\n" + BOUNDARY + "--\r\n").getBytes());
		out.flush();
		
		FileUtil.close(out);
	}
	// ---------------------------------------------------------------- Private method end
	
}
