package com.efhemo.baking;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.efhemo.baking.model.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class FragmentMediaPlayer extends Fragment {

    public static final String PLAYER_CURRENT_STATE = "Player Current State";
    private TextView textViewDescription;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private Steps steps;
    private Button previousBtn;
    private ImageView thumnailImage;
    private Long position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.media_player_layout, container, false);

        Bundle bundle = getArguments();
        initView(view);
        //MediaController mediaController = new MediaController(getContext());

        if (savedInstanceState != null) {
            position = savedInstanceState.getLong(PLAYER_CURRENT_STATE, 0);
        }

        if (bundle != null) {
            steps = bundle.getParcelable("steps");

            if(view.findViewById(R.id.short_description) != null){

                textViewDescription.setText(steps.getDescription());
            }

        }

        return view;
    }

    private void initializePlayer() {


            //create a new instance of SimpleExoPlayer
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            //set player into playerview
            playerView.setPlayer(player);

            player.setPlayWhenReady(true);

            Uri uri = null;
            try {
                if (!steps.getVideoURL().isEmpty()) {

                    uri = Uri.parse(steps.getVideoURL());
                } else if (!steps.getThumbnailURL().isEmpty()) {
                    playerView.setVisibility(View.GONE);
                    thumnailImage.setVisibility(View.VISIBLE);
                    Glide.with(getContext()).load(steps.getThumbnailURL()).
                            centerCrop().into(thumnailImage);
                } else {
                    playerView.setVisibility(View.GONE);
                    thumnailImage.setVisibility(View.VISIBLE);

                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            MediaSource mediaSource = buildMediaSource(uri);
            player.prepare(mediaSource, true, false);

            if(position != null){

                player.seekTo(position);
            }
            player.setPlayWhenReady(true);

    }

    void initView(View view){

        thumnailImage = view.findViewById(R.id.thumbImage);
        playerView = view.findViewById(R.id.video_view);

        textViewDescription = view.findViewById(R.id.short_description);

    }

    //create a datasource
    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory( // Extract your media source
                new DefaultHttpDataSourceFactory("Exoplayer")).
                createMediaSource(uri);
    }


    @Override
    public void onResume() {
        super.onResume();
        initializePlayer();

    }

    @Override
    public void onPause() {
        super.onPause();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (player!=null) {
            player.stop();
            player.release();
            player=null;
        }
    }


    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putLong(PLAYER_CURRENT_STATE, player.getCurrentPosition());

}



}
