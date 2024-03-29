/*
 * Universidade de Aveiro, 2017
 * Métodos Probabílisticos para Engenharia Informática
 * Mestrado Integrado em Engenharia de Computadores e Telemática
 * Marco Silva(84770) e Raquel Rainho(84891)
 */

package proj;

import java.util.HashMap;
import java.util.Map;

public class Loja implements LojaInterface{
	private String nomeLoja;
	private Map<String, Integer> produtos;
	private CountFilter countFilterProdutos;
	
	public Loja(String nomeLoja, String listaProdutos) {
		this.nomeLoja = nomeLoja;
		produtos = new HashMap<>();
		String [] aux = listaProdutos.split(", ");
		countFilterProdutos = new CountFilter(aux.length, 5);
		for (String elem: aux) {
			addProduto(elem.split(": ")[0], Integer.parseInt(elem.split(": ")[1]));

			// elem.split(": ")[0] --> Nome do produto (chave)
			// elem.split(": ")[1] --> Quantidade do produto (valor)	
		}
	}
	
	public String getNomeLoja() {
		return nomeLoja;
	}
	
	public String[] produtos() {
		String[] aux = new String[produtos.size()];
		int i=0;
		for(String produto : produtos.keySet()) {
			aux[i]=produto;
			i++;
		}
		return aux;
	}
	
	public int getQuantidade(String produto) {
		return produtos.get(produto);
	}
	
	public void atualizarStock(String produto, int quantidadeComprada) {
		if(produtos.containsKey(produto))
			produtos.put(produto, produtos.get(produto) - quantidadeComprada);
		for(int i=0; i<quantidadeComprada; i++)
			countFilterProdutos.bloomRemove(produto);
	}
	
	public void reporStock(String produto, int quantidadeARepor) {
		produtos.put(produto, produtos.get(produto)+quantidadeARepor);
		for(int i=0; i<quantidadeARepor; i++)
			countFilterProdutos.bloomInsertion(produto);
	}
	
	@Override
	public boolean addProduto(String produto, int quantidade) {
		if(produto == null)
			return false;
		
		for(int i=0; i<quantidade; i++)
			countFilterProdutos.bloomInsertion(produto);
		if(produtos.containsKey(produto)){
			int aux = produtos.get(produto) + quantidade;
			produtos.put(produto, aux);
			return true;
		}
		else{
			produtos.put(produto, quantidade);
			return true;
		}
	}
	
	public String hashToString() {
		String s = "";
		for (String elem: produtos.keySet()) 
			s += elem + ": "+ produtos.get(elem) + ", ";
		
		return s.substring(0, s.length() - 2); // para nao aparecer ", " no ultimo elemento
	}
	
	@Override
	public String toString() {
		return nomeLoja + "\t" + hashToString();
	}

	public boolean contains(String produto, int quant) {
		System.out.println("1");
		if(produto==null) return false;
		return countFilterProdutos.isMember(produto) && countFilterProdutos.count(produto)-quant>=0;
	}
}
