package com.xiaotu.common.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import sun.misc.BASE64Encoder;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EMail
{
	private static final Logger LOGGER = LoggerFactory.getLogger(EMail.class);
	
	public final static String SMTP = "smtp.mxhichina.com";
	
	public final static String USER = "mail.from.user";
	public final static String PASSWORD = "mail.from.password";
	
	private MimeMessage mimeMsg; // MIME邮件对象
	private Session session; // 邮件会话对象
	private Properties props; // 系统属性
	@SuppressWarnings("unused")
	private boolean needAuth = false; // smtp是否需要认证
	// smtp认证用户名和密码
	private String username;
	private String password;
	private Multipart mp; // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象
	
	/**
	 * Constructor
	 * @param smtp 邮件发送服务器
	 */
	public EMail(String smtp)
	{
		setSmtpHost(smtp);
		createMimeMessage();
	}
	
	/**
	 * 设置邮件发送服务器
	 * @param hostName String
	 */
	public void setSmtpHost(String hostName)
	{
		LOGGER.debug("设置系统属性：mail.smtp.host = " + hostName);
		if (props == null)
			props = System.getProperties(); // 获得系统属性对象
		props.put("mail.smtp.host", hostName); // 设置SMTP主机
	}
	
	/**
	 * 创建MIME邮件对象
	 * @return
	 */
	public boolean createMimeMessage()
	{
		try
		{
			LOGGER.debug("准备获取邮件会话对象！");
			session = Session.getDefaultInstance(props, null); // 获得邮件会话对象
		}
		catch (Exception e)
		{
			LOGGER.error("获取邮件会话对象时发生错误！", e);
			return false;
		}
		
		LOGGER.debug("准备创建MIME邮件对象！");
		try
		{
			mimeMsg = new MimeMessage(session); // 创建MIME邮件对象
			mp = new MimeMultipart();
			
			return true;
		}
		catch (Exception e)
		{
			LOGGER.error("创建MIME邮件对象失败！", e);
			return false;
		}
	}
	
	/**
	 * 设置SMTP是否需要验证
	 * @param need
	 */
	public void setNeedAuth(boolean need)
	{
		LOGGER.debug("设置smtp身份认证：mail.smtp.auth = " + need);
		if (props == null)
			props = System.getProperties();
		if (need)
		{
			props.put("mail.smtp.auth", "true");
		}
		else
		{
			props.put("mail.smtp.auth", "false");
		}
	}
	
	/**
	 * 设置用户名和密码
	 * @param name
	 * @param pass
	 */
	public void setNamePass(String name, String pass)
	{
		username = name;
		password = pass;
	}
	
	/**
	 * 设置邮件主题
	 * @param mailSubject
	 * @return
	 */
	public boolean setSubject(String mailSubject)
	{
		LOGGER.debug("设置邮件主题！");
		try
		{
			mimeMsg.setSubject(mailSubject);
			return true;
		}
		catch (Exception e)
		{
			LOGGER.error("设置邮件主题发生错误！");
			return false;
		}
	}
	
	/**
	 * 设置邮件正文
	 * @param mailBody String
	 */
	public boolean setBody(String mailBody)
	{
		try
		{
			BodyPart bp = new MimeBodyPart();
			bp.setContent("" + mailBody, "text/html;charset=GBK");
			mp.addBodyPart(bp);
			
			return true;
		}
		catch (Exception e)
		{
			LOGGER.error("设置邮件正文时发生错误！", e);
			return false;
		}
	}
	
	/**
	 * 添加附件
	 * @param filename String
	 */
	public boolean addFileAffix(String filename)
	{
		
		LOGGER.debug("增加邮件附件：" + filename);
		if (StringUtils.isBlank(filename))
		{
			return true;
		}
		try
		{
			BodyPart bp = new MimeBodyPart();
			FileDataSource fileds = new FileDataSource(filename);
			bp.setDataHandler(new DataHandler(fileds));
			bp.setFileName(fileds.getName());
			BASE64Encoder enc = new BASE64Encoder();
			bp.setFileName("=?GBK?B?" + enc.encode(fileds.getName().getBytes())
					+ "?=");
			mp.addBodyPart(bp);
			
			return true;
		}
		catch (Exception e)
		{
			LOGGER.error("增加邮件附件：" + filename + "发生错误！", e);
			return false;
		}
	}
	
	/**
	 * 设置发信人
	 * @param from String
	 */
	public boolean setFrom(String from)
	{
		LOGGER.debug("设置发信人！");
		try
		{
			mimeMsg.setFrom(new InternetAddress(from)); // 设置发信人
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	/**
	 * 设置收信人
	 * @param to String
	 */
	public boolean setTo(String to)
	{
		if (to == null)
			return false;
		try
		{
			mimeMsg.setRecipients(RecipientType.TO, InternetAddress.parse(to));
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	/**
	 * 设置抄送人
	 * @param copyto String
	 */
	public boolean setCopyTo(String copyto)
	{
		if (StringUtils.isBlank(copyto))
			return true;
		try
		{
			mimeMsg.addRecipients(RecipientType.CC,
					InternetAddress.parse(copyto));
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	/**
	 * 发送邮件
	 */
	public boolean sendOut()
	{
		try
		{
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			LOGGER.debug("正在发送邮件....");
			
			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect((String) props.get("mail.smtp.host"), username,
					password);
			transport.sendMessage(mimeMsg,
					mimeMsg.getRecipients(RecipientType.TO));
			// transport.sendMessage(mimeMsg,mimeMsg.getRecipients(RecipientType.CC));
			// transport.send(mimeMsg);
			
			LOGGER.debug("发送邮件成功！");
			transport.close();
			
			return true;
		}
		catch (Exception e)
		{
			LOGGER.error("邮件发送失败！", e);
			return false;
		}
	}
	
	/**
	 * 调用sendOut方法完成邮件发送
	 * @param smtp
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public static boolean send(String smtp, String from, String to,
			String subject, String content, String username, String password)
	{
		EMail theMail = new EMail(smtp);
		theMail.setNeedAuth(true); // 需要验证
		
		if (!theMail.setSubject(subject))
			return false;
		if (!theMail.setBody(content))
			return false;
		if (!theMail.setTo(to))
			return false;
		if (!theMail.setFrom(from))
			return false;
		theMail.setNamePass(username, password);
		
		if (!theMail.sendOut())
			return false;
		return true;
	}
	
	/**
	 * 调用sendOut方法完成邮件发送,带抄送
	 * @param smtp
	 * @param from
	 * @param to
	 * @param copyto
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public static boolean sendAndCc(String smtp, String from, String to,
			String copyto, String subject, String content, String username,
			String password)
	{
		EMail theMail = new EMail(smtp);
		theMail.setNeedAuth(true); // 需要验证
		
		if (!theMail.setSubject(subject))
			return false;
		if (!theMail.setBody(content))
			return false;
		if (!theMail.setTo(to))
			return false;
		if (!theMail.setCopyTo(copyto))
			return false;
		if (!theMail.setFrom(from))
			return false;
		theMail.setNamePass(username, password);
		
		if (!theMail.sendOut())
			return false;
		return true;
	}
	
	/**
	 * 调用sendOut方法完成邮件发送,带附件
	 * @param smtp
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @param filename 附件路径
	 * @return
	 */
	public static boolean send(String smtp, String from, String to,
			String subject, String content, String username, String password,
			String filename)
	{
		EMail theMail = new EMail(smtp);
		theMail.setNeedAuth(true); // 需要验证
		
		if (!theMail.setSubject(subject))
			return false;
		if (!theMail.setBody(content))
			return false;
		if (!theMail.addFileAffix(filename))
			return false;
		if (!theMail.setTo(to))
			return false;
		if (!theMail.setFrom(from))
			return false;
		theMail.setNamePass(username, password);
		
		if (!theMail.sendOut())
			return false;
		return true;
	}
	
	/**
	 * 调用sendOut方法完成邮件发送,带附件和抄送
	 * @param smtp
	 * @param from
	 * @param to
	 * @param copyto
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @param filename
	 * @return
	 */
	public static boolean sendAndCc(String smtp, String from, String to,
			String copyto, String subject, String content, String username,
			String password, String filename)
	{
		EMail theMail = new EMail(smtp);
		theMail.setNeedAuth(true); // 需要验证
		
		if (!theMail.setSubject(subject))
			return false;
		if (!theMail.setBody(content))
			return false;
		if (!theMail.addFileAffix(filename))
			return false;
		if (!theMail.setTo(to))
			return false;
		if (!theMail.setCopyTo(copyto))
			return false;
		if (!theMail.setFrom(from))
			return false;
		theMail.setNamePass(username, password);
		
		if (!theMail.sendOut())
			return false;
		return true;
	}
	
	/**
	 * 发送任务邮件
	 * @param content
	 */
	public static void sendTaskDetail(String content)
	{
		String smtp = "smtp.mxhichina.com";
		String from = "chenjiaxing@trinityearth.com.cn";
		String to = "chenjiaxing@trinityearth.com.cn";
		String copyto = "";
		String subject = "爬虫任务提醒邮件";
		String username = "chenjiaxing@trinityearth.com.cn";
		String password = "a1230123,";
		String filename = "";
		EMail.sendAndCc(smtp, from, to, copyto, subject, content, username,
				password, filename);
	}
	
	public static void main(String[] args)
	{
		String smtp = "smtp.mxhichina.com";
		String from = "chenjiaxing@trinityearth.com.cn";
		String to = "liuhongliang@trinityearth.com.cn";
		String copyto = "";
		String subject = "爬虫任务提醒邮件";
		String content = "测试邮件内容";
		String username = "chenjiaxing@trinityearth.com.cn";
		String password = "Chenjiaxing*";
		String filename = "";
		EMail.sendAndCc(smtp, from, to, copyto, subject, content, username,
				password, filename);
	}
}