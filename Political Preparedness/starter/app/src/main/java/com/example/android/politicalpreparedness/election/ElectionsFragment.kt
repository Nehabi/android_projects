package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.election.adapter.ElectionListener

class ElectionsFragment: Fragment() {

    private val electionViewModel by lazy {
        ViewModelProvider(this)[ElectionsViewModel::class.java]
    }
    private lateinit var binding: FragmentElectionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?)
    : View {
        binding = FragmentElectionBinding.inflate(inflater)
        binding.viewModel = electionViewModel

        binding.listUpcomingElections.adapter = ElectionListAdapter(ElectionListener {election ->
            electionViewModel.selectedElection(election)
        })
        binding.listFollowedElections.adapter = ElectionListAdapter(ElectionListener {election ->
            electionViewModel.selectedElection(election)
        })
        electionViewModel.upcomingElections.observe(viewLifecycleOwner) {
            (binding.listUpcomingElections.adapter as ElectionListAdapter).submitList(it)
        }
        electionViewModel.followedElections.observe(viewLifecycleOwner) {
            (binding.listFollowedElections.adapter as ElectionListAdapter).submitList(it)
        }
        electionViewModel.selectedElection.observe(viewLifecycleOwner) { election ->
            if (election != null) {
                this.findNavController().navigate(ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(election.id, election.division))
                electionViewModel.selectedElectionComplete()
            }
        }
        return binding.root
    }
}