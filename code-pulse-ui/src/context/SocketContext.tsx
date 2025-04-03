import { DrawingData } from "../types/app"
import {
    SocketContext as SocketContextType,
    SocketEvent,
    SocketId,
} from "../types/socket"
import { RemoteUser, USER_STATUS, User } from "../types/user"
import {
    ReactNode,
    createContext,
    useCallback,
    useContext,
    useEffect,
    useMemo,
} from "react"
import { toast } from "react-hot-toast"
import io from "socket.io-client" // ✅ socket.io-client@2.4.0 uses this
import { useAppContext } from "./AppContext"

const SocketContext = createContext<SocketContextType | null>(null)

export const useSocket = (): SocketContextType => {
    const context = useContext(SocketContext)
    if (!context) {
        throw new Error("useSocket must be used within a SocketProvider")
    }
    return context
}

const BACKEND_URL = import.meta.env.VITE_BACKEND_URL || "http://localhost:9098"

const SocketProvider = ({ children }: { children: ReactNode }) => {
    const {
        users,
        setUsers,
        setStatus,
        setCurrentUser,
        drawingData,
        setDrawingData,
    } = useAppContext()

    const socket = useMemo(
        () =>
            io(BACKEND_URL, {
                reconnectionAttempts: 2,
                transports: ['websocket'], // Optional: recommended for v2
            }),
        [],
    )

    const handleError = useCallback((err: any) => {
        console.log("socket error", err)
        setStatus(USER_STATUS.CONNECTION_FAILED)
        toast.dismiss()
        toast.error("Failed to connect to the server")
    }, [setStatus])

    const handleUsernameExist = useCallback(() => {
        toast.dismiss()
        setStatus(USER_STATUS.INITIAL)
        toast.error("The username you chose already exists in the room. Please choose a different username.")
    }, [setStatus])

    const handleJoiningAccept = useCallback(({ user, users }: { user: User; users: RemoteUser[] }) => {
        setCurrentUser(user)
        setUsers(users)
        toast.dismiss()
        setStatus(USER_STATUS.JOINED)

        if (users.length > 1) {
            toast.loading("Syncing data, please wait...")
        }
    }, [setCurrentUser, setStatus, setUsers])

    const handleUserLeft = useCallback(({ user }: { user: User }) => {
        toast.success(`${user.username} left the room`)
        setUsers(users.filter((u: User) => u.username !== user.username))
    }, [setUsers, users])

    const handleRequestDrawing = useCallback(({ socketId }: { socketId: SocketId }) => {
        socket.emit(SocketEvent.SYNC_DRAWING, { socketId, drawingData })
    }, [drawingData, socket])

    const handleDrawingSync = useCallback(({ drawingData }: { drawingData: DrawingData }) => {
        setDrawingData(drawingData)
    }, [setDrawingData])

    const handleUserJoined = useCallback(
        ({ user }: { user: RemoteUser }) => {
            console.log("Received user object:", user);
            if (!user || !user.socketId) {
                console.error("Invalid user or missing socketId");
                return;
            }
            // Handle user joined logic here
        },
        [setUsers, socket],
    );

    useEffect(() => {
        socket.on("connect_error", handleError)
        socket.on("connect_failed", handleError)
        socket.on(SocketEvent.USERNAME_EXISTS, handleUsernameExist)
        socket.on(SocketEvent.JOIN_ACCEPTED, handleJoiningAccept)
        socket.on(SocketEvent.USER_DISCONNECTED, handleUserLeft)
        socket.on(SocketEvent.REQUEST_DRAWING, handleRequestDrawing)
        socket.on(SocketEvent.SYNC_DRAWING, handleDrawingSync)

        return () => {
            socket.off("connect_error", handleError)
            socket.off("connect_failed", handleError)
            socket.off(SocketEvent.USERNAME_EXISTS, handleUsernameExist)
            socket.off(SocketEvent.JOIN_ACCEPTED, handleJoiningAccept)
            socket.off(SocketEvent.USER_DISCONNECTED, handleUserLeft)
            socket.off(SocketEvent.REQUEST_DRAWING, handleRequestDrawing)
            socket.off(SocketEvent.SYNC_DRAWING, handleDrawingSync)
        }
    }, [
        handleDrawingSync,
        handleError,
        handleJoiningAccept,
        handleRequestDrawing,
        handleUserLeft,
        handleUsernameExist,
        setUsers,
        socket,
    ])

    return (
        <SocketContext.Provider value={{ socket }}>
            {children}
        </SocketContext.Provider>
    )
}

export { SocketProvider }
export default SocketContext

