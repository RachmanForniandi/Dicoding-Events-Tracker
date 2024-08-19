package rachman.forniandi.dicodingeventstracker.domain.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import rachman.forniandi.dicodingeventstracker.R
import rachman.forniandi.dicodingeventstracker.databinding.ActivityMainBinding
import rachman.forniandi.dicodingeventstracker.domain.search.SearchEventFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainAppBar)

        val navBottomView = binding.bottomNavView
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_container) as NavHostFragment
        navController = navHostFragment.navController
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.upcomingEventsFragment,
                R.id.pastEventsFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navBottomView.setupWithNavController(navController)

        with(binding){
            mainAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_search -> {
                        startActivity(Intent(this@MainActivity, SearchEventFragment::class.java))
                        this@MainActivity.finish()
                        true
                    }

                    R.id.menu_setting->{
                        val intentSetting = Intent(Settings.ACTION_LOCALE_SETTINGS)
                        startActivity(intentSetting)
                        true
                    }
                    else -> false
                }
            }
        }


    }

    /*override fun onCreateOptionsMenu(item: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main,item)
        item.findItem(R.id.action_search).title = "Search events"
        return super.onCreateOptionsMenu(item)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                startActivity(Intent(this, SearchEventFragment::class.java))
                this.finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }*/

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()|| super.onSupportNavigateUp()

    }

}