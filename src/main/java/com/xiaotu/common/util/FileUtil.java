package com.xiaotu.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil
{
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FileUtil.class);
	
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * 上传文件工具方法
	 * @author xuchangjian 2017年6月21日下午1:39:38
	 * @param file
	 * @param storePath 存储目录（不带文件信息）
	 * @param newName 新文件名（带后缀），可不填
	 * @return key为fileRealName时，value表示文件原来的名称，带后缀
	 *         key为fileStoreName时，value表示文件存到服务器上的的名称，带后缀
	 *         key为storepath时，value表示文件存储路径（在请求参数storePath的基础上添加了一个日期路径）
	 *         key为size时，value表示文件大小
	 * @throws IOException
	 */
	public static final Map<String, String> uploadFile(MultipartFile file,
			String storePath, String newName) throws IOException
	{
		String newStorePath = "";
		String fileRealName = "";
		String fileStoreName = ""; //
		String size = ""; // 文件大小
		
		// 文件上传目录
		byte[] bytes = file.getBytes();
		if (bytes == null || bytes.length == 0)
		{
			return null;
		}
		if (bytes.length != 0)
		{
			// 文件真实名称
			fileRealName = file.getOriginalFilename();
			String suffix = ""; // 文件后缀
			String fileNameWithoutSuffix = ""; // 文件除了后缀以外的部分
			if (fileRealName.lastIndexOf(".") != -1)
			{
				suffix = fileRealName.substring(fileRealName.lastIndexOf("."));
				fileNameWithoutSuffix = fileRealName.substring(0,
						fileRealName.lastIndexOf("."));
			}
			size = file.getSize() + "";
			
			// 文件存储到服务器上的名称
			if (!StringUtils.isBlank(newName))
			{
				fileStoreName = newName;
			}
			else
			{
				fileStoreName = fileNameWithoutSuffix + "_"
						+ UUIDUtil.getUUID().substring(0, 7) + suffix;
			}
			
			// 在storePath的基础上添加一级日期文件夹
			String dateStr = sdf1.format(new Date());
			newStorePath = storePath + File.separator + dateStr
					+ File.separator;
			
			// 上传文件到服务器
			File folder = new File(newStorePath);
			if (!folder.exists())
			{
				folder.mkdirs();
			}
			
			File uploadedFile = new File(newStorePath + fileStoreName);
			FileCopyUtils.copy(bytes, uploadedFile);
		}
		
		// 保存剧本文件基本信息
		Map<String, String> map = new HashMap<String, String>();
		map.put("fileRealName", fileRealName);
		map.put("fileStoreName", fileStoreName);
		map.put("storepath", newStorePath);
		map.put("size", size);
		
		return map;
	}
	
	/**
	 * 去掉文件扩展名
	 * @param fileName
	 * @return
	 */
	public static String removeExtendName(String fileName, String extName)
	{
		if (StringUtil.isEmpty(fileName) || StringUtil.isEmpty(extName))
		{
			return fileName;
		}
		if (fileName.endsWith(extName))
			return fileName.substring(0, fileName.indexOf(extName));
		return fileName;
	}
	
	/**
	 * 获取文件名（不包含扩展名）
	 * @param fileName
	 * @return
	 */
	public static String getFileNameWithoutExt(String fileName)
	{
		if (StringUtils.isEmpty(fileName))
			return fileName;
		if (!fileName.contains("."))
			return fileName;
		return fileName.substring(0, fileName.lastIndexOf("."));
	}
	
	public static boolean fileExits(String fileName)
	{
		return new File(fileName).exists();
	}
	
	/**
	 * 获取文件扩展名,不含有"."
	 * @param fileName
	 * @return
	 */
	public static String getExtendName(String fileName)
	{
		String ext = getNameExtend(fileName);
		if (StringUtil.isEmpty(ext))
			return fileName;
		return ext;
	}
	
	/**
	 * 获取文件扩展名,含有"."
	 * @param fileName 文件名
	 * @return 扩展名
	 */
	public static String getNameExtend(String fileName)
	{
		if (fileName == null || fileName.equals(""))
		{
			return fileName;
		}
		int dot = fileName.lastIndexOf('.');
		if ((dot > -1) && (dot < (fileName.length() - 1)))
		{
			return fileName.substring(dot);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 检验是否为word文档
	 * @param fileName
	 * @return
	 */
	public static boolean checkWordDoc(String fileName)
	{
		if (fileName == null || fileName.equals(""))
		{
			return false;
		}
		String extendName = getExtendName(fileName);
		if (extendName == null || extendName.equals("")
				|| (!extendName.equals("doc") && !extendName.equals("docx")))
		{
			return false;
		}
		return true;
	}
	
	public static DocEntity readWordFile(File wordFile)
	{
		
		return null;
	}
	
	public static class DocEntity
	{
		private int charactersSize;
		
		private String text;
		
		public DocEntity(int charactersSize, String text)
		{
			super();
			this.charactersSize = charactersSize;
			this.text = text;
		}
		
		public int getCharactersSize()
		{
			return charactersSize;
		}
		
		public String getText()
		{
			return text;
		}
	}
	
	public static boolean makeDir(File descFile)
	{
		if (!descFile.getParentFile().exists())
		{
			// 如果目标文件所在的目录不存在，则创建父目录
			if (!descFile.getParentFile().mkdirs())
				return false;
		}
		return true;
	}
	
	public static boolean forceDeleteFile(String filePath)
	{
		if (!deleteFile(filePath))
		{
			System.gc();
			return deleteFile(filePath);
		}
		return true;
	}
	
	public static boolean deleteFile(String filePath)
	{
		File file = new File(filePath);
		LOGGER.info("want to delete file:" + filePath);
		if (file.exists())
			return file.delete();
		return true;
		
	}
	
	public static String getChildPath(String realPath, String parentPath)
	{
		String rootPath = new File(realPath).getAbsolutePath();
		File parentFile = new File(parentPath);
		if (!parentFile.isDirectory())
			return null;
		String prefixPath = parentFile.getAbsolutePath();
		prefixPath = (prefixPath.endsWith(File.separator) ? prefixPath
				: prefixPath + File.separator);
		int index = rootPath.indexOf(prefixPath);
		if (index < 0)
			return null;
		return rootPath.substring(index + prefixPath.length());
	}
	
	/**
	 * 按行读取文件
	 * @param fileName 文件名称
	 * @return 行列表
	 */
	public static List<String> readFileByLine(String fileName, String encode)
	{
		try
		{
			List<String> lineList = new ArrayList<String>();
			
			InputStreamReader ips = new InputStreamReader(
					new FileInputStream(new File(fileName)), encode);
			BufferedReader file = new BufferedReader(ips);
			String str = null;
			while ((str = file.readLine()) != null)
				lineList.add(str);
			
			ips.close();
			file.close();
			
			return lineList;
		}
		catch (FileNotFoundException e)
		{
			throw new RuntimeException(e);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public static void writeFile(String fileName, String content, String encode)
	{
		OutputStreamWriter osw = null;
		try
		{
			osw = new OutputStreamWriter(new FileOutputStream(fileName, true),
					encode);
			osw.write(content);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			try
			{
				osw.flush();
				osw.close();
			}
			catch (IOException e)
			{
				LOGGER.error(StringUtils.EMPTY, e);
			}
			
		}
	}
	
	public static void main(String[] args)
	{
		System.out.println(getExtendName("1.mp3"));
	}
}
