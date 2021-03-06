package com.ltz.hid_demo.socket;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ltz.hid_demo.R;
import com.ltz.hid_demo.utils.ToastUtil;

/**
 * 客户端
 */
public class ClientActivity extends AppCompatActivity {

    private SocketClient client;
    private TextView txt;
    private EditText edit;
    private Button btn;
    private EditText edit_server;
    private Button server_OK;
    private EditText edit_ip;
    private LinearLayout mLinearLayout;
    private LinearLayout mLinearLayout_client;

    private int port=9999;
    private String ip="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        txt=(TextView)findViewById ( R.id.textView );
        edit=(EditText)findViewById ( R.id.edit );
        btn=(Button)findViewById ( R.id.btn );

        edit_server=(EditText)findViewById ( R.id.editText_server );
        edit_ip=(EditText)findViewById ( R.id.client_ip );
        server_OK=(Button)findViewById ( R.id.server_OK );
        mLinearLayout=(LinearLayout)findViewById ( R.id.lin_1 ) ;
        mLinearLayout_client=(LinearLayout)findViewById ( R.id.lin_ip ) ;
        client=new SocketClient ();
        server_OK.setOnClickListener ( new View.OnClickListener ( )
        {
            @Override
            public void onClick(View v)
            {
                mLinearLayout.setVisibility ( View.GONE );
                mLinearLayout_client.setVisibility ( View.GONE );
                try {
                    port= Integer.parseInt(edit_server.getText ().toString ());
                    ip=edit_ip.getText ().toString ();
                    //服务端的IP地址和端口号
                    client.clintValue (ip ,port);
                    //开启客户端接收消息线程
                    client.openClientThread ();
                }catch (Exception e){
                    ToastUtil.ShortToast("请检查ip及地址");
                    mLinearLayout.setVisibility ( View.VISIBLE );
                    mLinearLayout_client.setVisibility ( View.VISIBLE );
                    e.printStackTrace ();
                }

            }
        } );


        /**
         * 发送消息
         * */
        btn.setOnClickListener ( new View.OnClickListener ( )
        {
            @Override
            public void onClick(View v)
            {
                client.sendMsg ( edit.getText ().toString () );

            }
        } );
        /**
         *  接受消息
         *
         **/

        SocketClient.mHandler=new Handler(  ){
            @Override
            public void handleMessage(Message msg)
            {
                txt.setText ( msg.obj.toString ());
                Log.i ( "LOG",msg.obj.toString ());
            }
        };


    }


}
