package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.network.api.IUserServer;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseConsumer;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;

import java.util.HashMap;
import java.util.Map;


/**
 * @author: aini
 * @date 2018/7/17 11:26
 * @description 用户绑定新手机
 */
public class BindPhonePresenter extends BasePresenter<BaseView<String>>{

    public void bindPhone(String loginname,String findcode){
        getView().onLoading();

        Map<String,Object> map = new HashMap<>();
        map.put("loginname",loginname);
        map.put("findcode",findcode);

        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IUserServer.class)
                .bindPhone(map)
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<Object>() {
                    @Override
                    protected void onSuccees(BaseEntity<Object> t) throws Exception {
                        getView().successed(t.getmMessage());
                    }
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code, error);
                    }
                }, new BaseErroeConsumer() {
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code,error);
                    }
                }, () -> {
                });
    }
}
