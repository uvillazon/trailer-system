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
import com.google.gwt.user.client.Window;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.util.DateUtil;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.PaddedPanel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.DateField;
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
import java.util.Date;
import org.trailer.client.parametros.ListaCompraProducto;
import org.trailer.client.sistema.Proveedor;
import org.trailer.client.util.Conector;
import org.trailer.client.util.KMenu;
import org.trailer.client.util.Utils;

/**
 *
 * @author example
 */
public class RealizarCompra extends Panel {

    private final int ANCHO = 1000;
    private final int ALTO = 540;
    private Panel panel;
    private ComboBox com_proveedor;
    private ComboBox com_producto;
    private ComboBox com_responsables;
    private ComboBox com_tipoDoc;
    private TextField tex_numeroDocumento;
    private TextField tex_ordenProd;
    private TextField tex_id;
    private DateField dat_fecha;
    private NumberField tex_montoPagar;
    private NumberField tex_montoTotal;
    private NumberField tex_descuentoPorcentaje;
    private NumberField tex_descuentoBs;
    private ListaComprasProductos lista;
    Proveedor proveedorSeleccionado;
    boolean respuesta = false;
    private TextArea tea_descripcion;
    private Object[][] proveedorM;
    private Object[][] productoM;
    private Object[][] responsablesM;
    private Object[][] compras;
    private Object[][] detalles;
    private Object[] tipoDocM;
    private Button but_aceptar;
    private Button but_cancelar;
    private Button but_limpiar;
    String selecionado = "";
    private Float totalTotal;
    private Float descPorcentaje;
    private Float descuentobss;
    private Float totalpagar;
    Compras compra;

    public RealizarCompra(Object[][] proveedor, Object[][] producto, Object[][] responsables) {
        this.compra = new Compras();

        this.proveedorM = proveedor;
        this.productoM = producto;
        this.responsablesM = responsables;
        this.tipoDocM = new String[]{"Factura", "Nota Venta", "Recibo"};
        this.setClosable(true);
        this.setId("TPfun2002");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Compra Nueva");
        setLayout(new FitLayout());
        onModuleLoad(false);
    }

    public RealizarCompra(Object[][] proveedores, Object[][] productos, Object[][] encargados, Object[][] compras, Object[][] detalles, Compras compra) {
        this.compra = compra;

        this.proveedorM = proveedores;
        this.productoM = productos;
        this.responsablesM = encargados;
        this.compras = compras;
        this.detalles = detalles;

        this.tipoDocM = new String[]{"Factura", "Nota Venta", "Recibo"};
        this.setClosable(true);
        this.setId("TPfun2002");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Compra Actualizar");
        setLayout(new FitLayout());
        onModuleLoad(true);

    }

    public void onModuleLoad(boolean d) {


        Panel pan_borderLayout = new Panel();
        pan_borderLayout.setLayout(new BorderLayout());
        pan_borderLayout.setBaseCls("x-plain");

        Panel pan_norte = new Panel();
        pan_norte.setLayout(new TableLayout(3));
        pan_norte.setBaseCls("x-plain");
        pan_norte.setHeight(90);
        pan_norte.setPaddings(5);

        Panel pan_sud = new Panel();
        pan_sud.setLayout(new TableLayout(3));
        pan_sud.setBaseCls("x-plain");
        pan_sud.setHeight(120);
        pan_sud.setPaddings(5);
        lista = new ListaComprasProductos();
        lista.onModuleLoad();
        Panel pan_centro = lista.getPanel();

        FormPanel for_panel1 = new FormPanel();
        for_panel1.setBaseCls("x-plain");
        for_panel1.setWidth(380);
        for_panel1.setLabelWidth(120);
        com_proveedor = new ComboBox("Proveedor", "proveedor");
        com_producto = new ComboBox("Producto", "producto");
        tex_id = new TextField("Id", "idcompra");
        tex_id.hide();
        for_panel1.add(tex_id);
        for_panel1.add(com_proveedor);
        for_panel1.add(com_producto);

        FormPanel for_panel2 = new FormPanel();
        for_panel2.setBaseCls("x-plain");
        for_panel2.setWidth(380);
        for_panel2.setLabelWidth(120);
        com_tipoDoc = new ComboBox("Tipo Docto.", "tipodoc");
        tex_numeroDocumento = new TextField("Numero docto.", "numerodocumento");
        tex_numeroDocumento.setWidth(160);
        for_panel2.add(com_tipoDoc);
        for_panel2.add(tex_numeroDocumento);


        FormPanel for_panel3 = new FormPanel();
        for_panel3.setBaseCls("x-plain");
        for_panel3.setWidth(380);
        for_panel3.setLabelWidth(120);
        com_responsables = new ComboBox("Responsable", "responsable");
        dat_fecha = new DateField("Fecha", "Y-m-d");
        dat_fecha.setWidth(150);
        dat_fecha.setValue(new Date());
        tex_ordenProd = new TextField("Orden Prod.", "ordenproduccion");
        tex_ordenProd.setWidth(150);
        for_panel3.add(com_responsables);
//        for_panel3.add(tex_ordenProd);
        for_panel3.add(dat_fecha);

        pan_norte.add(new PaddedPanel(for_panel1, 0, 0, 13, 10));
        pan_norte.add(new PaddedPanel(for_panel2, 0, 0, 13, 10));
        pan_norte.add(new PaddedPanel(for_panel3, 0, 0, 13, 10));

        FormPanel for_panel4 = new FormPanel();
        for_panel4.setBaseCls("x-plain");
        for_panel4.setWidth(300);
        for_panel4.setLabelWidth(120);
        tex_montoTotal = new NumberField("Monto total", "montototal");
        tex_montoTotal.disable();
        tex_montoPagar = new NumberField("Monto a pagar", "montopagar");
        tex_montoPagar.disable();
        for_panel4.add(tex_montoTotal);
        for_panel4.add(tex_montoPagar);

        FormPanel for_panel5 = new FormPanel();
        for_panel5.setBaseCls("x-plain");
        for_panel5.setWidth(300);
        for_panel5.setLabelWidth(130);
        tex_descuentoPorcentaje = new NumberField("Descuento (%)", "desuentoporcentaje");
        tex_descuentoBs = new NumberField("Descuento (Bs)", "descuento");
        for_panel5.add(tex_descuentoPorcentaje);
        for_panel5.add(tex_descuentoBs);

        FormPanel for_panel6 = new FormPanel();
        for_panel6.setBaseCls("x-plain");
        for_panel6.setWidth(400);
        tea_descripcion = new TextArea("Observacion", "observacion");
        tea_descripcion.setWidth("100%");

        for_panel6.add(tea_descripcion);

        Panel pan_botones = new Panel();
        pan_botones.setLayout(new HorizontalLayout(10));
        pan_botones.setBaseCls("x-plain");
        //       pan_botones.setHeight(40);
        but_aceptar = new Button("Guardar");
        but_cancelar = new Button("Cancelar");
        but_limpiar = new Button("Limpiar");
        pan_botones.add(but_aceptar);
        pan_botones.add(but_cancelar);
        pan_botones.add(but_limpiar);

        pan_sud.add(new PaddedPanel(for_panel4, 0, 0, 13, 10));
        pan_sud.add(new PaddedPanel(for_panel5, 0, 0, 13, 10));
        pan_sud.add(new PaddedPanel(for_panel6, 3, 0, 13, 10));
        pan_sud.add(new PaddedPanel(pan_botones, 5, 450, 10, 10), new TableLayoutData(3));


        pan_borderLayout.add(pan_norte, new BorderLayoutData(RegionPosition.NORTH));
        pan_borderLayout.add(pan_centro, new BorderLayoutData(RegionPosition.CENTER));
        pan_borderLayout.add(pan_sud, new BorderLayoutData(RegionPosition.SOUTH));
        add(pan_borderLayout);
        if (d) {
            initValues2();
            aniadirgrid();
        } else {
            initValues();
        }
        addListeners();

    }

    public void createCompra() {
        String idProveedor = com_proveedor.getValue();
        String numeroDocumento = tex_numeroDocumento.getValueAsString();
        String ordenproduccion = tex_ordenProd.getValueAsString();
        String tipoDoc = com_tipoDoc.getText();
        String idResponsable = com_responsables.getValue();
        String fecha;
        if (dat_fecha.getValue() == null || dat_fecha.getValueAsString() == "") {
            Date f = new Date();
            fecha = DateUtil.format(f, "Y-m-d");
        } else {
            fecha = DateUtil.format(dat_fecha.getValue(), "Y-m-d");
        }
        String montoTotal = tex_montoTotal.getText();
        String montoAPagar = tex_montoPagar.getText();
        String descuento = tex_descuentoBs.getText();
        String descuentoPorcentaje = tex_descuentoPorcentaje.getText();
        String observacion = tea_descripcion.getValueAsString();
        String idcompra;
        if (tex_id.getValueAsString() == "" || tex_id.getValueAsString() == null) {
            idcompra = "";
        } else {
            idcompra = tex_id.getValueAsString();
        }
        Record[] records = lista.getStore().getRecords();
        JSONArray productos = new JSONArray();
        JSONObject productoObject;

        JSONObject compraObject = new JSONObject();
        compraObject.put("idproveedor", new JSONString(idProveedor));
        compraObject.put("idcompra", new JSONString(idcompra));
        compraObject.put("idempleado", new JSONString(idResponsable));
        compraObject.put("tipodocumento", new JSONString(tipoDoc));
        compraObject.put("numerodocumento", new JSONString(numeroDocumento));
        compraObject.put("ordenproduccion", new JSONString(ordenproduccion));
        compraObject.put("fecha", new JSONString(fecha));
        compraObject.put("montototal", new JSONString(montoTotal));
        compraObject.put("montoapagar", new JSONString(montoAPagar));
        compraObject.put("descuento", new JSONString(descuento));
        compraObject.put("descuentoporcentaje", new JSONString(descuentoPorcentaje));
        compraObject.put("observacion", new JSONString(observacion));

//        Window.alert("tamaño del store "+lista.getStore().getRecords().length);
        for (int i = 0; i < lista.getStore().getRecords().length; i++) {

//            Window.alert("entro al forrrrr");
            productoObject = new JSONObject();
            productoObject.put("id", new JSONString(records[i].getAsString("id")));
            productoObject.put("detalle", new JSONString(records[i].getAsString("detalle")));
            productoObject.put("unidad", new JSONString(records[i].getAsString("unidad")));
            productoObject.put("cantidad", new JSONString(records[i].getAsString("cantidad")));
            productoObject.put("preciounitario", new JSONString(records[i].getAsString("preciounitario")));
            productoObject.put("preciototal", new JSONString(records[i].getAsString("preciototal")));
            productoObject.put("op", new JSONString(records[i].getAsString("op")));

            productos.set(i, productoObject);
            productoObject = null;
        }
        JSONObject resultado = new JSONObject();
        resultado.put("compra", compraObject);
        resultado.put("detalles", productos);

        String datos = "resultado=" + resultado.toString();
        String idcompras = tex_id.getValueAsString();
        Utils.setErrorPrincipal("registrando la compra", "cargar");
        String url = "./php/Compras.php?funcion=GuardarNuevaCompra&" + datos + "&idcompra=" + idcompras;
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
                            limpiarVentanaVenta();
                            compra.getGrid().getStore().reload();
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
                limpiarVentanaVenta();
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
                limpiarVentanaVenta();
            }
        });


        com_producto.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    String codigoProducto = field.getValueAsString().trim();
                    String idproductos = com_producto.getValueAsString().trim();
                    if (!idproductos.isEmpty()) {
                        cargarProducto(idproductos);
                        com_producto.setValue("");
                        com_producto.setEmptyText("");

                    }
                }
            }

            private void cargarProducto(String id) {

                String idProducto;
                String nombre;
                String unidad;
                if (id != null) {
                    SimpleStore producto = new SimpleStore(new String[]{"id", "nombre", "unidad"}, productoM);
                    producto.load();
                    producto.filter("id", id);
                    Record r = producto.getRecords()[0];

                    idProducto = r.getAsString("id");
                    nombre = r.getAsString("nombre");
                    unidad = r.getAsString("unidad");

                    Record registroCompra = lista.getRecordDef().createRecord(new Object[]{
                                idProducto, nombre, unidad, 1, 0.0, 0.0,0});

                    lista.getGrid().stopEditing();
                    lista.getGrid().getStore().insert(0, registroCompra);
                    lista.getGrid().startEditing(0, 0);
                    Float to = new Float(0);
                    for (int i = 0; i < lista.getStore().getRecords().length; i++) {
                        to += lista.getStore().getRecords()[i].getAsFloat("preciototal");
                    }
                    tex_montoTotal.setValue(to.toString());
                    com_producto.setValue("");
                    com_producto.setEmptyText("");
//                        com_producto.focus();
//                    lista.getGrid().se
                    respuesta = true;
                }
            }
        });
        //**************************************************
        //*************ACTUALIZAR TIPO DE CAMBIO*******************
        //**************************************************
//        tex_cambio.addListener(new TextFieldListenerAdapter() {
//
//        });
        tex_descuentoPorcentaje.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    recalcular(true);
                }
            }
        });
        tex_descuentoBs.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    recalcular(false);
                }
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
            tex_descuentoBs.setValue("");
            tex_descuentoPorcentaje.setValue("");
        }
        Float total = new Float(0);
        for (int i = 0; i < grid.getStore().getRecords().length; i++) {
            total += grid.getStore().getRecords()[i].getAsFloat("preciototal");
        }
        tex_montoTotal.setValue(total.toString());
        tex_montoPagar.setValue(total.toString());

    }

    public void resetCamposProveedor() {

        com_proveedor.reset();
        com_proveedor.focus();

    }

    public Panel getPanel() {
        return panel;
    }

    private void limpiarVentanaVenta() {
        lista.LimpiarGrid();
        tea_descripcion.setValue("");

        tex_descuentoBs.setValue(0);
        com_proveedor.setValue("");
        com_producto.setValue("");
        com_responsables.setValue("");
        com_tipoDoc.setValue("");
        tex_montoTotal.setValue(0);
        tex_montoPagar.setValue(0);
        tex_numeroDocumento.setValue("");
        tex_id.setValue("");
        tex_ordenProd.setValue("");

        tex_descuentoPorcentaje.setValue(0);
        dat_fecha.setValue("");
    }

    private void initValues() {

        SimpleStore proveedoresStore = new SimpleStore(new String[]{"idproveedor", "nombre"}, proveedorM);
        proveedoresStore.load();
        com_proveedor.setMinChars(1);
        com_proveedor.setFieldLabel("Proveedor");
        com_proveedor.setStore(proveedoresStore);
        com_proveedor.setDisplayField("nombre");
        com_proveedor.setValueField("idproveedor");
        com_proveedor.setForceSelection(true);
        com_proveedor.setMode(ComboBox.LOCAL);
        com_proveedor.setEmptyText("Buscar proveedor");
        com_proveedor.setLoadingText("buscando...");
        com_proveedor.setTypeAhead(true);
        com_proveedor.setSelectOnFocus(true);
        com_proveedor.setHideTrigger(true);

        SimpleStore productosStore = new SimpleStore(new String[]{"id", "nombre"}, productoM);
        productosStore.load();
        com_producto.setMinChars(1);
        com_producto.setFieldLabel("Producto");
        com_producto.setStore(productosStore);
        com_producto.setDisplayField("nombre");
        com_producto.setValueField("id");
        com_producto.setForceSelection(true);
        com_producto.setMode(ComboBox.LOCAL);
        com_producto.setEmptyText("Buscar producto");
        com_producto.setLoadingText("buscando...");
        com_producto.setTypeAhead(true);
        com_producto.setSelectOnFocus(true);
        com_producto.setHideTrigger(true);

        SimpleStore responsablesStore = new SimpleStore(new String[]{"idempleado", "nombre"}, responsablesM);
        responsablesStore.load();
        com_responsables.setMinChars(1);
        com_responsables.setFieldLabel("Responsable");
        com_responsables.setStore(responsablesStore);
        com_responsables.setDisplayField("nombre");
        com_responsables.setValueField("idempleado");
        com_responsables.setForceSelection(true);
        com_responsables.setMode(ComboBox.LOCAL);
        com_responsables.setEmptyText("Buscar responsable");
        com_responsables.setLoadingText("buscando...");
        com_responsables.setTypeAhead(true);
        com_responsables.setSelectOnFocus(true);
        com_responsables.setHideTrigger(true);
        tex_id.setValue("");

        SimpleStore documentosStore = new SimpleStore("tipos", tipoDocM);
        documentosStore.load();
        com_tipoDoc.setDisplayField("tipos");
        com_tipoDoc.setEmptyText("tipo Documento");
        com_tipoDoc.setStore(documentosStore);


    }

    private void initValues2() {

        SimpleStore proveedoresStore = new SimpleStore(new String[]{"idproveedor", "nombre"}, proveedorM);
        proveedoresStore.load();
        com_proveedor.setMinChars(1);
        com_proveedor.setFieldLabel("Proveedor");
        com_proveedor.setStore(proveedoresStore);
        com_proveedor.setDisplayField("nombre");
        com_proveedor.setValueField("idproveedor");
        com_proveedor.setForceSelection(true);
        com_proveedor.setMode(ComboBox.LOCAL);
        com_proveedor.setLoadingText("buscando...");
        com_proveedor.setTypeAhead(true);
        com_proveedor.setSelectOnFocus(true);
        com_proveedor.setHideTrigger(true);
        com_proveedor.setValue(compras[0][2].toString());


        SimpleStore productosStore = new SimpleStore(new String[]{"id", "nombre"}, productoM);
        productosStore.load();
        com_producto.setMinChars(1);
        com_producto.setFieldLabel("Producto");
        com_producto.setStore(productosStore);
        com_producto.setDisplayField("nombre");
        com_producto.setValueField("id");
        com_producto.setForceSelection(true);
        com_producto.setMode(ComboBox.LOCAL);
        com_producto.setEmptyText("");
        com_producto.setLoadingText("buscando...");
        com_producto.setTypeAhead(true);
        com_producto.setSelectOnFocus(true);
        com_producto.setHideTrigger(true);

        SimpleStore responsablesStore = new SimpleStore(new String[]{"idempleado", "nombre"}, responsablesM);
        responsablesStore.load();
        com_responsables.setMinChars(1);
        com_responsables.setFieldLabel("Responsable");
        com_responsables.setStore(responsablesStore);
        com_responsables.setDisplayField("nombre");
        com_responsables.setValueField("idempleado");
        com_responsables.setForceSelection(true);
        com_responsables.setMode(ComboBox.LOCAL);
        com_responsables.setEmptyText("Buscar responsable");
        com_responsables.setLoadingText("buscando...");
        com_responsables.setTypeAhead(true);
        com_responsables.setSelectOnFocus(true);
        com_responsables.setHideTrigger(true);
        com_responsables.setValue(compras[0][3].toString());


        SimpleStore documentosStore = new SimpleStore("tipos", tipoDocM);
        documentosStore.load();
        com_tipoDoc.setDisplayField("tipos");
        com_tipoDoc.setEmptyText("tipo Documento");
        com_tipoDoc.setStore(documentosStore);
        com_tipoDoc.setValue(compras[0][5].toString());

        tex_descuentoBs.setValue(compras[0][9].toString());
        tex_descuentoPorcentaje.setValue(compras[0][8].toString());
        tex_montoPagar.setValue(compras[0][7].toString());
        tex_montoTotal.setValue(compras[0][6].toString());
        tex_numeroDocumento.setValue(compras[0][1].toString());
        tea_descripcion.setValue(compras[0][10].toString());
        tex_ordenProd.setValue("");
        tex_id.setValue(compras[0][0].toString());
        dat_fecha.setValue(compras[0][4].toString());

    }

    public void aniadirgrid() {

        SimpleStore producto = new SimpleStore(new String[]{"id", "unidad", "cantidad", "preciounitario", "preciototal", "detalle"}, detalles);
        producto.load();
        Record[] dt = producto.getRecords();
        lista.getStore().insert(0, dt);
//        Window.alert("entro al for para insertar  4 ");
    }

    private void recalcular(boolean desc) {

        totalTotal = new Float(tex_montoTotal.getText());
        if (desc == true) {
            descPorcentaje = new Float(tex_descuentoPorcentaje.getText());
            descuentobss = (totalTotal * descPorcentaje) / 100;
            tex_descuentoBs.setValue(descuentobss);
        } else {
            descuentobss = new Float(tex_descuentoBs.getText());
            if (descuentobss == 0) {
                descPorcentaje = new Float(0);
            } else {
                descPorcentaje = (descuentobss * 100) / totalTotal;
            }
            tex_descuentoPorcentaje.setValue(descPorcentaje.toString());
        }

        totalpagar = totalTotal - descuentobss;
        tex_montoPagar.setValue(totalpagar.toString());
        tex_montoPagar.focus();
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
