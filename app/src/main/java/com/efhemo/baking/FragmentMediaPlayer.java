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

    private TextView textViewDescription;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private Steps steps;
    private Button previousBtn;
    private ImageView thumnailImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.media_player_layout, container, false);

        Bundle bundle = getArguments();
        initView(view);
        //MediaController mediaController = new MediaController(getContext());

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
        //player.seekTo(currentWindow, playbackPosition);

        Uri uri = null;
        try{
            if(!steps.getVideoURL().isEmpty()){

                uri = Uri.parse(steps.getVideoURL());
            }else{
                playerView.setVisibility(View.GONE);
                thumnailImage.setVisibility(View.VISIBLE);

            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
        player.setPlayWhenReady(true);
    }

    void initView(View view){

        thumnailImage = view.findViewById(R.id.thumbImage);
        playerView = view.findViewById(R.id.video_view);

        textViewDescription = view.findViewById(R.id.short_description);

        /*previousBtn = view.findViewById(R.id.previous_btn);
        Button nextBtn = view.findViewById(R.id.next_btn);*/
    }

    //create a datasource
    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory( // Extract your media source
                new DefaultHttpDataSourceFactory("Exoplayer")).
                createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }

    /*@Override
    public void onPause() {
        super.onPause();
        if (player!=null) {
            player.release();
            player = null;
        }
    }*/

    @Override
    public void onStop() {
        super.onStop();
        playerView.setPlayer(null);
        player.release();
        player = null;
    }

}
