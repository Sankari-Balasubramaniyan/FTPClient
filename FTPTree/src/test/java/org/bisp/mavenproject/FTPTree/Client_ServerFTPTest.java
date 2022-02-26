package org.bisp.mavenproject.FTPTree;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Test on address, port details
 */
public class Client_ServerFTPTest 
{
	Client_ServerFTP test = new Client_ServerFTP();
	
    /**
     * Test to check if the address obtained is correct
     * 
     */
    @Test
    public void dataAddressTest() {
    	
    	String content = "227 Entering Passive Mode (90,170,60,156,36,50).";
    	String address = test.dataAddress(content);
        assertTrue( address.equals("90.170.60.156") );
    }
    
    /**
     * Test to check if the port calculated is correct
     */
    @Test
    public void dataPortTest() {
    	
    	String content = "227 Entering Passive Mode (90,170,60,156,36,50).";
    	int port = test.dataPort(content);
    	assertTrue(port == 9266);
    }
}
