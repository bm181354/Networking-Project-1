Name: Biken Maharjan 
Course : Cs 455
Id : bm181354
file : README_PA1PART2



Compile:


javac filename.java


java filename <portNumber> <hostname>
or 
java filename  [Default hostname and port number will be used]

Server and Host are assigned with 58982 if no cmdline arguements are being passed.


Server.java:

Server echos message to all the connected threads[Users]. 

>>> It listens to all the threads message and echos back to all the connected threads. 
>>> If new user joins the chat then Server will echo message to all the users.
>>> If user decide to quit with "LogOut" then server echos message to all the connected users.
>>> Message send will act like group chat. No private message can't be deliver in this part of the program.
>>> Server keeps on running even if users decide to quit the program.


User.java:

Sends message to the server with the help of the socket. These message will be echo back to all the connected user.

X[Minor Error in User.java]

- Program works as described but while loggin out of the chat by typing "LogOut" keyword. I'm recieving "null" in <USER> side, which doesn't affect anybody else. Everybody else recieves the message from the server that <user> is leaving as expected by the program.  
