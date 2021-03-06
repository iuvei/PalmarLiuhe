package com.liuheonline.la.mvp.presenter;

import com.liuheonline.la.entity.QueryEntity;
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
 * @date 2018/7/13 16:14
 * @description
 */
public class QueryPresenter extends BasePresenter<BaseView<QueryEntity>> {

    /**
     * @description 获取支付二维码接口
     * @param
     * @return void
     */
    public void pays(String out_order_id,String sign){
        Map<String,Object> map = new HashMap<>();
        map.put("out_order_id",out_order_id);
        map.put("sign",sign);

        getView().onLoading();
        mSubscription = RetrofitFactoryUtil.getRetrofit(LiuHeApplication.getQuickline())
                .getApiService(IUserServer.class)
                .getquery(map)
                .compose(SetThread.io_main())
                .subscribe(new BaseConsumer<QueryEntity>() {
                    @Override
                    protected void onSuccees(BaseEntity<QueryEntity> t) throws Exception {
                        getView().successed(t.getmData());
                    }
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code, error);
                    }
                }, new BaseErroeConsumer() {
                    @Override
                    protected void onCodeError(int code, String error) throws Exception {
                        getView().onLoadFailed(code, error);
                    }
                }, () -> {

                });
    }
}
