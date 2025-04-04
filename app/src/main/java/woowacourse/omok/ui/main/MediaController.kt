package woowacourse.omok.ui.main

import android.content.Context
import android.media.MediaPlayer

class MediaController(
    private val context: Context,
    private val resourceId: Int,
) {
    private var mediaPlayer: MediaPlayer? = null

    fun play() {
        mediaPlayer = MediaPlayer.create(context, resourceId)
        mediaPlayer?.start()
    }

    fun stop() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
