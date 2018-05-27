package cn.com.taiji.service.impl;

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
	public void siginIn() {
		// TODO Auto-generated method stub
		System.out.println("签到");
	}

	@Override
	public void queryClass() {
		// TODO Auto-generated method stub
		System.out.println("查询课表");
	}

	@Override
	public void callName() {
		// TODO Auto-generated method stub
		System.out.println("点名");
	}

}
