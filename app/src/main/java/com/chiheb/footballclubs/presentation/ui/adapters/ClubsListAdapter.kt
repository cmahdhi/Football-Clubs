package com.chiheb.footballclubs.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chiheb.footballclubs.core.utils.loadImage
import com.chiheb.footballclubs.databinding.HolderTeamItemBinding
import com.chiheb.footballclubs.presentation.models.Team

class ClubsListAdapter(
    private val list: MutableList<Team> = mutableListOf(),
    private val callback: (Team) -> Unit
) : RecyclerView.Adapter<ClubsListAdapter.ClubViewHolder>() {

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        return ClubViewHolder(
            HolderTeamItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
        holder.bindView(list[position])
    }

    fun updateTeams(teams: List<Team>) {
        list.clear()
        list.addAll(teams)
        notifyDataSetChanged()
    }

    inner class ClubViewHolder(private val binding: HolderTeamItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(team: Team) {
            binding.teamImage.apply {
                loadImage(team.badgeImage)
                setOnClickListener { callback(team) }
            }
        }
    }
}