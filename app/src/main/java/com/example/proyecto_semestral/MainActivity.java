package com.example.proyecto_semestral;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.taller_28_foto.R;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText et;
    private Button btn1, btn2, btn3;
    private ImageView imageView1;
    private String rutaImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);

        et = (EditText) findViewById(R.id.et);
        imageView1 = (ImageView) findViewById(R.id.imageView1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tomar_Foto();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recuperar_Foto();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ver_Foto();
            }
        });

    }

    public void Tomar_Foto() {
        // Obtenemos el nombre de la foto del EditText
        String nombreFoto = et.getText().toString().trim();

        // Verificamos si el nombre está vacío o nulo
        if (nombreFoto.isEmpty()) {
            Toast.makeText(this, "Ingrese un nombre para la foto", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imagenArchivo = null;

        try {
            imagenArchivo = Ruta_Foto(nombreFoto); // Pasamos el nombre de la foto
        } catch (IOException ex) {
            Log.e("Error", ex.toString());
        }

        if (imagenArchivo != null) {
            Uri fotoUri = FileProvider.getUriForFile(this, "com.example.taller_28_foto", imagenArchivo);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
            startActivityForResult(intent, 1);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK ){
            Bitmap imageBitmap = BitmapFactory.decodeFile(rutaImagen);
            imageView1.setImageBitmap(imageBitmap);

        }
    }

    public File Ruta_Foto(String nombreFoto) throws IOException {
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File foto = File.createTempFile(nombreFoto, ".jpg", directorio);

        rutaImagen = foto.getAbsolutePath();
        return foto;
    }


    public void Recuperar_Foto(){
        Bitmap imageBitmap = BitmapFactory.decodeFile(rutaImagen);
        imageView1.setImageBitmap(imageBitmap);
        Toast.makeText(this, "Foto Guardada", Toast.LENGTH_SHORT).show();
    }

    public void Ver_Foto(){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivityForResult(intent, 1);
    }
}