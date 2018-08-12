package com.zshan.koqzs.androidonlinequizapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.zshan.koqzs.androidonlinequizapp.Common.Common;
import com.zshan.koqzs.androidonlinequizapp.Interface.ItemClickListener;
import com.zshan.koqzs.androidonlinequizapp.Model.Category;
import com.zshan.koqzs.androidonlinequizapp.ViewHolder.CategoryViewHolder;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {
    View myFragment;
    RecyclerView listCategory;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category,CategoryViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference categories;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database=FirebaseDatabase.getInstance();
        categories=database.getReference("Category");
    }

    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment= inflater.inflate(R.layout.fragment_categories, container, false);




        listCategory=(RecyclerView)myFragment.findViewById(R.id.listCategory);
        listCategory.setHasFixedSize(true);


        layoutManager =new LinearLayoutManager(container.getContext());

        listCategory.setLayoutManager(layoutManager);

        loadCategories();
        return myFragment;

    }

    private void loadCategories() {

        adapter= new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(
                Category.class,
                R.layout.category_layout,
                CategoryViewHolder.class,
                categories

        ) {
            @Override
            protected void populateViewHolder(CategoryViewHolder viewHolder, final Category model, int position) {
                viewHolder.category_name.setText(model.getName());
                Picasso.with(getActivity())
                        .load(model.getImage())
                        .into(viewHolder.category_image);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(getActivity(), String.format("%s|%s",adapter.getRef(position).getKey(),model.getName()), Toast.LENGTH_SHORT).show();
                        Intent startGame=new Intent(getActivity(),Start.class);
                        //Common.categoryId=adapter.getRef(position).getKey();
                        startActivity(startGame);


                    }
                });

            }
        };
        adapter.notifyDataSetChanged();
        listCategory.setAdapter(adapter);



    }

    public static Fragment newInstance() {
        return new CategoriesFragment();

    }
}


