package com.example.danielward.simpletimer;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final long DELAY = 1000;
    private long count;
    private Handler handler;
    private TextView textView;
    private Thread thread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.counter);

        // create a handler to "tick" the count
        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                Bundle data = msg.getData();
                boolean didWork = data.getBoolean("key", false);


                count++;
                textView.setText(String.valueOf(count));
            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();

        count = 0;
        thread = new Thread(runnable);
        thread.start();
    }

    @Override
    protected void onStop() {
        thread.interrupt();

        super.onStop();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(DELAY);
                    Bundle result = new Bundle();
                    result.getBoolean("key", false);

                    Message message = new Message();
                    message.setData(result);

                    handler.sendMessage(message);
                }
            } catch (InterruptedException e) {

            }
        }
    };
}
