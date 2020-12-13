package com.example.recycle_view;

import android.util.Log;

import java.util.ArrayList;

public class string_operatiion {
    public ArrayList<String> extract_string(String s)
    {
        String s1="";
        int k=0;
        ArrayList<String> strings=new ArrayList<>(  );


        while(k<s.length())
        {
            s1="";
            while((k<s.length())&&(s.charAt( k )!='|'))
            {
                s1+=s.charAt( k );
                k++;

            }
            strings.add( s1 );
            Log.d("string  ",s1);
            s1="";
            k++;



        }
        return  strings;
    }
}
