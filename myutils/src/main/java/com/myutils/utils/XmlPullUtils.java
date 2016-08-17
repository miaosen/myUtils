package com.myutils.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import com.myutils.core.logger.L;
import com.myutils.core.RowObject;

public class XmlPullUtils {

	/**
	 * 保存对象进xml文件中
	 * @param output
	 * @param rows
	 * @throws XmlPullParserException
	 * @throws IOException 
	 * @throws IllegalStateException 
	 * @throws IllegalArgumentException 
	 */
	public static void save(OutputStream output, List<RowObject> rows)
			throws XmlPullParserException, IllegalArgumentException, IllegalStateException, IOException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlSerializer serializer = (XmlSerializer) factory.newSerializer();
		serializer.setOutput(output, "UTF-8"); // 输出编码
		serializer.startDocument("UTF-8", true);// 文档开始
		try {
			//serializer.startTag(null, "persons");
			Iterator<RowObject> iterator = rows.iterator();
			while (iterator.hasNext()) {
				RowObject row = iterator.next();
				save(row,serializer);
			}
			//serializer.endTag(null, "persons");
			serializer.endDocument();

			// 刷出数据到xml文件
			serializer.flush();

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}  catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * 保存对象进xml文件中
	 * @param output
	 * @param rows
	 * @throws XmlPullParserException
	 * @throws IOException 
	 * @throws IllegalStateException 
	 * @throws IllegalArgumentException 
	 */
	public static void save(List<RowObject> rows,XmlSerializer serializer)
			throws XmlPullParserException, IllegalArgumentException, IllegalStateException, IOException {
		try {
			Iterator<RowObject> iterator = rows.iterator();
			while (iterator.hasNext()) {
				RowObject row = iterator.next();
				save(row,serializer);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}  catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	
	public static void save(RowObject row,XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException{
		for (String key:row.keySet()) {
			Object object = row.get(key);
			serializer.startTag(null, key);
			if(object instanceof RowObject){
				RowObject row2=(RowObject) object;
				save(row2,serializer);
			}else if(object instanceof String){
				// name元素
				serializer.text(object+"");
			}
			serializer.endTag(null, key);
		}
	}
	
	public static String getString(XmlPullParser parser,String name) throws XmlPullParserException, IOException {
		int event = parser.getEventType();
		String text = null;
		String elementName="";
		while (event != XmlPullParser.END_DOCUMENT) {
			if (event == XmlPullParser.START_DOCUMENT) {
			} else if (event == XmlPullParser.START_TAG) {
				elementName = parser.getName();
			} else if (event == XmlPullParser.END_TAG) {
				elementName = parser.getName();
			} else if (event == XmlPullParser.TEXT) {
				L.i("elementName===="+elementName);
				if (elementName.equals(name)) {
					text=parser.getText();
				}
			}
			L.i("elementName===="+elementName);
			// 别忘了调用次方法进行迭代
			event = parser.next();
		}
		return text;

	}


}
