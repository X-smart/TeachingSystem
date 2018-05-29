package cn.com.taiji.service.impl;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import cn.com.taiji.service.StudyService;

@Service
public class StudyServiceImpl implements StudyService {

	@Override
	public void attendClass() {
		// TODO Auto-generated method stub
		System.out.println("上课中。。。");
	}

	@Override
	public String siginIn() {
		// TODO Auto-generated method stub
		System.out.println("请输入签到姓名：");
		String name = new Scanner(System.in).next();
		System.out.println("输入n退出签到");
		String what = new Scanner(System.in).next();
		if (what.equals("n")) {
			return what;
		}
		return "0";
	}

	@Override
	public String queryClass() {
		// TODO Auto-generated method stub
		System.out.println("请输入要查询课表：");
		String classname = new Scanner(System.in).next();
		System.out.println("输入n退出查询");
		String what = new Scanner(System.in).next();
		if (what.equals("n")) {
			return what;
		}
		return "0";
		
	}

	@Override
	public void callName() {
		// TODO Auto-generated method stub
		System.out.println("请学号为："+(int)(Math.random()*29+1)+"的同学回答问题。");
	}

	@Override
	public void viewMenu() {
		System.out.println("--------------------");
		System.out.println("----教务系统欢迎您----");
		System.out.println("----请选择您的服务：--");
		System.out.println("----1.签到----------");
		System.out.println("----2.点名----------");
		System.out.println("----3.查询课表-------");
		System.out.println("--------------------");
	}
	}


