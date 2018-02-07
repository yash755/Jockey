package com.pappydevelopers.jockey.activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pappydevelopers.jockey.R;
import com.pappydevelopers.jockey.adapter.ImageAdapter;
import com.pappydevelopers.jockey.databasehelper.DataBaseHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dmax.dialog.SpotsDialog;

public class ImagePreview extends AppCompatActivity {

    ImageAdapter imageAdapter;
    ViewPager viewPager;
    private List<String> imageItems;
    String position = null;
    AlertDialog dialog;
    DataBaseHelper dataBaseHelper;
    Integer list_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Jockey");

        dataBaseHelper = new DataBaseHelper(this);

        if(getIntent().hasExtra("imagelist")) {
            imageItems =  getIntent().getStringArrayListExtra("imagelist");
            position = getIntent().getStringExtra("position");
            list_type = getIntent().getIntExtra("type",0);

            if (list_type == 1){
                setTitle("Jockey");
            } else if (list_type == 2){
                setTitle("My Favourites");
            }

        }

        imageAdapter = new ImageAdapter(this, imageItems);
        viewPager = (ViewPager) findViewById(R.id.image);
        viewPager.setAdapter(imageAdapter);
        viewPager.setCurrentItem(Integer.parseInt(position));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                Integer currentItem = viewPager.getCurrentItem();
                add_favourite(getIntent().getStringArrayListExtra("imagelist").get(currentItem));
                return true;
            case R.id.action_download:
                dialog = new SpotsDialog(this, R.style.Custom1);
                dialog.show();
                new DownloadImageAsyncTask()
                        .execute();
                return true;
            case R.id.action_share:
                Integer currentItem1 = viewPager.getCurrentItem();
                share_pic(getIntent().getStringArrayListExtra("imagelist").get(currentItem1));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    class DownloadImageAsyncTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... params)
        {
            // TODO Auto-generated method stub
            Integer currentItem = viewPager.getCurrentItem();
            downloadImages(getIntent().getStringArrayListExtra("imagelist").get(currentItem));
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            dialog.hide();
            Toast.makeText(getApplicationContext(), "Downloaded in picture folder...", Toast.LENGTH_LONG).show();

        }

    }
    private void downloadImages(String imageURL)
    {


        System.out.println(imageURL);
        URL imageUrl; //your URL from which image to be downloaded
        String domain;
        try
        {
            imageUrl = new URL(imageURL);

            HttpURLConnection urlConnection;
            try
            {
                urlConnection = (HttpURLConnection) imageUrl.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                urlConnection.connect();
                File filepath = Environment.getExternalStorageDirectory();

                File f = new File(filepath.getAbsolutePath()
                        + "/SarcasticJokes/");

                if (!f.exists())
                {
                    f.mkdirs();
                }
                Random rand = new Random();
                int value = rand.nextInt(1000);
                String[] split = imageURL.split("/");
                File file = new File(f, split[4]); // Here you can save the image with name and extension
                System.out.println("Path" + file);
                if (!file.exists())
                {
                    file.createNewFile();
                }
                FileOutputStream fileOutput = new FileOutputStream(file);
                InputStream inputStream = urlConnection.getInputStream();
                byte[] buffer = new byte[1024];
                int bufferLength = 0; // used to store a temporary size of the
                // buffer
                while ((bufferLength = inputStream.read(buffer)) > 0)
                {
                    fileOutput.write(buffer, 0, bufferLength);
                }
                fileOutput.close();

                System.out.println("FILE    " + file.getAbsolutePath());

                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),bmOptions);
              /*  bitmap = Bitmap.createScaledBitmap(bitmap,parent.getWidth(),parent.getHeight(),true);*/
                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Jockey" , "Jockey");



            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

    }

    private void share_pic(String image_url) {
        String[] split = image_url.split("/");
        File filepath = Environment.getExternalStorageDirectory();
        File f = new File(filepath + "/SarcasticJokes/" + split[4]);
        System.out.println("File" + f);
        if (f.exists()){
            System.out.println("Hello");
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
            shareIntent.setType("image/jpg");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
        } else {
            dialog = new SpotsDialog(this, R.style.Custom2);
            dialog.show();
            new DownloadImageAsyncTask1()
                    .execute();
        }
    }


    class DownloadImageAsyncTask1 extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... params)
        {
            // TODO Auto-generated method stub
            Integer currentItem = viewPager.getCurrentItem();
            downloadImages1(getIntent().getStringArrayListExtra("imagelist").get(currentItem));
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            dialog.hide();
            Integer currentItem = viewPager.getCurrentItem();
            File filepath = Environment.getExternalStorageDirectory();
            String image_url = getIntent().getStringArrayListExtra("imagelist").get(currentItem);
            String[] split = image_url.split("/");
            File f = new File(filepath + "/SarcasticJokes/" + split[4]);
            System.out.println("File" + f);
            if (f.exists()){
                System.out.println("Hello");
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.setType("image/jpg");
                startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
            }

        }

    }


    private void downloadImages1(String imageURL)
    {


        System.out.println(imageURL);
        URL imageUrl; //your URL from which image to be downloaded
        String domain;
        try
        {
            imageUrl = new URL(imageURL);

            HttpURLConnection urlConnection;
            try
            {
                urlConnection = (HttpURLConnection) imageUrl.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                urlConnection.connect();
                File filepath = Environment.getExternalStorageDirectory();

                File f = new File(filepath.getAbsolutePath()
                        + "/SarcasticJokes/");
                if (!f.exists())
                {
                    f.mkdirs();
                }
                Random rand = new Random();
                int value = rand.nextInt(1000);
                String[] split = imageURL.split("/");
                File file = new File(f,  split[4]); // Here you can save the image with name and extension
                System.out.println("Path" + file);
                if (!file.exists())
                {
                    file.createNewFile();
                }
                FileOutputStream fileOutput = new FileOutputStream(file);
                InputStream inputStream = urlConnection.getInputStream();
                byte[] buffer = new byte[1024];
                int bufferLength = 0; // used to store a temporary size of the
                // buffer
                while ((bufferLength = inputStream.read(buffer)) > 0)
                {
                    fileOutput.write(buffer, 0, bufferLength);
                }
                fileOutput.close();

                System.out.println("FILE    " + file.getAbsolutePath());

                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),bmOptions);
              /*  bitmap = Bitmap.createScaledBitmap(bitmap,parent.getWidth(),parent.getHeight(),true);*/
                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Jockey" , "Jockey");



            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

    }

    private void add_favourite(String image_url) {
        Cursor cr = dataBaseHelper.getParticularImage(image_url);
        if(cr.getCount() == 0){
            dataBaseHelper.insertImage(image_url);
            Toast.makeText(getApplicationContext(), "Added to favourite list.", Toast.LENGTH_SHORT).show();
        } else {
            dataBaseHelper.deleteImage(image_url);
            Toast.makeText(getApplicationContext(), "Removed from favourtie list.", Toast.LENGTH_SHORT).show();
            if (list_type == 2){
                Intent i = new Intent(ImagePreview.this, HomeScreen.class);
                startActivity(i);
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        imageItems = new ArrayList<String>();
    }
}
