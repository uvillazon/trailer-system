/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.sistema;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

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
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;
import org.trailer.client.util.Conector;
import org.trailer.client.util.Utils;

/**
 *
 * @author buggy
 */
public class EditarEmpleadoForm extends Window {

    private final int ANCHO = 500;
    private final int ALTO = 250;
    private final AnchorLayoutData ANCHO_LAYOUT_DATA = new AnchorLayoutData("90%");
    private final int WINDOW_PADDING = 5;
    private FormPanel formPanel;
    private TextField tex_codigoE;
    private TextField tex_nombreE;
    private TextField tex_apellidoE;
    private TextField tex_telefonoE;
    private TextField tex_celularE;
    private TextField tex_refnombre;
    private TextField tex_direccionE;
    private TextField tex_email;
    private DateField fecha;
//    private Panel formpanel;
    private Button but_aceptarP;
    private Button but_cancelarP;
    private String idEmpleado;
    private String codigoE;
    private String nombreE;
    private String ApellidoE;
    private String fechaIni;
    private String telefonoE;
    private String celularE;
    private String emailE;
    private String direccionE;
    private String ciudadC;
    private ComboBox com_cargoEmpleado;
    private ComboBox com_ciudadEmpleado;
    private String cargoC;
//    private String estadoC;
    private Object[][] cargosM;
    private Object[][] ciudadesM;
    private String[] estadoM;
    private boolean nuevo;
    private Empleado padre;
    private TextField tex_reftelefono;
    private String refNombre;
    private String refTelefono;

    public EditarEmpleadoForm(String idempleado, String codigo, String nombre, String apellido, String telefono, String celular, String direccion, String email, Object[][] cargos, String cargo, Object[][] ciudades, String ciudad, String fechaini, String refn, String reft, Empleado padre) {
        //com.google.gwt.user.client.Window.alert("Cuantas veces");
        this.idEmpleado = idempleado;
        this.codigoE = codigo;
        this.nombreE = nombre;
        this.ApellidoE = apellido;
        this.celularE = celular;
        this.telefonoE = telefono;
        this.cargoC = cargo;
        this.ciudadC = ciudad;
        this.direccionE = direccion;
        this.fechaIni = fechaini;
        this.refNombre = refn;
        this.refTelefono = reft;

        this.emailE = email;

        this.cargosM = cargos;
        this.ciudadesM = ciudades;


        this.padre = padre;

        String nombreBoton1 = "Guardar";
        String nombreBoton2 = "Cancelar";
        String tituloTabla = "Registar nuevo Empleado";

        if (idEmpleado != null) {
            nombreBoton1 = "Modificar";
            tituloTabla = "Editar Empleado";
            nuevo = false;
        } else {
            this.idEmpleado = "nuevo";
            nuevo = true;

        }

        setId("win-Empleados");
        but_aceptarP = new Button(nombreBoton1, new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                if (nuevo == false) {
                    GuardarEditarEmpleado();
                } else {
                    GuardarNuevoEmpleado();
                }
            }
        });
        but_cancelarP = new Button(nombreBoton2, new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                EditarEmpleadoForm.this.close();
                EditarEmpleadoForm.this.setModal(false);
            //formulario = null;
            }
        });

        setTitle(tituloTabla);
        setAutoHeight(true);
        setAutoWidth(true);
        setLayout(new FitLayout());
        setPaddings(WINDOW_PADDING);
        setButtonAlign(Position.CENTER);
        addButton(but_aceptarP);
        addButton(but_cancelarP);

        setCloseAction(Window.CLOSE);
        setPlain(true);
        formPanel = new FormPanel();
        formPanel.setBaseCls("x-plain");

        tex_codigoE = new TextField("Carnet Identidad", "codigo", 100);
        tex_codigoE.setMaxLength(10);
        tex_nombreE = new TextField("Nombres", "nombre", 200);
        tex_apellidoE = new TextField("Apellidos", "apellido", 200);
        tex_telefonoE = new TextField("Telefono", "telefono", 200);
//        tex_telefonoE.
        tex_direccionE = new TextField("Direccion", "direccion", 200);

        tex_email = new TextField("E-mail", "email", 200);
        tex_celularE = new TextField("Celular", "celular", 200);
        tex_refnombre = new TextField("ref: Nombre", "refnombre", 200);
        tex_reftelefono = new TextField("ref: Telefono", "reftelefono", 200);

        com_cargoEmpleado = new ComboBox("Cargo", "cargo");
        com_ciudadEmpleado = new ComboBox("Ciudad", "ciudad");
        fecha = new DateField("FechaInicio", "fechainicio", 100);
        fecha.setFormat("Y-m-d");

        FieldSet costos = new FieldSet("Referencia de un Familiar o Conocido");
        costos.add(tex_refnombre);
        costos.add(tex_reftelefono);
        formPanel.add(tex_codigoE);

        formPanel.add(tex_nombreE);
        formPanel.add(tex_apellidoE);
        formPanel.add(tex_telefonoE);
        formPanel.add(tex_celularE);
        formPanel.add(tex_direccionE);

        formPanel.add(tex_email);

        formPanel.add(com_cargoEmpleado);
        formPanel.add(com_ciudadEmpleado);
        formPanel.add(fecha);
        formPanel.add(costos);
        add(formPanel);
        initCombos();
        initValues();
    }

    private void initCombos() {


        com_ciudadEmpleado.setValueField("idciudad");
        com_ciudadEmpleado.setDisplayField("nombre");
        com_ciudadEmpleado.setForceSelection(true);
        com_ciudadEmpleado.setMinChars(1);
        com_ciudadEmpleado.setMode(ComboBox.LOCAL);
        com_ciudadEmpleado.setTriggerAction(ComboBox.ALL);
        com_ciudadEmpleado.setEmptyText("Seleccione una Ciudad");
        com_ciudadEmpleado.setLoadingText("Buscando");
        com_ciudadEmpleado.setTypeAhead(true);
        com_ciudadEmpleado.setSelectOnFocus(true);
        com_ciudadEmpleado.setHideTrigger(false);
        com_ciudadEmpleado.setReadOnly(true);
        SimpleStore ciudadStore = new SimpleStore(new String[]{"idciudad", "nombre"}, ciudadesM);
        ciudadStore.load();
        com_ciudadEmpleado.setStore(ciudadStore);



        com_cargoEmpleado.setValueField("idcargo");
        com_cargoEmpleado.setDisplayField("nombre");
        com_cargoEmpleado.setForceSelection(true);
        com_cargoEmpleado.setMinChars(1);
        com_cargoEmpleado.setMode(ComboBox.LOCAL);
        com_cargoEmpleado.setTriggerAction(ComboBox.ALL);
        com_cargoEmpleado.setEmptyText("Seleccione un Cargo");
        com_cargoEmpleado.setLoadingText("Buscando");
        com_cargoEmpleado.setTypeAhead(true);
        com_cargoEmpleado.setSelectOnFocus(true);
        com_cargoEmpleado.setHideTrigger(false);
        com_cargoEmpleado.setReadOnly(true);
        SimpleStore ciudadStore1 = new SimpleStore(new String[]{"idcargo", "nombre"}, cargosM);
        ciudadStore1.load();
        com_cargoEmpleado.setStore(ciudadStore1);







    }

    private void initValues() {
        tex_codigoE.setValue(codigoE);
        tex_nombreE.setValue(nombreE);
        tex_apellidoE.setValue(ApellidoE);
        tex_telefonoE.setValue(telefonoE);
        tex_celularE.setValue(celularE);
        tex_direccionE.setValue(direccionE);
        tex_email.setValue(emailE);
        com_cargoEmpleado.setValue(cargoC);
        com_ciudadEmpleado.setValue(ciudadC);
        fecha.setValue(fechaIni);
        tex_refnombre.setValue(refNombre);
        tex_reftelefono.setValue(refTelefono);
    }

    public Button getBut_aceptar() {
        return but_aceptarP;
    }

    public Button getBut_cancelar() {
        return but_cancelarP;
    }

    public void GuardarNuevoEmpleado() {
        String cadena = "php/Empleado.php?funcion=GuardarNuevoEmpleado";
        cadena =
                cadena + "&" + formPanel.getForm().getValues();
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

    public void GuardarEditarEmpleado() {
        String cadena = "php/Empleado.php?funcion=GuardarEditarEmpleado&idempleado=" + idEmpleado;
        cadena = cadena + "&" + formPanel.getForm().getValues();
        final Conector conec = new Conector(cadena, false);
        Utils.setErrorPrincipal("Actualizando...", "guardar");
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