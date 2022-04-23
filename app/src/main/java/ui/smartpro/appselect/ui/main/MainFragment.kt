package ui.smartpro.appselect.ui.main

import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ui.smartpro.appselect.MainActivity
import ui.smartpro.appselect.R
import ui.smartpro.appselect.adapter.MovieAdapter
import ui.smartpro.appselect.adapter.MovieLoadingAdapter
import ui.smartpro.appselect.databinding.MainFragmentBinding
import ui.smartpro.common.base.BaseFragment

class MainFragment(
    override val layoutId: Int = R.layout.main_fragment
) : BaseFragment<MainFragmentBinding>() {

    private lateinit var movieRv: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    lateinit var viewModel: MovieViewModel

    override fun initViews(view: View) {
        setupViews()
    }

    override fun initViewModel() {
        viewModel = (activity as MainActivity).mainViewModel
        fetchMovies()
    }

    private fun setupViews() {
        movieAdapter = MovieAdapter()
        movieRv = binding.movieList
        movieRv.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        movieRv.adapter = movieAdapter
        val header = MovieLoadingAdapter { movieAdapter.retry() }
        movieRv.adapter = movieAdapter.withLoadStateHeaderAndFooter(
            header = header,
            footer = MovieLoadingAdapter { movieAdapter.retry() }
        )

        movieAdapter.addLoadStateListener { loadState ->
            val isListEmpty =
                loadState.refresh is LoadState.NotLoading && movieAdapter.itemCount == 0
            showEmptyList(isListEmpty)
            header.loadState = loadState.mediator
                ?.refresh
                ?.takeIf { it is LoadState.Error && movieAdapter.itemCount > 0 }
                ?: loadState.prepend
            binding.movieList.isVisible =
                loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    requireContext(),
                    "\uD83D\uDE28 Упс, данных новых нет...!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun fetchMovies() {
        lifecycleScope.launch {
            viewModel.fetchMovies().collectLatest { pagingData ->
                movieAdapter.submitData(pagingData)
            }
        }
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.movieList.visibility = View.GONE
        } else {
            binding.movieList.visibility = View.VISIBLE
        }
    }
}