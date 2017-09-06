package com.xiaotu.common.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 功能：gzip压缩与解压帮助类
 */
public class GZIPUtils
{

    private static final Logger LOGGER = LoggerFactory.getLogger(GZIPUtils.class);

    /**
     * 压缩数据
     *
     * @param data 数据
     * @return 压缩结果
     */
     /*
   * 字符串压缩为字节数组
   */
    public static byte[] gzip(byte[] data)
    {
        if (data == null || data.length < 1)
            return data;
        LOGGER.debug("before gzip length:{}", data.length);
        GZIPOutputStream gzip = null;
        try
        {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(out);
            gzip.write(data);
            gzip.close();
            byte[] result = out.toByteArray();
            LOGGER.debug("after gzip length:{}", result.length);
            return result;
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }


    /**
     * 解压缩
     *
     * @param data 压缩数据
     * @return 解压数据
     */
    public static byte[] gunzip(byte[] data)
    {
        if (data == null || data.length < 1)
            return data;
        LOGGER.debug("before gunzip length:{}", data.length);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        GZIPInputStream ginzip = null;
        try
        {
            in = new ByteArrayInputStream(data);
            ginzip = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = ginzip.read(buffer)) > -1)
                out.write(buffer, 0, offset);
            byte[] result = out.toByteArray();
            LOGGER.debug("after gunzip length:{}", result.length);
            return result;
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            if (ginzip != null)
            {
                try
                {
                    ginzip.close();
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception
    {
        byte[] data = ("我是挺爱吃我是挺爱吃我是挺爱吃我是挺爱吃我是挺爱吃我是挺爱吃我是挺爱吃我是挺爱吃我是挺爱吃我是挺爱吃我是挺爱吃我是挺爱吃").getBytes();
        byte[] res = GZIPUtils.gzip(data);
        byte[] str = GZIPUtils.gunzip(res);
        System.out.println(new String(str));


        FileUtils.writeByteArrayToFile(new File("D:\\test.txt"), res);

        data = FileUtils.readFileToByteArray(new File("D:\\test.txt"));
        data = GZIPUtils.gunzip(data);

        System.out.println(new String(data));
    }

}
