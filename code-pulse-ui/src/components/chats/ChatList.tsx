"use client"

import { useAppContext } from "@/context/AppContext"
import { useChatRoom } from "@/context/ChatContext"
import { type SyntheticEvent, useEffect, useRef } from "react"
import { FaUser } from "react-icons/fa6";


function ChatList() {
  const { messages, isNewMessage, setIsNewMessage, lastScrollHeight, setLastScrollHeight } = useChatRoom()
  const { currentUser } = useAppContext()
  const messagesContainerRef = useRef<HTMLDivElement | null>(null)

  const handleScroll = (e: SyntheticEvent) => {
    const container = e.target as HTMLDivElement
    setLastScrollHeight(container.scrollTop)
  }

  // Scroll to bottom when messages change
  useEffect(() => {
    if (!messagesContainerRef.current) return
    messagesContainerRef.current.scrollTop = messagesContainerRef.current.scrollHeight
  }, [messages])

  useEffect(() => {
    if (isNewMessage) {
      setIsNewMessage(false)
    }
    if (messagesContainerRef.current) messagesContainerRef.current.scrollTop = lastScrollHeight
  }, [isNewMessage, setIsNewMessage, lastScrollHeight])

  return (
    <div
      className="flex-grow overflow-auto rounded-md bg-darkHover p-2"
      ref={messagesContainerRef}
      onScroll={handleScroll}
    >
      {/* Chat messages */}
      {messages.map((message, index) => {
        return (
          <div
            key={index}
            className={
              "mb-2 w-[70%] self-start break-words rounded-md bg-dark px-3 py-2" +
              (message.username === currentUser.username ? " ml-auto " : "")
            }
            style={{'marginRight':'65px'}}
          >
             <div className="flex items-center gap-2 py-1"
             
             >
              <FaUser 
                size={16}
                className="text-gray-400 hover:text-[#8e51ff] cursor-pointer transition-colors flex-shrink-0"
              />
              <p>{message.message}</p>
            </div>
            <div className="flex justify-between">
              <span className="text-xs text-primary">{message.username}</span>
              <span className="text-xs text-white">{message.timestamp}</span>
            </div>
           
          </div>
        )
      })}
    </div>
  )
}

export default ChatList

