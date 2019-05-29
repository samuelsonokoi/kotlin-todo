package com.example.todomobi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FeedsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView displayName;
    private FirebaseAuth mAuth;
    ListView rss_list;
    ArrayList<String> titles;
    ArrayList<String> descriptions;
    ArrayList<String> links;
    ArrayList<String> pubDates;
    SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);

        // Hide ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        pullToRefresh = findViewById(R.id.pull_refresh);

        //setting an setOnRefreshListener on the SwipeDownLayout
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            int refreshcounter = 1; //Counting how many times user have refreshed the layout

            @Override
            public void onRefresh() {
                // render feeds when refreshed
                new HandleInBackground().execute();
                pullToRefresh.setRefreshing(false);
            }
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.sign_out_btn).setOnClickListener(this);
        displayName = findViewById(R.id.display_name);
        rss_list = findViewById(R.id.rss_list);
        titles = new ArrayList<>();
        descriptions = new ArrayList<>();
        links = new ArrayList<>();
        pubDates = new ArrayList<>();

        rss_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // set list click to retrieve links
                Uri uri = Uri.parse(links.get(position));

                // open web page
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        // execute handleInBackground
        new HandleInBackground().execute();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null){
            displayName.setText("Hello, " + user.getDisplayName());
        } else {
            // Signed out
            Intent intent = new Intent(FeedsActivity.this, AuthActivity.class);
            startActivity(intent);
        }
    }

    public InputStream inputStream(URL url){
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e){
            return null;
        }
    }

    private void signOut() {
        AuthUI.getInstance().signOut(this);
        Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(FeedsActivity.this, AuthActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_out_btn:
                signOut();
                break;
        }
    }

    public class HandleInBackground extends AsyncTask<Integer, Void, Exception>{

        ProgressDialog progressDialog = new ProgressDialog(FeedsActivity.this);

        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // show progress dialog
            progressDialog.setMessage("Loading, please wait...");
            progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... integers) {

            try{
                URL url = new URL("http://feeds.news24.com/articles/Fin24/Tech/rss");

                XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();

                // disable xml namespace support
                xmlPullParserFactory.setNamespaceAware(false);

                XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
                xmlPullParser.setInput(inputStream(url), "UTF_8");

                // set boolean value for when we are in an item tag
                boolean item = false;

                // get the event type
                int eventType = xmlPullParser.getEventType();

                // loop through using XmlPullParser.end_document
                while (eventType != XmlPullParser.END_DOCUMENT){

                    if (eventType == XmlPullParser.START_TAG){
                        if (xmlPullParser.getName().equalsIgnoreCase("item")){
                            item = true;
                        } else if(xmlPullParser.getName().equalsIgnoreCase("title")){
                            // make sure we're inside the item
                            if (item){
                                titles.add(xmlPullParser.nextText());
                            }
                        } else if(xmlPullParser.getName().equalsIgnoreCase("description")){
                            if (item){
                                descriptions.add(xmlPullParser.nextText());
                            }
                        } else if(xmlPullParser.getName().equalsIgnoreCase("link")){
                            if (item){
                                links.add(xmlPullParser.nextText());
                            }
                        } else if(xmlPullParser.getName().equalsIgnoreCase("pubDate")){
                            if (item){
                                pubDates.add(xmlPullParser.nextText());
                            }
                        }
                    } else if(eventType == XmlPullParser.END_TAG && xmlPullParser.getName().equalsIgnoreCase("item")){
                        item = false;
                    }

                    // increment to next tag
                    eventType = xmlPullParser.next();

                }

            } catch (MalformedURLException e){
                exception = e;
            } catch(XmlPullParserException e){
                exception = e;
            } catch (IOException e){
                exception = e;
            }

            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);

            // Dismiss progress dialog
            progressDialog.dismiss();

            // Set adapter for list view
            ArrayAdapter<String> adapter = new ArrayAdapter<>(FeedsActivity.this,
                    android.R.layout.simple_list_item_1, titles);
            rss_list.setAdapter(adapter);
        }
    }
}
