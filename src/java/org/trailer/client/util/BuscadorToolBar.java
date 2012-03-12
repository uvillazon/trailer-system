/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.trailer.client.util;

import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.QuickTipsConfig;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.ToolbarTextItem;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.TextField;

/**
 *
 * @author example
 */
public class BuscadorToolBar {

    private Toolbar toolbar;
    private String[] nombreItems;
    private String[] tiposComponentes;
    private ToolbarTextItem itemsLabel1;
    private ToolbarTextItem itemsLabel2;
    private ToolbarTextItem itemsLabel3;
    private ToolbarTextItem itemsLabel4;
    private ToolbarTextItem itemsLabel5;
    private TextField itemsText1;
    private TextField itemsText2;
    private TextField itemsText3;
    private TextField itemsText4;
    private TextField itemsText5;
    private DateField itemsDate1;
    private DateField itemsDate2;
    private DateField itemsDate3;
    private DateField itemsDate4;
    private DateField itemsDate5;
    private ComboBox itemsCombo1;
    private ComboBox itemsCombo2;
    private ComboBox itemsCombo3;
    private ComboBox itemsCombo4;
    private ComboBox itemsCombo5;
    private ToolbarButton buscar;

    public BuscadorToolBar(String[] items) {
        this.nombreItems = items;
    }

    public BuscadorToolBar(String[] items, String[] tiposComponentes) {
        this.nombreItems = items;
        this.tiposComponentes = tiposComponentes;
    }

    public void onModuleLoad() {
        //create a toolbar and various menu items
        if (nombreItems.length == 6) {
            // Window.alert("Error : solo esta permitido max " + nombreItems.length + " elementos, k1");
            MessageBox.alert("Error : solo esta permitido max " + nombreItems.length + " elementos en el buscador, k1");
        } else {
            initComponents();
            setEmpytyText();
        }
    }

    public Toolbar getToolbar() {
        onModuleLoad();
        return toolbar;
    }

    private void initComponents() {
        toolbar = new Toolbar();

        TextField[] itemsText = {itemsText1, itemsText2, itemsText3, itemsText4, itemsText5};
        DateField[] itemsDate = {itemsDate1, itemsDate2, itemsDate3, itemsDate4, itemsDate5};
        ComboBox[] itemsCombo = {itemsCombo1, itemsCombo2, itemsCombo3, itemsCombo4, itemsCombo5};
        // TextField[] componentes = null;
        ToolbarTextItem[] itemsLabel = {itemsLabel1, itemsLabel2, itemsLabel3, itemsLabel4, itemsLabel5};

        for (int i = 0; i < nombreItems.length; i++) {
            itemsLabel[i] = new ToolbarTextItem(nombreItems[i]);
            if (tiposComponentes == null) {
                itemsText[i] = new TextField();
            // componentes[i] = itemsText[i];
            } else {
                if (tiposComponentes[i].equalsIgnoreCase("text")) {
                    itemsText[i] = new TextField();
                } else if (tiposComponentes[i].equalsIgnoreCase("date")) {
                    itemsDate[i] = new DateField();
                    itemsDate[i].setFormat("d-m-Y");
                } else if (tiposComponentes[i].equalsIgnoreCase("combo")) {
                    itemsCombo[i] = new ComboBox();
                }

            }


            if (i > 0) {
                toolbar.addSeparator();
            }
            toolbar.addItem(itemsLabel[i]);
            if (tiposComponentes == null) {
                toolbar.addField(itemsText[i]);
            } else {
                if (tiposComponentes[i].equalsIgnoreCase("text")) {
                    toolbar.addField(itemsText[i]);
                } else if (tiposComponentes[i].equalsIgnoreCase("date")) {
                    toolbar.addField(itemsDate[i]);
                } else if (tiposComponentes[i].equalsIgnoreCase("combo")) {
                    toolbar.addField(itemsCombo[i]);
                }

            }


        }
        if (tiposComponentes == null) {
            if (itemsText.length == 1) {
                this.itemsText1 = itemsText[0];

            } else if (itemsText.length == 2) {
                this.itemsText1 = itemsText[0];
                this.itemsText2 = itemsText[1];

            } else if (itemsText.length == 3) {
                this.itemsText1 = itemsText[0];
                this.itemsText2 = itemsText[1];
                this.itemsText3 = itemsText[2];

            } else if (itemsText.length == 4) {
                this.itemsText1 = itemsText[0];
                this.itemsText2 = itemsText[1];
                this.itemsText3 = itemsText[2];
                this.itemsText4 = itemsText[3];

            } else if (itemsText.length == 5) {
                this.itemsText1 = itemsText[0];
                this.itemsText2 = itemsText[1];
                this.itemsText3 = itemsText[2];
                this.itemsText4 = itemsText[3];
                this.itemsText5 = itemsText[4];

            }
        } else {
            if (tiposComponentes.length == 1) {
                if (tiposComponentes[0].equalsIgnoreCase("text")) {
                    this.itemsText1 = itemsText[0];
                } else if (tiposComponentes[0].equalsIgnoreCase("date")) {
                    this.itemsDate1 = itemsDate[0];
                } else if (tiposComponentes[0].equalsIgnoreCase("combo")) {
                    this.itemsCombo1 = itemsCombo[0];
                }
            } else if (tiposComponentes.length == 2) {
                if (tiposComponentes[0].equalsIgnoreCase("text")) {
                    this.itemsText1 = itemsText[0];
                } else if (tiposComponentes[0].equalsIgnoreCase("date")) {
                    this.itemsDate1 = itemsDate[0];
                } else if (tiposComponentes[0].equalsIgnoreCase("combo")) {
                    this.itemsCombo1 = itemsCombo[0];
                }
                if (tiposComponentes[1].equalsIgnoreCase("text")) {
                    this.itemsText2 = itemsText[1];
                } else if (tiposComponentes[1].equalsIgnoreCase("date")) {
                    this.itemsDate2 = itemsDate[1];
                } else if (tiposComponentes[1].equalsIgnoreCase("combo")) {
                    this.itemsCombo2 = itemsCombo[1];
                }

            } else if (tiposComponentes.length == 3) {
                if (tiposComponentes[0].equalsIgnoreCase("text")) {
                    this.itemsText1 = itemsText[0];
                } else if (tiposComponentes[0].equalsIgnoreCase("date")) {
                    this.itemsDate1 = itemsDate[0];
                } else if (tiposComponentes[0].equalsIgnoreCase("combo")) {
                    this.itemsCombo1 = itemsCombo[0];
                }
                if (tiposComponentes[1].equalsIgnoreCase("text")) {
                    this.itemsText2 = itemsText[1];
                } else if (tiposComponentes[1].equalsIgnoreCase("date")) {
                    this.itemsDate2 = itemsDate[1];
                } else if (tiposComponentes[1].equalsIgnoreCase("combo")) {
                    this.itemsCombo2 = itemsCombo[1];
                }
                if (tiposComponentes[2].equalsIgnoreCase("text")) {
                    this.itemsText3 = itemsText[2];
                } else if (tiposComponentes[2].equalsIgnoreCase("date")) {
                    this.itemsDate3 = itemsDate[2];
                } else if (tiposComponentes[2].equalsIgnoreCase("combo")) {
                    this.itemsCombo3 = itemsCombo[2];
                }
            } else if (tiposComponentes.length == 4) {
                if (tiposComponentes[0].equalsIgnoreCase("text")) {
                    this.itemsText1 = itemsText[0];
                } else if (tiposComponentes[0].equalsIgnoreCase("date")) {
                    this.itemsDate1 = itemsDate[0];
                } else if (tiposComponentes[0].equalsIgnoreCase("combo")) {
                    this.itemsCombo1 = itemsCombo[0];
                }
                if (tiposComponentes[1].equalsIgnoreCase("text")) {
                    this.itemsText2 = itemsText[1];
                } else if (tiposComponentes[1].equalsIgnoreCase("date")) {
                    this.itemsDate2 = itemsDate[1];
                } else if (tiposComponentes[1].equalsIgnoreCase("combo")) {
                    this.itemsCombo2 = itemsCombo[1];
                }
                if (tiposComponentes[2].equalsIgnoreCase("text")) {
                    this.itemsText3 = itemsText[2];
                } else if (tiposComponentes[2].equalsIgnoreCase("date")) {
                    this.itemsDate3 = itemsDate[2];
                } else if (tiposComponentes[2].equalsIgnoreCase("combo")) {
                    this.itemsCombo3 = itemsCombo[2];
                }
                if (tiposComponentes[3].equalsIgnoreCase("text")) {
                    this.itemsText4 = itemsText[3];
                } else if (tiposComponentes[3].equalsIgnoreCase("date")) {
                    this.itemsDate4 = itemsDate[3];
                } else if (tiposComponentes[3].equalsIgnoreCase("combo")) {
                    this.itemsCombo4 = itemsCombo[3];
                }
            } else if (tiposComponentes.length == 5) {
                if (tiposComponentes[0].equalsIgnoreCase("text")) {
                    this.itemsText1 = itemsText[0];
                } else if (tiposComponentes[0].equalsIgnoreCase("date")) {
                    this.itemsDate1 = itemsDate[0];
                } else if (tiposComponentes[0].equalsIgnoreCase("combo")) {
                    this.itemsCombo1 = itemsCombo[0];
                }
                if (tiposComponentes[1].equalsIgnoreCase("text")) {
                    this.itemsText2 = itemsText[1];
                } else if (tiposComponentes[1].equalsIgnoreCase("date")) {
                    this.itemsDate2 = itemsDate[1];
                } else if (tiposComponentes[1].equalsIgnoreCase("combo")) {
                    this.itemsCombo2 = itemsCombo[1];
                }
                if (tiposComponentes[2].equalsIgnoreCase("text")) {
                    this.itemsText3 = itemsText[2];
                } else if (tiposComponentes[2].equalsIgnoreCase("date")) {
                    this.itemsDate3 = itemsDate[2];
                } else if (tiposComponentes[2].equalsIgnoreCase("combo")) {
                    this.itemsCombo3 = itemsCombo[2];
                }
                if (tiposComponentes[3].equalsIgnoreCase("text")) {
                    this.itemsText4 = itemsText[3];
                } else if (tiposComponentes[3].equalsIgnoreCase("date")) {
                    this.itemsDate4 = itemsDate[3];
                } else if (tiposComponentes[3].equalsIgnoreCase("combo")) {
                    this.itemsCombo4 = itemsCombo[3];
                }

                if (tiposComponentes[4].equalsIgnoreCase("text")) {
                    this.itemsText5 = itemsText[4];
                } else if (tiposComponentes[4].equalsIgnoreCase("date")) {
                    this.itemsDate5 = itemsDate[4];
                } else if (tiposComponentes[4].equalsIgnoreCase("combo")) {
                    this.itemsCombo5 = itemsCombo[4];
                }
            }
        }

        buscar = new ToolbarButton("Buscar");

        buscar.setEnableToggle(true);
        QuickTipsConfig tipsConfig = new QuickTipsConfig();

        tipsConfig.setText("Iniciar busqueda");

        // tipsConfig.setTitle("Tip Title");
        buscar.setTooltip(tipsConfig);

        toolbar.addSeparator();

        toolbar.addButton(buscar);
    }

    public ToolbarButton getBuscar() {
        return buscar;
    }

    public TextField getItemsText1() {
        return itemsText1;
    }

    public TextField getItemsText2() {
        return itemsText2;
    }

    public TextField getItemsText3() {
        return itemsText3;
    }

    public TextField getItemsText4() {
        return itemsText4;
    }

    public TextField getItemsText5() {
        return itemsText5;
    }

    public ComboBox getItemsCombo1() {
        return itemsCombo1;
    }

    public ComboBox getItemsCombo2() {
        return itemsCombo2;
    }

    public ComboBox getItemsCombo3() {
        return itemsCombo3;
    }

    public ComboBox getItemsCombo4() {
        return itemsCombo4;
    }

    public ComboBox getItemsCombo5() {
        return itemsCombo5;
    }

    public DateField getItemsDate1() {
        return itemsDate1;
    }

    public DateField getItemsDate2() {
        return itemsDate2;
    }

    public DateField getItemsDate3() {
        return itemsDate3;
    }

    public DateField getItemsDate4() {
        return itemsDate4;
    }

    public DateField getItemsDate5() {
        return itemsDate5;
    }

    private void setEmpytyText() {
        String palabraBuscador = "Buscar por ";

        if (this.itemsText1 != null) {
            this.itemsText1.setEmptyText(palabraBuscador + nombreItems[0]);
        }

        if (this.itemsText2 != null) {
            this.itemsText2.setEmptyText(palabraBuscador + nombreItems[1]);
        }

        if (this.itemsText3 != null) {
            this.itemsText3.setEmptyText(palabraBuscador + nombreItems[2]);
        }

        if (this.itemsText4 != null) {
            this.itemsText4.setEmptyText(palabraBuscador + nombreItems[3]);
        }

        if (this.itemsText5 != null) {
            this.itemsText5.setEmptyText(palabraBuscador + nombreItems[4]);
        }
        if (this.itemsCombo1 != null) {
            this.itemsCombo1.setEmptyText(palabraBuscador + nombreItems[0]);
        }

        if (this.itemsCombo2 != null) {
            this.itemsCombo2.setEmptyText(palabraBuscador + nombreItems[1]);
        }

        if (this.itemsCombo3 != null) {
            this.itemsCombo3.setEmptyText(palabraBuscador + nombreItems[2]);
        }

        if (this.itemsCombo4 != null) {
            this.itemsCombo4.setEmptyText(palabraBuscador + nombreItems[3]);
        }

        if (this.itemsCombo5 != null) {
            this.itemsCombo5.setEmptyText(palabraBuscador + nombreItems[4]);
        }
        if (this.itemsDate1 != null) {
            this.itemsDate1.setEmptyText(palabraBuscador + nombreItems[0]);
        }

        if (this.itemsDate2 != null) {
            this.itemsDate2.setEmptyText(palabraBuscador + nombreItems[1]);
        }

        if (this.itemsDate3 != null) {
            this.itemsCombo3.setEmptyText(palabraBuscador + nombreItems[2]);
        }

        if (this.itemsDate4 != null) {
            this.itemsDate4.setEmptyText(palabraBuscador + nombreItems[3]);
        }

        if (this.itemsDate5 != null) {
            this.itemsDate5.setEmptyText(palabraBuscador + nombreItems[4]);
        }
    }
}
