package com.mchapagai.movies.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mchapagai.core.response.common.VideoResponse;
import com.mchapagai.core.response.shows.ShowResponse;
import com.mchapagai.movies.views.ItemOffsetDecoration;
import com.mchapagai.movies.views.MaterialTextView;
import com.mchapagai.movies.R;
import com.mchapagai.movies.adapter.VideosAdapter;
import com.mchapagai.movies.common.BaseFragment;
import com.mchapagai.movies.common.Constants;
import com.mchapagai.movies.model.OnTheAir;
import com.mchapagai.movies.view_model.ShowsViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class TrailerFragment extends BaseFragment {

    ImageView showsDetailsEmptyVideo;

    MaterialTextView showVideosErrorText;

    RecyclerView showsVideoRecyclerView;

    TextView showsVideosTitle;

    private ShowResponse onTheAir;
    private VideosAdapter videosAdapter;
    private final List<VideoResponse> videoItems = new ArrayList<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    ShowsViewModel showsViewModel;

    public static TrailerFragment newInstance(ShowResponse onTheAir) {
        if (onTheAir == null) {
            throw new IllegalArgumentException("The Movies Data can not be null");
        }
        Bundle args = new Bundle();
        args.putParcelable(Constants.SHOWS_DETAILS, onTheAir);

        TrailerFragment fragment = new TrailerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onTheAir = getArguments().getParcelable(Constants.SHOWS_DETAILS);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_details_video_fragment_container, container,
                false);
        showsDetailsEmptyVideo = view.findViewById(R.id._shows_details_empty_video);
        showVideosErrorText = view.findViewById(R.id.show_videos_error_text);
        showsVideoRecyclerView = view.findViewById(R.id.shows_video_recycler_view);
        showsVideosTitle = view.findViewById(R.id.shows_videos_title);

        populateMovieTrailer();
        return view;
    }

    private void loadVideos() {

        compositeDisposable.add(showsViewModel.fetchShowDetailsById(onTheAir.getId())
                .subscribe(
                        showsDetailsResponse -> {
                            if (showsDetailsResponse.getVideos().getVideoList().isEmpty()) {
                                showVideoError();
                            } else {
                                hideVideoError();
                                videosAdapter.setMovieVideos(
                                        showsDetailsResponse.getVideos().getVideoList());
                            }
                        }, throwable -> {

                        }

                ));
    }

    private void populateMovieTrailer() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        showsVideoRecyclerView.addItemDecoration(
                new ItemOffsetDecoration(requireContext(), R.dimen.margin_4dp));
        showsVideoRecyclerView.setLayoutManager(manager);
        showsVideoRecyclerView.setHasFixedSize(true);
        showsVideoRecyclerView.setItemAnimator(new DefaultItemAnimator());

        videosAdapter = new VideosAdapter(getContext(), videoItems);
        videosAdapter.setOnItemClick((view, position) -> {
            VideoResponse video = videosAdapter.getItem(position);
            if (video != null && video.isYoutubeVideo()) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + video.getKey()));
                startActivity(intent);
            }
        });
        showsVideoRecyclerView.setAdapter(videosAdapter);
    }


    private void showVideoError() {
        showsDetailsEmptyVideo.setVisibility(View.VISIBLE);
        showVideosErrorText.setVisibility(View.VISIBLE);
        showsVideoRecyclerView.setVisibility(View.GONE);
        showsVideosTitle.setVisibility(View.GONE);
    }

    private void hideVideoError() {
        showsDetailsEmptyVideo.setVisibility(View.GONE);
        showVideosErrorText.setVisibility(View.GONE);
        showsVideoRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadVideos();
    }

    @Override
    public void onPause() {
        super.onPause();
        compositeDisposable.clear();
    }

}