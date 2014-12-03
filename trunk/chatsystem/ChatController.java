package chatsystem;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import chatsystem.network.ChatNI;

public class ChatController {

	private ChatNI ni;
	
	private ArrayList<User> userList;
	
	private User localUser;
	
	

	//Constructors
	public ChatController(ChatNI ni) {
		this.userList = new ArrayList<User>();
		this.ni=ni;
		ni.setController(this);

	}
	
	public ChatController(){
		this.userList = new ArrayList<User>();
	}
	

	

	//from NI
	public void processHelloAck(User u){
		this.addNewToList(u);
		this.printList();
	}
	
	public void processHello(User u){
		System.out.println(u);
		System.out.println();
		if (!u.equals(u))
		{
			this.addNewToList(u);
			this.ni.sendHelloACK(u, localUser);
		}
	
	}
	
	public void processGoodbye(User u) {
		for (User us : userList) 
		{
			if (us.equals(u)){
				userList.remove(us);
				break;
			}
		}
		
	}
	
	//from GUI
	
	
	public void processConnect(String nickname){
		
		try {
			this.localUser=new User(nickname, InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			this.localUser=new User(nickname, null);
		} finally {
			System.out.println(localUser);
		}
		
		
		this.ni.sendHello(nickname);
	}
	
	public void processDisconnect() {
		this.ni.sendGoodbye(this.localUser.getName());
		
	}
	
	
	
	
	
	//Useful functions
	public void printList(){
		for (User u:userList){
			System.out.println(u);
		}
	}
	
	private void addNewToList(User u){
		int i;
		boolean appartient = false;
		for(i=0; i<userList.size(); i++){
			if (userList.get(i).equals(u)){ 
				appartient=true;
			}
		}
		if (!appartient) userList.add(u); 
	}
	
	
	
	public static void main(String[] args) {
		
		try {
						
			byte [] bytes1={(byte)192,(byte)2,(byte)10,(byte)1};
			byte [] bytes2={(byte)193,(byte)3,(byte)10,(byte)1};

			User julien = new User("jul", InetAddress.getLocalHost());
			User aurel = new User("aurel", InetAddress.getLocalHost());
			User lolo= new User("lolo",InetAddress.getByAddress(bytes2));
			
			ChatController controller=new ChatController();
			
			controller.addNewToList(aurel);
			controller.addNewToList(julien);
			controller.addNewToList(lolo);
			
			controller.printList();
			
			
			//controller.processGoodbye(lolo);
			
			//controller.printList();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		
	}








}