package com.example.diary

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
interface AddEmoji {
    fun addEmoji(emoji: String)
}
class EmojiAdapter (
    val addEmoji: AddEmoji,
    val emojis : List<String>

        ) : RecyclerView.Adapter<EmojiAdapter.ViewHolder>() {
    private var emojiPick : String = ""
    class ViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.emoji_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currEmoji = emojis[position]
        holder.itemView.apply {
            val emoji = this.findViewById<TextView>(R.id.tv_emoji)
            emoji.hint = currEmoji
            this.setOnClickListener {
                addEmoji.addEmoji(currEmoji)
            }
        }
    }

    override fun getItemCount(): Int {
        return emojis.size
    }
}