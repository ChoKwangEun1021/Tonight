package com.weare2024.tonight.data

data class ChatList(val myUid:String="", val yourUid:String="", val yourNickname:String="", val message:String="", val time:String="")

data class ChatList2(val users: HomeData, val message: ChatData2)
