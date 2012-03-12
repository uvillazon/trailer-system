/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.sistema;

import com.gwtext.client.core.Position;
//import com.gwtext.client.data.Record;
import com.gwtext.client.widgets.Window;
//import com.gwtext.client.widgets.Panel;
//import com.gwtext.client.widgets.form.Checkbox;
//import com.gwtext.client.widgets.form.Field;
//import com.gwtext.client.widgets.form.FieldSet;


import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.form.Field;
import org.trailer.client.util.Conector;
import org.trailer.client.util.Utils;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.TextFieldListenerAdapter;
import com.gwtext.client.widgets.layout.FitLayout;

/**
 *
 * @author buggy
 */
public class EditarProcesoForm extends Window {

    private final int ANCHO = 500;
    private final int ALTO = 250;
    private final AnchorLayoutData ANCHO_LAYOUT_DATA = new AnchorLayoutData("90%");
    private final int WINDOW_PADDING = 5;
    private FormPanel formPanel;
    private TextField tex_codigoC;
    private TextField tex_nombreC;
    private TextField tex_formulaC;
    private TextArea tex_observacionC;
    private ComboBox com_empleadoC;
    private ComboBox com_estadoE;
    private ComboBox com_tipoProceso;
    private ComboBox com_item;
//    private Panel formpanel;
    private Button but_aceptarP;
    private Button but_cancelarP;
    private String idprocesoC;
    private String nombreC;
    private String responsableC;
    private String idresponsableC;
    private String codigoC;
    private String tipoC;
    private String estadoC;
    private String item;
    private String observacionC;
    private String formulaC;
//    private String estadoC;
    private Object[][] empleadoM;
    private Object[][] itemsM;
    private String[] tipoempleadoM;
    private String[] estadoM;
    private String[] itemM;
    private boolean nuevo;
    private Proceso padre;

    public EditarProcesoForm(String idproceso, String codigo, String nombre, String responsable, String idresponsable, String observacion, String tipo, Object[][] encargados, Object[][] items, String estado, Proceso padre, String item, String formula) {
        //com.google.gwt.user.client.Window.alert("Cuantas veces");
        this.idprocesoC = idproceso;
        this.codigoC = codigo;
        this.nombreC = nombre;
        this.responsableC = responsable;
        this.itemsM = items;
        this.idresponsableC = idresponsable;
        this.tipoC = tipo;
        this.formulaC = formula;



        this.observacionC = observacion;

        this.estadoC = estado;
        this.empleadoM = encargados;
        this.item = item;
        if (tipoC == "") {

            tipoC = "proceso";
        }
        this.padre = padre;

        String nombreBoton1 = "Guardar";
        String nombreBoton2 = "Cancelar";
        String tituloTabla = "Registar nuevo Proceso";

        if (idprocesoC != null) {
            nombreBoton1 = "Modificar";
            tituloTabla = "Editar Proceso";
            nuevo = false;
        } else {
            this.idprocesoC = "nuevo";
            nuevo = true;

        }

        setId("win-Procesos");
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
                EditarProcesoForm.this.close();
                EditarProcesoForm.this.setModal(false);
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

        tex_observacionC = new TextArea("Observacion", "detalle");
        com_empleadoC = new ComboBox("Responsable", "encargado");
        com_estadoE = new ComboBox("Estado", "estado");
        com_tipoProceso = new ComboBox("Tipo Proceso", "tipo");
        com_item = new ComboBox("Item", "item");
        tex_formulaC = new TextField("Formula", "formula",200);


        if (tipoC == "proceso") {

            com_item.disable();
            tex_formulaC.disable();
        } else {

            com_empleadoC.disable();
        }
        formPanel.add(tex_codigoC);
        formPanel.add(tex_nombreC);
        formPanel.add(com_tipoProceso);
        formPanel.add(com_empleadoC);

        formPanel.add(com_item);
        formPanel.add(com_estadoE);
        formPanel.add(tex_observacionC, ANCHO_LAYOUT_DATA);
        FieldSet categoria = new FieldSet("Escribir La Formula Correcta Si el Item Lo tiene EJM(SASTRE/12) o Solo Un Valor");
        categoria.add(tex_formulaC);
        formPanel.add(categoria);



//        formPanel.add(com_estadoC);

        add(formPanel);
        initCombos();
        initValues();
        aniadirListeners();


    }

    private void initCombos() {

        SimpleStore proveedorStore = new SimpleStore(new String[]{"idempleado", "nombres"}, empleadoM);
        proveedorStore.load();


        com_empleadoC.setMinChars(1);
        com_empleadoC.setFieldLabel("Encargado");
        com_empleadoC.setStore(proveedorStore);
        com_empleadoC.setValueField("idempleado");
        com_empleadoC.setDisplayField("nombres");
        com_empleadoC.setForceSelection(true);
        com_empleadoC.setMode(ComboBox.LOCAL);
        com_empleadoC.setEmptyText("Buscar Encargado");
        com_empleadoC.setLoadingText("buscando...");
        com_empleadoC.setTypeAhead(true);
        com_empleadoC.setSelectOnFocus(true);
        com_empleadoC.setWidth(200);

        com_empleadoC.setHideTrigger(true);

        estadoM = new String[]{"Activo", "Inactivo"};

        SimpleStore estadosStore = new SimpleStore("estados", estadoM);
        estadosStore.load();

        com_estadoE.setDisplayField("estados");
        com_estadoE.setStore(estadosStore);

        tipoempleadoM = new String[]{"items", "proceso"};

        SimpleStore tiposStore = new SimpleStore("tipos", tipoempleadoM);
        tiposStore.load();
        com_tipoProceso.setDisplayField("tipos");
        com_tipoProceso.setStore(tiposStore);

        itemM = new String[]{"Igresos", "Egresos", "Impuestos"};

        SimpleStore tiposStore3 = new SimpleStore(new String[]{"id", "nombre"}, itemsM);
        tiposStore3.load();
        com_item.setMinChars(1);
        com_item.setFieldLabel("Items");
        com_item.setStore(tiposStore3);
        com_item.setValueField("id");
        com_item.setDisplayField("nombre");
        com_item.setForceSelection(true);
        com_item.setMode(ComboBox.LOCAL);
        com_item.setEmptyText("Buscar Items");
        com_item.setLoadingText("buscando...");
        com_item.setTypeAhead(true);
        com_item.setSelectOnFocus(true);
        com_item.setWidth(200);

        com_item.setHideTrigger(true);


    }

    private void initValues() {
        tex_codigoC.setValue(codigoC);
        tex_nombreC.setValue(nombreC);

        tex_observacionC.setValue(observacionC);

        com_empleadoC.setValue(idresponsableC);
        com_estadoE.setValue("Activo");
        com_tipoProceso.setValue(tipoC);
        com_item.setValue(item);
        tex_formulaC.setValue(formulaC);
    }

    public Button getBut_aceptar() {
        return but_aceptarP;
    }

    public Button getBut_cancelar() {
        return but_cancelarP;
    }

    public void GuardarNuevaEmpresa() {
        String cadena = "php/Proceso.php?funcion=GuardarNuevoProceso";
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
        String cadena = "php/Proceso.php?funcion=GuardarEditarProceso&idproceso=" + idprocesoC;
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

    private void aniadirListeners() {



        com_tipoProceso.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
//                    String codigoProducto = field.getValueAsString().trim();
                    String tipo1 = com_tipoProceso.getValueAsString().trim();
                    if (tipo1 == "items") {
                        com_empleadoC.clearValue();
                        com_empleadoC.disable();
                        com_item.enable();
                        tex_formulaC.enable();
                    } else {

                        com_empleadoC.enable();
                        com_item.clearValue();
                        com_item.disable();
                        tex_formulaC.setValue("");
                        tex_formulaC.disable();
                    }


                }
            }
        });
    }
}