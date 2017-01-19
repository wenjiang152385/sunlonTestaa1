package com.oraro.sunlon.util;


import com.oraro.sunlon.vo.Mark;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 将XML解析成Mark点集合
 * @author 王子榕
 */
public class MarkXmlUitl {
    /**
     * 将XML解析成Mark点集合入口
     * @param inStream xml的输入流
     * @return map集合，key为设备id号，value为Mark对象
     */
    public Map<String,Mark> readXML(InputStream inStream) {
        try {
            //创建解析器
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser saxParser = spf.newSAXParser();

            //设置解析器的相关特性，true表示开启命名空间特性
            //      saxParser.setProperty("http://xml.org/sax/features/namespaces",true);
            XMLContentHandler handler = new XMLContentHandler();
            saxParser.parse(inStream, handler);
            inStream.close();

            return handler.getPersons();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    //SAX类：DefaultHandler，它实现了ContentHandler接口。在实现的时候，只需要继承该类，重载相应的方法即可。
    public class XMLContentHandler extends DefaultHandler {

        private Map<String,Mark>  markList = null;
        private Mark mark;
        private String tagName = null;//当前解析的元素标签

        public Map<String,Mark>  getPersons() {
            return markList;
        }

        //接收文档开始的通知。当遇到文档的开头的时候，调用这个方法，可以在其中做一些预处理的工作。
        @Override
        public void startDocument() throws SAXException {
            markList = new HashMap<String,Mark>();
        }

        //接收元素开始的通知。当读到一个开始标签的时候，会触发这个方法。其中namespaceURI表示元素的命名空间；
        //localName表示元素的本地名称（不带前缀）；qName表示元素的限定名（带前缀）；atts 表示元素的属性集合
        @Override
        public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {

            if(localName.equals("mark")){
                mark = new Mark();
                mark.setId(atts.getValue("id"));
            }

            this.tagName = localName;
        }

        //接收字符数据的通知。该方法用来处理在XML文件中读到的内容，第一个参数用于存放文件的内容，
        //后面两个参数是读到的字符串在这个数组中的起始位置和长度，使用new String(ch,start,length)就可以获取内容。
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {

            if(tagName!=null){
                String data = new String(ch, start, length);
                if(tagName.equals("xValue")){
                    this.mark.setxValue(Integer.parseInt(data));
                }else if(tagName.equals("yValue")){
                    this.mark.setyValue(Integer.parseInt(data));
                }else if (tagName.equals("type")){
                    this.mark.setType(Integer.parseInt(data));
                }
            }
        }

        //接收文档的结尾的通知。在遇到结束标签的时候，调用这个方法。其中，uri表示元素的命名空间；
        //localName表示元素的本地名称（不带前缀）；name表示元素的限定名（带前缀）
        @Override
        public void endElement(String uri, String localName, String name) throws SAXException {

            if (localName.equals("mark")){
                markList.put(mark.getId(),mark);
                mark = null;
            }

            this.tagName = null;
        }
    }


}
