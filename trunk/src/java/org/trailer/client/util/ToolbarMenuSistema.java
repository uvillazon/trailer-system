/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.util;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.PanelListenerAdapter;

import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.MenuItem;
import com.gwtext.client.widgets.menu.event.BaseItemListenerAdapter;


//import org.asia.client.util.FormularioReporte;
/**
 *
 * @author Karen Saavedra
 */
public class ToolbarMenuSistema {

    private Toolbar too_toolbar;
    private Menu men_menuSistema;
    private TabPanel tap_tabPanel;
    private BaseItemListenerAdapter bit_listener;
    private ToolbarButton tob_sistema;
    private ToolbarButton tob_almacen;
    private ToolbarButton tob_venta;
    private ToolbarButton tob_reporte;
    private int index;
    private Menu men_menuAlamcen;
    private Menu men_menuVenta;
    private Menu men_menuReporte;
    private String LISTA_USUARIO_TABBED = "1000_lista_usuario";
    private String LISTA_CLIENTE_TABBED = "2000_lista_cliente";
    private String LISTA_EMPLEADO_TABBED = "2100_lista_empleado";
    private String LISTA_CATEGORIA_TABBED = "2200_lista_categoria";
//    private String LISTA_MARCA_TABBED = "2300_lista_marca";
    private String LISTA_PRODUCTO_TABBED = "3000_lista_producto";
    private String LISTA_PROVEEDOR_TABBED = "4000_lista_proveedor";
    private String LISTA_TRASPASO_TABBED = "5000_lista_traspaso";
    private String LISTA_ALMACEN_TABBED = "6000_lista_almacen";
    private String LISTA_MERMA_TABBED = "6500_merma_excedente";
    private String LISTA_EGRESO_TABBED = "6700_lista_egreso";
    private String LISTA_COMPRA_TABBED = "7000_lista_compra";
    private String LISTA_COMPRA_CON_ENTREGA_TABBED = "7500_lista_compra_con_entrega";
    private String LISTA_ENTREGA_TABBED = "8000_lista_entrega";
    private String LISTA_VENTA_TABBED = "9000_lista_venta";
    private String LISTA_PROFORMA_TABBED = "9100_lista_proforma";
    private String REPORTE_VENTA_TABBED = "9200_reporte_venta";
    private String REPORTE_CLIENTE_TABBED = "20000_reporte_cliente";
    private String REPORTE_COMPRA_TABBED = "7500_reporte_compra";
    //private String REPORTE_CLIENTE_TABBED = "2000_reporte_cliente";
    private String REPORTE_PRODUCTO_TABBED = "3100_reporte_producto";
    private String REPORTE_ARQUEO_TABBED = "3500_reporte_arqueo";
    private String SALIR_SYSTEM = "0000_SALIR";

    public ToolbarMenuSistema(TabPanel tap_tabPanel) {
        this.tap_tabPanel = tap_tabPanel;
    }

    public void onModuleLoad() {
        initComponents();
    }

    private void initMenus() {

        //****************************
        // System
        //****************************
        Item ite_listaUsuario = new Item("Lista Usuarios");
        ite_listaUsuario.setIconCls("grid-icon");
        ite_listaUsuario.addListener(bit_listener);
        ite_listaUsuario.setId(LISTA_USUARIO_TABBED);

        Item ite_listaCliente = new Item("Lista Cliente");
        ite_listaCliente.setIconCls("grid-icon");
        ite_listaCliente.addListener(bit_listener);
        ite_listaCliente.setId(LISTA_CLIENTE_TABBED);


        Item ite_listaEmpleado = new Item("Lista Empleado");
        ite_listaEmpleado.setIconCls("grid-icon");
        ite_listaEmpleado.addListener(bit_listener);
        ite_listaEmpleado.setId(LISTA_EMPLEADO_TABBED);

        Item ite_listaCategoria = new Item("Lista Cagegorias");
        ite_listaCategoria.setIconCls("grid-icon");
        ite_listaCategoria.addListener(bit_listener);
        ite_listaCategoria.setId(LISTA_CATEGORIA_TABBED);

//        Item ite_listaMarca = new Item("Lista Marcas");
//        ite_listaMarca.setIconCls("grid-icon");
//        ite_listaMarca.addListener(bit_listener);
//        ite_listaMarca.setId(LISTA_MARCA_TABBED);

        Item ite_salir = new Item("Salir");
        ite_salir.setIconCls("folder-icon");
        ite_salir.addListener(bit_listener);
        ite_salir.setId(SALIR_SYSTEM);

        men_menuSistema.addItem(ite_listaUsuario);
        men_menuSistema.addItem(ite_listaCliente);
        men_menuSistema.addItem(ite_listaEmpleado);
        men_menuSistema.addItem(ite_listaCategoria);
//        men_menuSistema.addItem(ite_listaMarca);
        men_menuSistema.addItem(ite_salir);


        //****************************
        // Almacen
        //****************************
        Item ite_productos = new Item("Productos");
        ite_productos.setIconCls("word-icon");
        men_menuAlamcen.addItem(ite_productos);
        ite_productos.addListener(bit_listener);
        ite_productos.setId(LISTA_PRODUCTO_TABBED);

        Item ite_proveedor = new Item("Proveedores");
        ite_proveedor.setIconCls("excel-icon");
        men_menuAlamcen.addItem(ite_proveedor);
        ite_proveedor.addListener(bit_listener);
        ite_proveedor.setId(LISTA_PROVEEDOR_TABBED);

        Item ite_compras = new Item("Compras");
        ite_compras.setIconCls("powerpoint-icon");
        men_menuAlamcen.addItem(ite_compras);
        ite_compras.addListener(bit_listener);
        ite_compras.setId(LISTA_COMPRA_TABBED);

        Item ite_comprasentrega = new Item("Compras con entrega");
        ite_comprasentrega.setIconCls("powerpoint-icon");
        men_menuAlamcen.addItem(ite_comprasentrega);
        ite_comprasentrega.addListener(bit_listener);
        ite_comprasentrega.setId(LISTA_COMPRA_CON_ENTREGA_TABBED);

        Item ite_traspaso = new Item("Traspasos");
        ite_traspaso.setIconCls("powerpoint-icon");
        men_menuAlamcen.addItem(ite_traspaso);
        ite_traspaso.addListener(bit_listener);
        ite_traspaso.setId(LISTA_TRASPASO_TABBED);

        Item ite_almacen = new Item("Lista Almacen");
        ite_almacen.setIconCls("grid-icon");
        men_menuAlamcen.addItem(ite_almacen);
        ite_almacen.addListener(bit_listener);
        ite_almacen.setId(LISTA_ALMACEN_TABBED);

        Item ite_merma = new Item("Mermas y Excedentes");
        ite_merma.setIconCls("grid-icon");
        men_menuAlamcen.addItem(ite_merma);
        ite_merma.addListener(bit_listener);
        ite_merma.setId(LISTA_MERMA_TABBED);

        Item ite_egreso = new Item("Egresos");
        ite_egreso.setIconCls("grid-icon");
        men_menuAlamcen.addItem(ite_egreso);
        ite_egreso.addListener(bit_listener);
        ite_egreso.setId(LISTA_EGRESO_TABBED);

        //****************************
        // Venta
        //****************************
        Item ite_venta = new Item("Lista Ventas");
        ite_venta.setIconCls("word-icon");
        men_menuVenta.addItem(ite_venta);
        ite_venta.addListener(bit_listener);
        ite_venta.setId(LISTA_VENTA_TABBED);

//        Item ite_proforma = new Item("Lista Venta");
//        ite_proforma.setIconCls("word-icon");
//        men_menuVenta.addItem(ite_proforma);
//        ite_proforma.addListener(bit_listener);
//        ite_proforma.setId(LISTA_PROFORMA_TABBED);
//

        Item ite_proforma = new Item("Lista Proforma");
        ite_proforma.setIconCls("word-icon");
        men_menuVenta.addItem(ite_proforma);
        ite_proforma.addListener(bit_listener);
        ite_proforma.setId(LISTA_PROFORMA_TABBED);


        //****************************
        // Reportes
        //****************************
        Item ite_rep_venta = new Item("Ventas");
        ite_rep_venta.setIconCls("word-icon");
        men_menuReporte.addItem(ite_rep_venta);
        ite_rep_venta.addListener(bit_listener);
        ite_rep_venta.setId(REPORTE_VENTA_TABBED);

        Item ite_rep_compra = new Item("Compras");
        ite_rep_compra.setIconCls("word-icon");
        men_menuReporte.addItem(ite_rep_compra);
        ite_rep_compra.addListener(bit_listener);
        ite_rep_compra.setId(REPORTE_COMPRA_TABBED);

        Item ite_rep_cliente = new Item("Estado de Resultados");
        ite_rep_cliente.setIconCls("word-icon");
        men_menuReporte.addItem(ite_rep_cliente);
        ite_rep_cliente.addListener(bit_listener);
        ite_rep_cliente.setId(REPORTE_CLIENTE_TABBED);
//
        Item ite_rep_arqueo = new Item("Arqueo");
        ite_rep_arqueo.setIconCls("word-icon");
        men_menuReporte.addItem(ite_rep_arqueo);
        ite_rep_arqueo.addListener(bit_listener);
        ite_rep_arqueo.setId(REPORTE_ARQUEO_TABBED);

        Item ite_rep_producto = new Item("Productos");
        ite_rep_producto.setIconCls("word-icon");
        men_menuReporte.addItem(ite_rep_producto);
        ite_rep_producto.addListener(bit_listener);
        ite_rep_producto.setId(REPORTE_PRODUCTO_TABBED);


    }

    private void initComponents() {
        men_menuSistema = new Menu();
        men_menuAlamcen = new Menu();
        men_menuVenta = new Menu();
        men_menuReporte = new Menu();

        too_toolbar = new Toolbar();
        bit_listener = new BaseItemListenerAdapter() {

            @Override
            public void onClick(BaseItem item, EventObject e) {
                Panel tab = addTab(item);
                if (tab != null) {
                    tap_tabPanel.activate(tab.getId());
                    tap_tabPanel.scrollToTab(tab, true);
                }
            }
        };

        initMenus();

        tob_sistema = new ToolbarButton("Sistema");
        tob_sistema.setMenu(men_menuSistema);
        tob_sistema.setIconCls("bmenu");

        tob_almacen = new ToolbarButton("Almacén");
        tob_almacen.setMenu(men_menuAlamcen);
        tob_almacen.setIconCls("bmenu");

        tob_venta = new ToolbarButton("Venta");
        tob_venta.setMenu(men_menuVenta);
        tob_venta.setIconCls("bmenu");

        tob_reporte = new ToolbarButton("Reportes");
        tob_reporte.setMenu(men_menuReporte);
        tob_reporte.setIconCls("bmenu");


        too_toolbar.addButton(tob_sistema);
        too_toolbar.addSeparator();
        too_toolbar.addButton(tob_almacen);
        too_toolbar.addSeparator();
        too_toolbar.addButton(tob_venta);
        too_toolbar.addSeparator();
        too_toolbar.addButton(tob_reporte);
        too_toolbar.addSeparator();
    }

    private Panel addTab(BaseItem item) {
        Panel tab = null;
        String id_item = item.getId();
        //MessageBox.alert(id_item);
//        if (id_item.equalsIgnoreCase(REPORTE_PRODUCTO_TABBED)) {
////            ListaTraspaso lista = new ListaTraspaso();
////            lista.onModuleLoad();
////            tab = lista.getPanel();
////            tab.setTitle("Lista de traspaso");
////            String idTabbed = "tab-" + REPORTE_PRODUCTO_TABBED;
////            tab.setId(idTabbed);
////            setPropiedades(idTabbed, tab);
//            MessageBox.alert("mostar reporte de producto");
//        }

        return tab;
    }

    public Toolbar getToo_toolbar() {
        return too_toolbar;
    }

    public void setPropiedades(String idTabbed, Panel tab, TabPanel tap_aux) {
        tap_aux.setActiveTab(idTabbed);
        tab.setAutoScroll(true);
        tab.setIconCls("tab-icon");
        tab.setClosable(true);
        tap_aux.add(tab);
    }


}
