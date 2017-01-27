package ua.com.hse.safetyaudit;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    String currentData, currentTime, currentDataHappened, currentTimeHappened, currentPlace, currentWho, currentInfo, currentInfoTotal;
    private static final int TAKE_PICTURE = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //** Обработка кнопки Сохранить
    public void currentInfoSave(View view) {
//** Получение текущей даты и времени из системы
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        currentData = day + "." + month + "." + year;
        currentTime = hour + "." + minutes;
//** Получение данных из заполненных полей
        EditText currentDateEditText = (EditText) findViewById(R.id.currentDataHappened);
        EditText currentTimeEditText = (EditText) findViewById(R.id.currentTimeHappened);
        EditText currentPlaceEditText = (EditText) findViewById(R.id.currentPlace);
        EditText currentWhoEditText = (EditText) findViewById(R.id.currentWho);
        EditText currentInfoEditText = (EditText) findViewById(R.id.currentInfo);

        currentDataHappened = currentDateEditText.getText().toString();
        currentTimeHappened = currentTimeEditText.getText().toString();
        currentPlace = currentPlaceEditText.getText().toString();
        currentWho = currentWhoEditText.getText().toString();
        currentInfo = currentInfoEditText.getText().toString();

    }

    public void currentInfoSend(View view) {

        //** Получение текущей даты и времени из системы
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        currentData = day + "." + month + "." + year;
        currentTime = hour + "." + minutes;
//** Получение данных из заполненных полей
        EditText currentDateEditText = (EditText) findViewById(R.id.currentDataHappened);
        EditText currentTimeEditText = (EditText) findViewById(R.id.currentTimeHappened);
        EditText currentPlaceEditText = (EditText) findViewById(R.id.currentPlace);
        EditText currentWhoEditText = (EditText) findViewById(R.id.currentWho);
        EditText currentInfoEditText = (EditText) findViewById(R.id.currentInfo);

        currentDataHappened = currentDateEditText.getText().toString();
        currentTimeHappened = currentTimeEditText.getText().toString();
        currentPlace = currentPlaceEditText.getText().toString();
        currentWho = currentWhoEditText.getText().toString();
        currentInfo = currentInfoEditText.getText().toString();

        currentInfoTotal = "Information:";
        currentInfoTotal += "\nDate and Time of record: " + currentData + " " + currentTime;
        currentInfoTotal += "\nDate: " + currentDataHappened;
        currentInfoTotal += "\nTime: " + currentTimeHappened;
        currentInfoTotal += "\nPlace: " + currentPlace;
        currentInfoTotal += "\nWho: " + currentWho;
        currentInfoTotal += "\nShort information:\n: " + currentInfo;


        //** отсылка письма с данными

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"soldiez@yandex.ru"});
        i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
        i.putExtra(Intent.EXTRA_TEXT, currentInfoTotal);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }
//** делаем фото, сохраняем и вставляем в вид
    public void takePhotoOne(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);
                    ImageView imageView = (ImageView) findViewById(R.id.takePhotoOne);
                    ContentResolver cr = getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);

                        imageView.setImageBitmap(bitmap);
                        Toast.makeText(this, selectedImage.toString(),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }



}
