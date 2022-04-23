package ui.smartpro.appselect.utils

import androidx.recyclerview.widget.DiffUtil
import ui.smartpro.data.entities.Movie

class DiffUtilCallBack : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
                && oldItem.display_title == newItem.display_title
                && oldItem.summary_short == newItem.summary_short
    }
}