package com.example.berita_09031281722080;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/** CREATE BY :
 *  NAME : NUR HANNA
 *  NIM : 09031281722080
 */
public class BeritaFragment extends Fragment {
    /** URL untuk berita dari data NewsApi */
    private static final String NEWS_REQUEST_URL =
            "https://newsapi.org/v2/top-headlines?country=id&apiKey=902444c60218414a89dbb9c3d7ab3938";

    /** Adapter untuk daftar berita */
    private BeritaAdapter mAdapter;

    /** TextView yang ditampilkan jika daftar kosong */
    private TextView mEmptyStateTextView;


    public BeritaFragment() {
        // Required empty public constructor
    }

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.category_activity, container, false);

        View tulisanAtas = rootView.findViewById(R.id.nama);
        tulisanAtas.setVisibility(View.GONE);

        View field = rootView.findViewById(R.id.edit);
        field.setVisibility(View.GONE);

        View tombol = rootView.findViewById(R.id.cari);
        tombol.setVisibility(View.GONE);


        // Cari referensi pada {@link ListView} pada layout
        ListView beritaListView = (ListView) rootView.findViewById(R.id.list);


        mEmptyStateTextView = (TextView) rootView.findViewById(R.id.empty_view);
        beritaListView.setEmptyView(mEmptyStateTextView);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();//method ini memerlukan permission manifest itu

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            BeritaAsyncTask task = new BeritaAsyncTask();
            task.execute(NEWS_REQUEST_URL);

            //    loaderManager.initLoader(BERITA_LOADER_ID, null, this);
        } else {

            View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }


        // Buat adapter baru yang mengambil daftar kosong gempa sebagai inputnyaåå
        mAdapter = new BeritaAdapter(getActivity(), new ArrayList<Berita>());

        // Atur adapter di {@link ListView}
        // agar daftar dapat diisi di antarmuka pengguna
        beritaListView.setAdapter(mAdapter);


        // Atur item click listener di ListView, yang mengirimkan intent ke perambah web
        // untuk membuka situs web dengan lebih banyak informasi mengenai gempa tertentu.
        beritaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Temukan gempa terkini yang diklik di
                Berita beritaSekarang = mAdapter.getItem(position);

                // Ubah String URL menjadi objek URI (untuk memasuki konstruktor Intent)
                Uri beritaUri = Uri.parse(beritaSekarang.getUrl());

                // Buat intent baru untuk melihat URI gempa
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, beritaUri);

                // Kirimkan intent agar membuka aktivitas baru
                startActivity(websiteIntent);
            }

        });
        return rootView;

    }


    private class BeritaAsyncTask extends AsyncTask<String, Void, List<Berita>> {

        @Override
        protected List<Berita> doInBackground(String... urls) {
            // Jangan lakukan request jika tidak ada URL, atau jika URL pertama nol.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Berita> result = Query.fetchNewsData(urls[0]);
            return result;

        }

        @Override
        protected void onPostExecute(List<Berita> data) {

            // Bersihkan adapter dari data gempa sebelumnya
            mAdapter.clear();

            // Sembunyikan indikator loading sebab datanya sudah dimuat
            View loadingIndicator = rootView.findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Atur teks status kosong agar menampilkan "Tidak ditemukan gempa."
            mEmptyStateTextView.setText(R.string.no_news);

            // Jika ada daftar {@link Berita} yang valid, tambahkan ke data set adapter.
            // Ini akan memicu pembaruan ListView..
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }

        }
    }


}
