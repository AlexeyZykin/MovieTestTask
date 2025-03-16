package com.alexisdev.film_catalog

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexisdev.film_catalog.adapter.FilmAdapter
import com.alexisdev.film_catalog.adapter.GenreAdapter
import com.alexisdev.film_catalog.databinding.FragmentFilmCatalogBinding
import com.alexisdev.film_catalog.model.FilmUi
import com.alexisdev.film_catalog.model.GenreUi
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.alexisdev.designsystem.R as designsystem


class FilmCatalogFragment : Fragment() {
    private lateinit var binding: FragmentFilmCatalogBinding
    private lateinit var filmAdapter: FilmAdapter
    private lateinit var genreAdapter: GenreAdapter
    private var currentSnackbar: Snackbar? = null
    private val viewModel by viewModel<FilmCatalogViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilmCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is FilmCatalogState.Loading -> {
                            showProgressBar(true)
                        }

                        is FilmCatalogState.Error -> {
                            showProgressBar(false)
                            showErrorSnackbar(binding.root, state.msg)
                        }

                        is FilmCatalogState.Content -> {
                            showProgressBar(false)
                            currentSnackbar?.dismiss()
                            renderContent(
                                genres = state.genres,
                                films = state.films,
                                selectedGenre = state.selectedGenre
                            )
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        genreAdapter = GenreAdapter(
            object : GenreAdapter.ClickListener {
                override fun onClick(genre: GenreUi) {
                    viewModel.onEvent(FilmCatalogEvent.OnSelectGenre(genre))
                }
            }
        )
        filmAdapter = FilmAdapter(
            object : FilmAdapter.ClickListener {
                override fun onClick(filmId: Int) {
                    //todo
                }
            }
        )
        binding.rvGenres.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGenres.adapter = genreAdapter
        binding.rvFilms.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvFilms.adapter = filmAdapter
        binding.rvGenres.isNestedScrollingEnabled = false
        binding.rvFilms.isNestedScrollingEnabled = false
    }

    private fun showProgressBar(isShow: Boolean) {
        if (isShow) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }

    private fun showErrorSnackbar(view: View, msg: String) {
        val snackbar = Snackbar.make(
            view, msg, Snackbar.LENGTH_INDEFINITE
        )

        snackbar.setBackgroundTint(requireContext().getColor(designsystem.color.dark_gray))
        snackbar.setActionTextColor(requireContext().getColor(designsystem.color.yellow))
        snackbar.setAction(getString(R.string.film_catalog_action_title)) {
            viewModel.onEvent(FilmCatalogEvent.OnRetry)
        }

        (snackbar.view.layoutParams as CoordinatorLayout.LayoutParams).behavior = null

        currentSnackbar = snackbar
        snackbar.show()
    }

    private fun renderContent(
        genres: List<GenreUi>,
        films: List<FilmUi>,
        selectedGenre: GenreUi?
    ) {
        binding.tvGenres.visibility = View.VISIBLE
        binding.tvFilms.visibility = View.VISIBLE
        genreAdapter.map(genres)
        filmAdapter.map(films)
        genreAdapter.updateSelectedGenre(selectedGenre)
    }

}
