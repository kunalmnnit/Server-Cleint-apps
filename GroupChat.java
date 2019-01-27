import java.net.*;
import java.io.*;
import java.util.*;

public class GroupChat{
private static String Terminate="Exit";
static String name;
static volatile boolean finished = false;
public static void main(String[] args) {
		try{
			InetAddress group=InetAddress.getByName(args[0]);
			int port=Integer.parseInt(args[1]);
			Scanner sc= new Scanner(System.in);
			System.out.println("Enter your name:");
			name=sc.nextLine();
			MulticastSocket socket= new MulticastSocket(port);
			//timeToDeploy
			socket.setTimeToLive(0);
			//this on localhost only (For a subnet set it as 1);
			socket.joinGroup(group);
			Thread t= new Thread(new ReadThread(socket,group,port));
			//Start Thread
			t.start();
			// sent to the current group
			System.out.println("start typing message....\n");
			while(true){
				String message;
				message=sc.nextLine();
				if(message.equalsIgnoreCase(GroupChat.Terminate))
				{
					finished=true;
					socket.leaveGroup(group);
					socket.close();
					break;
				}
				message=name + ": " + message;
				byte[] buffer = message.getBytes();
				DatagramPacket datagram = new DatagramPacket(buffer,buffer.length,group,port);
				socket.send(datagram);
			} 
		}
		catch(IOException se){
				System.out.println("Error!");
				se.printStackTrace();

			}
	}
}
class ReadThread implements Runnable{
	private MulticastSocket socket;
	private InetAddress group;
	int port;
	private static final int MAX_LEN=1000;
	ReadThread(MulticastSocket socket,InetAddress group,int port){
		this.socket=socket;
		this.group=group;
		this.port=port;
	}
	@Override
	public void run(){
		while(!GroupChat.finished)
		{
			byte[] buffer = new byte[ReadThread.MAX_LEN];
			DatagramPacket datagram=new DatagramPacket(buffer,buffer.length,group,port);
			String message;
			try{
				socket.receive(datagram);
				message = new String(buffer,0,datagram.getLength(),"UTF-8");
				if (!message.startsWith(GroupChat.name))
					System.out.println(message);
			}
					catch(IOException e){
						System.out.println("Socket closed!");
					}
		}
	}
}
