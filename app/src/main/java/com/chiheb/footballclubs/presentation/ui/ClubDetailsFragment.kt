package com.chiheb.footballclubs.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.chiheb.footballclubs.core.utils.loadImage
import com.chiheb.footballclubs.databinding.FragmentClubDetailsBinding
import com.chiheb.footballclubs.presentation.models.TeamDetails
import com.chiheb.footballclubs.presentation.presenters.ClubDetailsPresenter
import com.chiheb.footballclubs.presentation.ui.contracts.ClubDetailsView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ClubDetailsFragment : Fragment(), ClubDetailsView {

    private lateinit var binding: FragmentClubDetailsBinding

    @Inject
    lateinit var presenter: ClubDetailsPresenter

    private val args: ClubDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClubDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initListeners()
        presenter.fetchTeamDetails(args.team)
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
        binding.detailsContainer.isVisible = false
        binding.errorContainer.root.isVisible = false
    }

    override fun handleResult(teamDetails: TeamDetails) {
        with(binding) {
            detailsContainer.isVisible = true
            teamDetails.bannerImage?.also { teamBanner.loadImage(it) }
            teamCountry.text = teamDetails.country
            teamLeague.text = teamDetails.league
            teamDescription.text = teamDetails.description
        }
    }

    override fun handleError() {
        binding.errorContainer.root.isVisible = true
    }

    private fun initToolbar() {
        binding.toolbar.apply {
            title = args.team.teamName
            setNavigationOnClickListener { activity?.onBackPressed() }
        }
    }

    private fun initListeners() {
        binding.errorContainer.errorRetry.setOnClickListener {
            presenter.fetchTeamDetails(args.team)
        }
    }
}