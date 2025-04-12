// File: MainActivity.java
package com.sporsika.app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech tts;
    private TextView welcomeText;
    private Button playIntro, learnDua;
    private MediaPlayer introBeat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeText = findViewById(R.id.welcomeText);
        playIntro = findViewById(R.id.playIntro);
        learnDua = findViewById(R.id.learnDua);

        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(new Locale("bn", "BD"));
            }
        });

        playIntro.setOnClickListener(v -> playWelcomeMessage());
        learnDua.setOnClickListener(v -> showDuaLearning());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }

        introBeat = MediaPlayer.create(this, R.raw.intro_beat);
    }

    private void playWelcomeMessage() {
        introBeat.start();
        speak("আসসালামু আলাইকুম ওয়া রহমাতুল্লাহ, আমি স্পর্শিকা, আপনার ইসলামিক স্মার্ট সহচর");
    }

    private void showDuaLearning() {
        speak("আজকে আমরা শিখবো কালিমা তাইয়্যেবারার. লা ইলাহা ইল্লাল্লাহু মুহাম্মাদুর রাসুলুল্লাহ. তারপর সূরা ফাতিহা. বিসমিল্লাহির রহমানির রাহিম. আলহামদুলিল্লাহি রাব্বিল আলামিন...");
    }

    private void speak(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        if (introBeat != null) {
            introBeat.release();
        }
        super.onDestroy();
    }
}
