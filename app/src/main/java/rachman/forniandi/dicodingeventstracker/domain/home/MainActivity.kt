package rachman.forniandi.dicodingeventstracker.domain.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import rachman.forniandi.dicodingeventstracker.R
import rachman.forniandi.dicodingeventstracker.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val navBottomView = binding.bottomNavView
        setContentView(binding.root)
        supportActionBar?.elevation=0.0f
        setSupportActionBar(binding.toolbarMain)

        navController = findNavController(R.id.nav_host_container)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.upcomingEventsFragment,
                R.id.pastEventsFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navBottomView.setupWithNavController(navController)
    }

}