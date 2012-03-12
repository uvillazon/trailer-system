/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.reportes;

import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.SimpleStore;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.AnchorLayoutData;
import com.gwtext.client.widgets.layout.FormLayout;
import com.gwtext.client.widgets.layout.ColumnLayout;
import com.gwtext.client.widgets.layout.ColumnLayoutData;
import com.gwtext.client.widgets.layout.HorizontalLayout;

import java.util.Date;
import org.trailer.client.util.ReporteMediaCartaChorroWindow;
import org.trailer.client.util.Utils;
/**
 *
 * @author Uvillazon
 */
public class ConsultaProducto extends FormPanel {
   private ComboBox com_producto;
//    private ComboBox com_tipoventa1109;
//    private ComboBox com_marca1109;
//    private ComboBox com_categoria1109;
//    private ComboBox com_proveedor1109;
    private Object[][] producto;
   
    private SimpleStore almacenStore1109;
    private DateField dat_fechaini;
    private DateField dat_fechafin;
    private SimpleStore tipoVentaStore1109;
    private final AnchorLayoutData ANCHO_LAYOUT_DATA = new AnchorLayoutData("100%");
    private final int ANCHO = 1100;
    private Button imprimirEstado;
    private Button imprimirMovimiento;
    private ReporteMediaCartaChorroWindow print;
    // private Button resultados1109;
//    private consultas padre;

    public ConsultaProducto(Object[][] almacenMM1109) {
       // this.padre = padres;

        this.producto = almacenMM1109;
       
        setPaddings(100, 5, 5, 5);
//        setWidth(ANCHO);
//        setLabelWidth(100);
       setClosable(true);
       setId("TPfun6002");
       setIconCls("tab-icon");
        setAutoScroll(false);
        setTitle("Cunsulta Producto");

        initCombos();
        addListeners();
        Panel topPanel = new Panel();
        topPanel.setLayout(new ColumnLayout());
        topPanel.setBaseCls("x-plain");

        Panel downPanel = new Panel();
        downPanel.setLayout(new ColumnLayout());
        downPanel.setBaseCls("x-plain");

        Panel columnOnePanel = new Panel();
        columnOnePanel.setLayout(new FormLayout());
        columnOnePanel.setBaseCls("x-plain");
        //columnOnePanel.add(com_tipoTransaccion1109, ANCHO_LAYOUT_DATA);
        columnOnePanel.add(com_producto, ANCHO_LAYOUT_DATA);
      
        columnOnePanel.add(dat_fechaini, ANCHO_LAYOUT_DATA);
        columnOnePanel.add(dat_fechafin, ANCHO_LAYOUT_DATA);
        Panel columnTresPanel = new Panel();
        columnTresPanel.setLayout(new FormLayout());
        columnTresPanel.setBaseCls("x-plain");
        Panel columnseisPanel = new Panel();
        columnseisPanel.setLayout(new HorizontalLayout(2));
        columnseisPanel.setBaseCls("x-plain");
        Panel columnsietePanel = new Panel();
        columnsietePanel.setLayout(new HorizontalLayout(2));
        columnsietePanel.setBaseCls("x-plain");
        columnsietePanel.addButton(imprimirEstado);
        columnsietePanel.addButton(imprimirMovimiento);
        Panel columnCuatroPanel = new Panel();
        columnCuatroPanel.setLayout(new FormLayout());
        columnCuatroPanel.setBaseCls("x-plain");
        columnCuatroPanel.add(columnseisPanel, ANCHO_LAYOUT_DATA);
        columnCuatroPanel.add(columnsietePanel, ANCHO_LAYOUT_DATA);
        topPanel.add(columnOnePanel, new ColumnLayoutData(0.24));
//        topPanel.add(columnTresPanel, new ColumnLayoutData(0.24));
        downPanel.add(columnCuatroPanel, new ColumnLayoutData(0.24));

//        if (almacenM1109 != null) {
//            almacenStore1109 = new SimpleStore(new String[]{"id", "nombre"}, almacenM1109);
//            almacenStore1109.load();
//            com_almacen1109.setStore(almacenStore1109);
//        }



        initCombosValues();
        add(topPanel);
        add(downPanel);
    }
 void verReporteMovimientoProductos() {
        String enlace = "funcion=MovimientoProductoTerminado&" + getForm().getValues();
        if (print != null) {
            print.clear();
            print.destroy();
            print = null;
        }
        print = new ReporteMediaCartaChorroWindow(enlace);
        print.show();

    }
    private void initCombos() {

        com_producto = new ComboBox("Producto", "producto");
        SimpleStore proveedorStore = new SimpleStore(new String[]{"id", "nombre"}, producto);
        proveedorStore.load();


        com_producto.setMinChars(1);
        com_producto.setFieldLabel("Producto");
        com_producto.setStore(proveedorStore);
        com_producto.setValueField("id");
        com_producto.setDisplayField("nombre");
        com_producto.setForceSelection(true);
        com_producto.setMode(ComboBox.LOCAL);
        com_producto.setEmptyText("Buscar Producto");
        com_producto.setLoadingText("buscando...");
        com_producto.setTypeAhead(true);
        com_producto.setSelectOnFocus(true);
        com_producto.setWidth(200);

        com_producto.setHideTrigger(true);

        imprimirEstado = new Button("Consultar");
        imprimirMovimiento = new Button("Movimiento de Producto");

       dat_fechaini= new DateField("Fecha Ini", "fechaini");
        dat_fechaini.setFormat("Y-m-d");
        dat_fechaini.setName("fechaini");
        dat_fechaini.setValue(new Date());
//        Window.alert("2.8");
        dat_fechafin = new DateField("Fecha Fin", "fechafin");
        dat_fechafin.setFormat("Y-m-d");
        dat_fechafin.setName("fechafin");
        dat_fechafin.setValue(new Date());
//        Window.alert("2.11");
    }

    private void addListeners() {

        imprimirEstado.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                verReporteMovimientoProductos();
            }
        });
        imprimirMovimiento.addListener(new ButtonListenerAdapter() {

            @Override
            public void onClick(Button button, EventObject e) {
                verReporteMovimientoProductos();
            }
        });
    }

    private void initCombosValues() {

        com_producto.setValue("");

    }
}
