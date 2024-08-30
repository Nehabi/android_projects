package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

class ElectionsViewModel(application: Application): AndroidViewModel(application) {
    private val database = ElectionDatabase.getInstance(application)
    private val electionRepository = ElectionRepository(database)
    val upcomingElections = electionRepository.allElectionList
    val followedElections = electionRepository.followedElectionList
    var selectedElection = MutableLiveData<Election?>()

    init {
        viewModelScope.launch {
            electionRepository.refreshElectionData()
        }
    }

    fun selectedElection(election: Election) {
        selectedElection.value = election
    }

    fun selectedElectionComplete() {
        selectedElection.value = null
    }
}