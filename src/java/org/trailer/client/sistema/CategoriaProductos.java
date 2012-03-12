/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.sistema;

import org.trailer.client.parametros.*;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
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
import com.gwtext.client.widgets.grid.BaseColumnConfig;
import com.gwtext.client.widgets.grid.CheckboxColumnConfig;
import com.gwtext.client.widgets.grid.CheckboxSelectionModel;
import com.gwtext.client.widgets.grid.RowNumberingColumnConfig;
import org.trailer.client.util.Conector;
import org.trailer.client.util.Utils;
import com.gwtext.client.widgets.grid.event.GridRowListenerAdapter;
import org.trailer.client.MainEntryPoint;
import org.trailer.client.util.KMenu;

/**
 *
 * @author Uvillazon
 */
public class CategoriaProductos extends Panel {

  
    private GridPanel grid;
    private final int ANCHO = Utils.getScreenWidth() - 24;
    private final int ALTO = Utils.getScreenHeight() - 270;
    private ToolbarButton nuevaCategoriaProducto;
    private ToolbarButton editarCategoriaProducto;
    private ToolbarButton eliminarCategoriaProducto;
   
    private JsonReader reader;
    private CheckboxSelectionModel cbSelectionModel;
    private Store store;
    private String selecionado;
    private BaseColumnConfig[] columns;
    private ColumnModel columnModel;
    private ColumnConfig idGrupoColumn;
    private ColumnConfig codigoGrupoColumn;
    private ColumnConfig nombreGrupoColumn;
    private ColumnConfig descripcionGrupoColumn;
    private EditarCategoriaProductoForm formulario;
    private Componentes formC;

    public CategoriaProductos() {

        
        this.setClosable(true);
        this.setId("TPfun1013");
        setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Categoria Productos");
        onModuleLoad();
    }

    private void onModuleLoad() {
        DataProxy dataProxy = new ScriptTagProxy("php/CategoriaProducto.php?funcion=ListarCategoriaProducto");
        final RecordDef recordDef = new RecordDef(new FieldDef[]{
                    new StringFieldDef("idcategoriaproducto"),
                    new StringFieldDef("nombre"),
                    new StringFieldDef("descripcion"),});
        reader = new JsonReader(recordDef);
        reader.setRoot("resultado");
        reader.setTotalProperty("totalCount");

        store = new Store(dataProxy, reader, true);

        idGrupoColumn = new ColumnConfig("Id grupo", "idcategoriaproducto", 200, true);
        codigoGrupoColumn = new ColumnConfig("Nombre", "nombre", 200, true);
        nombreGrupoColumn = new ColumnConfig("Nombre", "nombre", 500, true);
        //nombreGrupoColumn.setId("expandible");
        descripcionGrupoColumn = new ColumnConfig("Descripcion", "descripcion", 200, true);
        descripcionGrupoColumn.setId("expandible");
        cbSelectionModel = new CheckboxSelectionModel();
        columns = new BaseColumnConfig[]{
                    new RowNumberingColumnConfig(),
                    new CheckboxColumnConfig(cbSelectionModel),
                    nombreGrupoColumn,
                    descripcionGrupoColumn
                };
        columnModel = new ColumnModel(columns);

        grid = new EditorGridPanel();
        grid.setId("grid-lista-Categoria-Producto");
        grid.setWidth(ANCHO);
        grid.setHeight(ALTO);
        grid.setTitle("Lista de Categorias Productos");
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

            public void onRowDblClick(GridPanel grid, int rowIndex, EventObject e) {
                Window.alert("En contruccion: aqui saldra la informacion del rol en detalle");

            }
        });

//        grid.a


        nuevaCategoriaProducto = new ToolbarButton("Nuevo");
        nuevaCategoriaProducto.setEnableToggle(true);
        QuickTipsConfig tipsConfig1 = new QuickTipsConfig();
        tipsConfig1.setText("Crear nueva Categoria Producto");
        nuevaCategoriaProducto.setTooltip(tipsConfig1);

        editarCategoriaProducto = new ToolbarButton("Editar");
        editarCategoriaProducto.setEnableToggle(true);
        QuickTipsConfig tipsConfig = new QuickTipsConfig();
        tipsConfig.setText("Editar Categoria Producto");
        editarCategoriaProducto.setTooltip(tipsConfig);

        eliminarCategoriaProducto = new ToolbarButton("Eliminar");
        eliminarCategoriaProducto.setEnableToggle(true);
        QuickTipsConfig tipsConfig2 = new QuickTipsConfig();
        tipsConfig2.setText("Eliminar Categoria Producto");
        eliminarCategoriaProducto.setTooltip(tipsConfig2);




        PagingToolbar pagingToolbar = new PagingToolbar(store);
        pagingToolbar.setPageSize(100);
        pagingToolbar.setDisplayInfo(true);
        pagingToolbar.setDisplayMsg("Mostrando {0} - {1} de {2}");
        pagingToolbar.setEmptyMsg("No topics to display");
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(nuevaCategoriaProducto);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(editarCategoriaProducto);
        pagingToolbar.addSeparator();
        pagingToolbar.addButton(eliminarCategoriaProducto);
     
        grid.setBottomToolbar(pagingToolbar);

        add(grid);
        //panel.add(grid);
        aniadirListenersCategoriaProducto();


    }

   

    private void aniadirListenersCategoriaProducto() {
        //**************************************************
        //***********ELIMINAR ROL
        //**************************************************

        eliminarCategoriaProducto.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    selecionado = records[0].getAsString("idcategoriaproducto");
                    MessageBox.confirm("Eliminar...", "Realmente desea eliminar??", new MessageBox.ConfirmCallback() {

                        public void execute(String btnID) {
                            if (btnID.equalsIgnoreCase("yes")) {
                                //eliminar
                                String enlace = "php/CategoriaProducto.php?funcion=EliminarCategoriaProducto&idcategoriaproducto=" + selecionado;
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
                eliminarCategoriaProducto.setPressed(false);
            }
        });
        nuevaCategoriaProducto.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                cargarDatosNuevoGrupo();
            }
        });
     

        editarCategoriaProducto.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                Record[] records = cbSelectionModel.getSelections();
                if (records.length == 1) {
                    String idcategoria = records[0].getAsString("idcategoriaproducto");
                    cargarDatosEditarCategoria(idcategoria);
                } else {
                    Utils.setErrorPrincipal("Por favor seleccione una categoria para editar", "error");
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

    public void reload() {
        store.reload();
        grid.reconfigure(store, columnModel);
        grid.getView().refresh();
    }

    private void cargarDatosNuevoGrupo() {

        formulario = null;
        formulario = new EditarCategoriaProductoForm(null, "", "",CategoriaProductos.this);
        // formulario = new EditarGrupoForm("", "", "", Grupo.this);
        formulario.show();

    }
    //**************************************************
    //***********EDITAR ROL
    //**************************************************

    private void cargarDatosEditarCategoria(String idcategoriaproducto) {
        String enlace = "php/CategoriaProducto.php?funcion=BuscarCategoriaProductoPorId&idcategoriaproducto=" + idcategoriaproducto;
        Utils.setErrorPrincipal("Cargando parametros para Categoria", "cargar");
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
                                String idcategoriaproducto = Utils.getStringOfJSONObject(productoO, "idcategoriaproducto");
                                String nombre = Utils.getStringOfJSONObject(productoO, "nombre");
                                String descripcion = Utils.getStringOfJSONObject(productoO, "descripcion");


                                formulario = null;
                                formulario = new EditarCategoriaProductoForm(idcategoriaproducto, nombre, descripcion, CategoriaProductos.this);
                                formulario.show();
                            } else {
                                Utils.setErrorPrincipal("Error en el objeto categoria", "error");
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
}
