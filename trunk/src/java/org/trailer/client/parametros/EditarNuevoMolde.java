/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.parametros;

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

import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.NumberField;
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
public class EditarNuevoMolde extends Window {

//    private final int ANCHO = 500;
//    private final int ALTO = 250;
    private final AnchorLayoutData ANCHO_LAYOUT_DATA = new AnchorLayoutData("98%");
    private final int WINDOW_PADDING = 10;
    private FormPanel formPanel;
    private TextField tex_codigo;
    private TextField tex_nombre;
    private TextField tex_imagen;
    private TextField tex_hilo;
    private TextField tex_sastre;
    private TextArea tex_observacion;
    private ComboBox com_categoria;
//    private Panel formpanel;
    private Button but_aceptarP;
    private Button but_cancelarP;
    private String idmolde;
    private String categoria;
    private String imagen;
    private Float  sastre;
    private Float hilo;
//    private String ciudadC;
    private String codigo;
    private String nombre;
    private String observacionC;
    private String idcategoria;
//    private Object[][] categoriasM;
    private Object[][] categoriaM;
    private boolean nuevo;
    private Molde padre;

    public EditarNuevoMolde(String idmolde, String imagen, String codigo, String nombre, String observacion, String idcategoria, String categoria, Object[][] categorias, Molde padre, Float hilo, Float  sastre) {
        //com.google.gwt.user.client.Window.alert(idgrupo);
        this.idmolde = idmolde;
        this.codigo = codigo;
        this.categoria = categoria;
        this.imagen = imagen;

        this.nombre = nombre;
        this.sastre = sastre;
        this.hilo = hilo;

        this.observacionC = observacion;
        this.categoriaM = categorias;
        this.idcategoria = idcategoria;

        this.padre = padre;

        String nombreBoton1 = "Guardar";
        String nombreBoton2 = "Cancelar";
        String tituloTabla = "Registar nuevo Molde";

        if (idmolde != null) {
            nombreBoton1 = "Modificar";
            tituloTabla = "Editar Molde";
            nuevo = false;
        } else {
            this.idmolde = "nuevo";
            nuevo = true;

        }

        setId("win-Moldes");
        but_aceptarP = new Button(nombreBoton1, new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                if (nuevo == false) {
                    GuardarEditarMolde();
                } else {
                    GuardarNuevoMolde();
                }
            }
        });
        but_cancelarP = new Button(nombreBoton2, new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                EditarNuevoMolde.this.close();
                EditarNuevoMolde.this.setModal(false);
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


        tex_codigo = new TextField("# numero", "codigo", 160);
        tex_nombre = new TextField("Nombre Molde", "nombre", 160);

        tex_imagen = new TextField("URL Imagen", "imagen", 160);
        tex_observacion = new TextArea("Observacion", "observacion");
        com_categoria = new ComboBox("Categoria", "categoria", 160);
        FieldSet costos = new FieldSet("Costos de Sastre y Hilo");
        tex_hilo = new TextField("Hilo", "hilo", 160);
        tex_sastre = new TextField("Sastre", "sastre", 160);
        costos.add(tex_sastre);
        costos.add(tex_hilo);
        //        categoria.add(tex_formulaC);



        formPanel.add(tex_codigo);

        formPanel.add(com_categoria);
        formPanel.add(tex_nombre);
        formPanel.add(tex_observacion, ANCHO_LAYOUT_DATA);
        formPanel.add(costos);
        add(formPanel);
        initCombos();
        initValues();
    }

    private void initCombos() {

        SimpleStore empresaStore = new SimpleStore(new String[]{"idcategoria", "nombre"}, categoriaM);
        empresaStore.load();
        com_categoria.setValueField("idcategoria");
        com_categoria.setDisplayField("nombre");
        com_categoria.setForceSelection(true);
        com_categoria.setMinChars(1);
        com_categoria.setMode(ComboBox.LOCAL);
        com_categoria.setTriggerAction(ComboBox.ALL);
        com_categoria.setEmptyText("Seleccione una Cageroia");
        com_categoria.setLoadingText("Buscando");
        com_categoria.setTypeAhead(true);
        com_categoria.setSelectOnFocus(true);
        com_categoria.setHideTrigger(true);
        com_categoria.setStore(empresaStore);

    }

    private void initValues() {
        tex_codigo.setValue(codigo);
        tex_imagen.setValue(imagen);

        tex_nombre.setValue(nombre);
        tex_observacion.setValue(observacionC);
        com_categoria.setValue(idcategoria);

        tex_sastre.setValue(sastre.toString());
        tex_hilo.setValue(hilo.toString());

    }

    public Button getBut_aceptar() {
        return but_aceptarP;
    }

    public Button getBut_cancelar() {
        return but_cancelarP;
    }

    public void GuardarNuevoMolde() {
        String cadena = "php/Molde.php?funcion=GuardarNuevoMolde";
        cadena =
                cadena + "&" + formPanel.getForm().getValues();
        final Conector conec = new Conector(cadena, false);
        Utils.setErrorPrincipal("Guardando....", "guardar");
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

    public void GuardarEditarMolde() {
        String cadena = "php/Molde.php?funcion=GuardarEditarMolde&idmolde=" + idmolde;
        cadena = cadena + "&" + formPanel.getForm().getValues();
        final Conector conec = new Conector(cadena, false);
        Utils.setErrorPrincipal("Actualizando los cambios...", "guardar");
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