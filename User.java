import java.io.*;
import java.net.*;
import java.util.*;

public class User extends Thread {
    
private static Socket userSocket = null;
//private static PrintStream output_stream = null;
private static PrintWriter output_stream = null;
private static BufferedReader input_stream = null;
private static String message = null; 
private static String extra = null;
private static String name = null;
private static BufferedReader inputLine = null;
private static boolean closed = false;
private static Thread listener = null;   

public static void main(String[] args) throws IOException{

      // initialization and instantiation
      int portNumber = 58982;
      String host = "localhost";
      User user = new User();
    
        // for commandline argument

        if (args.length < 1){
          System.out.println("Usage: java User <portnumber> \n Now using port number= "+portNumber );
        }
        else{
          portNumber = Integer.valueOf(args[0]).intValue();
          System.out.println("Usage: java User <portnumber>\n Now using port number= "+portNumber);
        }
    
        // open socket
        userSocket = new Socket(host,portNumber);
        inputLine = new BufferedReader(new InputStreamReader(System.in));
        output_stream = new PrintWriter(new OutputStreamWriter(userSocket.getOutputStream()));// out 
    
    
        // multithreading
        user.start();

    
       // write message to server from here. If null is detected then quick the program
        while(true){

        name = inputLine.readLine(); //user inputs 
        if (!name.equals("LogOut") || name != null ){
        output_stream.println(name); // send name to the server      
        output_stream.flush();
        }
        else{ break; } 
        }
      
    }// end of main

public void run(){
    try{
        //listener here
        while(true){

            input_stream = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));
            message = input_stream.readLine();
            System.out.println(message);
            if (message.equals("LogOut")){ break; }

         } // end of while loop
        
        
           input_stream.close();
           userSocket.close();
           output_stream.close();
           
        } catch(Exception e){
            
            System.exit(0);
            
        }  
    
}




} // end of user
