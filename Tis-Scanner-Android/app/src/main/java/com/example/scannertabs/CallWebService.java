package com.example.scannertabs;

import android.os.AsyncTask;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class CallWebService extends AsyncTask<String, Void, String> {

    private static String NAMESPACE = "tis-brno.cz/labContrWs/";
    private static String METHOD = "doBsTest";
    private static String SOAP = "tis-brno.cz/labContrWs/doBsTest";
    private static String URL = "http://192.168.99.7:8080/labContrWs/LabContrWs?wsdl";
    String[] infoArray;

    CallWebService(String[] infoArray) {
        this.infoArray = infoArray;
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = "";
        SoapObject request = new SoapObject(NAMESPACE, METHOD);
        String n0 = "n0:";

        PropertyInfo propertyInfo = new PropertyInfo();
        propertyInfo.setName(n0 + "idPat");
        propertyInfo.setValue(infoArray[0]);
        propertyInfo.setType(String.class);
        request.addProperty(propertyInfo);

        propertyInfo = new PropertyInfo();
        propertyInfo.setName(n0 + "idReq");
        propertyInfo.setValue(infoArray[1]);
        propertyInfo.setType(int.class);
        request.addProperty(propertyInfo);

        propertyInfo = new PropertyInfo();
        propertyInfo.setName(n0 + "pnum");
        propertyInfo.setValue("=C20191900100110");
        propertyInfo.setType(String.class);

        request.addProperty(propertyInfo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        envelope.setOutputSoapObject(request);

        List<HeaderProperty> hl = new ArrayList<HeaderProperty>();
        hl.add(new HeaderProperty("Content-Type", "text/xml; charset=UTF-8"));

        try {
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug = true;
            androidHttpTransport.setXmlVersionTag("<!--?xml version=\"1.0\" encoding= \"UTF-8\" ?-->");
            androidHttpTransport.call(SOAP, envelope, hl);
            SoapObject Result = (SoapObject)envelope.bodyIn;

            result = Result.getProperty("result").toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }
}
