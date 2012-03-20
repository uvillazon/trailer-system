package org.trailer.client.util;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.menu.BaseItem;
import com.gwtext.client.widgets.menu.Item;
import com.gwtext.client.widgets.menu.Menu;
import com.gwtext.client.widgets.menu.MenuItem;
import com.gwtext.client.widgets.menu.event.MenuListener;
import org.trailer.client.Entregas.EntregaNotaRemision;
import org.trailer.client.MainEntryPoint;
import org.trailer.client.cortes.CrearCorte;
import org.trailer.client.distribuciones.ProcesoDistribucion;
import org.trailer.client.distribuciones.ProcesoRecepcion;
import org.trailer.client.egresos.Cuenta;
import org.trailer.client.egresos.Egreso;
import org.trailer.client.egresos.RealizarEgreso;
import org.trailer.client.parametros.Componentes;
import org.trailer.client.parametros.Compras;
import org.trailer.client.parametros.Grupos;
import org.trailer.client.parametros.InventarioMateriaPrima;
import org.trailer.client.parametros.InventarioProducto;
import org.trailer.client.parametros.Logos;
import org.trailer.client.parametros.MateriaPrima;
import org.trailer.client.parametros.Molde;
import org.trailer.client.parametros.Producto;
import org.trailer.client.parametros.RealizarCompra;
import org.trailer.client.produccion.ListarOrdenes;
import org.trailer.client.produccion.OrdenProduccion;
import org.trailer.client.produccionproceso.EntregaProceso;
import org.trailer.client.produccionproceso.ListaProduccionProceso;
import org.trailer.client.reportes.ConsultaMateriaPrima;
import org.trailer.client.reportes.ConsultaProducto;
import org.trailer.client.reportes.ReporteOrdenes;
import org.trailer.client.reportes.StockMateriaPrima;
import org.trailer.client.sistema.Cargo;
import org.trailer.client.sistema.Categoria;
import org.trailer.client.sistema.CategoriaProductos;
import org.trailer.client.sistema.Ciudad;
import org.trailer.client.sistema.Cliente;
import org.trailer.client.sistema.Color;
import org.trailer.client.sistema.CuentaPrincipal;
import org.trailer.client.sistema.Empleado;
import org.trailer.client.sistema.Empresa;
import org.trailer.client.sistema.Proceso;
import org.trailer.client.sistema.Proveedor;
import org.trailer.client.sistema.Responsable;
import org.trailer.client.sistema.Unidad;
import org.trailer.client.system.Rol;
import org.trailer.client.system.Usuario;

//
/**
 *
 * @author FOREGROUND
 */
public class KMenu implements MenuListener {

    Button button = null;
    MainEntryPoint panel;

    public KMenu(MainEntryPoint p) {
        this.panel = p;
    }

    public void onModuleLoad(JSONObject conec) {

        button = new Button();
        button.setText("Sistema trailer:::...");
//        button.setWidth("150px");

        button.setIconCls("user-icon");
        Menu menu = new Menu();
        Object[][] categorias = Utils.getArrayOfJSONObject(conec, "resultado", new String[]{"idcategoriafuncion", "nombre"});
        for (int i = 0; i < categorias.length; i++) {
            String cadNom = categorias[i][1].toString();
            String cadID = categorias[i][0].toString();
            cadID = "CatFunc" + cadID;
            cadNom = cadNom;
            Menu subMenu = new Menu();
            subMenu.setId(cadID);
            subMenu.addListener(this);
            Object[][] func = Utils.getArrayOfJSONObject(conec, cadNom, new String[]{"idfuncion", "descripcion"});
//            com.google.gwt.user.client.Window.alert("nombre: "+cadNom+"id: "+cadID);
            for (int ii = 0; ii < func.length; ii++) {
                Item csharpItem = new Item(func[ii][1].toString());
                csharpItem.setId(func[ii][0].toString());
                csharpItem.setIconCls("settings-icon");
                subMenu.addItem(csharpItem);
            }
            MenuItem vsItem = new MenuItem(cadNom, subMenu);
            vsItem.setIconCls("plugins-nav-icon");
            menu.addItem(vsItem);

        }
        button.setMenu(menu);
        button.setMenuAlign("tl-bl?");

    }

    public KMenu getKmenu() {
        return this;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public void doBeforeHide(Menu menu) {
        //Window.alert("csharpItem8");
    }

    public void doBeforeShow(Menu menu) {
        //Window.alert("csharpItem7");
    }

    public void onClick(Menu menu, String menuItemId, EventObject e) {
        //Window.alert(menuItemId);
        try {
            //Widget temp = panel.getCuerpoP().getWidget(0);
            Panel temp = panel.getTabPanel().getItem("TP" + menuItemId);
            //Window.alert("Title::::: "+temp.getTitle());
            if (temp != null) {
                //Window.alert(temp.toString());
                panel.getTabPanel().activate(temp.getId());
                panel.getTabPanel().scrollToTab(temp, true);
            //panel.getCuerpoP().remove(temp);
            } else {
//                Window.alert("asdf");
                seleccionarOpcion(menu, menuItemId, e, null);
            }
        } catch (Exception ee) {
//            Window.alert("asdfasdfasdfasdfasdfasdf");
            seleccionarOpcion(menu, menuItemId, e, null);
        }


    }

    public void onHide(Menu menu) {
        //Window.alert("csharpItem1");
    }

    public void onItemClick(BaseItem item, EventObject e) {
        //Window.alert("csharpItem2");
    }

    public void onMouseOut(Menu menu, BaseItem menuItem, EventObject e) {
        //Window.alert("csharpItem3");
    }

    public void onMouseOver(Menu menu, BaseItem menuItem, EventObject e) {
        //Window.alert("csharpItem4");
    }

    public void onShow(Menu menu) {
        //Window.alert("csharpItem5");
    }

    public void seleccionarOpcion(Menu menu, String menuItemId, EventObject e, Object object) {
        if (object == null) {






            if (menuItemId.equalsIgnoreCase("fun1000")) {
                Usuario usuarios = new Usuario();
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(usuarios);
                    panel.getTabPanel().activate(usuarios.getId());
                    panel.getTabPanel().scrollToTab(usuarios, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun1001")) {
                Rol roles = new Rol();
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(roles);
                    panel.getTabPanel().activate(roles.getId());
                    panel.getTabPanel().scrollToTab(roles, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun1002")) {
                Proveedor proveedores = new Proveedor();
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(proveedores);
                    panel.getTabPanel().activate(proveedores.getId());
                    panel.getTabPanel().scrollToTab(proveedores, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun1003")) {
                Ciudad roles = new Ciudad();
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(roles);
                    panel.getTabPanel().activate(roles.getId());
                    panel.getTabPanel().scrollToTab(roles, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun1004")) {
                Empleado empleado = new Empleado();
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(empleado);
                    panel.getTabPanel().activate(empleado.getId());
                    panel.getTabPanel().scrollToTab(empleado, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun1005")) {
                Cargo roles = new Cargo();
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(roles);
                    panel.getTabPanel().activate(roles.getId());
                    panel.getTabPanel().scrollToTab(roles, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun1006")) {
                Cliente cliente = new Cliente();
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(cliente);
                    panel.getTabPanel().activate(cliente.getId());
                    panel.getTabPanel().scrollToTab(cliente, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun1007")) {
                Empresa empresa = new Empresa();
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(empresa);
                    panel.getTabPanel().activate(empresa.getId());
                    panel.getTabPanel().scrollToTab(empresa, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun1008")) {
                Responsable responsable = new Responsable();
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(responsable);
                    panel.getTabPanel().activate(responsable.getId());
                    panel.getTabPanel().scrollToTab(responsable, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun1009")) {
                Categoria categorias = new Categoria();
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(categorias);
                    panel.getTabPanel().activate(categorias.getId());
                    panel.getTabPanel().scrollToTab(categorias, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun1010")) {
                Unidad unidades = new Unidad();
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(unidades);
                    panel.getTabPanel().activate(unidades.getId());
                    panel.getTabPanel().scrollToTab(unidades, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun1011")) {
                Color colores = new Color();
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(colores);
                    panel.getTabPanel().activate(colores.getId());
                    panel.getTabPanel().scrollToTab(colores, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun1012")) {
                Proceso procesos = new Proceso();
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(procesos);
                    panel.getTabPanel().activate(procesos.getId());
                    panel.getTabPanel().scrollToTab(procesos, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun1013")) {
                CategoriaProductos categoriaproducto = new CategoriaProductos();
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(categoriaproducto);
                    panel.getTabPanel().activate(categoriaproducto.getId());
                    panel.getTabPanel().scrollToTab(categoriaproducto, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun1014")) {
                CuentaPrincipal cuentaprincipal = new CuentaPrincipal();
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(cuentaprincipal);
                    panel.getTabPanel().activate(cuentaprincipal.getId());
                    panel.getTabPanel().scrollToTab(cuentaprincipal, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun2000")) {
                MateriaPrima materiaprimas = new MateriaPrima();
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(materiaprimas);
                    panel.getTabPanel().activate(materiaprimas.getId());
                    panel.getTabPanel().scrollToTab(materiaprimas, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun2001")) {
                InventarioMateriaPrima inventariomateriaprimas = new InventarioMateriaPrima();
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(inventariomateriaprimas);
                    panel.getTabPanel().activate(inventariomateriaprimas.getId());
                    panel.getTabPanel().scrollToTab(inventariomateriaprimas, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun2002")) {
                String enlace = "/php/Compras.php?funcion=CargarNuevaCompra";
                Utils.setErrorPrincipal("Cargando productos", "cargar");
                final Conector conecaPB = new Conector(enlace, false);
                try {
                    conecaPB.getRequestBuilder().sendRequest(null, new RequestCallback() {

                        public void onResponseReceived(Request request, Response response) {
                            String data = response.getText();
                            JSONValue jsonValue = JSONParser.parse(data);
                            JSONObject jsonObject;
                            if ((jsonObject = jsonValue.isObject()) != null) {

                                String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                                String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");

                                if (errorR.equalsIgnoreCase("true")) {
                                    Object[][] ProveedorM = Utils.getArrayOfJSONObject(jsonObject, "proveedorM", new String[]{"idproveedor", "nombre"});
                                    Object[][] ProductoM = Utils.getArrayOfJSONObject(jsonObject, "productoM", new String[]{"id", "nombre", "unidad"});
                                    Object[][] encargadoM = Utils.getArrayOfJSONObject(jsonObject, "encargadoM", new String[]{"idempleado", "nombre"});

                                    RealizarCompra Compras = new RealizarCompra(ProveedorM, ProductoM, encargadoM);
//                                    Producto pro = new Producto(ciudadM1016, sucursalM1016, almacenM1016, categoriaM1016, subcategoriaM1016, caracM1016);
                                    if (panel.getTabPanel() == null) {
                                        Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                                    } else {

                                        Utils.setErrorPrincipal("Se cargo el manejador de productos", "mensaje");
                                        panel.getTabPanel().add(Compras);
                                        panel.getTabPanel().activate(Compras.getId());
                                        panel.getTabPanel().scrollToTab(Compras, true);
                                    }
                                //
                                } else {
                                    Utils.setErrorPrincipal(mensajeR, "error");
                                }
                            } else {
                                Utils.setErrorPrincipal("Error en los datos", "error");
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
            //                InventarioMateriaPrima inventariomateriaprimas = new InventarioMateriaPrima();
            //                if (panel.getTabPanel() == null) {
            //                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
            //                } else {
            //                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
            //                    panel.getTabPanel().add(inventariomateriaprimas);
            //                    panel.getTabPanel().activate(inventariomateriaprimas.getId());
            //                    panel.getTabPanel().scrollToTab(inventariomateriaprimas, true);
            //                }


            }
            if (menuItemId.equalsIgnoreCase("fun2003")) {
                Compras compras = new Compras(this, panel);
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(compras);
                    panel.getTabPanel().activate(compras.getId());
                    panel.getTabPanel().scrollToTab(compras, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun2004")) {
                Grupos grupos = new Grupos(this, panel);
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(grupos);
                    panel.getTabPanel().activate(grupos.getId());
                    panel.getTabPanel().scrollToTab(grupos, true);
                }
            }

            if (menuItemId.equalsIgnoreCase("fun2006")) {
                Producto producto = new Producto(this, panel);
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(producto);
                    panel.getTabPanel().activate(producto.getId());
                    panel.getTabPanel().scrollToTab(producto, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun2007")) {
                Logos logos = new Logos(this, panel);
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(logos);
                    panel.getTabPanel().activate(logos.getId());
                    panel.getTabPanel().scrollToTab(logos, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun2010")) {
                InventarioProducto inventarioproducto = new InventarioProducto();
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(inventarioproducto);
                    panel.getTabPanel().activate(inventarioproducto.getId());
                    panel.getTabPanel().scrollToTab(inventarioproducto, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun2011")) {
                Molde moldes = new Molde(this, panel);
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(moldes);
                    panel.getTabPanel().activate(moldes.getId());
                    panel.getTabPanel().scrollToTab(moldes, true);
                }
            }

            if (menuItemId.equalsIgnoreCase("fun3000")) {
                Cuenta cuenta = new Cuenta();
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(cuenta);
                    panel.getTabPanel().activate(cuenta.getId());
                    panel.getTabPanel().scrollToTab(cuenta, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun3001")) {
                Egreso egreso = new Egreso(this, panel);
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(egreso);
                    panel.getTabPanel().activate(egreso.getId());
                    panel.getTabPanel().scrollToTab(egreso, true);
                }
            }

            if (menuItemId.equalsIgnoreCase("fun3002")) {
                String enlace = "/php/Egreso.php?funcion=CargarPanelEgreso";
                Utils.setErrorPrincipal("Cargando Panel Consultas", "cargar");
                final Conector conecaPB = new Conector(enlace, false);
                try {
                    conecaPB.getRequestBuilder().sendRequest(null, new RequestCallback() {

                        public void onResponseReceived(Request request, Response response) {
                            String data = response.getText();
                            JSONValue jsonValue = JSONParser.parse(data);
                            JSONObject jsonObject;
                            if ((jsonObject = jsonValue.isObject()) != null) {

                                String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                                String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");

                                if (errorR.equalsIgnoreCase("true")) {
                                    Object[][] cuentaM = Utils.getArrayOfJSONObject(jsonObject, "cuentaM", new String[]{"idcuenta", "nombre"});
                                    Object[][] encargadoM = Utils.getArrayOfJSONObject(jsonObject, "encargadoM", new String[]{"idempleado", "nombre"});

                                    RealizarEgreso Compras = new RealizarEgreso(cuentaM, encargadoM);
//                                    Producto pro = new Producto(ciudadM1016, sucursalM1016, almacenM1016, categoriaM1016, subcategoriaM1016, caracM1016);
                                    if (panel.getTabPanel() == null) {
                                        Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                                    } else {

                                        Utils.setErrorPrincipal("Se cargo el manejador de productos", "mensaje");
                                        panel.getTabPanel().add(Compras);
                                        panel.getTabPanel().activate(Compras.getId());
                                        panel.getTabPanel().scrollToTab(Compras, true);
                                    }
                                //
                                } else {
                                    Utils.setErrorPrincipal(mensajeR, "error");
                                }
                            } else {
                                Utils.setErrorPrincipal("Error en los datos", "error");
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
            //                InventarioMateriaPrima inventariomateriaprimas = new InventarioMateriaPrima();
            //                if (panel.getTabPanel() == null) {
            //                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
            //                } else {
            //                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
            //                    panel.getTabPanel().add(inventariomateriaprimas);
            //                    panel.getTabPanel().activate(inventariomateriaprimas.getId());
            //                    panel.getTabPanel().scrollToTab(inventariomateriaprimas, true);
            //                }


            }


            if (menuItemId.equalsIgnoreCase("fun4000")) {

                String enlace = "/php/OrdenProduccion.php?funcion=CargarPanelOrdenProduccion";
                Utils.setErrorPrincipal("Cargando Panel Consultas", "cargar");
                final Conector conecaPB = new Conector(enlace, false);
                try {
                    conecaPB.getRequestBuilder().sendRequest(null, new RequestCallback() {

                        public void onResponseReceived(Request request, Response response) {
                            String data = response.getText();
                            JSONValue jsonValue = JSONParser.parse(data);
                            JSONObject jsonObject;
                            if ((jsonObject = jsonValue.isObject()) != null) {

                                String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                                String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");

                                if (errorR.equalsIgnoreCase("true")) {
                                    Object[][] productos = Utils.getArrayOfJSONObject(jsonObject, "productoM", new String[]{"id", "detalle", "unidad", "preciounitario"});
                                    Object[][] clientes = Utils.getArrayOfJSONObject(jsonObject, "clienteM", new String[]{"idcliente", "nombre"});
                                    Object[][] responsables = Utils.getArrayOfJSONObject(jsonObject, "responsableM", new String[]{"idresponsable", "nombre", "idempresa"});
                                    Object[][] empresas = Utils.getArrayOfJSONObject(jsonObject, "empresaM", new String[]{"idempresa", "nombre"});
                                    Object[][] colores = Utils.getArrayOfJSONObject(jsonObject, "colorM", new String[]{"id", "nombre"});
                                    Object[][] telas = Utils.getArrayOfJSONObject(jsonObject, "telaM", new String[]{"id", "detalle"});
                                    String fecha = Utils.getStringOfJSONObject(jsonObject, "fecha");
                                    String numeroproduccion = Utils.getStringOfJSONObject(jsonObject, "numeroproduccion");

                                    OrdenProduccion ordenproduccion = new OrdenProduccion(clientes, productos, responsables, empresas, telas, colores, numeroproduccion,fecha);
//                                    Producto pro = new Producto(ciudadM1016, sucursalM1016, almacenM1016, categoriaM1016, subcategoriaM1016, caracM1016);
                                    if (panel.getTabPanel() == null) {
                                        Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                                    } else {

                                        Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                                        panel.getTabPanel().add(ordenproduccion);
                                        panel.getTabPanel().activate(ordenproduccion.getId());
                                        panel.getTabPanel().scrollToTab(ordenproduccion, true);
                                    }
                                //
                                } else {
                                    Utils.setErrorPrincipal(mensajeR, "error");
                                }
                            } else {
                                Utils.setErrorPrincipal("Error en los datos", "error");
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

            if (menuItemId.equalsIgnoreCase("fun4001")) {
                ListarOrdenes ordenes = new ListarOrdenes(this, panel);
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(ordenes);
                    panel.getTabPanel().activate(ordenes.getId());
                    panel.getTabPanel().scrollToTab(ordenes, true);
                }
            }

            if (menuItemId.equalsIgnoreCase("fun5000")) {

                String enlace = "/php/Produccion.php?funcion=CargarPanelEntregaProceso";
                Utils.setErrorPrincipal("Cargando Panel Consultas", "cargar");
                final Conector conecaPB = new Conector(enlace, false);
                try {
                    conecaPB.getRequestBuilder().sendRequest(null, new RequestCallback() {

                        public void onResponseReceived(Request request, Response response) {
                            String data = response.getText();
                            JSONValue jsonValue = JSONParser.parse(data);
                            JSONObject jsonObject;
                            if ((jsonObject = jsonValue.isObject()) != null) {

                                String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                                String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");

                                if (errorR.equalsIgnoreCase("true")) {
                                    Object[][] ProductoM = Utils.getArrayOfJSONObject(jsonObject, "productoM", new String[]{"id", "detalle", "unidad"});
                                    Object[][] encargadoM = Utils.getArrayOfJSONObject(jsonObject, "encargadoM", new String[]{"id", "nombre"});
                                    Object[][] procesoM = Utils.getArrayOfJSONObject(jsonObject, "procesoM", new String[]{"id", "nombre"});
                                    Object[][] materiaM = Utils.getArrayOfJSONObject(jsonObject, "materiaM", new String[]{"id", "detalle", "unidad"});
                                    Object[][] productoIntermedio = Utils.getArrayOfJSONObject(jsonObject, "productosIntermedio", new String[]{"id", "detalle"});
                                    EntregaProceso entregaproceso = new EntregaProceso(procesoM, ProductoM, encargadoM, materiaM, productoIntermedio);
//                                    Producto pro = new Producto(ciudadM1016, sucursalM1016, almacenM1016, categoriaM1016, subcategoriaM1016, caracM1016);
                                    if (panel.getTabPanel() == null) {
                                        Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                                    } else {

                                        Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                                        panel.getTabPanel().add(entregaproceso);
                                        panel.getTabPanel().activate(entregaproceso.getId());
                                        panel.getTabPanel().scrollToTab(entregaproceso, true);
                                    }
                                //
                                } else {
                                    Utils.setErrorPrincipal(mensajeR, "error");
                                }
                            } else {
                                Utils.setErrorPrincipal("Error en los datos", "error");
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

            if (menuItemId.equalsIgnoreCase("fun5001")) {
                ListaProduccionProceso produccion = new ListaProduccionProceso();
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(produccion);
                    panel.getTabPanel().activate(produccion.getId());
                    panel.getTabPanel().scrollToTab(produccion, true);
                }
            }



            if (menuItemId.equalsIgnoreCase("fun6000")) {
                String enlace = "/php/MateriaPrima.php?funcion=CargarPanelConsultaMateriaPrima";
                Utils.setErrorPrincipal("Cargando Panel Consultas", "cargar");
                final Conector conecaPB = new Conector(enlace, false);
                try {
                    conecaPB.getRequestBuilder().sendRequest(null, new RequestCallback() {

                        public void onResponseReceived(Request request, Response response) {
                            String data = response.getText();
                            JSONValue jsonValue = JSONParser.parse(data);
                            JSONObject jsonObject;
                            if ((jsonObject = jsonValue.isObject()) != null) {

                                String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                                String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");

                                if (errorR.equalsIgnoreCase("true")) {
                                    Object[][] ProductoM = Utils.getArrayOfJSONObject(jsonObject, "productoM", new String[]{"id", "nombre"});
                                    ConsultaMateriaPrima Compras = new ConsultaMateriaPrima(ProductoM);
//                                    Producto pro = new Producto(ciudadM1016, sucursalM1016, almacenM1016, categoriaM1016, subcategoriaM1016, caracM1016);
                                    if (panel.getTabPanel() == null) {
                                        Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                                    } else {

                                        Utils.setErrorPrincipal("Se cargo el manejador de productos", "mensaje");
                                        panel.getTabPanel().add(Compras);
                                        panel.getTabPanel().activate(Compras.getId());
                                        panel.getTabPanel().scrollToTab(Compras, true);
                                    }
                                //
                                } else {
                                    Utils.setErrorPrincipal(mensajeR, "error");
                                }
                            } else {
                                Utils.setErrorPrincipal("Error en los datos", "error");
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
            //                InventarioMateriaPrima inventariomateriaprimas = new InventarioMateriaPrima();
            //                if (panel.getTabPanel() == null) {
            //                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
            //                } else {
            //                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
            //                    panel.getTabPanel().add(inventariomateriaprimas);
            //                    panel.getTabPanel().activate(inventariomateriaprimas.getId());
            //                    panel.getTabPanel().scrollToTab(inventariomateriaprimas, true);
            //                }


            }
            if (menuItemId.equalsIgnoreCase("fun6001")) {
                StockMateriaPrima stock = new StockMateriaPrima();
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(stock);
                    panel.getTabPanel().activate(stock.getId());
                    panel.getTabPanel().scrollToTab(stock, true);
                }
            }

            if (menuItemId.equalsIgnoreCase("fun6002")) {
                String enlace = "/php/ConsultaProducto.php?funcion=CargarPanelConsultaProducto";
                Utils.setErrorPrincipal("Cargando Panel Consultas", "cargar");
                final Conector conecaPB = new Conector(enlace, false);
                try {
                    conecaPB.getRequestBuilder().sendRequest(null, new RequestCallback() {

                        public void onResponseReceived(Request request, Response response) {
                            String data = response.getText();
                            JSONValue jsonValue = JSONParser.parse(data);
                            JSONObject jsonObject;
                            if ((jsonObject = jsonValue.isObject()) != null) {

                                String errorR = Utils.getStringOfJSONObject(jsonObject, "error");
                                String mensajeR = Utils.getStringOfJSONObject(jsonObject, "mensaje");

                                if (errorR.equalsIgnoreCase("true")) {
                                    Object[][] ProductoM = Utils.getArrayOfJSONObject(jsonObject, "productoM", new String[]{"id", "detalle"});
                                    ConsultaProducto Compras = new ConsultaProducto(ProductoM);
//                                    Producto pro = new Producto(ciudadM1016, sucursalM1016, almacenM1016, categoriaM1016, subcategoriaM1016, caracM1016);
                                    if (panel.getTabPanel() == null) {
                                        Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                                    } else {

                                        Utils.setErrorPrincipal("Se cargo el manejador de productos", "mensaje");
                                        panel.getTabPanel().add(Compras);
                                        panel.getTabPanel().activate(Compras.getId());
                                        panel.getTabPanel().scrollToTab(Compras, true);
                                    }
                                //
                                } else {
                                    Utils.setErrorPrincipal(mensajeR, "error");
                                }
                            } else {
                                Utils.setErrorPrincipal("Error en los datos", "error");
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
            //                InventarioMateriaPrima inventariomateriaprimas = new InventarioMateriaPrima();
            //                if (panel.getTabPanel() == null) {
            //                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
            //                } else {
            //                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
            //                    panel.getTabPanel().add(inventariomateriaprimas);
            //                    panel.getTabPanel().activate(inventariomateriaprimas.getId());
            //                    panel.getTabPanel().scrollToTab(inventariomateriaprimas, true);
            //                }


            }
             if (menuItemId.equalsIgnoreCase("fun6003")) {
                ReporteOrdenes stock = new ReporteOrdenes(this, panel);
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(stock);
                    panel.getTabPanel().activate(stock.getId());
                    panel.getTabPanel().scrollToTab(stock, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun7000")) {
                CrearCorte stock = new CrearCorte(this, panel);
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(stock);
                    panel.getTabPanel().activate(stock.getId());
                    panel.getTabPanel().scrollToTab(stock, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun8000")) {
                ProcesoDistribucion stock = new ProcesoDistribucion(this, panel);
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(stock);
                    panel.getTabPanel().activate(stock.getId());
                    panel.getTabPanel().scrollToTab(stock, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun8001")) {
                ProcesoRecepcion stock = new ProcesoRecepcion(this, panel);
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(stock);
                    panel.getTabPanel().activate(stock.getId());
                    panel.getTabPanel().scrollToTab(stock, true);
                }
            }
            if (menuItemId.equalsIgnoreCase("fun9000")) {
                EntregaNotaRemision stock = new EntregaNotaRemision(this, panel);
                if (panel.getTabPanel() == null) {
                    Utils.setErrorPrincipal("No existe el manejador de pestanas", "error");
                } else {
                    Utils.setErrorPrincipal("Se cargo el manejador", "mensaje");
                    panel.getTabPanel().add(stock);
                    panel.getTabPanel().activate(stock.getId());
                    panel.getTabPanel().scrollToTab(stock, true);
                }
            }


//            sistema flores jaldin menu compras



        } //            fin sistema flores jaldin
        //tienda///////////////
        else {


            if (menuItemId.equalsIgnoreCase("fun2002")) {
                RealizarCompra panelcompra = (RealizarCompra) object;
                panel.getTabPanel().add(panelcompra);
                panel.getTabPanel().activate(panelcompra.getId());
                panel.getTabPanel().scrollToTab(panelcompra, true);


            }

            if (menuItemId.equalsIgnoreCase("fun2005")) {
                Componentes panelComponentes = (Componentes) object;
                panel.getTabPanel().add(panelComponentes);
                panel.getTabPanel().activate(panelComponentes.getId());
                panel.getTabPanel().scrollToTab(panelComponentes, true);


            }

            if (menuItemId.equalsIgnoreCase("fun3002")) {
                RealizarEgreso panelEgreso = (RealizarEgreso) object;
                panel.getTabPanel().add(panelEgreso);
                panel.getTabPanel().activate(panelEgreso.getId());
                panel.getTabPanel().scrollToTab(panelEgreso, true);


            }
            if (menuItemId.equalsIgnoreCase("fun4000")) {
                OrdenProduccion ordenproduccion = (OrdenProduccion) object;
                panel.getTabPanel().add(ordenproduccion);
                panel.getTabPanel().activate(ordenproduccion.getId());
                panel.getTabPanel().scrollToTab(ordenproduccion, true);


            }

//            if (menuItemId.equalsIgnoreCase("fun5002")) {
//                PanelPedido p = (PanelPedido) object;
//                panel.getTabPanel().add(p);
//                panel.getTabPanel().activate(p.getId());
//                panel.getTabPanel().scrollToTab(p, true);
//
//
//            }
        }
    }

    public void seleccionarOpcionRemove(Menu menu, String menuItemId, EventObject e, Object object) {
        if (object == null) {
        } else {
            if (menuItemId.equalsIgnoreCase("fun7000")) {
                CrearCorte ordenproduccion = (CrearCorte) object;
                panel.getTabPanel().remove(ordenproduccion);



            }

        }

    }
}
