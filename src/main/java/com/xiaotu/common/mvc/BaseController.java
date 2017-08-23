package com.xiaotu.common.mvc;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("base")
public class BaseController
{
	@RequestMapping(value = "/forward/{path}/{page}")
	public ModelAndView forward(@PathVariable String path,
			@PathVariable String page, HttpServletRequest request,
			ModelAndView mv, ModelMap modelMap)
	{
		modelMap.putAll(this.getParams(request));
		mv.setViewName(path + "/" + page);
		return mv;
	}
	
	protected Map<String, Object> getParams(HttpServletRequest request)
	{
		Map<String, Object> paraMap = new HashMap<String, Object>();
		Enumeration<String> nameEnumeration = request.getParameterNames();
		if (nameEnumeration != null)
		{
			while (nameEnumeration.hasMoreElements())
			{
				String key = nameEnumeration.nextElement();
				paraMap.put(key, request.getParameter(key));
			}
		}
		return paraMap;
	}
}
