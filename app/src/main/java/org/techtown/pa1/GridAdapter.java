package org.techtown.pa1;

import android.graphics.Bitmap;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private int width;
    private int height;
    ArrayList<Bitmap> img_list;

    public GridAdapter(ArrayList<Bitmap> img_list, int width, int height){
        this.img_list = img_list;
        this.width = width;
        this.height = height;
    };

    @Override
    public int getCount() {
        return img_list.size();
    }

    @Override
    public Object getItem(int position) {
        return img_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;

        if(convertView == null){
            imageView = new ImageView(parent.getContext());
            imageView.setLayoutParams(new GridView.LayoutParams(width, height));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(0, 1, 0, 1);
        }
        else{
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(img_list.get(position));

        return imageView;
    }

    public void setImg_list(ArrayList<Bitmap> img_list){
        this.img_list = img_list;
    }

    public void setWH(int width, int height){
        this.width = width;
        this.height = height;
    }

    public ArrayList<Bitmap> getBitmap(){
        return img_list;
    }

}
