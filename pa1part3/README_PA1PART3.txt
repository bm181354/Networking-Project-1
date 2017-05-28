Name: Biken Maharjan 
Course : Cs 455
Id : bm181354
file : README_PA1PART3



Compile:


javac filename.java


java filename <portNumber> <hostname>
or 
java filename  [Default hostname and port number will be used]

Server and Host are assigned with 58982 if no cmdline arguements are being passed.


Server.java:

Server echos message to all the connected threads[Users]. 

>>> It listens to all the threads message and echos back to all the connected threads. 
>>> If new user joins the chat then Server will echo message to all the user.
>>> If user decide to quit with "LogOut" then server echos message to all the connected users.
>>> Message send will act like group chat and user can also send private message to connected users.
     >>> Private can be send in this way: "@NAME Hello". 
     >>> <NAME> Hello  // msg recieved will be in this from 
>>> Server keeps on running even if users decide to quit the program.