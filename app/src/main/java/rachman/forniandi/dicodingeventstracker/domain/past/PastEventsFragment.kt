package rachman.forniandi.dicodingeventstracker.domain.past

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import rachman.forniandi.dicodingeventstracker.R
import rachman.forniandi.dicodingeventstracker.adapters.EventsAdapter
import rachman.forniandi.dicodingeventstracker.data.remoteUtils.RemoteResponse
import rachman.forniandi.dicodingeventstracker.databinding.FragmentPastEventsBinding
import rachman.forniandi.dicodingeventstracker.domain.entity.Events
import rachman.forniandi.dicodingeventstracker.domain.viewmodels.PastEventsViewmodel


@AndroidEntryPoint
class PastEventsFragment : Fragment() {
    private var _binding: FragmentPastEventsBinding?= null
    private val binding get() = _binding!!
    private val eventAdapter by lazy { EventsAdapter(requireActivity()) }
    private val activeValue:Int=0
    private lateinit var pastEventsViewmodel:PastEventsViewmodel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =FragmentPastEventsBinding.inflate(inflater, container, false)
        pastEventsViewmodel = ViewModelProvider(this)[PastEventsViewmodel::class.java]
        setupSearchEvent()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState === null){
            pastEventsViewmodel.setValueActive(activeValue)
        }


        setListPastEvent()
        setSwipeRefreshEvent()
        showDataPastEvent()
        binding.swipeRefreshPastEvent.isRefreshing = true

        Log.e("TAG", "Ini adalah halaman pastEventFragment")
    }

    private fun setupSearchEvent() {
        binding.svEvent.setupWithSearchBar(binding.searchBar)
        binding.searchBar.setOnClickListener() {
            val actionSearch = PastEventsFragmentDirections.actionPastEventsFragmentToSearchEventsFragment()
            findNavController().navigate(actionSearch)
        }
        /*val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.past_event_menu, menu)
            }

            override fun onMenuItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {
                    R.id.menu_search->{
                        val actionSearch = PastEventsFragmentDirections.actionPastEventsFragmentToSearchEventsFragment()
                        findNavController().navigate(actionSearch)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)*/

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
                Log.d("test_data_event:",""+events)
                if (events != null) {
                    if (events.isEmpty()){
                        binding.swipeRefreshPastEvent.isRefreshing = false
                        binding.imgDataEmpty.visibility= View.VISIBLE
                        binding.txtLblEventNotAvailable.visibility=View.VISIBLE
                    }else{
                        events.let { eventAdapter.setData(it) }
                        binding.swipeRefreshPastEvent.isRefreshing = false
                        binding.imgDataEmpty.visibility= View.GONE
                        binding.txtLblEventNotAvailable.visibility=View.GONE
                    }

                }

            }

            is RemoteResponse.Error -> {
                hideShimmerEffect()
                Toast.makeText(requireActivity(),response.errorMessage, Toast.LENGTH_SHORT).show()
                binding.swipeRefreshPastEvent.isRefreshing = false
                binding.imgDataEmpty.visibility= View.VISIBLE
                binding.txtLblEventNotAvailable.visibility=View.VISIBLE
            }

            else -> {}
        }
    }

    private fun setListPastEvent() {
        binding.rvEventsPast.adapter = eventAdapter
        eventAdapter.setOnClickListener(object :EventsAdapter.OnEventClickListener{
            override fun onClick(position: Int, event: Events) {
                val toDetailEvent = PastEventsFragmentDirections.actionPastEventsFragmentToDetailEventsActivity(event)
                findNavController().navigate(toDetailEvent)
            }
        })
        showShimmerEffect()
    }

    private fun setSwipeRefreshEvent() {
        binding.swipeRefreshPastEvent.setOnRefreshListener {
            showDataPastEvent()
            hideShimmerEffect()
        }
    }
    private fun showShimmerEffect() {
        binding.shimmerFrameLayout2.startShimmer()
        binding.shimmerFrameLayout2.visibility = View.VISIBLE
        binding.rvEventsPast.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout2.stopShimmer()
        binding.shimmerFrameLayout2.visibility = View.GONE
        binding.rvEventsPast.visibility = View.VISIBLE
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}