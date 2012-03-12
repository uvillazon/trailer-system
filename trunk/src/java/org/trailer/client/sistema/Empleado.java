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
public class Empleado extends Panel {

    private GridPanel grid;
    private ColumnConfig nombreColumn;
    //private ColumnConfig estadoColumn;
    private final int ANCHO = Utils.getScreenWidth() - 24;
    private final int ALTO = Utils.getScreenHeight() - 270;
    private ToolbarButton nuevoEmpleado;
    private ToolbarButton editarEmpleado;
    private ToolbarButton elminarEmpleado;
    private ToolbarButton reporteEmpleado;
    private EditarEmpleadoForm formulario;
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
    private ColumnConfig apellidoColumn;
    private ColumnConfig telefonoColumn;
    private ColumnConfig emailColumn;
    private ColumnConfig webColumn;
    // private ColumnConfig tipoEmColumn;
    private ColumnConfig celularColumn;

    public Empleado() {
        this.setClosable(true);
        this.setId("TPfun1004");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Personal");
        onModuleLoad();
    }

    public void onModuleLoad() {

        DataProxy dataProxy = new ScriptTagProxy("php/Empleado.php?funcion=ListarEmpleados");
        final RecordDef recordDef = new RecordDef(new FieldDef[]{
                    new StringFieldDef("idempleado"),
                    new StringFieldDef("codigo"),
                    new StringFieldDef("nombres"),
                    new StringFieldDef("apellidos"),
                    new StringFieldDef("tipoempleado"),
                    new StringFieldDef("celular"),
                    new StringFieldDef("telefeno"),
                    new StringFieldDef("email"),});
        reader = new JsonReader(recordDef);
        reader.setRoot("resultado");
        reader.setTotalProperty("totalCount");

        store = new Store(dataProxy, reader, true);

        /* columnade idusuario  */

        /* columnade nombre  */
        idColumn = new ColumnConfig("Id Empleado", "idempleado", (ANCHO / 6), true);

        codigoColumn = new ColumnConfig("Codigo", "codigo", (ANCHO / 6), true);
        codigoColumn.setId("expandible");
        nombColumn = new ColumnConfig("Nombres", "nombres", (ANCHO / 6), true);
        apellidoColumn = new ColumnConfig("Apellidos", "apellidos", (ANCHO / 6), true);
        //  tipoEmColumn = new ColumnConfig("Tipo Empleado", "tipoempleado", (ANCHO / 8), true);
        telefonoColumn = new ColumnConfig("Telefono", "telefeno", (ANCHO / 6), true);
        celularColumn = new ColumnConfig("Celular", "celular", (ANCHO / 6), true);
        //estadoColumn = new ColumnConfig("Estado", "estado", (ANCHO / 8), true);
        emailColumn = new ColumnConfig("Email", "email", (ANCHO / 6), true);



        cbSelectionModel = new CheckboxSelectionModel();
        columns = new BaseColumnConfig[]{
                    new RowNumberingColumnConfig(),
                    new CheckboxColumnConfig(cbSelectionModel),
                    //column ID is company which is later used in setAutoExpandColumn
                    //                                        idColumn,
                    //                    idColumn,
                    codigoColumn,
                    nombColumn,
                    apellidoColumn,
                    //   tipoEmColumn,
                    telefonoColumn,
                    celularColumn,
                    emailColumn, // estadoColumn
                };

        columnModel = new ColumnModel(columns);

        grid = new EditorGridPanel();
        grid.setId("grid-lista-Empleados");
        grid.setWidth(ANCHO);
        grid.setHeight(ALTO);
        grid.setTitle("Lista de Empleados");
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


                selecionado = records[0].getAsString("idempleado");
                String enlTemp = "funcion=reporteempleadoHTML&idempleado=" + selecionado;
                verReporte(enlTemp);
//                    Window.alert(enlTemp);
            }
        });

//        grid.a


        nuevoEmpleado = new ToolbarButton("Nuevo");

        nuevoEmpleado.setEnableToggle(true);
        QuickTipsConfig tipsConfig1 = new QuickTipsConfig();

        tipsConfig1.setText("Crear nuevo Personal");

        nuevoEmpleado.setTooltip(tipsConfig1);

        editarEmpleado = new ToolbarButton("Editar");
        editarEmpleado.setEnableToggle(true);
        QuickTipsConfig tipsConfig = new QuickTipsConfig();
        tipsConfig.setText("Editar Datos Personal");
        editarEmpleado.setTooltip(tipsConfig);


        elminarEmpleado = new ToolbarButton("Eliminar");
        elminarEmpleado.setEnableToggle(true);
        QuickTipsConfig tipsConfig2 = new QuickTipsConfig();
        tipsConfig2.setText("Eliminar Personal");
        elminarEmpleado.setTooltip(tipsConfig2);

        reporteEmpleado = new ToolbarButton("Reporte");
        reporteEmpleado.setEnableToggle(true);
        QuickTipsConfig tipsConfig3 = new QuickTipsConfig();
        tipsConfig3.setText("Reporte(es)");

        reporteEmpleado.setTooltip(tipsConfig2);
        PagingToolbar pagingToolbar = new PagingToolbar(store);

        pagingToolbar.setPageSize(100);

        pagingToolbar.setDisplayInfo(true);

        pagingToolbar.setDisplayMsg("Mostrando {0} - {1} de {2}");

        pagingToolbar.setEmptyMsg("No topics to display");

        pagingToolbar.addSeparator();

        pagingToolbar.addButton(nuevoEmpleado);

        pagingToolbar.addSeparator();

        pagingToolbar.addButton(editarEmpleado);

        pagingToolbar.addSeparator();

        pagingToolbar.addButton(elminarEmpleado);

        pagingToolbar.addSeparator();

        pagingToolbar.addButton(reporteEmpleado);

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

    private void cargarDatosNuevoEmpleado() {

        String enlace = "php/Empleado.php?funcion=CargarNuevoEmpleado";
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
                                    Object[][] ciudadesM = Utils.getArrayOfJSONObject(marcaO, "ciudadM", new String[]{"idciudad", "nombre"});
                                    Object[][] cargosM = Utils.getArrayOfJSONObject(marcaO, "cargosM", new String[]{"idcargo", "nombre"});

//                                   scenes = Utils.getArrayOfJSONObject(marcaO, "almacenM", new String[]{"idalmacen", "nombre"});
                                    formulario = null;
//                                    MessageBox.alert("Envio parametros");
                                    formulario = new EditarEmpleadoForm(null, "", "", "", "", "", "", "", cargosM, "", ciudadesM, "", "", "", "", Empleado.this);
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

    private void cargarDatosEditarEmpleado(String idempleado) {

        String enlace = "php/Empleado.php?funcion=BuscarEmpleadoPorId&idempleado=" + idempleado;
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
                                String idempleado = Utils.getStringOfJSONObject(productoO, "idempleado");
                                String codigo = Utils.getStringOfJSONObject(productoO, "codigo");
                                String nombre = Utils.getStringOfJSONObject(productoO, "nombre");
                                String apellido = Utils.getStringOfJSONObject(productoO, "apellido");
                                String idciudad = Utils.getStringOfJSONObject(productoO, "idciudad");
                                String telefono = Utils.getStringOfJSONObject(productoO, "telefono");
                                String celular = Utils.getStringOfJSONObject(productoO, "celular");
                                String direccion = Utils.getStringOfJSONObject(productoO, "direccion");
                                String idcargo = Utils.getStringOfJSONObject(productoO, "idcargo");
                                String email = Utils.getStringOfJSONObject(productoO, "email");
                                String fechainicio = Utils.getStringOfJSONObject(productoO, "fechainicio");
                                String refnombre = Utils.getStringOfJSONObject(productoO, "refnombre");
                                String reftelefono = Utils.getStringOfJSONObject(productoO, "reftelefono");

                                Object[][] cargos = Utils.getArrayOfJSONObject(productoO, "cargosM", new String[]{"idcargo", "nombre"});
                                Object[][] ciudades = Utils.getArrayOfJSONObject(productoO, "ciudadM", new String[]{"idciudad", "nombre"});



//                                formulario = null;
                                formulario = new EditarEmpleadoForm(idempleado, codigo, nombre, apellido, telefono, celular, direccion, email, cargos, idcargo, ciudades, idciudad, fechainicio, refnombre, reftelefono, Empleado.this);
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

        elminarEmpleado.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("idempleado");
                    MessageBox.confirm("Eliminar Proveedor", "Realmente desea eliminar este Proveedor??", new MessageBox.ConfirmCallback() {

                        public void execute(String btnID) {
                            if (btnID.equalsIgnoreCase("yes")) {
                                //eliminar
                                String enlace = "php/Empleado.php?funcion=EliminarEmpleado&idempleado=" + selecionado;
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
                    MessageBox.alert("No hay venta selecionado para editar y/o selecciono mas de uno.");
                }
                elminarEmpleado.setPressed(false);
            }
        });
        nuevoEmpleado.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                cargarDatosNuevoEmpleado();
            }
        });
        editarEmpleado.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    String idempleado = records[0].getAsString("idempleado");
                    cargarDatosEditarEmpleado(idempleado);
                } else {
                    Utils.setErrorPrincipal("Por favor seleccione un producto para editar", "error");
                }

            }
        });
        reporteEmpleado.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        Record[] records = cbSelectionModel.getSelections();

                        if (records.length == 1) {
                            selecionado = records[0].getAsString("idempleado");
                            String enlTemp = "funcion=reporteEmpleadoHTML&idempleado=" + selecionado;
                            verReporte(enlTemp);

                        } else {
                            MessageBox.alert("No hay proveedor selecionado para ver el reporte.");
                        }

                        reporteEmpleado.setPressed(false);
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

