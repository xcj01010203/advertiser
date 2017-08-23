package com.xiaotu.advertiser.common.aop;

/**
 * @类名 ClientReponse
 * @日期 2015年3月2日
 * @作者 高海军
 * @功能 客户端响应
 */
public class ClientResponse
{
    public static final String ERROR = "1";

    public static final String SUCCESS = "0";

    private String message = "";

    private String status = SUCCESS;

    private Object data;

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }
}
