package com.yc.code_pulse_api;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.BroadcastOperations;
import com.yc.code_pulse_api.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CodePulseApiApplicationTest {

    @Mock
    private SocketIOServer mockServer;

    @Mock
    private SocketIOClient mockClient;

    @Mock
    private BroadcastOperations mockBroadcastOperations;

    private CodePulseApiApplication application;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        application = new CodePulseApiApplication();
        when(mockClient.getSessionId()).thenReturn(UUID.randomUUID());
        when(mockServer.getRoomOperations(anyString())).thenReturn(mockBroadcastOperations);
        doNothing().when(mockBroadcastOperations).sendEvent(anyString(), any());

        User testUser = new User("testUser", "testRoom", "online", 0, false, null, mockClient.getSessionId().toString());
        CodePulseApiApplication.userSocketMap.put(mockClient.getSessionId().toString(), testUser);
    }

    // @Test
    public void testJoinRequestEventListener() throws Exception {
        // Arrange
        ArgumentCaptor<DataListener<Map>> captor = ArgumentCaptor.forClass(DataListener.class);
        doNothing().when(mockServer).addEventListener(eq("join-request"), eq(Map.class), captor.capture());

        // Act
        application.registerEventListeners(mockServer);

        // Assert
        DataListener<Map> listener = captor.getValue();
        assertNotNull(listener);

        // Simulate a join-request event
        Map<String, String> data = Map.of("roomId", "testRoom", "username", "testUser");
        listener.onData(mockClient, data, null);

        // Verify that the client joins the room
        verify(mockClient).joinRoom("testRoom");
    }

    @Test
    public void testOfflineEventListener() throws Exception {
        // Arrange
        ArgumentCaptor<DataListener<Map>> captor = ArgumentCaptor.forClass(DataListener.class);
        doNothing().when(mockServer).addEventListener(eq("offline"), eq(Map.class), captor.capture());

        // Act
        application.registerEventListeners(mockServer);

        // Assert
        DataListener<Map> listener = captor.getValue();
        assertNotNull(listener);

        // Simulate an offline event
        Map<String, String> data = Map.of("socketId", mockClient.getSessionId().toString());
        listener.onData(mockClient, data, null);

        verify(mockBroadcastOperations).sendEvent(eq("offline"), any());
    }

    @Test
    public void testOnlineEventListener() throws Exception {
        // Arrange
        ArgumentCaptor<DataListener<Map>> captor = ArgumentCaptor.forClass(DataListener.class);
        doNothing().when(mockServer).addEventListener(eq("online"), eq(Map.class), captor.capture());

        // Act
        application.registerEventListeners(mockServer);

        // Assert
        DataListener<Map> listener = captor.getValue();
        assertNotNull(listener);

        // Simulate an online event
        Map<String, String> data = Map.of("socketId", mockClient.getSessionId().toString());
        listener.onData(mockClient, data, null);

        verify(mockBroadcastOperations).sendEvent(eq("online"), any());
    }

    @Test
    public void testSendMessageEventListener() throws Exception {
        // Arrange
        ArgumentCaptor<DataListener<Map>> captor = ArgumentCaptor.forClass(DataListener.class);
        doNothing().when(mockServer).addEventListener(eq("send-message"), eq(Map.class), captor.capture());

        // Act
        application.registerEventListeners(mockServer);

        // Assert
        DataListener<Map> listener = captor.getValue();
        assertNotNull(listener);

        // Simulate a send-message event
        Map<String, String> data = Map.of("message", "Hello World");
        listener.onData(mockClient, data, null);

        // Verify that the message is broadcasted
        verify(mockBroadcastOperations).sendEvent(eq("receive-message"), eq(data));
    }

    @Test
    public void testTypingStartEventListener() throws Exception {
        // Arrange
        ArgumentCaptor<DataListener<Map>> captor = ArgumentCaptor.forClass(DataListener.class);
        doNothing().when(mockServer).addEventListener(eq("typing-start"), eq(Map.class), captor.capture());

        // Act
        application.registerEventListeners(mockServer);

        // Assert
        DataListener<Map> listener = captor.getValue();
        assertNotNull(listener);

        // Simulate a typing-start event
        Map<String, Object> data = Map.of("cursorPosition", 5);
        listener.onData(mockClient, data, null);

        verify(mockBroadcastOperations).sendEvent(eq("typing-start"), any());
    }

    @Test
    public void testTypingPauseEventListener() throws Exception {
        // Arrange
        ArgumentCaptor<DataListener<Map>> captor = ArgumentCaptor.forClass(DataListener.class);
        doNothing().when(mockServer).addEventListener(eq("typing-pause"), eq(Map.class), captor.capture());

        // Act
        application.registerEventListeners(mockServer);

        // Assert
        DataListener<Map> listener = captor.getValue();
        assertNotNull(listener);

        // Simulate a typing-pause event
        Map<String, Object> data = Map.of();
        listener.onData(mockClient, data, null);

        // Verify that the typing-pause event is sent
        verify(mockBroadcastOperations).sendEvent(eq("typing-pause"), any());
    }

    @Test
    public void testEventListenerRegistration() {
        // Arrange
        ArgumentCaptor<DataListener<Map>> captor = ArgumentCaptor.forClass(DataListener.class);
        doNothing().when(mockServer).addEventListener(anyString(), eq(Map.class), captor.capture());

        // Act
        application.registerEventListeners(mockServer);

        // Assert
        assertEquals(18, captor.getAllValues().size(), "All event listeners should be registered");
    }
} 