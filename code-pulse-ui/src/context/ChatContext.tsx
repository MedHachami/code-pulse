import { ChatContext as ChatContextType, ChatMessage } from "@/types/chat"
import { SocketEvent } from "@/types/socket"
import {
    ReactNode,
    createContext,
    useContext,
    useEffect,
    useState,
} from "react"
import { useSocket } from "./SocketContext"

const ChatContext = createContext<ChatContextType | null>(null)

export const useChatRoom = (): ChatContextType => {
    const context = useContext(ChatContext)
    if (!context) {
        throw new Error("useChatRoom must be used within a ChatContextProvider")
    }
    return context
}

function ChatContextProvider({ children }: { children: ReactNode }) {
    const { socket } = useSocket()
    const [messages, setMessages] = useState<ChatMessage[]>([])
    const [isNewMessage, setIsNewMessage] = useState<boolean>(false)
    const [lastScrollHeight, setLastScrollHeight] = useState<number>(0)

    useEffect(() => {
        const handleReceiveMessage = (data: { message: ChatMessage }) => {
            if (data.message && typeof data.message === 'object') {
                setMessages(prevMessages => [...prevMessages, data.message]);
                setIsNewMessage(true);
            }
        };

        const handleMessageSentConfirmation = (data: { message: ChatMessage }) => {
            if (data.message && typeof data.message === 'object') {
                setMessages(prevMessages => [...prevMessages, data.message]);
                setIsNewMessage(true);
            }
        };

        // Remove any existing listeners first
        socket.off(SocketEvent.RECEIVE_MESSAGE);
        socket.off(SocketEvent.MESSAGE_SENT_CONFIRMATION);

        // Add listeners for both events
        socket.on(SocketEvent.MESSAGE_SENT_CONFIRMATION, handleMessageSentConfirmation);
        socket.on(SocketEvent.RECEIVE_MESSAGE, handleReceiveMessage);

        return () => {
            socket.off(SocketEvent.RECEIVE_MESSAGE);
            socket.off(SocketEvent.MESSAGE_SENT_CONFIRMATION);
        };
    }, [socket]);

    return (
        <ChatContext.Provider
            value={{
                messages,
                setMessages,
                isNewMessage,
                setIsNewMessage,
                lastScrollHeight,
                setLastScrollHeight,
            }}
        >
            {children}
        </ChatContext.Provider>
    )
}

export { ChatContextProvider }
export default ChatContext
