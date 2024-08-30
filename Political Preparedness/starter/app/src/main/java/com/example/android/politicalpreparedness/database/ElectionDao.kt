package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveAllElection(election: List<Election>)

    @Query("SELECT * FROM election_table")
    fun getAllElection(): LiveData<List<Election>>

    @Query("SELECT * FROM election_table WHERE follow = 1")
    fun getFollowedElection(): LiveData<List<Election>>

    @Query("SELECT * FROM election_table WHERE id = :id")
    fun getElectionById(id: Int): Election?

    @Query("DELETE FROM election_table WHERE id = :id")
    fun deleteElectionById(id: Int)

    @Query("UPDATE election_table SET follow = :follow WHERE id = :id")
    fun updateFollowedElection(id: Int, follow: Boolean)

    @Query("DELETE FROM election_table")
    fun clear()

    @Query("SELECT follow FROM election_table WHERE id = :id")
    fun getFollowedByElectionId(id: Int): Boolean?
}