package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding


class VoterInfoFragment : Fragment() {
    lateinit var binding: FragmentVoterInfoBinding
    lateinit var viewModelFactory: VoterInfoViewModelFactory
    lateinit var viewModel: VoterInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?)
    : View? {

        viewModelFactory = VoterInfoViewModelFactory(
            VoterInfoFragmentArgs.fromBundle(requireArguments()).argElectionId,
            VoterInfoFragmentArgs.fromBundle(requireArguments()).argDivision,
            requireActivity().application)
        viewModel = ViewModelProvider(this, viewModelFactory)[VoterInfoViewModel::class.java]

        binding = FragmentVoterInfoBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.stateLocations.setOnClickListener { votingLocation ->
            val url = viewModel.voterInfo.value?.state?.get(0)?.electionAdministrationBody?.votingLocationFinderUrl
            validateURL(url)
        }

        binding.stateBallot.setOnClickListener { ballotInfo ->
            val url = viewModel.voterInfo.value?.state?.get(0)?.electionAdministrationBody?.ballotInfoUrl
            validateURL(url)
        }

        binding.followButton.setOnClickListener{ followButton ->
            viewModel.handleFollowElection()
        }

        return binding.root
    }

    private fun validateURL(url: String?) {
        if(!url.isNullOrEmpty()) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        } else {
            Toast.makeText(requireContext(), "Error loading URL", Toast.LENGTH_SHORT).show()
        }
    }
}