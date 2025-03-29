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
import woowacourse.omok.domain.model.rule.judge.JudgeResult.NotFinished
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
            val omokGame = withContext(Dispatchers.IO) { omokGameUseCase() }
            updateStonesUI(omokGame.board)
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

    private fun updateStonesUI(board: OmokBoard) {
        forEachBoardPoint { rowIndex, columnIndex, point ->
            val position = Position(rowIndex, columnIndex)
            val state = board.find(position)

            point.setImageResource(
                when (state) {
                    PointState.OCCUPIED_BLACK -> drawable.black_stone
                    PointState.OCCUPIED_WHITE -> drawable.white_stone
                    else -> 0
                },
            )
        }
    }

    private fun forEachBoardPoint(event: (rowIndex: Int, columnIndex: Int, point: ImageView) -> Unit) {
        binding.board.children.filterIsInstance<TableRow>().forEachIndexed { rowIndex, row ->
            row.children.filterIsInstance<ImageView>().forEachIndexed { columnIndex, point ->
                event(rowIndex + 1, columnIndex + 1, point)
            }
        }
    }

    private fun setupClickListeners(omokGame: OmokGame) {
        setupPointClickListener(omokGame)
        setupRestartClickListener(omokGame)
    }

    private fun setupPointClickListener(omokGame: OmokGame) {
        forEachBoardPoint { rowIndex, columnIndex, point ->
            point.setOnClickListener {
                val position = Position(rowIndex, columnIndex)
                val playerStone = PlayerStone(omokGame.currentTurn, position)

                when (val result = omokGame.placeStone(position)) {
                    is Success -> {
                        updateStoneUI(point, playerStone)
                        handleJudge(omokGame, playerStone)
                    }

                    is Failure -> showSnackBar(getFailureMessage(result))
                }
            }
        }
    }

    private fun updateStoneUI(
        button: ImageView,
        playerStone: PlayerStone,
    ) {
        button.setImageResource(
            when (playerStone.color) {
                StoneColor.BLACK -> drawable.black_stone
                StoneColor.WHITE -> drawable.white_stone
            },
        )
    }

    private fun handleJudge(
        omokGame: OmokGame,
        playerStone: PlayerStone,
    ) {
        when (val judgeResult = omokGame.judge(playerStone)) {
            is Finished -> {
                updateBoardActivation(false)
                showResultDialog(getJudgeMessage(judgeResult))
            }

            is NotFinished -> {
                omokGame.reverseTurn()
                saveOmokGame(omokGame)
            }
        }
    }

    private fun updateBoardActivation(isEnabled: Boolean) {
        forEachBoardPoint { _, _, point ->
            point.isEnabled = isEnabled
        }
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

    private fun saveOmokGame(omokGame: OmokGame) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                omokGameRepository.saveGame(OmokGameEntity(omokGame.currentTurn, omokGame.board))
            }
        }
    }

    private fun getJudgeMessage(result: Finished): String =
        when (result) {
            is Finished.Win -> getString(string.omok_winning, result.stone.toText())
            is Finished.Draw -> getString(string.omok_draw)
        }

    private fun StoneColor.toText(): String =
        when (this) {
            StoneColor.BLACK -> getString(string.omok_black_label)
            StoneColor.WHITE -> getString(string.omok_white_label)
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
            deleteOmokGame()
            omokGame.restart()
            updateStonesUI(omokGame.board)
            updateBoardActivation(true)
            showSnackBar(getString(string.omok_game_restart))
        }
    }

    private fun deleteOmokGame() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                omokGameRepository.deleteGame()
            }
        }
    }
}
