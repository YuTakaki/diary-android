package com.example.diary

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddDiaryFragment : Fragment() {

    lateinit var user : String


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_diary, container, false)
        val title = view.findViewById<EditText>(R.id.etTitle)
        val diary = view.findViewById<EditText>(R.id.etDiary)
        val saveBtn = view.findViewById<Button>(R.id.btnSaveDiary)
        saveBtn.setOnClickListener {
            val auth = Firebase.auth.currentUser
            auth?.let {
                user = it.uid
            }
            val currentTime = java.time.LocalDateTime.now()
//            val data = hashMapOf(
//                "title" to title.text.toString(),
//                "date" to Date(currentTime.toString()),
//                "diary" to diary.text.toString(),
//                "user_id" to user
//            )

            val data = DiaryModel(
                com.google.firebase.Timestamp.now(),
                diary.text.toString(),
                title.text.toString(),
                user
            )
            Firebase.firestore.collection("diary").add(data).addOnSuccessListener {
                findNavController().navigate(R.id.action_addDiaryFragment_to_FirstFragment2)
            }
        }
        return view


    }

}