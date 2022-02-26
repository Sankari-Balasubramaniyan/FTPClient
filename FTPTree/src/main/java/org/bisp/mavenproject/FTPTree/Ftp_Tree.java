package org.bisp.mavenproject.FTPTree;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;



/**
 * Ftp_Tree : Class to print the contents of the directory in the form of tree.
 * @author Sankari Balasubramaniyan
 *
 */
public class Ftp_Tree {

	Client_ServerFTP client = Main.getClient();
	
	public static final String TEXT_RESET = "\u001B[0m";
	public static final String TEXT_RED = "\u001B[31m";
	public static final String TEXT_BLUE = "\u001B[34m";
	public static final String TEXT_CYAN = "\u001B[36m";
	
	/**
	 * listTree : Identify the type of content in directory and call the pertaining function to print 
	 * @param dlisting : An Arraylist containing all the contents of a subsequent directory
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	
	public void listTree(ArrayList<String> dlisting) throws UnknownHostException, IOException {
		
		String direc[] = new String[dlisting.size()];
		for(int i=0;i<dlisting.size();i++) {
			
			String data[] = dlisting.get(i).split(" ");
			char type = data[0].charAt(0);
			
			if(type == 'd') {
				direc[i] = data[data.length-1];
				Main.countDirectory++;
				directory(direc[i]);
			}
			else if(type == 'l') {
				direc[i] = data[data.length-3]+data[data.length-2]+data[data.length-1];
				Main.countLinks++;
				link(direc[i]);
			}
			else {
				direc[i] = data[data.length-1];
				Main.countFiles++;
				file(direc[i]);
			}
		}
	}
	
	/**
	 * directory : Prints the directory and find its contents to list
	 * @param directory 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	
	public void directory(String directory) throws UnknownHostException, IOException {
		
		for(int i=0;i<Main.identation;i++) {
			System.out.print("    |");
		}
		System.out.println("---" + TEXT_BLUE + directory + TEXT_RESET);
		client.dataConnect(directory);
	}
	
	/**
	 * link : Prints the name of the link
	 * @param file
	 */
	
	public void link(String link) {
		
		for(int i=0;i<Main.identation;i++) {
			System.out.print("    |");
		}
		System.out.println("---" + TEXT_CYAN + link + TEXT_RESET);
	}
	
	/**
	 * file : Prints the name of the file
	 * @param file
	 */
	
	public void file(String file) {
		
		for(int i=0;i<Main.identation;i++) {
			System.out.print("    |");
		}
		System.out.println("---" + TEXT_RED + file + TEXT_RESET);
	}
	
}