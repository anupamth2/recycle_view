package com.example.recycle_view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class programAdapter extends RecyclerView.Adapter<programAdapter.ViewHolder> {

    Context context;

    String [] name_array;
    String [] des_array;
    Integer [] image;
    private OnNoteListener mOnNotelistener;



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView t1;
        TextView t2;
        ImageView im;
        OnNoteListener onNoteListener;


        public ViewHolder(@NonNull View itemView,OnNoteListener onNoteListener) {
            super( itemView );
            t1=itemView.findViewById( R.id.t1 );
            t2=itemView.findViewById( R.id.t2 );
            im=itemView.findViewById( R.id.imageView );
            this.onNoteListener=onNoteListener;

            itemView.setOnClickListener( this );
        }


        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick( getAdapterPosition() );

        }
    }
    public  programAdapter(Context context1, String[] name, String[] des, Integer[] im,OnNoteListener onNoteListener)
    {
        this.context=context1;
        this.name_array=name;
        this.des_array=des;
        this.image=im;
        this.mOnNotelistener=onNoteListener;

    }
    @NonNull
    @Override
    public programAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from( context );
        View view=inflater.inflate( R.layout.single_item,parent,false );
        ViewHolder viewHolder=new ViewHolder( view,mOnNotelistener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull programAdapter.ViewHolder holder, final int position) {

        holder.t1.setText( name_array[position] );
        holder.t1.setTextSize( 50 );

        holder.t2.setText( des_array[position] );
        holder.t2.setTextSize( 50 );
        holder.im.setImageResource( image[position] );
        holder.im.setScaleX( (float)1 );
        holder.im.setScaleY( (float)1 );




    }
    public interface OnNoteListener{
        void onNoteClick(int position);
    }


    @Override
    public int getItemCount() {
        return des_array.length;
    }
}
