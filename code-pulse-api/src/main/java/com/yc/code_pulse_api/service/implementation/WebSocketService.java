package com.yc.code_pulse_api.service.implementation;

import com.yc.code_pulse_api.entity.enums.SocketEvent;
import com.yc.code_pulse_api.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class WebSocketService extends TextWebSocketHandler {

    // private final ObjectMapper objectMapper = new ObjectMapper();
    // private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    // private final List<User> userSocketMap = new ArrayList<>();

    // @Override
    // public void afterConnectionEstablished(WebSocketSession session) {
    //     sessions.put(session.getId(), session);
    // }

    // @Override
    // protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    //     Map<String, Object> payload = objectMapper.readValue(message.getPayload(), Map.class);
    //     String event = (String) payload.get("event");

    //     if (event == null) {
    //         return;
    //     }

    //     if (event.equals(SocketEvent.JOIN_REQUEST.getValue())) {
    //         handleJoinRequest(session, payload);
    //     } else if (event.equals(SocketEvent.SYNC_FILE_STRUCTURE.getValue())) {
    //         handleSyncFileStructure(payload);
    //     } else if (event.equals(SocketEvent.DIRECTORY_CREATED.getValue())) {
    //         handleDirectoryCreated(session, payload);
    //     } else if (event.equals(SocketEvent.DIRECTORY_UPDATED.getValue())) {
    //         handleDirectoryUpdated(session, payload);
    //     } else if (event.equals(SocketEvent.DIRECTORY_RENAMED.getValue())) {
    //         handleDirectoryRenamed(session, payload);
    //     } else if (event.equals(SocketEvent.DIRECTORY_DELETED.getValue())) {
    //         handleDirectoryDeleted(session, payload);
    //     } else if (event.equals(SocketEvent.FILE_CREATED.getValue())) {
    //         handleFileCreated(session, payload);
    //     } else if (event.equals(SocketEvent.FILE_UPDATED.getValue())) {
    //         handleFileUpdated(session, payload);
    //     } else if (event.equals(SocketEvent.FILE_RENAMED.getValue())) {
    //         handleFileRenamed(session, payload);
    //     } else if (event.equals(SocketEvent.FILE_DELETED.getValue())) {
    //         handleFileDeleted(session, payload);
    //     } else if (event.equals(SocketEvent.USER_OFFLINE.getValue())) {
    //         handleUserOffline(payload);
    //     } else if (event.equals(SocketEvent.USER_ONLINE.getValue())) {
    //         handleUserOnline(payload);
    //     } else if (event.equals(SocketEvent.SEND_MESSAGE.getValue())) {
    //         handleSendMessage(session, payload);
    //     } else if (event.equals(SocketEvent.TYPING_START.getValue())) {
    //         handleTypingStart(session, payload);
    //     } else if (event.equals(SocketEvent.TYPING_PAUSE.getValue())) {
    //         handleTypingPause(session);
    //     } else if (event.equals(SocketEvent.REQUEST_DRAWING.getValue())) {
    //         handleRequestDrawing(session);
    //     } else if (event.equals(SocketEvent.SYNC_DRAWING.getValue())) {
    //         handleSyncDrawing(payload);
    //     } else if (event.equals(SocketEvent.DRAWING_UPDATE.getValue())) {
    //         handleDrawingUpdate(session, payload);
    //     }
    // }

    // @Override
    // public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    //     User user = getUserBySessionId(session.getId());
    //     if (user != null) {
    //         String roomId = user.getRoomId();
    //         broadcastToRoom(roomId, SocketEvent.USER_DISCONNECTED, Map.of("user", user), session.getId());
    //         userSocketMap.removeIf(u -> u.getSessionId().equals(session.getId()));
    //     }
    //     sessions.remove(session.getId());
    // }

    // private void handleJoinRequest(WebSocketSession session, Map<String, Object> payload) throws IOException {
    //     String roomId = (String) payload.get("roomId");
    //     String username = (String) payload.get("username");

    //     // Check if username exists in the room
    //     boolean isUsernameExists = getUsersInRoom(roomId).stream()
    //             .anyMatch(u -> u.getUsername().equals(username));

    //     if (isUsernameExists) {
    //         sendToSession(session, SocketEvent.USERNAME_EXISTS, new HashMap<>());
    //         return;
    //     }

    //     User user = new User(
    //             username,
    //             roomId,
    //             User.USER_CONNECTION_STATUS.ONLINE,
    //             0,
    //             false,
    //             null,
    //             session.getId()
    //     );

    //     userSocketMap.add(user);
    //     broadcastToRoom(roomId, SocketEvent.USER_JOINED, Map.of("user", user), session.getId());
    //     List<User> users = getUsersInRoom(roomId);
    //     sendToSession(session, SocketEvent.JOIN_ACCEPTED, Map.of("user", user, "users", users));
    // }

    // private void handleSyncFileStructure(Map<String, Object> payload) throws IOException {
    //     Map<String, Object> fileStructure = (Map<String, Object>) payload.get("fileStructure");
    //     List<Object> openFiles = (List<Object>) payload.get("openFiles");
    //     Object activeFile = payload.get("activeFile");
    //     String socketId = (String) payload.get("socketId");

    //     WebSocketSession targetSession = sessions.get(socketId);
    //     if (targetSession != null) {
    //         sendToSession(targetSession, SocketEvent.SYNC_FILE_STRUCTURE, Map.of(
    //                 "fileStructure", fileStructure,
    //                 "openFiles", openFiles,
    //                 "activeFile", activeFile
    //         ));
    //     }
    // }

    // private void handleDirectoryCreated(WebSocketSession session, Map<String, Object> payload) throws IOException {
    //     String parentDirId = (String) payload.get("parentDirId");
    //     Object newDirectory = payload.get("newDirectory");
    //     String roomId = getRoomId(session.getId());
        
    //     if (roomId != null) {
    //         broadcastToRoom(roomId, SocketEvent.DIRECTORY_CREATED, Map.of(
    //                 "parentDirId", parentDirId,
    //                 "newDirectory", newDirectory
    //         ), session.getId());
    //     }
    // }

    // private void handleDirectoryUpdated(WebSocketSession session, Map<String, Object> payload) throws IOException {
    //     String dirId = (String) payload.get("dirId");
    //     List<Object> children = (List<Object>) payload.get("children");
    //     String roomId = getRoomId(session.getId());
        
    //     if (roomId != null) {
    //         broadcastToRoom(roomId, SocketEvent.DIRECTORY_UPDATED, Map.of(
    //                 "dirId", dirId,
    //                 "children", children
    //         ), session.getId());
    //     }
    // }

    // private void handleDirectoryRenamed(WebSocketSession session, Map<String, Object> payload) throws IOException {
    //     String dirId = (String) payload.get("dirId");
    //     String newName = (String) payload.get("newName");
    //     String roomId = getRoomId(session.getId());
        
    //     if (roomId != null) {
    //         broadcastToRoom(roomId, SocketEvent.DIRECTORY_RENAMED, Map.of(
    //                 "dirId", dirId,
    //                 "newName", newName
    //         ), session.getId());
    //     }
    // }

    // private void handleDirectoryDeleted(WebSocketSession session, Map<String, Object> payload) throws IOException {
    //     String dirId = (String) payload.get("dirId");
    //     String roomId = getRoomId(session.getId());
        
    //     if (roomId != null) {
    //         broadcastToRoom(roomId, SocketEvent.DIRECTORY_DELETED, Map.of(
    //                 "dirId", dirId
    //         ), session.getId());
    //     }
    // }

    // private void handleFileCreated(WebSocketSession session, Map<String, Object> payload) throws IOException {
    //     String parentDirId = (String) payload.get("parentDirId");
    //     Object newFile = payload.get("newFile");
    //     String roomId = getRoomId(session.getId());
        
    //     if (roomId != null) {
    //         broadcastToRoom(roomId, SocketEvent.FILE_CREATED, Map.of(
    //                 "parentDirId", parentDirId,
    //                 "newFile", newFile
    //         ), session.getId());
    //     }
    // }

    // private void handleFileUpdated(WebSocketSession session, Map<String, Object> payload) throws IOException {
    //     String fileId = (String) payload.get("fileId");
    //     String newContent = (String) payload.get("newContent");
    //     String roomId = getRoomId(session.getId());
        
    //     if (roomId != null) {
    //         broadcastToRoom(roomId, SocketEvent.FILE_UPDATED, Map.of(
    //                 "fileId", fileId,
    //                 "newContent", newContent
    //         ), session.getId());
    //     }
    // }

    // private void handleFileRenamed(WebSocketSession session, Map<String, Object> payload) throws IOException {
    //     String fileId = (String) payload.get("fileId");
    //     String newName = (String) payload.get("newName");
    //     String roomId = getRoomId(session.getId());
        
    //     if (roomId != null) {
    //         broadcastToRoom(roomId, SocketEvent.FILE_RENAMED, Map.of(
    //                 "fileId", fileId,
    //                 "newName", newName
    //         ), session.getId());
    //     }
    // }

    // private void handleFileDeleted(WebSocketSession session, Map<String, Object> payload) throws IOException {
    //     String fileId = (String) payload.get("fileId");
    //     String roomId = getRoomId(session.getId());
        
    //     if (roomId != null) {
    //         broadcastToRoom(roomId, SocketEvent.FILE_DELETED, Map.of(
    //                 "fileId", fileId
    //         ), session.getId());
    //     }
    // }

    // private void handleUserOffline(Map<String, Object> payload) throws IOException {
    //     String socketId = (String) payload.get("socketId");
    //     userSocketMap.forEach(user -> {
    //         if (user.getSessionId().equals(socketId)) {
    //             user.setStatus(User.USER_CONNECTION_STATUS.OFFLINE);
    //         }
    //     });
        
    //     String roomId = getRoomId(socketId);
    //     if (roomId != null) {
    //         broadcastToRoom(roomId, SocketEvent.USER_OFFLINE, Map.of("socketId", socketId), socketId);
    //     }
    // }

    // private void handleUserOnline(Map<String, Object> payload) throws IOException {
    //     String socketId = (String) payload.get("socketId");
    //     userSocketMap.forEach(user -> {
    //         if (user.getSessionId().equals(socketId)) {
    //             user.setStatus(User.USER_CONNECTION_STATUS.ONLINE);
    //         }
    //     });
        
    //     String roomId = getRoomId(socketId);
    //     if (roomId != null) {
    //         broadcastToRoom(roomId, SocketEvent.USER_ONLINE, Map.of("socketId", socketId), socketId);
    //     }
    // }

    // private void handleSendMessage(WebSocketSession session, Map<String, Object> payload) throws IOException {
    //     Object message = payload.get("message");
    //     String roomId = getRoomId(session.getId());
        
    //     if (roomId != null) {
    //         broadcastToRoom(roomId, SocketEvent.RECEIVE_MESSAGE, Map.of("message", message), session.getId());
    //     }
    // }

    // private void handleTypingStart(WebSocketSession session, Map<String, Object> payload) throws IOException {
    //     Integer cursorPosition = (Integer) payload.get("cursorPosition");
        
    //     userSocketMap.forEach(user -> {
    //         if (user.getSessionId().equals(session.getId())) {
    //             user.setTyping(true);
    //             user.setCursorPosition(cursorPosition);
    //         }
    //     });
        
    //     User user = getUserBySessionId(session.getId());
    //     if (user != null) {
    //         String roomId = user.getRoomId();
    //         broadcastToRoom(roomId, SocketEvent.TYPING_START, Map.of("user", user), session.getId());
    //     }
    // }

    // private void handleTypingPause(WebSocketSession session) throws IOException {
    //     userSocketMap.forEach(user -> {
    //         if (user.getSessionId().equals(session.getId())) {
    //             user.setTyping(false);
    //         }
    //     });
        
    //     User user = getUserBySessionId(session.getId());
    //     if (user != null) {
    //         String roomId = user.getRoomId();
    //         broadcastToRoom(roomId, SocketEvent.TYPING_PAUSE, Map.of("user", user), session.getId());
    //     }
    // }

    // private void handleRequestDrawing(WebSocketSession session) throws IOException {
    //     String roomId = getRoomId(session.getId());
    //     if (roomId != null) {
    //         broadcastToRoom(roomId, SocketEvent.REQUEST_DRAWING, Map.of("socketId", session.getId()), session.getId());
    //     }
    // }

    // private void handleSyncDrawing(Map<String, Object> payload) throws IOException {
    //     Object drawingData = payload.get("drawingData");
    //     String socketId = (String) payload.get("socketId");
        
    //     WebSocketSession targetSession = sessions.get(socketId);
    //     if (targetSession != null) {
    //         sendToSession(targetSession, SocketEvent.SYNC_DRAWING, Map.of("drawingData", drawingData));
    //     }
    // }

    // private void handleDrawingUpdate(WebSocketSession session, Map<String, Object> payload) throws IOException {
    //     Object snapshot = payload.get("snapshot");
    //     String roomId = getRoomId(session.getId());
        
    //     if (roomId != null) {
    //         broadcastToRoom(roomId, SocketEvent.DRAWING_UPDATE, Map.of("snapshot", snapshot), session.getId());
    //     }
    // }

    // // Helper methods
    // private List<User> getUsersInRoom(String roomId) {
    //     return userSocketMap.stream()
    //             .filter(user -> user.getRoomId().equals(roomId))
    //             .collect(Collectors.toList());
    // }

    // private String getRoomId(String sessionId) {
    //     for (User user : userSocketMap) {
    //         if (user.getSessionId().equals(sessionId)) {
    //             return user.getRoomId();
    //         }
    //     }
    //     return null;
    // }

    // private User getUserBySessionId(String sessionId) {
    //     return userSocketMap.stream()
    //             .filter(user -> user.getSessionId().equals(sessionId))
    //             .findFirst()
    //             .orElse(null);
    // }

    // private void sendToSession(WebSocketSession session, SocketEvent event, Map<String, Object> data) throws IOException {
    //     if (session.isOpen()) {
    //         Map<String, Object> payload = new HashMap<>(data);
    //         payload.put("event", event.getValue());
    //         session.sendMessage(new TextMessage(objectMapper.writeValueAsString(payload)));
    //     }
    // }

    // private void broadcastToRoom(String roomId, SocketEvent event, Map<String, Object> data, String excludeSessionId) {
    //     userSocketMap.stream()
    //             .filter(user -> user.getRoomId().equals(roomId) && !user.getSessionId().equals(excludeSessionId))
    //             .forEach(user -> {
    //                 WebSocketSession session = sessions.get(user.getSessionId());
    //                 if (session != null && session.isOpen()) {
    //                     try {
    //                         sendToSession(session, event, data);
    //                     } catch (IOException e) {
    //                         e.printStackTrace();
    //                     }
    //                 }
    //             });
    // }
}