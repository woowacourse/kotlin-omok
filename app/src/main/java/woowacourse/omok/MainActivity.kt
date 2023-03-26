package woowacourse.omok

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import omok.model.game.Board
import omok.model.game.OmokGame
import omok.model.game.PlacementState
import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor
import woowacourse.omok.database.OmokDB
import woowacourse.omok.database.OmokRepository
import woowacourse.omok.database.Repository

class MainActivity : AppCompatActivity() {
    private var omokGame = OmokGame(Board())
    private val omokRepo: Repository by lazy { OmokRepository(OmokDB.getInstance(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initListener()
        if (!omokRepo.isEmpty()) reloadPreviousGame()
    }

    private fun initListener() {
        getBoardImageViews()
            .forEachIndexed { index, view ->
                view.setOnClickListener {
                    val clickedCoordinate: Coordinate = convertCoordinate(index)
                    val lastPlacedStone = omokGame.turn(coordinate = { clickedCoordinate })
                    val placedStoneState = omokGame.judge(lastPlacedStone)
                    if (placedStoneState == PlacementState.STAY) {
                        placeGoStoneOnBoard(lastPlacedStone, view)
                        omokRepo.insert(lastPlacedStone, index)
                        view.isEnabled = false
                        return@setOnClickListener
                    }

                    placeGoStoneOnBoard(lastPlacedStone, view)
                    omokRepo.insert(lastPlacedStone, index)
                    omokRepo.clear()
                    disableBoard()
                    setWinnerText(lastPlacedStone.color, placedStoneState)
                }
            }

        findViewById<Button>(R.id.retry_button).setOnClickListener {
            omokRepo.clear()
            omokGame = OmokGame(Board())
            getBoardImageViews().forEach {
                it.setImageResource(0)
                it.isEnabled = true
            }
            findViewById<TextView>(R.id.winner_text).text = ""
        }
    }

    private fun reloadPreviousGame() {
        val boardImageViews = getBoardImageViews()
        omokRepo.getAll().forEach {
            omokGame.addStoneDirect(it.key)
            placeGoStoneOnBoard(it.key, boardImageViews[it.value])
            boardImageViews[it.value].isEnabled = false
        }
    }

    private fun convertCoordinate(target: Int): Coordinate {
        val index = target + 1
        val x = if (index % BOARD_SIZE == 0) BOARD_SIZE else index % BOARD_SIZE
        val y = if (index % BOARD_SIZE == 0) BOARD_SIZE - index / BOARD_SIZE + 1 else BOARD_SIZE - index / BOARD_SIZE
        return Coordinate(x, y)
    }

    private fun getBoardImageViews() = findViewById<TableLayout>(R.id.board).children
        .filterIsInstance<TableRow>()
        .flatMap { it.children }
        .filterIsInstance<ImageView>()
        .toList()

    private fun placeGoStoneOnBoard(goStone: GoStone, view: ImageView) {
        if (goStone.color == GoStoneColor.BLACK) {
            view.setImageResource(R.drawable.black_stone)
            return
        }
        view.setImageResource(R.drawable.white_stone)
    }

    private fun disableBoard() {
        findViewById<TableLayout>(R.id.board).children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { it.isEnabled = false }
    }

    private fun setWinnerText(color: GoStoneColor, placementState: PlacementState) {
        findViewById<TextView>(R.id.winner_text).text = when (placementState) {
            PlacementState.WIN -> "${color.name} 승리!"
            else -> "금수!: ${placementState.name}, ${GoStoneColor.WHITE.name} 승리!"
        }
    }

    override fun onDestroy() {
        omokRepo.close()
        super.onDestroy()
    }

    companion object {
        private const val BOARD_SIZE = 15
    }
}
