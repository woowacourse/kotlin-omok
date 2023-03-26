package woowacourse.omok

import android.content.ContentValues
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
import woowacourse.omok.model.GoStoneColorNumber

class MainActivity : AppCompatActivity() {
    private val omokGame = OmokGame(Board())
    private lateinit var omokRepo: Repository
    private lateinit var board: TableLayout
    private lateinit var winner: TextView
    private lateinit var retryButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        omokRepo = OmokRepository(OmokDB.getInstance(this))
        initView()

        if (hasPreviousGame()) setPreviousGame()

        getBoardImageViews()
            .forEachIndexed { index, view ->
                view.setOnClickListener {
                    val clickedCoordinate: Coordinate = index.toCoordinate()
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
                    disableBoard(board)
                    setWinnerText(winner, lastPlacedStone.color, placedStoneState)
                }
            }

        retryButton.setOnClickListener {
            omokRepo.clear()
            recreate()
        }
    }

    private fun initView() {
        board = findViewById(R.id.board)
        winner = findViewById(R.id.winner_text)
        retryButton = findViewById(R.id.retry_button)
    }

    private fun reloadPreviousGame() {
        val boardImageViews = getBoardImageViews()
        omokRepo.getAll().forEach {
            omokGame.addStoneDirect(it.key)
            placeGoStoneOnBoard(it.key, boardImageViews[it.value])
            boardImageViews[it.value].isEnabled = false
        }
    }

    private fun getBoardImageViews() = board.children
        .filterIsInstance<TableRow>()
        .flatMap { it.children }
        .filterIsInstance<ImageView>()
        .toList()

    private fun Int.toCoordinate(): Coordinate {
        val index = this + 1
        val x = if (index % BOARD_SIZE == 0) BOARD_SIZE else index % BOARD_SIZE
        val y = if (index % BOARD_SIZE == 0) BOARD_SIZE - index / BOARD_SIZE + 1 else BOARD_SIZE - index / BOARD_SIZE
        return Coordinate(x, y)
    }

    private fun placeGoStoneOnBoard(goStone: GoStone, view: ImageView) {
        if (goStone.color == GoStoneColor.BLACK) {
            view.setImageResource(R.drawable.black_stone)
        } else {
            view.setImageResource(R.drawable.white_stone)
        }
    }

    private fun recordGoStone(goStone: GoStone, index: Int) {
        val record = ContentValues().apply {
            put(KEY_GO_STONE_COLOR, GoStoneColorNumber.convertGoStoneColorNumber(goStone.color).number)
            put(KEY_BOARD_INDEX, index)
            put(KEY_COORDINATE_X, goStone.coordinate.x)
            put(KEY_COORDINATE_Y, goStone.coordinate.y)
        }
        omokRepo.insert(record)
    }

    private fun disableBoard(board: TableLayout) {
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { it.isClickable = false }
    }

    private fun setWinnerText(winnerTextView: TextView, color: GoStoneColor, placementState: PlacementState) {
        winnerTextView.text = when (placementState) {
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
