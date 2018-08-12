
// (Recycler View phase)(Fragments phase)
// we are using ItemClickListner to implement OnClick at Recycler item

package com.zshan.koqzs.androidonlinequizapp.Interface;

import android.view.View;

public interface ItemClickListener {

    void onClick(View view,int position,boolean isLongClick);
}
