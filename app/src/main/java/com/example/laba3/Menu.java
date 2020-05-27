package com.example.laba3;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class Menu extends AppCompatActivity {

    private DateFormat format = new SimpleDateFormat("HH:mm:ss "); //"yyyy.MM.dd 'at'
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private ArrayList<String> FIO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
        insertStartInfo();

        dbHelper.close();

        Button btn_openDB = findViewById(R.id.btn_openDB);
        Button btn_addItemDB = findViewById(R.id.btn_addItemDB);
        Button btn_replace = findViewById(R.id.btn_replace);


        btn_openDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, ShowDatabase.class);
                startActivity(intent);
            }
        });

        btn_addItemDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = dbHelper.getWritableDatabase();

                Random random = new Random();
                int number;
                number = random.nextInt(FIO.size());
                Calendar thisDate = Calendar.getInstance();
                String data = format.format(thisDate.getTime());

                String[] arrFIO = FIO.get(number).split(" ");
                database.execSQL("INSERT INTO students(first_name, middle_name, last_name, time) " +
                        "VALUES (\'" + arrFIO[1] + "\', \'" + arrFIO[2] + "\', \'" + arrFIO[0] + "\', \'" + data + "\');");
                Toast toast = Toast.makeText(Menu.this, "Запись добавлена!", Toast.LENGTH_LONG);
                toast.show();
                FIO.remove(number);

                dbHelper.close();
            }
        });

        btn_replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = dbHelper.getWritableDatabase();
                database.execSQL("UPDATE students SET first_name = 'Иван', middle_name = 'Иванович', last_name = 'Иванов' WHERE id = (SELECT max(id) FROM students)");
                Toast toast = Toast.makeText(Menu.this, "Запись успешно изменена!", Toast.LENGTH_LONG);
                toast.show();

                dbHelper.close();
            }
        });
    }

    private void insertStartInfo() {

        FIO = new ArrayList<>();
        FIO.add("Трибогов Мирослав Ишевич");
        FIO.add("Моралев Петр Силыч");
        FIO.add("Канистров Дорн Никитович");
        FIO.add("Листьева Лара Крофтовна");
        FIO.add("Троцкий Василий Петрович");
        FIO.add("Борджиа Чезаре Флорентинович");
        FIO.add("Таницкий Михаил Криллович");
        FIO.add("Патова Лена Дмитриевна");
        FIO.add("Соловаьева Ольга Геннадиевна");
        FIO.add("Жилина Любовь Петровна");
        FIO.add("Сидорова Симона Мэлсовна");
        FIO.add("Узумаки Наруто Минатович");
        FIO.add("Петина Галина Михайловна");
        FIO.add("Бабочкина Вара Олеговна");
        FIO.add("Артемов Артем Артемович");


        Random random = new Random();
        int number;
        String[] arrFIO;

        for (int i = 0; i < 5; i++) {

            number = random.nextInt(FIO.size());

            Calendar thisDate = Calendar.getInstance();
            String data = format.format(thisDate.getTime());

            arrFIO = FIO.get(number).split(" ");

            database.execSQL("INSERT INTO students(first_name, middle_name, last_name, time) " +
                    "VALUES (\'" + arrFIO[1] + "\', \'" + arrFIO[2] + "\', \'" + arrFIO[0] + "\', \'" + data + "\');");


            FIO.remove(number);
        }

    }
}
