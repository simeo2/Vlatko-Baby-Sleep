package com.example.babysleepsounds.playback

import android.content.Context
import android.util.Log
import androidx.annotation.RawRes
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import com.example.babysleepsounds.R
import com.example.babysleepsounds.domain.model.SleepSound

class SleepSoundPlayerController(context: Context) {
    private val appContext = context.applicationContext
    private val players = SleepSound.entries.associateWith { sound ->
        ExoPlayer.Builder(appContext).build().apply {
            repeatMode = Player.REPEAT_MODE_ONE
            setMediaItem(MediaItem.fromUri(RawResourceDataSource.buildRawResourceUri(sound.rawResourceId)))
            addListener(object : Player.Listener {
                override fun onPlayerError(error: PlaybackException) {
                    Log.e(TAG, "Playback failed for ${sound.displayName}", error)
                }
            })
            prepare()
        }
    }

    fun play(sound: SleepSound) {
        players[sound]?.play()
    }

    fun pause(sound: SleepSound) {
        players[sound]?.pause()
    }

    fun setVolume(sound: SleepSound, volume: Float) {
        players[sound]?.volume = volume.coerceIn(MIN_VOLUME, MAX_VOLUME)
    }

    fun release() {
        players.values.forEach { player ->
            player.stop()
            player.release()
        }
    }

    private companion object {
        private const val TAG = "SleepSoundPlayer"
        private const val MIN_VOLUME = 0f
        private const val MAX_VOLUME = 1f

        @get:RawRes
        private val SleepSound.rawResourceId: Int
            get() = when (this) {
                SleepSound.Rain -> R.raw.rain
                SleepSound.Ocean -> R.raw.ocean
                SleepSound.Fan -> R.raw.fan
                SleepSound.WhiteNoise -> R.raw.white_noise
                SleepSound.BrownNoise -> R.raw.brown_noise
                SleepSound.Heartbeat -> R.raw.heartbeat
            }
    }
}
