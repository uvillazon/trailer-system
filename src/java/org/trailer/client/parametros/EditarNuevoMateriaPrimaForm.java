/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.parametros;


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
import com.gwtext.client.widgets.form.TextArea;
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
public class EditarNuevoMateriaPrimaForm extends Window {

    private final int ANCHO = 500;
    private final int ALTO = 250;
    private final AnchorLayoutData ANCHO_LAYOUT_DATA = new AnchorLayoutData("90%");
    private final int WINDOW_PADDING = 5;
    private FormPanel formPanel;
    private TextField tex_codigoC;
    private TextField tex_nombreC;
//    private TextField tex_apellidoC;
//    private TextField tex_direccionC;
    private TextField tex_calidadC;
    private TextField tex_stockC;
    private TextArea tex_caracteristicaC;
    private ComboBox com_unidadC;
    private ComboBox com_colorC;
    private ComboBox com_categoriaC;
    private ComboBox com_estadoC;
//    private Panel formpanel;
    private Button but_aceptarP;
    private Button but_cancelarP;
    private String idmateriaprimaC;
    private String nombreC;
//    private String apellidoC;
    private String categoriaC;
    private String codigoC;
    private String unidadC;
    private String estadoC;
//    private String direccionC;
    private String colorC;
//    private String emailC;
    private String stockC;
    private String calidadC;
    private String caracteristicaC;
//    private String estadoC;
    private Object[][] categoriaM;
    private Object[][] colorM;
    private Object[][] unidadM;
    private String[] estadoM;
    private boolean nuevo;
    private MateriaPrima padre;

    public EditarNuevoMateriaPrimaForm(String idmateriaprima, String nombre, String codigo, String categoria,String calidad, String unidad, String color,String stock, String caracteristica,String estado,  Object[][] colores,  Object[][] unidades,  Object[][] categorias, MateriaPrima padre) {
        //com.google.gwt.user.client.Window.alert("Cuantas veces");
        this.idmateriaprimaC = idmateriaprima;
        this.codigoC = codigo;
        this.nombreC = nombre;
        this.categoriaC = categoria;
        this.unidadC = unidad;
        this.colorC = color;
        this.stockC=stock;
        this.calidadC=calidad;
        this.caracteristicaC=caracteristica;
        this.categoriaM = categorias;
        this.colorM=colores;
        this.unidadM=unidades;
        if (estado == null) {
            this.estadoC = "Activo";

        } else {
            this.estadoC = estado;
        }

        this.estadoM = new String[]{"Activo", "Inactivo"};
        this.padre = padre;

        String nombreBoton1 = "Guardar";
        String nombreBoton2 = "Cancelar";
        String tituloTabla = "Registar nueva Materia Prima";

        if (idmateriaprimaC != null) {
            nombreBoton1 = "Modificar";
            tituloTabla = "Editar Materia Prima";
            nuevo = false;
        } else {
            this.idmateriaprimaC = "nuevo";
            nuevo = true;

        }

        setId("win-MateriaPrima");
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
                EditarNuevoMateriaPrimaForm.this.close();
                EditarNuevoMateriaPrimaForm.this.setModal(false);
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


        tex_codigoC = new TextField("Codigo", "codigo",100);
        tex_codigoC.setMaxLength(6);
        tex_nombreC = new TextField("Nombre", "nombre",200);
        com_unidadC = new ComboBox("Unidad", "unidad",200);
        com_colorC = new ComboBox("Color", "color");
        tex_stockC = new TextField("Stock Minimo", "stockminimo",200);
        tex_calidadC = new TextField("Calidad", "calidad",200);
        tex_caracteristicaC= new TextArea("Caracteristica","caracteristica");
        com_categoriaC = new ComboBox("Categoria", "categoria");
        com_estadoC = new ComboBox("Estado", "estado");


//        formPanel.add(tex_codigoC);
        formPanel.add(tex_nombreC);
        formPanel.add(com_categoriaC);
        formPanel.add(tex_calidadC);
        formPanel.add(com_unidadC);
        formPanel.add(com_colorC);
        formPanel.add(tex_stockC);        
        formPanel.add(tex_caracteristicaC,ANCHO_LAYOUT_DATA);
        formPanel.add(com_estadoC);
        
        
        
//        formPanel.add(com_estadoC);

        add(formPanel);
        initCombos();
        initValues();
    }

    private void initCombos() {

        com_categoriaC.setValueField("idcategoria");
        com_categoriaC.setDisplayField("nombre");
        com_categoriaC.setForceSelection(true);
        com_categoriaC.setMinChars(1);
        com_categoriaC.setMode(ComboBox.LOCAL);
        com_categoriaC.setTriggerAction(ComboBox.ALL);
        com_categoriaC.setEmptyText("Seleccione una Categoria");
        com_categoriaC.setLoadingText("Buscando");
        com_categoriaC.setTypeAhead(true);
        com_categoriaC.setSelectOnFocus(true);
        com_categoriaC.setHideTrigger(true);
        com_categoriaC.setReadOnly(false);
        SimpleStore categoriaStore = new SimpleStore(new String[]{"idcategoria", "nombre"}, categoriaM);
        categoriaStore.load();
        com_categoriaC.setStore(categoriaStore);

        com_colorC.setValueField("idcolor");
        com_colorC.setDisplayField("nombre");
        com_colorC.setForceSelection(true);
        com_colorC.setMinChars(1);
        com_colorC.setMode(ComboBox.LOCAL);
        com_colorC.setTriggerAction(ComboBox.ALL);
        com_colorC.setEmptyText("Seleccione un Color");
        com_colorC.setLoadingText("Buscando");
        com_colorC.setTypeAhead(true);
        com_colorC.setSelectOnFocus(true);
        com_colorC.setHideTrigger(true);
        com_colorC.setReadOnly(false);
        SimpleStore colorStore = new SimpleStore(new String[]{"idcolor", "nombre"}, colorM);
        colorStore.load();
        com_colorC.setStore(colorStore);


        com_unidadC.setValueField("idunidad");
        com_unidadC.setDisplayField("nombre");
        com_unidadC.setForceSelection(true);
        com_unidadC.setMinChars(1);
        com_unidadC.setMode(ComboBox.LOCAL);
        com_unidadC.setTriggerAction(ComboBox.ALL);
        com_unidadC.setEmptyText("Seleccione una Unidad");
        com_unidadC.setLoadingText("Buscando");
        com_unidadC.setTypeAhead(true);
        com_unidadC.setSelectOnFocus(true);
        com_unidadC.setHideTrigger(true);
        com_unidadC.setReadOnly(false);
        SimpleStore unidadStore = new SimpleStore(new String[]{"idunidad", "nombre"}, unidadM);
        unidadStore.load();
        com_unidadC.setStore(unidadStore);

        SimpleStore estadosStore = new SimpleStore("estados", estadoM);
        estadosStore.load();
        com_estadoC.setDisplayField("estados");
        com_estadoC.setStore(estadosStore);



    }

    private void initValues() {
//            tex_codigoC.setValue(codigoC);
        tex_nombreC.setValue(nombreC);
        com_unidadC.setValue(unidadC);
        com_colorC.setValue(colorC);
        tex_stockC.setValue(stockC);
        tex_calidadC.setValue(calidadC);
        tex_caracteristicaC.setValue(caracteristicaC);
        com_categoriaC.setValue(categoriaC);
        com_estadoC.setValue(estadoC);
    }

    public Button getBut_aceptar() {
        return but_aceptarP;
    }

    public Button getBut_cancelar() {
        return but_cancelarP;
    }

    public void GuardarNuevoCliente() {
        String cadena = "php/MateriaPrima.php?funcion=GuardarNuevaMateriaPrima";
        cadena = cadena + "&" + formPanel.getForm().getValues();
        final Conector conec = new Conector(cadena, false);
        Utils.setErrorPrincipal("Guardando la nueva Materia Prima", "guardar");
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
        String cadena = "php/MateriaPrima.php?funcion=GuardarEditarMateriaPrima&idmateriaprima=" + idmateriaprimaC;
        cadena = cadena + "&" + formPanel.getForm().getValues();
        final Conector conec = new Conector(cadena, false);
        Utils.setErrorPrincipal("Actualizando los cambios en Materia Prima", "guardar");
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