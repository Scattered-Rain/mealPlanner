package meal.planner.dataBase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import meal.planner.dataBase.items.Ingredient;
import meal.planner.dataBase.items.Meal;
import meal.planner.dataBase.items.Recipe;

public class PdfDataWriter {
	
//	public static void main(String[] args){
//		Meal m = new Meal(0);
//		m.setName("Something");
//		m.setDescription("Else");
//		for(int c=0; c<5; c++){
//			Recipe r = new Recipe(1);
//			r.setName("234234");
//			r.setDescription("sdfshdfhsgs");
//			for(int c2=0; c2<4; c2++){
//				Ingredient i = new Ingredient(2);
//				i.setName("poop");
//				i.setDescription("sdgsddgs");
//				i.setPrice(5);
//				r.addIngredient(i, 2);
//			}
//			m.addSub(r);
//		}
//		try{
//			writePdf(m, new File("C:/Users/Flo/Desktop/fdfd.pdf"), 4);
//		}catch(Exception ex){}
//	}
	
	/** Creates a PDF of the given Meal */
	public static void writePdf(Meal meal, File file, int attendees) throws DocumentException, IOException{
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph(meal.getName()+" for "+attendees+" people"));
		document.add(new Paragraph("Price: $"+Math.round(meal.getPrice()*attendees*100)/100));
		document.add(new Paragraph("Description: "+meal.getDescription()));
		document.add(Chunk.NEWLINE);
		//document.add(new Paragraph("Recipes:"));
		int days = 0;
		for(Recipe r : meal.getSubs()){
			days++;
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph("Day "+days));
			document.add(new Paragraph(r.getName()));
			document.add(new Paragraph("Price: $"+Math.round(r.getPrice()*attendees*100)/100));
			ArrayList<Ingredient> subs = r.getSubs();
			if(subs.size()>0){
				String ing = "";
				for(int c=0; c<subs.size()-1; c++){
					ing += Math.round(r.getHashIngs().get(subs.get(c))*attendees * 100.0) / 100.0+" ";
					ing += subs.get(c).getName()+", ";
				}
				ing += Math.round(r.getHashIngs().get(subs.get(subs.size()-1))*attendees * 100.0) / 100.0+" ";
				ing += subs.get(subs.size()-1).getName();
				document.add(new Paragraph("Ingredients: "+ing));
			}
			document.add(new Paragraph("Description: "+r.getDescription()));
		}
		document.close();
	}
	
	public static void writePdf(HashMap<Ingredient, Double> ingrdients, File file) throws DocumentException, IOException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Shopping List"));
		document.add(new Paragraph(""));
		document.add(new Paragraph(""));
		document.add(new Paragraph("Items:"));
		for (Ingredient i : ingrdients.keySet()) {
			document.add(new Paragraph(String.format("%sx - %s", ingrdients.get(i), i.getName())));
		}
		document.close();
	}
}
