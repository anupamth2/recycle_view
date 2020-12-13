package com.example.recycle_view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class section_class extends MainActivity implements programAdapter.OnNoteListener{
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String[] name = {"ram", "syam", "mohan", "ghansyam"};
    String[] des = {"it is 1", "it is 2", "it is 3", "it is 4"};
    Integer[] integers = {R.drawable.image_29, R.drawable.image_28, R.drawable.image27, R.drawable.ic_baseline_accessible_24};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.section_class_xml );
        recyclerView = findViewById( R.id.recycle1 );
        recyclerView.setHasFixedSize( true );
        layoutManager=new LinearLayoutManager( this );
        recyclerView.setLayoutManager( layoutManager );
        adapter=new programAdapter( this,name,des,integers,this );
        recyclerView.setAdapter( adapter );

    }

    @Override
    public void onNoteClick(int position) {

        Log.d("thss","we have click listener "+position);
        Toast.makeText( this,"we clicked "+position,Toast.LENGTH_LONG ).show();
    }
}
