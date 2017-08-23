package com.xiaotu.common.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import com.xiaotu.advertiser.common.util.Constants;

/**
 * @类名 SocketUtil
 * @日期 2017年7月3日
 * @作者 高海军
 * @功能 socket帮助类
 */
public class SocketClientUtil
{
	private static final int LENGTH_HEADER_SIZE = 9;// 长度域大小
	
	private static final int READ_DATA_INTERVAL = 10;// 循环读取数据的间隔时间，单位ms
	
	public static final String EOF = "#eof#";
	
	private SocketChannel socketChannel;
	private Selector selector;
	
	public static void main(String[] args) throws IOException
	{
		System.out.println("start");
		byte[] dataArr = (""
				+ EOF).getBytes(Constants.CHARSET_UTF8);
		byte[] resArr = new SocketClientUtil("localhost", 8000).send(dataArr);
		if (resArr == null)
			return;
		FileUtils.write(new File("D://data.txt"), new String(resArr));
	}
	
	public SocketClientUtil(String host, int port)
	{
		try
		{
			this.initClient(host, port);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 请求服务端数据
	 * @param sendData 请求数据
	 * @return 相应数据
	 */
	public byte[] send(byte[] sendData)
	{
		try
		{
			this.writeData(sendData);
			return this.readData();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				this.close();
			}
			catch (IOException ex)
			{
				throw new RuntimeException(ex);
			}
		}
	}
	
	/**
	 * 读取服务端数据
	 * @return 数据内容
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @throws InterruptedException
	 */
	private byte[] readData() throws IOException, UnsupportedEncodingException,
			InterruptedException
	{
		int select = selector.select();
		if (select > 0)
		{
			byte[] data = null;
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> iter = keys.iterator();
			while (iter.hasNext())
			{
				SelectionKey sk = iter.next();
				if (sk.isReadable())
				{
					if ((data = this.readData(sk)) != null)
						return data;
				}
				iter.remove();
			}
		}
		return null;
	}
	
	/**
	 * 读取服务端发送的完整数据
	 * @param sk
	 * @return 数据内容
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @throws InterruptedException
	 */
	private byte[] readData(SelectionKey sk) throws IOException,
			UnsupportedEncodingException, InterruptedException
	{
		SocketChannel channel = (SocketChannel) sk.channel();
		ByteBuffer lenBuffer = ByteBuffer.allocate(LENGTH_HEADER_SIZE);// 存储长度信息
		int lenLen = 0;// 实际读取的长度域的长度
		int temp = 0;// 每次读取的长度
		
		do
		{
			temp = channel.read(lenBuffer);
		}
		while (temp > -1 && (lenLen += temp) != LENGTH_HEADER_SIZE);
		if (lenBuffer.position() < 1)
			return null;
		
		int dataLen = Integer.parseInt(new String(lenBuffer.array()));// 数据域总长度
		if (dataLen < 1)
			return null;
		
		ByteBuffer dataBuffer = ByteBuffer.allocate(dataLen);
		int len = 0;// 实际读取数据长度
		
		temp = channel.read(dataBuffer);
		while (temp > -1 && ((len += temp)) != dataLen)
		{
			temp = channel.read(dataBuffer);
			Thread.sleep(READ_DATA_INTERVAL);
		}
		if (dataBuffer.position() < 1)
			return null;
		
		return dataBuffer.array();
	}
	
	/**
	 * 写入请求数据
	 * @param data 请求数据
	 * @throws IOException
	 */
	private void writeData(byte[] data) throws IOException
	{
		try
		{
			ByteBuffer buffer = ByteBuffer.wrap(data);
			while (buffer.hasRemaining())
				socketChannel.write(buffer);
		}
		catch (IOException e)
		{
			if (socketChannel.isOpen())
				socketChannel.close();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 初始化客户端链接
	 * @param host 服务端主机
	 * @param port 端口
	 * @throws IOException
	 * @throws ClosedChannelException
	 * @throws InterruptedException
	 */
	private void initClient(String host, int port)
			throws IOException, ClosedChannelException, InterruptedException
	{
		InetSocketAddress addr = new InetSocketAddress(host, port);
		socketChannel = SocketChannel.open();
		
		selector = Selector.open();
		socketChannel.configureBlocking(false);
		socketChannel.register(selector, SelectionKey.OP_READ);
		// 连接到server
		socketChannel.connect(addr);
		
		while (!socketChannel.finishConnect())
			Thread.sleep(READ_DATA_INTERVAL);
	}
	
	/**
	 * 停止客户端
	 * @throws IOException
	 */
	private void close() throws IOException
	{
		if (selector != null && selector.isOpen())
			selector.close();
		if (socketChannel != null && socketChannel.isOpen())
			socketChannel.close();
	}
}