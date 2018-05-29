package cn.com.taiji.view;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.taiji.controller.HomeController;

@Component
public class DisplayMenu {

	@Autowired
	private HomeController homeController;
	
	public void displayMenu() {
		
		int sc;
		while(true) {
			homeController.viewMenu();
			sc =  new Scanner(System.in).nextInt();
			while (sc == 1) {
				String siginIn = homeController.siginIn();
				if (siginIn.equals("n")) {
					break;
				}
				homeController.viewMenu();
			}
			if (sc == 2) {
				homeController.callName();
			}
			while (sc == 3) {
				String queryClass = homeController.queryClass();
				if (queryClass.equals("n")) {
					break;
				}
				homeController.viewMenu();
			}
		}
	}
}
