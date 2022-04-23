package ui.smartpro.appselect.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import ui.smartpro.appselect.R
import ui.smartpro.appselect.databinding.MovieItemBinding
import ui.smartpro.appselect.utils.DiffUtilCallBack
import ui.smartpro.common.utils.useCoilToLoadPhoto
import ui.smartpro.data.entities.Movie

class MovieAdapter :
    PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(
        MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { movie ->
            holder.bindPost(movie)
        }
    }

    inner class MovieViewHolder(private val vb: MovieItemBinding) :
        RecyclerView.ViewHolder(vb.root) {
        fun bindPost(movie: Movie) {
            with(movie) {
                src.let {
                    useCoilToLoadPhoto(
                        itemView.context,
                        vb.imagePost,
                        R.drawable.placeholder_image,
                        it
                    )
                }
                vb.displayTitle.text = display_title
                vb.summaryShort.text = summary_short
            }
        }
    }
}