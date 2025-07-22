package dev.ymuratov.jm_test_project.feature.movies.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.ymuratov.jm_test_project.databinding.FragmentMoviesBinding
import dev.ymuratov.jm_test_project.feature.movies.ui.action.MoviesAction
import dev.ymuratov.jm_test_project.feature.movies.ui.adapter.GenresAdapter
import dev.ymuratov.jm_test_project.feature.movies.ui.adapter.GenresShimmerAdapter
import dev.ymuratov.jm_test_project.feature.movies.ui.event.MoviesEvent
import dev.ymuratov.jm_test_project.feature.movies.ui.fragment.MoviesFragmentDirections.Companion.actionMoviesFragmentToMovieInfoFragment
import dev.ymuratov.jm_test_project.feature.movies.ui.fragment.MoviesFragmentDirections.Companion.actionMoviesFragmentToMoviesByGenreBottomSheet
import dev.ymuratov.jm_test_project.feature.movies.ui.state.MoviesState
import dev.ymuratov.jm_test_project.feature.movies.ui.viewmodel.MoviesViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding: FragmentMoviesBinding
        get() = _binding ?: throw RuntimeException("FragmentMoviesBinding is null")

    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.moviesRetryButton.setOnClickListener {
            moviesViewModel.onEvent(MoviesEvent.RetryLoadData)
            binding.moviesRV.isVisible = true
            binding.moviesNoInternetContainer.isVisible = false
        }

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    moviesViewModel.uiState.collect(::handeState)
                }
                launch {
                    moviesViewModel.uiAction.collect(::handleAction)
                }
            }
        }
    }

    private fun handeState(uiState: MoviesState) {
        with(uiState) {
            val adapter = if (genres.isEmpty()) {
                GenresShimmerAdapter().apply { submitList(List(3) { "" }) }
            } else {
                GenresAdapter().apply {
                    onGenreMoreClickListener = { genre ->
                        findNavController().navigate(actionMoviesFragmentToMoviesByGenreBottomSheet(genre.id))
                    }
                    onMovieClickListener = { movie ->
                        findNavController().navigate(actionMoviesFragmentToMovieInfoFragment(movie.id))
                    }
                    submitList(genres)
                }
            }
            with(binding) {
                moviesRV.adapter = adapter
            }
        }
    }


    private fun handleAction(uiAction: MoviesAction) {
        when (uiAction) {
            is MoviesAction.ShowError -> {
                Snackbar.make(binding.root, uiAction.exception.message ?: "Unknown error", Snackbar.LENGTH_SHORT).show()
                binding.moviesRV.isVisible = false
                binding.moviesNoInternetContainer.isVisible = true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}