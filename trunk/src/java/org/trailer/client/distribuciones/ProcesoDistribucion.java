/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.distribuciones;

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
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.layout.HorizontalLayout;
import com.gwtext.client.widgets.layout.TableLayout;
import com.gwtext.client.widgets.layout.TableLayoutData;
import java.util.Date;
import org.trailer.client.MainEntryPoint;
import org.trailer.client.util.Conector;
import org.trailer.client.util.KMenu;
import org.trailer.client.util.ReporteMediaCartaChorroWindow;
import org.trailer.client.util.Utils;

/**
 *
 * @author example
 */
public class ProcesoDistribucion extends Panel {

    private final int ANCHO = 1000;
    private final int ALTO = 540;
    private Panel panel;
    private ComboBox com_items;
    private ComboBox com_encargado;
    private ComboBox com_itemguardados;
    private TextField tex_cantidad;
    private TextField tex_op;
    private TextField tex_totalmaterial;
    private TextArea tea_observacion;
    private DateField dat_fecha;
    private NumberField tex_totalitems;
    private ListaItem lista;
    private ListaMaterialItems lista1;
    boolean respuesta = false;
    private Object[][] clienteM;
    private Object[][] productoM;
    private Object[][] ciudadM;
    private Button but_aceptar;
    private Button but_cancelar;
    private Button but_eliminaritem;
    String selecionado = "";
    private Float totalItems;
    private Float totalMaterial;
    private Button but_nuevo;
    private Button but_guardaritem;
    private ComboBox com_malmacen;
    private Object[][] items;
    private Object[][] encargados;
    private Object[][] mpalmacen;
    private Object[][] itemsguardados;
    private KMenu kmenu;
    private MainEntryPoint pan;
    private EventObject e;
    private Button but_duplicarItem;
    private Button but_eliminarmaterial;
    private Button but_guardarItemMaterial;

    public ProcesoDistribucion(KMenu kmenu, MainEntryPoint panel) {

        this.kmenu = kmenu;
        this.pan = panel;
        this.setClosable(true);
        this.setId("TPfun8000");
        setIconCls("tab-icon");
        setAutoScroll(false);
        this.items = new Object[][]{new Object[]{"0", "No Existe Item o Producsot", "0"}};
        setTitle("Proceso Distribucion");
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
        pan_norte.setHeight(120);
        pan_norte.setPaddings(5);
        pan_norte.setAutoWidth(true);

        Panel pan_sud = new Panel();
        pan_sud.setLayout(new TableLayout(2));
        pan_sud.setBaseCls("x-plain");
        pan_sud.setHeight(90);
        pan_sud.setPaddings(5);
        lista = new ListaItem();
        lista.onModuleLoad();
        lista1 = new ListaMaterialItems();
        lista1.onModuleLoad();

        Panel pan_centro = new Panel();
        pan_centro.setLayout(new TableLayout(2));
        pan_centro.setBaseCls("x-plain");
        pan_centro.setPaddings(5, 5, 5, 5);
        pan_centro.add(new PaddedPanel(lista.getPanel(), 0, 0, 13, 0));
        pan_centro.add(new PaddedPanel(lista1.getPanel(), 0, 0, 13, 0));

        FormPanel for_panel1 = new FormPanel();
        for_panel1.setBaseCls("x-plain");
//        for_panel1.setWidth(250);
//        for_panel1.setLabelWidth(120);
        com_items = new ComboBox("Items", "items", 200);
        tex_op = new TextField("Op-", "op", 200);
        com_encargado = new ComboBox("Encargado", "encargado", 200);


        for_panel1.add(tex_op);
        for_panel1.add(com_items);
        for_panel1.add(com_encargado);



        FormPanel for_panel2 = new FormPanel();
        for_panel2.setBaseCls("x-plain");
        for_panel2.setWidth(360);
        for_panel2.setBorder(true);
        for_panel2.setTitle("Detalle Items");
        for_panel2.setLabelWidth(100);
//        for_panel2.setWidth(250);
//        for_panel2.setLabelWidth(120);
        tex_cantidad = new TextField("Cantidad (ref:0)", "cantidad", 200);
        dat_fecha = new DateField("Fecha Entrega", "Y-m-d", 200);
        but_guardaritem = new Button("Guardar Items");
//        tex_numeroDocumento.setWidth(160);
        for_panel2.add(tex_cantidad);
        for_panel2.add(dat_fecha);
        for_panel2.add(but_guardaritem);


        FormPanel for_panel3 = new FormPanel();
        for_panel3.setBaseCls("x-plain");
        for_panel3.setWidth(360);
        for_panel3.setBorder(true);
        for_panel3.setTitle("Material de Items");
        for_panel3.setLabelWidth(100);
//        for_panel3.setWidth(250);
//        for_panel3.setLabelWidth(120);



        com_malmacen = new ComboBox("Mp Almacen", "mpalmacen", 200);

        com_itemguardados = new ComboBox("Items Guardados", "itemsguardados", 200);
        but_duplicarItem = new Button("Duplicar Items");
        but_guardarItemMaterial = new Button("AÑADIR Material a Item");
        Panel pan_botonescliente = new Panel();
        pan_botonescliente.setLayout(new HorizontalLayout(2));
        pan_botonescliente.setBaseCls("x-plain");

        pan_botonescliente.add(but_duplicarItem);
        pan_botonescliente.add(but_guardarItemMaterial);
        for_panel3.add(com_malmacen);
        for_panel3.add(com_itemguardados);
        for_panel3.add(new PaddedPanel(pan_botonescliente, 5, 5, 5, 5), new TableLayoutData(2));

//        for_panel3.add(but_duplicarItem);






        pan_norte.add(new PaddedPanel(for_panel1, 0, 5, 5, 0));
        pan_norte.add(new PaddedPanel(for_panel2, 0, 5, 5, 0));
        pan_norte.add(new PaddedPanel(for_panel3, 0, 5, 5, 0));


        FormPanel for_panel4 = new FormPanel();
        for_panel4.setBaseCls("x-plain");
        for_panel4.setWidth(600);
        for_panel4.setLabelWidth(70);
        tex_totalitems = new NumberField("total Items", "totalitem", 200);
        tex_totalitems.setHeight(30);
        tex_totalitems.setValue("0");
        tex_totalitems.setReadOnly(true);
//        tex_montoTotal.disable();
        tex_totalitems.setCls("grande");

        for_panel4.add(tex_totalitems);


        FormPanel for_panel5 = new FormPanel();
        for_panel5.setBaseCls("x-plain");
        for_panel5.setWidth(600);
        for_panel5.setLabelWidth(70);
        tex_totalmaterial = new NumberField("Total Material", "totalmaterial", 200);
        tex_totalmaterial.setHeight(30);
        tex_totalmaterial.setValue("0");
        tex_totalmaterial.setReadOnly(true);
//        tex_montoTotal.disable();
        tex_totalmaterial.setCls("grande");
        for_panel5.add(tex_totalmaterial);



        Panel pan_botones = new Panel();
        pan_botones.setLayout(new HorizontalLayout(4));
        pan_botones.setBaseCls("x-plain");
        //       pan_botones.setHeight(40);
        but_aceptar = new Button("Guardar");
        but_cancelar = new Button("Limpiar");
        but_eliminaritem = new Button("Eliminar Items");

        but_eliminarmaterial = new Button("Eliminar Material Items");

        pan_botones.add(but_aceptar);
        pan_botones.add(but_cancelar);
        pan_botones.add(but_eliminaritem);
        pan_botones.add(but_eliminarmaterial);



        pan_sud.add(new PaddedPanel(for_panel4, 5, 300, 13, 0));
////        pan_sud.add(new PaddedPanel(for_panel5, 0, 0, 13, 10));
        pan_sud.add(new PaddedPanel(for_panel5, 5, 20, 13, 0));

        pan_sud.add(new PaddedPanel(pan_botones, 5, 350, 10, 10), new TableLayoutData(3));



        pan_borderLayout.add(pan_norte, new BorderLayoutData(RegionPosition.NORTH));
        pan_borderLayout.add(pan_centro, new BorderLayoutData(RegionPosition.CENTER));
        pan_borderLayout.add(pan_sud, new BorderLayoutData(RegionPosition.SOUTH));
        add(pan_borderLayout);
//        if (d) {
////            initValues2();
////            aniadirgrid();
//        } else {
        initValues();
//        }
        addListeners();

    }

    public void createCompra() {


        String codigo = tex_op.getValueAsString();

        String fecha;
        Record[] records;
        Record[] records1;

        records = lista.getStore().getRecords();
        records1 = lista1.getStore().getRecords();
        JSONArray productos = new JSONArray();
        JSONArray productos1 = new JSONArray();
        JSONObject productoObject;
        JSONObject productoObject1;

        JSONObject compraObject = new JSONObject();


        compraObject.put("op", new JSONString(codigo));

        for (int i = 0; i < lista1.getStore().getRecords().length; i++) {
// String[] nombreComlumns = {"id", "iditems", "idencargado", "uds", "detalle", "total", "fecha"};
            productoObject1 = new JSONObject();
            productoObject1.put("id", new JSONString(records1[i].getAsString("id")));
            productoObject1.put("iditems", new JSONString(records1[i].getAsString("iditems")));
            productoObject1.put("idencargado", new JSONString(records1[i].getAsString("idencargado")));
            productoObject1.put("uds", new JSONString(records1[i].getAsString("uds")));
            productoObject1.put("detalle", new JSONString(records1[i].getAsString("detalle")));
            productoObject1.put("total", new JSONString(records1[i].getAsString("total")));
            productoObject1.put("fecha", new JSONString(records1[i].getAsString("fecha")));




            productos1.set(i, productoObject1);
            productoObject1 = null;
        }
        JSONObject resultado = new JSONObject();
        resultado.put("distribucion", compraObject);
        resultado.put("detallesmaterial", productos1);

        String datos = "resultado=" + resultado.toString();

        Utils.setErrorPrincipal("registrando los cortes", "cargar");
        String url = "php/Distribucion.php?funcion=GuardarNuevaDistribucion&" + datos;
        final Conector conec = new Conector(url, false, "GET");
        try {
            conec.getRequestBuilder().sendRequest(datos, new RequestCallback() {

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
//                            String nume = Utils.getStringOfJSONObject(jsonObject, "numero");
//                            tex_cliente.setValue(nume);
//                            String idcoti = Utils.getStringOfJSONObject(jsonObject, "idcotizacion");
//                            String enlTemp = "funcion=cotizacionHTML&id=" + idcoti;
//                            verReporte(enlTemp);
                            limpiarVentanaVenta();
                            MessageBox.alert(mensajeR);
//                            kmenu.seleccionarOpcionRemove(null, "fun7000", e, ProcesoDistribucion.this);


//                               compra.getGrid().getStore().reload();
                        } else {

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
//
//        //**************************************************
//        //************* BOTON CANCELAR   *******************
//        //**************************************************
        but_cancelar.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                limpiarVentanaVenta();
            }
        });
//
//        //**************************************************
//        //************* BOTON ACEPTAR *******************
//        //**************************************************
        but_aceptar.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                createCompra();
            }
        });
//
//
        tex_op.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
//                    String codigoProducto = field.getValueAsString().trim();
                    String ordenproduccion = tex_op.getValueAsString().trim();
                    if (!ordenproduccion.isEmpty()) {
//                        com_cliente.setEditable(false);
//                        MessageBox.alert(op);
                        limpiarVentanaVenta();
                        cargarDistribucion(ordenproduccion);
                        com_items.clearValue();
                        tex_op.setValue(ordenproduccion);

                        com_encargado.clearValue();
                        com_itemguardados.clearValue();

//                        com_malmacen.clearValue();
//                        com_items.focus();
                    }
                }
            }
        });
        com_items.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    String idproductos = com_items.getValueAsString().trim();
                    if (!idproductos.isEmpty()) {
//                       

                        cargarItems(idproductos);
                    }
//                    MessageBox.alert(idproductos);

                }
            }
        });

        com_encargado.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    String idproductos = com_encargado.getValueAsString().trim();
                    String iditem = com_items.getValueAsString().trim();

                    if (!idproductos.isEmpty() && !iditem.isEmpty()) {
//

                        cargarItemsEncargado(idproductos, iditem);
                    } else {
                        MessageBox.alert("Seleccione un Items de OP");
                    }
//                    MessageBox.alert(idproductos);

                }
            }
        });
        but_guardaritem.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                String encargado = com_encargado.getValueAsString().trim();
                String items = com_items.getValueAsString().trim();
                String cantidad = tex_cantidad.getValueAsString().trim();
                String fecha = DateUtil.format(dat_fecha.getValue(), "Y-m-d");

                if ((!encargado.isEmpty()) && (!items.isEmpty()) && (!cantidad.isEmpty()) && (!fecha.isEmpty())) {
                    cargarProducto(encargado, items, cantidad, fecha);


                } else {
                    MessageBox.alert("Tiene que seleccionar un Item , encargo . Llenar Cantidad y plazo de Entrega");
                }
            }

            private void GuardarItems(final String item, final String encargado, final String nombreitem, final String nombreencargado, final String cantidad, final String fecha) {
                String enlace = "php/Distribucion.php?funcion=GuardarItemDistribucion&iditem=" + item + "&idencargado=" + encargado + "&detalle=" + nombreitem + "&encargado=" + nombreencargado + "&cantidad=" + cantidad + "&fecha=" + fecha;
                Utils.setErrorPrincipal("Cargando", "cargar");
                final Conector conec = new Conector(enlace, false);
                try {
                    conec.getRequestBuilder().sendRequest("asdf", new RequestCallback() {

                        public void onResponseReceived(Request request, Response response) {
                            String data = response.getText();
                            JSONValue jsonValue = JSONParser.parse(data);
                            JSONObject jsonObject;
                            if ((jsonObject = jsonValue.isObject()) != null) {
                                String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                                String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");
                                String id = Utils.getStringOfJSONObject(jsonObject, "id");
                                Record registroCompra = lista.getRecordDef().createRecord(new Object[]{
                                            id, encargado, nombreitem, nombreencargado, cantidad, fecha});

                                lista.getGrid().stopEditing();
                                lista.getGrid().getStore().insert(0, registroCompra);
                                lista.getGrid().startEditing(0, 0);
                                Float to = new Float(0);
                                for (int i = 0; i < lista.getStore().getRecords().length; i++) {
                                    to += lista.getStore().getRecords()[i].getAsFloat("cantidad");
                                }
                                tex_totalitems.setValue(to.toString());
                                Utils.setErrorPrincipal(mensajeR, "mensaje");

                            } else {
                                MessageBox.alert("no tiene respuesta");
                            }
                        }

                        public void onError(Request request, Throwable exception) {
                            //Window.alert("Ocurrio un error al conectar con el servidor ");
                            Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                        }
                    });
                } catch (RequestException ex) {
                    //Window.alert("Ocurrio un error al conectar con el servidor");
                    Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                }



            }

            private void cargarProducto(String encargado, String item, String cantidad, String fecha) {
                String nombreitem;
                String nombreencargado;
                SimpleStore producto = new SimpleStore(new String[]{"id", "nombre", "uds"}, items);
                producto.load();
                producto.filter("id", item);
                Record r = producto.getRecords()[0];

                nombreitem = r.getAsString("nombre");

                SimpleStore encargado1 = new SimpleStore(new String[]{"idempleado", "nombre"}, encargados);
                encargado1.load();
                encargado1.filter("idempleado", encargado);
                Record r1 = encargado1.getRecords()[0];
                nombreencargado = r1.getAsString("nombre");
//"id", "idencargado", "detalle", "encargado", "cantidad", "plazo"
                GuardarItems(item, encargado, nombreitem, nombreencargado, cantidad, fecha);




            }
        });
        but_guardarItemMaterial.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
//                MessageBox.alert("hola mundo");
                String idmaterial = com_malmacen.getValueAsString().trim();
                Record[] records = lista.getSelectionModel().getSelections();
                String iditem;
                String idenca;
                String cant;
                String det;
                String encar;
                Date f = new Date();
                String fechaent = DateUtil.format(f, "Y-m-d");
                if (records.length == 1) {
//                    "id", "idencargado", "detalle", "encargado", "cantidad", "plazo"
                    iditem = records[0].getAsString("id");
                    idenca = records[0].getAsString("idencargado");
                    cant = records[0].getAsString("cantidad");
                    encar = records[0].getAsString("encargado");
                    SimpleStore producto = new SimpleStore(new String[]{"id", "detalle", "unidad"}, mpalmacen);
                    producto.load();
                    producto.filter("id", idmaterial);
                    Record r = producto.getRecords()[0];
                    det = r.getAsString("detalle");
//                    {"id", "iditems", "idencargado", "uds", "detalle", "total", "fecha"}
                    Record registroCompra = lista1.getRecordDef().createRecord(new Object[]{
                                idmaterial, iditem, idenca, 0, det, 0, fechaent});

                    lista1.getGrid().stopEditing();
                    lista1.getGrid().getStore().insert(0, registroCompra);
                    lista1.getGrid().startEditing(0, 0);
                    com_malmacen.clearValue();
                    com_malmacen.focus();





                } else {
                    MessageBox.alert("No existe un item seleccionado y/o selecciono mas de uno.");
                }
            }
        });
        but_duplicarItem.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                String iddistribucion = com_itemguardados.getValueAsString().trim();
                Record[] records = lista.getSelectionModel().getSelections();
                String iditem;
                String idenca;

                if (records.length == 1) {
                    iditem = records[0].getAsString("id");
                    idenca = records[0].getAsString("idencargado");
                    cargarItemsMaterial(iddistribucion, iditem, idenca);

                } else {
                    MessageBox.alert("No existe un item seleccionado y/o selecciono mas de uno.");
                }
            }
        });
          but_eliminaritem.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = lista.getSelectionModel().getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("id");
                    MessageBox.confirm("Eliminar...", "Realmente desea eliminar Este Items . Se borraran todos los Materiales aÑadios y afectara almacen ??", new MessageBox.ConfirmCallback() {

                        public void execute(String btnID) {
                            if (btnID.equalsIgnoreCase("yes")) {

                                //eliminar
                                String enlace = "php/Distribucion.php?funcion=eliminarItemDistribucion&id=" + selecionado;
                                Utils.setErrorPrincipal("Eliminando...", "cargar");
                                final Conector conec = new Conector(enlace, false);
                                try {
                                    conec.getRequestBuilder().sendRequest("asdf", new RequestCallback() {

                                        public void onResponseReceived(Request request, Response response) {
                                            String data = response.getText();
                                            JSONValue jsonValue = JSONParser.parse(data);
                                            JSONObject jsonObject;
                                            if ((jsonObject = jsonValue.isObject()) != null) {
                                                String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                                                String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");
                                                if (errorR.equalsIgnoreCase("true")) {
                                                    Utils.setErrorPrincipal(mensajeR, "mensaje");
                                                    lista.getGrid().stopEditing();
                                                    lista.getStore().remove(lista.getSelectionModel().getSelected());
                                                    lista.getGrid().startEditing(0, 0);
                                                    lista1.LimpiarGrid();

                                                } else {
                                                    //Window.alert(mensajeR);
                                                    Utils.setErrorPrincipal(mensajeR, "error");
                                                }
                                            }
                                        }

                                        public void onError(Request request, Throwable exception) {
                                            //Window.alert("Ocurrio un error al conectar con el servidor ");
                                            Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                                        }
                                    });
                                } catch (RequestException ex) {
                                    //Window.alert("Ocurrio un error al conectar con el servidor");
                                    Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                                }
                            }
                        }
                    });
                } else {
                    MessageBox.alert("No hay venta selecionado para editar y/o selecciono mas de uno.");
                }
                but_eliminaritem.setPressed(false);
            }
        });
         but_eliminarmaterial.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = lista1.getSelectionModel().getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("id");
                    MessageBox.confirm("Eliminar...", "Realmente desea eliminar Este Material y Esto Afectara almacen ??", new MessageBox.ConfirmCallback() {

                        public void execute(String btnID) {
                            if (btnID.equalsIgnoreCase("yes")) {

                                //eliminar
                                String enlace = "php/Distribucion.php?funcion=eliminarItemDistribucionMaterial&id=" + selecionado;
                                Utils.setErrorPrincipal("Eliminando...", "cargar");
                                final Conector conec = new Conector(enlace, false);
                                try {
                                    conec.getRequestBuilder().sendRequest("asdf", new RequestCallback() {

                                        public void onResponseReceived(Request request, Response response) {
                                            String data = response.getText();
                                            JSONValue jsonValue = JSONParser.parse(data);
                                            JSONObject jsonObject;
                                            if ((jsonObject = jsonValue.isObject()) != null) {
                                                String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                                                String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");
                                                if (errorR.equalsIgnoreCase("true")) {
                                                    Utils.setErrorPrincipal(mensajeR, "mensaje");
                                                    lista1.getGrid().stopEditing();
                                                    lista1.getStore().remove(lista1.getSelectionModel().getSelected());
                                                    lista1.getGrid().startEditing(0, 0);

                                                } else {
                                                    //Window.alert(mensajeR);
                                                    Utils.setErrorPrincipal(mensajeR, "error");
                                                }
                                            }
                                        }

                                        public void onError(Request request, Throwable exception) {
                                            //Window.alert("Ocurrio un error al conectar con el servidor ");
                                            Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                                        }
                                    });
                                } catch (RequestException ex) {
                                    //Window.alert("Ocurrio un error al conectar con el servidor");
                                    Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                                }
                            }
                        }
                    });
                } else {
                    MessageBox.alert("No hay venta selecionado para editar y/o selecciono mas de uno.");
                }
                but_eliminarmaterial.setPressed(false);
            }
        });

//        //**************************************************
//        //*************CALCULAR TOTAL DE COMPRA ************
//        //**************************************************
        lista.getGrid().addGridRowListener(new GridRowListenerAdapter() {

            @Override
            public void onRowDblClick(GridPanel grid, int rowIndex, EventObject e) {
//                Window.alert("En contruccion: aqui saldra la informacion del rol en detalle");
                Record[] records = lista.getSelectionModel().getSelections();


                selecionado = records[0].getAsString("id");
//                String enlTemp = "funcion=reporteClienteHTML&idcliente=" + selecionado;
                String enlace = "php/Distribucion.php?funcion=ListarDetalleDistribucion&id=" + selecionado;
                Utils.setErrorPrincipal("Cargando", "cargar");
                lista1.LimpiarGrid();
                final Conector conec = new Conector(enlace, false);
                try {
                    conec.getRequestBuilder().sendRequest("asdf", new RequestCallback() {

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
                                        Object[][] productos = Utils.getArrayOfJSONObject(marcaO, "productoM", new String[]{"id", "iditems", "idencargado", "uds", "detalle", "total", "fecha"});
                                        cargarGrillasMaterial(productos);
//                                     MessageBox.alert("Cargo producto"+prod);

                                    } else {
                                        MessageBox.alert("No Hay datos en la consulta");
                                    }
                                } else {
                                    //Window.alert(mensajeR);
                                    Utils.setErrorPrincipal(mensajeR, "error");
                                }
                            }
                        }

                        public void onError(Request request, Throwable exception) {
                            //Window.alert("Ocurrio un error al conectar con el servidor ");
                            Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                        }
                    });
                } catch (RequestException ex) {
                    //Window.alert("Ocurrio un error al conectar con el servidor");
                    Utils.setErrorPrincipal("Ocurrio un error al conectar con el servidor", "error");
                }

//                    Window.alert(enlTemp);
            }
        });
        lista1.getGrid().addEditorGridListener(new EditorGridListenerAdapter() {

            @Override
            public boolean doBeforeEdit(GridPanel grid, Record record, String field, Object value, int rowIndex, int colIndex) {
                calcularSubTotal(grid, record, field, value, value, rowIndex, colIndex);
                return super.doBeforeEdit(grid, record, field, value, rowIndex, colIndex);
            }

            @Override
            public void onAfterEdit(GridPanel grid, Record record,
                    String field, Object newValue,
                    Object oldValue,
                    int rowIndex,
                    int colIndex) {
                calcularSubTotal(grid, record, field, newValue, oldValue, rowIndex, colIndex);
            }
        });
//
    }

    private void cargarDistribucion(final String op) {
        String enlace = "php/Distribucion.php?funcion=CargarPanelDistribucion&op=" + op;
        Utils.setErrorPrincipal("Cargando parametros...", "cargar");
        final Conector conec = new Conector(enlace, false);
        {

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

                                JSONValue marcaV = jsonObject.get("resultado");
                                JSONObject marcaO;
                                if ((marcaO = marcaV.isObject()) != null) {


                                    Object[][] itemsM1 = Utils.getArrayOfJSONObject(marcaO, "itemM", new String[]{"id", "nombre", "uds"});
                                    SimpleStore prodintStore = new SimpleStore(new String[]{"id", "nombre", "uds"}, itemsM1);
                                    prodintStore.reload();
                                    com_items.setStore(prodintStore);
                                    items = itemsM1;


                                    Object[][] mpalmacen1 = Utils.getArrayOfJSONObject(marcaO, "mpalmacenM", new String[]{"id", "detalle"});
//
                                    SimpleStore prodintStore2 = new SimpleStore(new String[]{"id", "detalle"}, mpalmacen1);
                                    prodintStore2.reload();
                                    com_malmacen.setStore(prodintStore2);
                                    mpalmacen = mpalmacen1;

                                    Object[][] encargadoM = Utils.getArrayOfJSONObject(marcaO, "encargadoM", new String[]{"idempleado", "nombre"});
                                    SimpleStore prodintStore1 = new SimpleStore(new String[]{"idempleado", "nombre"}, encargadoM);
                                    prodintStore1.reload();
                                    com_encargado.setStore(prodintStore1);
                                    encargados = encargadoM;

                                    Object[][] itemsGuardadosM = Utils.getArrayOfJSONObject(marcaO, "itemsGuardadosM", new String[]{"id", "detalle"});
                                    SimpleStore prodintStore3 = new SimpleStore(new String[]{"id", "detalle"}, itemsGuardadosM);
                                    prodintStore3.reload();
                                    com_itemguardados.setStore(prodintStore3);
                                    itemsguardados = itemsGuardadosM;
                                    com_items.focus();



//                                    CargarStore(itemsM, mpcompra, mpalmacen1);
//                                    com_productointermedio2.setStore(prodintStore);

//                                    tex_id.setValue(op);


//MessageBox.alert("No Hay datos en la consult111111111111a");

                                } else {
                                    MessageBox.alert("No Hay datos en la consulta");
                                }
                            } else {
//                                MessageBox.alert("No Hay datos en la consulta");
                                Utils.setErrorPrincipal(mensajeR, "error");
                            }
                        }

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

    private void cargarItems(final String op) {
        String enlace = "php/Distribucion.php?funcion=CargarDetalleItems&id=" + op;
        Utils.setErrorPrincipal("Cargando parametros...", "cargar");
        final Conector conec = new Conector(enlace, false);
        {

            try {
                conec.getRequestBuilder().sendRequest(null, new RequestCallback() {

                    private String uds;

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
                                    lista.LimpiarGrid();
                                    lista1.LimpiarGrid();
                                    dat_fecha.setValue("");
                                    tex_cantidad.setValue("");
                                    tex_totalitems.setValue("0");
                                    tex_totalmaterial.setValue("0");
                                    com_encargado.clearValue();
                                    com_malmacen.clearValue();
                                    Object[][] detalles = Utils.getArrayOfJSONObject(marcaO, "detalleM", new String[]{"id", "idencargado", "detalle", "encargado", "cantidad", "plazo"});
                                    tex_cantidad.setFieldLabel("cantidad ref:100");
                                    SimpleStore producto = new SimpleStore(new String[]{"id", "nombre", "uds"}, items);
                                    producto.load();
                                    producto.filter("id", op);
                                    Record r = producto.getRecords()[0];


                                    uds = r.getAsString("uds");
                                    tex_cantidad.setFieldLabel("Cantidad (ref:" + uds + ")");
                                    cargarGrillas(detalles);
                                } else {
                                    MessageBox.alert("No Hay datos en la consulta");
                                }
                            }
                        }

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

    private void cargarItemsMaterial(final String id, final String iditem, final String idencar) {
        String enlace = "php/Distribucion.php?funcion=CargarDetalleItemsMaterial&id=" + id + "&iditm=" + iditem + "&idencagado=" + idencar;
        Utils.setErrorPrincipal("Cargando parametros...", "cargar");
        final Conector conec = new Conector(enlace, false);
        {

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

                                JSONValue marcaV = jsonObject.get("resultado");
                                JSONObject marcaO;
                                if ((marcaO = marcaV.isObject()) != null) {

                                    lista1.LimpiarGrid();


                                    com_malmacen.clearValue();
                                    Object[][] productos = Utils.getArrayOfJSONObject(marcaO, "productoM", new String[]{"id", "iditems", "idencargado", "uds", "detalle", "total", "fecha"});
                                    cargarGrillasMaterial(productos);

//                                    cargarGrillas(detalles);
                                } else {
                                    MessageBox.alert("No Hay datos en la consulta");
                                }
                            }
                        }

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

    private void cargarItemsEncargado(final String idencar, final String iditem) {
        String enlace = "php/Distribucion.php?funcion=CargarDetalleItemsEncargado&idencargado=" + idencar + "&iditem=" + iditem;
        Utils.setErrorPrincipal("Cargando parametros...", "cargar");
        final Conector conec = new Conector(enlace, false);
        {

            try {
                conec.getRequestBuilder().sendRequest(null, new RequestCallback() {

                    private String uds;

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
                                    lista.LimpiarGrid();
                                    lista1.LimpiarGrid();
                                    dat_fecha.setValue("");
                                    tex_cantidad.setValue("");
                                    tex_totalitems.setValue("0");
                                    tex_totalmaterial.setValue("0");

                                    com_malmacen.clearValue();
                                    Object[][] detalles = Utils.getArrayOfJSONObject(marcaO, "detalleM", new String[]{"id", "idencargado", "detalle", "encargado", "cantidad", "plazo"});
                                    tex_cantidad.setFieldLabel("cantidad ref:100");
                                    SimpleStore producto = new SimpleStore(new String[]{"id", "nombre", "uds"}, items);
                                    producto.load();
                                    producto.filter("id", iditem);
                                    Record r = producto.getRecords()[0];


                                    uds = r.getAsString("uds");
                                    tex_cantidad.setFieldLabel("Cantidad (ref:" + uds + ")");
                                    cargarGrillas(detalles);
                                } else {
                                    MessageBox.alert("No Hay datos en la consulta");
                                }
                            }
                        }

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

    public void cargarGrillas(Object[][] entradas) {

        SimpleStore ordenProductos = new SimpleStore(new String[]{"id", "idencargado", "detalle", "encargado", "cantidad", "plazo"}, entradas);
        ordenProductos.reload();
        Record[] dt = ordenProductos.getRecords();
        lista.LimpiarGrid();
        lista.getStore().insert(0, dt);
        Float to = new Float(0);
        for (int i = 0; i < lista.getStore().getRecords().length; i++) {
            to += lista.getStore().getRecords()[i].getAsFloat("cantidad");
        }
        tex_totalitems.setValue(to.toString());



    }

    private void cargarGrillasMaterial(Object[][] productos) {
        SimpleStore ordenProductosSalidas = new SimpleStore(new String[]{"id", "iditems", "idencargado", "uds", "detalle", "total", "fecha"}, productos);
        ordenProductosSalidas.reload();
        Record[] dt1 = ordenProductosSalidas.getRecords();
        lista1.LimpiarGrid();
        lista1.getStore().insert(0, dt1);

        Float to = new Float(0);
        for (int i = 0; i < lista1.getStore().getRecords().length; i++) {
            to += lista1.getStore().getRecords()[i].getAsFloat("total");
        }
        tex_totalmaterial.setValue(to.toString());
    }
//

    private void calcularSubTotal(GridPanel grid, Record record, String field, Object newValue, Object oldValue, int rowIndex, int colIndex) {

//String[] nombreComlumns = {"id", "idtela", "uds", "nombrei", "nombret", "tela", "hoja", "uds1", "total", "totalunidades"};
        Record[] records = lista.getSelectionModel().getSelections();
//         String cant = records[0].getAsString("cantidad");
        record.set("total", record.getAsFloat("uds") * records[0].getAsFloat("cantidad"));
        Float to = new Float(0);
        for (int i = 0; i < lista1.getStore().getRecords().length; i++) {
            to += lista1.getStore().getRecords()[i].getAsFloat("total");
        }
        tex_totalmaterial.setValue(to.toString());
//        record.set("totalunidades", record.getAsFloat("hoja") * record.getAsFloat("uds1"));




    }
//
//    public void resetCamposProveedor() {
//
//        com_items.reset();
//        com_items.focus();
//
//    }

    public Panel getPanel() {
        return panel;
    }

    private void limpiarVentanaVenta() {


        lista.LimpiarGrid();
        lista1.LimpiarGrid();
        tex_op.setValue("");
        dat_fecha.setValue("");
        tex_cantidad.setValue("");
        tex_totalitems.setValue("0");
        tex_totalmaterial.setValue("0");
        com_items.clearValue();
        com_encargado.clearValue();
        com_malmacen.clearValue();
        com_itemguardados.clearValue();
        tex_op.focus();
    }

    private void initValues() {


        com_items.setMinChars(1);
        com_items.setFieldLabel("Items");

        SimpleStore productosIntermedioStore = new SimpleStore(new String[]{"id", "nombre", "uds"}, items);
        productosIntermedioStore.load();
        SimpleStore productosIntermedioStore1 = new SimpleStore(new String[]{"id", "detalle"}, items);
        productosIntermedioStore1.load();

        com_items.setStore(productosIntermedioStore);

        com_items.setDisplayField("nombre");
        com_items.setValueField("id");
        com_items.setForceSelection(true);
        com_items.setMode(ComboBox.LOCAL);
        com_items.setEmptyText("Buscar Items");
        com_items.setLoadingText("buscando...");
        com_items.setTypeAhead(true);
        com_items.setSelectOnFocus(true);
        com_items.setHideTrigger(true);
        com_items.enable();



        com_encargado.setStore(productosIntermedioStore);
        com_encargado.setMinChars(1);
        com_encargado.setFieldLabel("Encargado");
        com_encargado.setDisplayField("nombre");
        com_encargado.setValueField("idempleado");
        com_encargado.setForceSelection(true);
        com_encargado.setMode(ComboBox.LOCAL);
        com_encargado.setEmptyText("Buscar Encargado");
        com_encargado.setLoadingText("buscando...");
        com_encargado.setTypeAhead(true);
        com_encargado.setSelectOnFocus(true);
        com_encargado.setHideTrigger(true);
        com_encargado.enable();


        com_malmacen.setMinChars(1);
        com_malmacen.setStore(productosIntermedioStore1);
        com_malmacen.setFieldLabel("MP Almacen");
        com_malmacen.setDisplayField("detalle");
        com_malmacen.setValueField("id");
        com_malmacen.setForceSelection(true);
        com_malmacen.setMode(ComboBox.LOCAL);
        com_malmacen.setEmptyText("Buscar materia prima");
        com_malmacen.setLoadingText("buscando...");
        com_malmacen.setTypeAhead(true);
        com_malmacen.setSelectOnFocus(true);
        com_malmacen.setHideTrigger(true);
        com_malmacen.enable();

        com_itemguardados.setMinChars(1);
        com_itemguardados.setStore(productosIntermedioStore1);
        com_itemguardados.setFieldLabel("Items Guardado");
        com_itemguardados.setDisplayField("detalle");
        com_itemguardados.setValueField("id");
        com_itemguardados.setForceSelection(true);
        com_itemguardados.setMode(ComboBox.LOCAL);
        com_itemguardados.setEmptyText("Buscar Item Guardado");
        com_itemguardados.setLoadingText("buscando...");
        com_itemguardados.setTypeAhead(true);
        com_itemguardados.setSelectOnFocus(true);
        com_itemguardados.setHideTrigger(true);
        com_itemguardados.enable();






    }

    public void CargarStore(Object[][] items, Object[][] mcompra, Object[][] malmacen) {
        this.items = items;
        this.encargados = mcompra;
        this.mpalmacen = malmacen;

    }

    public void CargarStoreMolde(Object[][] moldesM) {

        this.itemsguardados = moldesM;

    }

    private void verReporte(String enlace) {
        ReporteMediaCartaChorroWindow print = new ReporteMediaCartaChorroWindow(enlace);
        print.show();
    }
}
