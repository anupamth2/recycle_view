package com.example.recycle_view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String[] name = {"ram", "syam", "mohan", "ghansyam"};
    String[] des = {"it is 1", "it is 2", "it is 3", "it is 4"};
    Integer[] integers = {R.drawable.ic_baseline_3d_rotation_24, R.drawable.ic_baseline_account_circle_24, R.drawable.ic_baseline_accessibility_new_24, R.drawable.ic_baseline_accessible_24};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        local_database localDatabase=new local_database( this,"enm_mart_data",null,1 );
        ArrayList<String> lq=new ArrayList<>(  );
        setRecyclerView();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        Log.d( "myclass","to get file " );
        firebase_class fb=new firebase_class();
        fb.get_id_1( 123,getApplicationContext() );
        lq=localDatabase.read();
        EditText e11=findViewById( R.id.editTextTextPersonName );
        e11.setText( lq.toString() );
        string_operatiion st=new string_operatiion();
        String string1="34322|fere.jpeg|ddwwe|sdwweww|";
        ArrayList<String> tl3=new ArrayList<>(  );
        tl3=st.extract_string( string1 );
        Log.d("string"," "+tl3);
        Toast.makeText(this,"table created "+lq,Toast.LENGTH_LONG ).show();
        local_database_2 ld=new local_database_2( this,"my_local_database",null,1 );
        ArrayList<String> see=new ArrayList<>(  );
        see=ld.product_read();
        Log.d("read_product", "the size "+see);







    }
    public  void section_fun(View view)
    {
        Intent intent=new Intent( this,product_description_class.class );
        startActivity( intent );
    }
    public void setRecyclerView()
    {
        /*recyclerView = findViewById( R.id.recycle );
        recyclerView.setHasFixedSize( true );
        layoutManager=new LinearLayoutManager( this );
        recyclerView.setLayoutManager( layoutManager );
        adapter=new programAdapter( this,name,des,integers );
        recyclerView.setAdapter( adapter );*/

    }
    public void refresh_page(View view)
    {
        Intent in=new Intent( this,break_class.class );
        //startActivity( in );
    }
}