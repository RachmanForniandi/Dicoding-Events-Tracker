package rachman.forniandi.dicodingeventstracker.domain.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import rachman.forniandi.dicodingeventstracker.adapters.EventsAdapter
import rachman.forniandi.dicodingeventstracker.data.remoteUtils.RemoteResponse
import rachman.forniandi.dicodingeventstracker.databinding.FragmentSearchEventBinding
import rachman.forniandi.dicodingeventstracker.domain.entity.Events
import rachman.forniandi.dicodingeventstracker.domain.viewmodels.SearchEventsViewModel
import rachman.forniandi.dicodingeventstracker.utils.hideKeyboard

@AndroidEntryPoint
class SearchEventFragment : Fragment() {


    private var _binding: FragmentSearchEventBinding?= null
    private val binding get() = _binding!!
    private lateinit var viewmodel: SearchEventsViewModel
    private val eventAdapter by lazy { EventsAdapter(requireActivity()) }
    private val activeValue:Int=-1



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchEventBinding.inflate(inflater,container,false)
        viewmodel = ViewModelProvider(this)[SearchEventsViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("TAG", "Ini adalah halaman searchEventFragment")

        if (savedInstanceState === null){
            viewmodel.fetchSearchEvent(activeValue,binding.svEvent.text.toString())
        }

        setOnSearch()

    }

    private fun setOnSearch() {
        binding.apply {
            rvEventSearch.apply {
                adapter = eventAdapter
                eventAdapter.setOnClickListener(object :EventsAdapter.OnEventClickListener{
                    override fun onClick(position: Int, event: Events) {
                        val toDetailEvent = SearchEventFragmentDirections.actionSearchEventsFragmentToDetailEventsActivity(event)
                        findNavController().navigate(toDetailEvent)
                    }
                })
            }
            svEvent.setupWithSearchBar(searchBar)
            svEvent.editText.setOnEditorActionListener { _, actionId, _ ->
                searchBar.setText(svEvent.text)
                svEvent.hide()
                actionSearchEvents()
                false
            }
        }


    }

    private fun actionSearchEvents() {

        context?.hideKeyboard(requireView())
        viewmodel.fetchSearchEvent(activeValue,binding.svEvent.text.toString())
        viewmodel.searchEvents.observe(viewLifecycleOwner,eventObserver)

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
                        binding.imgDataEventNotFound.visibility= View.VISIBLE
                        binding.txtErrorSearch.visibility=View.VISIBLE
                    }else{
                        events.let { eventAdapter.setData(it) }
                        binding.imgDataEventNotFound.visibility= View.GONE
                        binding.txtErrorSearch.visibility=View.GONE
                    }

                }

            }

            is RemoteResponse.Error -> {
                hideShimmerEffect()
                Toast.makeText(requireActivity(),response.errorMessage, Toast.LENGTH_SHORT).show()
                binding.imgDataEventNotFound.visibility= View.VISIBLE
                binding.txtErrorSearch.visibility=View.VISIBLE
            }

            else -> {}
        }
    }


    private fun showShimmerEffect() {
        binding.shimmerFrameLayoutSearch.startShimmer()
        binding.shimmerFrameLayoutSearch.visibility = View.VISIBLE
        binding.rvEventSearch.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayoutSearch.stopShimmer()
        binding.shimmerFrameLayoutSearch.visibility = View.GONE
        binding.rvEventSearch.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}