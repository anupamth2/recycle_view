package com.example.recycle_view;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class local_database_2 extends SQLiteOpenHelper {
    public local_database_2(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super( context, "my_local_database", factory, version );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String s1=" CREATE TABLE MY_DATABASE(FEILD_ID INT PRIMARY KEY,FEILD_DES VARCHAR(1000),FEILD_HEAD VARCHAR(500),FEILD_IMG VARCHAR(1000));";
        String s2="CREATE TABLE SECTION1(SEC1_ID INT PRIMARY KEY,SEC1_DES VARCHAR(2000),SEC1_HEAD VARCHAR(1000),SEC1_IMG VARCHAR(1000),feild_id INT,FOREIGN KEY(feild_id) REFERENCES MY_DATABASE(feild_id));";
        String s3="CREATE TABLE SUB_SECTION1(SUB_SEC1_ID INT PRIMARY KEY,SUB_SEC1_DES VARCHAR(2000),SUB_SEC1_HEAD VARCHAR(1000),SUB_SEC1_IMG VARCHAR(1000),sec1_id INT,FOREIGN KEY(sec1_id) REFERENCES SECTION1(sec1_id));";
        String s4="CREATE TABLE PRODUCT(PRO_ID INT PRIMARY KEY,PRO_DES VARCHAR(10000),PRO_HEAD VARCHAR(1000),PRO_IMG1 VARCHAR(2000),PRO_IMG2 VARCHAR(2000),PRO_IMG3 VARCHAR(2000),PRO_IMG4 VARCHAR(2000),sub_sec1_id INT,FOREIGN KEY(sub_sec1_id) REFERENCES SUB_SECTION1(sub_sec1_id));";
        try {
            sqLiteDatabase.execSQL( s1 );
            sqLiteDatabase.execSQL( s2 );
            sqLiteDatabase.execSQL( s3 );
            sqLiteDatabase.execSQL( s4 );

            Log.d("table","successful table created s2");
        }
        catch (Exception e)
        {
            Log.d("table","we get some error "+e);

        }

    }
    public  void product_write(int id,String heading,String des,String file_name)
    {
        ArrayList<String> l1=new ArrayList<>(  );
        string_operatiion st=new string_operatiion();
        l1=st.extract_string( des );
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cn=new ContentValues(  );
        cn.put( "PRO_ID",id );
        cn.put("PRO_DES",l1.get( 4 ));
        cn.put( "PRO_HEAD",heading );
        cn.put( "PRO_IMG1",l1.get( 1 ) );
        cn.put( "PRO_IMG2",l1.get( 2 ) );
        cn.put( "PRO_IMG3",l1.get( 3 ) );
        cn.put( "PRO_IMG4",file_name );
        cn.put( "sub_sec1_id",Integer.parseInt( l1.get( 0 )) );
        try{
            db.insertWithOnConflict( "PRODUCT",null,cn,SQLiteDatabase.CONFLICT_REPLACE );
            Log.d("product","table insertted successfully");
            db.close();
        }
        catch (Exception e)
        {
            Log.d("product","we have received some error "+e);

        }

    }
    public ArrayList<String> product_read()
    {
        ArrayList<String> s1=new ArrayList<>(  );
        SQLiteDatabase db=this.getReadableDatabase();
        String s11="select * from product;";
        try {
            Cursor cr = db.rawQuery( s11, null );
            if(cr.moveToFirst())
            {
                do{
                    s1.add( Integer.toString( cr.getInt( 0 ) ));
                    s1.add(cr.getString( 1 ));
                    s1.add(cr.getString( 2 ));
                    s1.add(cr.getString( 3 ));
                    s1.add(cr.getString( 4 ));
                    s1.add(cr.getString( 5 ));
                    s1.add(cr.getString( 6 ));
                    s1.add(Integer.toString( cr.getInt( 7 )));
                    Log.d("read_product","read product successfully");



                }while(cr.moveToNext());
            }

        }catch (Exception e)
        {
            Log.d("read_product","failed to read "+e);
        }
        return s1;


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
