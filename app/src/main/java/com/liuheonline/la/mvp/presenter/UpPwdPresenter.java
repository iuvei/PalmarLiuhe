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
 * @description 用户修改密码
 */
public class UpPwdPresenter extends BasePresenter<BaseView<Object>>{

    public void upUserPwd(String oldpassword,String password,String repassword){
        getView().onLoading();

        Map<String,Object> map = new HashMap<>();
        map.put("oldpassword",oldpassword);
        map.put("password",password);
        map.put("repassword",repassword);

        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IUserServer.class)
                .upUserPwd(map)
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<Object>() {
                    @Override
                    protected void onSuccees(BaseEntity<Object> t) throws Exception {
                        getView().successed(t.getmData());
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
