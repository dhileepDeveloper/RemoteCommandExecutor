package com.scopeInternational.rca.executor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.scopeInternational.rca.Model.Commander;

public class CommandExecutor {

	public static final String SERVER_URI = "http://localhost:8080/RemoteControlActivity";
	private static final int CMD_TIMEOUT = 5000;

	ArrayList outList = new ArrayList();

	@Autowired
	private RestTemplate restTemplate;

	public void execute()
	{
		
		while(true)
		{
			try{
				
			
				Commander commander = restTemplate.getForObject(SERVER_URI+"/getCommand", Commander.class);
				System.out.println("CommandExecute has started");
				if(commander != null)
				{
					int waitedTime = 0;
					String command = commander.getCommand();
					System.out.println("command is " + command);
					if(command != null)
					{
						Runtime runtime = Runtime.getRuntime();
						BufferedReader bufferedReader = null;
						InputStream inputStream = null;
						try {
							Process process = runtime.exec(command);
							System.out.println("Before process");
							System.out.println("After process");
							inputStream = process.getInputStream();
							bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
							String line = null;
							while(true)
							{
								
								if(bufferedReader.ready())
								{
									while((line=bufferedReader.readLine())!=null)
									{
										outList.add(line);
									}
								}
								if(outList.size() > 0 || waitedTime >= CMD_TIMEOUT)
								{	
									break;
								}
								else
								{
									waitedTime += 100;
									System.out.println("BufferedReader is ready for command " + command);
									System.out.println("waitedTime is " + waitedTime);
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
						} 
						finally
						{
							try {
								if(bufferedReader != null)
									bufferedReader.close();
								if(inputStream != null)
									inputStream.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
				else
				{
					outList.clear();
				}
				System.out.println("outList is " + outList);
				if(outList.size() > 0)
					transmit();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			catch(ResourceAccessException ex)
			{
				//ex.printStackTrace();
				System.out.println("Please check whether manager is up or not......");
				System.out.println("Going to sleep for 10 seconds...... bye ");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void transmit() {
		Commander commander = new Commander();
		String output = outList.toString();
		commander.setOutList(outList);
		commander.setTextArea(output);
		restTemplate.postForObject(SERVER_URI + "/processOutput", commander, Commander.class);
	}
}
