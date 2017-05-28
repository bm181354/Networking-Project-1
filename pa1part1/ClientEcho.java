import java.io.*;
import java.net.*;


class ClientEcho{

	public static void main(String args[]) throws Exception {

		// open the bufferedReader and initialization of variable           
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in)); //bin 
		String str = null;
		String host = "localhost";
		int portNumber = 58969;


		// for cmdline argument 		
        if (args.length < 1){
          	System.out.println("Usage: java ClientEcho <portnumber> \n Now using port number= "+ portNumber);
        }
        else if(args.length < 2){
          	portNumber = Integer.valueOf(args[0]).intValue();
         	 System.out.println("Usage: java ClientEcho <portnumber>\n Now using port number= "+ portNumber);
        }
        else {
          portNumber = Integer.valueOf(args[0]).intValue();
          System.out.println("Usage: java ClientEcho <portnumber>\n Now using port number= "+portNumber);
          host = args[1];
        }

        
        // open port and socket 
        Socket clientSocket =new Socket(host,portNumber); // soc
		PrintStream outToServer = new  PrintStream(clientSocket.getOutputStream());// out 
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //cin
		

        // message to the server that will send back to the client   
		System.out.println("Enter any message for the server:");
		str = inFromUser.readLine();
		outToServer.println(str);
		outToServer.flush();

        // message from the server
        str = inFromServer.readLine();
        // console out the echo message
        System.out.println("Message Recieved from Server [Echo] = "+str);
        // goodbye 
        System.out.println("Bye");
        
         
        // closing all the connection  
        outToServer.close();
        inFromServer.close();
		clientSocket.close();

	}	

}
