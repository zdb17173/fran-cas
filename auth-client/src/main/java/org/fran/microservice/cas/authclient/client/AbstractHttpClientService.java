package org.fran.microservice.cas.authclient.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author fran
 * @Description
 * @Date 2019/4/15 11:11
 */
public abstract class AbstractHttpClientService {
    private static final Logger logger = LoggerFactory.getLogger(AbstractHttpClientService.class);
    private String baseUrl;
    protected HttpClient client;
    protected HttpClient httpsClient;
    protected ObjectMapper mapper;
    protected RequestConfig requestConfig;
    private boolean debug = false;

    public void init(
            String baseUrl,
            int connectTimeout,
            int connectionRequestTimeout,
            int socketTimeOut
    ){
        init(baseUrl, connectTimeout, connectionRequestTimeout, socketTimeOut, false);
    }

    public void init(
            String baseUrl,
            int connectTimeout,
            int connectionRequestTimeout,
            int socketTimeOut,
            boolean debug
    ){
        this.debug = debug;
        this.baseUrl = baseUrl == null? "" : baseUrl;
        client = HttpClientBuilder.create().build();
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeOut)
                .build();

        SSLContext sslContext = null;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        httpsClient = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
    }

    public <T> T doPost(String path, Object param, Class<T> clazz) throws Exception {

        return doPost(path, param, null, ContentType.APPLICATION_JSON, clazz);
    }

    public <T> T doPost(String path, Object param, Map<String, String> headers, ContentType contentType , Class<T> clazz) throws Exception {
        HttpRequestBase request = new HttpPost(baseUrl + path);
        request.setConfig(requestConfig);
        if(headers != null && headers.size()>0){
            for(String key : headers.keySet()){
                if(headers.get(key) != null)
                    request.setHeader(key , headers.get(key));
            }
        }
        if(param != null) {
            ((HttpPost) request).setEntity(getBody(param, contentType));
        }

        return doRequest(request, clazz);
    }


    public <T> T doGet(String path, Map<String, Object> param, Map<String, String> headers, Class<T> clazz) throws Exception {
        String url = getUrlParam(path, param);
        HttpRequestBase request = new HttpGet(url);
        request.setConfig(requestConfig);
        if(headers != null && headers.size()>0){
            for(String key : headers.keySet()){
                if(headers.get(key) != null)
                    request.setHeader(key , headers.get(key));
            }
        }
        return doRequest(request, clazz);
    }

    public <T> T doGet(String path, Map<String, Object> param, Map<String, String> headers, JavaType clazz) throws Exception {
        String url = getUrlParam(path, param);
        HttpRequestBase request = new HttpGet(url);
        request.setConfig(requestConfig);
        if(headers != null && headers.size()>0){
            for(String key : headers.keySet()){
                if(headers.get(key) != null)
                    request.setHeader(key , headers.get(key));
            }
        }
        return doRequest(request, clazz);
    }

    public <T> T doGet(String path, Map<String, Object> param, Map<String, String> headers, TypeReference<T> clazz) throws Exception {
        String url = getUrlParam(path, param);
        HttpRequestBase request = new HttpGet(url);
        request.setConfig(requestConfig);
        if(headers != null && headers.size()>0){
            for(String key : headers.keySet()){
                if(headers.get(key) != null)
                    request.setHeader(key , headers.get(key));
            }
        }
        return doRequest(request, clazz);
    }

    private String getUrlParam(String path, Map<String, Object> param){
        String p = "";
        if(param == null || param.size() == 0)
            return baseUrl + (path == null? "" : path);
        else{
            for(String k : param.keySet()) {
                Object v = param.get(k);
                p += (p.equals("")? "": "&") + k + "=";

                try {
                    p += v == null? "" : URLEncoder.encode(v.toString(), StandardCharsets.UTF_8.name());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            if(p == null || "".equals(p))
                return baseUrl + path;
            else
                return baseUrl + path + "?" + p;
        }
    }

    private HttpEntity getBody(Object parameters, ContentType contentType) throws IOException {
        if(contentType == null || contentType == ContentType.APPLICATION_JSON){
            String json = mapper.writeValueAsString(parameters);

            StringEntity request = new StringEntity(json, StandardCharsets.UTF_8);
            request.setContentType(ContentType.APPLICATION_JSON.toString());
            return request;
        }
        else if(contentType == ContentType.APPLICATION_FORM_URLENCODED){

            if(parameters!= null){
                List<NameValuePair> params = new ArrayList<>();

                JsonNode t = mapper.valueToTree(parameters);
                t.fieldNames().forEachRemaining( fieldName -> {
                    params.add(new BasicNameValuePair(fieldName, t.get(fieldName).asText()));
                });
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
                entity.setContentType("application/x-www-form-urlencoded");
                return entity;
            }else
                return new UrlEncodedFormEntity(new ArrayList<>(), "UTF-8");
        }else{
            throw new IOException("unsupport contentType["+ Objects.toString(contentType) +"]");
        }
    }

    private <T> T doRequest(HttpRequestBase request, Class<T> clazz) throws Exception {
        try {
            HttpResponse response = client.execute(request);
            try(InputStream stream = response.getEntity().getContent()){
                if(debug || clazz.equals(String.class)){
                    String json = readStream(stream);
                    logger.info("------------------------");
                    logger.info(json);
                    logger.info("------------------------");
                    if(clazz.equals(String.class)){
                        return (T)json;
                    }
                    T res = mapper.readValue(json, clazz);
                    return res;
                }else{
                    T res = mapper.readValue(stream, clazz);
                    return res;
                }
            }catch (Exception e){
                logger.error("convert json error", e);
                throw e;
            }
        } catch (Exception e) {
            logger.error("request error", e);
            throw e;
        }finally {
            request.releaseConnection();
        }
    }

    private <T> T doRequest(HttpRequestBase request, TypeReference<T> typeReference) throws Exception {
        try {
            HttpResponse response = client.execute(request);
            try(InputStream stream = response.getEntity().getContent()){
                if(debug){
                    String json = readStream(stream);
                    logger.info("------------------------");
                    logger.info(json);
                    logger.info("------------------------");
                    T res = mapper.readValue(json, typeReference);
                    return res;
                }else{
                    T res = mapper.readValue(stream, typeReference);
                    return res;
                }
            }catch (Exception e){
                logger.error("convert json error", e);
                throw e;
            }
        } catch (Exception e) {
            logger.error("request error", e);
            throw e;
        }finally {
            request.releaseConnection();
        }
    }

    private <T> T doRequest(HttpRequestBase request, JavaType javaType) throws Exception {
        try {
            HttpResponse response = client.execute(request);
            try(InputStream stream = response.getEntity().getContent()){
                if(debug){
                    String json = readStream(stream);
                    logger.info("------------------------");
                    logger.info(json);
                    logger.info("------------------------");
                    T res = mapper.readValue(json, javaType);
                    return res;
                }else{
                    T res = mapper.readValue(stream, javaType);
                    return res;
                }
            }catch (Exception e){
                logger.error("convert json error", e);
                throw e;
            }
        } catch (Exception e) {
            logger.error("request error", e);
            throw e;
        }finally {
            request.releaseConnection();
        }
    }

    private String readStream(InputStream stream) throws IOException {
        return CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
    }

}
