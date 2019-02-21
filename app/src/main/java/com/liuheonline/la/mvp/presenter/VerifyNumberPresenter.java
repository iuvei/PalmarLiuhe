package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.network.api.IAccountServer;
import com.liuheonline.la.ui.LiuHeApplication;
import com.liuheonline.la.utils.RetrofitFactoryUtil;
import com.yxt.itv.library.base.BasePresenter;
import com.yxt.itv.library.base.BaseView;
import com.yxt.itv.library.http.retrofit.BaseConsumer;
import com.yxt.itv.library.http.retrofit.BaseEntity;
import com.yxt.itv.library.http.retrofit.SetThread;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Action;
import com.liuheonline.la.network.retrofit.BaseErroeConsumer;

/**
 * @author: aini
 * @date 2018/7/13 16:14
 * @description
 */
public class VerifyNumberPresenter extends BasePresenter<BaseView<Object>> {

    /**
     * @description 银行卡验证手机接口
     * @param
     * @return void
     */
    public void verifyNumber(String fastname,String code){
        Map<String,Object> map = new HashMap<>();
        map.put("fastname",fastname);
        map.put("code",code);

        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IAccountServer.class)
                .verifyNumber(map)
                .compose(SetThread.<BaseEntity<Object>>io_main())
                .subscribe(new BaseConsumer<Object>() {
                    @Override
                    protected void onSuccees(BaseEntity<Object> t) throws Exception {
                        getView().successed(t.getmData());
                    }
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code, error);
                    }
                }, new BaseErroeConsumer() {                    @Override                    protected void onCodeError(int code, String error) throws Exception {                        getView().onLoadFailed(code, error);                    }                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
    }
}
