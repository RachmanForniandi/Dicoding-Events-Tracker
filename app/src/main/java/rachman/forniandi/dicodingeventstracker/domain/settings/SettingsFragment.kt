package rachman.forniandi.dicodingeventstracker.domain.settings

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import dagger.hilt.android.AndroidEntryPoint
import rachman.forniandi.dicodingeventstracker.databinding.FragmentSettingsBinding
import rachman.forniandi.dicodingeventstracker.domain.settings.alarmWorker.EventAlarmWorker
import rachman.forniandi.dicodingeventstracker.domain.viewmodels.SettingThemeViewModel
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var settingThemeViewModel: SettingThemeViewModel
    private lateinit var workManager: WorkManager

    companion object {
        private const val WORK_NAME = "DailyEventNotification"
    }

    // ─── Runtime permission launcher (Android 13+) ───────────────────────────
    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                settingThemeViewModel.setNotificationAlarmEvent(true)
                Toast.makeText(
                    requireContext(),
                    getString(rachman.forniandi.dicodingeventstracker.R.string.notifications_permission_granted),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Revert the switch if permission was denied
                binding.switchNotificationAlarm.isChecked = false
                Toast.makeText(
                    requireContext(),
                    getString(rachman.forniandi.dicodingeventstracker.R.string.notifications_permission_rejected),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    // ─────────────────────────────────────────────────────────────────────────

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        settingThemeViewModel = ViewModelProvider(this)[SettingThemeViewModel::class.java]
        workManager = WorkManager.getInstance(requireActivity())
        setupSwitchTheme()
        observeWorkStatus()
        return binding.root
    }

    private fun setupSwitchTheme() {
        with(binding) {

            // ── Theme switch ──────────────────────────────────────────────────
            settingThemeViewModel.obtainTheme().observe(viewLifecycleOwner) { isDarkThemeActivated ->
                if (isDarkThemeActivated) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchChangeTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchChangeTheme.isChecked = false
                }
            }
            switchChangeTheme.setOnCheckedChangeListener { _, isChecked ->
                settingThemeViewModel.setTheme(isChecked)
            }

            // ── Notification alarm switch ─────────────────────────────────────
            settingThemeViewModel.getNotificationAlarmEvent()
                .observe(viewLifecycleOwner) { isNotificationActive ->
                    if (isNotificationActive) {
                        switchNotificationAlarm.isChecked = true
                        startPeriodicTask()
                    } else {
                        switchNotificationAlarm.isChecked = false
                        cancelPeriodicTask()
                    }
                }

            switchNotificationAlarm.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    requestNotificationPermissionIfNeeded()
                } else {
                    settingThemeViewModel.setNotificationAlarmEvent(false)
                }
            }
        }
    }

    /**
     * Observe WorkManager's [WorkInfo] state for the daily notification job and
     * show a subtle status toast so the user knows the work is actually scheduled.
     */
    private fun observeWorkStatus() {
        workManager.getWorkInfosForUniqueWorkLiveData(WORK_NAME)
            .observe(viewLifecycleOwner) { workInfoList ->
                val info = workInfoList?.firstOrNull() ?: return@observe
                when (info.state) {
                    WorkInfo.State.RUNNING -> {
                        // Worker is actively fetching; no UI change needed
                    }
                    WorkInfo.State.FAILED -> {
                        Toast.makeText(
                            requireContext(),
                            "Event notification check failed — will retry automatically.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> { /* ENQUEUED, BLOCKED, CANCELLED, SUCCEEDED — no toast needed */ }
                }
            }
    }

    // ─── WorkManager helpers ──────────────────────────────────────────────────

    private fun startPeriodicTask() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicWorkRequest =
            PeriodicWorkRequest.Builder(EventAlarmWorker::class.java, 1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    WorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()

        // UPDATE replaces any existing request with the new constraints/interval,
        // while KEEP would silently ignore this call if work is already scheduled.
        workManager.enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            periodicWorkRequest
        )
    }

    private fun cancelPeriodicTask() {
        workManager.cancelUniqueWork(WORK_NAME)
    }

    // ─── Permission helpers ───────────────────────────────────────────────────

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                // Permission already granted → just enable the feature
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    settingThemeViewModel.setNotificationAlarmEvent(true)
                }
                // Should show rationale → launch request anyway (rationale shown by OS)
                else -> {
                    requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            // Below Android 13, no runtime permission is required
            settingThemeViewModel.setNotificationAlarmEvent(true)
        }
    }

    // ─── Navigation helpers ───────────────────────────────────────────────────

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnBackSetting.setOnClickListener {
                @Suppress("DEPRECATION")
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