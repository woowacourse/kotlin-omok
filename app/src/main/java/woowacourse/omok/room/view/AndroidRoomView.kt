package woowacourse.omok.room.view

import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.room.domain.Stage
import woowacourse.omok.room.domain.Stages
import woowacourse.omok.room.domain.User
import woowacourse.omok.room.domain.Users

class AndroidRoomView(
    private val usersRecycler: RecyclerView,
    private val stagesRecycler: RecyclerView,
    private val userAdd: Button,
    private val stageAdd: Button,
    private val roomMessage: TextView
) {
    val userAddObserver: RoomEventObserver = RoomEventObserver()
    val stageAddObserver: RoomEventObserver = RoomEventObserver()

    fun setUp(onSelectUser: (User) -> Unit, onSelectStage: (Stage) -> Unit) {
        usersRecycler.adapter = UserAdapter(Users(listOf())) {
            onSelectUser(it)
            roomMessage.text = MESSAGE_USER_SELECT.format(it.name)
        }

        stagesRecycler.adapter = StageAdapter(Stages(listOf())) {
            onSelectStage(it)
            roomMessage.text = MESSAGE_STAGE_SELECT.format(it.id.toString())
        }

        userAdd.setOnClickListener {
            userAddObserver.notifyEvent()
        }

        stageAdd.setOnClickListener {
            stageAddObserver.notifyEvent()
        }
    }

    fun setAllUsers(users: Users) {
        (usersRecycler.adapter as UserAdapter?)?.users = users
        (usersRecycler.adapter as UserAdapter?)?.notifyDataSetChanged()
    }

    fun setAllStages(stages: Stages) {
        (stagesRecycler.adapter as StageAdapter?)?.stages = stages
        (stagesRecycler.adapter as StageAdapter?)?.notifyDataSetChanged()
    }

    companion object {
        const val MESSAGE_USER_SELECT = "선택한 유저 : %s"
        const val MESSAGE_STAGE_SELECT = "선택한 방 : %s"
    }
}
