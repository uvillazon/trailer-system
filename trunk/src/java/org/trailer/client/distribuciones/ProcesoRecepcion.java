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
public class ProcesoRecepcion extends Panel {

    private final int ANCHO = 1000;
    private final int ALTO = 540;
    private Panel panel;
    private ComboBox com_encargado;
    private TextField tex_cantidad;
    private TextField tex_op;
    private TextField tex_totalmaterial;
    private DateField dat_fecha;
    private NumberField tex_totalitems;
    private ListaItem lista;
    private ListaEntregaItems lista1;
    boolean respuesta = false;
    String selecionado = "";
    private Button but_guardaritem;
    private Object[][] encargados;
    private KMenu kmenu;
    private MainEntryPoint pan;
    private EventObject e;
    private Button but_eliminarEntrega;
    private Object[][] items;
    private Button but_limpiar;

    public ProcesoRecepcion(KMenu kmenu, MainEntryPoint panel) {

        this.kmenu = kmenu;
        this.pan = panel;
        this.setClosable(true);
        this.setId("TPfun8001");
        setIconCls("tab-icon");
        setAutoScroll(false);
        this.items = new Object[][]{new Object[]{"0", "No Existe Item o Producsot", "0"}};
        setTitle("Proceso De Recepcion de Trabajos");
        setLayout(new FitLayout());
        onModuleLoad(false);
    }

    public void onModuleLoad(boolean d) {


        Panel pan_borderLayout = new Panel();
        pan_borderLayout.setLayout(new BorderLayout());
        pan_borderLayout.setBaseCls("x-plain");

        Panel pan_norte = new Panel();
        pan_norte.setLayout(new TableLayout(2));
        pan_norte.setBaseCls("x-plain");
        pan_norte.setHeight(120);
        pan_norte.setPaddings(5);
        pan_norte.setAutoWidth(false);

        Panel pan_sud = new Panel();
        pan_sud.setLayout(new TableLayout(2));
        pan_sud.setBaseCls("x-plain");
        pan_sud.setHeight(90);
        pan_sud.setPaddings(5);
        lista = new ListaItem();
        lista.onModuleLoad();
        lista1 = new ListaEntregaItems();
        lista1.onModuleLoad();

        Panel pan_centro = new Panel();
        pan_centro.setLayout(new TableLayout(2));
        pan_centro.setBaseCls("x-plain");
        pan_centro.setPaddings(5, 5, 5, 5);
        pan_centro.add(new PaddedPanel(lista.getPanel(), 0, 0, 13, 0));
        pan_centro.add(new PaddedPanel(lista1.getPanel(), 0, 0, 13, 0));

        FormPanel for_panel1 = new FormPanel();
        for_panel1.setBaseCls("x-plain");
        for_panel1.setWidth(500);
        for_panel1.setBorder(true);
        for_panel1.setTitle("Detalle oP");
//        for_panel1.setLabelWidth(120);

        tex_op = new TextField("Op-", "op", 200);
        com_encargado = new ComboBox("Encargado", "encargado", 200);


        for_panel1.add(tex_op);

        for_panel1.add(com_encargado);



        FormPanel for_panel2 = new FormPanel();
        for_panel2.setBaseCls("x-plain");
        for_panel2.setWidth(500);
        for_panel2.setBorder(true);
        for_panel2.setTitle(" Registro de Entrega");
        for_panel2.setLabelWidth(100);
//        for_panel2.setWidth(250);
//        for_panel2.setLabelWidth(120);
        tex_cantidad = new TextField("Cantidad Entregada", "cantidad", 200);
        dat_fecha = new DateField("Fecha de Entrega", "Y-m-d", 200);
        dat_fecha.setValue(new Date());
        but_guardaritem = new Button("Guardar Entrega");
//        tex_numeroDocumento.setWidth(160);
        for_panel2.add(tex_cantidad);
        for_panel2.add(dat_fecha);
        for_panel2.add(but_guardaritem);










        pan_norte.add(new PaddedPanel(for_panel1, 0, 5, 5, 0));

        pan_norte.add(new PaddedPanel(for_panel2, 0, 120, 5, 0));


        FormPanel for_panel4 = new FormPanel();
        for_panel4.setBaseCls("x-plain");
        for_panel4.setWidth(600);
        for_panel4.setLabelWidth(70);
        tex_totalitems = new NumberField("total a entregar", "total", 200);
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
        tex_totalmaterial = new NumberField("Total Entregados", "totalmaterial", 200);
        tex_totalmaterial.setHeight(30);
        tex_totalmaterial.setValue("0");
        tex_totalmaterial.setReadOnly(true);
//        tex_montoTotal.disable();
        tex_totalmaterial.setCls("grande");
        for_panel5.add(tex_totalmaterial);



        Panel pan_botones = new Panel();
        pan_botones.setLayout(new HorizontalLayout(1));
        pan_botones.setBaseCls("x-plain");
        //       pan_botones.setHeight(40);


        but_eliminarEntrega = new Button("Eliminar Entrega");
        but_limpiar = new Button("Limpiar Panel");


        pan_botones.add(but_limpiar);
        pan_botones.add(but_eliminarEntrega);



        pan_sud.add(new PaddedPanel(for_panel4, 5, 300, 13, 0));
////        pan_sud.add(new PaddedPanel(for_panel5, 0, 0, 13, 10));
        pan_sud.add(new PaddedPanel(for_panel5, 5, 20, 13, 0));

        pan_sud.add(new PaddedPanel(pan_botones, 5, 550, 10, 10), new TableLayoutData(2));



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
////
////        //**************************************************
////        //************* BOTON CANCELAR   *******************
////        //**************************************************
        but_limpiar.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                limpiarVentanaVenta();
            }
        });
////
////        //**************************************************
////        //************* BOTON ACEPTAR *******************
////        //**************************************************
//        but_aceptar.addListener(new ButtonListenerAdapter() {
//
//            @Override
//            public void onClick(Button button, EventObject e) {
//                createCompra();
//            }
//        });
////
////
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

                        tex_op.setValue(ordenproduccion);

                        com_encargado.clearValue();



//                        com_malmacen.clearValue();
//                        com_items.focus();
                    }
                }
            }
        });
//
//
        com_encargado.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    String idproductos = com_encargado.getValueAsString().trim();


                    if (!idproductos.isEmpty()) {
//

                        cargarItemsEncargado(idproductos);
                    } else {
                        MessageBox.alert("Seleccione un Items de OP");
                    }
//                    MessageBox.alert(idproductos);

                }
            }
        });
//
        but_guardaritem.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
//                MessageBox.alert("hola mundo");

                Record[] records = lista.getSelectionModel().getSelections();

                String iditem;
                String detalle;
                String fecha = DateUtil.format(dat_fecha.getValue(), "Y-m-d");
                String cantidad = tex_cantidad.getValueAsString();
                if (records.length == 1) {
//                    "id", "idencargado", "detalle", "encargado", "cantidad", "plazo"
                    iditem = records[0].getAsString("id");
                    detalle = records[0].getAsString("detalle");
                    if ((!fecha.isEmpty()) && (!cantidad.isEmpty())) {

                        GuardarEntrega(iditem, detalle, fecha, cantidad);

                    } else {
                        MessageBox.alert("Debe Ingresar Fecha de Entrega y Cantidad Entregada");
                    }






                } else {
                    MessageBox.alert("No existe un item seleccionado y/o selecciono mas de uno.");
                }
            }

            private void GuardarEntrega(final String iditem, final String detalle, final String fecha, final String cantidad) {
//                {"id", "iditems", "cantidad", "detalle", "fecha"}
                String enlace = "php/Recepcion.php?funcion=GuardarItemEntrega&iditem=" + iditem + "&detalle=" + detalle + "&fecha=" + fecha + "&cantidad=" + cantidad;
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
                                if (errorR.equalsIgnoreCase("true")) {
                                    Record registroCompra = lista1.getRecordDef().createRecord(new Object[]{
                                                id, iditem, cantidad, detalle, fecha});

                                    lista1.getGrid().stopEditing();
                                    lista1.getGrid().getStore().insert(0, registroCompra);
                                    lista1.getGrid().startEditing(0, 0);
                                    Float to = new Float(0);
                                    for (int i = 0; i < lista1.getStore().getRecords().length; i++) {
                                        to += lista1.getStore().getRecords()[i].getAsFloat("cantidad");
                                    }
                                    tex_totalmaterial.setValue(to.toString());
                                    Utils.setErrorPrincipal(mensajeR, "mensaje");
                                } else {
                                    Utils.setErrorPrincipal(mensajeR, "error");
                                    MessageBox.alert(mensajeR);
                                }



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
        });
//
         but_eliminarEntrega.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = lista1.getSelectionModel().getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("id");
                    MessageBox.confirm("Eliminar...", "Realmente desea eliminar Esta Entrega ??", new MessageBox.ConfirmCallback() {


                        public void execute(String btnID) {
                            if (btnID.equalsIgnoreCase("yes")) {

                                //eliminar
                                String enlace = "php/Recepcion.php?funcion=eliminarItemDistribucionEntrega&id=" + selecionado;
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
                but_eliminarEntrega.setPressed(false);
            }
        });
//
////        //**************************************************
////        //*************CALCULAR TOTAL DE COMPRA ************
////        //**************************************************
        lista.getGrid().addGridRowListener(new GridRowListenerAdapter() {

            @Override
            public void onRowDblClick(GridPanel grid, int rowIndex, EventObject e) {
//                Window.alert("En contruccion: aqui saldra la informacion del rol en detalle");
                Record[] records = lista.getSelectionModel().getSelections();


                selecionado = records[0].getAsString("id");
//                String enlTemp = "funcion=reporteClienteHTML&idcliente=" + selecionado;
                String enlace = "php/Recepcion.php?funcion=ListarDetalleEntregaDistribucion&id=" + selecionado;
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
                                        Object[][] productos = Utils.getArrayOfJSONObject(marcaO, "productoM", new String[]{"id", "iditems", "cantidad", "detalle", "fecha"});
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
//        lista1.getGrid().addEditorGridListener(new EditorGridListenerAdapter() {
//
//            @Override
//            public boolean doBeforeEdit(GridPanel grid, Record record, String field, Object value, int rowIndex, int colIndex) {
//                calcularSubTotal(grid, record, field, value, value, rowIndex, colIndex);
//                return super.doBeforeEdit(grid, record, field, value, rowIndex, colIndex);
//            }
//
//            @Override
//            public void onAfterEdit(GridPanel grid, Record record,
//                    String field, Object newValue,
//                    Object oldValue,
//                    int rowIndex,
//                    int colIndex) {
//                calcularSubTotal(grid, record, field, newValue, oldValue, rowIndex, colIndex);
//            }
//        });
////

    }

    private void cargarDistribucion(final String op) {
        String enlace = "php/Recepcion.php?funcion=CargarPanelRecepcion&op=" + op;
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




                                    Object[][] encargadoM = Utils.getArrayOfJSONObject(marcaO, "encargadoM", new String[]{"idempleado", "nombre"});
                                    SimpleStore prodintStore1 = new SimpleStore(new String[]{"idempleado", "nombre"}, encargadoM);
                                    prodintStore1.reload();
                                    com_encargado.setStore(prodintStore1);
                                    encargados =
                                            encargadoM;
                                    com_encargado.focus();




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

    private void cargarItemsMaterial(final String id,
            final String iditem,
            final String idencar) {
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

    private void cargarItemsEncargado(final String idencar) {
        String enlace = "php/Recepcion.php?funcion=CargarDetalleItemsEncargado&idencargado=" + idencar;
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
                                    dat_fecha.setValue(new Date());
                                    tex_cantidad.setValue("");
                                    tex_totalitems.setValue("0");
                                    tex_totalmaterial.setValue("0");


                                    Object[][] detalles = Utils.getArrayOfJSONObject(marcaO, "detalleM", new String[]{"id", "idencargado", "detalle", "encargado", "cantidad", "plazo"});

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
        for (int i = 0; i <
                lista.getStore().getRecords().length; i++) {
            to += lista.getStore().getRecords()[i].getAsFloat("cantidad");
        }

        tex_totalitems.setValue(to.toString());



    }

    private void cargarGrillasMaterial(Object[][] productos) {
        SimpleStore ordenProductosSalidas = new SimpleStore(new String[]{"id", "iditems", "cantidad", "detalle", "fecha"}, productos);
        ordenProductosSalidas.reload();
        Record[] dt1 = ordenProductosSalidas.getRecords();
        lista1.LimpiarGrid();
        lista1.getStore().insert(0, dt1);

        Float to = new Float(0);
        for (int i = 0; i <
                lista1.getStore().getRecords().length; i++) {
            to += lista1.getStore().getRecords()[i].getAsFloat("cantidad");
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
        for (int i = 0; i <
                lista1.getStore().getRecords().length; i++) {
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
        dat_fecha.setValue(new Date());
        tex_cantidad.setValue("");
        tex_totalitems.setValue("0");
        tex_totalmaterial.setValue("0");

        com_encargado.clearValue();

        tex_op.focus();
    }

    private void initValues() {




        SimpleStore productosIntermedioStore = new SimpleStore(new String[]{"id", "nombre", "uds"}, items);
        productosIntermedioStore.load();
        SimpleStore productosIntermedioStore1 = new SimpleStore(new String[]{"id", "detalle"}, items);
        productosIntermedioStore1.load();




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









    }

    private void verReporte(String enlace) {
        ReporteMediaCartaChorroWindow print = new ReporteMediaCartaChorroWindow(enlace);
        print.show();
    }
}
