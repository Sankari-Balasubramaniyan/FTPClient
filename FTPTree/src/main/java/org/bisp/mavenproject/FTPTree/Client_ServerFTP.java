package org.bisp.mavenproject.FTPTree;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * Client_ServerFTP : Class to establish connection between client and FTP server
 * @author Sankari Balasubramaniyan 
 * 
 */

public class Client_ServerFTP {
	
	private Socket controlSocket;
	private PrintWriter commandPrinter;
	private BufferedReader commandReader;
	private BufferedReader dataReader;
	
	/**
	 * Connect to FTP server anonymously
	 * @param address : Address of the server
	 * @param port : Port number
	 * @throws Error
	 */
	
	public void controlConnect(String address, int port)throws IOException, UnknownHostException {
		controlConnect(address,port,"anonymous","anonymous");
	}

	/**
	 * controlConnect : Connect to FTP server via server command an exchange status codes
	 * @param address : Address of FTP server
	 * @param port : Port number
	 * @param username 
	 * @param password
	 * @throws Error
	 */
	
	public void controlConnect(String address,int port, String username, String password)throws IOException, UnknownHostException {
		
		controlSocket = new Socket(address,port);
		OutputStream out = controlSocket.getOutputStream();
		commandPrinter = new PrintWriter(out, true);

		InputStream in = controlSocket.getInputStream();
		InputStreamReader isr = new InputStreamReader(in);
		commandReader = new BufferedReader(isr);
		commandReader.readLine();                        

		commandPrinter.println("USER "+ username);
		String content = commandReader.readLine();    
		if(!content.equals("331 Please specify the password.")) {
			throw new Error("Wrong Username");
		}

		commandPrinter.println("PASS " + password);                 
		content = commandReader.readLine();
		if(!content.equals("230 Login successful.")) {
			throw new Error("Wrong Password");
		}
		
	}	
	
	/**
	 * dataConnect : Connect to FTP server to obtain data from given directory and store it in an Arraylist to be printed
	 * @param directory : Directory who's content has to be list
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	
	public void dataConnect(String directory)throws IOException, UnknownHostException {
		
		Ftp_Tree tree = new Ftp_Tree();
		
		if(commandPrinter == null) {
			throw new Error("Disconnection");
		}

		commandPrinter.println("CWD "+directory);
		commandReader.readLine();

		commandPrinter.println("PASV");
		String content = commandReader.readLine();
		
		String dataAddress = dataAddress(content);
		int dataPort = dataPort(content);
		Socket dataSocket = new Socket(dataAddress,dataPort);
		
		ArrayList<String> directoryList = new ArrayList<>();
		
		commandPrinter.println("LIST ");
		commandReader.readLine();
		
		InputStream in = dataSocket.getInputStream();
		InputStreamReader isr = new InputStreamReader(in);
		dataReader = new BufferedReader(isr);
		
		Main.identation++;

		String fileContent = dataReader.readLine();
		while(fileContent!=null) {
			directoryList.add(fileContent);
			fileContent = dataReader.readLine();
		}
		
		commandReader.readLine();
		
		tree.listTree(directoryList);
		
		commandPrinter.println("CWD "+"..");
		commandReader.readLine();
		Main.identation--;
		
		dataSocket.close();
	}
	
	/**
	 * Find the address of the server for Data Connection
	 * @param content : Contains the address and port details of the server sent by it
	 * @return : Address of the server
	 */
	
	public String dataAddress(String content) {
		
		String data[] = content.substring(content.indexOf('(') + 1, content.indexOf(')')).split(",");
		String dataAddress = data[0];
		for (int i = 1; i < 4; i++) {
			dataAddress = dataAddress + "." + data[i];
		}
		return dataAddress;
	}
	
	/**
	 * Find the port of the server for Data Connection
	 * @param content : Contains the address and port details of the server sent by it
	 * @return : Port which the server listens to
	 */
	
	public int dataPort(String content) {
		
		String data[] = content.substring(content.indexOf('(') + 1, content.indexOf(')')).split(",");
		int dataPort = (Integer.parseInt(data[4]) * 256) + Integer.parseInt(data[5]);
		return dataPort;
	}
	/*
	 * Close sockets
	 */
	
	public void closeSocket() throws IOException {
		
		controlSocket.close();
	}
	
	/**
	 * Close command and data pipes
	 * @throws IOException
	 */
	
	public void closeCommand() throws IOException {
		
		commandPrinter.close();
		commandReader.close();
		dataReader.close();
	}
}
