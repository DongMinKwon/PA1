package org.techtown.pa1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ArrayList<Bitmap> three_list = new ArrayList<>();
    ArrayList<Bitmap> four_list = new ArrayList<>();
    ArrayList<Bitmap> tmp;
    int [][] nearNode3 = {{1, 3, -1, -1}, {0, 2, 4, -1}, {1, 5, -1, -1}, {0, 4, 6, -1}, {1, 3, 5, 7}, {2, 4, 8, -1}, {3, 7, -1, -1}, {4, 6, 8, -1}, {5, 7, -1, -1}};
    int [][] nearNode4 = {{1, 4, -1, -1}, {0, 2, 5, -1}, {1, 3, 6, -1}, {2, 7, -1, -1},
            {0, 5, 8, -1}, {1, 4, 6, 9}, {2, 5, 7, 10}, {3, 6, 11, -1}, {4, 9, 12, -1}, {5, 8, 10, 13},
            {6, 9, 11, 14}, {7, 10, 15, -1}, {8, 13, -1, -1}, {9, 12, 14, -1}, {10, 13, 15, -1}, {11, 14, -1, -1}};

    Button three;
    Button four;
    Button shuffle;
    ImageView iv;
    int full_width;
    int full_height;
    int checknum = 3;
    int indexWhite = 8;
    int gameOver = 1;
    Bitmap compare;

    GridView matrix;
    GridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matrix = (GridView)findViewById(R.id.matrix);
        compare = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.white);

        three = (Button)findViewById(R.id.button3);
        four = (Button)findViewById(R.id.button4);
        shuffle = (Button)findViewById(R.id.button5);

        iv = (ImageView)findViewById(R.id.imageView2);

        make_bitmap();

        adapter = new GridAdapter(three_list, 360, 320);
        matrix.setAdapter(adapter);

        three.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                adapter.setImg_list(three_list);
                adapter.setWH(360, 320);
                matrix.setNumColumns(3);
                matrix.setColumnWidth(360);
                matrix.setAdapter(adapter);
                checknum = 3;
                indexWhite = 8;
                gameOver = 1;
            }
        });

        four.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                adapter.setImg_list(four_list);
                adapter.setWH(270, 240);
                matrix.setNumColumns(4);
                matrix.setColumnWidth(270);
                matrix.setAdapter(adapter);
                checknum = 4;
                indexWhite = 15;
                gameOver = 1;
            }
        });

        shuffle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                tmp = new ArrayList<>();
                if(checknum == 3) {
                    tmp.addAll(three_list);
                }
                else {
                    tmp.addAll(four_list);
                }
                Collections.shuffle(tmp);

                indexWhite = getIndexWhite(tmp);
                adapter.setImg_list(tmp);
                adapter.notifyDataSetChanged();
                gameOver = 0;
            }
        });


        matrix.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int movable = 0;

                if(gameOver == 0){
                    if(checknum == 3){
                        for(int i = 0; i<4; i++){
                            if(nearNode3[position][i] == indexWhite){
                                movable = 1;
                            }
                        }
                    }
                    else{
                        for(int i = 0; i<4; i++){
                            if(nearNode4[position][i] == indexWhite){
                                movable = 1;
                            }
                        }
                    }
                    if(movable == 1){
                        Collections.swap(adapter.getBitmap(), indexWhite, position);
                        indexWhite = position;
                        adapter.notifyDataSetChanged();
                    }

                    if(checknum == 3){
                        if(isOver(three_list, adapter.getBitmap()) == 1){
                            Toast.makeText(getApplicationContext(), "finish", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        if(isOver(four_list, adapter.getBitmap()) == 1){
                            Toast.makeText(getApplicationContext(), "finish", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else Toast.makeText(getApplicationContext(), "please shuffle", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void make_bitmap(){
        Bitmap muyaho = BitmapFactory.decodeResource(getResources(), R.drawable.muyaho);

        full_width = muyaho.getWidth();
        full_height = muyaho.getHeight();
        int width3 = full_width/3;
        int height3 = full_height/3;

        int width4 = full_width/4;
        int height4 = full_height/4;

        Bitmap sub_bitmap;
        for(int h = 0; h<=2; h++){
            for(int w = 0; w<=2;w++){
                if(w != 2 || h != 2)
                    sub_bitmap = Bitmap.createBitmap(muyaho, w*width3, h*height3, width3, height3);
                else
                    sub_bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.white);
                three_list.add(sub_bitmap);
            }
        }

        for(int h = 0; h<=3; h++){
            for(int w = 0; w<=3;w++){
                if(w != 3 || h != 3)
                    sub_bitmap = Bitmap.createBitmap(muyaho, w*width4, h*height4, width4, height4);
                else
                    sub_bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.white);
                four_list.add(sub_bitmap);
            }
        }
    }

    public int getIndexWhite(ArrayList<Bitmap> img_list){
        for(int i = 0; i<img_list.size(); i++){
            if(compare.sameAs(img_list.get(i))) return i;
        }

        return -1;
    }

    public int isOver(ArrayList<Bitmap> list1, ArrayList<Bitmap> list2){
        for(int i = 0; i<list1.size(); i++){
            if(list1.get(i).sameAs(list2.get(i))){
                continue;
            }
            else return 0;
        }
        gameOver = 1;
        return 1;
    }
}
