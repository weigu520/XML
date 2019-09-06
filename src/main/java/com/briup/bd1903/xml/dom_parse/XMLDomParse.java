package com.briup.bd1903.xml.dom_parse;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 描述:
 * XML文档的DOM解析
 *
 * @author WeiGu
 * @create 2019-09-04 14:31
 */
public class XMLDomParse {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        //1.创建DocumentBuilderFactory工厂对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //2.创建DocumentBuilder对象
        DocumentBuilder builder = factory.newDocumentBuilder();

        //3.获取和整个XML文档相关的Document对象
        InputStream is = XMLDomParse.class.getClassLoader().getResourceAsStream("pom.xml");
        if(is==null) return;
        Document document = builder.parse(is);

//        System.out.println(document);

        //4.获取XML文档的根元素
        Element de = document.getDocumentElement();
//        System.out.println(d);

        //增删该查
        Element haha=document.createElement("haha");


        de.appendChild(haha);


        parse(de);

        TransformerFactory tff=TransformerFactory.newInstance();

        Transformer tf = tff.newTransformer();

        DOMSource ds = new DOMSource(document);
        StreamResult sr = new StreamResult(new File("G:\\pom.xml"));
        tf.transform(ds,sr);
//        String deTagName = de.getTagName();
//
//        System.out.print("<"+deTagName+" ");
//
//        //属性
//        NamedNodeMap deAttrs = de.getAttributes();
//        int length=deAttrs.getLength();
//        for(int x=0;x<length;x++){
//            Attr attr= (Attr) deAttrs.item(x);
//            String attrName = attr.getName();
//            String attrValue = attr.getValue();
//            System.out.print(attrName+"=\""+attrValue+"\""+((x==length-1)?"":" "));
//        }
//
//        //根标签的起始标记
//        System.out.println(">");
//
//        //根元素的子元素,指的是project元素的子元素
//        NodeList cnl = de.getChildNodes();
//        for(int x=0;x<cnl.getLength();x++){
//            Node node = cnl.item(x);
//            if(node.getNodeType()==Node.ELEMENT_NODE){
//                System.out.println(node);
//            }
//        }
//
//
//        //根标签的结束标签
//        System.out.println("<"+deTagName+">");

    }

    public static void parse(Node node){
        //如果是Document节点对象,则获取根节点对象,再传递至该方法
        if(node.getNodeType()==Node.DOCUMENT_NODE){
            Document doc= (Document) node;
            Element de = doc.getDocumentElement();
            parse(de);
        }
        //如果是Element节点对象,打印节点名称,打印属性,还要获取子节点
        if(node.getNodeType()==Node.ELEMENT_NODE){
            Element e= (Element) node;
            String tagName = e.getTagName();

            if("groupId".equals(tagName)){
                Node parentNode = e.getParentNode();
                parentNode.removeChild(e);
            }
            if("modelVersion".equalsIgnoreCase(tagName)){
                e.getChildNodes().item(0).setNodeValue("5.0");
            }


            System.out.print("<"+tagName+(e.hasAttributes()?" ":""));

            NamedNodeMap attrs = e.getAttributes();
            int length=attrs.getLength();
            for(int x=0;x<attrs.getLength();x++){
                Attr attr = (Attr) attrs.item(x);
                String attrName = attr.getName();
                String value = attr.getValue();
                System.out.print(attrName+"="+"\""+value+"\""+((x==length-1)?"":" "));
            }
            System.out.print(">");


            NodeList nls = e.getChildNodes();

            for(int x=0;x<nls.getLength();x++){
                Node node1 = nls.item(x);
                parse(node1);
            }

            System.out.println("</"+tagName+">");

            if(node.getNodeType()==Node.TEXT_NODE){
                Text text= (Text) node;
                String value = text.getWholeText();
                System.out.print(value);
            }

        }
    }
}