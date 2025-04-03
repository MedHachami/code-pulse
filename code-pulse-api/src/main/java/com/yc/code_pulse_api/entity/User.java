package com.yc.code_pulse_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String roomId;
    private String status;
    private int cursorPosition;
    private boolean typing;
    private String currentFile;
    private String socketId;

    public User(String username, String roomId, String status, int cursorPosition, boolean typing, String currentFile, String socketId) {
        this.username = username;
        this.roomId = roomId;
        this.status = status;
        this.cursorPosition = cursorPosition;
        this.typing = typing;
        this.currentFile = currentFile;
        this.socketId = socketId;
    }

    public String getUsername() { return username; }
    public String getRoomId() { return roomId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getCursorPosition() { return cursorPosition; }
    public void setCursorPosition(int cursorPosition) { this.cursorPosition = cursorPosition; }
    public boolean isTyping() { return typing; }
    public void setTyping(boolean typing) { this.typing = typing; }
    public String getSocketId() { return socketId; }
}
