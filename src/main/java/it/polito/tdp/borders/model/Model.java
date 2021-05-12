package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private SimpleGraph<Country,DefaultEdge> grafo;
	private Map<Integer,Country> idMap;
	private BordersDAO dao;

	public Model() {
		idMap = new HashMap<>();
		dao = new BordersDAO();
		dao.loadAllCountries(idMap);
	}
	
	public void creaGrafo(int anno) {
		grafo = new SimpleGraph<>(DefaultEdge.class);
		
		
		for(Border b : dao.getCountryPairs(anno, idMap)) {
			if(!grafo.containsVertex(b.getC1())) {
				grafo.addVertex(b.getC1());
			}
			
			if(!grafo.containsVertex(b.getC2())) {
				grafo.addVertex(b.getC2());
			}
			
			grafo.addEdge(b.getC1(), b.getC2());
		}
		
		System.out.println("GRAFO CREATO");
		System.out.println("# VERTICI: "+grafo.vertexSet().size());
		System.out.println("# ARCHI: "+grafo.edgeSet().size());
		
	}
	
	public SimpleGraph<Country, DefaultEdge> getGrafo() {
		return grafo;
	}
	
	public List<Country> statiRaggiungibili(Country partenza){
		
		BreadthFirstIterator<Country,DefaultEdge> bfv = new BreadthFirstIterator<>(this.grafo,partenza);
		//DepthFirstIterator <Country,DefaultEdge> bfv = new DepthFirstIterator<>(this.grafo,partenza);
		List<Country> raggiungibili = new ArrayList<>();
		
		while(bfv.hasNext()) {
			Country c = bfv.next();
			
			if(!c.equals(partenza)) {
				raggiungibili.add(c);
			}
			
		}
		
		System.out.println("Reachable countries: "+raggiungibili.size());
		
		return raggiungibili;
	}
	
	public List<Country> statiRaggiungibili2(Country partenza){
		
		List<Country> daVisitare = new LinkedList<>();
		List<Country> visitati = new LinkedList<>();
		
		daVisitare.add(partenza);
		
		while(!daVisitare.isEmpty()) {
			
			for(Country c : daVisitare) {
				
				for(DefaultEdge e : grafo.outgoingEdgesOf(c)) {
					
					Country target = grafo.getEdgeTarget(e); // sono da controllare entrambi perché, anche se sto iterando sui outgoingEdges, non è detto che c sia sempre source
					Country source = grafo.getEdgeSource(e);
					
					if(!daVisitare.contains(target) && !visitati.contains(target)) {
						daVisitare.add(target);
					}
					
					if(!daVisitare.contains(source) && !visitati.contains(source)) {
						daVisitare.add(source);
					}
				}
				
				daVisitare.remove(c);
				visitati.add(c);
			}
		}
		
		visitati.remove(partenza);
		
		System.out.println("Reachable countries: "+visitati.size());
		
		return visitati;
		
		
	}
	
	public List<Country> statiRaggiungibili3(Country partenza){
		
		List<Country> visitati = new ArrayList<>();
		
		ricorsione(partenza,visitati,0);
		
		visitati.remove(partenza);
		System.out.println("Reachable countries: "+visitati.size());
		
		return visitati;
	}
	
	private void ricorsione(Country corrente, List<Country> visitati,int livello) {
		
		visitati.add(corrente);
			
		for(DefaultEdge e : grafo.outgoingEdgesOf(corrente)) {
			
			Country target = grafo.getEdgeTarget(e);
			Country source = grafo.getEdgeSource(e);
			
			if(!visitati.contains(target)) {
				ricorsione(target, visitati, livello+1);
			} 
			
			if(!visitati.contains(source)) {
				ricorsione(source, visitati, livello+1);
			} 

		}
		
		
		
	}
	
}
