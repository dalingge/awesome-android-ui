package com.dalingge.awesome.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dalingge.awesome.R;
import com.dalingge.awesome.adapter.DiffAdapter;
import com.dalingge.awesome.bean.TestBean;
import com.dalingge.awesome.callback.DiffCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DiffActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<TestBean> mDatas;
    private DiffAdapter mAdapter;
    @Override
    protected int getLayout() {
        return R.layout.activity_diff;
    }

    @Override
    protected boolean isBack() {
        return true;
    }

    @Override
    protected void initView() {
        initData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mAdapter = new DiffAdapter(this, mDatas);
        recyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add(new TestBean("张旭童1", "Android", R.drawable.download));
        mDatas.add(new TestBean("张旭童2", "Java", R.drawable.chrome));
        mDatas.add(new TestBean("张旭童3", "背锅", R.drawable.truck));
        mDatas.add(new TestBean("张旭童4", "手撕产品", R.drawable.wallet));
        mDatas.add(new TestBean("张旭童5", "手撕测试", R.drawable.chalk));
    }

    /**
     * 模拟刷新操作
     *
     * @param view
     */
    @OnClick(R.id.fab)
    public void onRefresh(View view) {
        try {
            mNewDatas = new ArrayList<>();
            for (TestBean bean : mDatas) {
                mNewDatas.add(bean.clone());//clone一遍旧数据 ，模拟刷新操作
            }
            mNewDatas.add(new TestBean("赵子龙", "帅", R.drawable.wallet));//模拟新增数据
            mNewDatas.get(0).setDesc("Android+");
            mNewDatas.get(0).setPic(R.drawable.truck);//模拟修改数据
            TestBean testBean = mNewDatas.get(1);//模拟数据位移
            mNewDatas.remove(testBean);
            mNewDatas.add(testBean);

            //新宠
            //利用DiffUtil.calculateDiff()方法，传入一个规则DiffUtil.Callback对象，和是否检测移动item的 boolean变量，得到DiffUtil.DiffResult 的对象
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //放在子线程中计算DiffResult
                    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(mDatas, mNewDatas), true);
                    Message message = mHandler.obtainMessage(H_CODE_UPDATE);
                    message.obj = diffResult;//obj存放DiffResult
                    message.sendToTarget();
                }
            }).start();
            //mAdapter.notifyDataSetChanged();//以前普通青年的我们只能这样，现在我们是文艺青年了，有新宠了

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private static final int H_CODE_UPDATE = 1;
    private List<TestBean> mNewDatas;//增加一个变量暂存newList
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case H_CODE_UPDATE:
                    //取出Result
                    DiffUtil.DiffResult diffResult = (DiffUtil.DiffResult) msg.obj;
                    //利用DiffUtil.DiffResult对象的dispatchUpdatesTo（）方法，传入RecyclerView的Adapter，轻松成为文艺青年
                    diffResult.dispatchUpdatesTo(mAdapter);
                    //别忘了将新数据给Adapter
                    mDatas = mNewDatas;
                    mAdapter.setDatas(mDatas);
                    break;
            }
        }
    };

}
