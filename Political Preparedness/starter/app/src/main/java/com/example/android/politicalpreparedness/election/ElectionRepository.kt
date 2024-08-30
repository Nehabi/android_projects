package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ElectionRepository(private val database: ElectionDatabase) {
    val allElectionList: LiveData<List<Election>> = database.electionDao.getAllElection()
    val followedElectionList: LiveData<List<Election>> = database.electionDao.getFollowedElection()

    suspend fun followElectionById(electionId: Int, follow: Boolean) {
        withContext(Dispatchers.IO) {
            database.electionDao.updateFollowedElection(electionId, follow)
        }
    }

    suspend fun getFollowedByElectionId(electionId: Int): Boolean? {
        val temp: Boolean?
        withContext(Dispatchers.IO) {
            temp = database.electionDao.getFollowedByElectionId(electionId)
        }
        return temp
    }

    suspend fun refreshElectionData() {
        withContext(Dispatchers.IO){
            val elections = CivicsApi.retrofitService.getElections().elections
            database.electionDao.saveAllElection(elections)
        }
    }
}