package woowacourse.omok

import android.util.Log
import android.widget.ImageView
import android.widget.TableRow
import androidx.core.view.children
import woowacourse.omok.data.OmokDataStore
import woowacourse.omok.data.OmokDataStoreImpl
import woowacourse.omok.data.adapter.OmokEntityAdapter
import woowacourse.omok.databinding.ActivityMainBinding
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.game.FinishAction
import woowacourse.omok.model.game.FinishType
import woowacourse.omok.model.game.OmokGame
import woowacourse.omok.model.game.OmokPlayers
import woowacourse.omok.model.game.TurnHistory
import woowacourse.omok.ui.BaseActivity
import woowacourse.omok.ui.message
import woowacourse.omok.ui.stoneImage

class MainActivity(private val boardSize: Int = 15) :
    BaseActivity<ActivityMainBinding>(),
    FinishAction {
    private val omokDataStore: OmokDataStore by lazy { OmokDataStoreImpl(this) }
    private lateinit var omokGame: OmokGame

    override fun onInit() {
        initializeUI()
    }

    override fun viewBinding() = ActivityMainBinding.inflate(layoutInflater)

    private fun initializeUI() {
        val omokEntities = omokDataStore.omokEntities()
        omokGame =
            OmokGame(
                OmokEntityAdapter.Board(boardSize, omokEntities),
                this,
                TurnHistory(OmokPlayers(), omokDataStore.lastStonePosition()),
            )
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
            omokGame.progressTurn(this, index)
        }
    }

    private fun OmokGame.progressTurn(
        stoneImageView: ImageView,
        index: Int,
    ) {
        if (isFinish()) return

        val position = Position(index / boardSize, index % boardSize)
        val placeType = turn(position)
        if (!placeType.canPlace()) {
            showToast(resources.getString(placeType.message()))
            return
        }
        omokDataStore.add(position, placeType.stone)
        stoneImageView.setImageResource(placeType.stone.stoneImage())
        if (!omokGame.isFinish()) setResultText()
    }

    private fun restartGame() {
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
        omokDataStore.save(omokGame.isFinish())
    }

    override fun onFinish(finishType: FinishType) {
        setResultText(finishType)
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
    }
}
