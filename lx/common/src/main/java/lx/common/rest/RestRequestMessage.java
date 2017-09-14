package lx.common.rest;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Map;

/**
 * 发送Rest请求信息对象
 */
public class RestRequestMessage
{
    /**
     * GET, POST, PUT, DELETE
     */
    private String httpMethod;

    private Map<String, String> params;

    private Map<String, String> headers;

    private Object payload;

    /**
     * 构造函数
     */
    public RestRequestMessage()
    {
    }

    /**
     * 取得httpMethod
     *
     * @return 返回 httpMethod
     */
    public String getHttpMethod()
    {
        return httpMethod;
    }

    /**
     * 设置httpMethod
     *
     * @param httpMethod 要设置的httpMethod
     */
    public void setHttpMethod(String httpMethod)
    {
        this.httpMethod = httpMethod;
    }

    /**
     * 取得params
     *
     * @return 返回 params
     */
    public Map<String, String> getParams()
    {
        return null == params ? Collections.<String, String> emptyMap() : Collections.unmodifiableMap(params);
    }

    /**
     * 设置params
     *
     * @param params 要设置的params
     */
    public void setParams(Map<String, String> params)
    {
        this.params = params;
    }

    /**
     * 取得headers
     *
     * @return 返回 headers
     */
    public Map<String, String> getHeaders()
    {
        return null == headers ? Collections.<String, String> emptyMap() : Collections.unmodifiableMap(headers);
    }

    /**
     * 设置headers
     *
     * @param headers 要设置的headers
     */
    public void setHeaders(Map<String, String> headers)
    {
        this.headers = headers;
    }

    /**
     * 取得payload
     *
     * @return 返回 payload
     */
    public Object getPayload()
    {
        return payload;
    }

    /**
     * 设置payload
     *
     * @param payload 要设置的payload
     */
    public void setPayload(Object payload)
    {
        this.payload = payload;
    }

    /**
     * 返回String
     *
     * @return String
     */
    public String getPayLoad()
    {
        return null == payload ? StringUtils.EMPTY : payload.toString();
    }
}
