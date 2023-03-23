package woowacourse.omok.controller

import dto.StoneDTO
import woowacourse.omok.db.OmokDBHelper
import woowacourse.omok.domain.Stage
import woowacourse.omok.domain.User
import woowacourse.omok.view.AndroidRoomView

class RoomController(
    private val omokDBHelper: OmokDBHelper,
    private val roomView: AndroidRoomView
) {
    fun process() {
        roomView.setUp(::onSelectUser, ::onSelectStage)
        getAllUsers()
    }

    private fun getAllUsers() {
        val users = omokDBHelper.selectAllUsers()
        roomView.setAllUsers(users)
        getAllStagesByUserId(users.value[0])
    }

    private fun getAllStagesByUserId(user: User) {
        val stages = omokDBHelper.selectAllStagesByUserId(user)
        roomView.setAllStages(stages)
    }

    fun onPlaceStone(stoneDTO: StoneDTO, stage: Stage) {
        omokDBHelper.insertStoneToStage(stoneDTO, stage)
    }

    private fun onSelectUser(user: User) {
        getAllStagesByUserId(user)
    }

    private fun onSelectStage(stage: Stage) {
        // notify to game controller with stage
    }
}
