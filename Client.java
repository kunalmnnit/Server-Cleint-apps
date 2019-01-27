import java.net.*;
import java.util.*;
import java.io.*;

public class Client{
	//initialize socket and input-output stream
	private Socket socket=null;
	private DataOutputStream out = null;
	private DataInputStream input = null;
	// constructor to put ip address and port
	public Client(String address,int port){
		//establish a connection
		try{
			socket=new Socket(address,port);
			System.out.println("Connected");
			//takes input from terminal
			input=new DataInputStream(System.in);
			//sends output to the socket
			out = new DataOutputStream(socket.getOutputStream());
		}
		catch(UnknownHostException e){
			System.out.println(e);
		}
		catch(IOException i){
			System.out.println(i);
		}
		//string to read message from input
		String line="";
		//keep reading until "over" is input
		while(!line.equals("Over"))
		{
			try{
				line=input.readLine();
				out.writeUTF(line);
			}
			catch(IOException i)
			{
				System.out.println(i);
			}
		}
		//close the connection
		try{
			input.close();
			out.close();
			socket.close();
		}
		catch(IOException i){
			System.out.println(i);
		}
	}
	public static void main(String[] args) {
		Client client=new Client("127.0.0.1",5000);
	}
}