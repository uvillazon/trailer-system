/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.produccionproceso;

import com.gwtext.client.data.Store;
import org.trailer.client.MainEntryPoint;
import org.trailer.client.util.KMenu;
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
import com.gwtext.client.util.DateUtil;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.PaddedPanel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FormPanel;
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
//import org.trailer.client.parametros.ListaCompraProducto;
import org.trailer.client.sistema.Proveedor;
import org.trailer.client.util.Conector;
import org.trailer.client.util.Utils;

/**
 *
 * @author Uvillazon
 */
public class EntregaProceso extends Panel {

    private final int ANCHO = 1000;
    private final int ALTO = 540;
    private Panel panel;
    private ComboBox com_Proceso;
    private ComboBox com_producto;
    private ComboBox com_productoProc;
    private ComboBox com_operador;
    private ComboBox com_materia;
    private ComboBox com_productointermedio1;
    private ComboBox com_productointermedio2;
    private TextField tex_salida;
    private TextField tex_Orden;
    private TextField tex_produccion;
    private TextField tex_id;
//    private DateField dat_fecharecepcion;
    private DateField dat_fechaentrega;
//    private NumberField tex_montoCuenta;
//    private NumberField tex_montoTotal;
//    private NumberField tex_saldo;
//    private NumberField tex_descuentoBs;
//    private RadioButton rad_empresa;
//    private RadioButton rad_cliente;
    private ListaProductosProceso lista;
    private ListaProductosProceso lista1;
    private SimpleStore responsablesStore;
    private SimpleStore prodStore;
    Proveedor proveedorSeleccionado;
    boolean respuesta = false;
    private TextArea tea_observaciones;
    private Object[][] procesoM;
    private Object[][] productoM;
    private Object[][] responsablesM;
    private Object[][] materiaM;
    private Object[][] detalles;
    private Object[][] orden;
    private Object[][] productointermedioM;
    private Object[][] productoordenM;
    private Button but_aceptar;
    private Button but_cancelar;
    private Button but_limpiar;
    String selecionado = "";
    private Float totalTotal;
    private Float montocuenta;
    private Float saldo;
//    private Float totalpagar;
    private KMenu kmenu;
    private MainEntryPoint pan;
//    private Store prodStore;

    public EntregaProceso(KMenu kmenu, MainEntryPoint panel) {

        this.kmenu = kmenu;
        this.pan = panel;
        this.setClosable(true);
        this.setId("TPfun5000");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Orden de Produccion");
        setLayout(new FitLayout());
        onModuleLoad(false);
    }

    public EntregaProceso(Object[][] proceso, Object[][] producto, Object[][] responsables, Object[][] materia, Object[][] productoIntermedio) {
//        this.compra=compra;

        this.procesoM = proceso;
        this.productoM = producto;
        this.responsablesM = responsables;
        this.materiaM = materia;
        this.productointermedioM = productoIntermedio;
        responsablesStore = new SimpleStore(new String[]{"idempleado", "nombre"}, responsablesM);
        responsablesStore.reload();
//        this.tipoDocM = new String[]{"Factura", "Nota Venta", "Recibo"};
        this.setClosable(true);
        this.setId("TPfun5000");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Produccion de Procesos");
        setLayout(new FitLayout());
        onModuleLoad(false);

    }

    public EntregaProceso(Object[][] orden, Object[][] detalle, Object[][] clientes, Object[][] productos, Object[][] responsables, Object[][] empresas) {
//        this.compra=compra;

        this.procesoM = clientes;
        this.productoM = productos;
        this.responsablesM = responsables;
        this.materiaM = empresas;
        this.orden = orden;
        this.detalles = detalle;

//        this.productoordenM = new String[]{"id":"", "Nota Venta", "Recibo"};
        this.setClosable(true);
        this.setId("TPfun5000");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Produccion");
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
        pan_norte.setHeight(125);
        pan_norte.setPaddings(5, 30, 5, 10);

        Panel pan_sud = new Panel();
        pan_sud.setLayout(new TableLayout(2));
        pan_sud.setBaseCls("x-plain");
        pan_sud.setHeight(100);
        pan_sud.setPaddings(5, 20, 5, 10);

        lista = new ListaProductosProceso();
        lista.onModuleLoad("Entrega de Materiales o Productos");
        lista1 = new ListaProductosProceso();
        lista1.onModuleLoad("Recepcion de Productos");
        Panel pan_centro = new Panel();
        pan_centro.setLayout(new TableLayout(2));
        pan_centro.setBaseCls("x-plain");
        pan_centro.setPaddings(5, 5, 5, 5);
        pan_centro.add(new PaddedPanel(lista.getPanel(), 0, 0, 13, 0));
        pan_centro.add(new PaddedPanel(lista1.getPanel(), 0, 0, 13, 0));

        FormPanel for_panel1 = new FormPanel();
        for_panel1.setBaseCls("x-plain");
        for_panel1.setWidth(380);
        for_panel1.setLabelWidth(150);
        com_Proceso = new ComboBox("Procesos", "proceso");
        com_Proceso.setWidth(180);
        com_producto = new ComboBox("Producto", "producto");
        com_producto.setWidth(180);
        com_productoProc = new ComboBox("Producto Terminado", "producto");
        com_productoProc.setWidth(180);
        com_productointermedio1 = new ComboBox("Producto Int.", "productointermedio");
        com_productointermedio1.setWidth(180);

        com_productointermedio2 = new ComboBox("Producto Int.", "productointermedio");
        com_productointermedio2.setWidth(180);


        tex_salida = new TextField("Salida", "salida");
        tex_salida.setWidth(180);
        tex_Orden = new TextField("Orden Produccion", "orden");
        tex_Orden.setWidth(180);
        tex_produccion = new TextField("# Proceso Produccion", "produccion");
        tex_produccion.setWidth(180);
        tex_id = new TextField("Id", "idproduccion");
        tex_id.hide();
        com_operador = new ComboBox("Operario", "operario");
        com_operador.setWidth(180);
//        dat_fecharecepcion = new DateField("Fecha Recepcion", "Y-m-d");
//        dat_fecharecepcion.setWidth(180);
        dat_fechaentrega = new DateField("Fecha Entrega", "Y-m-d");
        dat_fechaentrega.setWidth(180);
        com_materia = new ComboBox("Materia", "materia");
        com_materia.setWidth(180);
        for_panel1.setTitle("Informacion Produccion de Procesos");
        for_panel1.add(tex_id);
        for_panel1.add(tex_produccion);
        for_panel1.add(tex_Orden);
        for_panel1.add(com_Proceso);
        for_panel1.add(com_operador);

        FormPanel for_panel2 = new FormPanel();
        for_panel2.setBaseCls("x-plain");
        for_panel2.setWidth(360);
        for_panel2.setBorder(true);
        for_panel2.setTitle("Entrega de Materiales/Productos");
        for_panel2.setLabelWidth(100);
        for_panel2.add(com_materia);
        for_panel2.add(com_productointermedio1);
        for_panel2.add(com_producto);

        FormPanel for_panel3 = new FormPanel();
        for_panel3.setBaseCls("x-plain");
        for_panel3.setWidth(380);
        for_panel3.setLabelWidth(150);
        for_panel3.setTitle("Recepcion de Productos");
        for_panel3.add(com_productoProc);
        for_panel3.add(com_productointermedio2);
        for_panel3.add(tex_salida);

        pan_norte.add(new PaddedPanel(for_panel1, 0, 10, 13, 10));
        pan_norte.add(new PaddedPanel(for_panel2, 0, 10, 13, 10));
        pan_norte.add(new PaddedPanel(for_panel3, 0, 10, 13, 10));

        FormPanel for_panel4 = new FormPanel();
        for_panel4.setBaseCls("x-plain");
        for_panel4.setWidth(400);
        for_panel4.setLabelWidth(120);
        for_panel4.add(dat_fechaentrega);

        FormPanel for_panel6 = new FormPanel();
        for_panel6.setBaseCls("x-plain");
        for_panel6.setWidth(350);
        tea_observaciones = new TextArea("Observacion", "observacion");
        tea_observaciones.setWidth("100%");

        for_panel6.add(tea_observaciones);

        Panel pan_botones = new Panel();
        pan_botones.setLayout(new HorizontalLayout(5));
        pan_botones.setBaseCls("x-plain");
        //       pan_botones.setHeight(40);
        but_aceptar = new Button("Guardar");
        but_cancelar = new Button("Cancelar");
        but_limpiar = new Button("Limpiar");
        pan_botones.add(but_aceptar);
        pan_botones.add(but_cancelar);
        pan_botones.add(but_limpiar);

        pan_sud.add(new PaddedPanel(for_panel4, 5, 100, 13, 0));
//        pan_sud.add(new PaddedPanel(for_panel5, 0, 0, 13, 10));
        pan_sud.add(new PaddedPanel(for_panel6, 5, 20, 13, 0));
        pan_sud.add(new PaddedPanel(pan_botones, 0, 400, 15, 10), new TableLayoutData(3));


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

    public void createOrden() {
        String procesp = "";
        String operador = "";
        String numeroproduccion = "";
        String ordenproduccion;
        procesp = com_Proceso.getValueAsString();
        ordenproduccion = tex_Orden.getValueAsString();
        operador = com_operador.getValueAsString();
        numeroproduccion = tex_produccion.getValueAsString();
        String id = tex_id.getValueAsString();
//   
        String fechaent;
        if (dat_fechaentrega.getValueAsString() == null || dat_fechaentrega.getValueAsString() == "") {
            Date f = new Date();
            fechaent = DateUtil.format(f, "Y-m-d");
        } else {
            fechaent = DateUtil.format(dat_fechaentrega.getValue(), "Y-m-d");
        }
//        String montoTotal = tex_montoTotal.getText();
//        String montoAcuenta = tex_montoCuenta.getText();
//        String saldos = tex_saldo.getText();
        String observacion = tea_observaciones.getValueAsString();
//        String identrega;

        Record[] records = lista.getStore().getRecords();
        Record[] records1 = lista1.getStore().getRecords();
        JSONArray entregas = new JSONArray();
        JSONArray salidas = new JSONArray();
        JSONObject entregaObject;
        JSONObject salidaObject;

        JSONObject ordenObject = new JSONObject();
        ordenObject.put("proceso", new JSONString(procesp));
        ordenObject.put("id", new JSONString(id));
        ordenObject.put("operador", new JSONString(operador));

        ordenObject.put("fechaentrega", new JSONString(fechaent));

        ordenObject.put("observacion", new JSONString(observacion));
        ordenObject.put("numeroproduccion", new JSONString(numeroproduccion));
        ordenObject.put("ordenproduccion", new JSONString(ordenproduccion));



        for (int i = 0; i < lista.getStore().getRecords().length; i++) {
//
//            MessageBox.alert("entro al forrrrr");
            entregaObject = new JSONObject();
            entregaObject.put("id", new JSONString(records[i].getAsString("id")));
            entregaObject.put("detalle", new JSONString(records[i].getAsString("detalle")));
            entregaObject.put("unidad", new JSONString(records[i].getAsString("unidad")));
            entregaObject.put("cantidad", new JSONString(records[i].getAsString("cantidad")));
//            productoObject.put("preciounitario", new JSONString(records[i].getAsString("preciounitario")));
//            productoObject.put("preciototal", new JSONString(records[i].getAsString("preciototal")));

            entregas.set(i, entregaObject);
            entregaObject = null;
        }
        for (int j = 0; j < lista1.getStore().getRecords().length; j++) {

//            MessageBox.alert("entro al forrrrr1111");
            salidaObject = new JSONObject();
            salidaObject.put("id", new JSONString(records1[j].getAsString("id")));
            salidaObject.put("detalle", new JSONString(records1[j].getAsString("detalle")));
//            productoObject.put("unidad", new JSONString(records[i].getAsString("unidad")));
            salidaObject.put("cantidad", new JSONString(records1[j].getAsString("cantidad")));
//            productoObject.put("preciounitario", new JSONString(records[i].getAsString("preciounitario")));
//            productoObject.put("preciototal", new JSONString(records[i].getAsString("preciototal")));

            salidas.set(j, salidaObject);
            entregaObject = null;
        }
        JSONObject resultado = new JSONObject();
        resultado.put("orden", ordenObject);
        resultado.put("entregas", entregas);
        resultado.put("salidas", salidas);

        String datos = "resultado=" + resultado.toString();
//        String idorden = tex_id.getValueAsString();
        Utils.setErrorPrincipal("registrando la compra", "cargar");
        String url = "./php/Produccion.php?funcion=GuardarProduccionProceso&" + datos;
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
//                               compra.getGrid().getStore().reload();
                        } else {
//                            com.google.gwt.user.client.Window.alert("error 1000");
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
                createOrden();
            }
        });


        but_limpiar.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                limpiarVentanaVenta();
            }
        });
        tex_Orden.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
//                    String codigoProducto = field.getValueAsString().trim();
                    String orden = tex_Orden.getText().trim();
                    if (!orden.isEmpty()) {
                        tex_Orden.setReadOnly(true);
                        com_productoProc.enable();
                        com_productointermedio1.enable();
                        com_productointermedio2.enable();
                        cargarTerminados(orden);
                        com_Proceso.focus();
                    }
                }
            }
        });
        tex_produccion.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
//                    String codigoProducto = field.getValueAsString().trim();
                    String orden = tex_produccion.getText().trim();
                    if (!orden.isEmpty()) {
                        tex_Orden.setReadOnly(true);
                        com_Proceso.setReadOnly(true);
                        com_operador.setReadOnly(true);
                        com_productoProc.enable();
                        com_productointermedio1.enable();
                        com_productointermedio2.enable();
                        cargarTerminadosOrden(orden);
                        tex_Orden.focus();
                    }
                }
            }
        });

        com_materia.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
//                    String codigoProducto = field.getValueAsString().trim();
                    String idmateria = com_materia.getValueAsString().trim();
                    if (!idmateria.isEmpty()) {
                        cargarProducto(idmateria, 0, "m");
                        com_materia.setValue("");
                        com_materia.setEmptyText("");
                    }
                }
            }
        });
        com_productointermedio1.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
//                    String codigoProducto = field.getValueAsString().trim();
                    String idmateria = com_productointermedio1.getValueAsString().trim();
                    if (!idmateria.isEmpty()) {

                        cargarProductoIntermedio(idmateria, 0);
//                        com_productointermedio1.setValue("");
//                        com_productointermedio1.setEmptyText("");
//                        com_productointermedio1.focus();
                    }
                }
            }
        });
        com_productointermedio2.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
//                    String codigoProducto = field.getValueAsString().trim();
                    String idmateria = com_productointermedio2.getValueAsString().trim();
                    if (!idmateria.isEmpty()) {

                        cargarProductoIntermedio(idmateria, 1);
//                        com_productointermedio1.setValue("");
//                        com_productointermedio1.setEmptyText("");
//                        com_productointermedio1.focus();
                    }
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
                        cargarProducto(idproductos, 0, "pd");
                        com_producto.setValue("");
                        com_producto.setEmptyText("");
                    }
                }
            }
        });
        tex_salida.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
//                    String codigoProducto = field.getValueAsString().trim();
                    String orden = tex_Orden.getText().trim();
                    if (!orden.isEmpty()) {

                        String detalle = tex_salida.getValueAsString();
                        Record registroCompra = lista1.getRecordDef().createRecord(new Object[]{
                                    1, 1, "pieza", detalle});

                        lista1.getGrid().stopEditing();
                        lista1.getGrid().getStore().insert(0, registroCompra);
                        lista1.getGrid().startEditing(0, 0);
                        tex_salida.setValue("");
                        tex_salida.focus();
                        ;
                    }
                }
            }
        });

        com_Proceso.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
//                    String codigoProducto = field.getValueAsString().trim();
                    String idproceso = com_Proceso.getValueAsString().trim();
                    if (!idproceso.isEmpty()) {
                        com_Proceso.setEditable(false);
                        cargarNumeroProduccion(idproceso);
                    }
                }
            }
        });

        com_productoProc.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    String codigoProducto = field.getValueAsString().trim();
                    String idproductosproc = com_productoProc.getValueAsString().trim();
                    if (!idproductosproc.isEmpty()) {
                        cargarProducto(idproductosproc, 1, "");
                        com_productoProc.setValue("");
                        com_productoProc.setEmptyText("");
                    }
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

    public void cargarTerminados(String orden) {
        String enlace = "php/Produccion.php?funcion=BuscarProductosPorId&numeroorden=" + orden;
        Utils.setErrorPrincipal("Cargando parametros de la compra", "cargar");
        final Conector conec = new Conector(enlace, false);
        {

            try {
                conec.getRequestBuilder().sendRequest(null, new RequestCallback() {

                    private EventObject e;

                    public void onResponseReceived(Request request, Response response) {
                        String data = response.getText();
                        JSONValue jsonValue = JSONParser.parse(data);
                        JSONObject jsonObject;
                        if ((jsonObject = jsonValue.isObject()) != null) {
                            String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                            String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");
                            if (errorR.equalsIgnoreCase("true")) {
                                Utils.setErrorPrincipal(mensajeR, "mensaje");

                                JSONValue marcaV = jsonObject.get("resultado");
                                JSONObject marcaO;
                                if ((marcaO = marcaV.isObject()) != null) {
                                    Object[][] productos = Utils.getArrayOfJSONObject(marcaO, "productoOrdenM", new String[]{"id", "detalle", "unidad"});
                                    SimpleStore prodStore = new SimpleStore(new String[]{"id", "detalle", "unidad"}, productos);
                                    prodStore.reload();
                                    com_productoProc.setStore(prodStore);
                                    productoordenM = productos;


                                    Object[][] productosintermedio = Utils.getArrayOfJSONObject(marcaO, "productoIntermedioM", new String[]{"id", "detalle", "unidad"});
                                    SimpleStore prodintStore = new SimpleStore(new String[]{"id", "detalle", "unidad"}, productosintermedio);
                                    prodintStore.reload();
                                    com_productointermedio1.setStore(prodintStore);
                                    com_productointermedio2.setStore(prodintStore);
                                    productointermedioM = productosintermedio;
                                    CargarStore(productos, productosintermedio);
//                                     MessageBox.alert("Cargo producto"+productos);

                                } else {
                                    MessageBox.alert("No Hay datos en la consulta");
                                }
                            }
                        } else {
                        }
                        throw new UnsupportedOperationException("Not supported yet.");
                    }

                    public void onError(Request request, Throwable exception) {
                        throw new UnsupportedOperationException("Not supported yet.");
                    }
                });

            } catch (RequestException e) {
                e.getMessage();
                MessageBox.alert("Ocurrio un error al conectarse con el SERVIDOR");
            }

        }
    }

    public void cargarTerminadosOrden(String orden) {
        String enlace = "php/Produccion.php?funcion=BuscarProduccionPorNumero&numeroproduccion=" + orden;
        Utils.setErrorPrincipal("Cargando parametros de la compra", "cargar");
        final Conector conec = new Conector(enlace, false);
        {

            try {
                conec.getRequestBuilder().sendRequest(null, new RequestCallback() {

                    private EventObject e;

                    public void onResponseReceived(Request request, Response response) {
                        String data = response.getText();
                        JSONValue jsonValue = JSONParser.parse(data);
                        JSONObject jsonObject;
                        if ((jsonObject = jsonValue.isObject()) != null) {
                            String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                            String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");
                            if (errorR.equalsIgnoreCase("true")) {
                                Utils.setErrorPrincipal(mensajeR, "mensaje");

                                JSONValue marcaV = jsonObject.get("resultado");
                                JSONObject marcaO;
                                if ((marcaO = marcaV.isObject()) != null) {
                                    Object[][] productos = Utils.getArrayOfJSONObject(marcaO, "productoOrdenM", new String[]{"id", "detalle", "unidad"});
                                    SimpleStore prodStore = new SimpleStore(new String[]{"id", "detalle", "unidad"}, productos);
                                    prodStore.reload();
                                    com_productoProc.setStore(prodStore);
                                    productoordenM = productos;
                                    String orden = Utils.getStringOfJSONObject(marcaO, "numeroorden");
                                    String proceso = Utils.getStringOfJSONObject(marcaO, "idproceso");
                                    String operador = Utils.getStringOfJSONObject(marcaO, "id");
                                    String observacion = Utils.getStringOfJSONObject(marcaO, "observacion");
                                    String fecchaentrega = Utils.getStringOfJSONObject(marcaO, "fechaentrega");
                                    String idproduccion = Utils.getStringOfJSONObject(marcaO, "idproduccion");
                                    Object[][] entradas = Utils.getArrayOfJSONObject(marcaO, "entregasM", new String[]{"id", "cantidad", "detalle", "unidad"});
                                    Object[][] salidas = Utils.getArrayOfJSONObject(marcaO, "salidasM", new String[]{"id", "cantidad", "detalle", "unidad"});



                                    Object[][] productosintermedio = Utils.getArrayOfJSONObject(marcaO, "productoIntermedioM", new String[]{"id", "detalle", "unidad"});
                                    SimpleStore prodintStore = new SimpleStore(new String[]{"id", "detalle", "unidad"}, productosintermedio);
                                    prodintStore.reload();
                                    com_productointermedio1.setStore(prodintStore);
                                    com_productointermedio2.setStore(prodintStore);
                                    productointermedioM = productosintermedio;
                                    CargarStore(productos, productosintermedio);
                                    cargarGrillas(entradas, salidas, idproduccion, orden, proceso, observacion, operador, fecchaentrega);

//                                    MessageBox.alert("Cargo producto" + productos);

                                } else {
                                    MessageBox.alert("No Hay datos en la consulta");
                                }
                            }
                        } else {
                        }
                        throw new UnsupportedOperationException("Not supported yet.");
                    }

                    public void onError(Request request, Throwable exception) {
                        throw new UnsupportedOperationException("Not supported yet.");
                    }
                });

            } catch (RequestException e) {
                e.getMessage();
                MessageBox.alert("Ocurrio un error al conectarse con el SERVIDOR");
            }

        }
    }

    private void cargarItems(String idproceso) {
        String enlace = "php/Produccion.php?funcion=BuscarNumeroProduccion&idproceso=" + idproceso;
        Utils.setErrorPrincipal("Cargando parametros...", "cargar");
        final Conector conec = new Conector(enlace, false);
        {

            try {
                conec.getRequestBuilder().sendRequest(null, new RequestCallback() {

                    private EventObject e;

                    public void onResponseReceived(Request request, Response response) {
                        String data = response.getText();
                        JSONValue jsonValue = JSONParser.parse(data);
                        JSONObject jsonObject;
                        if ((jsonObject = jsonValue.isObject()) != null) {
                            String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                            String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");
                            if (errorR.equalsIgnoreCase("true")) {
                                Utils.setErrorPrincipal(mensajeR, "mensaje");

                                JSONValue marcaV = jsonObject.get("resultado");
                                JSONObject marcaO;
                                if ((marcaO = marcaV.isObject()) != null) {
                                    Object[][] entradas = Utils.getArrayOfJSONObject(marcaO, "entrada", new String[]{"id", "cantidad", "unidad", "detalle", "fecha"});
                                    Object[][] salidas = Utils.getArrayOfJSONObject(marcaO, "salida", new String[]{"id", "cantidad", "unidad", "detalle", "fecha"});
                                    String idproduccion = Utils.getStringOfJSONObject(marcaO, "idproduccion");
                                    String fecchaentrega = Utils.getStringOfJSONObject(marcaO, "fechaentrega");
                                    String observacion = Utils.getStringOfJSONObject(marcaO, "observacion");
                                    String encargado = Utils.getStringOfJSONObject(marcaO, "encargado");
//                                    cargarGrillas(entradas, salidas, idproduccion, observacion, encargado, fecchaentrega);

                                } else {
                                    MessageBox.alert("No Hay datos en la consulta");
                                }
                            }
                        } else {
                        }
                        throw new UnsupportedOperationException("Not supported yet.");
                    }

                    public void onError(Request request, Throwable exception) {
                        throw new UnsupportedOperationException("Not supported yet.");
                    }
                });

            } catch (RequestException e) {
                e.getMessage();
                MessageBox.alert("Ocurrio un error al conectarse con el SERVIDOR");
            }

        }
    }

    private void cargarNumeroProduccion(String idproceso) {
        String enlace = "php/Produccion.php?funcion=BuscarNumeroProduccion&idproceso=" + idproceso;
        Utils.setErrorPrincipal("Cargando parametros...", "cargar");
        final Conector conec = new Conector(enlace, false);
        {

            try {
                conec.getRequestBuilder().sendRequest(null, new RequestCallback() {

                    private EventObject e;

                    public void onResponseReceived(Request request, Response response) {
                        String data = response.getText();
                        JSONValue jsonValue = JSONParser.parse(data);
                        JSONObject jsonObject;
                        if ((jsonObject = jsonValue.isObject()) != null) {
                            String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                            String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");
                            if (errorR.equalsIgnoreCase("true")) {
                                Utils.setErrorPrincipal(mensajeR, "mensaje");

                                JSONValue marcaV = jsonObject.get("resultado");
                                JSONObject marcaO;
                                if ((marcaO = marcaV.isObject()) != null) {

                                    String numeroproduccion = Utils.getStringOfJSONObject(marcaO, "numeroproduccion");
                                    tex_produccion.setValue(numeroproduccion);
                                    tex_id.setValue("");
                                    com_operador.focus();

                                } else {
                                    MessageBox.alert("No Hay datos en la consulta");
                                }
                            }
                        } else {
                        }
                        throw new UnsupportedOperationException("Not supported yet.");
                    }

                    public void onError(Request request, Throwable exception) {
                        throw new UnsupportedOperationException("Not supported yet.");
                    }
                });

            } catch (RequestException e) {
                e.getMessage();
                MessageBox.alert("Ocurrio un error al conectarse con el SERVIDOR");
            }

        }
    }

    public void cargarGrillas(Object[][] entradas, Object[][] salidas, String idproduccion, String orden, String proceso, String observacion, String encargado, String fecchaentrega) {
        tea_observaciones.setValue(observacion);
        com_operador.setValue(encargado);
        dat_fechaentrega.setValue(fecchaentrega);
        tex_Orden.setValue(orden);
        com_Proceso.setValue(proceso);
        tex_id.setValue(idproduccion);
        SimpleStore ordenProductos = new SimpleStore(new String[]{"id", "cantidad", "detalle", "unidad"}, entradas);
        ordenProductos.reload();
        Record[] dt = ordenProductos.getRecords();
        lista.LimpiarGrid();
        lista.getStore().insert(0, dt);

        SimpleStore ordenProductosSalidas = new SimpleStore(new String[]{"id", "cantidad", "detalle", "unidad"}, salidas);
        ordenProductosSalidas.reload();
        Record[] dt1 = ordenProductosSalidas.getRecords();
        lista1.LimpiarGrid();
        lista1.getStore().insert(0, dt1);

    }

    private void cargarProducto(String id, int lis, String dato) {

        String idProducto = "";
        String detalle = "";
        String unidad = "";
        String fecha = "";
        if (id != null) {
            if (lis == 0) {

                if (dato == "pd") {
//                            producto=null;                            
                    SimpleStore producto = new SimpleStore(new String[]{"id", "detalle", "unidad"}, productoM);
                    producto.load();
                    producto.filter("id", id);
                    Record r = producto.getRecords()[0];
                    idProducto = r.getAsString("id");
                    detalle = r.getAsString("detalle");
                    unidad = r.getAsString("unidad");
//                    fecha = r.getAsString("fecha");
                    Record registroCompra = lista.getRecordDef().createRecord(new Object[]{
                                idProducto, 1, unidad, detalle});

                    lista.getGrid().stopEditing();
                    lista.getGrid().getStore().insert(0, registroCompra);
                    lista.getGrid().startEditing(0, 0);
                    com_producto.setValue("");
                    com_producto.setEmptyText("");
                    com_producto.focus();
                    com_materia.setValue("");
                    com_materia.clearValue();
                    com_materia.setEmptyText("Buscar Materia");
                    com_productoProc.setValue("");
                    com_productoProc.clearValue();
                    com_productoProc.setEmptyText("Buscar Producto");

                } else {

                    if (dato == "m") {
                        SimpleStore materia = new SimpleStore(new String[]{"id", "detalle", "unidad"}, materiaM);
                        materia.load();
                        materia.filter("id", id);
                        Record r = materia.getRecords()[0];
                        idProducto = r.getAsString("id");
                        detalle = r.getAsString("detalle");
                        unidad = r.getAsString("unidad");
                        fecha = r.getAsString("fecha");
                        Record registroCompra = lista.getRecordDef().createRecord(new Object[]{
                                    idProducto, 1, unidad, detalle});

                        lista.getGrid().stopEditing();
                        lista.getGrid().getStore().insert(0, registroCompra);
                        lista.getGrid().startEditing(0, 0);
                        com_materia.setValue("");
                        com_materia.setEmptyText("");
                        com_materia.focus();
                        com_producto.setValue("");
                        com_producto.clearValue();
                        com_producto.setEmptyText("Buscar Producto");
                        com_productoProc.setValue("");
                        com_productoProc.clearValue();
                        com_productoProc.setEmptyText("Buscar Producto");

                    } else {
                        Store productointM = com_productointermedio1.getStore();
//                       = new SimpleStore(new String[]{"id", "detalle", "unidad"}, productointermedioM);
                        productointM.reload();
                        productointM.filter("id", id);
                        Record r = productointM.getRecords()[0];
                        idProducto = r.getAsString("id");
                        detalle = r.getAsString("detalle");
                        unidad = r.getAsString("unidad");
                        MessageBox.alert(idProducto);
//                        fecha = r.getAsString("fecha");
                        Record registroCompra = lista.getRecordDef().createRecord(new Object[]{
                                    idProducto, 1, unidad});

                        lista.getGrid().stopEditing();
                        lista.getGrid().getStore().insert(0, registroCompra);
                        lista.getGrid().startEditing(0, 0);
//                        com_materia.setValue("");
//                        com_materia.setEmptyText("");
//                        com_materia.focus();
//                        com_producto.setValue("");
//                        com_producto.clearValue();
//                        com_producto.setEmptyText("Buscar Producto");
//                        com_productoProc.setValue("");
//                        com_productoProc.clearValue();
//                        com_productoProc.setEmptyText("Buscar Producto");
                        com_productointermedio1.clearValue();
                        com_productointermedio1.focus();

                    }
                }

            //                        respuesta = true;
            } else {
                if (dato == "") {
                    SimpleStore productoProc = new SimpleStore(new String[]{"id", "detalle", "unidad"}, productoM);
                    productoProc.load();
                    productoProc.filter("id", id);
                    Record r = productoProc.getRecords()[0];

                    idProducto = r.getAsString("id");
                    detalle = r.getAsString("detalle");
                    unidad = r.getAsString("unidad");
                    fecha = r.getAsString("fecha");

                    Record registroCompra = lista1.getRecordDef().createRecord(new Object[]{
                                idProducto, 1, unidad, detalle});

                    lista1.getGrid().stopEditing();
                    lista1.getGrid().getStore().insert(0, registroCompra);
                    lista1.getGrid().startEditing(0, 0);
                    com_productoProc.setValue("");
                    com_productoProc.setEmptyText("");
                    com_productoProc.focus();
                    com_producto.setValue("");
                    com_producto.clearValue();
                    com_producto.setEmptyText("Buscar Producto");
                    com_materia.setValue("");
                    com_materia.clearValue();
                    com_materia.setEmptyText("Buscar Materia");
                }
//                        respuesta = true;
            }
        }
    }

    private void cargarProductoIntermedio(String id, int lis) {

        String idProducto = "";
        String detalle = "";
        String unidad = "";
        String fecha = "";

        if (id != null) {
            if (lis == 0) {
                SimpleStore materia = new SimpleStore(new String[]{"id", "detalle", "unidad"}, productointermedioM);
                materia.load();
                materia.filter("id", id);
                Record r = materia.getRecords()[0];
//                idProducto = r.getAsString("id");
                detalle = r.getAsString("detalle");
//                unidad = r.getAsString("unidad");
//                fecha = r.getAsString("fecha");
//                detalle = com_productointermedio1.getValueAsString();
                unidad = "pieza";
//                MessageBox.alert("sas" + idProducto + "dsd" + id);
//                        fecha = r.getAsString("fecha");
                Record registroCompra = lista.getRecordDef().createRecord(new Object[]{
                            id, 1, unidad, detalle});

                lista.getGrid().stopEditing();
                lista.getGrid().getStore().insert(0, registroCompra);
                lista.getGrid().startEditing(0, 0);
//                        com_materia.setValue("");
//                        com_materia.setEmptyText("");
//                        com_materia.focus();
//                        com_producto.setValue("");
//                        com_producto.clearValue();
//                        com_producto.setEmptyText("Buscar Producto");
//                        com_productoProc.setValue("");
//                        com_productoProc.clearValue();
//                        com_productoProc.setEmptyText("Buscar Producto");
                com_productointermedio1.clearValue();
                com_productointermedio1.focus();


            } else {
                SimpleStore materia = new SimpleStore(new String[]{"id", "detalle", "unidad"}, productointermedioM);
                materia.load();
                materia.filter("id", id);
                Record r = materia.getRecords()[0];
//                idProducto = r.getAsString("id");
                detalle = r.getAsString("detalle");
//                unidad = r.getAsString("unidad");
//                fecha = r.getAsString("fecha");
//                detalle = com_productointermedio1.getValueAsString();
                unidad = "pieza";
//                MessageBox.alert("sas" + idProducto + "dsd" + id);
//                        fecha = r.getAsString("fecha");
                Record registroCompra = lista1.getRecordDef().createRecord(new Object[]{
                            id, 1, unidad, detalle});

                lista1.getGrid().stopEditing();
                lista1.getGrid().getStore().insert(0, registroCompra);
                lista1.getGrid().startEditing(0, 0);

                com_productointermedio2.clearValue();
                com_productointermedio2.focus();
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
//            tex_descuentoBs.setValue("");
//            tex_descuentoPorcentaje.setValue("");
        }
        Float total = new Float(0);
        for (int i = 0; i < grid.getStore().getRecords().length; i++) {
            total += grid.getStore().getRecords()[i].getAsFloat("preciototal");
        }
//        tex_montoTotal.setValue(total.toString());
//        tex_montoCuenta.setValue(total.toString());

    }

    public Panel getPanel() {
        return panel;
    }

    private void limpiarVentanaVenta() {
        lista.LimpiarGrid();
        lista1.LimpiarGrid();
        tea_observaciones.setValue("");
        com_Proceso.clearValue();
        com_producto.clearValue();
        com_productointermedio1.clearValue();
        com_productointermedio2.clearValue();
        com_productoProc.clearValue();
        com_operador.clearValue();
        com_materia.clearValue();
        tex_id.setValue("");
        tex_salida.setValue("");
        tex_produccion.setValue("");
        tex_Orden.setValue("");
        tex_Orden.setReadOnly(false);
    }

    private void initValues() {

        SimpleStore clientesStore = new SimpleStore(new String[]{"id", "nombre"}, procesoM);
        clientesStore.load();
        com_Proceso.setMinChars(1);
        com_Proceso.setFieldLabel("Proceso");
        com_Proceso.setStore(clientesStore);
        com_Proceso.setDisplayField("nombre");
        com_Proceso.setValueField("id");
//        com_Proceso.setValue("");
//        com_Proceso.setForceSelection(true);
        com_Proceso.setMode(ComboBox.LOCAL);
        com_Proceso.setEmptyText("Buscar Proceso");
        com_Proceso.setLoadingText("buscando...");
        com_Proceso.setTypeAhead(true);
//        com_Proceso.disable();
//        com_Proceso.setSelectOnFocus(true);
        com_Proceso.setHideTrigger(true);

        SimpleStore productosStore = new SimpleStore(new String[]{"id", "detalle"}, productoM);
        productosStore.load();
        com_producto.setMinChars(1);
        com_producto.setFieldLabel("Producto");
        com_producto.setStore(productosStore);
        com_producto.setDisplayField("detalle");
        com_producto.setValueField("id");
//        com_producto.setForceSelection(true);
        com_producto.setMode(ComboBox.LOCAL);
        com_producto.setEmptyText("Buscar producto");
        com_producto.setLoadingText("buscando...");
        com_producto.setTypeAhead(true);
//        com_producto.setSelectOnFocus(true);
        com_producto.setHideTrigger(true);

        SimpleStore productosOrdenStore = new SimpleStore(new String[]{"id", "detalle"}, productointermedioM);
        productosOrdenStore.load();
        com_productoProc.setStore(productosOrdenStore);
        com_productoProc.setMinChars(1);
        com_productoProc.setFieldLabel("Producto Terminado");
        com_productoProc.setDisplayField("detalle");
        com_productoProc.setValueField("id");
        com_productoProc.setMode(ComboBox.LOCAL);
        com_productoProc.setEmptyText("Buscar producto Orden Produccion");
        com_productoProc.setLoadingText("buscando...");
        com_productoProc.setTypeAhead(true);
        com_productoProc.disable();
        com_productoProc.setHideTrigger(true);


        SimpleStore productosIntermedioStore = new SimpleStore(new String[]{"id", "detalle"}, productointermedioM);
        productosIntermedioStore.load();
        com_productointermedio1.setMinChars(1);
        com_productointermedio1.setFieldLabel("Producto Intermedios");
        com_productointermedio1.setStore(productosOrdenStore);
        com_productointermedio1.setDisplayField("detalle");
        com_productointermedio1.setValueField("id");
//        com_productoProc.setForceSelection(true);
        com_productointermedio1.setMode(ComboBox.LOCAL);
        com_productointermedio1.setEmptyText("Buscar producto Intermedio");
        com_productointermedio1.setLoadingText("buscando...");
        com_productointermedio1.setTypeAhead(true);
        com_productointermedio1.disable();
        com_producto.setSelectOnFocus(true);
        com_productointermedio1.setHideTrigger(true);



        com_productointermedio2.setMinChars(1);
        com_productointermedio2.setFieldLabel("Producto Intermedios");
        com_productointermedio2.setStore(productosOrdenStore);
        com_productointermedio2.setDisplayField("detalle");
        com_productointermedio2.setValueField("id");
//        com_productoProc.setForceSelection(true);
        com_productointermedio2.setMode(ComboBox.LOCAL);
        com_productointermedio2.setEmptyText("Buscar producto Intermedio");
        com_productointermedio2.setLoadingText("buscando...");
        com_productointermedio2.setTypeAhead(true);
        com_productointermedio2.disable();
        com_producto.setSelectOnFocus(true);
        com_productointermedio2.setHideTrigger(true);

        responsablesStore = new SimpleStore(new String[]{"id", "nombre"}, responsablesM);
        responsablesStore.reload();

        com_operador.setMinChars(1);
        com_operador.setFieldLabel("Encargado");
        com_operador.setStore(responsablesStore);
        com_operador.setDisplayField("nombre");
        com_operador.setValueField("id");
//        com_responsables.setForceSelection(true);
        com_operador.setMode(ComboBox.LOCAL);
        com_operador.setEmptyText("Buscar Encargado");
        com_operador.setLoadingText("buscando...");
        com_operador.setTypeAhead(true);
//        com_responsables.disable();
//        com_responsables.setSelectOnFocus(true);
        com_operador.setHideTrigger(true);
        tex_id.setValue("");
        tex_salida.setValue("");
        tex_Orden.setValue("");

        SimpleStore empresasStore = new SimpleStore(new String[]{"id", "detalle"}, materiaM);
        empresasStore.load();
        com_materia.setMinChars(1);
        com_materia.setFieldLabel("Materia");
        com_materia.setStore(empresasStore);
        com_materia.setDisplayField("detalle");
        com_materia.setValueField("id");
        com_materia.setForceSelection(true);
        com_materia.setMode(ComboBox.LOCAL);
        com_materia.setEmptyText("Buscar Materia");
        com_materia.setLoadingText("buscando...");
        com_materia.setTypeAhead(true);
//        com_empresa.disable();
//        com_materia.setSelectOnFocus(true);
        com_materia.setHideTrigger(true);

    }

    private void initValues2() {

        SimpleStore proveedoresStore = new SimpleStore(new String[]{"idcliente", "nombre"}, procesoM);
        proveedoresStore.load();
        com_Proceso.setMinChars(1);
        com_Proceso.setFieldLabel("Cliente");
        com_Proceso.setStore(proveedoresStore);
        com_Proceso.setDisplayField("nombre");
        com_Proceso.setValueField("idcliente");
        com_Proceso.setForceSelection(true);
        com_Proceso.setMode(ComboBox.LOCAL);
        com_Proceso.setLoadingText("buscando...");
        com_Proceso.setTypeAhead(true);
        com_Proceso.setSelectOnFocus(true);
        com_Proceso.setHideTrigger(true);
        com_Proceso.setValue(orden[0][3].toString());


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

        SimpleStore responsablesStore = new SimpleStore(new String[]{"id", "nombre"}, responsablesM);
        responsablesStore.load();
        com_operador.setMinChars(1);
        com_operador.setFieldLabel("Responsable");
        com_operador.setStore(responsablesStore);
        com_operador.setDisplayField("nombre");
        com_operador.setValueField("id");
        com_operador.setForceSelection(true);
        com_operador.setMode(ComboBox.LOCAL);
        com_operador.setEmptyText("Buscar responsable");
        com_operador.setLoadingText("buscando...");
        com_operador.setTypeAhead(true);
        com_operador.setSelectOnFocus(true);
        com_operador.setHideTrigger(true);
        com_operador.setValue(orden[0][5].toString());

        SimpleStore empresasStore = new SimpleStore(new String[]{"idempresa", "nombre"}, materiaM);
        empresasStore.load();
        com_materia.setMinChars(1);
        com_materia.setFieldLabel("Empresa");
        com_materia.setStore(empresasStore);
        com_materia.setDisplayField("nombre");
        com_materia.setValueField("idempresa");
        com_materia.setForceSelection(true);
        com_materia.setMode(ComboBox.LOCAL);
        com_materia.setEmptyText("Buscar Empresa");
        com_materia.setLoadingText("buscando...");
        com_materia.setTypeAhead(true);
//        com_empresa.disable();
        com_materia.setSelectOnFocus(true);
        com_materia.setHideTrigger(true);
        com_materia.setValue(orden[0][4].toString());

//        dat_fecharecepcion.setValue(orden[0][1].toString());
        dat_fechaentrega.setValue(orden[0][2].toString());
//        tex_descuentoBs.setValue(orden[0][9].toString());
//        tex_montoTotal.setValue(orden[0][6].toString());
//        tex_montoCuenta.setValue(orden[0][7].toString());
//        tex_saldo.setValue(orden[0][8].toString());
//        tex_numeroDocumento.setValue(orden[0][1].toString());
        tea_observaciones.setValue(orden[0][9].toString());
        tex_id.setValue(orden[0][0].toString());

    }

    public void aniadirgrid() {

        SimpleStore ordenProductos = new SimpleStore(new String[]{"id", "unidad", "cantidad", "preciounitario", "preciototal", "detalle"}, detalles);
        ordenProductos.load();
        Record[] dt = ordenProductos.getRecords();
        lista.getStore().insert(0, dt);
//        Window.alert("entro al for para insertar  4 ");
    }

    public void CargarStore(Object[][] productoOrden, Object[][] ProductoIntermedio) {
        this.productointermedioM = ProductoIntermedio;
        this.productoordenM = productoOrden;
    }

    private void recalcular(boolean desc) {

//        totalTotal = new Float(tex_montoTotal.getText());
//        montocuenta = new Float(tex_montoCuenta.getText());
        if (desc == true) {
//            descPorcentaje = new Float(tex_descuentoPorcentaje.getText());
            saldo = (totalTotal - montocuenta);
//            tex_saldo.setValue(saldo);
        }
//        } else {
//            descuentobss = new Float(tex_descuentoBs.getText());
//            if (descuentobss == 0) {
//                descPorcentaje = new Float(0);
//            } else {
//                descPorcentaje = (descuentobss * 100) / totalTotal;
//            }
////            tex_descuentoPorcentaje.setValue(descPorcentaje.toString());
//        }

//        totalpagar = totalTotal - saldo;
//        tex_montoCuenta.setValue(totalpagar.toString());
//        tex_montoCuenta.focus();
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

