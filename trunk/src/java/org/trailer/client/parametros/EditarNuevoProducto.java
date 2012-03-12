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
public class EditarNuevoProducto extends Window {

    private final int ANCHO = 500;
    private final int ALTO = 250;
    private final AnchorLayoutData ANCHO_LAYOUT_DATA = new AnchorLayoutData("90%");
    private final int WINDOW_PADDING = 5;
    private FormPanel formPanel;
    private TextField tex_codigoC;
    private TextField tex_nombreC;
    private TextField tex_medidas;
    private TextField tex_tela;
    private TextField tex_imagen;
    private TextField tex_nitC;
    private TextField tex_emailC;
    private TextField tex_faxC;
    private TextArea tex_descripcion;
    private ComboBox com_categoria;
//    private Panel formpanel;
    private Button but_aceptarP;
    private Button but_cancelarP;
    private String idproducto;
    private String nombre;
    private String imagen;
    private String ciudadC;
    private String codigo;
    private String medidas;
    private String descripcion;
    private String tela;
    private String idcategoria;
    private String idgrupo;
    private String observacionC;
//    private String estadoC;
    private Object[][] categoriasM;
    private Object[][] gruposM;
    private boolean nuevo;
    private Producto padre;
    private ComboBox com_grupos;

    public EditarNuevoProducto(String idproducto, String nombre, String imagen, String codigo, String medidas, String tela, String idgrupo, String idcategoria, String descripcion, Object[][] grupos, Object[][] categorias, Producto padre) {
        //com.google.gwt.user.client.Window.alert(idgrupo);
        this.idproducto = idproducto;
        this.codigo = codigo;
        this.nombre = nombre;
        this.imagen = imagen;

        this.medidas = medidas;
        this.tela = tela;
        this.idgrupo = "gru-1";
        this.idcategoria = idcategoria;
        this.descripcion = descripcion;
        this.categoriasM = categorias;
        this.gruposM = grupos;

        this.padre = padre;

        String nombreBoton1 = "Guardar";
        String nombreBoton2 = "Cancelar";
        String tituloTabla = "Registar nuevo Producto";

        if (idproducto != null) {
            nombreBoton1 = "Modificar";
            tituloTabla = "Editar Producto";
            nuevo = false;
        } else {
            this.idproducto = "nuevo";
            nuevo = true;

        }

        setId("win-productos");
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
                EditarNuevoProducto.this.close();
                EditarNuevoProducto.this.setModal(false);
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
        tex_medidas = new TextField("Medidas", "medidas", 200);
        tex_tela = new TextField("Tela", "tela", 200);
        tex_imagen = new TextField("URL Imagen", "imagen", 200);
        tex_descripcion = new TextArea("Descripcion", "descripcion");
        tex_descripcion.setHeight(50);
        tex_descripcion.setWidth(200);
        com_categoria = new ComboBox("Categoria", "categoria");
        com_grupos = new ComboBox("Grupo", "grupo");


        formPanel.add(tex_codigoC);
        formPanel.add(tex_nombreC);
        formPanel.add(tex_medidas);

        formPanel.add(tex_tela);

//        formPanel.add(tex_imagen);
//        formPanel.add(com_grupos);
        formPanel.add(com_categoria);
        formPanel.add(tex_descripcion);



//        formPanel.add(com_estadoC);

        add(formPanel);
        initCombos();
        initValues();
    }

    private void initCombos() {

        com_categoria.setValueField("idcategoriaproducto");
        com_categoria.setDisplayField("nombre");
        com_categoria.setForceSelection(true);
        com_categoria.setMinChars(1);
        com_categoria.setMode(ComboBox.LOCAL);
        com_categoria.setTriggerAction(ComboBox.ALL);
        com_categoria.setEmptyText("Seleccione una categoria");
        com_categoria.setLoadingText("Buscando");
        com_categoria.setTypeAhead(true);
        com_categoria.setSelectOnFocus(true);
        com_categoria.setHideTrigger(false);
        com_categoria.setReadOnly(true);
        SimpleStore categoriaStore = new SimpleStore(new String[]{"idcategoriaproducto", "nombre"}, categoriasM);
        categoriaStore.load();
        com_categoria.setStore(categoriaStore);


        com_grupos.setValueField("idgrupo");
        com_grupos.setDisplayField("nombre");
        com_grupos.setForceSelection(true);
        com_grupos.setMinChars(1);
        com_grupos.setMode(ComboBox.LOCAL);
        com_grupos.setTriggerAction(ComboBox.ALL);
        com_grupos.setEmptyText("Seleccione un grupo");
        com_grupos.setLoadingText("Buscando");
        com_grupos.setTypeAhead(true);
        com_grupos.setSelectOnFocus(true);
        com_grupos.setHideTrigger(false);
        com_grupos.setReadOnly(true);
        SimpleStore grupoStore = new SimpleStore(new String[]{"idgrupo", "nombre"}, gruposM);
        grupoStore.load();
        com_grupos.setStore(grupoStore);




    }

    private void initValues() {
        tex_codigoC.setValue(codigo);
        tex_nombreC.setValue(nombre);
        tex_imagen.setValue(imagen);
        tex_medidas.setValue(medidas);
        tex_descripcion.setValue(descripcion);
        tex_tela.setValue(tela);
        com_grupos.setValue(idgrupo);
        tex_descripcion.setValue(descripcion);
        com_categoria.setValue(idcategoria);
    }

    public Button getBut_aceptar() {
        return but_aceptarP;
    }

    public Button getBut_cancelar() {
        return but_cancelarP;
    }

    public void GuardarNuevoCliente() {
        String cadena = "php/Producto.php?funcion=GuardarNuevoProducto";
        cadena =
                cadena + "&" + formPanel.getForm().getValues();
        final Conector conec = new Conector(cadena, false);
        Utils.setErrorPrincipal("Guardando el nuevo Producto", "guardar");
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
        String cadena = "php/Producto.php?funcion=GuardarEditarProducto&idproducto=" + idproducto;
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