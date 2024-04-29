package com.weare2024.tonight.data

data class ChatList(val yourUid:String="", val yourNickname:String="", val message:String="", val time:String="")
//data class ChatRoomInfo(val roomName:String="", val yourUid:String="", val myUid:String="", val yourNickname:String="", val myNickname:String="")
data class ChatRoomInfo(val chatRoomInfo: ChatRoom)
data class ChatRoom(val roomName:String="", val yourUid:String="", val myUid:String="", val yourNickname:String="", val myNickname:String="", val lastMessage:String="")
data class ChatList2(val chatRoom: ChatRoom, val chat: ChatData2)

data class ChatRoom1(val users:MutableMap<String, Boolean>? = mutableMapOf())
