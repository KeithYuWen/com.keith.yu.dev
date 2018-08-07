package com.k12.common.socket;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class TCPServer {
	public static void main(String args[]) {
		try {
			int serverPort = 7896;
			ServerSocket listenSocket = new ServerSocket(serverPort);
			while (true) {
				Socket clientSocket = listenSocket.accept();
				Connection c = new Connection(clientSocket);
			}
		} catch (IOException e) {
			System.out.println("Listen:" + e.getMessage());
		}
	}
}

class Connection extends Thread {
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;

	public Connection(Socket aClientSocket) {
		try {
			clientSocket = aClientSocket;
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
			this.start();
		} catch (IOException e) {
			System.out.println("Connection:" + e.getMessage());
		}
		
		
		
	}

	public void run() {
		try {
			String data = in.readUTF();
			// out.writeUTF(data);
			if (data.equals("ABC")) {
				out.writeUTF("<result><!-- code: 0 - error, 1 - success --><code>0</code><weight>111.33</weight><unit>kg</unit><errmsg></errmsg></result>");
			}
		} catch (EOFException e) {
			System.out.println("EOF:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO:" + e.getMessage());
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {/* 关闭失败 */
			}
		}
	}
	
	protected void onBoReadNoTest() throws Exception {
		Socket soc = null;
		InputStreamReader isr = null;// 定义一个可读输入流
		String data = "";
		BufferedReader bf = null;// 定义一个BufferedReader类型的读内容的引用
		InetAddress addr = InetAddress.getByName("127.0.0.1");
		int serverPort = 7896;
		if (addr.isReachable(5000)) {
			System.out.println("SUCCESS - ping " + addr
					+ " with no interface specified");
			try {
				soc = new Socket(addr, serverPort);
				System.out.println("Socket Success!");
				DataInputStream in = new DataInputStream(soc.getInputStream());
				DataOutputStream out = new DataOutputStream(
						soc.getOutputStream());
				out.writeUTF("ABC");
				// BufferedReader brin = new BufferedReader(new
				// InputStreamReader(soc.getInputStream()));
				System.out.println("DataInputStream Success!");
				data = in.readUTF();
				// System.out.println("接收到的数据:" + brin.readLine());
				// isr = new InputStreamReader(soc.getInputStream());//
				// 创建一个来自套接字soc的可读输入流
				// bf = new BufferedReader(isr);//
				// 把soc的可读输入流作为参数创建一个BufferedReader
				// data = bf.readLine();// 以每行为单位读取从客户端发来的数据
				System.out.println("接收到的数据:" + data);
				this.DOM(data);
				// in.close();
			} catch (UnknownHostException e) {
				System.out.println("Socket Error:" + e.getMessage());
			} catch (EOFException e) {
				System.out.println("EOF:" + e.getMessage());
			} catch (IOException e) {
				System.out.println("IO:" + e.getMessage());
			} finally {
				if (soc != null)
					try {
						soc.close();
					} catch (IOException e) {/* close failed */
					}
			}
		} else {
			System.out.println("FAILURE - ping " + addr
					+ " with no interface specified");
		}

	}
	
	public void DOM(String data) {  
        long lasting = System.currentTimeMillis();  
  
        try {  
//            File f = new File("F:/xmltest.xml");  
//        	FileInputStream fis=new FileInputStream("data.xml");
//        	BufferedInputStream bis=new BufferedInputStream(fis);
//        	DataInputStream dis=new DataInputStream(bis);

        	byte[] b = data.getBytes();
        	InputStream inp = new ByteArrayInputStream(b);
        	
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
            DocumentBuilder builder = factory.newDocumentBuilder();  
            Document doc = (Document) builder.parse(inp);
            NodeList nl = doc.getElementsByTagName("result");  
            for (int i = 0; i < nl.getLength(); i++) {  
                System.out.println("||code:  |"+ doc.getElementsByTagName("code").item(i).getFirstChild().getNodeValue());   
                System.out.println("||weight:  |"+ doc.getElementsByTagName("weight").item(i).getFirstChild().getNodeValue());  
                System.out.println("||unit:  |"+ doc.getElementsByTagName("unit").item(i).getFirstChild().getNodeValue());  
                System.out.println("||errmsg:  |"+ doc.getElementsByTagName("errmsg").item(i).getFirstChild().getNodeValue()); 
                }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        System.out.println("DOM RUNTIME：" + (System.currentTimeMillis() - lasting) + " MS");  
    }
}
