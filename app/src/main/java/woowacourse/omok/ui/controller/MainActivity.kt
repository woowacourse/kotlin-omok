package woowacourse.omok.ui.controller

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableRow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import com.google.android.material.snackbar.Snackbar
import woowacourse.omok.OmokApplication
import woowacourse.omok.R.drawable
import woowacourse.omok.R.string
import woowacourse.omok.databinding.ActivityMainBinding
import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.omokboard.OmokGame
import woowacourse.omok.domain.model.omokboard.PointState
import woowacourse.omok.domain.model.omokboard.Position
import woowacourse.omok.domain.model.player.PlayerStone
import woowacourse.omok.domain.model.player.StoneColor
import woowacourse.omok.domain.model.rule.judge.DrawRule
import woowacourse.omok.domain.model.rule.judge.JudgeResult.Finished
import woowacourse.omok.domain.model.rule.judge.JudgeRule
import woowacourse.omok.domain.model.rule.judge.WinningRule
import woowacourse.omok.domain.model.rule.place.AlreadyExistStoneRule
import woowacourse.omok.domain.model.rule.place.ExternalRule
import woowacourse.omok.domain.model.rule.place.InvalidPositionRule
import woowacourse.omok.domain.model.rule.place.PlaceResult.Failure
import woowacourse.omok.domain.model.rule.place.PlaceResult.Success
import woowacourse.omok.domain.model.rule.place.PlaceRule
import woowacourse.omok.domain.repository.OmokRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var omokRepository: OmokRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        omokRepository = (application as OmokApplication).omokRepository
        setupView()

        val omokGame = setupOmokGame()
        updateLastBoardUI(omokGame.board)

        val placeRules = listOf(InvalidPositionRule(), AlreadyExistStoneRule(), ExternalRule())
        val judgeRules = listOf(WinningRule(), DrawRule())

        setupClickListeners(omokGame, placeRules, judgeRules)
    }

    private fun setupView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupOmokGame(): OmokGame {
        val lastBoard = omokRepository.loadBoard() ?: OmokBoard.create()
        val lastTurn = omokRepository.loadLastTurn() ?: StoneColor.BLACK

        val omokGame = OmokGame(lastBoard, lastTurn)
        return omokGame
    }

    private fun updateLastBoardUI(board: OmokBoard) {
        binding.board.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, row ->
            row.children.filterIsInstance<ImageView>().forEachIndexed { colIndex, button ->
                val position = Position(rowIndex + 1, colIndex + 1)
                val state = board.find(position)

                when (state) {
                    PointState.OCCUPIED_BLACK -> button.setImageResource(drawable.black_stone)
                    PointState.OCCUPIED_WHITE -> button.setImageResource(drawable.white_stone)
                    else -> Unit
                }
            }
        }
    }

    private fun setupClickListeners(
        omokGame: OmokGame,
        placeRules: List<PlaceRule>,
        judgeRules: List<JudgeRule>,
    ) {
        setupPointClickListener(omokGame, placeRules, judgeRules)
        setupRestartClickListener(omokGame)
    }

    private fun setupPointClickListener(
        omokGame: OmokGame,
        placeRules: List<PlaceRule>,
        judgeRules: List<JudgeRule>,
    ) {
        binding.board.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, row ->
            row.children.filterIsInstance<ImageView>().forEachIndexed { colIndex, button ->
                button.setOnClickListener {
                    val position = Position(rowIndex + 1, colIndex + 1)
                    val playerStone = PlayerStone(omokGame.currentTurn, position)

                    when (val result = omokGame.placeStone(placeRules, position)) {
                        is Success -> handlePlaceSuccess(button, playerStone, omokGame, judgeRules)
                        is Failure -> showSnackBar(getFailureMessage(result))
                    }
                }
            }
        }
    }

    private fun handlePlaceSuccess(
        button: ImageView,
        playerStone: PlayerStone,
        omokGame: OmokGame,
        judgeRules: List<JudgeRule>,
    ) {
        button.setImageResource(
            when (omokGame.currentTurn) {
                StoneColor.BLACK -> drawable.black_stone
                StoneColor.WHITE -> drawable.white_stone
            },
        )

        handleJudge(omokGame, playerStone, judgeRules)
        omokGame.reverseTurn()
        showSnackBar(getString(string.omok_turn, omokGame.currentTurn.toText()))

        omokRepository.saveLastTurn(omokGame.currentTurn)
        omokRepository.saveBoard(omokGame.board)
    }

    private fun StoneColor.toText(): String =
        when (this) {
            StoneColor.BLACK -> getString(string.omok_black_label)
            StoneColor.WHITE -> getString(string.omok_white_label)
        }

    private fun handleJudge(
        omokGame: OmokGame,
        playerStone: PlayerStone,
        judgeRules: List<JudgeRule>,
    ) {
        val judgeResult = omokGame.judge(judgeRules, playerStone)

        if (judgeResult is Finished) {
            disableBoard()
            showResultDialog(getJudgeMessage(judgeResult))
        }
    }

    private fun disableBoard() =
        with(binding.board) {
            children
                .filterIsInstance<TableRow>()
                .flatMap { it.children }
                .filterIsInstance<ImageView>()
                .forEach { it.isEnabled = false }
        }

    private fun showResultDialog(message: String) {
        AlertDialog
            .Builder(this)
            .setTitle(getString(string.omok_game_end))
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(getString(string.omok_exit_alert)) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun getJudgeMessage(result: Finished): String =
        when (result) {
            is Finished.Win -> getString(string.omok_winning, result.stone.toText())
            is Finished.Draw -> getString(string.omok_draw)
        }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun getFailureMessage(result: Failure): String =
        when (result) {
            Failure.AlreadyExistStone -> getString(string.omok_already_exist_stone_error)
            Failure.DoubleFourViolation -> getString(string.omok_double_four_error)
            Failure.DoubleThreeViolation -> getString(string.omok_double_three_error)
            Failure.InvalidPosition -> getString(string.omok_invalid_position_error)
            Failure.OverlineViolation -> getString(string.omok_overline_error)
        }

    private fun setupRestartClickListener(omokGame: OmokGame) {
        binding.btnOmokRestart.setOnClickListener {
            omokRepository.clearGameData()
            omokGame.restart()
            binding.board.children.forEach { row ->
                (row as TableRow).children.forEach { point ->
                    (point as ImageView).setImageResource(0)
                    point.isEnabled = true
                }
            }
            showSnackBar(getString(string.omok_game_restart))
        }
    }
}
