package chatsystem.network;

import chatsystem.User;

public interface FromRemote {

	public void onHelloReceived(User u);
	
	public void onHelloAckReceived(User u);
	
	public void onGoodbyeReceived(User u);

}
