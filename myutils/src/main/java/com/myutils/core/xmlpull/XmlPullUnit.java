package com.myutils.core.xmlpull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import com.myutils.core.RowObject;
import com.myutils.unit.file.FileModel;
import com.myutils.utils.XmlPullUtils;

public class XmlPullUnit {

	private FileModel fileModel;

	private OutputStream output;

	private XmlPullParserFactory factory;

	private XmlSerializer serializer;

	private XmlPullParser parser = null;

	/**
	 * 文件名称
	 */
	private String xmlFileName;
	
	private InputStream is;

	public XmlPullUnit(String xmlFileName) {
		this.xmlFileName = xmlFileName;
		initFileCfg();
//		initXmlSerializer();
//		initXmlPullParser();
		init();
	}
	
	

	public XmlPullUnit(InputStream is) {
		this.is = is;
		init();
	}
	
	private void init() {
		try {
			factory = XmlPullParserFactory.newInstance();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 读取xml相关初始化
	 */
	private void initXmlPullParser() {
		try {
			parser = factory.newPullParser();
			if(xmlFileName!=null){
				is = new FileInputStream(fileModel.getPath());
			}
			parser.setInput(is, "UTF-8");
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 保存xml相关初始化
	 */
	private void initXmlSerializer() {
		try {
			output = new FileOutputStream(fileModel.getPath());
			serializer = (XmlSerializer) factory.newSerializer();
			serializer.setOutput(output, "UTF-8"); // 输出编码
			serializer.startDocument("UTF-8", true);// 文档开始

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化文件配置
	 */
	private void initFileCfg() {
		fileModel = new FileModel();
		fileModel.setSuffix(".xml");
		fileModel.setNextDir("/xml");
		fileModel.setName(xmlFileName);
		fileModel.createFile();
		
	}

	/**
	 * 保存RowObject对象
	 * 
	 * @param row
	 */
	public void saveRow(RowObject row) {
		try {
			if(serializer==null){
				initXmlSerializer();
			}
			XmlPullUtils.save(row, serializer);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param keyName
	 * @param text
	 */
	public void saveString(String keyName, String text) {
		try {
			if(serializer==null){
				initXmlSerializer();
			}
			serializer.startTag(null, keyName);
			serializer.text(text);
			serializer.endTag(null, keyName);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param keyName
	 * @param text
	 */
	public String getString(String keyName) {
		String text = null;
		try {
			if (parser == null) {
				initXmlPullParser();
			}
			text = XmlPullUtils.getString(parser, keyName);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;

	}

	/**
	 * 保存List<RowObject>对象
	 * 
	 * @param row
	 */
	public void saveRows(List<RowObject> rows) {
		try {
			if(serializer==null){
				initXmlSerializer();
			}
			XmlPullUtils.save(rows, serializer);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 关, 刷出数据到xml文件
	 */
	public void close() {
		try {
			if(serializer!=null){
				serializer.endDocument();
				serializer.flush();
				output.close();
				output.flush();
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
