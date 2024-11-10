package rachman.forniandi.dicodingeventstracker.domain.settings

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import rachman.forniandi.dicodingeventstracker.databinding.FragmentSettingsBinding
import rachman.forniandi.dicodingeventstracker.domain.settings.alarmWorker.EventAlarmWorker
import rachman.forniandi.dicodingeventstracker.domain.viewmodels.SettingThemeViewModel
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding?= null
    private val binding get() = _binding!!
    private lateinit var settingThemeViewModel: SettingThemeViewModel
    private lateinit var workManager: WorkManager
    private lateinit var periodicWorkRequest: PeriodicWorkRequest


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =FragmentSettingsBinding.inflate(inflater, container, false)
        settingThemeViewModel = ViewModelProvider(this)[SettingThemeViewModel::class.java]
        workManager = WorkManager.getInstance(requireActivity())
        setupSwitchTheme()
        return binding.root
    }

    private fun setupSwitchTheme() {
        with(binding) {

            settingThemeViewModel.obtainTheme().observe(viewLifecycleOwner){ isDarkThemeActivated->
                if (isDarkThemeActivated){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchChangeTheme.isChecked = true
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchChangeTheme.isChecked = false
                }
            }
            switchChangeTheme.setOnCheckedChangeListener { _, isChecked ->
                settingThemeViewModel.setTheme(isChecked)
            }


            settingThemeViewModel.getNotificationAlarmEvent().observe(viewLifecycleOwner){ isNotificationActive->
                if (isNotificationActive){
                    switchNotificationAlarm.isChecked = true
                    startPeriodicTask()
                }else{
                    switchNotificationAlarm.isChecked = false
                    cancelPeriodicTask()
                }
            }

            switchNotificationAlarm.setOnCheckedChangeListener { _, isChecked ->
                settingThemeViewModel.setNotificationAlarmEvent(isChecked)
            }
        }
    }

    private fun startPeriodicTask() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        periodicWorkRequest = PeriodicWorkRequest.Builder(EventAlarmWorker::class.java, 1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "DailyEventNotification",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }

    private fun cancelPeriodicTask() {
        workManager.cancelUniqueWork("DailyEventNotification")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnBackSetting.setOnClickListener {
                activity?.onBackPressed()
            }

            lineOptionSettingChangeLanguage.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}