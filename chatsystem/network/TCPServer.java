package chatsystem.network;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

// TODO: Auto-generated Javadoc
/**
 * The Class TCPServer is aimed at receiving files.
 */
public class TCPServer extends Thread{
	
	/** The listen socket. */
	private ServerSocket listenSocket;
	
	/** The name. */
	private String name;

	/** The location. */
	private File location;
	
	/** The ni. */
	private ChatNI ni;
	
	/**
	 * Instantiates a new TCP server.
	 *
	 * @param location the location
	 * @param name the name
	 * @param ni the ni
	 */
	public TCPServer(File location,String name,ChatNI ni){
		this.location=location;
		this.name=name;
		this.ni=ni;
		try{
			listenSocket = new ServerSocket(ChatNI.NUM_PORT);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		super.run();
	    InputStream is = null;
	    FileOutputStream fos = null;
	    BufferedOutputStream bos = null;
	    Socket clientSocket=null;
	    int bufferSize = 0;
	    try{
	    	System.out.println("Avant d'accepter");
	    	while (clientSocket==null){
	    		clientSocket = listenSocket.accept();
	    	}
	    	System.out.println("Apr�s acceptation");
		    is = clientSocket.getInputStream();
	        bufferSize = clientSocket.getReceiveBufferSize();
	        //fos = new FileOutputStream("D:/GROS DESIGN/resultat");
	        String path=location.getAbsolutePath()+"/"+name;
	        File file=new File(path);
	        fos=new FileOutputStream(file);
	        bos = new BufferedOutputStream(fos);
	        byte[] bytes = new byte[bufferSize];

	        int count;

	        while ((count = is.read(bytes)) > 0) {
	            bos.write(bytes, 0, count);
	        }

	        bos.flush();
	        bos.close();
	        is.close();
	        clientSocket.close();
	        listenSocket.close();
	        ni.onFileCompleted();
	    }catch (Exception e){
			e.printStackTrace();
		}
		

	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
//		try {
//			TCPServer tcpServer = new TCPServer(new File("D:/"),"cle.txt");
//			tcpServer.start();
//			System.out.println("Thread principal : server d�marr�");
//			TCPClient tcpClient = new TCPClient(InetAddress.getLocalHost(), new File("D:/cl�ssha1.txt"));
//			tcpClient.start();
//		}catch (Exception e){
//			e.printStackTrace();
//		}

	}


	
}
