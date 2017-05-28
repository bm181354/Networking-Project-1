
import java.io.*;
import java.net.*;


class ServerEcho{
    
    public static void main(String args[]) throws Exception {
        
        // default port number
        int portNumber = 58969;
        
        if (args.length < 1){
            System.out.println("Usage: java ServerEcho <portnumber> \n Now using port number= "+ portNumber);
        }
        else {
            // for cmdline argument
            portNumber = Integer.valueOf(args[0]).intValue();
            System.out.println("Usage: java ServerEcho <portnumber>\n Now using port number= "+ portNumber);
        }

        
        // open socket, buffereReader and initialization of variables.
        ServerSocket welcomeSocket = new ServerSocket(portNumber);
        BufferedReader bin = null;
        String str = null;
        Socket connectionSocket = welcomeSocket.accept();
        bin = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Client joined:"+ connectionSocket);
        
        
        PrintStream outToClient = new  PrintStream (connectionSocket.getOutputStream());
        
        try{
        
           // sends back recieve message back to the sender [echos it back]
        do{
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            // message recieved from client
            str = inFromClient.readLine();
            
            if(str != null){
                
                // echo message to the sender
                outToClient.println("<User>: "+ str);
                outToClient.flush();
            
            }
            else { System.out.println("User Left");}
            
            
        } while(!(str.equals("Close")));
        }catch(Exception ee) {
           // break;
        }
        
        // closing all the connection
        connectionSocket.close();
        outToClient.close();
        
    }
    
}

