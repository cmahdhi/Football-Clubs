package com.chiheb.footballclubs.data.entities.extensions

import com.chiheb.footballclubs.core.bases.SPORT_SOCCER
import com.chiheb.footballclubs.data.entities.LeagueEntity
import com.chiheb.footballclubs.data.entities.LeaguesEntity
import com.chiheb.footballclubs.data.entities.TeamDetailsEntity
import com.chiheb.footballclubs.data.entities.TeamEntity
import com.chiheb.footballclubs.domain.models.LeagueDomain
import com.chiheb.footballclubs.domain.models.TeamDetailsDomain
import com.chiheb.footballclubs.domain.models.TeamDomain

fun TeamEntity.toDomain() = TeamDomain(
    teamId = this.teamId,
    teamName = this.teamName,
    badgeImage = this.badgeImage
)

fun List<TeamEntity>.toDomain() = this.map { it.toDomain() }

fun TeamDetailsEntity.toDomain() = TeamDetailsDomain(
    teamName = this.teamName,
    bannerImage = this.bannerImage,
    country = this.country,
    league = this.league,
    description = this.description
)

fun LeaguesEntity.toDomain() =
    this.leagues.filter { it.leagueSport == SPORT_SOCCER }.map { it.toDomain() }

fun LeagueEntity.toDomain() = LeagueDomain(
    leagueName = this.leagueName
)
