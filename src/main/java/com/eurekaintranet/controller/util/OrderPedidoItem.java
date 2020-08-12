package com.eurekaintranet.controller.util;

import com.eurekaintranet.controller.OrdenesDespachoController;

import java.util.Comparator;

//Se va a ordenar por medio de descripcion item, cantidad y color del mismo.
// Extension1(descripcion_ext1), Extension2(descripcion_ext2)
public enum OrderPedidoItem implements Comparator<OrdenesDespachoController> {


    //CRITERIOS DE ORDENAMIENTO PARA ITEMPEDIDO.
    ITEM_SORT{
        public int compare(OrdenesDespachoController ip1, OrdenesDespachoController ip2){
            return ip1.getF_item().compareTo(ip2.getF_item());
        }},

    DESC_EXT1_SORT{
        public int compare(OrdenesDespachoController ip1, OrdenesDespachoController ip2){
            return ip1.getF_desc_ext_detalle_1().compareTo(ip2.getF_desc_ext_detalle_1());
        }},

    DESC_EXT2_SORT{
        public int compare(OrdenesDespachoController ip1, OrdenesDespachoController ip2){
            return ip1.getF_desc_ext_detalle_2().compareTo(ip2.getF_desc_ext_detalle_2());
        }
    };


    //Ordena de manera descendente
    public static Comparator<OrdenesDespachoController> ascending(final Comparator<OrdenesDespachoController> other){
        return new Comparator<OrdenesDespachoController>(){
            public int compare(OrdenesDespachoController ip1, OrdenesDespachoController ip2){
                return other.compare(ip1, ip2);
            }
        };
    }



    //Realiza el proceso de comparacion.
    public static Comparator<OrdenesDespachoController> getComparator(final OrderPedidoItem... multipleOptions){
        return new Comparator<OrdenesDespachoController>() {
            @Override
            public int compare(OrdenesDespachoController o1, OrdenesDespachoController o2) {
                for (OrderPedidoItem option : multipleOptions){
                    int result = option.compare(o1, o2);
                    if (result != 0){
                        return result;
                    }
                }
                return 0;
            }
        };
    }

}
