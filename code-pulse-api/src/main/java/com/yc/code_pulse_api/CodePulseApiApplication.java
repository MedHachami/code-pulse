package com.yc.code_pulse_api;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.corundumstudio.socketio.*;
import com.yc.code_pulse_api.entity.User;


import java.util.*;

@SpringBootApplication
public class CodePulseApiApplication {
    static final Map<String, User> userSocketMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(CodePulseApiApplication.class, args);
    }

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname("localhost");
        config.setPort(9098);
        SocketIOServer server = new SocketIOServer(config);

        server.addConnectListener(client -> {
            System.out.println("Client connected: " + client.getSessionId());
        });

        server.addDisconnectListener(client -> {
            User user = userSocketMap.remove(client.getSessionId().toString());
            if (user != null) {
                System.out.println(user.getUsername() + " disconnected");
                server.getRoomOperations(user.getRoomId()).sendEvent("user-disconnected", user);
                client.leaveRoom(user.getRoomId());
            }
        });

        registerEventListeners(server);

        server.start();
        return server;
    }

    static void registerEventListeners(SocketIOServer server) {
        server.addEventListener("join-request", Map.class, (client, data, ackSender) -> {
            String roomId = (String) data.get("roomId");
            String username = (String) data.get("username");

            boolean exists = userSocketMap.values().stream()
                    .anyMatch(u -> u.getRoomId().equals(roomId) && u.getUsername().equals(username));

            if (exists) {
                client.sendEvent("username-exists");
                return;
            }

            User user = new User(username, roomId, "online", 0, false, null, client.getSessionId().toString());
            userSocketMap.put(client.getSessionId().toString(), user);
            client.joinRoom(roomId);

            server.getRoomOperations(roomId).sendEvent("user-joined", user);
            List<User> usersInRoom = new ArrayList<>();
            userSocketMap.values().forEach(u -> {
                if (u.getRoomId().equals(roomId)) usersInRoom.add(u);
            });

            client.sendEvent("join-accepted", Map.of("user", user, "users", usersInRoom));
        });

        server.addEventListener("offline", Map.class, (client, data, ackSender) -> {
            String socketId = (String) data.get("socketId");
            User user = userSocketMap.get(socketId);
            if (user != null) {
                user.setStatus("offline");
                server.getRoomOperations(user.getRoomId()).sendEvent("offline", Map.of("socketId", socketId));
            }
        });

        server.addEventListener("online", Map.class, (client, data, ackSender) -> {
            String socketId = (String) data.get("socketId");
            User user = userSocketMap.get(socketId);
            if (user != null) {
                user.setStatus("online");
                server.getRoomOperations(user.getRoomId()).sendEvent("online", Map.of("socketId", socketId));
            }
        });

        server.addEventListener("send-message", Map.class, (client, data, ackSender) -> {
            User user = userSocketMap.get(client.getSessionId().toString());
            if (user != null) {
                server.getRoomOperations(user.getRoomId()).sendEvent("receive-message", data);
            }
        });

        server.addEventListener("typing-start", Map.class, (client, data, ackSender) -> {
            User user = userSocketMap.get(client.getSessionId().toString());
            if (user != null) {
                user.setTyping(true);
                user.setCursorPosition((Integer) data.get("cursorPosition"));
                server.getRoomOperations(user.getRoomId()).sendEvent("typing-start", Map.of("user", user));
            }
        });

        server.addEventListener("typing-pause", Map.class, (client, data, ackSender) -> {
            User user = userSocketMap.get(client.getSessionId().toString());
            if (user != null) {
                user.setTyping(false);
                server.getRoomOperations(user.getRoomId()).sendEvent("typing-pause", Map.of("user", user));
            }
        });

        registerFileStructureEvents(server);
        registerDrawingEvents(server);
    }

    private static void registerFileStructureEvents(SocketIOServer server) {
        server.addEventListener("sync-file-structure", Map.class, (client, data, ackSender) -> {
            String socketId = (String) data.get("socketId");
            server.getClient(UUID.fromString(socketId)).sendEvent("sync-file-structure", data);
        });

        String[] fileEvents = {"directory-created", "directory-updated", "directory-renamed", "directory-deleted",
                "file-created", "file-updated", "file-renamed", "file-deleted"};

        for (String event : fileEvents) {
            server.addEventListener(event, Map.class, (client, data, ackSender) -> emitToRoom(server, client, event, data));
        }
    }

    @SuppressWarnings("unchecked")
	private static void registerDrawingEvents(SocketIOServer server) {
        server.addEventListener("request-drawing", Map.class, (client, data, ackSender) -> {
            emitToRoom(server, client, "request-drawing", Map.of("socketId", client.getSessionId().toString()));
        });

        server.addEventListener("sync-drawing", Map.class, (client, data, ackSender) -> {
            String socketId = (String) data.get("socketId");
            server.getClient(UUID.fromString(socketId)).sendEvent("sync-drawing", data);
        });

        server.addEventListener("drawing-update", Map.class, (client, data, ackSender) -> {
            emitToRoom(server, client, "drawing-update", data);
        });
    }

    private static void emitToRoom(SocketIOServer server, SocketIOClient senderClient, String event, Map<String, Object> data) {
        User sender = userSocketMap.get(senderClient.getSessionId().toString());
        if (sender != null) {
            Collection<SocketIOClient> clients = server.getRoomOperations(sender.getRoomId()).getClients();
            for (SocketIOClient client : clients) {
                if (!client.getSessionId().equals(senderClient.getSessionId())) {
                    client.sendEvent(event, data);
                }
            }
        }
    }
    

  
}