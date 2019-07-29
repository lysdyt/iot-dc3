package com.pnoker.device.virtual.server;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.Scanner;

import static com.pnoker.device.virtual.constant.ProtocolConstant.MSG_BEGIN;
import static com.pnoker.device.virtual.constant.ProtocolConstant.MSG_END;

/**
 * Copyright(c) 2019. Pnoker All Rights Reserved.
 *
 * <p>Author : Charles
 *
 * <p>Email : xinguangduan@163.com
 *
 * <p>Description: 消息接收线程
 */
@Slf4j
public class MessageReceiverThread implements Runnable {
  private Socket socket;
  private boolean started;
  private Integer port;
  private String temp = "";
  private Integer defaultPort = 8765;

  public MessageReceiverThread(int port) {
    this.port = port;
  }

  public void initServer() {
    log.info("configuration port: {},default port:{} ", port, defaultPort);
    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(port == null ? defaultPort : port);
      started = true;
      log.info("Socket服务已启动，占用端口： {}", serverSocket.getLocalPort());
    } catch (IOException e) {
      log.error("端口冲突,异常信息：{}", e);
      System.exit(0);
    }

    try {
      log.info("waiting for message...");
      socket = serverSocket.accept();
      socket.setKeepAlive(true);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }

  @Override
  public void run() {
    initServer();
    receiveWithByte();
  }

  public void receiveWithLine() {
    Scanner scanner = null;
    try {
      scanner = new Scanner(socket.getInputStream());
      while (scanner.hasNext()) {
        final String message = scanner.nextLine();
        log.info(message);
        System.out.println(message);
      }

    } catch (IOException e) {
      log.error(e.getMessage(), e);
    } finally {
      scanner.close();
    }
  }

  public void receiveWithByte() {
    try {
      StringBuffer temp = new StringBuffer();
      Reader reader = new InputStreamReader(socket.getInputStream());
      CharBuffer charbuffer = CharBuffer.allocate(8192);
      while (reader.read(charbuffer) != -1) {
        charbuffer.flip();
        temp.append(charbuffer.toString());
        if (temp.indexOf(MSG_BEGIN) != -1 && temp.indexOf(MSG_END) != -1) {
          log.info("received :{}", temp);
          temp.setLength(0);
        }
        if (temp.length() > 1024 * 16) {
          break;
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    } finally {
      if (socket != null) {
        try {
          if (!socket.isClosed()) {
            socket.close();
          }
        } catch (Exception e) {
        }
      }
    }
  }
}
