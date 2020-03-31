package it.polito.tdp.lab04.model;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		

    	for (Corso c: model.getAllCorsi())
    		if (c!=null)
    			System.out.println(c.getNome()+"\n");
    	


	}

}
