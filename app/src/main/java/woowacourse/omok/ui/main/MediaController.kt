package woowacourse.omok.ui.main

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes
import woowacourse.omok.R

class MediaController(
    private val context: Context,
    @RawRes resourceId: Int,
) {
    private var mediaPlayer: MediaPlayer? = null

    fun play() {
        mediaPlayer = MediaPlayer.create(context, R.raw.apple)
        mediaPlayer?.start()
    }

    fun stop()  {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
