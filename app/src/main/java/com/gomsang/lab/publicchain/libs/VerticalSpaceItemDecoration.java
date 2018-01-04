package com.gomsang.lab.publicchain.libs;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by devkg on 2018-01-05.
 */

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;

    public VerticalSpaceItemDecoration(int verticalSpaceHeight){
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // if not a last item, add space
        if(parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() -1 ){
            outRect.bottom = verticalSpaceHeight;
        }
    }
}