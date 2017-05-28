/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * A chat server that delivers public and private messages.
 */
public class Server {

  // The server socket.
  private static ServerSocket serverSocket = null;
  // The client socket.
  private static Socket userSocket = null;

  // This chat server can accept up to maxUsersCount clients' connections.
  private static final int maxUsersCount = 10;
  private static final userThread[] threads = new userThread[maxUsersCount];

  public static void main(String args[]) {

    // The default port number.
    int portNumber = 58988;
    if (args.length < 1) {
      System.out.println("Usage: java MultiThreadChatServerSync <portNumber>\n"
          + "Now using port number=" + portNumber);
    } else {
      portNumber = Integer.valueOf(args[0]).intValue();
    }

    /*
     * Open a server socket on the portNumber (default 2222). Note that we can
     * not choose a port less than 1023 if we are not privileged users (root).
     */
    try {
      serverSocket = new ServerSocket(portNumber);
    } catch (IOException e) {
      System.out.println(e);
    }

    /*
     * Create a client socket for each connection and pass it to a new client
     * thread.
     */
    while (true) {
      try {
        userSocket = serverSocket.accept();
        int i = 0;
        for (i = 0; i < maxUsersCount; i++) {
          if (threads[i] == null) {
            (threads[i] = new userThread(userSocket, threads)).start();
            break;
          }
        }
        if (i == maxUsersCount) {
          PrintStream output_stream = new PrintStream(userSocket.getOutputStream());
          output_stream.println("Server too busy. Try later.");
          output_stream.close();
          userSocket.close();
        }
      } catch (IOException e) {
        System.out.println(e);
      }
    }
  }
}

/*
 * The chat client thread. This client thread opens the input and the output
 * streams for a particular client, ask the client's name, informs all the
 * clients connected to the server about the fact that a new client has joined
 * the chat room, and as long as it receive data, echos that data back to all
 * other clients. The thread broadcast the incoming messages to all clients and
 * routes the private message to the particular client. When a client leaves the
 * chat room this thread informs also all the clients about that and terminates.
 */
class userThread extends Thread {

  private String userName = null;
  private BufferedReader input_stream = null;
  private PrintStream output_stream = null;
  private Socket userSocket = null;
  private final userThread[] threads;
  private int maxUsersCount;

  public userThread(Socket userSocket, userThread[] threads) {
    this.userSocket = userSocket;
    this.threads = threads;
    maxUsersCount = threads.length;
  }

  public void run() {
    int maxUsersCount = this.maxUsersCount;
    userThread[] threads = this.threads;

    try {
      /*
       * Create input and output streams for this client.
       */
      input_stream = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));
      output_stream = new PrintStream(userSocket.getOutputStream());
      String name;
      while (true) {
        output_stream.println("Enter your name.");
        name = input_stream.readLine().trim();
        if (name.indexOf('@') == -1) {
          break;
        } else {
          output_stream.println("The name should not contain '@' character.");
        }
      }

      /* Welcome the new the client. */
      output_stream.println("Welcome " + name
          + " to our chat room.\nTo leave enter /quit in a new line.");
      synchronized (this) {
        for (int i = 0; i < maxUsersCount; i++) {
          if (threads[i] != null && threads[i] == this) {
            userName = "@" + name;
            break;
          }
        }
        for (int i = 0; i < maxUsersCount; i++) {
          if (threads[i] != null && threads[i] != this) {
            threads[i].output_stream.println("*** A new user " + name
                + " entered the chat room !!! ***");
          }
        }
      }
      /* Start the conversation. */
      while (true) {
        String line = input_stream.readLine();
        if (line.startsWith("/quit")) {
          break;
        }
        /* If the message is private sent it to the given client. */
        if (line.startsWith("@")) {
          String[] words = line.split("\\s", 2);
          if (words.length > 1 && words[1] != null) {
            words[1] = words[1].trim();
            if (!words[1].isEmpty()) {
              synchronized (this) {
                for (int i = 0; i < maxUsersCount; i++) {
                  if (threads[i] != null && threads[i] != this
                      && threads[i].userName != null
                      && threads[i].userName.equals(words[0])) {
                    threads[i].output_stream.println("<" + name + "> " + words[1]);
                    /*
                     * Echo this message to let the client know the private
                     * message was sent.
                     */
                    this.output_stream.println("<" + name + "> " + words[1]);
                    break;
                  }
                }
              }
            }
          }
        } else {
          /* The message is public, broadcast it to all other clients. */
          synchronized (this) {
            for (int i = 0; i < maxUsersCount; i++) {
              if (threads[i] != null && threads[i].userName != null) {
                threads[i].output_stream.println("<" + name + "> " + line);
              }
            }
          }
        }
      }
      synchronized (this) {
        for (int i = 0; i < maxUsersCount; i++) {
          if (threads[i] != null && threads[i] != this
              && threads[i].userName != null) {
            threads[i].output_stream.println("*** The user " + name
                + " is leaving the chat room !!! ***");
          }
        }
      }
      output_stream.println("*** Bye " + name + " ***");

      /*
       * Clean up. Set the current thread variable to null so that a new client
       * could be accepted by the server.
       */
      synchronized (this) {
        for (int i = 0; i < maxUsersCount; i++) {
          if (threads[i] == this) {
            threads[i] = null;
          }
        }
      }
      /*
       * Close the output stream, close the input stream, close the socket.
       */
      input_stream.close();
      output_stream.close();
      userSocket.close();
    } catch (IOException e) {
    }
  }
}