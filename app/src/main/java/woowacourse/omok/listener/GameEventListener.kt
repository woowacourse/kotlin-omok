package woowacourse.omok.listener

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import domain.player.Players
import domain.point.Point
import domain.result.TurnResult
import domain.stone.StoneColor
import listener.OmokGameEventListener
import view.mapper.toPresentation
import woowacourse.omok.R

class GameEventListener(private val context: Context, private val view: TextView) : OmokGameEventListener {
    override fun onStartGame() {
        Toast.makeText(context, R.string.start_game, Toast.LENGTH_LONG).show()
    }

    override fun onEndGame(players: Players) {
        if (players.isPlaying) return
        Toast.makeText(context, R.string.end_game, Toast.LENGTH_LONG).show()
        if (players.isFoul) {
            view.text = context.getString(R.string.is_forbidden).plus(context.getString(R.string.who_is_winner).format(players.curPlayerColor.next().toPresentation().text))
            return
        }
        view.text = context.getString(R.string.who_is_winner).format(players.curPlayerColor.toPresentation().text)
    }

    override fun onStartTurn(stoneColor: StoneColor, point: Point?) {
        view.text = context.getString(R.string.who_is_turn).format(stoneColor.toPresentation().text)
    }

    override fun onEndTurn(result: TurnResult) {
        if(result is TurnResult.Failure) Toast.makeText(context, R.string.already_exist, Toast.LENGTH_LONG).show()
        view.text = context.getString(R.string.who_is_turn).format(result.players.curPlayerColor.toPresentation().text)
    }
}