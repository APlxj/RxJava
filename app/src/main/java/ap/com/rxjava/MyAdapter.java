package ap.com.rxjava;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 类描述：
 * 创建人：swallow.li
 * 创建时间：
 * Email: swallow.li@kemai.cn
 * 修改备注：
 */
public abstract class MyAdapter<T> extends BaseAdapter {

    public List<T> mData;
    public Context mContext;

    public MyAdapter(List<T> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void putNewData(List<T> mData) {
        if (null != mData) {
            this.mData = mData;
            notifyDataSetChanged();
        }
    }

    public void putData(List<T> mData) {
        if (null != mData) {
            this.mData.addAll(mData);
            notifyDataSetChanged();
        }
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
