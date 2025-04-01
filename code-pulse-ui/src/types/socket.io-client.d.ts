declare module 'socket.io-client' {
    interface Socket {
        on(event: string, callback: (...args: any[]) => void): this;
        off(event: string, callback?: (...args: any[]) => void): this;
        emit(event: string, ...args: any[]): this;
        connect(): this;
        disconnect(): this;
    }

    export default function io(url: string, options?: any): Socket;
}
