package com.example.laba3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdapterListStudents extends RecyclerView.Adapter<AdapterListStudents.MyViewHolder>{

    public DatabaseHelper dbHelper;
    public SQLiteDatabase database;
    public Cursor cursor;


    private int size;

    public AdapterListStudents(Context context) {

        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();

        cursor = database.rawQuery("SELECT COUNT(*) FROM students", null);
        cursor.moveToFirst();
        this.size =  cursor.getInt(0);
        cursor = database.rawQuery("SELECT * FROM students", null);
        cursor.moveToFirst();
        Log.d("SIZE_STUDENTS", String.valueOf(size));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_list_students, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return size;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_id;
        TextView tv_surname;
        TextView tv_name;
        TextView tv_fat;
        TextView tv_time;
        String surname, name, fat;


        public MyViewHolder(View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_surname = itemView.findViewById(R.id.tv_surname);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_fat = itemView.findViewById(R.id.tv_fat);
            tv_time = itemView.findViewById(R.id.tv_time);

        }

        void bind() {
            if (!cursor.isAfterLast()) {
                tv_id.setText(cursor.getString(0));
                surname = cursor.getString(3);
                name = cursor.getString(1);
                fat = cursor.getString(2);



                tv_name.setText(name);
                tv_surname.setText(surname);
                tv_fat.setText(fat);
                tv_time.setText(cursor.getString(4));
                cursor.moveToNext();
            } else {
                cursor.close();
                dbHelper.close();
            }
        }
    }
}
