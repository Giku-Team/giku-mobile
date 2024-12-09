package com.mobile.giku.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.mobile.giku.R
import java.time.LocalDate

class CalendarAdapter(private val dates: List<LocalDate>) :
    RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    private val today: LocalDate = LocalDate.now()

    inner class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: MaterialCardView = itemView.findViewById(R.id.cardItemCalendar)
        val dayText: TextView = itemView.findViewById(R.id.mtrl_calendar_days_of_week)
        val dateText: TextView = itemView.findViewById(R.id.mtrl_calendar_date_of_week)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_horizontal_calendar, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date = dates[position]

        holder.dayText.text = date.dayOfWeek.name.substring(0, 3)
        holder.dateText.text = date.dayOfMonth.toString()

        if (date == today) {
            holder.cardView.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.md_theme_primary)
            )
            holder.dateText.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.md_theme_onPrimary)
            )
            holder.dayText.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.md_theme_onPrimary)
            )
        } else {
            holder.cardView.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.md_theme_onPrimary)
            )
            holder.dateText.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.md_theme_scrim)
            )
            holder.dayText.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.md_theme_scrim)
            )
        }
    }

    override fun getItemCount(): Int = dates.size
}
