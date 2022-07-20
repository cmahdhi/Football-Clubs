package com.chiheb.footballclubs.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.chiheb.footballclubs.core.utils.hideKeyboard
import com.chiheb.footballclubs.core.utils.setOnEditorSearchListener
import com.chiheb.footballclubs.core.utils.textChanges
import com.chiheb.footballclubs.databinding.FragmentClubsListBinding
import com.chiheb.footballclubs.presentation.models.League
import com.chiheb.footballclubs.presentation.models.Team
import com.chiheb.footballclubs.presentation.presenters.FootballClubsPresenter
import com.chiheb.footballclubs.presentation.ui.adapters.ClubsListAdapter
import com.chiheb.footballclubs.presentation.ui.contracts.ClubsListView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

private const val SEARCH_AUTOCOMPLETE_DEBOUNCE_TIME = 500L

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class ClubsListFragment : Fragment(), ClubsListView {

    private lateinit var binding: FragmentClubsListBinding

    @Inject
    lateinit var presenter: FootballClubsPresenter

    private val adapter by lazy {
        ClubsListAdapter(callback = {
            findNavController().navigate(ClubsListFragmentDirections.toClubDetails(it))
        })
    }

    private val autoCompleteAdapter by lazy {
        ArrayAdapter<String>(
            binding.searchQuery.context,
            android.R.layout.simple_dropdown_item_1line
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClubsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initTextChangeListener()
        initView()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }

    override fun toggleLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.clubsList.isVisible = false
        binding.welcomeMessage.isVisible = false
        binding.errorContainer.root.isVisible = false
    }

    override fun handleResult(teams: List<Team>) {
        binding.clubsList.isVisible = true
        adapter.updateTeams(teams)
    }

    override fun handleError() {
        binding.errorContainer.root.isVisible = true
    }

    override fun handleLeaguesResult(leagues: List<League>) {
        autoCompleteAdapter.clear()
        autoCompleteAdapter.addAll(leagues.map { it.leagueName })
        autoCompleteAdapter.filter.filter(null)
    }

    private fun initView() {
        with(binding) {
            clubsList.adapter = adapter
            searchQuery.setAdapter(autoCompleteAdapter)
        }

        if (adapter.itemCount > 0) {
            binding.progressBar.isVisible = false
            binding.welcomeMessage.isVisible = false
            binding.errorContainer.root.isVisible = false
            binding.clubsList.isVisible = true
        }
    }

    private fun initListeners() {
        with(binding) {
            errorContainer.errorRetry.setOnClickListener { searchTeams() }
            clearSearch.setOnClickListener { clearSearchQuery() }
            searchQuery.setOnEditorSearchListener { searchTeams() }
            searchQuery.setOnItemClickListener { _, _, _, _ -> searchTeams() }
        }
    }

    private fun initTextChangeListener() {
        binding.searchQuery
            .textChanges()
            .distinctUntilChanged()
            .onEach { query ->
                binding.clearSearch.isVisible = query.isNotEmpty()
            }
            .debounce(SEARCH_AUTOCOMPLETE_DEBOUNCE_TIME)
            .mapLatest { searchLeagues(it) }
            .launchIn(lifecycleScope)
    }

    private fun clearSearchQuery() {
        binding.searchQuery.setText(String())
    }

    private fun searchTeams() {
        view?.hideKeyboard()
        presenter.searchTeams(binding.searchQuery.text.toString())
    }

    private fun searchLeagues(query: String) {
        if (query.isNotEmpty()) {
            presenter.getLeagues(query)
        } else {
            autoCompleteAdapter.clear()
        }
    }
}