/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.cortes;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.LocalizableResource.Meaning;
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
import org.trailer.client.MainEntryPoint;
import org.trailer.client.cortes.ListaItems;
import org.trailer.client.util.Conector;
import org.trailer.client.util.KMenu;
import org.trailer.client.util.ReporteMediaCartaChorroWindow;
import org.trailer.client.util.Utils;

/**
 *
 * @author example
 */
public class CrearCorte extends Panel {

    private final int ANCHO = 1000;
    private final int ALTO = 540;
    private Panel panel;
    private ComboBox com_items;
    private ComboBox com_mcompra;
    private ComboBox com_ciudad;
    private ComboBox com_molde;
    private TextField tex_cliente;
    private TextField tex_id;
    private TextField tex_op;
    private TextField tex_apellido;
    private TextField tex_fecha;
    private TextField tex_direccion;
    private TextField tex_email;
    private TextField tex_empresa;
    private TextField tex_entrega;
    private TextArea tea_observacion;
    private DateField dat_fecha;
    private NumberField tex_montoTotal;
    private ListaItems lista;
    boolean respuesta = false;
    private Object[][] clienteM;
    private Object[][] productoM;
    private Object[][] ciudadM;
    private Button but_aceptar;
    private Button but_cancelar;
    private Button but_eliminaritem;
    String selecionado = "";
    private Float totalTotal;
    private String numerodoc;
    private String numcliente;
    private Button but_nuevo;
    private ComboBox com_malmacen;
    private Object[][] items;
    private Object[][] mpcompra;
    private Object[][] mpalmacen;
    private Object[][] moldes;
    private KMenu kmenu;
    private MainEntryPoint pan;
    private EventObject e;

    public CrearCorte(KMenu kmenu, MainEntryPoint panel) {

        this.kmenu = kmenu;
        this.pan = panel;
        this.setClosable(true);
        this.setId("TPfun7000");
        setIconCls("tab-icon");
        setAutoScroll(false);
        this.items = new Object[][]{new Object[]{"0", "No Existe Item o Producsot", "0"}};
        setTitle("Crear Corte");
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
        lista = new ListaItems();
        lista.onModuleLoad();
        Panel pan_centro = lista.getPanel();

        FormPanel for_panel1 = new FormPanel();
        for_panel1.setBaseCls("x-plain");
//        for_panel1.setWidth(250);
//        for_panel1.setLabelWidth(120);
        com_items = new ComboBox("Items", "items", 200);
        com_items.disable();
        tex_op = new TextField("Op-", "op", 200);
        com_mcompra = new ComboBox("MP de Compras", "mpcompra", 200);
        com_mcompra.disable();

        com_molde = new ComboBox("Molde", "molde", 200);

        for_panel1.add(tex_op);
        for_panel1.add(com_items);
        for_panel1.add(com_molde);
        for_panel1.add(com_mcompra);



        FormPanel for_panel2 = new FormPanel();
        for_panel2.setBaseCls("x-plain");
//        for_panel2.setWidth(250);
//        for_panel2.setLabelWidth(120);
        tex_cliente = new TextField("Cliente", "cliente", 200);
        tex_cliente.disable();

        tex_empresa = new TextField("Empresa", "empresa", 200);
        tex_empresa.disable();
        com_malmacen = new ComboBox("MP Almacen", "mpalmacen", 200);
        com_malmacen.disable();
//        tex_numeroDocumento.setWidth(160);
        for_panel2.add(tex_cliente);

        for_panel2.add(tex_empresa);
        for_panel2.add(com_malmacen);


        FormPanel for_panel3 = new FormPanel();
        for_panel3.setBaseCls("x-plain");
//        for_panel3.setWidth(250);
//        for_panel3.setLabelWidth(120);

        tex_fecha = new TextField("Fecha Entrega", "fechaentrega", 200);
        tex_fecha.disable();
        tea_observacion = new TextArea("Observacion", "observacion");

        tea_observacion.setWidth(180);
        tea_observacion.disable();
        for_panel3.add(tex_fecha);
        for_panel3.add(tea_observacion);






        pan_norte.add(new PaddedPanel(for_panel1, 0, 5, 5, 0));
        pan_norte.add(new PaddedPanel(for_panel2, 0, 5, 5, 0));
        pan_norte.add(new PaddedPanel(for_panel3, 0, 5, 5, 0));


        FormPanel for_panel4 = new FormPanel();
        for_panel4.setBaseCls("x-plain");
        for_panel4.setWidth(400);
        for_panel4.setLabelWidth(70);
        tex_montoTotal = new NumberField("Monto total", "montototal", 200);
        tex_montoTotal.setHeight(30);
        tex_montoTotal.setValue("2123");
        tex_montoTotal.setReadOnly(true);
//        tex_montoTotal.disable();
        tex_montoTotal.setCls("grande");

        for_panel4.add(tex_montoTotal);


        FormPanel for_panel5 = new FormPanel();
        for_panel5.setBaseCls("x-plain");
        for_panel5.setWidth(400);
        for_panel5.setLabelWidth(70);
        tex_entrega = new TextField("Entrega", "entrega", 200);
        tex_entrega.setValue("10");
        for_panel5.add(tex_entrega);



        Panel pan_botones = new Panel();
        pan_botones.setLayout(new HorizontalLayout(2));
        pan_botones.setBaseCls("x-plain");
        //       pan_botones.setHeight(40);
        but_aceptar = new Button("Guardar");
        but_cancelar = new Button("Limpiar");
        but_eliminaritem = new Button("Eliminar Item Corte");


        pan_botones.add(but_aceptar);
        pan_botones.add(but_cancelar);
        pan_botones.add(but_eliminaritem);



//        pan_sud.add(new PaddedPanel(for_panel4, 5, 100, 13, 0));
////        pan_sud.add(new PaddedPanel(for_panel5, 0, 0, 13, 10));
//        pan_sud.add(new PaddedPanel(for_panel5, 5, 20, 13, 0));

        pan_sud.add(new PaddedPanel(pan_botones, 5, 450, 10, 10), new TableLayoutData(3));


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

        records = lista.getStore().getModifiedRecords();
        JSONArray productos = new JSONArray();
        JSONObject productoObject;

        JSONObject compraObject = new JSONObject();


        compraObject.put("op", new JSONString(codigo));

//        records1 = lista.getStore().getModifiedRecords();
//        Window.alert("tamaño del store Modificado "+lista.getStore().getModifiedRecords().length);
//        Window.alert("tamaño del store "+lista.getStore().getRecords().length);

        for (int i = 0; i < lista.getStore().getModifiedRecords().length; i++) {
//  {"id", "idtela","idcorte", "uds", "nombrei", "nombret","nombrec", "tela", "hoja", "uds1", "total", "totalunidades"};

//            Window.alert("entro al forrrrr");
            productoObject = new JSONObject();
            productoObject.put("id", new JSONString(records[i].getAsString("id")));
            productoObject.put("idtela", new JSONString(records[i].getAsString("idtela")));
            productoObject.put("idcorte", new JSONString(records[i].getAsString("idcorte")));
            productoObject.put("uds", new JSONString(records[i].getAsString("uds")));
            productoObject.put("nombrei", new JSONString(records[i].getAsString("nombrei")));
            productoObject.put("nombret", new JSONString(records[i].getAsString("nombret")));
            productoObject.put("nombrec", new JSONString(records[i].getAsString("nombrec")));
            productoObject.put("tela", new JSONString(records[i].getAsString("tela")));
            productoObject.put("hoja", new JSONString(records[i].getAsString("hoja")));
            productoObject.put("uds1", new JSONString(records[i].getAsString("uds1")));
            productoObject.put("total", new JSONString(records[i].getAsString("total")));
            productoObject.put("totalunidades", new JSONString(records[i].getAsString("totalunidades")));



            productos.set(i, productoObject);
            productoObject = null;
        }
        JSONObject resultado = new JSONObject();
        resultado.put("corte", compraObject);
        resultado.put("detalles", productos);

        String datos = "resultado=" + resultado.toString();

        Utils.setErrorPrincipal("registrando los cortes", "cargar");
        String url = "php/corte.php?funcion=GuardarNuevaCorte&" + datos;
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
                            MessageBox.alert(mensajeR);
                            kmenu.seleccionarOpcionRemove(null, "fun7000", e, CrearCorte.this);

                            limpiarVentanaVenta();
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


        tex_op.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
//                    String codigoProducto = field.getValueAsString().trim();
                    String ordenproduccion = tex_op.getValueAsString().trim();
                    if (!ordenproduccion.isEmpty()) {
//                        com_cliente.setEditable(false);
//                        MessageBox.alert(op);
                        cargarCorte(ordenproduccion);
                        com_items.clearValue();
                        com_molde.clearValue();
                        com_mcompra.clearValue();
                        com_malmacen.clearValue();
                        com_items.focus();
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
//                        com_cliente.setEditable(false);
//                        MessageBox.alert(op);

                        com_molde.clearValue();
                        com_mcompra.clearValue();
                        com_malmacen.clearValue();
                        com_molde.focus();
                        cargarMolde(idproductos);
                    }
//                    MessageBox.alert(idproductos);

                }
            }
        });

        com_molde.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    String idproductos = com_molde.getValueAsString().trim();
                    if (!idproductos.isEmpty()) {
//                        com_cliente.setEditable(false);
//                        MessageBox.alert(op);

                        com_mcompra.focus();
                    }
//                    MessageBox.alert(idproductos);

                }
            }
        });

        com_mcompra.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    String codigoProducto = field.getValueAsString().trim();
                    String idproductos = com_mcompra.getValueAsString().trim();
                    String iditem = com_items.getValueAsString().trim();
                    String idcorte = com_molde.getValueAsString().trim();
                    if ((!idproductos.isEmpty()) && (!iditem.isEmpty()) && (!idcorte.isEmpty())) {
                        cargarProducto(idproductos, iditem, idcorte);
                        com_mcompra.setValue("");
                        com_mcompra.setEmptyText("");
                        com_mcompra.focus();

                    } else {
                        MessageBox.alert("Tiene que seleccionar un Item y tambien una Materia ");
                    }
                }
            }

            private void cargarProducto(String id, String item, String idm) {

                String idi;
                String nombre;
                String uds;
                String tela;
                String molde;
                if (id != null) {
                    SimpleStore producto = new SimpleStore(new String[]{"id", "nombre", "uds"}, items);
                    producto.load();
                    producto.filter("id", item);
                    Record r = producto.getRecords()[0];

                    idi = r.getAsString("id");
                    nombre = r.getAsString("nombre");
                    uds = r.getAsString("uds");
                    SimpleStore materia = new SimpleStore(new String[]{"id", "nombre"}, mpcompra);
                    materia.load();
                    materia.filter("id", id);
                    Record r1 = materia.getRecords()[0];


                    tela = r1.getAsString("nombre");
                    SimpleStore moldeM = new SimpleStore(new String[]{"id", "nombre"}, moldes);
                    moldeM.load();
                    moldeM.filter("id", idm);
                    Record r12 = moldeM.getRecords()[0];


                    molde = r12.getAsString("nombre");

//String[] nombreComlumns = {"id", "idtela", "uds", "nombrei", "nombret", "tela", "hoja", "uds1", "total", "totalunidades"};
                    Record registroCompra = lista.getRecordDef().createRecord(new Object[]{
                                idi, id, idm, uds, nombre, tela, molde, 0.0, 0.0, 0.0, 0.0, 0.0});

                    lista.getGrid().stopEditing();
                    lista.getGrid().getStore().insert(0, registroCompra);
                    lista.getGrid().startEditing(0, 0);


                }
            }
        });

        com_malmacen.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    String codigoProducto = field.getValueAsString().trim();
                    String idproductos = com_malmacen.getValueAsString().trim();
                    String iditem = com_items.getValueAsString().trim();
                    String idcorte = com_molde.getValueAsString().trim();
                    if ((!idproductos.isEmpty()) && (!iditem.isEmpty()) && (!idcorte.isEmpty())) {
                        cargarProducto1(idproductos, iditem, idcorte);
                        com_malmacen.setValue("");
                        com_malmacen.setEmptyText("");
                        com_malmacen.focus();

                    } else {
                        MessageBox.alert("Tiene que seleccionar un Item y tambien una Materia ");
                    }
                }
            }

            private void cargarProducto1(String id, String item, String idm) {

                String idi;
                String nombre;
                String uds;
                String tela;
                String corte;
                if (id != null) {
                    SimpleStore producto = new SimpleStore(new String[]{"id", "nombre", "uds"}, items);
                    producto.load();
                    producto.filter("id", item);
                    Record r = producto.getRecords()[0];

                    idi = r.getAsString("id");
                    nombre = r.getAsString("nombre");
                    uds = r.getAsString("uds");
                    SimpleStore materia = new SimpleStore(new String[]{"id", "detalle"}, mpalmacen);
                    materia.load();
                    materia.filter("id", id);
                    Record r1 = materia.getRecords()[0];


                    tela = r1.getAsString("detalle");


                    corte = r1.getAsString("nombre");
                    SimpleStore moldeM = new SimpleStore(new String[]{"id", "nombre"}, moldes);
                    moldeM.load();
                    moldeM.filter("id", idm);
                    Record r12 = moldeM.getRecords()[0];
                    corte = r12.getAsString("nombre");
//String[] nombreComlumns = {"id", "idtela", "uds", "nombrei", "nombret", "tela", "hoja", "uds1", "total", "totalunidades"};
                    Record registroCompra = lista.getRecordDef().createRecord(new Object[]{
                                idi, id, idm, uds, nombre, tela, corte, 0.0, 0.0, 0.0, 0.0, 0.0});

                    lista.getGrid().stopEditing();
                    lista.getGrid().getStore().insert(0, registroCompra);
                    lista.getGrid().startEditing(0, 0);


                }
            }
        });



        but_eliminaritem.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = lista.getSelectionModel().getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("id");
                    MessageBox.confirm("Eliminar...", "Realmente desea eliminar??", new MessageBox.ConfirmCallback() {

                        public void execute(String btnID) {
                            if (btnID.equalsIgnoreCase("yes")) {

                                //eliminar
                                String enlace = "php/Corte.php?funcion=eliminarItemCorte&idcorte=" + selecionado;
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

    private void cargarCorte(final String op) {
        String enlace = "php/Corte.php?funcion=CargarPanelCorte&op=" + op;
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

                                    String cliente = Utils.getStringOfJSONObject(marcaO, "cliente");
                                    String fechaentrega = Utils.getStringOfJSONObject(marcaO, "fechaentrega");
                                    String observacion = Utils.getStringOfJSONObject(marcaO, "observacion");
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

                                    Object[][] mpcompra1 = Utils.getArrayOfJSONObject(marcaO, "mpcompraM", new String[]{"id", "nombre"});
                                    SimpleStore prodintStore1 = new SimpleStore(new String[]{"id", "nombre"}, mpcompra1);
                                    prodintStore1.reload();
                                    com_mcompra.setStore(prodintStore1);
                                    mpcompra = mpcompra1;


//                                    CargarStore(itemsM, mpcompra, mpalmacen1);
//                                    com_productointermedio2.setStore(prodintStore);

//                                    tex_id.setValue(op);
                                    tex_cliente.setValue(cliente);
                                    tex_fecha.setValue(fechaentrega);
                                    tea_observacion.setValue(observacion);

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

    private void cargarMolde(final String op) {
        String enlace = "php/Corte.php?funcion=CargarMolde&id=" + op;
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

                                    Object[][] moldeM = Utils.getArrayOfJSONObject(marcaO, "moldeM", new String[]{"id", "nombre"});
//
                                    SimpleStore prodintStore = new SimpleStore(new String[]{"id", "nombre"}, moldeM);
                                    prodintStore.reload();
                                    com_molde.setStore(prodintStore);
                                    CargarStoreMolde(moldeM);
                                    lista.LimpiarGrid();
                                    Object[][] detalles = Utils.getArrayOfJSONObject(marcaO, "detalleM", new String[]{"id", "idtela", "idcorte", "uds", "nombrei", "nombret", "nombrec", "tela", "hoja", "uds1", "total", "totalunidades"});

                                    cargarGrillas(detalles);



//                                    com_productointermedio2.setStore(prodintStore);

//                                    tex_id.setValue(op);


//MessageBox.alert("No Hay datos en la consult111111111111a");

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

        SimpleStore ordenProductos = new SimpleStore(new String[]{"id", "idtela", "idcorte", "uds", "nombrei", "nombret", "nombrec", "tela", "hoja", "uds1", "total", "totalunidades"}, entradas);
        ordenProductos.reload();
        Record[] dt = ordenProductos.getRecords();
        lista.LimpiarGrid();
        lista.getStore().insert(0, dt);



    }

    private void calcularSubTotal(GridPanel grid, Record record, String field, Object newValue, Object oldValue, int rowIndex, int colIndex) {

//String[] nombreComlumns = {"id", "idtela", "uds", "nombrei", "nombret", "tela", "hoja", "uds1", "total", "totalunidades"};
        record.set("total", record.getAsFloat("tela") * record.getAsFloat("hoja"));
        record.set("totalunidades", record.getAsFloat("hoja") * record.getAsFloat("uds1"));




    }

    public void resetCamposProveedor() {

        com_items.reset();
        com_items.focus();

    }

    public Panel getPanel() {
        return panel;
    }

    private void limpiarVentanaVenta() {


        lista.LimpiarGrid();

        tex_op.setValue("");

        com_items.clearValue();

        com_molde.clearValue();

        com_mcompra.clearValue();

        com_malmacen.clearValue();

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

        com_molde.setStore(productosIntermedioStore);
        com_molde.setMinChars(1);
        com_molde.setFieldLabel("Molde");
        com_molde.setDisplayField("nombre");
        com_molde.setValueField("id");
        com_molde.setForceSelection(true);
        com_molde.setMode(ComboBox.LOCAL);
        com_molde.setEmptyText("Buscar molde");
        com_molde.setLoadingText("buscando...");
        com_molde.setTypeAhead(true);
        com_molde.setSelectOnFocus(true);
        com_molde.setHideTrigger(true);
        com_molde.enable();


        com_mcompra.setStore(productosIntermedioStore);
        com_mcompra.setMinChars(1);
        com_mcompra.setFieldLabel("MP Compra");
        com_mcompra.setDisplayField("nombre");
        com_mcompra.setValueField("id");
        com_mcompra.setForceSelection(true);
        com_mcompra.setMode(ComboBox.LOCAL);
        com_mcompra.setEmptyText("Buscar materia prima");
        com_mcompra.setLoadingText("buscando...");
        com_mcompra.setTypeAhead(true);
        com_mcompra.setSelectOnFocus(true);
        com_mcompra.setHideTrigger(true);
        com_mcompra.enable();


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






    }

    public void CargarStore(Object[][] items, Object[][] mcompra, Object[][] malmacen) {
        this.items = items;
        this.mpcompra = mcompra;
        this.mpalmacen = malmacen;

    }

    public void CargarStoreMolde(Object[][] moldesM) {

        this.moldes = moldesM;

    }

    private void verReporte(String enlace) {
        ReporteMediaCartaChorroWindow print = new ReporteMediaCartaChorroWindow(enlace);
        print.show();
    }
}
