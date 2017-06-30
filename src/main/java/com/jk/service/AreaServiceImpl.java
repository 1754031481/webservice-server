package com.jk.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jk.dao.AreaMapper;
import com.jk.model.Area;
import com.mysql.jdbc.TimeUtil;

@Service("areaService")
@WebService
public class AreaServiceImpl implements AreaService{
	
	@Autowired
	private AreaMapper areaMapper;
	
	

	/**
	 * 将数据库查询出来的数据通过拼接生成xml文件
	 */
	@Override
	public void findArea(Area area) {
		List<Area> areaList = areaMapper.findArea(area);
		StringBuffer sb=new StringBuffer();
		if(areaList!=null && areaList.size()>0){
				 sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	             sb.append("<nodes>");
	             for (int i = 0; i < areaList.size(); i++) {
	            	 Area area1 = areaList.get(i);
	            	 sb.append("<area>");
	            	 sb.append("<id>"  + area1.getId()+ "</id>");
	            	 sb.append("<createDate>" + area1.getCreateDate()+ "</createDate>");
	            	 sb.append("<modifyDate>" + area1.getModifyDate()+ "</modifyDate>");
	            	 sb.append("<orders>" + area1.getOrders()+ "</orders>");
	            	 sb.append("<fullName>" + area1.getFullName()+ "</fullName>");
	            	 sb.append("<name>" + area1.getName()+ "</name>");
	            	 sb.append("<treePath>" + area1.getTreePath()+ "</treePath>");
	            	 sb.append("<parent>" + area1.getParent()+ "</parent>");
	            	 sb.append("</area>");
				}
	             sb.append("</nodes>");
		}
		try {
			Document document = DocumentHelper.parseText(sb.toString());
			writer(document);
			System.out.println("服务端调用成功，并且完美生成了xml文件，哈哈");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将数据库查询出来的数据通过dom4j生成xml
	 */
	@Override
	public void dom4jXml(Area area) {
		List<Area> areaList = areaMapper.findArea(area);
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
		Document document = DocumentHelper.createDocument(); //创建document对象
		 Element root = document.addElement("node"); // 创建根节点 
	     for (Area area2 : areaList) { //循环没一个area
	    	 Element addElement = root.addElement("area");//创建根节点下的子节点
	    	 
	    	 Element id = addElement.addElement("id");  //  子节点的属性 字段名展示
		     id.setText(area2.getId().toString()); 
		     
		     Element createDate = addElement.addElement("createDate");  
		     createDate.setText(sim.format(area2.getCreateDate())); 
		     
		     Element modifyDate = addElement.addElement("modifyDate");  
		     modifyDate.setText(sim.format(area2.getModifyDate())); 
		     
		     Element orders = addElement.addElement("orders");
		     if(null!=area2.getOrders()){
		    	 orders.setText(area2.getOrders().toString());
		     }else{
		    	 orders.setText("");
		     }
		     
		     Element fullName = addElement.addElement("fullName");  
		     fullName.setText(area2.getFullName()); 
		     
		     Element name = addElement.addElement("name");  
		     name.setText(area2.getName()); 
		     
		     Element treePath = addElement.addElement("treePath");  
		     treePath.setText(area2.getTreePath()); 
		     
		     Element parent = addElement.addElement("parent");  
		     if(null!=area2.getOrders()){
		    	 parent.setText(area2.getParent().toString()); 
		     }else{
		    	 parent.setText("");
		     }
		    
		}
		try {
			writer(document);// 输出
			System.out.println("服务端调用成功，并且完美生成了xml文件，dom4j");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("你是傻逼么，这都不对");
		}
	}
	/** 
     * 把document对象写入新的文件 
     *  
     * @param sb 
     * @throws Exception 
     */  
	public void writer(Document document) throws Exception {  
        // 紧凑的格式  
        // OutputFormat format = OutputFormat.createCompactFormat();  
        // 排版缩进的格式  
        OutputFormat format = OutputFormat.createPrettyPrint();  
        // 设置编码  
        format.setEncoding("UTF-8");  
        // 创建XMLWriter对象,指定了写出文件及编码格式  
        // XMLWriter writer = new XMLWriter(new FileWriter(new  
        // File("src//a.xml")),format);  
        XMLWriter writer = new XMLWriter(new OutputStreamWriter(  
                new FileOutputStream(new File("F://diqu.xml")), "UTF-8"), format);  
        // 写入  
        writer.write(document);  
        // 立即写入  
        writer.flush();  
        // 关闭操作  
        writer.close();  
    }

}
