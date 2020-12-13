package com.example.recycle_view;


import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class break_class extends MainActivity {
    private StorageReference mStorageRef=FirebaseStorage.getInstance().getReference();
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    Bitmap bitmap=null;
    String se;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.enm_image );
        try {


            EditText e1 = (EditText) findViewById( R.id.ttt1 );
            local_database enm = new local_database( getApplicationContext(), "enm_app", null, 1 );
            ArrayList<String> in = new ArrayList<>();
            in = enm.read();
            String k1 = ( in.get( in.size() - 4 ) );
            Log.d( "ths", " d " + k1 );
            getId(123);
            String s = e1.getText().toString();
            Intent ine=new Intent( getApplicationContext(),MainActivity.class );
            startActivity( ine );
            finish();
        }
        catch (Exception e)
        {
            Log.d("table4","error: "+e);
        }



    }
    //this function take a string as input and then it connect with the firebase
    //firestore and in the collection enm_mart it check the document with
    //input string name if it exist then collect all the details like id,heading,
    //description file_image
    //then it call three function
    // the first with file_image then fuction receive the image from firebase
    //and store in local storage
    //then load image take the file_name as input load it from the directory
    // and save to image view
    //at last it call enm class write function which write details received into
    // local database


    private void get_id_details(String s) {
        DocumentReference db=database.collection( "enm_mart" ).document(s);
        db.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("msg1", "DocumentSnapshot data: " + document.getData());
                        String id=document.getString( "id" );
                        String heading=document.getString( "heading" );
                        String description=document.getString( "description" );
                        String file_name=document.getString( "image_file" );
                        String ss=" ??? "+id+" "+heading+" "+description+" "+file_name;
                        EditText e1=findViewById( R.id.ttt1 );
                        e1.append( ss );
                        get_image_details(file_name);
                        load_image(file_name);
                        local_database enm=new local_database( getApplicationContext(),"enm_mart_data",null,1 );
                        int k=enm.write( Integer.parseInt( id ),heading,description,file_name );
                        if(k==1)
                            Log.d("tag11","successful write in database");
                        else
                            Log.d("tag11","database write failed");
                        ArrayList<String> l1=new ArrayList<>(  );
                        l1=enm.read();
                        Log.d("tag12","l1 last index"+l1.get( l1.size()-1 ));
                    } else {
                        Log.d("msg5", "No suchh document");
                    }
                } else {
                    Log.d("msg5", "get failed with ", task.getException());
                }
            }
        });

    }
    //this function take file name as input and then go to the directory
    //named enm_mart_local and load image from there and and set the image
    // the image view

    public void load_image(String file_name) {
        ContextWrapper cw=new ContextWrapper( getApplicationContext() );
        File dir=cw.getDir( "enm_mart_local",MODE_PRIVATE );

        try {
            File f=new File(dir, file_name);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView im=(ImageView)findViewById( R.id.imageView3 );
            im.setImageBitmap( b );
            Log.d( "tag10","successfully loaded" );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("tag10","we got expection error"+e);
        }
    }
    //this function take one input that is name of file
    //then with that fill name it connect with the firebase storage and
    // if a image is found with the input file_name then it download that
    //image and call a function named save image local which save the file
    //in local storage with input file name

    private void get_image_details(final String file_name) {
        String s="/"+file_name;
        StorageReference ref=mStorageRef.child(s);
        long maxbytes=1024*1024;
        if(bitmap==null)
            Log.d("bitmap","bitmap successful");


        ref.getBytes( maxbytes ).addOnSuccessListener( new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap1= BitmapFactory.decodeByteArray( bytes,0,bytes.length );
                bitmap=bitmap1;

                ImageView im=(ImageView)findViewById( R.id.imageView3 );
                //im.setImageBitmap( bitmap1 );
                save_image_local(file_name,bitmap1);




            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("tag6","on failure eror"+e);
                // Toast.makeText( getApplicationContext(),"download failed",Toast.LENGTH_LONG ).show();
            }
        } );

    }
    //this function take two input and file name of image and bitmap of image
    //then if the file not exist already then the function get dir
    //name enm_mart_local and then save the image with file_name received
    //this function do not call any other function

    private void save_image_local(String file_name, Bitmap bitmap1) {
        ContextWrapper cw=new ContextWrapper( getApplicationContext() );
        File dir=cw.getDir( "enm_mart_local",MODE_PRIVATE );
        File file=new File(dir,file_name  );
        if(!file.exists())
        {
            FileOutputStream fos = null;
            try{
                fos=new FileOutputStream(file);
                bitmap1.compress(Bitmap.CompressFormat.JPEG,100,fos);
                fos.flush();
                fos.close();
                Log.d("tag9","successful");
                // Toast.makeText( getApplicationContext(),"saved at "+file.toString(),Toast.LENGTH_LONG ).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.d("tag9","file not found ");

            } catch (IOException e) {
                Log.d("tag9","we are fun throwing error");
                e.printStackTrace();
            }
        }
    }

    //this function check there exist a id or not
    //if it exist it get the value
    //this get id function call the firebase firestore and check the value
    // inside the product document it check the value of global id
    //then it call the get_id_details with value of global id

    public void getId(final int k1)
    {
        final String s1="";

        DocumentReference db=database.collection( "enm_mart" ).document("product");
        db.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("msg1", "DocumentSnapshot data: " + document.getData());
                        String s=document.getString( "global_id" );
                        EditText e1=(EditText)findViewById( R.id.ttt1 );
                        e1.setText( s );
                        int sss=Integer.parseInt( s );
                        if(sss>k1) {

                            get_id_details( s );
                            Log.d("ths","we have call the function");
                        }
                        else if(sss<=k1)
                        {
                            Log.d("ths","no need to call");

                        }
                    } else {
                        Log.d("msg1", "No such document");
                    }
                } else {
                    Log.d("msg1", "get failed with ", task.getException());
                }
            }
        });


    }

}

