//java program for Server
import java.net.*;
import java.io.*;

public class Server{
 //initialize socket and streams
	private Socket socket=null;
	private DataInputStream input=null;
	private ServerSocket server=null;
	//Constructor with port
	public Server(int port){
		//starts server and waits for connection
		try{
			server= new ServerSocket(port);
			System.out.println("Server Started");
			System.out.println("Waiting for a client...");
			socket=server.accept();
			System.out.println("Client accepted");
			//takes input fro clien socket
			input=new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			String line="";
			//read message from client until over is sent
			while(!line.equals("over")){
				try{
					line=input.readUTF();
					System.out.println(line);
				}
				catch(IOException i){
					System.out.println(i);
				}
			}
			System.out.println("Closing Connection");
			//close connection
			socket.close();
			input.close();
		}
		catch(IOException i){
			System.out.println(i);

		}
	}
	 public static void main(String args[])
    {
        Server server = new Server(5000);
    }
}