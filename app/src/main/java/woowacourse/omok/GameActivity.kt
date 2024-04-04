package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.domain.controller.FinishedObserver
import woowacourse.omok.domain.controller.InvalidPositionHandler
import woowacourse.omok.domain.controller.NextPositionListener
import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.OmokGame
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition
import woowacourse.omok.database.OmokTurnDao
import woowacourse.omok.database.OmokTurnDbHelper
import woowacourse.omok.database.toStonePosition
import woowacourse.omok.domain.model.state.AlreadyHaveStone
import woowacourse.omok.domain.model.state.ForbiddenPosition
import woowacourse.omok.domain.model.state.GameState
import woowacourse.omok.domain.model.toOmokTurn
import woowacourse.omok.domain.view.output

class GameActivity : AppCompatActivity() {
    private lateinit var omokTurnDao: OmokTurnDao
    private lateinit var omokBoard: Board
    private lateinit var omokGame: OmokGame

    private val invalidPositionHandler = initInvalidPositionHandler()
    private val finishedObserver = initFinishedObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        omokBoard = Board()
        omokGame = OmokGame(omokBoard)

        omokTurnDao = OmokTurnDao(OmokTurnDbHelper(this))
        initBoard(omokTurnDao.findAll().map { it.toStonePosition() })
    }

    private fun initBoard(loadedStonePositions: List<StonePosition>) {
        val boardView: TableLayout = findViewById(R.id.board)
        boardView
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { row, views ->
                views.children.filterIsInstance<ImageView>()
                    .forEachIndexed { col, view ->
                        val currentPosition = Position(row, col)
                        loadBoardData(loadedStonePositions, currentPosition, view)
                        placeStoneListener(view, currentPosition)
                    }
            }
    }

    private fun loadBoardData(
        stonePositions: List<StonePosition>,
        position: Position,
        view: ImageView,
    ) {
        stonePositions.find { it.position == position }?.let {
            changeStoneUI(view, it.stone)
        }
    }

    private fun placeStoneListener(
        view: ImageView,
        currentPosition: Position,
    ) {
        view.setOnClickListener {
            omokGame.gameTurn(
                nextPositionListener =
                object : NextPositionListener {
                    override fun nextPosition(gameState: GameState): Position = currentPosition

                    override fun nextStonePositionCallback(gameState: GameState) {
                        val latestStone = gameState.latestStone()
                        changeStoneUI(view, latestStone)
                        omokTurnDao.save(StonePosition(currentPosition, latestStone).toOmokTurn())
                    }
                },
                invalidPositionHandler = invalidPositionHandler,
                finishedObserver = finishedObserver,
            )
            return@setOnClickListener
        }
    }

    private fun initInvalidPositionHandler(): InvalidPositionHandler =
        InvalidPositionHandler { inValidStonePosition, invalidPositionState ->
            when (invalidPositionState) {
                is ForbiddenPosition -> showToastInvalidPosition(inValidStonePosition, "금수 규칙에 따라 둘 수 없습니다.")
                is AlreadyHaveStone -> showToastInvalidPosition(inValidStonePosition, "이미 돌이 있는 위치이므로 둘 수 없습니다.")
            }
        }

    private fun initFinishedObserver() =
        FinishedObserver { gameState ->
            val latestStone = gameState.latestStone()
            showStoneToast(latestStone, "이 승리했습니다.")
            omokBoard = Board()
            omokTurnDao.clearAll()
            startActivity(
                Intent(this, RestartActivity::class.java).apply {
                    putExtra("winner", gameState.latestStone().output())
                })

            this.finish()
        }

    private fun showToastInvalidPosition(
        inValidStonePosition: StonePosition,
        message: String = "",
    ) {
        Toast.makeText(
            this,
            "${inValidStonePosition.stone.output()}이 두려는 위치는 위치${inValidStonePosition.position.output()}는 $message",
            Toast.LENGTH_SHORT,
        ).show()
    }

    private fun showStoneToast(
        stone: Stone,
        message: String,
    ) {
        Toast.makeText(this, "${stone.output()} $message.", Toast.LENGTH_SHORT).show()
    }

    private fun changeStoneUI(
        view: ImageView,
        recentPlayerStone: Stone,
    ) {
        view.setImageResource(
            when (recentPlayerStone) {
                Stone.BLACK -> R.drawable.black_stone
                Stone.WHITE -> R.drawable.white_stone
                Stone.NONE -> throw IllegalArgumentException("NONE은 둘 수 없습니다.")
            },
        )
    }
}
