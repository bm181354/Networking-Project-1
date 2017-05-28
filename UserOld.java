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
private static BufferedReader inputLine = null;
private static boolean closed = false;
    
public static void main(String[] args) throws IOException{

      int portNumber = 58999;
      String host = "localhost";
      
      userSocket = new Socket(host,portNumber);
      inputLine = new BufferedReader(new InputStreamReader(System.in));
      output_stream = new PrintWriter(new OutputStreamWriter(userSocket.getOutputStream()));// out 
      
      Thread listener = new Thread((Runnable) input_stream); // listening thread
      listener.start();

      System.out.println(" Enter your name: ");
      extra = inputLine.readLine(); //user inputs 
      output_stream.println(extra); // send name to the server      
      output_stream.flush();
    
      while(true){  
        System.out.println("Enter any message: ");
        extra = inputLine.readLine(); //user inputs 
        output_stream.println(extra); // send message to the server      
        output_stream.flush(); 
      }


      
    }

public void run(){
    try{

        while(true){
            input_stream = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));

            message = input_stream.readLine();
            System.out.println("Message Recieved = "+ message);

            if (extra.equals(" ### Bye")){ break; }

        } // end of while loop
        
           input_stream.close();
           userSocket.close();
           output_stream.close();
           
        } catch(Exception e){
            System.out.println(e);
        }  
    
}




} // end of user
