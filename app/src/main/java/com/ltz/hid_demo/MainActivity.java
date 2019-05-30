package com.ltz.hid_demo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity2;
import com.ltz.hid_demo.socket.SocketDemoActivity;
import com.ltz.hid_demo.widget.CustomEditText;

/**
 * 接收扫码枪扫描结果
 */
public class MainActivity extends AppCompatActivity {


    private static final int SCAN_REQUEST_CODE = 100;
    private static final int CAMERA_PERMISSION = 110;

    private ScanKeyManager scanKeyManager;

    private TextView text;

    private CustomEditText mCustomEditText;

    private Button btn_qr,btn_clear,btn_socket;

    private StringBuffer buffer = new StringBuffer();
    private StringBuffer buffer1 = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.text);
        mCustomEditText = (CustomEditText) findViewById(R.id.mCustomEditText);

        //拦截扫码器回调,获取扫码内容
        scanKeyManager = new ScanKeyManager(new ScanKeyManager.OnScanValueListener() {
            @Override
            public void onScanValue(String value) {
                buffer.append("-->" + value);
                Log.i("LOG", value);
              //  text.setText(buffer.toString());
                mCustomEditText.setText(buffer.toString());
            }
        });



//        mCustomEditText.setScanCodeListener(new CustomEditText.ScanCodeListener() {
//            @Override
//            public void handleCode(String code) {
//                buffer1.append("-->" + code);
//                mCustomEditText.setText(buffer1.toString());
//            }
//        });

        btn_qr = (Button) findViewById(R.id.btn_qr);
        btn_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT > 22) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSION);
                    } else {
                        startScanActivity();
                    }
                } else {
                    startScanActivity();
                }
            }
        });

        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buffer.setLength(0);
                buffer1.setLength(0);
              //  text.setText(buffer.toString());
                mCustomEditText.setText(buffer1.toString());
            }
        });


        btn_socket = (Button) findViewById(R.id.btn_socket);
        btn_socket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(MainActivity.this,SocketDemoActivity.class));
            }
        });
    }

    /*监听键盘事件,除了返回事件都将它拦截,
      使用我们自定义的拦截器处理该事件*/
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() != KeyEvent.KEYCODE_BACK) {
//            buffer.append("-->" + event.getDisplayLabel());
//            text.setText(buffer.toString());
            scanKeyManager.analysisKeyEvent(event);
//            mCustomEditText.dispatchKeyEvent(event);
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private void startScanActivity() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity2.class);
        intent.putExtra(CaptureActivity2.USE_DEFUALT_ISBN_ACTIVITY, true);
        startActivityForResult(intent, SCAN_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startScanActivity();
                } else {
                    Toast.makeText(MainActivity.this, "请手动打开摄像头权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public int checkSelfPermission(String permission) {
        return super.checkSelfPermission(permission);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SCAN_REQUEST_CODE) {
                //Todo Handle the isbn number entered manually
                String isbn = data.getStringExtra("CaptureIsbn");
                if (!TextUtils.isEmpty(isbn)) {
                    //todo something
                  //  Toast.makeText(this, "解析到的内容为" + isbn, Toast.LENGTH_LONG).show();
                    mCustomEditText.setText("主扫解析到的内容为~"+isbn);
                    if (isbn.contains("http")) {
//                        Intent intent = new Intent(this, WebViewActivity.class);
//                        intent.putExtra(WebViewActivity.RESULT, isbn);
//                        startActivity(intent);
                    }
                }
            }
        }
    }

}