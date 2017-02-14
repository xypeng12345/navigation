package com.xyp.meyki_bear.navigationtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 项目名称：NavigationTest
 * 类描述：
 * 创建人：meyki-bear
 * 创建时间：2017/2/13 14:43
 * 修改人：meyki-bear
 * 修改时间：2017/2/13 14:43
 * 修改备注：
 */

public class MyFramgent extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_1,null);
        Bundle arguments = getArguments();
        String number = arguments.getString("number");
        TextView tv= (TextView) view.findViewById(R.id.tv1);
        tv.setText(number);
        return view;
    }
}
