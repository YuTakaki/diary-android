package com.example.diary

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import java.text.SimpleDateFormat
import java.util.*

interface SeeDiary {
    fun seeDiary(diary : DiaryModel)
}
class DiaryAdapter (
    val seeDiary: SeeDiary,
    private val options: FirestoreRecyclerOptions<DiaryModel>
) : FirestoreRecyclerAdapter<DiaryModel, DiaryAdapter.DiaryViewHolder>(options){

    class DiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        return DiaryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.diary_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int, model: DiaryModel) {
        holder.itemView.apply {
            val snapshot = getSnapshots().getSnapshot(holder.absoluteAdapterPosition)
            model.id = snapshot.getId()
            val title = this.findViewById<TextView>(R.id.tv_dl_title)
            val month = this.findViewById<TextView>(R.id.tv_month)
            val day = this.findViewById<TextView>(R.id.tv_dl_day)
            val card = this.findViewById<CardView>(R.id.diaryCard)
            title.text = "${model.title.toString()} ${model.emoji.toString()}"
            val monthformatter = SimpleDateFormat("MMM")
            val monthDateString = monthformatter.format(Date(model.date?.seconds!! * 1000L))
            val dayformatter = SimpleDateFormat("dd")
            val dayDateString = dayformatter.format(Date(model.date.seconds * 1000L))
            month.text = monthDateString
            day.text = dayDateString
            card.setCardBackgroundColor(Color.parseColor(model.color.toString()))
        }
        holder.itemView.setOnClickListener{
            seeDiary.seeDiary(model)
        }
    }
}