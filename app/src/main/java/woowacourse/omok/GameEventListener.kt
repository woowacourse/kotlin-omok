package woowacourse.omok

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import domain.player.Players
import domain.point.Point
import domain.stone.StoneColor
import listener.OmokGameEventListener
import view.mapper.toPresentation

class GameEventListener(private val context: Context, private val view: TextView) : OmokGameEventListener {
    override fun onStartGame() {
        Toast.makeText(context, "오목 게임을 시작합니다.", Toast.LENGTH_LONG).show()
    }

    override fun onEndGame(stoneColor: StoneColor) {
        Toast.makeText(context, "오목 게임이 종료되었습니다.", Toast.LENGTH_LONG).show()
        view.text = "%s의 승리입니다.".format(stoneColor.toPresentation().text)
    }

    override fun onStartTurn(stoneColor: StoneColor, point: Point?) {
        view.text = "%s의 차례입니다.".format(stoneColor.toPresentation())
    }

    override fun onEndTurn(players: Players) {
    }


}