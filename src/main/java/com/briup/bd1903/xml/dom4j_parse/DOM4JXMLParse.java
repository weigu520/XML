package com.briup.bd1903.xml.dom4j_parse;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;
import org.dom4j.tree.DefaultText;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * 描述:dom4j解析
 *
 * @author WeiGu
 * @create 2019-09-04 16:56
 */
public class DOM4JXMLParse {
    public static void main(String[] args) throws DocumentException, IOException {
        SAXReader saxReader = new SAXReader();

        //整个XML文档对象
        Document document = saxReader.read(DOM4JXMLParse.class.getResourceAsStream("/students.xml"));

        //获取根节点
        Element re = document.getRootElement();
        QName qName = re.getQName();
        String name = qName.getName();
        System.out.print("<"+name+" ");

        //处理根节点的属性
        List attrs = re.attributes();
        attrs.forEach(o->{
            Attribute attr= (Attribute) o;
            String attrName = attr.getName();
            String attrValue = attr.getValue();
            System.out.print(attrName+"=\""+attrValue+"\" ");
        });

        System.out.print(">");


        String text = re.getText();
        System.out.print(text);

        //获取某个节点的所有Element子节点,
        //处理根节点的子节点
        List es = re.elements();
        es.forEach(o->{
            //e指的是student节点
            Element e= (Element) o;
            String name1 = e.getQName().getName();

            System.out.print("<"+name1+">");

            Element ex=new DefaultElement("delected");
            Text text2=new DefaultText("0");
            ex.add(text2);
            e.add(ex);


            List<Element> e1 = e.elements();
            //e1指的是id name age
            e1.forEach(o1->{
                String name2 = o1.getQName().getName();
                System.out.print("<"+name2+">");
                String text1 = o1.getText();//不获取子元素的文本值
//                String stringValue = o1.getStringValue();//获取子元素及其所有子节点的文本值
                System.out.print(text1);

                System.out.print("</"+name2+">");
            });


            System.out.println("</"+name1+">");

        });

        System.out.println("</"+name+">");

        System.out.println(document.asXML());


        OutputFormat of=new OutputFormat();
        of.setIndent(true);

        FileWriter fw = new FileWriter(new File("G:\\student.xml"));

        XMLWriter xw=new XMLWriter(fw,of);
        xw.write(document);
        xw.flush();
        xw.close();

    }
}