package com.example.xc_voyager.easylife;

import android.content.Context;
import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.route.DrivingRouteLine;

import java.lang.reflect.Type;
import java.util.List;

public class RouteLineAdapter extends BaseAdapter
{
    private List<? extends RouteLine> routeLines;
    private LayoutInflater layoutInflater;
    private Type mType;

    public RouteLineAdapter(Context context, List<? extends RouteLine> routeLines, Type type)
    {
        this.routeLines = routeLines;
        layoutInflater = LayoutInflater.from(context);
        this.mType = type;
    }

    @Override
    public int getCount()
    {
        return routeLines.size();
    }

    @Override
    public Object getItem(int pos)
    {
        return pos;
    }

    @Override
    public long getItemId(int pos)
    {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent)
    {
        NodeViewHolder holder;
        if (convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.activity_travel_transit_item, null);
            holder = new NodeViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.transitName);
            holder.lightNum = (TextView) convertView.findViewById(R.id.lightNum);
            holder.dis = (TextView) convertView.findViewById(R.id.dis);
            convertView.setTag(holder);
        }
        else
            holder = (NodeViewHolder) convertView.getTag();
        switch (mType)
        {
            case DRIVING_ROUTE:
                DrivingRouteLine drivingRouteLine = (DrivingRouteLine) routeLines.get(pos);
                holder.name.setText("路线" + (pos + 1));
                holder.lightNum.setText("红绿灯数：" + drivingRouteLine.getLightNum());
                if (drivingRouteLine.getDistance() < 10000)
                    holder.dis.setText("距离：" + drivingRouteLine.getDistance() + "米");
                else
                    holder.dis.setText("距离：" + drivingRouteLine.getDistance() / 1000 + "公里");
                break;
            case TRANSIT_ROUTE:
                holder.name.setText("路线" + (pos + 1));
                int time = routeLines.get(pos).getDuration();
                if (time <= 3600)
                    holder.lightNum.setText(time / 60 + "分钟");
                else
                    holder.lightNum.setText(time / 3600 + "小时" + (time % 3600) / 60 + "分钟");
                if (routeLines.get(pos).getDistance() < 10000)
                    holder.dis.setText("距离：" + routeLines.get(pos).getDistance() + "米");
                else
                    holder.dis.setText("距离：" + routeLines.get(pos).getDistance() / 1000 + "公里");
                break;
        }
        return convertView;
    }

    private class NodeViewHolder
    {

        private TextView name;
        private TextView lightNum;
        private TextView dis;
    }

    public enum Type
    {
        TRANSIT_ROUTE, // 公交
        DRIVING_ROUTE // 驾车
    }
}
