package rachman.forniandi.dicodingeventstracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rachman.forniandi.dicodingeventstracker.R
import rachman.forniandi.dicodingeventstracker.databinding.ItemEventCarrouselBinding
import rachman.forniandi.dicodingeventstracker.domain.entity.Events
import rachman.forniandi.dicodingeventstracker.utils.EventDiffUtil

class CarrouselEventsAdapter (private val mContext: Context): RecyclerView.Adapter<CarrouselEventsAdapter.CarrouselEventsHolder>() {

    private var events = emptyList<Events>()
    private var onClickListener: OnEventClickListener?= null

    class CarrouselEventsHolder (view: ItemEventCarrouselBinding): RecyclerView.ViewHolder(view.root){
        val txtTitleEvent = view.txtTitleNameEvent
        val imgPromoteEvent = view.imgEvent
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarrouselEventsHolder {
        val bindingView = ItemEventCarrouselBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CarrouselEventsHolder(bindingView)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    fun setOnClickListener(onClickListener: OnEventClickListener) {
        this.onClickListener = onClickListener
    }


    interface OnEventClickListener {
        fun onClick(position: Int, event: Events)
    }

    override fun onBindViewHolder(holder: CarrouselEventsHolder, position: Int) {
        val eventData = events[position]
        holder.txtTitleEvent.text = eventData.name
        Glide.with(mContext)
            .load(eventData.imageLogo)
            .centerCrop()
            .placeholder(R.drawable.place_holder)
            .error(R.drawable.error_placeholder)
            .into(holder.imgPromoteEvent)

        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position,eventData)
        }
    }



    fun setData(eventData: List<Events>){
        val dataDiffUtil = EventDiffUtil(events,eventData)
        val diffUtilResult = DiffUtil.calculateDiff(dataDiffUtil)
        events = eventData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}