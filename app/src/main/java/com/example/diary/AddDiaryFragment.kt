package com.example.diary

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddDiaryFragment : Fragment() , ChangeColor, AddEmoji{

    lateinit var user : String
    lateinit var views : View
    lateinit var rvEmojis : RecyclerView
    var color = "#Faa356"
    var emojiPick = ""
    private val colors = listOf("#Faa356", "#FA7970", "#FFBB86FC", "#FF03DAC5", "#7ce38b")
    private val emojis = listOf("\uD83E\uDD23", "\uD83D\uDE01", "\uD83D\uDE0D", "\uD83D\uDE12", "\uD83D\uDE1F", "\uD83D\uDE21", "\uD83E\uDD22")

    override fun addEmoji(emoji: String) {
        emojiPick = emoji
        for (i in 0 until rvEmojis.childCount){
            val holder = rvEmojis.getChildAt(i).findViewById<TextView>(R.id.tv_emoji)
            if (holder.hint == emoji) {
                holder.text = emoji
            }else{
                holder.text = ""
            }
        }
    }
    override fun changeColor(colorPick: String) {
        color = colorPick
        (activity as MainActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(color)))
        views.findViewById<ConstraintLayout>(R.id.addDiaryLayout).background = ColorDrawable(Color.parseColor(color))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        views = inflater.inflate(R.layout.fragment_add_diary, container, false)
        return views
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(color)))

        val rvColors = views.findViewById<RecyclerView>(R.id.rvColorsAdd)
        rvEmojis = views.findViewById<RecyclerView>(R.id.rv_emojis)
        rvEmojis.adapter = EmojiAdapter(this, emojis)
        rvEmojis.layoutManager = LinearLayoutManager(views.context)
        rvColors.adapter = CircleAdapter(this, colors)
        rvColors.layoutManager = LinearLayoutManager(views.context, LinearLayoutManager.HORIZONTAL, false)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.addDiary -> {
                val title = views.findViewById<EditText>(R.id.etTitle)
                val diary = views.findViewById<EditText>(R.id.etDiary)
                val auth = Firebase.auth.currentUser
                auth?.let {
                    user = it.uid
                }
                val data = DiaryModel(
                    com.google.firebase.Timestamp.now(),
                    diary.text.toString(),
                    title.text.toString(),
                    user,
                    color = color,
                    emoji = emojiPick
                )
                Firebase.firestore.collection("diary").add(data).addOnSuccessListener {
                    findNavController().navigate(R.id.action_addDiaryFragment_to_FirstFragment2)
                }


            }
            else -> Log.d("backbutton", item.itemId.toString())
        }
        return super.onOptionsItemSelected(item)
    }

}