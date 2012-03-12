/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.system;

/**
 *
 * @author Administrador
 */
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.HtmlEditor;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;
import org.trailer.client.util.Conector;
import org.trailer.client.util.Utils;

public class ReportarErrorWindow extends Window {

    private final int ANCHO = 500;
    private final int ALTO = 400;
    private final AnchorLayoutData ANCHO_LAYOUT_DATA = new AnchorLayoutData("90%");
    private final int WINDOW_PADDING = 5;
    private FormPanel formPanel;
    private TextField tex_titulo;
    private HtmlEditor html_detalle;
    private Button but_aceptar;

    /**
     * Si id usuario es null, quiere decir que es para crear un nuevo
     */
    public ReportarErrorWindow() {

        this.setId("win-usuario");


        this.setTitle("Reportar errores ocurridos en el sistema.. ");
        this.setWidth(ANCHO);
        this.setMinWidth(ANCHO);
        this.setLayout(new FitLayout());
        this.setPaddings(WINDOW_PADDING);
        this.setButtonAlign(Position.CENTER);
//        this.addButton(but_aceptar);
//        this.addButton(but_cancelar);

        this.setCloseAction(Window.CLOSE);
        this.setPlain(true);

        formPanel = new FormPanel();
        //strips all Ext styling for the component
        formPanel.setBaseCls("x-plain");
        formPanel.setLabelWidth(ANCHO - 400);
        formPanel.setUrl("save-form.php");
        formPanel.setWidth(ANCHO);
        formPanel.setHeight(ALTO);
        formPanel.setLabelAlign(Position.TOP);


        tex_titulo = new TextField("Titulo", "titulo");
        html_detalle = new HtmlEditor("Detalle", "detalle");
        but_aceptar = new Button("Aceptar");
        but_aceptar.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                JSONObject erroresReportar = new JSONObject();
                erroresReportar.put("titulo", new JSONString(tex_titulo.getText()));
                erroresReportar.put("detalle", new JSONString(html_detalle.getValueAsString()));

                String datos = "resultado=" + erroresReportar.toString();
                String enlace = "php/EnviarErrores.php?";
                Utils.setErrorPrincipal("Enviando el reporte de errores", "cargar");
                final Conector conec = new Conector(enlace, false, "POST");
                try {
                    conec.getRequestBuilder().sendRequest(datos, new RequestCallback() {

                        public void onResponseReceived(Request request, Response response) {

                            String data = response.getText();
                            JSONValue jsonValue = JSONParser.parse(data);
                            JSONObject jsonObject;
                            if ((jsonObject = jsonValue.isObject()) != null) {
                                String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                                String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");
                                if (errorR.equalsIgnoreCase("true")) {
                                    //MessageBox.alert(mensajeR);
                                    Utils.setErrorPrincipal(mensajeR, "mensaje");
                                    ReportarErrorWindow.this.close();
                                } else {
                                    //Window.alert(mensajeR);
                                    Utils.setErrorPrincipal(mensajeR, "error");
                                }
                            }
                        }

                        public void onError(Request request, Throwable exception) {
                            //Window.alert("Ocurrio un error al conectar con el servidor ");
                            Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                        }
                    });
                } catch (RequestException ex) {
                    Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                }
            }
        });
        formPanel.add(tex_titulo, ANCHO_LAYOUT_DATA);
        formPanel.add(html_detalle, ANCHO_LAYOUT_DATA);
        formPanel.addButton(but_aceptar);

        add(formPanel);
    }
}