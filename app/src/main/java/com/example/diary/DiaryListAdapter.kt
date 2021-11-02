package com.example.diary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

interface SeeDiary {
    fun seeDiary(diary : DiaryModel)
}

class DiaryListAdapter (
    val seeDiary: SeeDiary,
    val diaries : MutableList<DiaryModel>
        ): RecyclerView.Adapter<DiaryListAdapter.ViewHolder>() {

            class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.diary_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val diary = diaries[position]
        holder.itemView.apply {
            val sample = this.findViewById<TextView>(R.id.sample)
            sample.text = diary.diary
        }
        holder.itemView.setOnClickListener{
            seeDiary.seeDiary(diary)
        }
    }

    override fun getItemCount(): Int {
        return diaries.size
    }
}