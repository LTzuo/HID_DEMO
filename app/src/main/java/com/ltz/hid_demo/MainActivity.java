package com.ltz.hid_demo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ltz.hid_demo.widget.CustomEditText;

/**
 * 接收扫码枪扫描结果
 */
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;

    private ScanKeyManager scanKeyManager;

    private TextView text;

    private CustomEditText mCustomEditText;

    private Button btn_qr,btn_clear;

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
                // 扫码
                // intent.putExtra("autoEnlarged", true);
                goScanner();
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

//    private void requestPermission() {
//        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)) {
//                Toast.makeText(MainActivity.this, "二维码扫码需要相机权限", Toast.LENGTH_SHORT).show();
//            } else {
//                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
//            }
//        } else {
//            goScanner();
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goScanner();
                }
                return;
            }
        }
    }

    private void goScanner() {
//        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
//        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
//        if (requestCode == REQUEST_CODE) {
//            //处理扫描结果（在界面上显示）
//            if (null != data) {
//                Bundle bundle = data.getExtras();
//                if (bundle == null) {
//                    return;
//                }
//                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
//                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
//                }
//            }
//        }
    }

}