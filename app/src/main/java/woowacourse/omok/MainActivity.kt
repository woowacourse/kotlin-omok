package woowacourse.omok

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import woowacourse.omok.data.OmokDataStoreImpl
import woowacourse.omok.data.adapter.OmokEntityAdapter
import woowacourse.omok.data.`interface`.OmokDataStore
import woowacourse.omok.databinding.ActivityMainBinding
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.game.FinishAction
import woowacourse.omok.model.game.FinishType
import woowacourse.omok.model.game.OmokGame
import woowacourse.omok.model.game.OmokPlayers
import woowacourse.omok.model.game.PlaceType
import woowacourse.omok.model.game.TurnHistory
import woowacourse.omok.ui.message
import woowacourse.omok.ui.stoneImage

class MainActivity(private val boardSize: Int = 15) : AppCompatActivity(), FinishAction {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val omokDataStore: OmokDataStore by lazy { OmokDataStoreImpl(this) }
    private lateinit var omokGame: OmokGame
    private var isFinish = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeUI()
    }

    private fun initializeUI() {
        val omokEntities = omokDataStore.omokEntities()
        val board = OmokEntityAdapter.Board(boardSize, omokEntities)
        omokGame = OmokGame(board, this, TurnHistory(OmokPlayers(), omokDataStore.lastStonePosition()))
        setResultText()

        stoneImageView { index, view ->
            view.setStoneViewOnClickListener(index)
            val omokEntity =
                omokEntities.firstOrNull { index == OmokEntityAdapter.index(boardSize, it) }
                    ?: return@stoneImageView
            view.setImageResource(OmokEntityAdapter.stone(omokEntity).stoneImage())
        }

        binding.restartButton.setOnClickListener {
            restartGame()
        }
    }

    private fun setResultText(finishType: FinishType = FinishType.NOT_FINISH) {
        binding.resultText.text =
            when (finishType) {
                FinishType.DRAW -> resources.getString(R.string.result_draw)
                FinishType.BLACK_PLAYER_WIN,
                FinishType.WHITE_PLAYER_WIN,
                -> {
                    resources.getString(R.string.result_win).format(finishType.stone.message(this))
                }

                FinishType.NOT_FINISH -> progressText()
            }
    }

    private fun progressText(): String {
        val progressText =
            resources.getString(R.string.turn_player).format(omokGame.nowOrderStone().message(this))
        return omokGame.recentPosition()?.run {
            "$progressText \n ${resources.getString(R.string.last_stone_position).format(row, col)}"
        } ?: progressText
    }

    private fun ImageView.setStoneViewOnClickListener(index: Int) {
        setOnClickListener {
            Log.d(TAG, "position (${index / boardSize}, ${index % boardSize})")
            if (isFinish) return@setOnClickListener
            omokGame.progressTurn(this, index)
        }
    }

    private fun OmokGame.progressTurn(
        stoneImageView: ImageView,
        index: Int,
    ) {
        val position = Position(index / boardSize, index % boardSize)
        val placeType = turn(position)
        if (placeType == PlaceType.CANNOT_PLACE) {
            showToast(resources.getString(R.string.cannot_place_position))
            return
        }
        omokDataStore.add(position, placeType.stone)
        stoneImageView.setImageResource(placeType.stone.stoneImage())
        setResultText()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun restartGame() {
        isFinish = false
        omokGame.restart()
        omokDataStore.reset()
        stoneImageView { _, view -> view.setImageResource(0) }
        setResultText()
    }

    private fun stoneImageView(block: (Int, ImageView) -> Unit) {
        binding.board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .forEachIndexed { index, view ->
                block(index, view)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        omokDataStore.save(isFinish)
    }

    override fun onFinish(finishType: FinishType) {
        isFinish = true
        setResultText(finishType)
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
    }
}
