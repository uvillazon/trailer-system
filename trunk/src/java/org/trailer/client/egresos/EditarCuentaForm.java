/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.egresos;


import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Position;
//import com.gwtext.client.data.Record;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Window;
//import com.gwtext.client.widgets.Panel;
//import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
//import com.gwtext.client.widgets.form.Field;
//import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextField;
//import com.gwtext.client.widgets.layout.AnchorLayoutData;
//import com.gwtext.client.widgets.layout.FitLayout;
//import com.gwtext.client.widgets.layout.HorizontalLayout;
//import com.gwtext.client.widgets.layout.VerticalLayout;
//import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;
import org.trailer.client.util.Conector;
import org.trailer.client.util.Utils;

/**
 *
 * @author buggy
 */
public class EditarCuentaForm extends Window {

    private final int ANCHO = 500;
    private final int ALTO = 250;
    private final AnchorLayoutData ANCHO_LAYOUT_DATA = new AnchorLayoutData("90%");
    private final int WINDOW_PADDING = 5;
    private FormPanel formPanel;
    
    private TextField tex_nombreC;

   
    private ComboBox com_estadoE;
   
//    private Panel formpanel;
    private Button but_aceptarP;
    private Button but_cancelarP;
    private String idcuenta;
    private String nombre;
    private String responsableC;
    private String idresponsableC;
    
    private String tipo;


    private String observacionC;
//    private String estadoC;
    private Object[][] empleadoM;
    private String[] tipoempleadoM;
    private Object[][] cuentasM;
    private String[] estadoM;
    private boolean nuevo;
    private Cuenta padre;

    public EditarCuentaForm(String idcuenta,  String nombre, String tipo,Object[][] cuentaM, Cuenta padre) {
        //com.google.gwt.user.client.Window.alert("Cuantas veces");
        this.idcuenta = idcuenta;
        this.nombre = nombre;
        this.cuentasM = cuentaM;
        this.tipo = tipo;

       this.padre = padre;

        String nombreBoton1 = "Guardar";
        String nombreBoton2 = "Cancelar";
        String tituloTabla = "Registar nueva Cuenta";

        if (idcuenta != null) {
            nombreBoton1 = "Modificar";
            tituloTabla = "Editar Cuenta";
            nuevo = false;
        } else {
            this.idcuenta = "nuevo";
            nuevo = true;

        }

        setId("win-Cuentas");
        but_aceptarP = new Button(nombreBoton1, new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                if (nuevo == false) {
                    GuardarEditarEmpresa();
                } else {
                    GuardarNuevaEmpresa();
                }
            }
        });
        but_cancelarP = new Button(nombreBoton2, new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                EditarCuentaForm.this.close();
                EditarCuentaForm.this.setModal(false);
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


//
        tex_nombreC = new TextField("Nombre", "nombre",200);


        com_estadoE = new ComboBox("Cuentas Egreso", "cuenta");

        formPanel.add(tex_nombreC);

        formPanel.add(com_estadoE);

        add(formPanel);
        initCombos();
        initValues();
    }

    private void initCombos() {


//        estadoM = new String[]{"Diario","Mensual","Anual"};
//
//        SimpleStore estadosStore = new SimpleStore("tipo", estadoM);
//        estadosStore.load();
//        com_estadoE.setDisplayField("tipo");
//        com_estadoE.setStore(estadosStore);
//
         SimpleStore cuentaStore = new SimpleStore(new String[]{"id", "nombre"}, cuentasM);
        cuentaStore.load();


        com_estadoE.setMinChars(1);
        com_estadoE.setFieldLabel("cuenta");
        com_estadoE.setStore(cuentaStore);
        com_estadoE.setValueField("id");
        com_estadoE.setDisplayField("nombre");
        com_estadoE.setForceSelection(true);
        com_estadoE.setMode(ComboBox.LOCAL);
        com_estadoE.setEmptyText("Buscar Cuenta");
        com_estadoE.setLoadingText("buscando...");
        com_estadoE.setTypeAhead(true);
        com_estadoE.setSelectOnFocus(true);
        com_estadoE.setWidth(200);

        com_estadoE.setHideTrigger(true);



    }

    private void initValues() {
//        tex_codigoC.setValue(codigoC);
        tex_nombreC.setValue(nombre);

//        tex_observacionC.setValue(observacionC);
//
//        com_empleadoC.setValue(idresponsableC);
        com_estadoE.setValue(tipo);
//        com_tipoProceso.setValue(tipoC);
    }

    public Button getBut_aceptar() {
        return but_aceptarP;
    }

    public Button getBut_cancelar() {
        return but_cancelarP;
    }

    public void GuardarNuevaEmpresa() {
        String cadena = "php/Cuentas.php?funcion=GuardarNuevaCuenta";
        cadena =
                cadena + "&" + formPanel.getForm().getValues();
        final Conector conec = new Conector(cadena, false);
        Utils.setErrorPrincipal("Guardando el nuevo Proceso", "guardar");
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

    public void GuardarEditarEmpresa() {
        String cadena = "php/Cuentas.php?funcion=GuardarEditarCuenta&idcuenta=" + idcuenta;
        cadena = cadena + "&" + formPanel.getForm().getValues();
        final Conector conec = new Conector(cadena, false);
        Utils.setErrorPrincipal("Actualizando los cambios en Proceso", "guardar");
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