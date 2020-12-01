package com.berrontech.weight.update.util.http;

import com.berrontech.weight.update.context.ContentType;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * Create by 郭文梁 2019/4/19 0019 14:03
 * HttpUtils
 * Http相关工具类
 *
 * @author 郭文梁
 */
@Slf4j
public class HttpUtils {
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 获取客户端
     *
     * @return 客户端对象
     */
    private static CloseableHttpClient getClient() {
        return HttpClients.createDefault();
    }

    /**
     * POST请求
     *
     * @param url         URL
     * @param body        BODY
     * @param contentType Content-Type Header
     * @return Response
     * @throws IOException IO异常
     */
    public static String post(String url, String body, String contentType) throws IOException {
        try (CloseableHttpClient client = getClient()) {
            return doPost(client, url, body, contentType);
        }
    }

    /**
     * 发送Post请求 请求体为JSON
     *
     * @param url  地址
     * @param body 请求体
     * @return 响应结果
     */
    public static String postJson(String url, String body) throws IOException {
        return post(url, body, ContentType.JSON_UTF8);
    }

    /**
     * 发起Get请求
     *
     * @param url 请求地址
     * @return 字符串响应内容
     * @throws IOException IO异常
     */
    public static String get(String url) throws IOException {
        try (CloseableHttpClient client = getClient()) {
            HttpGet get = new HttpGet(url);
            try (CloseableHttpResponse response = client.execute(get)) {
                return response2String(response);
            }
        }
    }

    /**
     * 将Http响应转换为字符串
     *
     * @param response 响应对象
     * @return 字符创
     * @throws IOException IO异常
     */
    private static String response2String(CloseableHttpResponse response) throws IOException {
        val res = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
        val respCode = response.getStatusLine().getStatusCode();
        if (respCode == HttpStatus.SC_OK) {
            return res;
        } else {
            log.error("Error response code=[{}], body=[{}]", respCode, res);
            throw new IOException("Response status code is " + respCode);
        }
    }

    /**
     * 发送POST请求 内容为xml
     *
     * @param url  路径
     * @param body 内容
     * @return 响应
     * @throws IOException IO异常
     */
    public static String postXml(String url, String body) throws IOException {
        return post(url, body, ContentType.XML_URT8);
    }

    /**
     * 执行POST操作
     *
     * @param client      客户端
     * @param url         地址
     * @param body        内容
     * @param contentType Content-Type Header
     * @return 响应
     * @throws IOException IO异常
     */
    private static String doPost(CloseableHttpClient client, String url, String body, String contentType) throws IOException {
        HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(body, DEFAULT_CHARSET);
        entity.setContentType(contentType);
        post.setEntity(entity);
        try (CloseableHttpResponse response = client.execute(post)) {
            return response2String(response);
        }
    }

    /**
     * 构建带证书的Client
     *
     * @param keyStore 证书
     * @param password 密码
     * @return Client
     * @throws Exception 异常
     */
    private static CloseableHttpClient buildSecurityClient(KeyStore keyStore, String password) throws Exception {
        HttpClientBuilder builder = HttpClientBuilder.create();
        SSLContext sslcontext = SSLContext.getInstance("SSL");
        X509TrustManager tm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] arg0,
                                           String arg1) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0,
                                           String arg1) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        String alg = KeyManagerFactory.getDefaultAlgorithm();
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(alg);
        keyManagerFactory.init(keyStore, password.toCharArray());
        KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
        sslcontext.init(keyManagers, new TrustManager[]{tm}, null);
        builder.setSSLContext(sslcontext);
        return builder.build();
    }

    /**
     * Wrap response as HttpApiStringResponse Object
     *
     * @param response apache-http-component response object
     * @return HttpApiStringResponse
     * @throws IOException IOE
     */
    private static HttpApiStringResponse asStringResponse(CloseableHttpResponse response) throws IOException {
        val code = response.getStatusLine().getStatusCode();
        val content = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
        return new HttpApiStringResponse(code, content);
    }

    /**
     * Map params as string form Body
     *
     * @param params params
     * @return body string
     */
    public static String asFormBody(Map<String, Object> params) {
        val body = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (body.length() > 0) {
                body.append('&');
            }
            body.append(entry.getKey()).append('=').append(entry.getValue());
        }
        return body.toString();
    }

    /**
     * Download file
     *
     * @param url  url
     * @param file file
     * @throws IOException IOE
     */
    public static void download(String url, File file) throws IOException {
        try (final CloseableHttpClient client = getClient()) {
            final HttpGet get = new HttpGet(url);
            final CloseableHttpResponse response = client.execute(get);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                throw new IOException("Can not download from server with status code:" + statusCode);
            }
            final HttpEntity entity = response.getEntity();
            if (entity == null) {
                throw new IOException("The server return a empty response!");
            }
            writeContentIntoFile(entity.getContent(), file);
            response.close();
        }
    }

    private static void writeContentIntoFile(InputStream content, File file) throws IOException {
        try {
            try (final FileOutputStream out = new FileOutputStream(file)) {
                IOUtils.copy(content, out);
            }
        } finally {
            if (content != null) {
                content.close();
            }
        }
    }
}

