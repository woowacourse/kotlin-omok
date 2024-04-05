package woowacourse.omok

import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import omok.model.OmokGameState
import omok.model.entity.StoneColor
import woowacourse.omok.db.StoneDao

class MainActivity : AppCompatActivity() {
    lateinit var stoneDao: StoneDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stoneDao = StoneDao(applicationContext)
        val omokGameState = OmokGameState()
        val board = findViewById<TableLayout>(R.id.board)
        val omokBoardImageView = OmokBoardImageView(board)
        val omokStateHolder = OmokStateHolder(omokGameState)
        val omokBoardDbHandler = OmokBoardDbHandler(stoneDao)
        val resultToast = ResultToast(applicationContext)

        omokStateHolder.subscribedBy(omokBoardImageView)
        omokStateHolder.subscribedBy(omokBoardDbHandler)
        omokStateHolder.subscribedBy(resultToast)

        omokBoardImageView.setOnClickListener {
            omokStateHolder.proceed(it)
        }

        val resetButton = findViewById<Button>(R.id.reset_button)
        resetButton.setOnClickListener {
            omokStateHolder.replaceState(OmokGameState())
        }

        omokStateHolder.replaceState(loadOmokGameState())
    }

    private fun loadOmokGameState(): OmokGameState {
        val stones = stoneDao.findAll()
        val points = stones.map { it.point }
        return OmokGameState().runMultiple(points)
    }
}

fun StoneColor.toDrawableId() =
    when (this) {
        StoneColor.BLACK -> R.drawable.black_stone
        StoneColor.WHITE -> R.drawable.white_stone
    }
