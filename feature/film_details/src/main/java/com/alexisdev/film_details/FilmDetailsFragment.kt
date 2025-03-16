package com.alexisdev.film_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alexisdev.designsystem.R
import com.alexisdev.film_details.databinding.FragmentFilmDetailsBinding
import com.alexisdev.film_details.model.FilmUi
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class FilmDetailsFragment : Fragment() {
    private lateinit var binding: FragmentFilmDetailsBinding
    private val viewModel by viewModel<FilmDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilmDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is FilmDetailsState.Loading -> {
                            showProgress(true)
                        }

                        is FilmDetailsState.Content -> {
                            showProgress(false)
                            renderContent(state.filmUi)
                        }
                    }
                }
            }
        }

    }

    private fun showProgress(isShow: Boolean) {
        if (isShow) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }

    private fun renderContent(filmUi: FilmUi) {
        (activity as AppCompatActivity).supportActionBar?.title = filmUi.name
        binding.tvFilmRating.text = filmUi.rating.toString()
        binding.tvFilmDescription.text = filmUi.description
        binding.tvLocalizedFilmName.text = filmUi.localizedName
        val genre = filmUi.genres.firstOrNull()
            ?.let { requireContext().getString(it.title).lowercase() }
        binding.tvFilmGenreYear.text = if (genre.isNullOrEmpty()) {
            "${filmUi.year} год"
        } else {
            "$genre, ${filmUi.year} год"
        }
        Glide
            .with(requireContext())
            .load(filmUi.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.img_placeholder)
            .into(binding.imgFilm)
    }

}