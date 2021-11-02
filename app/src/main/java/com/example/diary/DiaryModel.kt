package com.example.diary

import java.sql.Timestamp
import java.util.*

data class DiaryModel (
    val date : com.google.firebase.Timestamp? = null,
    val diary : String = "",
    val title : String = "",
    val user_id : String = "",
        )

data class DiaryListModel (
    val diaryList : List<DiaryModel>? = null
        )