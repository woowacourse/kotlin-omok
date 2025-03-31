package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import rule.type.Violation
import woowacourse.omok.domain.GameResultDialog
import woowacourse.omok.domain.db.OmokRepository
import woowacourse.omok.domain.omokboard.ColumnPosition
import woowacourse.omok.domain.omokboard.OmokBoard
import woowacourse.omok.domain.omokboard.PlayingBoard
import woowacourse.omok.domain.omokboard.Position
import woowacourse.omok.domain.omokboard.RowPosition
import woowacourse.omok.domain.placeresult.GameFinish
import woowacourse.omok.domain.placeresult.GameOnGoing
import woowacourse.omok.domain.placeresult.InvalidMove
import woowacourse.omok.domain.placeresult.PlaceResult
import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.player.StoneColor
import woowacourse.omok.domain.rule.OmokRule
import woowacourse.omok.domain.rule.RuleNavigation
import woowacourse.omok.domain.service.OmokGame

class MainActivity : AppCompatActivity() {
    private lateinit var board: TableLayout
    private lateinit var playingBoard: PlayingBoard
    private lateinit var omokGame: OmokGame
    private lateinit var omokRepository: OmokRepository
    private lateinit var gameResultDialog: GameResultDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        board = findViewById(R.id.board)
        playingBoard = PlayingBoard(OmokBoard(), RuleNavigation(OmokRule.whiteRules, OmokRule.blackRules))
        omokGame = OmokGame(playingBoard)
        omokRepository = OmokRepository(this)

        board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, imageView ->
                val row = index / 15
                val column = index % 15

                omokRepository.queryStoneAt(row, column)?.let { color ->
                    val stoneColor = StoneColor.valueOf(color)
                    playingBoard.placeStone(PlayerStone(stoneColor, Position(RowPosition(row + 1), ColumnPosition(column + 1))))
                    stoneColor.toUi(imageView)
                }

                imageView.setOnClickListener {
                    val currentColor = omokGame.currentStoneColor.name
                    omokRepository.insertStone(currentColor, row, column)
                    omokGame.start(Position(RowPosition(row + 1), ColumnPosition(column + 1))) { placeResult ->
                        handlePlaceResult(placeResult, imageView)
                    }
                }
            }
    }

    private fun resetBoard() {
        board.children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEach { it.setImageResource(0) }

        playingBoard.board = OmokBoard()
    }

    private fun handlePlaceResult(
        placeResult: PlaceResult,
        imageView: ImageView,
    ) {
        when (placeResult) {
            is GameOnGoing -> updateStone(imageView)
            is GameFinish -> {
                updateStone(imageView)
                gameResultDialog = GameResultDialog(this, omokRepository, ::resetBoard)
                gameResultDialog.popUp(placeResult.gameResult)
            }
            is InvalidMove.AlreadyExistStone -> showToast(displayMisPlaceMessage(placeResult))
            is InvalidMove.ExternalRenjuRule -> showToast(displayForbiddenMessage(placeResult.rule))
        }
    }

    private fun displayMisPlaceMessage(error: PlaceResult): String {
        return when (error) {
            InvalidMove.AlreadyExistStone -> getString(R.string.ALREADY_EXIST_MESSAGE)
            InvalidMove.InvalidPosition -> getString(R.string.INVALID_POSITION_MESSAGE)
            else -> ""
        }
    }

    private fun displayForbiddenMessage(violation: Violation): String {
        return when (violation) {
            Violation.DOUBLE_THREE -> getString(R.string.FORBIDDEN_DOUBLE_THREE)
            Violation.DOUBLE_FOUR -> getString(R.string.FORBIDDEN_DOUBLE_FOUR)
            Violation.OVERLINE -> getString(R.string.FORBIDDEN_OVERLINE)
            else -> ""
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun updateStone(imageView: ImageView) {
        omokGame.currentStoneColor.toUi(imageView)
    }

    private fun StoneColor.toUi(imageView: ImageView) {
        when (this) {
            StoneColor.BLACK -> imageView.setImageResource(R.drawable.black_stone)
            StoneColor.WHITE -> imageView.setImageResource(R.drawable.white_stone)
        }
    }

    override fun onDestroy() {
        omokRepository.close()

        super.onDestroy()
    }
}
