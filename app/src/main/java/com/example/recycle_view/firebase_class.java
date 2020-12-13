package com.example.recycle_view;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

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

import static android.content.Context.MODE_PRIVATE;

public class firebase_class  {
    private StorageReference mStorageRef= FirebaseStorage.getInstance().getReference();
    FirebaseFirestore database=FirebaseFirestore.getInstance();
    Bitmap bitmap;
    public  void get_id_1(final int k1, final Context applicationContext)
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
                        //EditText e1=(EditText)findViewById( R.id.ttt1 );
                        //e1.setText( s );
                        int sss=Integer.parseInt( s );
                        int k12=k1;
                        while (sss>k12) {
                            k12++;



                            get_id_details_1( Integer.toString( k12 ),applicationContext );
                            Log.d("ths","we have call the function "+k12);
                        }
                        if(sss<=k1)
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


    private void get_id_details_1(final String s, final Context applicationContext) {
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
                        Log.d("msg1"," "+ss);
                       // EditText e1=findViewById( R.id.ttt1 );
                        //e1.append( ss );
                        string_operatiion string_operatiion=new string_operatiion();
                        ArrayList<String> str=new ArrayList<>(  );
                        str=string_operatiion.extract_string( description );
                        Log.d("description"," e "+str);
                        local_database database=new local_database( applicationContext,"enm_mart_data",null,1 );
                        database.write( Integer.parseInt( id ),heading,description,file_name );
                        local_database_2 ld=new local_database_2( applicationContext,"my_local_database",null,1);
                        ld.product_write( Integer.parseInt( id ),heading,description,file_name );

                        Log.d("msg123"," "+id+" "+description);

                        get_image_details_1(file_name,applicationContext);
                        load_image(file_name,applicationContext);
                        local_database enm=new local_database( applicationContext,"enm_mart_data",null,1 );
                        int k=enm.write( Integer.parseInt( id ),heading,description,file_name );

                        if(k==1)
                            Log.d("tag11","successful write in database "+file_name+" "+s);
                        else
                            Log.d("tag11","database write failed");
                        ArrayList<String> l1=new ArrayList<>(  );
                        //l1=enm.read();
                        //Log.d("tag12","l1 last index"+l1.get( l1.size()-1 ));
                    } else {
                        Log.d("msg5", "No suchh document");
                    }
                } else {
                    Log.d("msg5", "get failed with ", task.getException());
                }
            }
        });

    }
    private void get_image_details_1(final String file_name,final  Context applicationContext) {
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
                Log.d("msg11","image gain successfull "+file_name);

              //  ImageView im=(ImageView)findViewById( R.id.imageView3 );
                //im.setImageBitmap( bitmap1 );
                save_image_local(file_name,bitmap1,applicationContext);




            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("tag6","on failure eror"+e);
                // Toast.makeText( getApplicationContext(),"download failed",Toast.LENGTH_LONG ).show();
            }
        } );

    }
    private void save_image_local(String file_name, Bitmap bitmap1,Context applicationContext) {
        ContextWrapper cw=new ContextWrapper( applicationContext );
        File dir=cw.getDir( "enm_mart_local",MODE_PRIVATE );
        File file=new File(dir,file_name  );
        if(file.exists())
            Log.d("tag9","this file exist already "+file);
        if(!file.exists())
        {
            FileOutputStream fos = null;
            try{
                fos=new FileOutputStream(file);
                bitmap1.compress(Bitmap.CompressFormat.JPEG,100,fos);
                fos.flush();
                fos.close();
                Log.d("tag9","successful "+file_name);
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
    public void load_image(String file_name,Context applicationContext) {
        ContextWrapper cw=new ContextWrapper( applicationContext );
        File dir=cw.getDir( "enm_mart_local",MODE_PRIVATE );

        try {
            File f=new File(dir, file_name);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
           // ImageView im=(ImageView)findViewById( R.id.imageView3 );
          //  im.setImageBitmap( b );
            Log.d( "tag10","successfully loaded" );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("tag10","we got expection error"+e);
        }
    }
}
