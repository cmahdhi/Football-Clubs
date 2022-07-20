package com.chiheb.footballclubs.domain.models.extensions

import com.chiheb.footballclubs.domain.models.LeagueDomain
import com.chiheb.footballclubs.domain.models.TeamDetailsDomain
import com.chiheb.footballclubs.domain.models.TeamDomain
import com.chiheb.footballclubs.presentation.models.League
import com.chiheb.footballclubs.presentation.models.Team
import com.chiheb.footballclubs.presentation.models.TeamDetails

fun TeamDomain.toModel() = Team(
    teamId = this.teamId,
    teamName = this.teamName,
    badgeImage = this.badgeImage
)

fun List<TeamDomain>.toModel() = this.map { it.toModel() }

fun TeamDetailsDomain.toModel() = TeamDetails(
    teamName = this.teamName,
    bannerImage = this.bannerImage,
    country = this.country,
    league = this.league,
    description = this.description
)

fun List<LeagueDomain>.toLeagueModel() = this.map { it.toModel() }

fun LeagueDomain.toModel() = League(
    leagueName = this.leagueName
)
