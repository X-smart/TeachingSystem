package cn.com.taiji;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.com.taiji.controller.HomeController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeachingSystemApplicationTests {

	@Autowired
	private HomeController homeController;
	
	@Test
	public void attendClass() {
		homeController.attendClass();
	}
	
	@Test
	public void siginIn() {
		homeController.siginIn();
	}
	
	@Test
	public void queryClass() {
		homeController.queryClass();
	}
	
	@Test
	public void callName() {
		homeController.callName();
	}

}
