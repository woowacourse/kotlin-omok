package woowacourse.omok.ui.game

import android.content.Context
import android.content.Intent
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
import woowacourse.omok.R.drawable
import woowacourse.omok.R.string
import woowacourse.omok.databinding.ActivityOmokGameBinding
import woowacourse.omok.domain.model.game.OmokGameEntity.Companion.DEFAULT_GAME_ID
import woowacourse.omok.domain.model.game.OmokGameManager
import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.omokboard.PointState
import woowacourse.omok.domain.model.omokboard.Position
import woowacourse.omok.domain.model.player.PlayerStone
import woowacourse.omok.domain.model.player.StoneColor
import woowacourse.omok.domain.model.rule.judge.JudgeResult
import woowacourse.omok.domain.model.rule.judge.JudgeResult.Finished
import woowacourse.omok.domain.model.rule.place.PlaceResult.Failure
import woowacourse.omok.domain.model.rule.place.PlaceResult.Success

class OmokGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOmokGameBinding
    private lateinit var omokGameManager: OmokGameManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()

        val gameId = intent.getIntExtra(GAME_ID, DEFAULT_GAME_ID)
        omokGameManager = OmokGameManager(applicationContext, gameId)
        updateStonesUI(omokGameManager.board)
        setupClickListeners()
    }

    private fun setupView() {
        binding = ActivityOmokGameBinding.inflate(layoutInflater)
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

    private fun setupClickListeners() {
        setupPointClickListener()
        setupRestartClickListener()
    }

    private fun setupPointClickListener() {
        forEachBoardPoint { rowIndex, columnIndex, point ->
            point.setOnClickListener {
                val position = Position(rowIndex, columnIndex)
                when (val result = omokGameManager.placeStone(position)) {
                    is Success -> {
                        val playerStone = PlayerStone(omokGameManager.omokGame.currentTurn, position)
                        updateStoneUI(point, playerStone)
                        handleJudge(playerStone)
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

    private fun handleJudge(playerStone: PlayerStone) {
        val result: JudgeResult = omokGameManager.judgeMove(playerStone)

        if (result is Finished) {
            updateBoardActivation(false)
            showResultDialog(getJudgeMessage(result))
        }
    }

    private fun updateBoardActivation(isActive: Boolean) {
        forEachBoardPoint { _, _, point ->
            point.isEnabled = isActive
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

    private fun setupRestartClickListener() {
        binding.btnOmokRestart.setOnClickListener {
            omokGameManager.restartGame()
            updateStonesUI(omokGameManager.omokGame.game.board)
            updateBoardActivation(true)
            showSnackBar(getString(string.omok_game_restart))
        }
    }

    companion object {
        private const val GAME_ID = "GAME_ID"

        fun getIntent(
            context: Context,
            gameId: Int,
        ): Intent =
            Intent(context, OmokGameActivity::class.java).apply {
                putExtra(GAME_ID, gameId)
            }
    }
}
