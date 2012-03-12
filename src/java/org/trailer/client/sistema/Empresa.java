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
import com.gwtext.client.core.UrlParam;
import com.gwtext.client.widgets.grid.BaseColumnConfig;
import com.gwtext.client.widgets.grid.CheckboxColumnConfig;
import com.gwtext.client.widgets.grid.CheckboxSelectionModel;
import com.gwtext.client.widgets.grid.RowNumberingColumnConfig;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.event.TextFieldListenerAdapter;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;
import org.trailer.client.util.BuscadorToolBar;
import org.trailer.client.util.Conector;
import org.trailer.client.util.Utils;
import org.trailer.client.util.ReporteMediaCartaChorroWindow;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.PagingToolbar;
import com.gwtext.client.widgets.QuickTipsConfig;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.data.*;
import com.gwtext.client.widgets.form.Checkbox;

/**
 *
 * @author buggy
 */
public class Empresa extends Panel {

    private GridPanel grid;
    private ColumnConfig idColumn;
    private ColumnConfig codigoColumn;
    private ColumnConfig ciudadColumn;
    private ColumnConfig nombreColumn;
    private ColumnConfig telefonoColumn;
    private ColumnConfig direccionColumn;
    private ColumnConfig responsableColumn;
    private ColumnConfig nitColumn;
    private final int ANCHO = Utils.getScreenWidth() - 24;
    private final int ALTO = Utils.getScreenHeight() - 270;
    private ToolbarButton editarEmpresa;
    private ToolbarButton eliminarEmpresa;
    private ToolbarButton nuevoEmpresa;
    private BuscadorToolBar buscadorToolBar;
    protected String buscarcodigo;
    protected String buscarnombre;
    protected String buscarciudad;
    private ToolbarButton buscar;
    PagingToolbar pagingToolbar = new PagingToolbar();
//    private ToolbarButton reporteCliente;
    private ToolbarButton responsable;
    private EditarNuevaEmpresa formulario;

    protected ExtElement ext_element;
    CheckboxSelectionModel cbSelectionModel;
    Store store;
    private String selecionado;
    private BaseColumnConfig[] columns;
    ColumnModel columnModel;

    public Empresa() {
        this.setClosable(true);
        this.setId("TPfun1007");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Empresas");
        onModuleLoad();
    }

    public void onModuleLoad() {

        DataProxy dataProxy = new ScriptTagProxy("php/Empresa.php?funcion=ListarEmpresas");
        final RecordDef recordDef = new RecordDef(new FieldDef[]{
                    new StringFieldDef("idempresa"),
                    new StringFieldDef("codigo"),
                    new StringFieldDef("nombre"),
                    new StringFieldDef("nit"),
                    new StringFieldDef("ciudad"),
                    new StringFieldDef("responsable"),
                    new StringFieldDef("telefono"),
                    new StringFieldDef("direccion")
                });
        JsonReader reader = new JsonReader(recordDef);
        reader.setRoot("resultado");
        reader.setTotalProperty("totalCount");

        store = new Store(dataProxy, reader, true);
        idColumn = new ColumnConfig("Id Empresa", "idempresa",(ANCHO / 8), true);
        idColumn.setWidth(100);
        codigoColumn = new ColumnConfig("Codigo", "codigo",(ANCHO / 8));

        codigoColumn.setId("expandible");
        ciudadColumn = new ColumnConfig("Ciudad", "ciudad", (ANCHO / 8), true);
        nombreColumn = new ColumnConfig("Nombre", "nombre", (ANCHO / 8), true);
        telefonoColumn = new ColumnConfig("Telefono", "telefono", (ANCHO / 8), true);
        direccionColumn = new ColumnConfig("Direccion", "direccion", (ANCHO / 8), true);
        nitColumn = new ColumnConfig("Nit", "nit", (ANCHO / 8), true);
        responsableColumn = new ColumnConfig("Responsable", "responsable", (ANCHO / 8), true);

        cbSelectionModel = new CheckboxSelectionModel();
        columns = new BaseColumnConfig[]{
                    new RowNumberingColumnConfig(),
                    new CheckboxColumnConfig(cbSelectionModel),
                    codigoColumn,
                    nombreColumn,                    
                    nitColumn,
                    ciudadColumn,
                    responsableColumn,
                    telefonoColumn,
                    direccionColumn
                };

        columnModel = new ColumnModel(columns);

        grid = new EditorGridPanel();
        grid.setId("grid-lista-Empresa");
        grid.setWidth(ANCHO);
        grid.setHeight(ALTO);
        grid.setTitle("Empresas");
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


                selecionado = records[0].getAsString("idempresa");
                String enlTemp = "funcion=reporteEmpresaHTML&idempresa=" + selecionado;
                verReporte(enlTemp);
//                    Window.alert(enlTemp);
            }
        });

        nuevoEmpresa = new ToolbarButton("Nuevo");
        nuevoEmpresa.setEnableToggle(true);
        QuickTipsConfig tipsConfig1 = new QuickTipsConfig();
        tipsConfig1.setText("Crear Empresa");
        nuevoEmpresa.setTooltip(tipsConfig1);

        editarEmpresa = new ToolbarButton("Editar");
        editarEmpresa.setEnableToggle(true);
        QuickTipsConfig tipsConfig = new QuickTipsConfig();
        tipsConfig.setText("Editar Empresa");
        editarEmpresa.setTooltip(tipsConfig);

        eliminarEmpresa = new ToolbarButton("Eliminar");
        eliminarEmpresa.setEnableToggle(true);
        QuickTipsConfig tipsConfig2 = new QuickTipsConfig();
        tipsConfig2.setText("Eliminar Empresa");
        eliminarEmpresa.setTooltip(tipsConfig2);

        QuickTipsConfig tipsConfig3 = new QuickTipsConfig();
//        reporteCliente = new ToolbarButton("Reporte");
//        reporteCliente.setEnableToggle(true);
//        tipsConfig3.setText("Reporte Cliente");
//        reporteCliente.setTooltip(tipsConfig3);


         responsable = new ToolbarButton("Responsable");
        responsable.setEnableToggle(true);
        QuickTipsConfig tipsConfig4 = new QuickTipsConfig();
        tipsConfig4.setText("Cambiar Responsable Pendiente , Inactivo");
        responsable.setTooltip(tipsConfig3);


        pagingToolbar = new PagingToolbar(store);
        pagingToolbar.setPageSize(100);
        pagingToolbar.setDisplayInfo(true);
        pagingToolbar.setDisplayMsg("Mostrando {0} - {1} de {2}");
        pagingToolbar.setEmptyMsg("No topics to display");
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(nuevoEmpresa);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(editarEmpresa);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(eliminarEmpresa);
//        pagingToolbar.addSeparator();
//        pagingToolbar.addButton(reporteCliente);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(responsable);
        pagingToolbar.addSeparator();

        String items[] = {"Codigo", "Nombre", "Ciudad"};
        String tiposItems[] = {"text", "text", "text"};
        buscadorToolBar = new BuscadorToolBar(items, tiposItems);
        grid.setTopToolbar(buscadorToolBar.getToolbar());
        grid.setBottomToolbar(pagingToolbar);
        buscar = buscadorToolBar.getBuscar();
//
        addListenersBuscador();

        addListenersBuscadoresText();

//        grid.setBottomToolbar(pagingToolbar);

        add(grid);
        //panel.add(grid);
        aniadirListenersEmpresa();
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

    private void CargarNuevaEmpresa() {
        String enlace = "php/Empresa.php?funcion=CargarNuevaEmpresa";
        Utils.setErrorPrincipal("Cargando parametros de Nueva Empresa", "cargar");
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
                                    formulario = new EditarNuevaEmpresa(null,"","", "", "", "", "", "", "", "", "", "" , "" ,ciudades, Empresa.this);
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

    private void cargarDatosEditarEmpresa(String idempresa) {
        String enlace = "php/Empresa.php?funcion=BuscarEmpresaPorId&idempresa=" + idempresa;
        Utils.setErrorPrincipal("Cargando parametros de nueva empresa", "cargar");
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
                                    String idempresa = Utils.getStringOfJSONObject(marcaO, "idempresa");
                                    String codigo = Utils.getStringOfJSONObject(marcaO, "codigo");
                                    String ciudad = Utils.getStringOfJSONObject(marcaO, "idciudad");
                                    String nombre = Utils.getStringOfJSONObject(marcaO, "nombre");
                                    String responsable = Utils.getStringOfJSONObject(marcaO, "responsable");
                                    String telefono = Utils.getStringOfJSONObject(marcaO, "telefono");
                                    String direccion = Utils.getStringOfJSONObject(marcaO, "direccion");
                                    String nit = Utils.getStringOfJSONObject(marcaO, "nit");
                                    String fax = Utils.getStringOfJSONObject(marcaO, "fax");
                                    String email = Utils.getStringOfJSONObject(marcaO, "email");
                                    String observacion = Utils.getStringOfJSONObject(marcaO, "observacion");
                                     String fecha = Utils.getStringOfJSONObject(marcaO, "fecha");
                                      String web = Utils.getStringOfJSONObject(marcaO, "web");
//                                    String  = Utils.getStringOfJSONObject(marcaO, "estado");
                                    formulario = null;
                                    formulario = new EditarNuevaEmpresa(idempresa, nombre,responsable, codigo, ciudad, telefono, nit, fax, email, direccion, observacion,fecha,web, ciudades, Empresa.this);
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


    private void aniadirListenersEmpresa() {
        //**************************************************
        //***********ELIMINAR ROL
        //**************************************************

        eliminarEmpresa.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("idempresa");
                    MessageBox.confirm("Eliminar Empresa", "Realmente desea eliminar esta Empresa??", new MessageBox.ConfirmCallback() {

                        public void execute(String btnID) {
                            if (btnID.equalsIgnoreCase("yes")) {
                                //eliminar
                                String enlace = "php/Empresa.php?funcion=EliminarEmpresa&idempresa=" + selecionado;
                                Utils.setErrorPrincipal("Eliminando el Empresa", "cargar");
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
                    MessageBox.alert("No hay Empresa selecionado  y/o selecciono mas de uno.");
                }
                eliminarEmpresa.setPressed(false);
            }
        });
        //**************************************************
        //***********NUEVO Empresa
        //**
        nuevoEmpresa.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                CargarNuevaEmpresa();
            }
        });

        //**************************************************
        //***********EDITAR Empresa
        //**************************************************
        editarEmpresa.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
                            String idempresa = records[0].getAsString("idempresa");
                            cargarDatosEditarEmpresa(idempresa);
                        } else {

                            Utils.setErrorPrincipal("Por favor seleccione una Empresa para editar", "error");
                        }

                    }
                });
//        reporteCliente.addListener(
//                new ButtonListenerAdapter() {
//
//                    @Override
//                    public void onClick(Button button, EventObject e) {
//                        Record[] records = cbSelectionModel.getSelections();
//
//                        if (records.length == 1) {
//                            selecionado = records[0].getAsString("idcliente");
//                            String enlTemp = "funcion=reporteclienteHTML&idcliente=" + selecionado;
//                            verReporte(enlTemp);
//
//                        } else {
//                            MessageBox.alert("No hay marca selecionado para ver el reporte.");
//                        }
//
//                        reporteCliente.setPressed(false);
//                    }
//                });
        responsable.addListener(
                new ButtonListenerAdapter() {

                    @Override
                    public void onClick(Button button, EventObject e) {
                        Record[] records = cbSelectionModel.getSelections();
                        if (records.length == 1) {
                            String idempresa = records[0].getAsString("idempresa");

                        } else {

                            Utils.setErrorPrincipal("seleccione una empresa para cambiar estado", "error");
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

//    public String getLinksaveUpdate(String idrol, String nombre, String estado, Object[] seleccionados) {
//        String dev = "";
//        if (seleccionados.length >= 1) {
//
//            if (idrol == null) {
//                idrol = "nuevo";
//            }
//            JSONObject todos = new JSONObject();
//            todos.put("idrol", new JSONString(idrol));
//            todos.put("nombre", new JSONString(nombre));
//            todos.put("estado", new JSONString(estado));
//            JSONArray funcS = new JSONArray();
//            for (int i = 0; i < seleccionados.length; i++) {
//                Checkbox che = (Checkbox) seleccionados[i];
//                funcS.set(i, new JSONString(che.getId().substring(1)));
//            }
//            todos.put("funciones", funcS);
//            dev = todos.toString();
//        } else {
//            Utils.setErrorPrincipal("Por favor seleccione por lo menos una funacion", "error");
//        }
//        return dev;
//    }

    private void addListenersBuscador() {
        buscar.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                buscarSegunParametros();
            }
        });
    }

    private void addListenersBuscadoresText() {

        //*********************************************************************
        //***************BUSCADOR CODIGO************************************
        //*********************************************************************
        buscadorToolBar.getItemsText1().addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    buscarSegunParametros();
                //com.google.gwt.user.client.Window.alert("apreto el enter en el campo 1");
                }
            }
        });

        //*********************************************************************
        //***************BUSCADOR DE NOMBRE************************************
        //*********************************************************************
        buscadorToolBar.getItemsText2().addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    buscarSegunParametros();
                //com.google.gwt.user.client.Window.alert("apreto el enter en el campo 1");
                }
            }
        });

        //*********************************************************************
        //***************BUSCADOR DE CIUDAD************************************
        //*********************************************************************
        buscadorToolBar.getItemsText3().addListener(new TextFieldListenerAdapter() {

            @Override
            public void onSpecialKey(Field field, EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    buscarSegunParametros();
                //com.google.gwt.user.client.Window.alert("apreto el enter en el campo 1");
                }
            }
        });

    }

    public void buscarSegunParametros() {
        buscarcodigo = buscadorToolBar.getItemsText1().getText();
        buscarnombre = buscadorToolBar.getItemsText2().getText();
        buscarciudad = buscadorToolBar.getItemsText3().getText();
        store.reload(new UrlParam[]{
                    new UrlParam("start", 0), new UrlParam("limit", 100),
                    new UrlParam("buscarcodigo", buscarcodigo),
                    new UrlParam("buscarnombre", buscarnombre),
                    new UrlParam("buscarciudad", buscarciudad)
                }, false);
    }


    private void verReporte(String enlace) {
        ReporteMediaCartaChorroWindow print = new ReporteMediaCartaChorroWindow(enlace);
        print.show();
    }
}
