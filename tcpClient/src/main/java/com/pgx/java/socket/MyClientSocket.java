package com.pgx.java.socket;// A Java program for a Client



import java.net.*;
import java.io.*;

public class MyClientSocket
{
    // initialize socket and input output streams
    private Socket socket		 = null;
    private DataInputStream input = null;
    private DataOutputStream out	 = null;

    final String secretKey = "secret";
    // constructor to put ip address and port
    public MyClientSocket(String address, int port)
    {
        // establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
            input = new DataInputStream(System.in);

            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }

        // string to read message from input
        String line = "";

        // keep reading until "Over" is input
        while (!line.equals("Over"))
        {
            try
            {
                line = input.readLine();
                //encrypt input
                final String encryptedLine = EncryptDecryptService.encrypt(line,secretKey);
                System.out.println("LINE AFTER ENCRYPT " + encryptedLine);
                out.writeUTF(encryptedLine);
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }

        // close the connection
        try
        {
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public static void main(String args[])
    {
        MyClientSocket client = new MyClientSocket("127.0.0.1", 5000);
    }
} 
