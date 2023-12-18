/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 60000L
    }
    // The current word
    private val _word = MutableLiveData<String>()
    val word : LiveData<String>
        get() = _word
    // The current score
    private val _score = MutableLiveData<Int>()
    val score : LiveData<Int>
        get() = _score

    // state for game finished
    private val _finished = MutableLiveData<Boolean>()
    val finished : LiveData<Boolean>
        get() = _finished

    private val timer: CountDownTimer

    // The current score
    private val _timeLeft = MutableLiveData<String>()
    val timeLeft : LiveData<String>
        get() = _timeLeft

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        Log.i("GameViewModel", "GameViewModel created")
        resetList()
        nextWord()
        _score.value = 0
        _word.value = wordList[0]
        _finished.value = false
        _timeLeft.value = DateUtils.formatElapsedTime(COUNTDOWN_TIME)
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _timeLeft.value = DateUtils.formatElapsedTime(millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                _timeLeft.value = DateUtils.formatElapsedTime(DONE)
                _finished.value = true
                resetList()
            }
        }

        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed")
        super.onCleared()
        timer.cancel()
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.value = wordList.removeAt(0)
    }

    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    fun gameCompleted() {
        _finished.value = false
    }
}

// TODO (02) Create the GameViewModel class, extending ViewModel
// TODO (03) Add init and override onCleared; Add log statements to both