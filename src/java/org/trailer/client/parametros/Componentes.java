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
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.PaddedPanel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.NumberField;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.TextFieldListenerAdapter;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.event.EditorGridListenerAdapter;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.layout.HorizontalLayout;
import com.gwtext.client.widgets.layout.TableLayout;
import com.gwtext.client.widgets.layout.TableLayoutData;
import org.trailer.client.parametros.ListaCompraProducto;
import org.trailer.client.sistema.Proveedor;
import org.trailer.client.util.Conector;
import org.trailer.client.util.Utils;

/**
 *
 * @author example
 */
public class Componentes extends Panel {

    private final int ANCHO = 1000;
    private final int ALTO = 540;
    private Panel panel;
    private ComboBox com_procesos;
    private ComboBox com_producto;
    private ComboBox com_materiales;
    private TextField tex_nombre;
    private String nombre;
    private String descripcion;
    private String idgrupo;
    private TextField tex_id;
    private NumberField tex_precioVentaU;
    private NumberField tex_costoUnit;
    private NumberField tex_cantidad;
    private NumberField tex_utilidad;
    private NumberField tex_utilidadTotal;
    private NumberField tex_precioTotal;
    private ListaCompraProducto lista;
    Proveedor proveedorSeleccionado;
    boolean respuesta = false;
    private TextArea tea_descripcion;
    private Object[][] procesosM;
    private Object[][] productoM;
    private Object[][] materialesM;
    private Object[][] detalles;
    private Button but_aceptar;
    private Button but_cancelar;
    private Button but_limpiar;
    String selecionado = "";
    private Float costounit;
    private Float utilidad;
    private Float precioventauni;
    private Float preciototal;
    private Float totalutilidad;
    private Float cantidad;
    private String grupo;
    private String url1;
    Grupos padre;

    public Componentes(String idgrupo, String nombre, String descripcion,Float precio,Float utilidad, Object[][] procesos, Object[][] productos, Object[][] materiales, Object[][] detalles, String grupo) {

        this.idgrupo = idgrupo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        
        this.utilidad = utilidad;
        this.precioventauni = precio;
        this.procesosM = procesos;
        this.productoM = productos;
        this.materialesM = materiales;
        this.detalles = detalles;
        
        if (grupo=="grupo") {
            this.url1 = "GuardarComponentesGrupo";
        }
        else{
            this.url1 = "GuardarComponentesProducto";
        }

        this.setClosable(true);
        this.setId("TPfun2005");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle(nombre);
        setLayout(new FitLayout());
        onModuleLoad(false);

    }

    public void onModuleLoad(boolean d) {

        Panel pan_borderLayout = new Panel();
        pan_borderLayout.setLayout(new BorderLayout());
        pan_borderLayout.setBaseCls("x-plain");

        Panel pan_norte = new Panel();
        pan_norte.setLayout(new TableLayout(3));
        pan_norte.setBaseCls("x-plain");
        pan_norte.setHeight(90);
        pan_norte.setPaddings(10, 130, 10, 10);

        Panel pan_sud = new Panel();
        pan_sud.setLayout(new TableLayout(3));
        pan_sud.setBaseCls("x-plain");
        pan_sud.setHeight(120);
        pan_sud.setPaddings(5, 30, 10, 8);

        lista = new ListaCompraProducto();
        lista.onModuleLoad();
        Panel pan_centro = lista.getPanel();

        FormPanel for_panel1 = new FormPanel();
        for_panel1.setBaseCls("x-plain");
        for_panel1.setWidth(500);
        for_panel1.setLabelWidth(120);
        com_procesos = new ComboBox("Proceso/Item", "proceso");
        com_procesos.setWidth(300);
        com_materiales = new ComboBox("Material", "material");
        com_materiales.setWidth(300);
        com_producto = new ComboBox("Producto", "producto");
        com_producto.setWidth(300);
        tex_nombre = new TextField("Nombre", "nombre");
        tex_nombre.setWidth(300);
        tex_id = new TextField("Id", "idgrupo");
        tex_id.hide();
        for_panel1.add(tex_id);
        for_panel1.add(tex_nombre);
        for_panel1.add(com_materiales);

        FormPanel for_panel2 = new FormPanel();
        for_panel2.setBaseCls("x-plain");
        for_panel2.setWidth(500);
        for_panel2.setLabelWidth(120);
        for_panel2.add(com_procesos);
        for_panel2.add(com_producto);

        pan_norte.add(new PaddedPanel(for_panel1, 0, 0, 13, 10));
        pan_norte.add(new PaddedPanel(for_panel2, 0, 0, 13, 10));

        FormPanel for_panel4 = new FormPanel();
        for_panel4.setBaseCls("x-plain");
        for_panel4.setWidth(400);
        for_panel4.setLabelWidth(120);
        tex_costoUnit = new NumberField("Costo Unitario", "costounitario");
        tex_costoUnit.setWidth(220);
        tex_costoUnit.disable();
        tex_precioVentaU = new NumberField("Precio Venta/u", "precioventa");
        tex_precioVentaU.setWidth(220);
        tex_precioVentaU.disable();
        tex_cantidad = new NumberField("Cantidad", "cantidad");
        tex_cantidad.setWidth(220);
        for_panel4.add(tex_costoUnit);
        for_panel4.add(tex_precioVentaU);
        for_panel4.add(tex_cantidad);

        FormPanel for_panel5 = new FormPanel();
        for_panel5.setBaseCls("x-plain");
        for_panel5.setWidth(400);
        for_panel5.setLabelWidth(120);
        tex_utilidad = new NumberField("Utilidad/u", "utilidad");
        tex_utilidad.setWidth(220);
        for_panel5.add(tex_utilidad);
        tex_utilidadTotal = new NumberField("Utilidad Total", "utilidadtotal");
        tex_utilidadTotal.setWidth(220);
        tex_utilidadTotal.disable();
        for_panel5.add(tex_utilidadTotal);
        tex_precioTotal = new NumberField("Precio Total", "preciototal");
        tex_precioTotal.setWidth(220);
        tex_precioTotal.disable();
        for_panel5.add(tex_precioTotal);

        FormPanel for_panel6 = new FormPanel();
        for_panel6.setBaseCls("x-plain");
        for_panel6.setWidth(400);
        tea_descripcion = new TextArea("Observacion", "observacion");
        tea_descripcion.setWidth(260);

        for_panel6.add(tea_descripcion);

        Panel pan_botones = new Panel();
        pan_botones.setLayout(new HorizontalLayout(10));
        pan_botones.setBaseCls("x-plain");
        but_aceptar = new Button("Guardar");
        but_cancelar = new Button("Cancelar");
        but_limpiar = new Button("Limpiar");
        pan_botones.add(but_aceptar);
        pan_botones.add(but_cancelar);
        pan_botones.add(but_limpiar);

        pan_sud.add(new PaddedPanel(for_panel4, 0, 0, 13, 5));
        pan_sud.add(new PaddedPanel(for_panel5, 0, 0, 13, 5));
        pan_sud.add(new PaddedPanel(for_panel6, 3, 0, 13, 5));
        pan_sud.add(new PaddedPanel(pan_botones, 2, 450, 5, 5), new TableLayoutData(2));


        pan_borderLayout.add(pan_norte, new BorderLayoutData(RegionPosition.NORTH));
        pan_borderLayout.add(pan_centro, new BorderLayoutData(RegionPosition.CENTER));
        pan_borderLayout.add(pan_sud, new BorderLayoutData(RegionPosition.SOUTH));
        add(pan_borderLayout);
        initValues();
        if (detalles != null) {
            aniadirgrid();
        }


        addListeners();

    }

    public void createCompra() {
        String nombres = tex_nombre.getValueAsString();
        String costoUnitario = tex_costoUnit.getText();
        String observacion = tea_descripcion.getValueAsString();
        String pvu = tex_precioVentaU.getText();
        String cant = tex_cantidad.getText();
        String utilidad = tex_utilidad.getText();
        String utto = tex_utilidadTotal.getText();
        String ptot = tex_precioTotal.getText();
        String idgrupod;
        if (tex_id.getValueAsString() == "" || tex_id.getValueAsString() == null) {
            idgrupod = "";
        } else {
            idgrupod = tex_id.getValueAsString();
        }
        Record[] records = lista.getStore().getRecords();
        JSONArray productos = new JSONArray();
        JSONObject productoObject;

        JSONObject compraObject = new JSONObject();
        compraObject.put("idgrupo", new JSONString(idgrupo));
        compraObject.put("nombre", new JSONString(nombres));
        compraObject.put("costounitario", new JSONString(costoUnitario));
        compraObject.put("precioventa", new JSONString(pvu));
        compraObject.put("cantidad", new JSONString(cant));
        compraObject.put("utilidad", new JSONString(utilidad));
        compraObject.put("utilidadtotal", new JSONString(utto));
        compraObject.put("preciototal", new JSONString(ptot));
        compraObject.put("descripcion", new JSONString(observacion));
        for (int i = 0; i < lista.getStore().getRecords().length; i++) {

            productoObject = new JSONObject();
            productoObject.put("id", new JSONString(records[i].getAsString("id")));
            productoObject.put("detalle", new JSONString(records[i].getAsString("detalle")));
            productoObject.put("unidad", new JSONString(records[i].getAsString("unidad")));
            productoObject.put("cantidad", new JSONString(records[i].getAsString("cantidad")));
            productoObject.put("preciounitario", new JSONString(records[i].getAsString("preciounitario")));
            productoObject.put("preciototal", new JSONString(records[i].getAsString("preciototal")));

            productos.set(i, productoObject);
            productoObject = null;
        }
        JSONObject resultado = new JSONObject();
        resultado.put("grupo", compraObject);
        resultado.put("detalles", productos);

        String datos = "resultado=" + resultado.toString();
        String idgrupos = tex_id.getValueAsString();
        Utils.setErrorPrincipal("Guardando...", "cargar");

        String url = "./php/Componentes.php?funcion="+url1+"&" + datos + "&idgrupo=" + idgrupos;
        final Conector conec = new Conector(url, false, "GET");
        try {
            conec.getRequestBuilder().sendRequest(datos, new RequestCallback() {

                public void onResponseReceived(Request request, Response response) {
                    String data = response.getText();
                    JSONValue jsonValue = JSONParser.parse(data);
                    JSONObject jsonObject;
                    if ((jsonObject = jsonValue.isObject()) != null) {
                        String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                        String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");
                        if (errorR.equalsIgnoreCase("true")) {
                            Utils.setErrorPrincipal(mensajeR, "mensaje");
                            MessageBox.alert("Se Guardo Correctamente ahora puede cerrar el Panel");
//                               limpiarVentanaComponentes();
//                                Componentes.this.destroy();
//                               compra.getGrid().getStore().reload();
                        } else {
                            com.google.gwt.user.client.Window.alert("error 1000");
                            Utils.setErrorPrincipal(mensajeR, "error");
                        }
                    } else {
                        com.google.gwt.user.client.Window.alert("error 1001");
                        Utils.setErrorPrincipal("Error en la respuesta del servidor", "error");
                    }
                }

                public void onError(Request request, Throwable exception) {
                    com.google.gwt.user.client.Window.alert("error 1002");
                    Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                }
            });
        } catch (RequestException ex) {
            com.google.gwt.user.client.Window.alert("error 1003");
            Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
        }
    //
    }

    private void addListeners() {

        //**************************************************
        //************* BOTON CANCELAR   *******************
        //**************************************************
        but_cancelar.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                limpiarVentanaComponentes();
            }
        });

        //**************************************************
        //************* BOTON ACEPTAR *******************
        //**************************************************
        but_aceptar.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                createCompra();
            }
        });


        but_limpiar.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                limpiarVentanaComponentes();
            }
        });

        tex_utilidad.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    calcularUtilidad(true);
                }
            }
        });
        tex_cantidad.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    calcularPrecioPorCantidad(true);
                }
            }
        });


        com_producto.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    String codigoProducto = field.getValueAsString().trim();
                    String idproductos = com_producto.getValueAsString().trim();
                    if (!idproductos.isEmpty()) {
                        cargarProducto(idproductos, 0);

                        com_producto.setValue("");
                        com_producto.setEmptyText("");
                        com_procesos.clearValue();
                        com_materiales.clearValue();
                    }
                }
            }

            public void onFocus() {
                com_procesos.setEmptyText("Buscar Procesos");
                com_materiales.setEmptyText("Buscar Materiales");
            }
        });
        com_procesos.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    String codigoProducto = field.getValueAsString().trim();
                    String idproceso = com_procesos.getValueAsString().trim();
                    if (!idproceso.isEmpty()) {
                        cargarProducto(idproceso, 1);

                        com_procesos.setValue("");
                        com_procesos.setEmptyText("");
                        com_producto.clearValue();
                        com_materiales.clearValue();
                    }
                }
            }

            public void onFocus() {
                com_producto.setEmptyText("Buscar Productos");
                com_materiales.setEmptyText("Buscar Materiales");
            }
        });
        com_materiales.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    String codigoProducto = field.getValueAsString().trim();
                    String idmaterial = com_materiales.getValueAsString().trim();
//                    MessageBox.alert(idmaterial);
                    if (!idmaterial.isEmpty()) {
                        cargarProducto(idmaterial, 2);

                        com_materiales.setValue("");
                        com_materiales.setEmptyText("");
                        com_procesos.clearValue();
                        com_producto.clearValue();
                    }
                }
            }

            public void onFocus() {
                com_producto.setEmptyText("Buscar Productos");
                com_procesos.setEmptyText("Buscar Procesos");
            }
        });


        //**************************************************
        //*************CALCULAR TOTAL DE COMPRA ************
        //**************************************************
        lista.getGrid().addEditorGridListener(new EditorGridListenerAdapter() {

            @Override
            public void onAfterEdit(GridPanel grid, Record record, String field, Object newValue, Object oldValue, int rowIndex, int colIndex) {
                calcularSubTotal(grid, record, field, newValue, oldValue, rowIndex, colIndex);
            }
        });

    }

    private void cargarProducto(String id, int num) {

        String idProducto;
        String nombre;
        String unidad;
        String costo;
        if (id != null) {
            Record r = null;
            if (num == 0) {
                SimpleStore producto = new SimpleStore(new String[]{"id", "costo", "unidad", "detalle"}, productoM);
                producto.load();
                producto.filter("id", id);
                r = producto.getRecords()[0];
            } else {
                if (num == 1) {
                    SimpleStore producto = new SimpleStore(new String[]{"id", "costo", "unidad", "detalle"}, procesosM);
                    producto.load();
                    producto.filter("id", id);
                    r = producto.getRecords()[0];
                } else {
                    if (num == 2) {
                        SimpleStore producto = new SimpleStore(new String[]{"id", "costo", "unidad", "detalle"}, materialesM);
                        producto.load();
                        producto.filter("id", id);
                        r = producto.getRecords()[0];
                    }
                }
            }

            idProducto = r.getAsString("id");
            nombre = r.getAsString("detalle");
            unidad = r.getAsString("unidad");
            costo = r.getAsString("costo");

            Record registroCompra = lista.getRecordDef().createRecord(new Object[]{
                        idProducto, nombre, unidad, 1, costo, costo});

            lista.getGrid().stopEditing();
            lista.getGrid().getStore().insert(0, registroCompra);
            lista.getGrid().startEditing(0, 0);
            Float to = new Float(0);
            for (int i = 0; i < lista.getStore().getRecords().length; i++) {
                to += lista.getStore().getRecords()[i].getAsFloat("preciototal");
            }
            tex_costoUnit.setValue(to.toString());

            if (num == 0) {
//                            com_producto.setValue("");
                com_producto.setEmptyText("");
                com_producto.focus();
                com_procesos.clearValue();
                com_materiales.clearValue();
                com_materiales.setEmptyText("Buscar Materiales");
                com_procesos.setEmptyText("Buscar Procesos");
                respuesta = true;
            } else {
                if (num == 1) {
//                                com_procesos.setValue("");
                    com_procesos.setEmptyText("");
                    com_procesos.focus();
                    com_producto.clearValue();

                    com_materiales.clearValue();
                    com_materiales.setEmptyText("Buscar Materiales");
                    com_producto.setEmptyText("Buscar Productos");
                    respuesta = true;
                } else {
                    if (num == 2) {
//                                    com_materiales.setValue("");
                        com_materiales.setEmptyText("");
                        com_materiales.focus();
                        com_procesos.clearValue();
                        com_producto.clearValue();
                        com_procesos.setEmptyText("Buscar Procesos");
                        com_producto.setEmptyText("Buscar Productos");
                        respuesta = true;
                    }
                }

            }

        }
    }

    private void calcularSubTotal(GridPanel grid, Record record, String field, Object newValue, Object oldValue, int rowIndex, int colIndex) {
        String temp = newValue.toString();
        Float old = new Float(oldValue.toString());

        Float ne = old;
        try {
            ne = new Float(temp);
        } catch (Exception e) {
            com.google.gwt.user.client.Window.alert("atapadp  " + e.getMessage());
            ne = old;
        }
        if (colIndex == 3) {
            record.set("cantidad", ne);
        }

        record.commit();

        if (record.getAsFloat("preciounitario") != 0.0) {
            record.set("preciototal", record.getAsFloat("cantidad") * record.getAsFloat("preciounitario"));
        }
        Float total = new Float(0);
        for (int i = 0; i < grid.getStore().getRecords().length; i++) {
            total += grid.getStore().getRecords()[i].getAsFloat("preciototal");
        }
        tex_costoUnit.setValue(total.toString());
//        tex_montoPagar.setValue(total.toString());

    }

    public void resetCamposProveedor() {

        com_procesos.reset();
        com_procesos.focus();

    }

    public Panel getPanel() {
        return panel;
    }

    private void limpiarVentanaComponentes() {
        lista.LimpiarGrid();
        com_procesos.setValue("");
        com_producto.setValue("");
        com_materiales.setValue("");
        tex_costoUnit.setValue(0);
        tex_utilidad.setValue(0);
        tex_precioVentaU.setValue(0);
        tex_utilidadTotal.setValue(0);
        tex_cantidad.setValue(0);
        tex_precioTotal.setValue(0);

    }

    private void initValues() {

        SimpleStore procesosStore = new SimpleStore(new String[]{"id", "costo", "unidad", "detalle"}, procesosM);
        procesosStore.load();
        com_procesos.setMinChars(1);
        com_procesos.setFieldLabel("Procesos");
        com_procesos.setStore(procesosStore);
        com_procesos.setDisplayField("detalle");
        com_procesos.setValueField("id");
        com_procesos.setForceSelection(true);
        com_procesos.setMode(ComboBox.LOCAL);
        com_procesos.setEmptyText("Buscar procesos");
        com_procesos.setLoadingText("buscando...");
        com_procesos.setTypeAhead(true);
        com_procesos.setSelectOnFocus(true);
        com_procesos.setHideTrigger(true);

        SimpleStore productosStore = new SimpleStore(new String[]{"id", "costo", "unidad", "detalle"}, productoM);
        productosStore.load();
        com_producto.setMinChars(1);
        com_producto.setFieldLabel("Producto");
        com_producto.setStore(productosStore);
        com_producto.setDisplayField("detalle");
        com_producto.setValueField("id");
        com_producto.setForceSelection(true);
        com_producto.setMode(ComboBox.LOCAL);
        com_producto.setEmptyText("Buscar productos");
        com_producto.setLoadingText("buscando...");
        com_producto.setTypeAhead(true);
        com_producto.setSelectOnFocus(true);
        com_producto.setHideTrigger(true);

        SimpleStore materialesStore = new SimpleStore(new String[]{"id", "costo", "unidad", "detalle"}, materialesM);
        materialesStore.load();
        com_materiales.setMinChars(1);
        com_materiales.setFieldLabel("Materiales");
        com_materiales.setStore(materialesStore);
        com_materiales.setDisplayField("detalle");
        com_materiales.setValueField("id");
        com_materiales.setForceSelection(true);
        com_materiales.setMode(ComboBox.LOCAL);
        com_materiales.setEmptyText("Buscar materiales");
        com_materiales.setLoadingText("buscando...");
        com_materiales.setTypeAhead(true);
        com_materiales.setSelectOnFocus(true);
        com_materiales.setHideTrigger(true);

        tex_id.setValue(idgrupo);

        tex_utilidad.setValue(utilidad);
        tex_precioVentaU.setValue(precioventauni);
        tex_utilidadTotal.setValue(0);
        tex_cantidad.setValue(0);
        tex_precioTotal.setValue(0);

        tex_nombre.setValue(nombre);
        tea_descripcion.setValue(descripcion);

//        SimpleStore documentosStore = new SimpleStore("tipos", tipoDocM);
//        documentosStore.load();
//        com_tipoDoc.setDisplayField("tipos");
//        com_tipoDoc.setEmptyText("tipo Documento");
//        com_tipoDoc.setStore(documentosStore);


    }

    public void aniadirgrid() {

        SimpleStore producto = new SimpleStore(new String[]{"id", "detalle", "unidad", "cantidad", "preciounitario", "preciototal"}, detalles);
        producto.load();
        Record[] dt = producto.getRecords();
        lista.getStore().insert(0, dt);

        Float to = new Float(0);
        for (int i = 0; i < lista.getStore().getRecords().length; i++) {
            to += lista.getStore().getRecords()[i].getAsFloat("preciototal");
        }
        tex_costoUnit.setValue(to.toString());
//        Window.alert("entro al for para insertar  4 ");
    }

    private void calcularUtilidad(boolean desc) {

        costounit = new Float(tex_costoUnit.getText());
        if (desc == true) {
            utilidad = new Float(tex_utilidad.getText());
            precioventauni = (costounit + utilidad);
            tex_precioVentaU.setValue(precioventauni);
        }
    }

    private void calcularPrecioPorCantidad(boolean cant) {
        utilidad = new Float(tex_utilidad.getText());
        precioventauni = new Float(tex_precioVentaU.getText());
        if (cant == true) {
            cantidad = new Float(tex_cantidad.getText());
            totalutilidad = utilidad * cantidad;
            preciototal = cantidad * precioventauni;
            tex_utilidadTotal.setValue(totalutilidad);
            tex_precioTotal.setValue(preciototal);
        }
    }

    public static double round(double val, int places) {
        long factor = (long) Math.pow(10, places);
        val = val * factor;
        long tmp = Math.round(val);
        return (double) tmp / factor;
    }

    public static float round(float val, int places) {
        return (float) round((double) val, places);
    }
}
