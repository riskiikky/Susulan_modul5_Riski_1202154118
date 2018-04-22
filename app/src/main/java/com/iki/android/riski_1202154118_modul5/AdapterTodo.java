package com.iki.android.riski_1202154118_modul5;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterTodo extends RecyclerView.Adapter<AdapterTodo.ViewHolder>{
    private List<Todo> mTodoList;
    private Context mContext;
    private RecyclerView mRecyclerV;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView todoNameTxtV;
        public TextView todoDescTxtV;
        public TextView todoPrioTxtV;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            todoNameTxtV = (TextView) v.findViewById(R.id.name); //text view todoname find berdasarkan name
            todoDescTxtV = (TextView) v.findViewById(R.id.desc); //text view tododesc find berdasarkan desc
            todoPrioTxtV = (TextView) v.findViewById(R.id.prio); //tex view todoprio find berdasarkan id prio
        }
    }


    public void add(int positition, Todo todo){
        mTodoList.add(positition, todo);
        notifyItemInserted(todo);

    }

    private void notifyItemInserted(Todo todo) {
    }

    public void remove(int position) {
        final Todo todo = mTodoList.get(position);
//        mTodoList.remove(position);
//        notifyItemRemoved(position);
        todoHelper dbHelper = new todoHelper(mContext);
        dbHelper.deleteTodoRecord(todo.getId(), mContext);

        mTodoList.remove(position);
//        mRecyclerV.removeViewAt(position);
        notifyItemRemoved(position); //notify item jika dihapus
        notifyItemRangeChanged(position, mTodoList.size());
        notifyDataSetChanged(); //notify data diset jika berubah

    }

    public AdapterTodo(List<Todo> myDataset, Context context, RecyclerView recyclerView) {
        mTodoList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }


    @Override
    public AdapterTodo.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.single_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Todo todo = mTodoList.get(position);
        holder.todoNameTxtV.setText(todo.getName()); //holder todoname set text dari class todo getname
        holder.todoDescTxtV.setText(todo.getDesc()); //holder todoname set text dari calss todo getdesc
        holder.todoPrioTxtV.setText(todo.getPrio()); //holder todoname set text dari class todo getprio

        //listen to single view layout click
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Choose option"); //set judul
                builder.setMessage("delete user?"); //delete user
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() { //jika tombol delete di tekan
                    @Override
                    public void onClick(DialogInterface dialog, int which) { //onclick dialog interface
                        todoHelper dbHelper = new todoHelper(mContext);
                        dbHelper.deleteTodoRecord(todo.getId(), mContext); //delete record

                        mTodoList.remove(position); //hapus dari posisi
                        mRecyclerV.removeViewAt(position); //hapus dari recycleview
                        notifyItemRemoved(position);//notify item telah dihapus
                        notifyItemRangeChanged(position, mTodoList.size());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { //jika tombol cancel ditekan
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); //dialog diabaikan
                    }
                });
                builder.create().show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mTodoList.size();
    }



}
