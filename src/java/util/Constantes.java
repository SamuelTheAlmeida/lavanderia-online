/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.HashMap;
import java.util.Map;

public final class Constantes {
    public static final int PERFIL_CLIENTE = 1;
    public static final int PERFIL_FUNCIONARIO = 2;
    
    public static final int STATUS_PENDENTE_PAGAMENTO = 1;
    public static final int STATUS_EM_ANDAMENTO = 2;
    public static final int STATUS_AGUARDANDO_ENTREGA = 3;
    public static final int STATUS_ENTREGUE = 4;
    
    
    public static final HashMap<Integer, String> STATUS_PEDIDO = new HashMap<>();
    static {
        STATUS_PEDIDO.put(STATUS_PENDENTE_PAGAMENTO, "Pendente de Pagamento");
        STATUS_PEDIDO.put(STATUS_EM_ANDAMENTO, "Em andamento");
        STATUS_PEDIDO.put(STATUS_AGUARDANDO_ENTREGA, "Aguardando Entrega");
        STATUS_PEDIDO.put(STATUS_ENTREGUE, "Entregue");
    }
}
