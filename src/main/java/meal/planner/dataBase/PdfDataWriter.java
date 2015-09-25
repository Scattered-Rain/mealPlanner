package meal.planner.dataBase;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import meal.planner.dataBase.items.Ingredient;
import meal.planner.dataBase.items.Meal;
import meal.planner.dataBase.items.Recipe;

public class PdfDataWriter {
	
	/** Creates a PDF of the given Meal */
	public static void writePdf(Meal meal, String file) throws DocumentException, IOException{
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph(meal.getName()));
		document.add(new Paragraph("Price: "+meal.getPrice()));
		document.add(new Paragraph("Description: "+meal.getDescription()));
		document.add(new Paragraph(""));
		document.add(new Paragraph("Recipes:"));
		for(Recipe r : meal.getSubs()){
			document.add(new Paragraph(""));
			document.add(new Paragraph(r.getName()));
			document.add(new Paragraph("Price"+r.getPrice()));
			document.add(new Paragraph("Description: "+r.getDescription()));
			ArrayList<Ingredient> subs = r.getSubs();
			if(subs.size()>0){
				String ing = "";
				for(int c=0; c<subs.size()-1; c++){
					ing += subs.get(c)+", ";
				}
				ing += subs.get(subs.size()-1);
				document.add(new Paragraph("Ingredients: "+ing));
			}
		}
		document.close();
	}
	
}
