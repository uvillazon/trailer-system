/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.sistema;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.grid.EditorGridPanel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.event.GridCellListenerAdapter;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.widgets.grid.BaseColumnConfig;
import com.gwtext.client.widgets.grid.CheckboxColumnConfig;
import com.gwtext.client.widgets.grid.CheckboxSelectionModel;
import com.gwtext.client.widgets.grid.RowNumberingColumnConfig;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;
import org.trailer.client.util.Conector;
import org.trailer.client.util.Utils;
import org.trailer.client.util.ReporteMediaCartaChorroWindow;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.PagingToolbar;
import com.gwtext.client.widgets.QuickTipsConfig;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.data.*;

/**
 *
 * @author buggy
 */
public class Proveedor extends Panel {

    private GridPanel grid;
    private ColumnConfig idColumn;
    private ColumnConfig codigoColumn;
    private ColumnConfig ciudadColumn;
    private ColumnConfig nombreColumn;
    private ColumnConfig telefonoColumn;
    private ColumnConfig direccionColumn;
    private ColumnConfig representanteColumn;
//    private ColumnConfig nitColumn;
    private final int ANCHO = Utils.getScreenWidth() - 24;
    private final int ALTO = Utils.getScreenHeight() - 270;
    private ToolbarButton editarProveedor;
    private ToolbarButton eliminarProveedor;
    private ToolbarButton nuevoProveedor;
    PagingToolbar pagingToolbar = new PagingToolbar();
//    private ToolbarButton reporteCliente;
    private ToolbarButton cambiarestado;
    private EditarNuevoProveedorForm formulario;

    protected ExtElement ext_element;
    CheckboxSelectionModel cbSelectionModel;
    Store store;
    private String selecionado;
    private BaseColumnConfig[] columns;
    ColumnModel columnModel;

    public Proveedor() {
        this.setClosable(true);
        this.setId("TPfun1002");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Proveedores");
        onModuleLoad();
    }

    public void onModuleLoad() {

        DataProxy dataProxy = new ScriptTagProxy("php/Proveedor.php?funcion=ListarProveedores");
        final RecordDef recordDef = new RecordDef(new FieldDef[]{
                    new StringFieldDef("idproveedor"),
                    new StringFieldDef("codigo"),
                    new StringFieldDef("nombre"),
                    new StringFieldDef("representante"),
                    new StringFieldDef("telefono"),
                    new StringFieldDef("direccion"),
                    new StringFieldDef("ciudad"),
                });
        JsonReader reader = new JsonReader(recordDef);
        reader.setRoot("resultado");
        reader.setTotalProperty("totalCount");

        store = new Store(dataProxy, reader, true);
        idColumn = new ColumnConfig("Id Proveedor", "idproveedor",(ANCHO / 8), true);
        idColumn.setWidth(100);
        codigoColumn = new ColumnConfig("Codigo", "codigo",(ANCHO / 8),true);

        
        ciudadColumn = new ColumnConfig("Ciudad", "ciudad", (ANCHO / 8), true);


        nombreColumn = new ColumnConfig("Nombre", "nombre", (ANCHO / 8), true);
        nombreColumn.setId("expandible");
        telefonoColumn = new ColumnConfig("Telefono", "telefono", (ANCHO / 8), true);
        direccionColumn = new ColumnConfig("Direccion", "direccion", (ANCHO / 8), true);
//        nitColumn = new ColumnConfig("Nit", "nit", (ANCHO / 8), true);
        representanteColumn = new ColumnConfig("Representante", "representante", (ANCHO / 8), true);

        cbSelectionModel = new CheckboxSelectionModel();
        columns = new BaseColumnConfig[]{
                    new RowNumberingColumnConfig(),
                    new CheckboxColumnConfig(cbSelectionModel),
                   
                    nombreColumn,                    
                    representanteColumn,
                    telefonoColumn,
                    direccionColumn,
                    ciudadColumn
                };

        columnModel = new ColumnModel(columns);

        grid = new EditorGridPanel();
        grid.setId("grid-lista-Proveedores");
        grid.setWidth(ANCHO);
        grid.setHeight(ALTO);
        grid.setTitle("Proveedores");
        grid.setStore(store);
        grid.setColumnModel(columnModel);
        grid.setTrackMouseOver(true);
        grid.setAutoExpandColumn("expandible");
        grid.setLoadMask(true);
        grid.setSelectionModel(cbSelectionModel);
        grid.setFrame(true);
        grid.setStripeRows(true);
        grid.setIconCls("grid-icon");
        grid.addGridRowListener(new GridRowListenerAdapter() {

            @Override
            public void onRowDblClick(GridPanel grid, int rowIndex, EventObject e) {
//                Window.alert("En contruccion: aqui saldra la informacion del rol en detalle");
                Record[] records = cbSelectionModel.getSelections();


                selecionado = records[0].getAsString("idproveedor");
                String enlTemp = "funcion=reporteProveedorHTML&idproveedor=" + selecionado;
                verReporte(enlTemp);
//                    Window.alert(enlTemp);
            }
        });

        nuevoProveedor = new ToolbarButton("Nuevo");
        nuevoProveedor.setEnableToggle(true);
        QuickTipsConfig tipsConfig1 = new QuickTipsConfig();
        tipsConfig1.setText("Crear Proveedor");
        nuevoProveedor.setTooltip(tipsConfig1);

        editarProveedor = new ToolbarButton("Editar");
        editarProveedor.setEnableToggle(true);
        QuickTipsConfig tipsConfig = new QuickTipsConfig();
        tipsConfig.setText("Editar Proveedor");
        editarProveedor.setTooltip(tipsConfig);

        eliminarProveedor = new ToolbarButton("Eliminar");
        eliminarProveedor.setEnableToggle(true);
        QuickTipsConfig tipsConfig2 = new QuickTipsConfig();
        tipsConfig2.setText("Eliminar Proveedor");
        eliminarProveedor.setTooltip(tipsConfig2);

//        QuickTipsConfig tipsConfig3 = new QuickTipsConfig();
        cambiarestado = new ToolbarButton("Cambiar Estado");
        cambiarestado.setEnableToggle(true);
        QuickTipsConfig tipsConfig3 = new QuickTipsConfig();
        tipsConfig3.setText("Cambiar Estado Cliente a Pendiente , Inactivo");
        cambiarestado.setTooltip(tipsConfig3);


        pagingToolbar = new PagingToolbar(store);
        pagingToolbar.setPageSize(100);
        pagingToolbar.setDisplayInfo(true);
        pagingToolbar.setDisplayMsg("Mostrando {0} - {1} de {2}");
        pagingToolbar.setEmptyMsg("No topics to display");
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(nuevoProveedor);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(editarProveedor);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(eliminarProveedor);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(cambiarestado);
        pagingToolbar.addSeparator();

        grid.setBottomToolbar(pagingToolbar);

        add(grid);
        //panel.add(grid);
        aniadirListenersProveedor();
    }

    public GridPanel getGrid() {
        return grid;
    }

    public void setGrid(GridPanel grid) {
        this.grid = grid;
    }

    public void reload() {
        store.reload();
        grid.reconfigure(store, columnModel);
        grid.getView().refresh();
    }

    private void CargarNuevaProveedor() {
        String enlace = "php/Proveedor.php?funcion=CargarNuevoProveedor";
        Utils.setErrorPrincipal("Cargando parametros de Nuevo Proveedor", "cargar");
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
                                    Object[][] ciudades = Utils.getArrayOfJSONObject(marcaO, "ciudadM", new String[]{"idciudad", "nombre"});

                                    formulario = null;
                                    formulario = new EditarNuevoProveedorForm(null,"","", "", "", "", "", "", "", "", "", ciudades, Proveedor.this);
                                    formulario.show();
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

    private void cargarDatosEditarProveedor(String idproveedor) {
        String enlace = "php/Proveedor.php?funcion=BuscarProveedorPorId&idproveedor=" + idproveedor;
        Utils.setErrorPrincipal("Cargando parametros de nuevo Proveedor", "cargar");
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
                                    Object[][] ciudades = Utils.getArrayOfJSONObject(marcaO, "ciudadM", new String[]{"idciudad", "nombre"});
                                    String idproveedor = Utils.getStringOfJSONObject(marcaO, "idproveedor");
                                    String codigo = Utils.getStringOfJSONObject(marcaO, "codigo");
                                    String ciudad = Utils.getStringOfJSONObject(marcaO, "idciudad");
                                    String nombre = Utils.getStringOfJSONObject(marcaO, "nombre");
                                    String representante = Utils.getStringOfJSONObject(marcaO, "representante");
                                    String telefono = Utils.getStringOfJSONObject(marcaO, "telefono");
                                    String direccion = Utils.getStringOfJSONObject(marcaO, "direccion");
                                    String web = Utils.getStringOfJSONObject(marcaO, "web");
                                    String pais = Utils.getStringOfJSONObject(marcaO, "pais");
                                    String email = Utils.getStringOfJSONObject(marcaO, "email");
                                    String observacion = Utils.getStringOfJSONObject(marcaO, "observacion");
//                                    String  = Utils.getStringOfJSONObject(marcaO, "estado");
                                    formulario = null;
                                    formulario = new EditarNuevoProveedorForm(idproveedor, nombre,representante, codigo, ciudad, pais, telefono, web, email, direccion, observacion, ciudades, Proveedor.this);
                                    formulario.show();
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


    private void aniadirListenersProveedor() {
        //**************************************************
        //***********ELIMINAR ROL
        //**************************************************

        eliminarProveedor.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("idproveedor");
                    MessageBox.confirm("Eliminar Proveedor", "Realmente desea eliminar esta Proveedor??", new MessageBox.ConfirmCallback() {

                        public void execute(String btnID) {
                            if (btnID.equalsIgnoreCase("yes")) {
                                //eliminar
                                String enlace = "php/Proveedor.php?funcion=EliminarProveedor&idproveedor=" + selecionado;
                                Utils.setErrorPrincipal("Eliminando el Proveedor", "cargar");
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
                                                    reload();
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
                    MessageBox.alert("No hay Proveedor selecionado  y/o selecciono mas de uno.");
                }
                eliminarProveedor.setPressed(false);
            }
        });
        //**************************************************
        //***********NUEVO PROVEEDOR
        //**
        nuevoProveedor.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                CargarNuevaProveedor();
            }
        });

        //**************************************************
        //***********EDITAR PROVEEDOR
        //**************************************************
        editarProveedor.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
                            String idproveedor = records[0].getAsString("idproveedor");
                            cargarDatosEditarProveedor(idproveedor);
                        } else {

                            Utils.setErrorPrincipal("Por favor seleccione un Proveedor para editar", "error");
                        }

                    }
                });

        cambiarestado.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
                            String idproveedor = records[0].getAsString("idproveedor");

                        } else {

                            Utils.setErrorPrincipal("seleccione un proveedor para cambiar estado", "error");
                        }

                    }
                });



        //**************************************************
        //*********** LISTENERS DE LA TABLA
        //**************************************************
        grid.addListener(
                new PanelListenerAdapter() {

                    @Override
                    public void onRender(Component component) {
                        store.load(0, 100);
                    }
                });
        grid.addGridCellListener(
                new GridCellListenerAdapter() {

                    @Override
                    public void onCellClick(GridPanel grid, int rowIndex, int colIndex, EventObject e) {
                        if (grid.getColumnModel().getDataIndex(colIndex).equals("indoor")) {
                            Record record = grid.getStore().getAt(rowIndex);
                            record.set("indoor", !record.getAsBoolean("indoor"));
                        }

                    }
                });

    }

    private void verReporte(String enlace) {
        ReporteMediaCartaChorroWindow print = new ReporteMediaCartaChorroWindow(enlace);
        print.show();
    }
}
