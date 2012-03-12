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
public class EditarNuevoLogo extends Window {

//    private final int ANCHO = 500;
//    private final int ALTO = 250;
    private final AnchorLayoutData ANCHO_LAYOUT_DATA = new AnchorLayoutData("98%");
    private final int WINDOW_PADDING = 10;
    private FormPanel formPanel;
    private TextField tex_canalC;
    private NumberField tex_ancho;
    private TextField tex_diseño;
    private TextField tex_puntada;
    private TextField tex_imagen;
    private NumberField tex_alto;
//    private TextField tex_puntadaC;
//    private TextField tex_faxC;
    private TextArea tex_observacion;
    private ComboBox com_empresa;
//    private Panel formpanel;
    private Button but_aceptarP;
    private Button but_cancelarP;
    private String idproducto;
    private String empresa;
    private String imagen;
//    private String ciudadC;
    private String canal;
    private String diseño;
//    private String descripcion;
    private String puntada;
    private double altoC;
    private double anchoC;
    private String observacionC;
//    private String estadoC;
    private String idempresa;
//    private Object[][] categoriasM;
    private Object[][] empresasM;
    private boolean nuevo;
    private Logos padre;
    private String kardex;
    private TextField tex_kardex;

    public EditarNuevoLogo(String idproducto, String imagen, String canal, String kardex,String diseño, String puntada, double ancho, double alto, String observacion,String idempresa, String empresa,Object[][] empresas, Logos padre) {
        //com.google.gwt.user.client.Window.alert(idgrupo);
        this.idproducto = idproducto;
        this.canal = canal;
        this.empresa = empresa;
        this.imagen = imagen;
        this.kardex = kardex;
        this.diseño = diseño;
        this.puntada = puntada;
        this.anchoC = ancho;
        this.altoC = alto;
        this.observacionC = observacion;
        this.empresasM=empresas;
        this.idempresa=idempresa;

        this.padre = padre;

        String nombreBoton1 = "Guardar";
        String nombreBoton2 = "Cancelar";
        String tituloTabla = "Registar nuevo Bordado";

        if (idproducto != null) {
            nombreBoton1 = "Modificar";
            tituloTabla = "Editar Bordado";
            nuevo = false;
        } else {
            this.idproducto = "nuevo";
            nuevo = true;

        }

        setId("win-logos");
        but_aceptarP = new Button(nombreBoton1, new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                if (nuevo == false) {
                    GuardarEditarLogo();
                } else {
                    GuardarNuevoLogo();
                }
            }
        });
        but_cancelarP = new Button(nombreBoton2, new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                EditarNuevoLogo.this.close();
                EditarNuevoLogo.this.setModal(false);
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


        tex_canalC = new TextField("Canal", "canal", 160);
        tex_ancho = new NumberField("Ancho", "ancho", 160);
        tex_alto=new NumberField("Alto", "alto",160);
        tex_diseño = new TextField("Diseño", "nombre", 160);
        tex_puntada = new TextField("Puntada", "puntadas", 160);
        tex_imagen = new TextField("URL Imagen", "imagen", 160);
        tex_observacion = new TextArea("Observacion", "observacion");
        com_empresa = new ComboBox("Empresa", "idempresa",160);
        tex_kardex = new TextField("Kardex","kardex",100);

        formPanel.add(tex_canalC);
        formPanel.add(tex_kardex);
        formPanel.add(tex_diseño);
        formPanel.add(tex_imagen);
        formPanel.add(com_empresa);
        formPanel.add(tex_puntada);
        formPanel.add(tex_alto);
        formPanel.add(tex_ancho);
        formPanel.add(tex_observacion,ANCHO_LAYOUT_DATA);

        add(formPanel);
        initCombos();
        initValues();
    }

    private void initCombos() {

        SimpleStore empresaStore = new SimpleStore(new String[]{"idempresa", "nombre"}, empresasM);
        empresaStore.load();
        com_empresa.setValueField("idempresa");
        com_empresa.setDisplayField("nombre");
        com_empresa.setForceSelection(true);
        com_empresa.setMinChars(1);
        com_empresa.setMode(ComboBox.LOCAL);
        com_empresa.setTriggerAction(ComboBox.ALL);
        com_empresa.setEmptyText("Seleccione una Empresa");
        com_empresa.setLoadingText("Buscando");
        com_empresa.setTypeAhead(true);
        com_empresa.setSelectOnFocus(true);
        com_empresa.setHideTrigger(true);
        com_empresa.setStore(empresaStore);

    }

    private void initValues() {
        tex_canalC.setValue(canal);
        tex_imagen.setValue(imagen);
        tex_diseño.setValue(diseño);
        tex_puntada.setValue(puntada);
        tex_alto.setValue(altoC);
        tex_ancho.setValue(anchoC);
        tex_observacion.setValue(observacionC);
        com_empresa.setValue(idempresa);
        tex_kardex.setValue(kardex);

    }

    public Button getBut_aceptar() {
        return but_aceptarP;
    }

    public Button getBut_cancelar() {
        return but_cancelarP;
    }

    public void GuardarNuevoLogo() {
        String cadena = "php/Producto.php?funcion=GuardarNuevoLogo";
        cadena =
                cadena + "&" + formPanel.getForm().getValues();
        final Conector conec = new Conector(cadena, false);
        Utils.setErrorPrincipal("Guardando el nuevo logo", "guardar");
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

    public void GuardarEditarLogo() {
        String cadena = "php/Producto.php?funcion=GuardarEditarLogo&idproducto=" + idproducto;
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