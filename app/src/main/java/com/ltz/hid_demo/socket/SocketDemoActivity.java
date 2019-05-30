package com.ltz.hid_demo.socket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ltz.hid_demo.R;
import com.ltz.hid_demo.utils.ToastUtil;

/**
 * socketDemo
 */
public class SocketDemoActivity extends AppCompatActivity {

    private Button open_server,open_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_demo);

        open_server = findViewById(R.id.open_server);
        open_client = findViewById(R.id.open_client);

        open_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SocketDemoActivity.this,ServerActivity.class));
            }
        });

        open_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SocketDemoActivity.this,ClientActivity.class));
            }
        });
    }
}
