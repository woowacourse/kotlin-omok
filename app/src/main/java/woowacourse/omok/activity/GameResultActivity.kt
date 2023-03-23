package woowacourse.omok.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.omok.R
import woowacourse.omok.model.StoneColorModel
import woowacourse.omok.utils.setImageByResId

class GameResultActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_result)

        setWinnerStoneImage()
        setClickListener()
    }

    private fun setWinnerStoneImage() {
        val winnerStoneColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("winner_color", StoneColorModel::class.java)
        } else {
            intent.getSerializableExtra("winner_color")
        }
        val winnerStoneIv = findViewById<ImageView>(R.id.winner_stone_iv)
        val winnerStoneTv = findViewById<TextView>(R.id.winner_stone_tv)

        when (winnerStoneColor) {
            StoneColorModel.BLACK -> {
                winnerStoneIv.setImageByResId(R.drawable.pink_bear)
                winnerStoneTv.text = getString(R.string.winner_result_format, StoneColorModel.BLACK.text)
            }
            StoneColorModel.WHITE -> {
                winnerStoneIv.setImageByResId(R.drawable.white_stone)
                winnerStoneTv.text = getString(R.string.winner_result_format, StoneColorModel.WHITE.text)
            }
        }
    }

    private fun setClickListener() {
        findViewById<View>(R.id.restart_btn).setOnClickListener(this)
        findViewById<View>(R.id.end_game_btn).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.restart_btn -> restartGame()
            R.id.end_game_btn -> endGame()
        }
    }

    private fun restartGame() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun endGame() {
        finish()
    }
}
