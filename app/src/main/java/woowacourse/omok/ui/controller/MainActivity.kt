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
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import woowacourse.omok.OmokApplication
import woowacourse.omok.R.drawable
import woowacourse.omok.R.string
import woowacourse.omok.databinding.ActivityMainBinding
import woowacourse.omok.domain.model.game.OmokGameEntity
import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.omokboard.OmokGame
import woowacourse.omok.domain.model.omokboard.PointState
import woowacourse.omok.domain.model.omokboard.Position
import woowacourse.omok.domain.model.player.PlayerStone
import woowacourse.omok.domain.model.player.StoneColor
import woowacourse.omok.domain.model.rule.judge.JudgeResult.Finished
import woowacourse.omok.domain.model.rule.place.PlaceResult.Failure
import woowacourse.omok.domain.model.rule.place.PlaceResult.Success
import woowacourse.omok.domain.repository.OmokGameRepository
import woowacourse.omok.domain.usecase.GetOmokGameUseCase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val omokGameRepository: OmokGameRepository
        get() = (application as OmokApplication).omokGameRepository

    private val omokGameUseCase: GetOmokGameUseCase
        get() = (application as OmokApplication).getOmokGameUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()

        lifecycleScope.launch {
            val omokGame =
                withContext(Dispatchers.IO) {
                    omokGameUseCase()
                }
            updateLastBoardUI(omokGame.board)
            setupClickListeners(omokGame)
        }
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

    private fun setupClickListeners(omokGame: OmokGame) {
        setupPointClickListener(omokGame)
        setupRestartClickListener(omokGame)
    }

    private fun setupPointClickListener(omokGame: OmokGame) {
        binding.board.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, row ->
            row.children.filterIsInstance<ImageView>().forEachIndexed { colIndex, button ->
                button.setOnClickListener {
                    val position = Position(rowIndex + 1, colIndex + 1)
                    val playerStone = PlayerStone(omokGame.currentTurn, position)

                    when (val result = omokGame.placeStone(position = position)) {
                        is Success -> handlePlaceSuccess(button, playerStone, omokGame)
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
    ) {
        button.setImageResource(
            when (omokGame.currentTurn) {
                StoneColor.BLACK -> drawable.black_stone
                StoneColor.WHITE -> drawable.white_stone
            },
        )

        handleJudge(omokGame, playerStone)
        omokGame.reverseTurn()
        showSnackBar(getString(string.omok_turn, omokGame.currentTurn.toText()))

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                omokGameRepository.saveGame(OmokGameEntity(omokGame.currentTurn, omokGame.board))
            }
        }
    }

    private fun StoneColor.toText(): String =
        when (this) {
            StoneColor.BLACK -> getString(string.omok_black_label)
            StoneColor.WHITE -> getString(string.omok_white_label)
        }

    private fun handleJudge(
        omokGame: OmokGame,
        playerStone: PlayerStone,
    ) {
        val judgeResult = omokGame.judge(playerStone = playerStone)

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
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    omokGameRepository.deleteGame()
                }
            }
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
