package org.feup.apm.terminalnfc;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.RSAPublicKeySpec;

public class MainActivity extends AppCompatActivity {
  private static PublicKey pubKey = null;    // will hold the public key (as long as the app is in memory)
  private static final int keysize = 512;    // the public key will come with 512 bits
  static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
  public final static String[] products = {  // the types of products (the first is type 1)
      "Oranges",
      "Mandarins",
      "Peaches",
      "Pears",
      "Apples",
      "Pineapples",
      "Plums",
      "Grapes"
  };
  private TextView tv;                      // the terminal messages

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    tv = findViewById(R.id.content);
    findViewById(R.id.bt_qr).setOnClickListener(this::scanOrderQR);
  }

  /* The NFC messages are received in their own activities and sent to the MainActivity */
  @Override
  public void onResume() {
    super.onResume();
    int type = getIntent().getIntExtra("type", 0);        // type of NFC message (key(1) or order(2))
    switch (type) {
      case 1:
        showKey(getIntent().getByteArrayExtra("key"));                // get the NFC message (key modulus)
        break;
      case 2:
        showOrder(getIntent().getByteArrayExtra("order"));            // get the NFC message (signed order)
        break;
      default:
        tv.setText(R.string.tv_waiting);
    }
  }

  @Override
  public void onNewIntent(Intent intent) {       // if the intent is delivered to the current instance
    setIntent(intent);
  }

  private void scanOrderQR(View view) {
    try {
      Intent intent = new Intent(ACTION_SCAN);
      intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
      startActivityForResult(intent, 1);
    }
    catch (ActivityNotFoundException anfe) {
      showDialog(this, "No Scanner Found", "Download a scanner QRcode app?", "Yes", "No").show();
    }
  }
  private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
    AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
    downloadDialog.setTitle(title);
    downloadDialog.setMessage(message);
    downloadDialog.setPositiveButton(buttonYes, (di, i) -> {
        Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        act.startActivity(intent);
      });
    downloadDialog.setNegativeButton(buttonNo, null);
    return downloadDialog.show();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    ((TextView)findViewById(R.id.content)).setText(R.string.tv_waiting);
    if (requestCode == 1) {
      if (resultCode == RESULT_OK) {
        String contents = data.getStringExtra("SCAN_RESULT");
        data.putExtra("type", 2);
        data.putExtra("order", contents.getBytes(StandardCharsets.ISO_8859_1));
        setIntent(data);
      }
    }
  }

  void showKey(byte[] modulus) {
    String error = "";
    try {
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");        // to build a key object we need a KeyFactory object
      // the key raw values (as BigIntegers) are used to build an appropriate KeySpec
      RSAPublicKeySpec RSAPub = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger("65537"));
      pubKey = keyFactory.generatePublic(RSAPub);                   // the KeyFactory is used to build the key object from the key spec
    }
    catch (Exception ex) {
      error = ex.getMessage();
    }
    String text = "Public Key:\nModulus (";
    text += modulus.length;
    text += "):\n";
    text += byteArrayToHex(modulus);
    text += "\nExponent: 010001\n\n";
    text += error;
    tv.setText(text);                                               // show the raw values of key components (in hex)
  }

  void showOrder(byte[] order) {
    String error = "";
    boolean validated = false;
    StringBuilder sb = new StringBuilder();
    int nr = order[0];                                              // get the nr of different products (first position)

    for (int k=1; k<=nr; k++) {
      sb.append(" - ");
      sb.append(products[order[k]-1]);                              // get the name of each product from the type
      sb.append("\n");
    }
    if (pubKey == null)
      sb.append("\nMissing pub key+.");
    else {
      byte[] mess = new byte[nr+1];                                // extract the order and the signature from the all message
      byte[] sign = new byte[keysize/8];
      ByteBuffer bb = ByteBuffer.wrap(order);
      bb.get(mess, 0, nr+1);
      bb.get(sign, 0, keysize/8);
      try {
        Signature sg = Signature.getInstance("SHA256WithRSA");      // verify the signature with the public key
        sg.initVerify(pubKey);
        sg.update(mess);
        validated = sg.verify(sign);
      }
      catch (Exception ex) {
        error = "\n" + ex.getMessage();
      }
    }
    sb.append("\nValidated = ");
    sb.append(validated);
    sb.append(error);
    ((TextView)findViewById(R.id.content)).setText(sb.toString());                                    // show order and validation
  }

  String byteArrayToHex(byte[] ba) {                              // converter
    StringBuilder sb = new StringBuilder(ba.length * 2);
    for (byte b : ba)
      sb.append(String.format("%02x", b));
    return sb.toString();
  }
}
