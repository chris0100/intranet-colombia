package com.eurekaintranet.controller.util;


import com.eurekaintranet.controller.OrdenesDespachoController;
import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.Comparator;



public class OrdenesComparator implements Comparator<OrdenesDespachoController> {


    @Override
    public int compare(OrdenesDespachoController o1, OrdenesDespachoController o2) {
        return new CompareToBuilder()
                .append(o1.getF_item(), o2.getF_item()).toComparison();
                //.append(o1.getF_desc_ext_detalle_1(), o2.getF_desc_ext_detalle_1()).toComparison(); //tamaño
                //.append(o1.getF_desc_ext_detalle_2(), o2.getF_desc_ext_detalle_2()).toComparison(); //color
    }


}
