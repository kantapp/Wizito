package com.kantapp.wizito;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kantapp.wizito.Model.ClientModel;

import java.util.List;

/**
 * Created by Android A on 4/20/2018.
 */

public class ClientListAdapter extends BaseAdapter
{
    private List<ClientModel> clientModel;
    public ClientListAdapter(List<ClientModel> clientModel)
    {
        this.clientModel = clientModel;
    }

    @Override
    public int getCount() {
        return clientModel.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_client_layout,null);
        TextView clientname=convertView.findViewById(R.id.clientName);
        clientname.setText(clientModel.get(position).getName());

        return convertView;
    }
}
