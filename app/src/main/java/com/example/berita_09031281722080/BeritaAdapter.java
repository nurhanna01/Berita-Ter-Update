package com.example.berita_09031281722080;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BeritaAdapter extends ArrayAdapter<Berita> {

    public BeritaAdapter(Context context, List<Berita> beritaa){
        super(context,0,beritaa);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //mengecek apakah ada view yang bisa digunakan kembali,jika tidak maka view akan di inflate
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_berita, parent, false);
        }

        // mencari dan ambil posisi pada class Berita
        Berita beritaSekarang = getItem(position);

        //mencari TextView menggunakan id  untuk menempatkan sumber
        TextView sumberView = (TextView) listItemView.findViewById(R.id.sumber);
        // Mengambil sumber pada objek beritaSekarang
        // set text tersebut pada textView
        sumberView.setText(beritaSekarang.getSumber());

        // Cari TextView dengan view ID judul
        TextView judulView = (TextView) listItemView.findViewById(R.id.judul);
        // Tampilkan judul berita di TextView
        judulView.setText(beritaSekarang.getJudul());

        String waktuOriginal = beritaSekarang.getWaktu();



        //Jika sudah ada 2 String terpisah, bisa kita tampilkan dalam 2 TextViews di tata letak list item.

        TextView tanggalView = (TextView) listItemView.findViewById(R.id.tanggal);
        String formatTanggal = formatDate(waktuOriginal);
        tanggalView.setText(formatTanggal);

        TextView jamView = (TextView) listItemView.findViewById(R.id.jam);
        String formatJam = formatTime(waktuOriginal);
        jamView.setText(formatJam);

        //String gambar = beritaSekarang.getUrlImage();
        //Picasso.get().load(gambar).into(imageView);
        ImageView ImageView = (ImageView) listItemView.findViewById(R.id.gambar);
        //Picasso.get().load(beritaSekarang.getUrlImage()).into(ImageView);

        if(TextUtils.isEmpty(beritaSekarang.getUrlImage())) {
            // Load default image
            ImageView.setImageResource(R.drawable.placeholder);
        } else {
            Picasso.get().load(beritaSekarang.getUrlImage()).into(ImageView);
        }



        return listItemView;

    }

    /**
     * Kembalikan string tanggal terformat ("Mar 3, 1984") dari objek Date.
     * @return
     */
    private String formatDate(String dateObject) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // format kita "HARUS SAMA TIDAK BOLEH FORMATNYA BEDA BAHKAN 1 CHAR PUN
        SimpleDateFormat output= new SimpleDateFormat("dd-MM-yyyy");
        Date tanggal = null;
        try {
            tanggal = input.parse(dateObject);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output.format(tanggal);

    }


    /**
     * Kembalikan string tanggal terformat ( "4:30 PM") dari objek Date.
     */
    private String formatTime(String dateObject){

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // format kita "HARUS SAMA TIDAK BOLEH FORMATNYA BEDA BAHKAN 1 CHAR PUN
        SimpleDateFormat output= new SimpleDateFormat("h:mm a");
        Date jam = null;
        try {
            jam = input.parse(dateObject);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output.format(jam);
    }

}

