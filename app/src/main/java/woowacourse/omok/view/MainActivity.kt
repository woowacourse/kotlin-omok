package woowacourse.omok.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import woowacourse.omok.R
import woowacourse.omok.view.games.GamesFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setWindowInsets()

        replaceFragment(GamesFragment())
    }

    private fun setWindowInsets() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun replaceFragment(
        fragment: Fragment,
        backStackName: String = "",
    ) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_main, fragment)
        if (backStackName.isNotEmpty()) transaction.addToBackStack(backStackName)
        transaction.commit()
    }
}
