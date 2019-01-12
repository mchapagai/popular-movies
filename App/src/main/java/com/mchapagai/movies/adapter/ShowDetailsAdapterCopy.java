package com.mchapagai.movies.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mchapagai.movies.R;
import com.mchapagai.movies.model.Genres;
import com.mchapagai.movies.model.ProductionCompanies;
import com.mchapagai.movies.model.ShowsCreatedBy;
import com.mchapagai.movies.model.ShowsNetwork;
import com.mchapagai.movies.model.binding.ShowsDetailsResponse;
import java.util.List;

public class ShowDetailsAdapterCopy extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ShowsNetwork> showNetworkList;

    private List<Genres> showGenreItems;

    private List<String> showLanguageItems;

    private List<ShowsCreatedBy> showCreatedByItems;

    private List<ProductionCompanies> showProductionCompaniesItems;

    private Integer dataCount;

    private ShowItemViewType showItemViewType;

    public ShowDetailsAdapterCopy(final ShowsDetailsResponse showsDetailsResponse, ShowItemViewType showItemViewType) {
        switch (showItemViewType) {
            case SHOW_CREATED_BY:
                showCreatedByItems = showsDetailsResponse.getCreatedBy();
                dataCount= showCreatedByItems.size();
                break;
            case SHOW_NETWORK:
                showNetworkList = showsDetailsResponse.getNetworks();
                dataCount= showNetworkList.size();
                break;
            case SHOW_LANGUAGE:
                showLanguageItems = showsDetailsResponse.getLanguages();
                dataCount= showLanguageItems.size();
                break;
            case SHOW_GENRE:
                showGenreItems = showsDetailsResponse.getGenres();
                dataCount= showGenreItems.size();
                break;
            case SHOW_PRODUCTION_COMPANIES:
                showProductionCompaniesItems = showsDetailsResponse.getProductionCompanies();
                dataCount= showProductionCompaniesItems.size();
                break;
            default:
                showCreatedByItems = showsDetailsResponse.getCreatedBy();
                dataCount= showCreatedByItems.size();
                break;
        }
        this.showItemViewType = showItemViewType;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link # onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see # onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {

        ShowItemViewType type = ShowItemViewType.values()[viewType];
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemView;
        switch (type) {
            case SHOW_CREATED_BY:
            case SHOW_GENRE:
                itemView = inflater.inflate(R.layout.show_detail_items, parent, false);
                return new ViewHolder(itemView);
            case SHOW_NETWORK:
                itemView = inflater.inflate(R.layout.show_detail_items, parent, false);
                return new NetworkViewHolder(itemView);
//            case SHOW_LANGUAGE:
//                itemView = inflater.inflate(R.layout.show_detail_items, parent, false);
//                return new ViewHolder(itemView);
//            case SHOW_PRODUCTION_COMPANIES:
//                itemView = inflater.inflate(R.layout.show_detail_items, parent, false);
//                return new ViewHolder(itemView);
//            case SHOW_NETWORK:
//                itemView = inflater.inflate(R.layout.show_detail_items, parent, false);
//                return new ViewHolder(itemView);
            default:
                itemView = inflater.inflate(R.layout.show_detail_items, parent, false);
                return new ViewHolder(itemView);
        }

    }

    @Override
    public int getItemViewType(final int position) {
        return showItemViewType.ordinal();
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     *
     * Override {@link # onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        switch (showItemViewType) {
            case SHOW_NETWORK:
                final ShowsNetwork response = showNetworkList.get(position);
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.episodeNumber.setText(response.getName());
                viewHolder.originalTitle.setText(response.getOriginCountry());
                break;

            case SHOW_GENRE:
                final Genres genreResponse = showGenreItems.get(position);
                NetworkViewHolder networkViewHolder = (NetworkViewHolder) holder;
                networkViewHolder.genreName.setText(genreResponse.getName());
                break;
            case SHOW_CREATED_BY:
                final ShowsCreatedBy showsCreatedBy = showCreatedByItems.get(position);
                NetworkViewHolder networkViewHolder1 = (NetworkViewHolder) holder;
                networkViewHolder1.productionName.setText(showsCreatedBy.getName());
                break;
            case SHOW_PRODUCTION_COMPANIES:
                final ProductionCompanies productionCompanies = showProductionCompaniesItems.get(position);
                ViewHolder viewHolder1 = (ViewHolder) holder;
                viewHolder1.episodeNumber.setText(productionCompanies.getName());
                viewHolder1.originalTitle.setText(productionCompanies.getId());
                break;

        }

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return dataCount;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView originalTitle, episodeNumber;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            originalTitle = itemView.findViewById(R.id.original_title);
            episodeNumber = itemView.findViewById(R.id.number_of_episode);
        }
    }

    class NetworkViewHolder extends RecyclerView.ViewHolder {

        TextView genreName, productionName, dummyText;

        public NetworkViewHolder(@NonNull final View itemView) {
            super(itemView);

            genreName = itemView.findViewById(R.id.original_title);
            productionName = itemView.findViewById(R.id.number_of_episode);
        }
    }
}
