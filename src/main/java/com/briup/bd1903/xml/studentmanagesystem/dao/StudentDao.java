package com.briup.bd1903.xml.studentmanagesystem.dao;

import com.briup.bd1903.xml.studentmanagesystem.domain.Student;
import com.briup.bd1903.xml.studentmanagesystem.utils.StudentUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import javax.naming.NameNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 描述:学生数据访问层，封装操作xml数据的方法
 *
 * @author WeiGu
 * @create 2019-09-04 19:00
 */
public class StudentDao {
    //添加学生信息
    public void addStudent(Student stu){
        try{
            Document document = StudentUtils.getDocument();
            Element student = document.createElement("student");
            student.setAttribute("id",stu.getId()+"");//给学生元素设置属性id
            student.setAttribute("delete","0");//0代表没有删除  1代表不删除

            // 分别创建姓名、年龄节点
            //姓名节点
            Element name=document.createElement("name");
            name.setTextContent(stu.getName());
            //年龄节点
            Element age=document.createElement("age");
            age.setTextContent(stu.getAge()+"");
            //文本节点
//            Text text1=document.createTextNode("\n"+"\t");
//            Text text2=document.createTextNode("\n"+"\t");
//            Text text3=document.createTextNode("\n");
            Text text4=document.createTextNode("\n");
            //将姓名年龄添加到student子节点上
//            student.appendChild(text1);
            student.appendChild(name);
//            student.appendChild(text2);
            student.appendChild(age);
//            student.appendChild(text3);

            //将student节点添加到students节点上
            document.getElementsByTagName("students").item(0).appendChild(student);
//            document.getElementsByTagName("students").item(1).appendChild(text4);
//            document.appendChild(text4);
            //写入到内存中
            StudentUtils.writeXML(document);
            System.out.println("添加学生"+stu+"成功!");

        }catch (Exception e){
        }
    }

    //根据学生姓名查询学生信息
    public Student selectStudentByName(String name) throws NameNotFoundException{
        try {
            Document document = StudentUtils.getDocument();
            NodeList list = document.getElementsByTagName("student");//所有student节点
            for (int x = 0; x < list.getLength(); x++) {
                Element ele = (Element) list.item(x);
                if (name.equals((ele.getElementsByTagName("name")).item(0).getTextContent())) {
                    Student student = new Student();
                    student.setId(Integer.parseInt(ele.getAttribute("id")));
                    student.setAge(Integer.parseInt((ele.getElementsByTagName("age")).item(0).getTextContent()));
                    student.setName(name);
                    return student;
                }
            }
            throw new NameNotFoundException(name+"不存在!"+"请重新输入数字2查询!!!");
        }catch (NameNotFoundException e){
            throw e;
        }catch (Exception e){}

        return null;
    }

    //删除学生信息
    public boolean deleteStudentByName(String name){
        try {
            Document document = StudentUtils.getDocument();
            NodeList list = document.getElementsByTagName("student");
            for(int x=0;x<list.getLength();x++){
                Element ele= (Element) list.item(x);
                if(ele.getAttribute("delete").equals("0")) {
                    if (name.equals((ele.getElementsByTagName("name")).item(0).getTextContent())) {
//                    ele.getParentNode().removeChild(ele);
                        //设置标识,不能真的删除该学生
                        ele.setAttribute("delete","1");
                        StudentUtils.writeXML(document);
                        return true;
                    }
                }
            }
        }catch (Exception e){}
        return false;
    }

    public void updateStudent() throws NameNotFoundException, IOException {
        int newAge=0;
        String newName=null;
        String newId=null;
//        System.out.println("请输入你要修改的学生的姓名加空格,修改后的姓名加空格,修改后的age加空格,修改后的id");
//        Scanner br = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        ArrayList<String> arrayList = new ArrayList<>();
//        String s = br.readLine();
//        String[] split = s.split(" ");
//        for (int x=0;x<split.length;x++) {
//            arrayList.add(split[x]);
//        }
        System.out.println("请输入你要修改的姓名");
        String name = br.readLine();
        Student student = selectStudentByName(name);
        System.out.println("你要修改的学生信息为:"+student);
        try {
            Document document = StudentUtils.getDocument();
            NodeList list = document.getElementsByTagName("student");
            for(int x=0;x<list.getLength();x++){
                Element ele= (Element) list.item(x);
                if(name.equals((ele.getElementsByTagName("name")).item(0).getTextContent())){
                    System.out.println("请输入你修改后的姓名");
                    newName = br.readLine();
                    System.out.println(newName);
                    if(ele.getElementsByTagName("name").item(0).getTextContent().equals(newName)){
                        System.out.println("你输入的姓名存在了!");
                    }else {
                        //name节点只有文本节点
                        Text text = (Text) ele.getElementsByTagName("name").item(0).getFirstChild();
                        text.setTextContent(newName);
                    }
                    System.out.println("请输入你修改后的年龄");
                    newAge = Integer.parseInt(br.readLine());
                    if(ele.getElementsByTagName("age").item(0).getTextContent().equals(String.valueOf(newAge))){
                        System.out.println("你输入的age存在了!");
                    }else {
                        //age节点只有文本节点
                        Text text1 = (Text) ele.getElementsByTagName("age").item(0).getFirstChild();
                        text1.setTextContent(String.valueOf(newAge));
                    }
                    System.out.println("请输入你修改后的id");
                    newId = br.readLine();
                    if(ele.getAttribute("id").equals(newId)){
                        System.out.println("你输入的id存在了!");
                    }else {
                        //id属性
                        ele.setAttribute("id",newId);
                    }
                    student.setAge(newAge);
                    student.setName(newName);
                    student.setId(Integer.parseInt(newId));
                    System.out.println("修改"+student.getName()+"成功");
                    StudentUtils.writeXML(document);
                }
            }
        }catch (Exception e){}
    }
}