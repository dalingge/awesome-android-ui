package com.dalingge.awesome.airbnb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dalingge.awesome.R;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main1);

    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.content_1, ListFragment.newInstance())
          .commit();
    }
  }
}
