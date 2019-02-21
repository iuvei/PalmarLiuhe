package com.liuheonline.la.ui.widget.popu;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ysyy.aini.palmarliuhe.R;
import com.liuheonline.la.entity.BankNameEntity;
import com.liuheonline.la.entity.NoddEntity;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author: aini
 * @date 2018/8/1 15:20
 * @description 设置窗口
 */
public class AgencyPopu extends BasePopupWindow{

    RecyclerView recyclerView;
    private OnViewClickListener onViewClickListener;
    BaseQuickAdapter<NoddEntity.ListBean,BaseViewHolder> baseQuickAdapter;
    public AgencyPopu(Context context, OnViewClickListener onViewClickListener) {
        super(context);
        this.onViewClickListener = onViewClickListener;
        initAdapter();
        initRecycleview();
    }

    private void initAdapter(){
        baseQuickAdapter = new BaseQuickAdapter<NoddEntity.ListBean, BaseViewHolder>(R.layout.item_agency) {
            @Override
            protected void convert(BaseViewHolder helper, NoddEntity.ListBean item) {
                helper.setText(R.id.agency_id,item.getNum_id()+"");
                helper.setText(R.id.agency_text,item.getNodd()+"");
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onViewClickListener.onVeiwCilck(item);
                    }
                });
            }
        };
    }
    private void initRecycleview(){
        recyclerView = (RecyclerView) findViewById(R.id.agency_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(baseQuickAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }


    public void showPopupWindow(View view,List<NoddEntity.ListBean> list) {
        baseQuickAdapter.setNewData(list);
        super.showPopupWindow();
    }

    @Override
    protected Animation initShowAnimation() {
        return getTranslateVerticalAnimation(1f, 0f, 500);
    }

    @Override
    protected Animation initExitAnimation() {
        return getTranslateVerticalAnimation(0f, 1f, 500);
    }

    @Override
    public View getClickToDismissView() {
        return getPopupWindowView();
    }

    @Override
    public View onCreatePopupView() {
        return createPopupById(R.layout.popu_agencypicker);
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.picker_root);
    }

    public interface OnViewClickListener {
        void onVeiwCilck(NoddEntity.ListBean listBean);
    }
}
