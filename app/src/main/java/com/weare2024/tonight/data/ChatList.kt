package com.weare2024.tonight.data

data class ChatList(val yourUid:String="", val yourNickname:String="", val message:String="", val time:String="")
//data class ChatRoomInfo(val roomName:String="", val yourUid:String="", val myUid:String="", val yourNickname:String="", val myNickname:String="")
data class ChatRoomInfo(val chatRoom: MutableMap<String, String>?)
data class ChatList2(val chatRoom: ChatRoomInfo, val chat: ChatData2)
