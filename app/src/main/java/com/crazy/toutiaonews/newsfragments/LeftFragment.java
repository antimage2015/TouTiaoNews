package com.crazy.toutiaonews.newsfragments;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.crazy.toutiaonews.R;


public class LeftFragment extends Fragment {

    private Button[] buttons = new Button[5];

    public Callbacks callbacks;

    public interface Callbacks {
        void mediaClick();

        void musicClick(Button[] buttons);

        void pictureClick();

        void loginClick();

        void signinClick();

    }

    public static LeftFragment newInstance() {
        LeftFragment fragment = new LeftFragment();
        return fragment;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof Callbacks) {
            callbacks = (Callbacks) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnMerchantFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_left, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        buttons[0] = (Button) getView().findViewById(R.id.lef_click);
        buttons[1] = (Button) getView().findViewById(R.id.media_button);
        buttons[2] = (Button) getView().findViewById(R.id.picture_button);
        buttons[3] = (Button)getView().findViewById(R.id.login);
        buttons[4] = (Button)getView().findViewById(R.id.sign_in);

        mediaClick(buttons[1]);
        musicClick(buttons[0], buttons);
        pictureClick(buttons[2]);
        loginButtonClick(buttons[3]);
        signinButtonClick(buttons[4]);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        // 将 callbacks 复为 null
        if (callbacks != null)
            callbacks = null;
    }

    private void mediaClick(Button mediaButton) {
        mediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbacks.mediaClick();
            }
        });
    }

    private void musicClick(Button musicButton, final Button[] buttons) {
        musicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbacks.musicClick(buttons);
            }
        });
    }

    private void pictureClick(Button pictureButton) {
        pictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbacks.pictureClick();
            }
        });
    }

    private void loginButtonClick(Button loginButton){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbacks.loginClick();
            }
        });
    }

    private void signinButtonClick(Button signinButton ){
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callbacks.signinClick();
            }
        });
    }
}

