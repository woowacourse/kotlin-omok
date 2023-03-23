package woowacourse.omok.view

import androidx.recyclerview.widget.RecyclerView
import woowacourse.omok.domain.Stage
import woowacourse.omok.domain.Stages
import woowacourse.omok.domain.User
import woowacourse.omok.domain.Users

class AndroidRoomView(
    private val usersRecycler: RecyclerView,
    private val stagesRecycler: RecyclerView
) {
    fun setUp(onSelectUser: (User) -> Unit, onSelectStage: (Stage) -> Unit) {
        usersRecycler.adapter = UserAdapter(Users(listOf()), onSelectUser)
        stagesRecycler.adapter = StageAdapter(Stages(listOf()), onSelectStage)
    }

    fun setAllUsers(users: Users) {
        (usersRecycler.adapter as UserAdapter?)?.users = users
        (usersRecycler.adapter as UserAdapter?)?.notifyDataSetChanged()
    }

    fun setAllStages(stages: Stages) {
        (stagesRecycler.adapter as StageAdapter?)?.stages = stages
        (stagesRecycler.adapter as StageAdapter?)?.notifyDataSetChanged()
    }
}
