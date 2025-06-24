package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Server {
  private ServerSocket serverSocket;

  private Set<Socket> clients;

  private Map<Socket, PrintWriter> clientWriters;

  public Server(int port) throws IOException {
    serverSocket = new ServerSocket(port, 50, InetAddress.getByName("localhost"));
    clients = new HashSet<>();
    clientWriters = new HashMap<>();
  }

  public void stop() throws IOException {
    serverSocket.close();
  }

  public void start() {
    System.out.println("Server started on: " + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort());

    new Thread(() -> handleClients()).start();

    while (true) {
      try {
        Socket clientSocket = serverSocket.accept();
        System.out.println("New connection from " + clientSocket.getRemoteSocketAddress());
        clients.add(clientSocket);
        try {
          PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
          clientWriters.put(clientSocket, writer);
        } catch (IOException e) {
          System.err.println("Failed to create PrintWriter for client " + e.getMessage());
          try {
            clientSocket.close();
          } catch (IOException e2) {
            // ignore
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void broadcast(String data) {
    Set<Socket> clientsToRemove = new HashSet<>();
    for (Socket client : clients) {
      if (client.isClosed() || client.isInputShutdown() || client.isOutputShutdown()) {
        System.out.println("Client is closed or shutdown");
        clientsToRemove.add(client);
        continue;
      }
      try {
        PrintWriter out = clientWriters.get(client);
        if (out == null) {
          System.err.println("No PrintWriter found for client " + client.getRemoteSocketAddress());
          clientsToRemove.add(client);
          continue;
        }

        System.out.println("[to " + client.getRemoteSocketAddress() + "] " + data);
        out.println(data);
        out.flush();

        if (client.getInputStream().available() > 0) {
          System.out.println("Client requested to end");
          clientsToRemove.add(client);
        }

        // check the status of printwriter
        if (out.checkError()) {
          System.err.println("PrintWriter error for client " + client.getRemoteSocketAddress());
          clientsToRemove.add(client);
        }
      } catch (Exception e) {
        e.printStackTrace();
        clientsToRemove.add(client);
      }
    }

    for (Socket client : clientsToRemove) {
      clients.remove(client);
      PrintWriter writer = clientWriters.remove(client);
      if (writer != null) {
        writer.close();
      }
      try {
        client.close();
      } catch (IOException e) {
        // ignore
      }
      System.out.println("Client " + client.getRemoteSocketAddress() + " removed");
    }
  }

  private void handleClients() {
    try {
      DataSource dataSource = new DataSource();
      while (true) {
        String data = dataSource.getNextLine();
        broadcast(data);

        Thread.sleep(500);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    Server server = null;
    try {
      server = new Server(5000);
      server.start();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (server != null) {
        try {
          server.stop();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

}
