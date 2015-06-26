package com.example.rainvic.thread;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;
import android.os.Message;

public class MainActivity extends ActionBarActivity {
    //When you use thread, use this Handler to update the interfaces!!
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            TextView vickysText = (TextView) findViewById(R.id.vickysText);
            vickysText.setText("Nice job Victor!!!!");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickVickysButton(View view){

        //NEVER try to put interface changes into the thread, its a bad programming!! it might crash!!
        Runnable r = new Runnable() {
            @Override
            public void run() {
                // here we are simulate the any considerable time consuming of calculation by let the system wait for a ten seconds
                long futureTime = System.currentTimeMillis() + 10000;
                while(System.currentTimeMillis() < futureTime){
                    synchronized (this){
                        try{
                            wait(futureTime-System.currentTimeMillis());
                        }catch (Exception e){
                            Log.i("test", "test");
                        }
                    }
                }
                //and call this handler in the thread!
                handler.sendEmptyMessage(0);
            }
        };

        Thread vickysTread = new Thread(r);
        vickysTread.start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
