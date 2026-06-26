package com.example.babysleepsounds.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.babysleepsounds.data.mock.MockSleepSoundsData
import com.example.babysleepsounds.domain.model.SleepSound
import com.example.babysleepsounds.domain.model.SleepSoundsUiState
import com.example.babysleepsounds.domain.model.SleepTimerOption
import com.example.babysleepsounds.playback.SleepSoundPlayerController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SleepSoundsViewModel(application: Application) : AndroidViewModel(application) {
    private val playerController = SleepSoundPlayerController(application)
    private val mutableUiState = MutableStateFlow(MockSleepSoundsData.initialState)
    val uiState: StateFlow<SleepSoundsUiState> = mutableUiState.asStateFlow()

    init {
        MockSleepSoundsData.initialState.sounds.forEach { soundState ->
            playerController.setVolume(soundState.sound, soundState.volume)
            if (soundState.isPlaying) {
                playerController.play(soundState.sound)
            }
        }
    }

    fun toggle(sound: SleepSound) {
        mutableUiState.update { state ->
            state.copy(
                sounds = state.sounds.map { soundState ->
                    if (soundState.sound == sound) {
                        val isPlaying = !soundState.isPlaying
                        if (isPlaying) {
                            playerController.play(sound)
                        } else {
                            playerController.pause(sound)
                        }
                        soundState.copy(isPlaying = isPlaying)
                    } else {
                        soundState
                    }
                }
            )
        }
    }

    fun setVolume(sound: SleepSound, volume: Float) {
        val clampedVolume = volume.coerceIn(0f, 1f)
        playerController.setVolume(sound, clampedVolume)
        mutableUiState.update { state ->
            state.copy(
                sounds = state.sounds.map { soundState ->
                    if (soundState.sound == sound) soundState.copy(volume = clampedVolume) else soundState
                }
            )
        }
    }

    fun selectTimer(option: SleepTimerOption) {
        mutableUiState.update { state ->
            state.copy(selectedTimerOption = option, isTimerPreviewActive = true)
        }
    }

    fun clearTimerSelection() {
        mutableUiState.update { state ->
            state.copy(selectedTimerOption = null, isTimerPreviewActive = false)
        }
    }

    override fun onCleared() {
        playerController.release()
        super.onCleared()
    }
}
