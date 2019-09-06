package com.briup.bd1903.xml.sax_parse;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

/**
 * 描述:
 * Sax解析
 *
 * @author WeiGu
 * @create 2019-09-04 16:21
 */
public class XMLSaxParse {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        //1.获取SAXParseFactory对象
        SAXParserFactory f = SAXParserFactory.newInstance();
        f.setNamespaceAware(true);//打开命名空间解释
        //2.获取SAXParser对象
        SAXParser saxParser = f.newSAXParser();

        class MyHandler extends DefaultHandler{
            @Override
            public void startDocument() throws SAXException {
                System.out.println("开始解析文档!");
            }

            @Override
            public void endDocument() throws SAXException {
                System.out.println("文档解析结束!");
            }

            @Override
            public void startElement(String uri,            //当前标签的命名空间内容
                                     String localName,      //标签名
                                     String qName,          //带命名空间前缀的标签名
                                     Attributes attributes  //当前标签的属性
            ) throws SAXException {

                int attrLength = attributes.getLength();
                System.out.print("<"+localName+(attrLength==0?"":" "));
                for(int x=0;x<attrLength;x++){
                    String attrName = attributes.getQName(x);
                    String attrValue = attributes.getValue(x);
                    System.out.print(attrName+"=\""+attrValue+"\"");
                }
                System.out.print(">");


//                System.out.println("uri="+uri);
//                System.out.println("localName="+localName);
//                System.out.println("qName="+qName);
//                System.out.println("attributes="+attributes);
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
//                System.out.println("结束解析"+localName+"标签!");
                System.out.println("</"+localName+">");
            }


            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                System.out.print(new String(ch,start,length));
            }
        }

        //3.解析
        saxParser.parse(XMLSaxParse.class.getClassLoader().getResourceAsStream("students.xml"),new MyHandler());
    }
}