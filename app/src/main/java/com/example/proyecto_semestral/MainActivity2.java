package com.example.proyecto_semestral;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taller_28_foto.R;

import java.io.File;

public class MainActivity2 extends AppCompatActivity {

    private ListView listView;
    private ImageView imageView2;
    private String[] archivos;
    private ArrayAdapter<String> adaptador;
    private String ruta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        archivos = dir.list();
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, archivos);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adaptador);

        imageView2 = findViewById(R.id.imageView2);

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCurrentImage();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showImage(i);
            }
        });

    }

    private void showImage(int position) {
        Bitmap bitmap = BitmapFactory.decodeFile(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + archivos[position]);
        imageView2.setImageBitmap(bitmap);
    }

    private void deleteCurrentImage() {
        if (archivos.length > 0) {
            File fileToDelete = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + archivos[0]);
            boolean deleted = fileToDelete.delete();
            if (deleted) {
                archivos = getExternalFilesDir(Environment.DIRECTORY_PICTURES).list();
                adaptador.notifyDataSetChanged();
                imageView2.setImageResource(0); // Clear the ImageView after deletion
            }
        }
    }
}
