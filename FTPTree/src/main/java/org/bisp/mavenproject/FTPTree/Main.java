package org.bisp.mavenproject.FTPTree;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Main class
 * @author Sankari Balasubramaniyan
 *
 */

public class Main {
	
	static Client_ServerFTP client = new Client_ServerFTP();
	static int identation = 0;
	static int countDirectory = 0;
	static int countLinks = 0;
	static int countFiles = 0;
	
	/**
	 * Main function to get address, port, directory to list of the server and call necessary functions.
	 * @param args : String array containing address, port, directory details given by user.
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	
	public static void main(String args[])throws IOException, UnknownHostException {
		String defaultAddress =  "ftp.ubuntu.com";
		int defaultPort = 21;
		String defaultDirectory = "/";
		int n = args.length;
		if(n==1) {
			defaultAddress = args[0];
		}
		else if(n==2) {
			defaultPort = Integer. parseInt(args[1]);
		}
		else if(n==3) {
			defaultDirectory = args[2];
		}

		System.out.println(defaultDirectory.substring(defaultDirectory.lastIndexOf('/') + 1, defaultDirectory.length()));
		client.controlConnect(defaultAddress, defaultPort);
		client.dataConnect(defaultDirectory);
		
		System.out.println();
		System.out.println(countDirectory + " directories ," + countLinks + " links ," + countFiles + " files");
		
		client.closeSocket();
		client.closeCommand();
	}
	/**
	 * getter function
	 * @return object if type CLient_ServerFTP
	 */
	public static Client_ServerFTP getClient() { return client;}
}


