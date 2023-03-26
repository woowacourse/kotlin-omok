package domain.player

import domain.point.Point
import domain.point.Points
import domain.rule.OmokRule
import domain.state.PlayerState
import domain.state.PlayingState
import domain.stone.StoneColor

class WhitePlayer(state: PlayerState = PlayingState(), rule: OmokRule) : Player(state, rule) {
    override fun putPoint(point: Point, otherPoints: Points): Player = WhitePlayer(state.add(point, otherPoints, rule), rule)
    override fun getLatestPlayer(player: Player): Player {
        if(player.getPointSize() == getPointSize()) return player
        return this
    }
    override fun getColor(): StoneColor = StoneColor.WHITE
}
