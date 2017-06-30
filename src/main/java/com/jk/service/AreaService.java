package com.jk.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.jk.model.Area;

@WebService
public interface AreaService {

	@WebMethod
	void findArea(Area area);

	
	@WebMethod
	void dom4jXml(Area area);

}
