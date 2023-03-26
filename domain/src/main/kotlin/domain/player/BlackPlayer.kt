package domain.player

import domain.point.Point
import domain.point.Points
import domain.rule.OmokRule
import domain.state.PlayerState
import domain.state.PlayingState
import domain.stone.StoneColor

class BlackPlayer(state: PlayerState = PlayingState(), rule: OmokRule) : Player(state, rule) {
    override fun putPoint(point: Point, otherPoints: Points): Player = BlackPlayer(state.add(point, otherPoints, rule), rule)
    override fun getLatestPlayer(player: Player): Player {
        if(player.getPointSize() == getPointSize()) return this
        return player
    }

    override fun getColor(): StoneColor = StoneColor.BLACK
}
