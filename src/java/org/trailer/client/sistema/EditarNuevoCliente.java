/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.sistema;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;

import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;
import org.trailer.client.util.Conector;
import org.trailer.client.util.Utils;

/**
 *
 * @author buggy
 */
public class EditarNuevoCliente extends Window {

    private final int ANCHO = 500;
    private final int ALTO = 250;
    private final AnchorLayoutData ANCHO_LAYOUT_DATA = new AnchorLayoutData("90%");
    private final int WINDOW_PADDING = 5;
    private FormPanel formPanel;
    private TextField tex_codigoC;
    private TextField tex_nombreC;
    private TextField tex_apellidoC;
    private TextField tex_telefonoC;
    private TextField tex_direccionC;
    private TextField tex_nitC;
    private TextField tex_emailC;
    private TextField tex_faxC;
    private DateField dat_fecha;
    private TextArea tex_observacionC;
    private ComboBox com_ciudadC;
//    private Panel formpanel;
    private Button but_aceptarP;
    private Button but_cancelarP;
    private String idclienteC;
    private String nombreC;
    private String apellidoC;
    private String ciudadC;
    private String codigoC;
    private String telefonoC;
    private String direccionC;
    private String nitC;
    private String emailC;
    private String faxC;
     private String fechaC;
    private String observacionC;
//    private String estadoC;
    private Object[][] ciudadM;
    private boolean nuevo;
    private Cliente padre;

    public EditarNuevoCliente(String idcliente, String nombre, String apellido, String codigo, String ciudad, String telefono, String nit, String fax, String email, String direccion, String observacion,String fecha, Object[][] ciudades, Cliente padre) {
        //com.google.gwt.user.client.Window.alert("Cuantas veces");
        this.idclienteC = idcliente;
        this.codigoC = codigo;
        this.nombreC = nombre;
        this.apellidoC = apellido;
        this.ciudadC = ciudad;
        this.telefonoC = telefono;
        this.nitC = nit;
        this.faxC = fax;
        this.observacionC = observacion;
        this.emailC = email;
        this.direccionC = direccion;
        this.ciudadM = ciudades;
        this.fechaC = fecha;

        this.padre = padre;

        String nombreBoton1 = "Guardar";
        String nombreBoton2 = "Cancelar";
        String tituloTabla = "Registar nuevo Cliente";

        if (idclienteC != null) {
            nombreBoton1 = "Modificar";
            tituloTabla = "Editar Cliente";
            nuevo = false;
        } else {
            this.idclienteC = "nuevo";
            nuevo = true;

        }

        setId("win-Clientes");
        but_aceptarP = new Button(nombreBoton1, new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                if (nuevo == false) {
                    GuardarEditarCliente();
                } else {
                    GuardarNuevoCliente();
                }
            }
        });
        but_cancelarP = new Button(nombreBoton2, new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                EditarNuevoCliente.this.close();
                EditarNuevoCliente.this.setModal(false);
            //formulario = null;
            }
        });

        setTitle(tituloTabla);
        setAutoWidth(true);
        setAutoHeight(true);
        setLayout(new FitLayout());
        setPaddings(WINDOW_PADDING);
        setButtonAlign(Position.CENTER);
        addButton(but_aceptarP);
        addButton(but_cancelarP);

        setCloseAction(Window.CLOSE);
        setPlain(true);
        formPanel = new FormPanel();
        formPanel.setBaseCls("x-plain");


        tex_codigoC = new TextField("Codigo", "codigo", 100);
        tex_codigoC.setMaxLength(6);
        tex_nombreC = new TextField("Nombre", "nombre", 200);
        tex_apellidoC = new TextField("Apellido", "apellido", 200);
        tex_telefonoC = new TextField("Telefono", "telefono", 200);
        tex_direccionC = new TextField("Direccion", "direccion", 200);
        tex_nitC = new TextField("Nit", "nit", 200);
        tex_faxC = new TextField("Fax", "fax", 200);
        tex_emailC = new TextField("Email", "email", 200);
        tex_observacionC = new TextArea("Observacion", "observacion");
        com_ciudadC = new ComboBox("Ciudad", "ciudad");
        dat_fecha = new DateField("Fecha Nac.", "Y-m-d");
        dat_fecha.setName("fecha");
        dat_fecha.setWidth(200);


        formPanel.add(tex_codigoC);
        formPanel.add(tex_nombreC);
        formPanel.add(tex_apellidoC);
        formPanel.add(tex_nitC);
        formPanel.add(tex_telefonoC);
        formPanel.add(tex_faxC);
        formPanel.add(tex_direccionC);
        formPanel.add(tex_emailC);
        formPanel.add(com_ciudadC);
        formPanel.add(dat_fecha);
        formPanel.add(tex_observacionC, ANCHO_LAYOUT_DATA);



//        formPanel.add(com_estadoC);

        add(formPanel);
        initCombos();
        initValues();
    }

    private void initCombos() {

        com_ciudadC.setValueField("idciudad");
        com_ciudadC.setDisplayField("nombre");
        com_ciudadC.setForceSelection(true);
        com_ciudadC.setMinChars(1);
        com_ciudadC.setMode(ComboBox.LOCAL);
        com_ciudadC.setTriggerAction(ComboBox.ALL);
        com_ciudadC.setEmptyText("Seleccione una Ciudad");
        com_ciudadC.setLoadingText("Buscando");
        com_ciudadC.setTypeAhead(true);
        com_ciudadC.setSelectOnFocus(true);
        com_ciudadC.setHideTrigger(false);
        com_ciudadC.setReadOnly(true);
        SimpleStore ciudadStore = new SimpleStore(new String[]{"idciudad", "nombre"}, ciudadM);
        ciudadStore.load();
        com_ciudadC.setStore(ciudadStore);




    }

    private void initValues() {
        tex_codigoC.setValue(codigoC);
        tex_nombreC.setValue(nombreC);
        tex_apellidoC.setValue(apellidoC);
        tex_telefonoC.setValue(telefonoC);
        tex_direccionC.setValue(direccionC);
        tex_nitC.setValue(nitC);
        tex_faxC.setValue(faxC);
        tex_observacionC.setValue(observacionC);
        tex_emailC.setValue(emailC);
        com_ciudadC.setValue(ciudadC);
        dat_fecha.setValue(fechaC);

    }

    public Button getBut_aceptar() {
        return but_aceptarP;
    }

    public Button getBut_cancelar() {
        return but_cancelarP;
    }

    public void GuardarNuevoCliente() {
        String cadena = "php/Cliente.php?funcion=GuardarNuevoCliente";
        cadena =
                cadena + "&" + formPanel.getForm().getValues();
        final Conector conec = new Conector(cadena, false);
        Utils.setErrorPrincipal("Guardando el nuevo Cliente", "guardar");
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

    public void GuardarEditarCliente() {
        String cadena = "php/Cliente.php?funcion=GuardarEditarCliente&idcliente=" + idclienteC;
        cadena = cadena + "&" + formPanel.getForm().getValues();
        final Conector conec = new Conector(cadena, false);
        Utils.setErrorPrincipal("Actualizando los cambios en Cliente", "guardar");
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