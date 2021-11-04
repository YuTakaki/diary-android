package com.example.diary

import com.google.firebase.Timestamp

data class DiaryModel(
    val date: Timestamp? = null,
    val diary: String = "",
    val title: String = "",
    val user_id: String = "",
    var id: String = "",
    var color: String = "",
    var emoji : String = "",
        )
