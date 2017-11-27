package ap.com.rxjava;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
    }

    private NewAdapter adapter;
    private List<String> strings = new ArrayList<>();

    private void initView() {
        ListView mListView = (ListView) findViewById(R.id.listview);
        adapter = new NewAdapter(strings, this);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (strings.get(position)) {
                    case "base":
                        showActivity(BaseActivity.class);
                        break;
                    case "deep":
                        showActivity(DeepActivity.class);
                        break;
                }
            }
        });
    }

    private void showActivity(Class<?> aClass) {
        startActivity(new Intent(MainActivity.this, aClass));
    }

    private void init() {
        String[] strs = getResources().getStringArray(R.array.tv);
        for (String str : strs) {
            strings.add(str);
        }
        adapter.putNewData(strings);
    }

    class NewAdapter extends MyAdapter<String> {

        public NewAdapter(List mData, Context mContext) {
            super(mData, mContext);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_new, null);
            TextView tv = (TextView) convertView.findViewById(R.id.tv);
            tv.setText(position + 1 + "、" + mData.get(position));
            return convertView;
        }
    }
}
