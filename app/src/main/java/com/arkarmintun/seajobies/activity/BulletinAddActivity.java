package com.arkarmintun.seajobies.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.arkarmintun.seajobies.R;
import com.arkarmintun.seajobies.model.Agent;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BulletinAddActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private List<String> agents = new ArrayList<>();
    public final static int PICK_PHOTO_CODE = 1046;
    private byte[] image;
    private ParseObject agentObject;
    private String content;
    private String agent;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_add);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Agent");
        query.selectKeys(Arrays.asList("name"));
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        Agent agent = new Agent();
                        agent.name = objects.get(i).getString("name");
                        agents.add(agent.name);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.spinner_item, agents);
                    AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.agent_list);
                    autoCompleteTextView.setAdapter(adapter);
                } else {
                    Toast.makeText(getBaseContext(), "Something wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AppCompatButton btnLoadImage = (AppCompatButton) findViewById(R.id.btn_load_image);
        AppCompatButton btnFeedSave = (AppCompatButton) findViewById(R.id.btn_feed_save);

        btnLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPickPhoto(v);
            }
        });

        btnFeedSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputContent = (EditText) findViewById(R.id.input_content);
                AutoCompleteTextView agentList = (AutoCompleteTextView) findViewById(R.id.agent_list);

                content = inputContent.getText().toString();
                agent = agentList.getText().toString();
                Log.e("Content", content);
                Log.e("Agent", agent);

                if (content.equals("") || agent.equals("")) {
                    Toast.makeText(BulletinAddActivity.this, "Complete the form.", Toast.LENGTH_SHORT).show();
                } else {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Agent");
                    query.whereEqualTo("name", agent);
                    query.getFirstInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                ParseObject feed = new ParseObject("Bulletin");
                                feed.put("content", content);
                                feed.put("createdBy", object);

                                if (image != null) {
                                    ParseFile file = new ParseFile("image.jpeg", image);
                                    file.saveInBackground();
                                    feed.put("photo", file);
                                }

                                feed.saveInBackground();
                            }
                        }
                    });
                }

//                ParseFile file = new ParseFile("image.jpeg", image);
//                file.saveInBackground();
//
//                ParseObject feed = new ParseObject("Bulletin");
//                feed.put("content", content);
//                feed.put("createdBy", agentObject);
//                feed.put("photo", file);
//                feed.saveInBackground();

            }
        });
    }

    // Trigger gallery selection for a photo
    public void onPickPhoto(View view) {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Uri photoUri = data.getData();
            // Do something with the photo based on Uri
            Bitmap selectedImage = null;
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                image = stream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Load the selected image into a preview
            ImageView ivPreview = (ImageView) findViewById(R.id.img_load_image);
            ivPreview.setImageBitmap(selectedImage);
        }
    }
}
