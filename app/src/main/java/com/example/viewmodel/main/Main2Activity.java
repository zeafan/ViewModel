package com.example.viewmodel.main;

import android.content.Context;
import android.os.Bundle;

import com.example.viewmodel.model.Favourites;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.viewmodel.R;

import java.util.Date;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private FavouritesAdapter mFavAdapter;
    private FavouriteViewModel mFavViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.listView);
        FloatingActionButton fab = findViewById(R.id.fab);
        mFavViewModel = ViewModelProviders.of(this).get(FavouriteViewModel.class);

        List<Favourites> favourites = mFavViewModel.getFavs();
        mFavAdapter = new FavouritesAdapter(this, R.layout.list_item_row, favourites);
        listView.setAdapter(mFavAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public class FavouritesAdapter extends ArrayAdapter<Favourites> {

        private class ViewHolder {
            TextView tvUrl;
            TextView tvDate;
        }

        public FavouritesAdapter(Context context, int layoutResourceId, List<Favourites> todos) {
            super(context, layoutResourceId, todos);
        }

        @Override
        @NonNull
        public View getView(int position, View convertView, ViewGroup parent) {
            Favourites favourites = getItem(position);
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.list_item_row, parent, false);
                viewHolder.tvUrl = convertView.findViewById(R.id.tvUrl);
                viewHolder.tvDate = convertView.findViewById(R.id.tvDate);
                convertView.setTag(R.id.VH, viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag(R.id.VH);

            }

            viewHolder.tvUrl.setText(favourites.mUrl);
            viewHolder.tvDate.setText((new Date(favourites.mDate).toString()));
            convertView.setTag(R.id.POS, position);
            return convertView;
        }

    }

}
