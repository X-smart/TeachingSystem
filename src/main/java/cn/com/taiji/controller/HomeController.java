package cn.com.taiji.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.com.taiji.service.StudyService;

@Controller
public class HomeController {

	@Autowired
	private StudyService studyService;
	
	public void  attendClass() {
		studyService.attendClass();
	};
	
	public void  siginIn() {
		studyService.siginIn();
	};
	
	public void  queryClass() {
		studyService.queryClass();
	};
	
	public void  callName() {
		studyService.callName();
	};
}