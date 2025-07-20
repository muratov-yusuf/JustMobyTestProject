package dev.ymuratov.jm_test_project.feature.info.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dev.ymuratov.jm_test_project.databinding.FragmentMovieInfoBinding
import dev.ymuratov.jm_test_project.feature.info.ui.viewmodel.MovieInfoViewModel

class MovieInfoFragment : Fragment() {

    private var _binding: FragmentMovieInfoBinding? = null
    private val binding: FragmentMovieInfoBinding
        get() = _binding ?: throw RuntimeException("FragmentMovieInfoBinding is null")

    private val movieInfoViewModel: MovieInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieInfoViewModel


        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            binding.appBarLayout.layoutParams =
                (binding.appBarLayout.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    topMargin = systemBars.top
                }
            insets
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}