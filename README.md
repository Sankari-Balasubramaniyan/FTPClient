# TreeFTP 

Submitted by Sankari Balasubramaniyan

## **OBJECTIVE**

**List the directories and files present in the given path of an FTP server**

- Establish Client, server connection via TCP protocol
- Communicate commands, status codes and data between Client and Server.

## **ENVIRONMENT AND EXECUTION**

The project is written in java built using maven, hence maven is a prerequisite to run the program. 

**PARAMETERS** 

- **ADDRESS** : Address of the server, the user wishes to connect

- **PORT NUMBER** : Port number which the server listens to

- **DIRECTORY** : Path of the directory the user wishes to tree of.

By default the parameters are set as 

- **ADDRESS** : ftp.ubuntu.com

- **PORT NUMBER** : 21

- **DIRECTORY** : /

The project also runs without any parameters, hence none of the parameters are mandatory. (The Main function has to be modified just a little bit to get username and password from the user). 

**Execution examples**

1.	 java -cp ./target/FTPTree-0.0.1-SNAPSHOT.jar org.bisp.mavenproject.FTPTree.Main
2.	java -cp ./target/FTPTree-0.0.1-SNAPSHOT.jar org.bisp.mavenproject.FTPTree.Main ftp.free.fr 21 /cdimage/focal

## **PROGRAM STRUCTURE**

The project has the following classes and methods

**Main Class** : Launches the main function

1.	**main function** : gets the arguments (address, port, directory) from the user and calls to establish control and data channel. Closes the sockets and pipelines by the end of the program

2.	**getClient** : returns the Client_ServerFTP object, purpose is to maintain a single class object for the entire session.

**Client_ServerFTP Class** : establish the client to FTP server connection

1.	**controlConnect** : gets the address, port, username and password as the argument and connects to FTPserver to maintain a control channel for communicating ASCII commands and status codes.

2.	**dataConnect** : gets the name/path of the directory the user needs to be listed. Establishes a connection to the FTP server via passive mode and maintains a data channel to receive the list of files/directory sent by the server.

3.	**dataAddress** : returns the address of the server which client uses to establish data connection.

4.	**dataPort** : returns the port of server which the server listens to.

5.	**closeSocket** : close control socket.

6.	**closeCommand** : close command and data pipelines.

**FTP_Tree class** : print directories in the form of tree

1.	**listTree** : finds the type and name of given directory and calls necessary function to print.

2.	**directory** : prints the given directory name in colour blue and call dataConnect function to get the contents of the directory.

3.	**link** : prints link name maintaining indentation in colour cyan.

4.	**file** : prints file name maintaining identation in colour red.

(Colour codes are given adhereing to the ubuntu tree command).

## **CODE SNIPPETS**

```java
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
```
String dataAddress(String content) gets a content sent by the serverFTP has an argument, and return the address pertaining to the FTP server.

```java
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
```
listTree(ArrayList<String> dlisting) takes an arraylist containing the directory contents, finds the type, name and count of directory/file/link in the content and call necessary functions to print.

## **KEY DEVELOPMENT POINTS**

- The program complies with common project guidelines.
- The program works with FTP servers like ftp.ubuntu.com, ftp.free.fr etc.
- The program supports an added parameter which gets the directory path from the user and displays its tree. So any directory’s tree can be displayed.
- The program adheres to the colour code of ubuntu tree command. 

#### _The doc folder contains a brief project report and video presentation._

