/*
 * Universidade de Aveiro, 2017
 * M�todos Probab�listicos para Engenharia Inform�tica
 * Mestrado Integrado em Engenharia de Computadores e Telem�tica
 * Marco Silva(84770) e Raquel Rainho(84891)
 */

package proj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// � nesta classe que se vai ler o ficheiro DadosCliente
public class ClienteSet {
	private List<Cliente> clienteSet;
	private Map<Integer, LinkedList<Compras>> comprasFeitasPeloCliente;
	private ComprasSet comprasSet;
	
	public ClienteSet() {
		clienteSet = new ArrayList<>();// VER MELHOR FORMA DE FAZER ISTO!!
		comprasFeitasPeloCliente = new HashMap<>();
		comprasSet = new ComprasSet();
		addClienteFromFile();
	}
	
	private void addClienteFromFile() {
		List<String> aux = new ArrayList<>();
		FileRdWr.readFile("DadosCliente.txt", aux);
		Cliente c;
		for (String elem: aux) {
			c = new Cliente(elem);
			clienteSet.add(c);
			comprasFeitasPeloCliente.put(c.getNif(), comprasSet.comprasDoCliente(c.getNif()));
		}
	}
	
	// Depois de j� ter sido lido o ficheiro, podemos querer adicionar novos clientes
	public boolean addCliente(Cliente c) {
		if (c == null) return false;
		for(Cliente client : clienteSet)
			if (client.equals(c)) return false;
		clienteSet.add(c);
		
		return true;
	}
	
	public String getCliente(int index){
		return clienteSet.get(index).toString();
	}
	
	public void printClientes() {
		clienteSet.forEach(System.out::println);
	}
}
