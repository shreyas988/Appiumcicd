package com.qa;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestEmulator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		try {
			startEmulator();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		}

	public static void startEmulator() throws InterruptedException, IOException  {
		
		
		//Runtime.getRuntime().exec(System.getProperty("user.dir")+"//src//main//resources//startEmulator.sh");
		String path=System.getProperty("user.dir")+"/src/main/resources/startEmulator.sh";
		System.out.println(path);
			
			//Runtime.getRuntime().exec(path);
		/*	Runtime run=Runtime.getRuntime();
			Process pr=run.exec(path);
			
				pr.waitFor();
			
			BufferedReader buf= new BufferedReader(new InputStreamReader(pr.getInputStream()));
	String line = "";
	while((line=buf.readLine())!=null) {
		System.out.println(line);*/
	//}
		String[] arguments = new String[] {"sh", path};
		Process proc = new ProcessBuilder(arguments).start();
			System.out.println("Emulator has started");
			Runtime.getRuntime().exec("sh /Users/shreyas/TDD/TDDFramework/src/main/resources/startEmulator.sh");

}
	
}
