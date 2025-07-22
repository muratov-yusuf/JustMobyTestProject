package dev.ymuratov.jm_test_project.feature.info.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.ymuratov.jm_test_project.R
import dev.ymuratov.jm_test_project.core.ui.CenteredImageSpan
import dev.ymuratov.jm_test_project.core.ui.utils.toImageUrl
import dev.ymuratov.jm_test_project.databinding.FragmentMovieInfoBinding
import dev.ymuratov.jm_test_project.feature.info.ui.action.MovieInfoAction
import dev.ymuratov.jm_test_project.feature.info.ui.adapter.CastAdapter
import dev.ymuratov.jm_test_project.feature.info.ui.adapter.HorizontalPaddingDecoration
import dev.ymuratov.jm_test_project.feature.info.ui.event.MovieInfoEvent
import dev.ymuratov.jm_test_project.feature.info.ui.state.MovieInfoState
import dev.ymuratov.jm_test_project.feature.info.ui.viewmodel.MovieInfoViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieInfoFragment : Fragment() {

    private var _binding: FragmentMovieInfoBinding? = null
    private val binding: FragmentMovieInfoBinding
        get() = _binding ?: throw RuntimeException("FragmentMovieInfoBinding is null")

    private val movieInfoViewModel: MovieInfoViewModel by viewModels()
    private val castAdapter = CastAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val paddingPx = (16 * resources.displayMetrics.density).toInt()
        with(binding) {
            movieInfoCastRV.addItemDecoration(HorizontalPaddingDecoration(paddingPx, paddingPx))
            movieInfoCastRV.adapter = castAdapter
            movieInfoBackButton.setOnClickListener {
                findNavController().popBackStack()
            }
            movieInfoRetryButton.setOnClickListener {
                movieInfoViewModel.onEvent(MovieInfoEvent.RetryLoadData)
                binding.progressBar.isVisible = false
                movieInfoNoInternetContainer.isVisible = false
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            binding.movieInfoMainToolbar.updatePadding(top = systemBars.top)
            insets
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    movieInfoViewModel.uiState.collect(::handeState)
                }
                launch {
                    movieInfoViewModel.uiAction.collect(::handleAction)
                }
            }
        }
    }

    private fun handeState(uiState: MovieInfoState) {
        with(uiState) {
            with(binding) {
                progressBar.isVisible = movieInfo == null
                movieInfoMainContainer.isVisible = movieInfo != null

                movieInfoTeaserButton.isVisible = videoId != null
                movieInfoTeaserButton.setOnClickListener {
                    videoId?.let { videoId -> navigateToYT(videoId) }
                }
                movieInfo?.let { movieInfo ->
                    val star = ContextCompat.getDrawable(requireContext(), R.drawable.ic_star)
                        ?.apply { setBounds(0, 0, intrinsicWidth, intrinsicHeight) }
                    val starSpan = CenteredImageSpan(star!!)

                    val spannable =
                        SpannableString(" ${movieInfo.voteAverage} • ${movieInfo.genres.joinToString()}${if (movieInfo.adult) " • 18+" else ""}")
                    spannable.setSpan(starSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


                    movieInfoPosterIV.load(movieInfo.backdropPath.toImageUrl()) {
                        crossfade(true)
                        placeholder(R.drawable.ic_poster_placeholder)
                        error(R.drawable.ic_poster_placeholder)
                    }
                    movieInfoNameTV.text = movieInfo.title
                    movieInfoDetailsTV.setText(spannable, TextView.BufferType.SPANNABLE)
                    movieInfoBudgetTV.text = formatMoneyShort(movieInfo.budget)
                    movieInfoRevenueTV.text = formatMoneyShort(movieInfo.revenue)
                    movieInfoOverviewTV.text = movieInfo.overview
                }
                val directors = crew.filter { it.job == "Director" }
                movieInfoDirectorsLabelTV.isVisible = directors.isNotEmpty()
                movieInfoDirectorsTV.apply {
                    isVisible = directors.isNotEmpty()
                    text = directors.joinToString { it.name }
                }

                val producers = crew.filter { it.job == "Producer" }
                movieInfoProducersLabelTV.isVisible = producers.isNotEmpty()
                movieInfoProducersTV.apply {
                    isVisible = producers.isNotEmpty()
                    text = producers.joinToString { it.name }
                }

                val writers = crew.filter { it.job == "Writer" }
                movieInfoWritersLabelTV.isVisible = directors.isNotEmpty()
                movieInfoWritersTV.apply {
                    isVisible = writers.isNotEmpty()
                    text = writers.joinToString { it.name }
                }

                val composers = crew.filter { it.job!!.contains("Composer") }
                movieInfoComposersLabelTV.isVisible = composers.isNotEmpty()
                movieInfoComposersTV.apply {
                    isVisible = composers.isNotEmpty()
                    text = composers.joinToString { it.name }
                }
            }

            castAdapter.submitList(cast)
        }
    }

    private fun handleAction(uiAction: MovieInfoAction) {
        when (uiAction) {
            is MovieInfoAction.ShowError -> {
                Snackbar.make(binding.root, uiAction.exception.message ?: "Unknown error", Snackbar.LENGTH_SHORT).show()
                binding.movieInfoMainContainer.isVisible = false
                binding.progressBar.isVisible = false
                binding.movieInfoNoInternetContainer.isVisible = true
            }
        }
    }

    private fun navigateToYT(videoId: String) {
        val intent = Intent(Intent.ACTION_VIEW, "vnd.youtube:$videoId".toUri())
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        if (intent.resolveActivity(requireContext().packageManager) != null) {
            requireContext().startActivity(intent)
        } else {
            val webIntent = Intent(Intent.ACTION_VIEW, "https://www.youtube.com/watch?v=$videoId".toUri())
            requireContext().startActivity(webIntent)
        }
    }

    private fun formatMoneyShort(amount: Int): String {
        return when {
            amount >= 1_000_000_000 -> "$%.1fB".format(amount / 1_000_000_000.0)
            amount >= 1_000_000 -> "$%.1fM".format(amount / 1_000_000.0)
            amount >= 1_000 -> "$%,d".format(amount)
            else -> "$amount"
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}