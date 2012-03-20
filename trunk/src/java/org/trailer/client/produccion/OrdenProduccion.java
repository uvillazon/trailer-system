/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.produccion;

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
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;
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
import org.trailer.client.parametros.ListaCompraProducto;
import org.trailer.client.sistema.Proveedor;
import org.trailer.client.util.Conector;
import org.trailer.client.util.Utils;

/**
 *
 * @author Uvillazon
 */
public class OrdenProduccion extends Panel {

    private final int ANCHO = 1000;
    private final int ALTO = 540;
    private Panel panel;
    private ComboBox com_Clientes;
    private ComboBox com_producto;
    private ComboBox com_responsables;
    private ComboBox com_empresa;
//    private TextField tex_numeroDocumento;
    private TextField tex_id;
    private TextField tex_numeroproduccion;
    private TextField Tex_numerorecibo;
    private DateField dat_fecharecepcion;
    private DateField dat_fechaentrega;
    private NumberField tex_montoCuenta;
    private NumberField tex_montoTotal;
    private NumberField tex_saldo;
//    private NumberField tex_descuentoBs;
    private RadioButton rad_empresa;
    private RadioButton rad_cliente;
    private ListaProductosOrden lista;
    private SimpleStore responsablesStore;
    Proveedor proveedorSeleccionado;
    boolean respuesta = false;
    private TextArea tea_detalleCostura;
    private Object[][] clientesM;
    private Object[][] productoM;
    private Object[][] responsablesM;
    private Object[][] empresasM;
    private Object[][] detalles;
    private Object[][] orden;
    private Object[][] telas;
    private Object[][] colores;
    private Button but_aceptar;
    private Button but_cancelar;
    private Button but_limpiar;
    String selecionado = "";
    private Float totalTotal;
    private Float montocuenta;
    private Float saldo;
//    private Float totalpagar;
    private KMenu kmenu;
    private String ordenproduccion;
    private MainEntryPoint pan;
    private TextArea tea_detalleBordado;
    private String numeroorden;
    private String fecha;


    public OrdenProduccion(KMenu kmenu, MainEntryPoint panel) {

        this.kmenu = kmenu;
        this.pan = panel;
        this.setClosable(true);
        this.setId("TPfun4000");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Orden de Produccion");
        setLayout(new FitLayout());
        onModuleLoad(false);
    }

    public OrdenProduccion(Object[][] clientes, Object[][] productos, Object[][] responsables, Object[][] empresas, Object[][] telas, Object[][] colores, String numeroorden,String fecha) {
//        this.compra=compra;

        this.clientesM = clientes;
        this.productoM = productos;
        this.responsablesM = responsables;
        this.empresasM = empresas;
        this.numeroorden = numeroorden;
        this.colores = colores;
        this.telas = telas;
        this.fecha = fecha;
        responsablesStore = new SimpleStore(new String[]{"idresponsable", "nombre", "idempresa"}, responsablesM);
        responsablesStore.reload();
//        this.tipoDocM = new String[]{"Factura", "Nota Venta", "Recibo"};
        this.setClosable(true);
        this.setId("TPfun4000");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Orden de Produccion");
        setLayout(new FitLayout());
        onModuleLoad(false);

    }

    public OrdenProduccion(Object[][] clientes, Object[][] productos, Object[][] responsables, Object[][] empresas, Object[][] telas, Object[][] colores, String numeroorden, Object[][] orden, Object[][] detalle) {
//        this.compra=compra;

        this.clientesM = clientes;
        this.productoM = productos;
        this.responsablesM = responsables;
        this.empresasM = empresas;
        this.numeroorden = numeroorden;
        this.colores = colores;
        this.telas = telas;
        this.orden = orden;
        this.detalles = detalle;

        responsablesStore = new SimpleStore(new String[]{"idresponsable", "nombre", "idempresa"}, responsablesM);
        responsablesStore.reload();
//        this.tipoDocM = new String[]{"Factura", "Nota Venta", "Recibo"};
        this.setClosable(true);
        this.setId("TPfun4000");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Orden de Produccion");
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
        pan_norte.setPaddings(5, 20, 5, 10);

        Panel pan_sud = new Panel();
        pan_sud.setLayout(new TableLayout(2));
        pan_sud.setBaseCls("x-plain");
        pan_sud.setHeight(110);
        pan_sud.setPaddings(15, 20, 5, 10);
        lista = new ListaProductosOrden();
        lista.onModuleLoad(telas, colores);
        Panel pan_centro = lista.getPanel();
        pan_centro.setHeight(340);

        FormPanel for_panel1 = new FormPanel();
        for_panel1.setBaseCls("x-plain");
        for_panel1.setWidth(380);
        for_panel1.setLabelWidth(120);
        com_Clientes = new ComboBox("Proveedor", "proveedor");
        com_Clientes.setWidth(180);
        com_producto = new ComboBox("Producto", "producto");
        com_producto.setWidth(180);
        tex_id = new TextField("Id", "idcompra");
        tex_id.hide();
        tex_numeroproduccion = new TextField("No Orden OP", "numeroorden");
        tex_numeroproduccion.setReadOnly(false);
//        tex_numeroproduccion.se
//        tex_numeroproduccion.set
        com_responsables = new ComboBox("Responsable", "responsable");
        com_responsables.setWidth(180);
        dat_fecharecepcion = new DateField("Fecha", "Y-m-d");
        dat_fecharecepcion.setWidth(180);
        
        dat_fechaentrega = new DateField("Fecha Entrega", "Y-m-d");
        dat_fechaentrega.setWidth(180);
        com_empresa = new ComboBox("Empresa", "empresa");
        com_empresa.setWidth(180);
//        tex_numeroDocumento = new TextField("Numero docto.", "numerodocumento");
//        tex_numeroDocumento.setWidth(160);
        tex_montoTotal = new NumberField("Monto total", "montototal");
        tex_montoTotal.setReadOnly(true);
        tex_montoTotal.setWidth(200);
        tex_montoTotal.setHeight(30);
        tex_montoCuenta = new NumberField("Monto a Cuenta", "montopagar");
//        tex_montoCuenta.disable();
        tex_montoCuenta.setWidth(200);
        tex_saldo = new NumberField("Saldo", "saldo");
        tex_saldo.setWidth(200);
        tex_saldo.setReadOnly(true);
        tex_saldo.setHeight(30);
        Tex_numerorecibo = new TextField("No Recibo", "recibo");
        Tex_numerorecibo.setWidth(200);
//        Tex_numerorecibo.set


//        tex_descuentoBs = new NumberField("Descuento (Bs)", "descuento");
        rad_empresa = new RadioButton("empresa", "Empresa    ");
        rad_cliente = new RadioButton("cliente", "Cliente    ");
        rad_cliente.setWidth("90px");
        rad_empresa.setEnabled(true);
        rad_empresa.setWidth("90px");
        rad_empresa.setChecked(true);
//        com_Clientes.setDisabled(true);
        rad_cliente.setEnabled(true);

        for_panel1.add(tex_id);
        for_panel1.add(dat_fecharecepcion);
        for_panel1.add(dat_fechaentrega);
        for_panel1.add(com_producto);

        FormPanel for_panel2 = new FormPanel();
        for_panel2.setBaseCls("x-plain");
        for_panel2.setWidth(380);
        for_panel2.setLabelWidth(120);
        for_panel2.add(rad_empresa);
        for_panel2.add(rad_cliente);
        for_panel2.add(com_Clientes);
        for_panel2.add(tex_numeroproduccion);


        FormPanel for_panel3 = new FormPanel();
        for_panel3.setBaseCls("x-plain");
        for_panel3.setWidth(380);
        for_panel3.setLabelWidth(120);
        for_panel3.add(com_empresa);
        for_panel3.add(com_responsables);

        pan_norte.add(new PaddedPanel(for_panel1, 0, 0, 13, 10));
        pan_norte.add(new PaddedPanel(for_panel2, 0, 0, 13, 10));
        pan_norte.add(new PaddedPanel(for_panel3, 0, 0, 13, 10));

        FormPanel for_panel4 = new FormPanel();
        for_panel4.setBaseCls("x-plain");
        for_panel4.setWidth(400);
        for_panel4.setLabelWidth(120);
        for_panel4.add(tex_montoTotal);
//        for_panel4.add(tex_montoCuenta);
        for_panel4.add(tex_saldo);


        FormPanel for_panel6 = new FormPanel();
        for_panel6.setBaseCls("x-plain");
        for_panel6.setWidth(380);
        tea_detalleCostura = new TextArea("Detalle Costura", "detallecostura");
        tea_detalleCostura.setWidth("100%");


        tea_detalleBordado = new TextArea("Detalle De Bordado", "detallebordado");
        tea_detalleBordado.setWidth("100%");


        for_panel6.add(tex_montoCuenta);
        for_panel6.add(Tex_numerorecibo);
//        for_panel6.add(tea_detalleBordado);

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

        pan_sud.add(new PaddedPanel(for_panel4, 2, 0, 13, 10));
//        pan_sud.add(new PaddedPanel(for_panel5, 0, 0, 13, 10));
        pan_sud.add(new PaddedPanel(for_panel6, 2, 0, 13, 10));
        pan_sud.add(new PaddedPanel(pan_botones, 1, 370, 10, 10), new TableLayoutData(3));


        pan_borderLayout.add(pan_norte, new BorderLayoutData(RegionPosition.NORTH));
        pan_borderLayout.add(pan_centro, new BorderLayoutData(RegionPosition.CENTER));
        pan_borderLayout.add(pan_sud, new BorderLayoutData(RegionPosition.SOUTH));
        add(pan_borderLayout);
        if (d) {
            initValues();
            initValues2();
            aniadirgrid();
        } else {
            initValues();
        }
        addListeners();

    }

    public void createOrden() {
        String idcliente = "";
        String idResponsable = "";
        String idempresa = "";
        if (rad_cliente.isChecked()) {
            idcliente = com_Clientes.getValue();
        } else if (rad_empresa.isChecked()) {
            idResponsable = com_responsables.getValue();
            idempresa = com_empresa.getValue();
        }

        String fecharec;
        if (dat_fecharecepcion.getValue() == null || dat_fecharecepcion.getValueAsString() == "") {
            
            fecharec = fecha;
        } else {
            fecharec = DateUtil.format(dat_fecharecepcion.getValue(), "Y-m-d");
        }
        String fechaent;
        if (dat_fechaentrega.getValue() == null || dat_fechaentrega.getValueAsString() == "") {
           
            fechaent = fecha;
        } else {
            fechaent = DateUtil.format(dat_fechaentrega.getValue(), "Y-m-d");
        }
        String montoTotal = tex_montoTotal.getText();
        String montoAcuenta = tex_montoCuenta.getText();
        String saldos = tex_saldo.getText();
        String orden1 = tex_numeroproduccion.getValueAsString();

        String recibo = Tex_numerorecibo.getValueAsString();
        String idOrden;
        if (tex_id.getValueAsString() == "" || tex_id.getValueAsString() == null) {
            idOrden = "";
        } else {
            idOrden = tex_id.getValueAsString();
        }
        Record[] records = lista.getStore().getRecords();
        JSONArray productos = new JSONArray();
        JSONObject productoObject;

        JSONObject ordenObject = new JSONObject();
        ordenObject.put("cliente", new JSONString(idcliente));
        ordenObject.put("idorden", new JSONString(idOrden));
        ordenObject.put("responsable", new JSONString(idResponsable));
        ordenObject.put("empresa", new JSONString(idempresa));
        ordenObject.put("fecharecepcion", new JSONString(fecharec));
        ordenObject.put("fechaentrega", new JSONString(fechaent));
        ordenObject.put("montototal", new JSONString(montoTotal));
        ordenObject.put("montoacuenta", new JSONString(montoAcuenta));
        ordenObject.put("saldo", new JSONString(saldos));
        ordenObject.put("orden", new JSONString(orden1));

        ordenObject.put("recibo", new JSONString(recibo));

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
            productoObject.put("tela", new JSONString(records[i].getAsString("tela")));
            productoObject.put("color", new JSONString(records[i].getAsString("color")));
            productoObject.put("detalle1", new JSONString(records[i].getAsString("detalle1")));
            productoObject.put("detallebordado", new JSONString(records[i].getAsString("detallebordado")));
            productoObject.put("detallecostura", new JSONString(records[i].getAsString("detallecostura")));
            productoObject.put("estado", new JSONString(records[i].getAsString("estado")));

            productos.set(i, productoObject);
            productoObject = null;
        }
        JSONObject resultado = new JSONObject();
        resultado.put("orden", ordenObject);
        resultado.put("detalles", productos);

        String datos = "resultado=" + resultado.toString();
//        String idorden = tex_id.getValueAsString();
        Utils.setErrorPrincipal("registrando la compra", "cargar");
        String url = "./php/OrdenProduccion.php?funcion=GuardarOrdenProduccion&" + datos + "&idorden=" + idOrden;
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
                            String numeroorden = Utils.getStringOfJSONObject(jsonObject, "numeroorden");
                            tex_numeroproduccion.setValue(numeroorden);
                            limpiarVentanaVenta();
                     
                            //ListarOrdenes
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
                createOrden();
            }
        });


        but_limpiar.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                limpiarVentanaVenta();
            }
        });

        com_empresa.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    com_responsables.focus();
//                    String codigoProducto = field.getValueAsString().trim();
                    String idempresa = com_empresa.getValueAsString().trim();
                    if (!idempresa.isEmpty()) {
//                        MessageBox.alert(idempresa);
//                        cargarProducto(idproceso,1);
                        com_responsables.setValue("");
//                          responsablesStore.clearFilter();
                        responsablesStore.filter("idempresa", idempresa);
//                        responsablesStore.reload();
//                        com_responsables.setStore(responsablesStore);
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
                String preciounitario;
                if (id != null) {
                    SimpleStore producto = new SimpleStore(new String[]{"id", "nombre", "unidad", "preciounitario"}, productoM);
                    producto.load();
                    producto.filter("id", id);
                    Record r = producto.getRecords()[0];

                    idProducto = r.getAsString("id");
                    nombre = r.getAsString("nombre");
                    preciounitario = r.getAsString("preciounitario");
                    unidad = r.getAsString("unidad");

                    Record registroCompra = lista.getRecordDef().createRecord(new Object[]{
                                idProducto, nombre, "M", 1, 0, 0, "", "", "", "", "", ""});

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
                    com_producto.focus();
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
//        tex_descuentoPorcentaje.addListener(new TextFieldListenerAdapter() {
//
//            @Override
//            public void onSpecialKey(Field field, EventObject e) {
//                if (e.getKey() == EventObject.ENTER) {
//                    recalcular(true);
//                }
//            }
//        });
        tex_montoCuenta.addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    recalcular(true);
                }
            }
        });
        rad_empresa.addClickListener(new ClickListener() {

            public void onClick(Widget sender) {
                com_empresa.setDisabled(false);
                com_responsables.setDisabled(false);
                rad_cliente.setChecked(false);
                com_Clientes.setDisabled(true);
                com_Clientes.setValue("");
            }
        });
        rad_cliente.addClickListener(new ClickListener() {

            public void onClick(Widget sender) {
                com_Clientes.setDisabled(false);
                com_empresa.setDisabled(true);
                com_empresa.setValue("");
                com_responsables.setDisabled(true);
                com_responsables.setValue("");
                rad_empresa.setChecked(false);
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
//        lista.getGrid().addGridRowListener(new GridRowListenerAdapter() {
//
//            @Override
//            public void onRowDblClick(GridPanel grid, int rowIndex, EventObject e) {
////                Window.alert("En contruccion: aqui saldra la informacion del rol en detalle");
//                Record[] records = lista.getSelectionModel().getSelections();
//
//
//                selecionado = records[0].getAsString("id");
//
//
//
//
////                     Record registroCompra = lista.getRecordDef().createRecord(new Object[]{
////                                "prd-1", "prueba", "", 1, 2, 213123,"","","","",""});
//
//
//
//
////                    lista.getRecordDef().createRecord(2,new Object()[])
////                    Window.alert(enlTemp);
//
//
//            }
//        });

    }

    private void calcularSubTotal(GridPanel grid, Record record, String field, Object newValue, Object oldValue, int rowIndex, int colIndex) {
        String temp = newValue.toString();
        Float old = new Float(oldValue.toString());

        Float ne = old;
        try {
            ne = new Float(temp);
        } catch (Exception e) {
            com.google.gwt.user.client.Window.alert("atapadp  " + e.getMessage());
            ne =
                    old;
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
        for (int i = 0; i <
                grid.getStore().getRecords().length; i++) {
            total += grid.getStore().getRecords()[i].getAsFloat("preciototal");
        }

        tex_montoTotal.setValue(total.toString());
//        tex_montoCuenta.setValue(total.toString());

    }

    public void resetCamposProveedor() {

        com_Clientes.reset();
        com_Clientes.focus();

    }

    public Panel getPanel() {
        return panel;
    }

    private void limpiarVentanaVenta() {
        lista.LimpiarGrid();
        lista.LimpiarPanelAdicioanl();
        tea_detalleCostura.setValue("");
        tea_detalleBordado.setValue("");

        com_Clientes.clearValue();
        com_producto.clearValue();
        com_responsables.clearValue();
        com_empresa.clearValue();
        tex_montoTotal.setValue(0);
        tex_montoCuenta.setValue(0);
        Tex_numerorecibo.setValue("");
        tex_id.setValue("");
        tex_saldo.setValue(0);
        dat_fecharecepcion.setValue(new Date());
        dat_fechaentrega.setValue("");
    }

    private void initValues() {

        SimpleStore clientesStore = new SimpleStore(new String[]{"idcliente", "nombre"}, clientesM);
        clientesStore.load();
        com_Clientes.setMinChars(1);
        com_Clientes.setFieldLabel("Cliente");
        com_Clientes.setStore(clientesStore);
        com_Clientes.setDisplayField("nombre");
        com_Clientes.setValueField("idcliente");
        com_Clientes.setValue("");
//        com_Clientes.setForceSelection(true);
        com_Clientes.setMode(ComboBox.LOCAL);
        com_Clientes.setEmptyText("Buscar Cliente");
        com_Clientes.setLoadingText("buscando...");
        com_Clientes.setTypeAhead(true);
        com_Clientes.disable();
//        com_Clientes.setSelectOnFocus(true);
        com_Clientes.setHideTrigger(true);

        SimpleStore productosStore = new SimpleStore(new String[]{"id", "nombre",}, productoM);
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
//        com_producto.setSelectOnFocus(true);
        com_producto.setHideTrigger(true);


        com_responsables.setMinChars(1);
        com_responsables.setFieldLabel("Responsable");
        com_responsables.setStore(responsablesStore);
        com_responsables.setDisplayField("nombre");
        com_responsables.setValueField("idresponsable");
        com_responsables.setTriggerAction(ComboBox.ALL);
//        com_responsables.setForceSelection(true);
        com_responsables.setMode(ComboBox.LOCAL);
        com_responsables.setEmptyText("Buscar responsable");
        com_responsables.setLoadingText("buscando...");
        com_responsables.setTypeAhead(true);
//        com_responsables.disable();
//        com_responsables.setSelectOnFocus(true);
        com_responsables.setHideTrigger(true);
        tex_id.setValue("");

        SimpleStore empresasStore = new SimpleStore(new String[]{"idempresa", "nombre"}, empresasM);
        empresasStore.load();
        com_empresa.setMinChars(1);
        com_empresa.setFieldLabel("Empresa");
        com_empresa.setStore(empresasStore);
        com_empresa.setDisplayField("nombre");
        com_empresa.setValueField("idempresa");
//        com_empresa.setForceSelection(true);
        com_empresa.setMode(ComboBox.LOCAL);
        com_empresa.setEmptyText("Buscar Empresa");
        com_empresa.setLoadingText("buscando...");
        com_empresa.setTypeAhead(true);
//        com_empresa.disable();
//        com_empresa.setSelectOnFocus(true);
        com_empresa.setHideTrigger(true);

        tex_numeroproduccion.setValue(numeroorden);
        dat_fecharecepcion.setValue(fecha);
    }

    private void initValues2() {

        tex_id.setValue(orden[0][0].toString());
        dat_fecharecepcion.setValue(orden[0][1].toString());
        dat_fechaentrega.setValue(orden[0][2].toString());
        com_Clientes.setValue(orden[0][3].toString());
        com_empresa.setValue(orden[0][4].toString());
        com_responsables.setValue(orden[0][5].toString());
        tex_montoTotal.setValue(orden[0][6].toString());
        tex_montoCuenta.setValue(orden[0][7].toString());
        tex_saldo.setValue(orden[0][8].toString());

        Tex_numerorecibo.setValue(orden[0][9].toString());

    }

    public void aniadirgrid() {

        SimpleStore ordenProductos = new SimpleStore(new String[]{"id", "detalle", "unidad", "cantidad", "preciounitario", "preciototal", "tela", "color", "detalle1", "detallebordado", "detallecostura", "estado"}, detalles);
        ordenProductos.load();
        Record[] dt = ordenProductos.getRecords();
        lista.getStore().insert(0, dt);
//        Window.alert("entro al for para insertar  4 ");
    }

    private void recalcular(boolean desc) {

        totalTotal = new Float(tex_montoTotal.getText());
        montocuenta =
                new Float(tex_montoCuenta.getText());
        if (desc == true) {
//            descPorcentaje = new Float(tex_descuentoPorcentaje.getText());
            saldo = (totalTotal - montocuenta);
            tex_saldo.setValue(saldo);
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
        val =
                val * factor;
        long tmp = Math.round(val);
        return (double) tmp / factor;
    }

    public static float round(float val, int places) {
        return (float) round((double) val, places);
    }
}

