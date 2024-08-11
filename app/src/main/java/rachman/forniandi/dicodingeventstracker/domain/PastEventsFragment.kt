package rachman.forniandi.dicodingeventstracker.domain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import rachman.forniandi.dicodingeventstracker.adapters.EventsAdapter
import rachman.forniandi.dicodingeventstracker.data.remoteUtils.RemoteResponse
import rachman.forniandi.dicodingeventstracker.databinding.FragmentPastEventsBinding
import rachman.forniandi.dicodingeventstracker.domain.entity.Events
import rachman.forniandi.dicodingeventstracker.domain.viewmodels.PastEventsViewmodel



class PastEventsFragment : Fragment() {
    private var _binding: FragmentPastEventsBinding?= null
    private val binding get() = _binding
    private val eventAdapter by lazy { EventsAdapter(requireActivity()) }
    private val activeValue:Int=0
    private val pastEventsViewmodel:PastEventsViewmodel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =FragmentPastEventsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState === null){
            pastEventsViewmodel.setValueActive(activeValue)
        }

        setListPastEvent()
        showDataPastEvent()
        setSwipeRefreshEvent()
    }

    private fun showDataPastEvent() {
        pastEventsViewmodel.setValueActive(activeValue)
        pastEventsViewmodel.pastEvent.observe(viewLifecycleOwner,eventObserver)

    }

    private val eventObserver = Observer<RemoteResponse<List<Events>?>?> { response ->
        when (response) {
            is RemoteResponse.Loading -> {
                showShimmerEffect()
            }

            is RemoteResponse.Success -> {
                hideShimmerEffect()
                val events = response.data
                events?.let { eventAdapter.setData(it) }

            }

            is RemoteResponse.Error -> {
                hideShimmerEffect()
                Toast.makeText(requireActivity() ,response.errorMessage, Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    private fun setListPastEvent() {
        binding?.rvEventsPast?.adapter = eventAdapter

        showShimmerEffect()
    }

    private fun setSwipeRefreshEvent() {
        binding?.swipeRefreshPastEvent?.setOnRefreshListener {
            showDataPastEvent()
            hideShimmerEffect()
        }
    }
    private fun showShimmerEffect() {
        binding?.shimmerFrameLayout2?.startShimmer()
        binding?.shimmerFrameLayout2?.visibility = View.VISIBLE
        binding?.rvEventsPast?.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding?.shimmerFrameLayout2?.stopShimmer()
        binding?.shimmerFrameLayout2?.visibility = View.GONE
        binding?.rvEventsPast?.visibility = View.VISIBLE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}