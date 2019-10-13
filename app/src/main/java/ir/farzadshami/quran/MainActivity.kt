package ir.farzadshami.quran

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    companion object {
        private const val ID_DOWNLOAD = 1
        private const val ID_SEARCH = 2
        private const val ID_HOME = 3
        private const val ID_BOOKMARK = 4
        private const val ID_SETTINGS = 5
        private var fragment: Fragment? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation.add(MeowBottomNavigation.Model(ID_DOWNLOAD, R.drawable.ic_download))
        bottomNavigation.add(MeowBottomNavigation.Model(ID_SEARCH, R.drawable.ic_search))
        bottomNavigation.add(MeowBottomNavigation.Model(ID_HOME, R.drawable.ic_home))
        bottomNavigation.add(MeowBottomNavigation.Model(ID_BOOKMARK, R.drawable.ic_bookmark))
        bottomNavigation.add(MeowBottomNavigation.Model(ID_SETTINGS, R.drawable.ic_settings))

        bottomNavigation.show(ID_HOME)

        bottomNavigation.setOnShowListener {
            fragment = when (it.id) {
                ID_HOME -> SuraListActivity()
                ID_DOWNLOAD -> DownloadMangerActivity()
                ID_BOOKMARK -> FavoriteActivity()
                ID_SEARCH -> SearchActivity()
                else -> Kossher()
            }
            Timer("SettingUp", false).schedule(500) {
                if (fragment != null) {
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.my_fragment, fragment!!).commit()
                }
            }

        }

        /*bottomNavigation.setOnClickMenuListener {
            when (it.id) {
                ID_HOME -> {
                    fragment = SuraListActivity()
                }
                ID_BOOKMARK -> {
                    fragment = SuraListActivity()
                }
            }
            if (fragment != null) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.my_fragment, fragment!!).commit()
            }
        }*/
    }

}