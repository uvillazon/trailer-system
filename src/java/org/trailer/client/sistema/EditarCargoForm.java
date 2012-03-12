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
public class EditarCargoForm extends Window {

    private final AnchorLayoutData ANCHO_LAYOUT_DATA = new AnchorLayoutData("90%");
    private final int WINDOW_PADDING = 5;
    private FormPanel formPanel;
    private TextField tex_codigoCargo;
    private TextField tex_nombreColor;
    private TextArea tex_descripcionColor;
    private TextField tex_codigoBarraColor;
    private Button but_aceptarColor;
    private Button but_cancelarColor;
    private String codigo;
    private String idcargo;
    private String nombreCargo;
    private boolean nuevo;
    private Cargo padre;

    public EditarCargoForm(String idcargo, String codigo, String nombre, Cargo padre) {
        //com.google.gwt.user.client.Window.alert("Cuantas veces");
        this.idcargo = idcargo;
        this.codigo = codigo;
        this.nombreCargo = nombre;

        this.padre = padre;

        String nombreBoton1 = "Guardar";
        String nombreBoton2 = "Cancelar";
        String tituloTabla = "Registar Nuevo Cargo";

        if (idcargo != null) {
            nombreBoton1 = "Modificar";
            tituloTabla = "Editar Cargo";
            nuevo = false;
        } else {
            this.idcargo = "nuevo";
            nuevo = true;

        }

        setId("win-cargos");
        but_aceptarColor = new Button(nombreBoton1, new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                if (nuevo == false) {
                    GuardarEditarColor();
                } else {
                    GuardarNuevoColor();
                }
            }
        });
        but_cancelarColor = new Button(nombreBoton2, new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                EditarCargoForm.this.close();
                EditarCargoForm.this.setModal(false);
            //formulario = null;
            }
        });

        setTitle(tituloTabla);
        setAutoHeight(true);
        setAutoWidth(true);
        setLayout(new FitLayout());
        setPaddings(WINDOW_PADDING);
        setButtonAlign(Position.CENTER);
        addButton(but_aceptarColor);
        addButton(but_cancelarColor);

        setCloseAction(Window.CLOSE);
        setPlain(true);
        DecoratorPanel decPanel = new DecoratorPanel();
        decPanel.setTitle("Registro De Cargos");

        formPanel = new FormPanel();




        tex_codigoCargo = new TextField("Codigo", "codigo", 100);
        tex_codigoCargo.setMaxLength(6);
        tex_nombreColor = new TextField("Nombre", "nombre", 200);




        formPanel.add(tex_codigoCargo);
        formPanel.add(tex_nombreColor);

        decPanel.add(formPanel);

        add(decPanel);
        initValues();
    }

    private void initValues() {
        tex_codigoCargo.setValue(codigo);
        tex_nombreColor.setValue(nombreCargo);


    }

    public Button getBut_aceptar() {
        return but_aceptarColor;
    }

    public Button getBut_cancelar() {
        return but_cancelarColor;
    }

    public void GuardarNuevoColor() {
        String cadena = "php/Cargo.php?funcion=GuardarNuevoCargo&idcargo=" + idcargo;
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

    public void GuardarEditarColor() {
        String cadena = "php/Cargo.php?funcion=GuardarEditarCargo&idcargo=" + idcargo;
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
