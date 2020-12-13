package com.example.recycle_view;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class local_database extends SQLiteOpenHelper {
    public local_database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super( context, name, factory, version );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            String se = "create table if not exists local_table_1(id int primary key ,heading varchar(1000),description varchar(10000),file_name varchar(1000));";
            sqLiteDatabase.execSQL( se );
            Log.d("table_1","table created table 1");

        }
        catch (Exception e)
        {
            //Toast.makeText( this,"primary key error",Toast.LENGTH_LONG ).show();
            Log.d("table1","falied to create table");
        }

    }
    public int write(int id,String head,String details,String file_name)
    {
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues cn=new ContentValues(  );
        cn.put( "id",id );
        cn.put( "heading",head );
        cn.put( "description",details );
        cn.put( "file_name",file_name );
        try
        {
            database.insertWithOnConflict( "local_table_1",null,cn,SQLiteDatabase.CONFLICT_REPLACE);
            Log.d("table1","insetted successfully");
            database.close();
            return  1;
        }catch (Exception e)
        {
            Log.d("key34","this"+e);
            return 0;
        }

    }
    public ArrayList<String > read()
    {
        ArrayList<String> l1=new ArrayList<>(  );
        SQLiteDatabase db=this.getReadableDatabase();
        String command="select * from local_table_1";

        try
        {
            //db.execSQL( command );
            Cursor result=db.rawQuery(command,null);
            if(result.moveToFirst())
            {
                do{
                    l1.add( Integer.toString(result.getInt( 0 ) ));
                    l1.add( result.getString( 1 ) );
                    l1.add( result.getString( 2 ) );
                    l1.add( result.getString( 3 ) );

                }while (result.moveToNext());
            }

        }catch (Exception e)
        {
            Log.d("key33","search_msg"+e);


        }
        return l1;

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
