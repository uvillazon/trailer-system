/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.sistema;

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
import com.gwtext.client.data.DataProxy;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.JsonReader;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.ScriptTagProxy;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.PagingToolbar;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.QuickTipsConfig;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.EditorGridPanel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.event.GridCellListenerAdapter;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.widgets.grid.BaseColumnConfig;
import com.gwtext.client.widgets.grid.CheckboxColumnConfig;
import com.gwtext.client.widgets.grid.CheckboxSelectionModel;
import com.gwtext.client.widgets.grid.RowNumberingColumnConfig;
import org.trailer.client.util.Conector;
import org.trailer.client.util.Utils;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;
//import org.trailer.client.marca.EditarMarcaForm;
import org.trailer.client.util.ReporteMediaCartaChorroWindow;
//import org.selkis.client.util.Utils;

/**
 *
 * @author Administrador
 */
public class Proceso extends Panel {

    private GridPanel grid;
    private ColumnConfig nombreColumn;
    //private ColumnConfig estadoColumn;
    private final int ANCHO = Utils.getScreenWidth() - 24;
    private final int ALTO = Utils.getScreenHeight() - 270;
    private ToolbarButton nuevoProceso;
    private ToolbarButton editarProceso;
    private ToolbarButton elminarProceso;
    private ToolbarButton reporteProceso;
    private EditarProcesoForm formulario;
    protected ExtElement ext_element;
    private JsonReader reader;
    private CheckboxSelectionModel cbSelectionModel;
    private Store store;
    private String selecionado;
    private BaseColumnConfig[] columns;
    private ColumnModel columnModel;
    private ColumnConfig idColumn;
    private ColumnConfig codigoColumn;
    private ColumnConfig nombColumn;
    private ColumnConfig tipoColumn;
    private ColumnConfig telefonoColumn;
    private ColumnConfig encargadoColumn;
    private ColumnConfig webColumn;
    // private ColumnConfig tipoEmColumn;
    private ColumnConfig celularColumn;

    public Proceso() {
        this.setClosable(true);
        this.setId("TPfun1012");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Procesos");
        onModuleLoad();
    }

    public void onModuleLoad() {

        DataProxy dataProxy = new ScriptTagProxy("php/Proceso.php?funcion=ListarProcesos");
        final RecordDef recordDef = new RecordDef(new FieldDef[]{
                    new StringFieldDef("idproceso"),
                    new StringFieldDef("codigo"),
                    new StringFieldDef("nombre"),
                    new StringFieldDef("tipo"),
                    new StringFieldDef("detalle"),});
        reader = new JsonReader(recordDef);
        reader.setRoot("resultado");
        reader.setTotalProperty("totalCount");

        store = new Store(dataProxy, reader, true);

        /* columnade idusuario  */

        /* columnade nombre  */
        idColumn = new ColumnConfig("Id Proceso", "idproceso", (ANCHO / 4), true);

        codigoColumn = new ColumnConfig("Codigo", "codigo", (ANCHO / 4), true);
        codigoColumn.setId("expandible");
        nombColumn = new ColumnConfig("Nombres", "nombre", (ANCHO / 4), true);
        tipoColumn = new ColumnConfig("Tipo", "tipo", (ANCHO / 4), true);
        encargadoColumn = new ColumnConfig("Detalle", "detalle", (ANCHO / 4), true);



        cbSelectionModel = new CheckboxSelectionModel();
        columns = new BaseColumnConfig[]{
                    new RowNumberingColumnConfig(),
                    new CheckboxColumnConfig(cbSelectionModel),
                    //column ID is company which is later used in setAutoExpandColumn
                    //                                        idColumn,
                    //                    idColumn,
                    codigoColumn,
                    nombColumn,
                    tipoColumn,
                    encargadoColumn, // estadoColumn
                };

        columnModel = new ColumnModel(columns);

        grid = new EditorGridPanel();
        grid.setId("grid-lista-Proceso");
        grid.setWidth(ANCHO);
        grid.setHeight(ALTO);
        grid.setTitle("Lista de proceso");
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
                Record[] records = cbSelectionModel.getSelections();


                selecionado = records[0].getAsString("idproceso");
                String enlTemp = "funcion=reporteprocesoHTML&idempleado=" + selecionado;
                verReporte(enlTemp);
//                    Window.alert(enlTemp);
            }
        });

//        grid.a


        nuevoProceso = new ToolbarButton("Nuevo");

        nuevoProceso.setEnableToggle(true);
        QuickTipsConfig tipsConfig1 = new QuickTipsConfig();

        tipsConfig1.setText("Crear nuevo Proceso");

        nuevoProceso.setTooltip(tipsConfig1);

        editarProceso = new ToolbarButton("Editar");
        editarProceso.setEnableToggle(true);
        QuickTipsConfig tipsConfig = new QuickTipsConfig();
        tipsConfig.setText("Editar Datos Proceso");
        editarProceso.setTooltip(tipsConfig);


        elminarProceso = new ToolbarButton("Eliminar");
        elminarProceso.setEnableToggle(true);
        QuickTipsConfig tipsConfig2 = new QuickTipsConfig();
        tipsConfig2.setText("Eliminar Proceso");
        elminarProceso.setTooltip(tipsConfig2);

        reporteProceso = new ToolbarButton("Detalle");
        reporteProceso.setEnableToggle(true);
        QuickTipsConfig tipsConfig3 = new QuickTipsConfig();
        tipsConfig3.setText("Detalle(es)");

        reporteProceso.setTooltip(tipsConfig2);
        PagingToolbar pagingToolbar = new PagingToolbar(store);

        pagingToolbar.setPageSize(100);

        pagingToolbar.setDisplayInfo(true);

        pagingToolbar.setDisplayMsg("Mostrando {0} - {1} de {2}");

        pagingToolbar.setEmptyMsg("No topics to display");

        pagingToolbar.addSeparator();

        pagingToolbar.addButton(nuevoProceso);

        pagingToolbar.addSeparator();

        pagingToolbar.addButton(editarProceso);

        pagingToolbar.addSeparator();

        pagingToolbar.addButton(elminarProceso);

        pagingToolbar.addSeparator();

        pagingToolbar.addButton(reporteProceso);

        pagingToolbar.addSeparator();

        grid.setBottomToolbar(pagingToolbar);

        add(grid);
        //panel.add(grid);

        aniadirListenersProveedor();

    //RootPanel.get().add(panel);
    }

    public GridPanel getGrid() {
        return grid;
    }

    public void reload() {
        store.reload();
        grid.reconfigure(store, columnModel);
        grid.getView().refresh();
    }

    public void setGrid(GridPanel grid) {
        this.grid = grid;
    }

    private void cargarDatosNuevoProceso() {

        String enlace = "php/Proceso.php?funcion=CargarNuevoProceso";
        Utils.setErrorPrincipal("Cargando parametros", "cargar");
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

//                                    Object[][] categorias = Utils.getArrayOfJSONObject(marcaO, "categoriaM", new String[]{"idcategoria", "nombre"});
                                    Object[][] encargadoM = Utils.getArrayOfJSONObject(marcaO, "encargadoM", new String[]{"idempleado", "nombres"});
                                    Object[][] itemM = Utils.getArrayOfJSONObject(marcaO, "itemM", new String[]{"id", "nombre"});

//                                   scenes = Utils.getArrayOfJSONObject(marcaO, "almacenM", new String[]{"idalmacen", "nombre"});
                                    formulario = null;
//                                    MessageBox.alert("Envio parametros");
                                    formulario = new EditarProcesoForm(null, "", "", "", "", "", "", encargadoM, itemM, "", Proceso.this, "","");
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

    private void cargarDatosEditarEmpleado(String idproceso) {

        String enlace = "php/Proceso.php?funcion=BuscarProcesoPorId&idproceso=" + idproceso;
        Utils.setErrorPrincipal("Cargando parametros...", "cargar");
        final Conector conec = new Conector(enlace, false);

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

                            JSONValue productoV = jsonObject.get("resultado");
                            JSONObject productoO;
                            if ((productoO = productoV.isObject()) != null) {
                                String idproceso = Utils.getStringOfJSONObject(productoO, "idproceso");
                                String codigo = Utils.getStringOfJSONObject(productoO, "codigo");
                                String nombre = Utils.getStringOfJSONObject(productoO, "nombre");
                                String tipo = Utils.getStringOfJSONObject(productoO, "tipo");
                                String estado = Utils.getStringOfJSONObject(productoO, "estado");
                                String idempleado = Utils.getStringOfJSONObject(productoO, "idempleado");
                                String empleado = Utils.getStringOfJSONObject(productoO, "encargado");
                                String detalle = Utils.getStringOfJSONObject(productoO, "detalle");
                                String item = Utils.getStringOfJSONObject(productoO, "item");
                                String formula = Utils.getStringOfJSONObject(productoO, "formula");
                                Object[][] encargados = Utils.getArrayOfJSONObject(productoO, "encargadoM", new String[]{"idempleado", "nombres"});
                                Object[][] items = Utils.getArrayOfJSONObject(productoO, "itemM", new String[]{"id", "nombre"});



//                                formulario = null;
                                formulario = new EditarProcesoForm(idproceso, codigo, nombre, empleado, idempleado, detalle, tipo, encargados,items, estado, Proceso.this, item,formula);
////                                formulario = new EditarEmpleadoForm(null, "", "", "", "", "", "", "", "", "", null, almacenes, Empleado.this);
                                formulario.show();
                            } else {
                                Utils.setErrorPrincipal("Error en el objeto proveedor", "error");
                            }
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

    private void aniadirListenersProveedor() {
        //**************************************************
        //***********ELIMINAR ROL
        //**************************************************

        elminarProceso.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("idproceso");
                    MessageBox.confirm("Eliminar Proveedor", "Realmente desea eliminar este Proceso??", new MessageBox.ConfirmCallback() {

                        public void execute(String btnID) {
                            if (btnID.equalsIgnoreCase("yes")) {
                                //eliminar
                                String enlace = "php/Proceso.php?funcion=EliminarProceso&idproceso=" + selecionado;
                                Utils.setErrorPrincipal("Eliminando el Proceso", "cargar");
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
                    MessageBox.alert("No hay venta selecionado para editar y/o selecciono mas de uno.");
                }
                elminarProceso.setPressed(false);
            }
        });
        nuevoProceso.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                cargarDatosNuevoProceso();
            }
        });
        editarProceso.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    String idproceso = records[0].getAsString("idproceso");
                    cargarDatosEditarEmpleado(idproceso);
                } else {
                    Utils.setErrorPrincipal("Por favor seleccione un producto para editar", "error");
                }

            }
        });
        reporteProceso.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        Record[] records = cbSelectionModel.getSelections();

                        if (records.length == 1) {
                            selecionado = records[0].getAsString("idproceso");
                            String enlTemp = "funcion=reporteprocesoHTML&idproceso=" + selecionado;
                            verReporte(enlTemp);

                        } else {
                            MessageBox.alert("No hay proveedor selecionado para ver el reporte.");
                        }

                        reporteProceso.setPressed(false);
                    }
                });

        //**************************************************
        //***********EDITAR ROL
        //**************************************************


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

