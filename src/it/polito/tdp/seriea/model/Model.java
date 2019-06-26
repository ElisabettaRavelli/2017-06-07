package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	
	private SerieADAO dao;
	private Graph<Team, DefaultWeightedEdge> grafo;
	private List<Team> teams;
	private List<Season> seasons;
	private Map<String, Team> teamIdMap;
	
	public Model() {
		this.dao = new SerieADAO();
		this.teamIdMap = new HashMap<>();
		
		this.teams = dao.listTeams(teamIdMap);
		this.seasons = dao.listSeasons();
		
	}

	public List<Season> getSeason() {
		return seasons;
	}
	
	public List<Team> getTeam(){
		return teams;
	}
	
	public void creaGrafo(Season season) {
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		List<Match> matches = dao.listMatches(season, teamIdMap);
		
		for (Match m : matches) {
			grafo.addVertex(m.getHomeTeam());
			grafo.addVertex(m.getAwayTeam());
			
			if(m.getFtr().equals("H")) {
				Double peso = 1.0;
				Graphs.addEdge(this.grafo, m.getHomeTeam(), m.getAwayTeam(), peso);
							
			}else if(m.getFtr().equals("D")) {
				Double peso = 0.0;
				Graphs.addEdge(this.grafo, m.getHomeTeam(), m.getAwayTeam(), peso);
						
			}else if(m.getFtr().equals("A")) {
				Double peso = -1.0;
				Graphs.addEdge(this.grafo, m.getHomeTeam(), m.getAwayTeam(), peso);
			}
		}
		
		System.out.println("Grafo creato");
		
	}


	
	public int getVertici() {
		return this.grafo.vertexSet().size();
	}
	public int getArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Team> getClassifica() {
		// azzero i punteggi
		for (Team t : grafo.vertexSet())
			t.setPunti(0);

		// considero ogni partita
		for (DefaultWeightedEdge e : grafo.edgeSet()) {
			Team home = grafo.getEdgeSource(e);
			Team away = grafo.getEdgeTarget(e);
			switch ((int) grafo.getEdgeWeight(e)) {
			case +1:
				home.setPunti(home.getPunti() + 3);
				break;
			case -1:
				away.setPunti(away.getPunti() + 3);
				break;
			case 0:
				home.setPunti(home.getPunti() + 1);
				away.setPunti(away.getPunti() + 1);
				break;
			}
		}

		List<Team> classifica = new ArrayList<Team>(grafo.vertexSet());
		Collections.sort(classifica, new Comparator<Team>() {

			@Override
			public int compare(Team o1, Team o2) {
				return -(o1.getPunti() - o2.getPunti());
			}
		});

		return classifica;
	}
}
