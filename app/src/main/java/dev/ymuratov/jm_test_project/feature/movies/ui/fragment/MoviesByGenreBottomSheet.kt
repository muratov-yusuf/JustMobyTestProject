package dev.ymuratov.jm_test_project.feature.movies.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.ymuratov.jm_test_project.R
import dev.ymuratov.jm_test_project.databinding.DialogBsMoviesByGenreBinding
import dev.ymuratov.jm_test_project.feature.movies.ui.adapter.MoviesByGenreAdapter
import dev.ymuratov.jm_test_project.feature.movies.ui.adapter.PagingLoadStateAdapter
import dev.ymuratov.jm_test_project.feature.movies.ui.state.MoviesByGenreState
import dev.ymuratov.jm_test_project.feature.movies.ui.viewmodel.MoviesByGenreViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesByGenreBottomSheet : BottomSheetDialogFragment(R.layout.dialog_bs_movies_by_genre) {

    private var _binding: DialogBsMoviesByGenreBinding? = null
    private val binding: DialogBsMoviesByGenreBinding
        get() = _binding ?: throw RuntimeException("DialogBsMoviesByGenreBinding is null")

    private val moviesByGenreViewModel: MoviesByGenreViewModel by viewModels()
    private val moviesByGenreAdapter = MoviesByGenreAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutInflater = LayoutInflater.from(requireContext())
        _binding = DialogBsMoviesByGenreBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.moviesByGenreRV.adapter = moviesByGenreAdapter.withLoadStateHeaderAndFooter(
            header = PagingLoadStateAdapter { moviesByGenreAdapter.retry() },
            footer = PagingLoadStateAdapter { moviesByGenreAdapter.retry() }
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    moviesByGenreViewModel.uiState.collect(::handeState)
                }
            }
        }
    }

    private suspend fun handeState(uiState: MoviesByGenreState) {
        with(uiState) {
            with(binding) {
                moviesByGenreAdapter.submitData(movies)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}