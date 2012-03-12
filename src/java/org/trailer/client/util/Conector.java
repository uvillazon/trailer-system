/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.util;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;

/**
 *
 * @author FOREGROUND
 */
public class Conector {

    private String DEFAULT_SEARCH_URL = "";
    private RequestBuilder requestBuilder;
    private String data = "";
    private boolean callback = false;
    private boolean finalizado = false;
    private boolean bandd = false;
    private int repeat = 1;

    public Conector(String url, boolean callback, String method) {
        String path = Window.Location.getPath();
        path = path.substring(1);
        int position = path.indexOf("/");
        path = path.substring(0, position);
        this.DEFAULT_SEARCH_URL = "/" + path + "/" + url;
        //Window.alert(DEFAULT_SEARCH_URL);
        if (method.equalsIgnoreCase("POST")) {
            requestBuilder = new RequestBuilder(RequestBuilder.POST, DEFAULT_SEARCH_URL);
            requestBuilder.setHeader("Content-type", "application/x-www-form-urlencoded");
        } else {
            requestBuilder = new RequestBuilder(RequestBuilder.GET, DEFAULT_SEARCH_URL);
        }

        this.callback = callback;
    }

    public Conector(String url, boolean callback) {
        String path = Window.Location.getPath();
        path = path.substring(1);
        int position = path.indexOf("/");
        path = path.substring(0, position);
        this.DEFAULT_SEARCH_URL = "/" + path + "/" + url;
        //Window.alert(DEFAULT_SEARCH_URL);

        requestBuilder = new RequestBuilder(RequestBuilder.GET, DEFAULT_SEARCH_URL);

        this.callback = callback;
    }

    private class JSONResponseTextHandler implements RequestCallback {

        public void onError(Request request, Throwable exception) {
            finalizado = false;
            displayRequestError(exception.toString());
        }

        public void onResponseReceived(Request request, Response response) {
            data = response.getText();
            finalizado = true;
        }
    }

    public void doFetchURL() {

        try {
            finalizado = false;
            requestBuilder.sendRequest(null, new JSONResponseTextHandler());
        } catch (RequestException ex) {
            displaySendError(ex.toString());
        }
    }

    private void displayParseError(String responseText) {
        displayError("Failed to parse JSON response", responseText);
    }

    private void displayRequestError(String message) {
        displayError("Request failed.", message);
    }

    private void displaySendError(String message) {
        displayError("Failed to send the request.", message);
    }

    private void displayError(String errorType, String errorMessage) {
    }

    public String quitarCallback(String dato) {
        int i = dato.indexOf("(");
        i = i + 1;
        int largo = dato.length();
        largo = largo - 2;
        dato = dato.substring(0, largo);
        dato = dato.substring(i);
        return dato;
    }

    public void fueCompletado2() {
        boolean dev = false;
        if (callback == true) {
            data = quitarCallback(data);
        } else {
        }
        JSONObject jsonObject;
        try {
            JSONValue jsonValue = JSONParser.parse(data);
            if ((jsonObject = jsonValue.isObject()) != null) {
                JSONValue valorError = jsonObject.get("error");
                String valorErrorS = valorError.toString();
                if (valorErrorS.equalsIgnoreCase("true")) {
                    dev = true;
                } else {
                    dev = false;
                }

            }
        } catch (JSONException e) {
            dev = false;
        }
        bandd = dev;
    }

    private String getMensaje() {
        String dev = "";
        String dato = quitarCallback(data);
        JSONObject jsonObject;
        try {

            JSONValue jsonValue = JSONParser.parse(data);
            if ((jsonObject = jsonValue.isObject()) != null) {
                JSONValue valorError = jsonObject.get("mensaje");
                dev = valorError.toString();

            }
        } catch (JSONException e) {
            dev = data;
        }
        return dev;
    }

    public String formatearResultado(String dato) {
        int i = dato.indexOf("[");
        i = i + 1;
        int largo = dato.length();
        largo = largo - 1;
        dato = dato.substring(0, largo);
        dato = dato.substring(i);
        return dato;
    }

    public boolean isCallback() {
        return callback;
    }

    public void setCallback(boolean callback) {
        this.callback = callback;
    }

    public String getDEFAULT_SEARCH_URL() {
        return DEFAULT_SEARCH_URL;
    }

    public void setDEFAULT_SEARCH_URL(String DEFAULT_SEARCH_URL) {
        this.DEFAULT_SEARCH_URL = DEFAULT_SEARCH_URL;
    }

    public static native void repeatTime() /*-{
    setInterval("@org.selkis.client.util.Conector::testtry()()",1000);
    }-*/;

    public static void testtry() {
        Window.setStatus("holaaaaaaaaaaa");
        Window.setStatus("holaaaaaaabsda");
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public RequestBuilder getRequestBuilder() {
        return requestBuilder;
    }

    public void setRequestBuilder(RequestBuilder requestBuilder) {
        this.requestBuilder = requestBuilder;
    }
}
