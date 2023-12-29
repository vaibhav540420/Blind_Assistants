package com.example.software2.ocrhy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class MainActivity9 extends AppCompatActivity {

    EditText txt_phoneNumber,confirm;
    ImageView voice_btn;
    ImageView call;
    TextToSpeech textToSpeech;
    final int VOICE_CODE = 100;
    final int VOICE_CODE1= 200;
    String numberEntered,choice;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);

        txt_phoneNumber = findViewById(R.id.txt_number);
        //confirm=findViewById(R.id.confirm);
        voice_btn = findViewById(R.id.btn_call);
        call = findViewById(R.id.call);


        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                    Toast.makeText(MainActivity9.this, "tap on the screen and say the number", Toast.LENGTH_SHORT).show();
                    textToSpeech.speak("enter phone number.", TextToSpeech.QUEUE_FLUSH, null);
                    textToSpeech.setSpeechRate(1f);
                }
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        voice_to_text();
                    }
                }, 2000);

            }
        });

        voice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voice_to_text();
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = txt_phoneNumber.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" +phoneNumber));
                startActivity(intent);
            }
        });

    }

   private void callByVoice(){
        String phoneNumber = txt_phoneNumber.getText().toString();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" +phoneNumber));
        startActivity(intent);

        numberEntered="";
    }

        private void voice_to_text() {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    "Say Something!!");
            try {
                startActivityForResult(intent, VOICE_CODE);
            } catch (ActivityNotFoundException e) {

            }
        }
        protected void onActivityResult(int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case VOICE_CODE: {
                    if (resultCode == RESULT_OK && null != data) {

                        ArrayList<String> result = data
                                .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        txt_phoneNumber.setText(result.get(0));


                        if (txt_phoneNumber.getText().toString().equals("read")) {

                            Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                            startActivity(intent);
                            txt_phoneNumber.setText(null);
                        }
                        if (txt_phoneNumber.getText().toString().equals("calculator")) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                            startActivity(intent);
                            txt_phoneNumber.setText(null);
                        }
                        if (txt_phoneNumber.getText().toString().equals("time and date")) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity4.class);
                            startActivity(intent);
                            txt_phoneNumber.setText(null);
                        }
                        if (txt_phoneNumber.getText().toString().equals("weather")) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity5.class);
                            startActivity(intent);
                            txt_phoneNumber.setText(null);
                        }

                        if (txt_phoneNumber.getText().toString().equals("battery")) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity6.class);
                            startActivity(intent);
                            txt_phoneNumber.setText(null);
                        }
                        if (txt_phoneNumber.getText().toString().equals("main menu")) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            txt_phoneNumber.setText(null);
                        }
                        if (txt_phoneNumber.getText().toString().equals("location")) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity8.class);
                            startActivity(intent);
                            txt_phoneNumber.setText(null);
                        }
                        if (txt_phoneNumber.getText().toString().equals("call")) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity9.class);
                            startActivity(intent);
                            txt_phoneNumber.setText(null);
                        }

                        if (txt_phoneNumber.getText().toString().equals("exit")) {
                            onPause();
                            finishAffinity();
                        }
                        }
                }
            }

            // user will have to say number
            //To ensure editText is not empty
            numberEntered = txt_phoneNumber.getText().toString();
            if (numberEntered.trim().equals("")) {
                Toast.makeText(MainActivity9.this, "Enter phone number", Toast.LENGTH_SHORT).show();
                textToSpeech.setLanguage(Locale.US);
                textToSpeech.speak("Enter phone number.", TextToSpeech.QUEUE_FLUSH, null);
                textToSpeech.setSpeechRate(1f);
            }
            else {
                textToSpeech.setLanguage(Locale.US);
                // textToSpeech.speak("you entered number"+ numberEntered +"say yes to call or no to edit number", TextToSpeech.QUEUE_FLUSH, null);
                textToSpeech.speak("you entered number"+ numberEntered+".  tap on bottom of screen to call or volume up button to go in main menu", TextToSpeech.QUEUE_FLUSH, null);
                textToSpeech.setSpeechRate(1f);
//              voice_to_text1();
//                if(choice=="yes") {
//                    callByVoice(); //calling
//                }
//                if(choice=="no")
//                {
//                    Intent intent = new Intent(getApplicationContext(), MainActivity9.class);
//                    startActivity(intent);
//                }
            }
        }


//    private void voice_to_text1() {
//        Intent intent1 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//        intent1.putExtra(RecognizerIntent.EXTRA_PROMPT,
//                "Say Something!!");
//        try {
//            startActivityForResult(intent1, VOICE_CODE1);
//        } catch (ActivityNotFoundException e) {
//
//        }
//    }
//
//    protected void onActivityResult1(int requestCode1, int resultCode1, Intent data1) {
//        super.onActivityResult(requestCode1, resultCode1, data1);
//
//        switch (requestCode1) {
//            case VOICE_CODE1: {
//                if (resultCode1 == RESULT_OK && null != data1) {
//
//                    ArrayList<String> result = data1
//                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    confirm.setText(result.get(0));
//                    choice = confirm.getText().toString();
//                }
//            }
//        }
//    }

    public boolean onKeyDown(int keyCode, @Nullable KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            textToSpeech.speak("You are in main menu. just swipe right and say what you want", TextToSpeech.QUEUE_FLUSH, null);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textToSpeech.speak("you are in main menu. just swipe right and say what you want", TextToSpeech.QUEUE_FLUSH, null);
                }
            },1000);
        }
        return true;
    }
}
