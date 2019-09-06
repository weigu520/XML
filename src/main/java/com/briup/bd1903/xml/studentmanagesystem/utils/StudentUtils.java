package com.briup.bd1903.xml.studentmanagesystem.utils;

import com.briup.bd1903.xml.studentmanagesystem.domain.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * 封装操作dom文件功能方法
 *
 * @author WeiGu
 * @create 2019-09-04 18:50
 */
public class StudentUtils {
    //获取Document对象
    public static Document getDocument() throws ParserConfigurationException, IOException, SAXException {
        //创建DocumentBuilderFactory对象
        DocumentBuilderFactory dbf =DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(StudentUtils.class.getClassLoader().getResourceAsStream("students.xml"));
        return document;
    }
    //将内存中的内容写到硬盘
    public static void writeXML(Document document) throws TransformerException{
        //创建TransformerFactory对象
        TransformerFactory tff = TransformerFactory.newInstance();
        //创建Transformer对象
        Transformer tf = tff.newTransformer();
        tf.transform(new DOMSource(document),new StreamResult(new File("G:\\BD1903\\xml\\code\\src\\main\\resources\\students.xml")));
    }
    public  static void studentInList() throws IOException, SAXException, ParserConfigurationException {
        List<Student> stuList=new ArrayList<>();
        System.out.println("所有学生的信息如下:");
        Document document = StudentUtils.getDocument();
        NodeList list = document.getElementsByTagName("student");
        for(int x=0;x<list.getLength();x++){
            Element ele= (Element) list.item(x);
            if(ele.getAttribute("delete").equals("0")) {
//                if (ele.getElementsByTagName("name").item(0).getTextContent().equals("name")){
                Student stu = new Student();
                stu.setId(Integer.parseInt(ele.getAttribute("id")));
                stu.setName(ele.getElementsByTagName("name").item(0).getTextContent());
                stu.setAge(Integer.parseInt(ele.getElementsByTagName("age").item(0).getTextContent()));
                stuList.add(stu);
//                }
            }
        }
        //打印所有学生
        stuList.forEach(System.out::println);
    }
}