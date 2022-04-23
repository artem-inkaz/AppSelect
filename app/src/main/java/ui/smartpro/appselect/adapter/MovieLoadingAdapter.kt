package ui.smartpro.appselect.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import ui.smartpro.appselect.databinding.ItemLoadingStateBinding

class MovieLoadingAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<MovieLoadingAdapter.LoadingStateViewHolder>() {

    class LoadingStateViewHolder(private val vb: ItemLoadingStateBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(vb.root) {
        init {
            vb.btnRetry.setOnClickListener {
                retry()
            }
        }

        /**
         * 3 Функция с именем bindState, которая принимает LoadState в качестве аргумента
         * LoadState — это закрытый класс, который может иметь любое из следующих состояний:
         * NotLoading: Загрузка данных не происходит и ошибок нет
         * Загрузка: данные загружаются.
         * Ошибка: Получение данных завершается с ошибкой.
         */

        fun bindState(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                vb.tvErrorMessage.text = loadState.error.localizedMessage
            }
            vb.progressBar.isVisible = loadState is LoadState.Loading
            vb.tvErrorMessage.isVisible = loadState is LoadState.Loading
            vb.btnRetry.isVisible = loadState is LoadState.Error
        }
    }

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bindState(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder {

        val view =
            ItemLoadingStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingStateViewHolder(view, retry)
    }
}