package cmov.goncalobo.trainticketsystem.Others;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.View;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import com.onbarcode.barcode.android.AndroidColor;
import com.onbarcode.barcode.android.IBarcode;
import com.onbarcode.barcode.android.QRCode;

import cmov.goncalobo.trainticketsystem.Entities.Ticket;

public class QRCodeGenerator extends View {

    private Ticket ticket;

    public QRCodeGenerator(Context context, Ticket ticket) {
        super(context);
        this.ticket = ticket;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        try {
            testQRCode(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testQRCode(Canvas canvas) throws Exception
    {
        QRCode barcode = new QRCode();

      /*
         QRCode Valid data char set:
              numeric data (digits 0 - 9);
              alphanumeric data (digits 0 - 9; upper case letters A -Z; nine other characters: space, $ % * + - . / : );
              byte data (default: ISO/IEC 8859-1);
              Kanji characters
      */
        // some data
        // for example: bizcard format
        // barcode.setData("BIZCARD:N:Kelly;X:Goto;T:Design Ethnographer;C:gotomedia LLC;A:2169 Folsom Street M302;B:4158647007;F:4158647004;M:4159907005;E:kelly@gotomedia.com;;");

        byte[] keyBytes = new byte[16];

        for(int i = 0;i<keyBytes.length;i++)
            keyBytes[i] = ticket.getID().getBytes()[i];

        String key = generateKey(keyBytes);
        String qrString = ticket.display(4)+"\n\n" + key;

        Utils._(qrString, getContext());
        Utils._("" + qrString.length(), getContext());

                // a small QRcode with 68 characters
        barcode.setData(qrString);
        barcode.setDataMode(QRCode.M_AUTO);
        barcode.setVersion(1);
        barcode.setEcl(QRCode.ECL_L);

        //  if you want to encode GS1 compatible QR Code, you need set FNC1 mode to IBarcode.FNC1_ENABLE
        barcode.setFnc1Mode(IBarcode.FNC1_NONE);

        //  Set the processTilde property to true, if you want use the tilde character "~" to specify special characters in the input data. Default is false.
        //  1-byte character: ~ddd (character value from 0 ~ 255)
        //  ASCII (with EXT): from ~000 to ~255
        //  2-byte character: ~6ddddd (character value from 0 ~ 65535)
        //  Unicode: from ~600000 to ~665535
        //  ECI: from ~7000000 to ~7999999
        //  SJIS: from ~9ddddd (Shift JIS 0x8140 ~ 0x9FFC and 0xE040 ~ 0xEBBF)
        barcode.setProcessTilde(false);

        // unit of measure for X, Y, LeftMargin, RightMargin, TopMargin, BottomMargin
        barcode.setUom(IBarcode.UOM_PIXEL);
        // barcode module width in pixel
        barcode.setX(9f);

        barcode.setLeftMargin(30f);
        barcode.setRightMargin(30f);
        barcode.setTopMargin(50f);
        barcode.setBottomMargin(50f);
        // barcode image resolution in dpi
        barcode.setResolution(72);

        // barcode bar color and background color in Android device
        barcode.setForeColor(AndroidColor.black);
        barcode.setBackColor(AndroidColor.white);
      /*
      specify your barcode drawing area
    */
        RectF bounds = new RectF(0, 0, 0, 0);
        barcode.drawBarcode(canvas, bounds);
    }

    public String generateKey(byte[] i){

        byte[] input = i, output;
        String res = "", key = "";

        try {
            KeyPairGenerator kgen = KeyPairGenerator.getInstance("RSA");  //RSA keys
            kgen.initialize(368);                                         //size in bits
            KeyPair kp = kgen.generateKeyPair();
            PrivateKey pri = kp.getPrivate();                             // private key in a Java class
            PublicKey pub = kp.getPublic();                               // the corresponding public key in a Java class
            res += ("Private: (" + pri.toString() +")\n");
            res += ("Public: (" + pub.toString() + ")\n");
            Signature sg = Signature.getInstance("SHA1WithRSA");          // for signing with the stated algorithm
            sg.initSign(pri);                                             // supply the private key
            sg.update(input);                                             // define the data to sign
            output = sg.sign();                                           // produce the signature
            res += ("Signature: (" + output.length + "bytes)\n");
            key = output.toString();
            sg.initVerify(pub);                                          // supply the public key
            sg.update(input);                                            // supply the data to verify
            boolean verify = sg.verify(output);                          // verify the signature (output) using the original data
            res += ("\nverify: " + verify +"\n");

            input[10] = 100;                                             // change a byte
            res += "\nChanged data.\n";
            sg.update(input);
            verify = sg.verify(output);                                  // verify the signature (output) using the changed data
            res += ("verify: " + verify +"\n");
        }
        catch (Exception ex) {
            res += ex.toString();
        }
        return key;
    }

}
