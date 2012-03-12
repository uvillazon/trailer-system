/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.sistema;

//import org.trailer.client.configuracion.*;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;
import org.trailer.client.util.Conector;
import org.trailer.client.util.Utils;

/**
 *
 * @author buggy
 */
public class EditarUnidadForm extends Window {

    private final AnchorLayoutData ANCHO_LAYOUT_DATA = new AnchorLayoutData("90%");
    private final int WINDOW_PADDING = 5;
    private FormPanel formPanel;
    private TextField tex_codigoUnidad;
    private TextField tex_nombreUnidad;
    //private TextArea tex_descripcionUnidad;
   // private TextField tex_codigoBarraUnidad;
    private Button but_aceptarUnidad;
    private Button but_cancelarUnidad;
    private String codigo;
    private String idunidad;
    private String nombreUnidad;
    private boolean nuevo;
    private Unidad padre;

    public EditarUnidadForm(String idcargo, String codigo, String nombre, Unidad padre) {
        //com.google.gwt.user.client.Window.alert("Cuantas veces");
        this.idunidad = idcargo;
        this.codigo = codigo;
        this.nombreUnidad = nombre;

        this.padre = padre;

        String nombreBoton1 = "Guardar";
        String nombreBoton2 = "Cancelar";
        String tituloTabla = "Registar Nueva Unidad";

        if (idcargo != null) {
            nombreBoton1 = "Modificar";
            tituloTabla = "Editar Unidad";
            nuevo = false;
        } else {
            this.idunidad = "nuevo";
            nuevo = true;

        }

        setId("win-unidad");
        but_aceptarUnidad = new Button(nombreBoton1, new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                if (nuevo == false) {
                    GuardarEditarUnidad();
                } else {
                    GuardarNuevoUnidad();
                }
            }
        });
        but_cancelarUnidad = new Button(nombreBoton2, new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                EditarUnidadForm.this.close();
                EditarUnidadForm.this.setModal(false);
            //formulario = null;
            }
        });

        setTitle(tituloTabla);
        setAutoHeight(true);
        setAutoWidth(true);
        setLayout(new FitLayout());
        setPaddings(WINDOW_PADDING);
        setButtonAlign(Position.CENTER);
        addButton(but_aceptarUnidad);
        addButton(but_cancelarUnidad);

        setCloseAction(Window.CLOSE);
        setPlain(true);
        DecoratorPanel decPanel = new DecoratorPanel();
        decPanel.setTitle("Registro De Unidades");

        formPanel = new FormPanel();




        tex_codigoUnidad = new TextField("Codigo", "codigo", 100);
        tex_codigoUnidad.setMaxLength(6);
        tex_nombreUnidad = new TextField("Nombre", "nombre", 200);
        //tex_descripcionUnidad = new TextArea("Detalle", "detalle");



        formPanel.add(tex_codigoUnidad);
        formPanel.add(tex_nombreUnidad);
       // formPanel.add(tex_descripcionUnidad);

        decPanel.add(formPanel);

        add(decPanel);
        initValues();
    }

    private void initValues() {
        tex_codigoUnidad.setValue(codigo);
        tex_nombreUnidad.setValue(nombreUnidad);


    }

    public Button getBut_aceptar() {
        return but_aceptarUnidad;
    }

    public Button getBut_cancelar() {
        return but_cancelarUnidad;
    }

    public void GuardarNuevoUnidad() {
        String cadena = "php/Unidad.php?funcion=GuardarNuevaUnidad&idunidad=" + idunidad;
        cadena = cadena + "&" + formPanel.getForm().getValues();
        final Conector conec = new Conector(cadena, false);
        Utils.setErrorPrincipal("Guardando...", "guardar");
        try {
            conec.getRequestBuilder().sendRequest(null, new RequestCallback() {

                public void onResponseReceived(Request request, Response response) {
                    String data = response.getText();
                    JSONValue jsonValue = JSONParser.parse(data);
                    JSONObject jsonObject;
                    if ((jsonObject = jsonValue.isObject()) != null) {
                        String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                        String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");
                        if (errorR.equalsIgnoreCase("true")) {
                            Utils.setErrorPrincipal(mensajeR, "mensaje");
                            close();
                            padre.reload();
                        } else {
                            Utils.setErrorPrincipal(mensajeR, "error");
                        }
                    }

                }

                public void onError(Request request, Throwable exception) {
                    Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                }
            });

        } catch (RequestException ex) {
            ex.getMessage();
            Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
        }
    }

    public void GuardarEditarUnidad() {
        String cadena = "php/Unidad.php?funcion=GuardarEditarUnidad&idunidad=" + idunidad;
        cadena = cadena + "&" + formPanel.getForm().getValues();
        final Conector conec = new Conector(cadena, false);
        Utils.setErrorPrincipal("Actualizando....", "guardar");
        try {
            conec.getRequestBuilder().sendRequest(null, new RequestCallback() {

                public void onResponseReceived(Request request, Response response) {
                    String data = response.getText();
                    JSONValue jsonValue = JSONParser.parse(data);
                    JSONObject jsonObject;
                    if ((jsonObject = jsonValue.isObject()) != null) {
                        String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                        String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");
                        if (errorR.equalsIgnoreCase("true")) {
                            Utils.setErrorPrincipal(mensajeR, "mensaje");
                            close();
                            padre.reload();
                        } else {
                            Utils.setErrorPrincipal(mensajeR, "error");
                        }
                    }
                }

                public void onError(Request request, Throwable exception) {
                    Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                }
            });

        } catch (RequestException ex) {
            ex.getMessage();
            Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
        }
    }
}
