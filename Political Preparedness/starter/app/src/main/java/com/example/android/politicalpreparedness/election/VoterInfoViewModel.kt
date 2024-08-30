package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.launch

class VoterInfoViewModel(
    private val electionId: Int,
    private val division: Division,
    application: Application
) : ViewModel() {
    private val database = ElectionDatabase.getInstance(application)
    private val electionRepository = ElectionRepository(database)
    val voterInfo = MutableLiveData<VoterInfoResponse>()
    var isFollow = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch {
            isFollow.value = electionRepository.getFollowedByElectionId(electionId) ?: false
            voterInfo.value = CivicsApi.retrofitService.getVoterInfo(
                electionId,
                division.id)
        }
    }

    fun handleFollowElection() {
        viewModelScope.launch {
            if (isFollow.value == true) {
                electionRepository.followElectionById(electionId, false)
            } else {
                electionRepository.followElectionById(electionId, true)
            }
            isFollow.value = electionRepository.getFollowedByElectionId(electionId) ?: false
        }
    }
}