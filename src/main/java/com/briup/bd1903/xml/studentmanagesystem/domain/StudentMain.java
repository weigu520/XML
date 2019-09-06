package com.briup.bd1903.xml.studentmanagesystem.domain;


import com.briup.bd1903.xml.studentmanagesystem.dao.StudentDao;
import com.briup.bd1903.xml.studentmanagesystem.utils.StudentUtils;
import org.xml.sax.SAXException;

import javax.naming.NameNotFoundException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 描述:
 *
 * @author WeiGu
 * @create 2019-09-04 19:49
 */
public class StudentMain {
    private static BufferedReader br;
    private static StudentDao dao;
    static {
        br=new BufferedReader(new InputStreamReader(System.in));
        dao=new StudentDao();
    }
    public static void main(String[] args){
        System.out.println("请选择操作模式：1:添加用户    2：查找用户    3：删除用户   4:修改用户   5：退出系统");
//        BufferedReader br=null;
        try {
            while (true){
//                br=new BufferedReader(new InputStreamReader(System.in));
                String pattern = br.readLine();
                int parseInt = Integer.parseInt(pattern);
                switch (parseInt){
                    case 1:
                        add();
                        break;
                    case 2:
                        find();
                        break;
                    case 3:
//                        studentInList();
                        delete();
                        break;
                    case 4:
                        update();
                        break;
                    case 5:
                        exitSystem();
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void update() {
        try {
            StudentUtils.studentInList();
            dao.updateStudent();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void exitSystem() {
        System.exit(0);
    }

    private static void delete(){
        try {
            StudentUtils.studentInList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        System.out.println("请输入你要删除的学生的姓名:");
        try{
            String name=br.readLine();
            boolean del=dao.deleteStudentByName(name);
            if(del==true) {
                System.out.println("删除成功");
            }else {
                System.out.println("删除失败,请重新输入数字选择你要操作的模式");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void find() {
        System.out.println("请输入你要查询的学生的姓名:");
        try {
            String name = br.readLine();
            Student student=dao.selectStudentByName(name);
            System.out.println("学生的信息如下:");
            System.out.println(student);
            System.out.println("查询学生成功,请输入你继续要操作的功能");
        }catch (NameNotFoundException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void add() throws IOException {
        System.out.println("请输入你的学号:");
        int id=Integer.parseInt(br.readLine());
        System.out.println("请输入你的姓名:");
        String name=br.readLine();
        System.out.println("请输入你的年龄:");
        int age=Integer.parseInt(br.readLine());
        Student student = new Student();
        student.setName(name);
        student.setId(id);
        student.setAge(age);
        dao.addStudent(student);
        System.out.println("添加学生成功,请输入你继续要操作的功能");
    }
}