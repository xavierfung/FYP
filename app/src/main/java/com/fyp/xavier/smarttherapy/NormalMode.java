package com.fyp.xavier.smarttherapy;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.xavier.smarttherapy.helper.BTConnetion;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Xavier on 17/1/2016.
 */
public class NormalMode extends Activity {

    // counter + animation + bluetooth + game

    //BT + SPP UUID
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //private final int[] difficulties_timeout = new int[]{5000, 1000, 500};
    private static final Random random = new Random();
    private static final String TAG_SCORE = "score";
    final int handlerState = 0;                        //used to identify handler message
    String address = null;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    Handler bluetoothIn;
    Double sensorr0, sensorr1, sensorr2, sensorr3;
    int score = 0;
    CountDownTimer timer = null;
    Finger currentfinger = null;
    int currentMillis = 0;
    private StringBuilder recDataString = new StringBuilder();
    private ConnectedThread mConnectedThread;
    private Button btnstart, btnstop;
    private TextView twcounter, sensorvalue1, sensorvalue2, sensorvalue3, sensorvalue4, txtString, txtStringLength, twinstruction;
    private TextView test;
    // private ImageView FingerImage;
    private WebView Gifview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    /**/

        super.onCreate(savedInstanceState);

        setContentView(R.layout.normal_mode);
        btnstart = (Button) findViewById(R.id.button);
        btnstop = (Button) findViewById(R.id.button2);
        twcounter = (TextView) findViewById(R.id.textView1);
        twcounter.setText("100");
        txtString = (TextView) findViewById(R.id.txtString);
        txtStringLength = (TextView) findViewById(R.id.txtStringLength);
        sensorvalue1 = (TextView) findViewById(R.id.fsr_value1);
        sensorvalue2 = (TextView) findViewById(R.id.fsr_value2);
        sensorvalue3 = (TextView) findViewById(R.id.fsr_value3);
        sensorvalue4 = (TextView) findViewById(R.id.fsr_value4);
        twinstruction = (TextView) findViewById(R.id.twinstruction);
        test = (TextView) findViewById(R.id.tvtest);
        Gifview = (WebView) findViewById(R.id.web_view);


        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {                                        //if message is what we want
                    String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
                    recDataString.append(readMessage);                                    //keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("~");                    // determine the end-of-line
                    if (endOfLineIndex > 0) {                                           // make sure there data before ~
                        String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
                        txtString.setText("Data Received = " + dataInPrint);
                        int dataLength = dataInPrint.length();                            //get length of data received
                        txtStringLength.setText("String Length = " + String.valueOf(dataLength));

                        if (recDataString.charAt(0) == '#')                                //if it starts with # we know it is what we are looking for
                        {
                            String sensor0 = recDataString.substring(1, 5);             //get sensor value from string between indices 1-5
                            String sensor1 = recDataString.substring(6, 10);            //same again...
                            String sensor2 = recDataString.substring(11, 15);
                            String sensor3 = recDataString.substring(16, 20);

                            sensorvalue1.setText(sensor0);    //update the textviews with sensor values
                            sensorvalue2.setText(sensor1);
                            sensorvalue3.setText(sensor2);
                            sensorvalue4.setText(sensor3);

                            sensorr0 = Double.parseDouble(sensor0);
                            sensorr1 = Double.parseDouble(sensor1);
                            sensorr2 = Double.parseDouble(sensor2);
                            sensorr3 = Double.parseDouble(sensor3);

                            {
                                if (currentfinger == Finger.One) {

                                    if (sensorr0 < 2.5) {
                                        score++;
                                        drawAnFinger();
                                    }
                                    if (sensorr1 < 2.5) {
                                        wrongFinger();
                                    }
                                    if (sensorr2 < 2.5) {
                                        wrongFinger();
                                    }
                                    if (sensorr3 < 2.5) {
                                        wrongFinger();
                                    }
                                }
                                if (currentfinger == Finger.Two) {

                                    if (sensorr0 < 2.5) {
                                        wrongFinger();
                                    }
                                    if (sensorr1 < 2.5) {
                                        score++;
                                        drawAnFinger();
                                    }
                                    if (sensorr2 < 2.5) {
                                        wrongFinger();
                                    }
                                    if (sensorr3 < 2.5) {
                                        wrongFinger();
                                    }
                                }

                                if (currentfinger == Finger.Three) {

                                    if (sensorr0 < 2.5) {
                                        wrongFinger();
                                    }
                                    if (sensorr1 < 2.5) {
                                        wrongFinger();
                                    }
                                    if (sensorr2 < 2.5) {
                                        score++;
                                        drawAnFinger();
                                    }
                                    if (sensorr3 < 2.5) {
                                        wrongFinger();
                                    }
                                }
                                if (currentfinger == Finger.Four) {

                                    if (sensorr0 < 2.5) {
                                        wrongFinger();
                                    }
                                    if (sensorr1 < 2.5) {
                                        wrongFinger();
                                    }
                                    if (sensorr2 < 2.5) {
                                        wrongFinger();
                                    }
                                    if (sensorr3 < 2.5) {
                                        score++;
                                        drawAnFinger();
                                    }
                                }
                            }

                        }
                        recDataString.delete(0, recDataString.length());                    //clear all string data
                        // strIncom =" ";
                        dataInPrint = " ";
                    }
                }
            }
        };

        myBluetooth = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();

        // Start btn
        {
            btnstart.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    configureTimer();
                    drawAnFinger();
                    timer.start();
                    btnstart.setClickable(false);
                }
            });

            btnstop.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    gameover();
                }
            });
        }
    }


    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return device.createRfcommSocketToServiceRecord(myUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    @Override
    public void onResume() {
        super.onResume();

        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();

        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(BTConnetion.EXTRA_ADDRESS);

        //create device and set the MAC address
        BluetoothDevice device = myBluetooth.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        // Establish the Bluetooth socket connection.
        try {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                //insert code to deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("x");
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }

    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {

        if (myBluetooth == null) {
            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (myBluetooth.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    protected void configureTimer() {
        //set time

        timer = new CountDownTimer(100000, 10) {

            public void onTick(long millisUntilFinished) {
                currentMillis = (int) millisUntilFinished;
                twcounter.setText(String.format("%.0f", (float) millisUntilFinished / 1000));
            }

            public void onFinish() {
                gameover();
            }
        };
    }

    private void drawAnFinger() {
        // Draw a random finger, write the hint, restart the count down
        currentfinger = Finger.values()[random.nextInt(Finger.values().length)];
        twinstruction.setText("Please Press Finger " + currentfinger.name());

        {
            if (currentfinger == Finger.One) {
                Gifview.loadUrl("file:///android_asset/finger_1.GIF");
                Gifview.getSettings().setLoadWithOverviewMode(true);
                Gifview.getSettings().setUseWideViewPort(true);
            }
            if (currentfinger == Finger.Two) {
                Gifview.loadUrl("file:///android_asset/finger_2.GIF");
                Gifview.getSettings().setLoadWithOverviewMode(true);
                Gifview.getSettings().setUseWideViewPort(true);
            }

            if (currentfinger == Finger.Three) {
                Gifview.loadUrl("file:///android_asset/finger_3.GIF");
                Gifview.getSettings().setLoadWithOverviewMode(true);
                Gifview.getSettings().setUseWideViewPort(true);
            }
            if (currentfinger == Finger.Four) {
                Gifview.loadUrl("file:///android_asset/finger_4.GIF");
                Gifview.getSettings().setLoadWithOverviewMode(true);
                Gifview.getSettings().setUseWideViewPort(true);
            }
        }


    }

    private void gameover() {
        // Stop the count down, show gameover
        timer.cancel();
        new AlertDialog.Builder(this)
                .setTitle("Gameover")
                .setMessage(String.format("Score: %d\nUpload Data", score))
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent intent = new Intent(getApplicationContext(), Add_Record_Test.class);
                                startActivity(intent);
                            }
                        })
                .setNegativeButton(android.R.string.no,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                finish();
                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();

        Intent in = new Intent(getApplicationContext(),
                Add_Record_Test.class);
        // sending score to next activity
        in.putExtra(TAG_SCORE, score);

    }

    private void wrongFinger() {
        test.setText("Wrong, Please try again");
    }


    //game
    enum Finger {
        One, Two, Three, Four
    }

    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }


        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                finish();

            }
        }
    }

}
