package com.gappydevelopers.xsarcasm.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;

import com.gappydevelopers.xsarcasm.adapter.ImageAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.gappydevelopers.xsarcasm.R;
import com.gappydevelopers.xsarcasm.databasehelper.DataBaseHelper;
import com.gappydevelopers.xsarcasm.sharedprefrence.UserLocalStore;
import com.gappydevelopers.xsarcasm.utils.Utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class ImagePreview extends AppCompatActivity {

    UserLocalStore userLocalStore;
    ViewPager viewPager;
    private List<String> imageItems;
    String position = null;
    LinearLayout linearLayout;
    AlertDialog dialog;
    DataBaseHelper dataBaseHelper;
    Integer list_type;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;
    ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Extreme Sarcasm");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

        linearLayout = (LinearLayout) findViewById(R.id.imageLayout);


//            mAdView = findViewById(R.id.adView);
            MobileAds.initialize(this, getString(R.string.ad_id));
//            AdRequest adRequest = new AdRequest.Builder().build();
//            mAdView.loadAd(adRequest);
//            mAdView.setAdListener(new AdListener() {
//                @Override
//                public void onAdLoaded() {
//                    System.out.println("Hello I am ad");
//                }
//
//                @Override
//                public void onAdFailedToLoad(int errorCode) {
//                    System.out.println("Hello I amwklwjwqwqjljwqjwqjw ad");
//                }
//            });
//
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(getString(R.string.int_ad));
            mInterstitialAd.loadAd(new AdRequest.Builder().build());

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        dataBaseHelper = new DataBaseHelper(this);

        if (getIntent().hasExtra("imagelist")) {
            imageItems = getIntent().getStringArrayListExtra("imagelist");
            position = getIntent().getStringExtra("position");
            /*list_type = getIntent().getIntExtra("type", 0);*/

         /*   if (list_type == 1) {
                setTitle("Jockey");
            } else if (list_type == 2) {
                setTitle("My Favourites");
            }*/

        }

        imageAdapter = new ImageAdapter(this, imageItems);
        viewPager = (ViewPager) findViewById(R.id.image);
        viewPager.setAdapter(imageAdapter);
        viewPager.setCurrentItem(Integer.parseInt(position));
     /*   viewPager.setPageTransformer(true, new TabletTransformer());*/


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
                addFavourite(getIntent().getStringArrayListExtra("imagelist").get(currentItem));
                return true;
            case R.id.action_download:
                Integer currentItem2 = viewPager.getCurrentItem();
                downloadPic(getIntent().getStringArrayListExtra("imagelist").get(currentItem2));
                return true;
            case R.id.action_share:
                Integer currentItem1 = viewPager.getCurrentItem();
                sharePic(getIntent().getStringArrayListExtra("imagelist").get(currentItem1));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void checkPermission(int flag) {
        userLocalStore.setFlag(flag);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {

            System.out.println("Dialog Show 1");
            dialog = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build();
            //dialog = new SpotsDialog(this, R.style.Custom1);
            dialog.show();
            new DownloadImageAsyncTask(flag)
                    .execute();
        }

    }

    private void downloadPic(String image_url) {
        String[] split = image_url.split("/");
        File filepath = Environment.getExternalStorageDirectory();
        File f = new File(filepath + "/" + split[split.length -1]);
        System.out.println("File" + f);
        if (f.exists()) {
            Toast.makeText(getApplicationContext(), "File already downloaded...", Toast.LENGTH_LONG).show();
        } else {
            checkPermission(0);
        }
    }

    private void sharePic(String image_url) {
        String[] split = image_url.split("/");
        File filepath = Environment.getExternalStorageDirectory();
        File f = new File(filepath + "/" + split[split.length -1]);
        System.out.println("File" + f);
        if (f.exists()) {
            sharingPic(f);
        } else {
            checkPermission(1);
        }
    }

    class DownloadImageAsyncTask extends AsyncTask<Void, Void, Void> {
        public int flag;

        public DownloadImageAsyncTask(int flag) {
            this.flag = flag;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            Integer currentItem = viewPager.getCurrentItem();
            downloadImages(getIntent().getStringArrayListExtra("imagelist").get(currentItem));
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            dialog.dismiss();
            if (flag == 0) {

                if (android.os.Build.VERSION.SDK_INT != 22) {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
                Toast.makeText(getApplicationContext(), "Downloaded in files...", Toast.LENGTH_LONG).show();
            } else if (flag == 1) {
                Integer currentItem1 = viewPager.getCurrentItem();
                String[] split = getIntent().getStringArrayListExtra("imagelist").get(currentItem1).split("/");
                File filepath = Environment.getExternalStorageDirectory();
                File f = new File(filepath + "/"  + split[split.length -1 ]);
                if (f.exists()) {
                    sharingPic(f);
                }
            }
        }

    }

    private void downloadImages(String imageURL) {


        System.out.println(imageURL);
        URL imageUrl; //your URL from which image to be downloaded
        String domain;
        int count;
        try {
            imageUrl = new URL(imageURL);

            URLConnection urlConnection;
            try {

                urlConnection = imageUrl.openConnection();
                urlConnection.connect();
                //urlConnection = (HttpURLConnection) imageUrl.openConnection();
                //urlConnection.setRequestMethod("GET");
                //urlConnection.setDoOutput(true);
                //urlConnection.connect();

                int lenghtOfFile = urlConnection.getContentLength();

                File filepath = Environment.getExternalStorageDirectory();
                File f = new File(filepath.getAbsolutePath());

                if (!f.exists()) {
                    f.mkdirs();
                }

                String[] split = imageURL.split("/");
                File file = new File(f + "/" +split[split.length -1]); // Here you can save the image with name and extension
                System.out.println("Path" + file);
                if (!file.exists()) {
                    file.createNewFile();
                }
   //             FileOutputStream fileOutput = new FileOutputStream(file);
  //              InputStream inputStream = urlConnection.getInputStream();
   //             byte[] buffer = new byte[1024];
//                int bufferLength = 0; // used to store a temporary size of the
//                // buffer
//                while ((bufferLength = inputStream.read(buffer)) > 0) {
//                    fileOutput.write(buffer, 0, bufferLength);
//                }
//                fileOutput.close();
//
//                System.out.println("FILE    " + file.getAbsolutePath());



                InputStream input = new BufferedInputStream(imageUrl.openStream(),
                        8192);

                // Output stream
                OutputStream output = new FileOutputStream(file);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();



                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bmOptions);
                /*  bitmap = Bitmap.createScaledBitmap(bitmap,parent.getWidth(),parent.getHeight(),true);*/
                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Jockey", "Jockey");


            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private void addFavourite(String image_url) {
        Cursor cr = dataBaseHelper.getParticularImage(image_url);
        if (cr.getCount() == 0) {
            dataBaseHelper.insertImage(image_url);
            Toast.makeText(getApplicationContext(), "Added to favourite list.", Toast.LENGTH_SHORT).show();
        } else {
            dataBaseHelper.deleteImage(image_url);
            Toast.makeText(getApplicationContext(), "Removed from favourtie list.", Toast.LENGTH_SHORT).show();
/*            if (list_type == 2) {
                Intent i = new Intent(ImagePreview.this, MainScreen.class);
                startActivity(i);
            }*/
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        imageItems = new ArrayList<String>();

        userLocalStore = new UserLocalStore(this);

        if (!userLocalStore.getuserloggedIn()) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.imageLayout), "Swipe to view other images", Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();

            userLocalStore.setUserloggedIn(true);
        }

        if (!new Utils().check_connection(ImagePreview.this)) {
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.imageLayout), "No internet connection!", Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.RED);

            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();


        }
    }

    @Override
    public void onBackPressed() {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
        Intent i = new Intent(ImagePreview.this, MainScreen.class);
        startActivity(i);
    }

    public void sharingPic(File f) {
        System.out.println(" Hello");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), "com.gappydevelopers.xsarcasm.provider", f);
            System.out.println(photoUri);
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, photoUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.setType("image/jpg");
            startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));


        } else {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.setType("image/jpg");
            startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
        }
    }


    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dialog = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build();
                    //dialog = new SpotsDialog(this, R.style.Custom1);
                    System.out.println("Hello");
                    dialog.show();
                    new DownloadImageAsyncTask(userLocalStore.getFlag())
                            .execute();

                } else {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        boolean showRationale = shouldShowRequestPermissionRationale(permissions[0]);
                        if (!showRationale) {
                            Snackbar snackbar = Snackbar
                                    .make(findViewById(R.id.imageLayout), "Enable permission to download image.", Snackbar.LENGTH_LONG)
                                    .setAction("Go to settings", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                                            intent.setData(uri);
                                            startActivityForResult(intent, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                                        }
                                    });
                            snackbar.setActionTextColor(Color.RED);
                            View sbView = snackbar.getView();
                            TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
                            textView.setTextColor(Color.YELLOW);
                            snackbar.show();
                        } else {
                            Utils.showSnackBar(linearLayout, getString(R.string.download_permission));
                        }
                    }
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}
