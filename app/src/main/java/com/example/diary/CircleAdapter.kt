package com.example.diary

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

interface ChangeColor {
    fun changeColor(colorPick : String)
}
class CircleAdapter(
    val changeColor: ChangeColor,
    val colors : List<String>
) : RecyclerView.Adapter<CircleAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.color_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val color = colors[position]
        holder.itemView.apply {
            this.findViewById<CardView>(R.id.colorOption).background = ColorDrawable(Color.parseColor(color))
            this.setOnClickListener {
                changeColor.changeColor(color)
            }
        }
    }

    override fun getItemCount(): Int {
        return colors.size

    }
}