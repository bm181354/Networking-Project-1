

// pa1part2
import java.io.*;
import java.net.*;
import java.util.*;



public class Server {


  private static ServerSocket serverSocket = null;
  private static Socket userSocket = null;
  private static final int maxUsersCount = 5;
  

  private static final userThread[] threads = new userThread[maxUsersCount];

  public static void main(String args[]) {

    // The default port number.
    int portNumber = 58982;
    // if command line is less then 1 then automatically assign default port as port.
    if (args.length < 1) {
      System.out.println("Usage: java Server <portNumber>\n" + "Now using port number= " + portNumber);

      System.out.println("Maximum user count= "+ maxUsersCount);
    } else {  // user passes port number here 
      //converting string into integer 
      portNumber = Integer.valueOf(args[0]).intValue();

    }
      //instantiating Server Socket 
    try {
      serverSocket = new ServerSocket(portNumber);
    } catch (Exception e) {
      
    }
    
    // start multithreading for all the user 
    while (true) {
      try {
        int i = 0;
        userSocket = serverSocket.accept();
        
        for (i = 0; i < maxUsersCount; i++) {
          if (threads[i] == null) {
            // create a thread and start the start method for multithreading
            threads[i] = new userThread(userSocket, threads);
            threads[i].start();
            
            break;
          }
        }
        // to establish the constraint of maximum of 5 users.
        // other user on the same will be not allowed to enter unless of the user leaves.
        if (i >= maxUsersCount) {

          // sends to the server and server will echo the message to the user.
          PrintStream output_stream = new PrintStream(userSocket.getOutputStream());
          output_stream.println("Server too busy. Try later.");
          // close the port for the user
          output_stream.close();
          userSocket.close();
        }
      } catch (IOException e) {
        
      }
    }
  } 
}// end of class Server

class userThread extends Thread {

  
  private String userName = null;
  private BufferedReader input_stream = null;
  private PrintStream output_stream = null;
  private Socket userSocket = null;
  private final userThread[] threads;
  private int maxUsersCount;

  // constructor for userThread
  public userThread(Socket userSocket, userThread[] threads) {
    this.userSocket = userSocket;
    this.threads = threads;
    maxUsersCount = threads.length;
  }
  // overwrites the run method from Thread 
  // start() will initiate the run method
  public void run() {

     // initializing the variable
     // focus on this particular thread or user's variable

    int maxUsersCount = this.maxUsersCount;
    userThread[] threads = this.threads;

    try {
          input_stream = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));
          output_stream = new PrintStream(userSocket.getOutputStream());
          String name;
      while (true) { 
           output_stream.println("Enter your name.");   // GO HERE just once if name is valid
           name = input_stream.readLine();    

        if (!name.equals("LogOut")) { // break out of the while loop and later thread will be deleted for the user

             break;

        } else {
              output_stream.println(" LogOut is Invalid name."); // or else go get the valid 
        }
      } 
      output_stream.println("Welcome " +name+ " to our chat room. \nTo leave enter LogOut in a newline.");
     
     // Using synchronized to stop any kind of error parallel threading;
      //thread[i] without synchronized can access same part of code causing error 
      synchronized (this) {
        for (int i = 0; i < maxUsersCount; i++) {
          // concatinate "@" with name for private name part.
          // userName will be use for private message 
          if (threads[i] != null && threads[i] == this) {

              userName = "@" + name;
              break;
          }
        }
        // message will be shown everytime A new user enters the chat room
        for (int i = 0; i < maxUsersCount; i++) {
          if (threads[i] != null && threads[i] != this) {

            threads[i].output_stream.println("*** A new user " + name + " entered the chat room !!! ***");
         
          }
        }
      }
     
      while (true) {
        String msg = input_stream.readLine();
        if (msg.startsWith("LogOut")) {
          break;
        }
         
         // echo message to all the users in the socket  
          synchronized (this) {
            for (int i = 0; i < maxUsersCount;  i++) {
              if (threads[i] != null && threads[i].userName != null) {
                // to all the user with the threads[i]
                threads[i].output_stream.println("<" + name + "> " + msg);
              }
            }
          }
        
      } // end of the while  

      // echo the message to all the connected user when somebody leaves 

      synchronized (this) {
        for (int i = 0; i < maxUsersCount; i++) {
            
          if (threads[i] != null && threads[i] != this && threads[i].userName != null) {

            threads[i].output_stream.println("*** The user " + name + " is leaving the chat room !!! ***");

              }
            }
        }

      // send the message to the leaving user.
      output_stream.println("### Bye " + name + " ###");

      synchronized (this) {
          // clear out the thread or leaving user
        for (int i = 0; i < maxUsersCount; i++) {
                if (threads[i] == this) {
                    // removing the thread 
                    threads[i] = null;
                  
                     }
          
             }
         }


    // closing all the circuit and sockets
      input_stream.close();
      
      userSocket.close();
      
      output_stream.close();
      
    } catch (Exception e) {
    }
  }
} // end of userThread 